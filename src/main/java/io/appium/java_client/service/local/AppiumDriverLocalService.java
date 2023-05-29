/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.appium.java_client.service.local;

import com.google.common.annotations.VisibleForTesting;
import io.appium.java_client.internal.ReflectionHelpers;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.net.UrlChecker;
import org.openqa.selenium.os.CommandLine;
import org.openqa.selenium.remote.service.DriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.appium.java_client.service.local.AppiumServiceBuilder.BROADCAST_IP4_ADDRESS;
import static io.appium.java_client.service.local.AppiumServiceBuilder.BROADCAST_IP6_ADDRESS;
import static org.slf4j.event.Level.DEBUG;
import static org.slf4j.event.Level.INFO;

public final class AppiumDriverLocalService extends DriverService {

    private static final String URL_MASK = "http://%s:%d/";
    private static final Logger LOG = LoggerFactory.getLogger(AppiumDriverLocalService.class);
    private static final Pattern LOGGER_CONTEXT_PATTERN = Pattern.compile("^(\\[debug\\] )?\\[(.+?)\\]");
    private static final String APPIUM_SERVICE_SLF4J_LOGGER_PREFIX = "appium.service";
    private static final Duration DESTROY_TIMEOUT = Duration.ofSeconds(60);
    private static final Duration IS_RUNNING_PING_TIMEOUT = Duration.ofMillis(1500);

    private final File nodeJSExec;
    private final List<String> nodeJSArgs;
    private final Map<String, String> nodeJSEnvironment;
    private final Duration startupTimeout;
    private final ReentrantLock lock = new ReentrantLock(true); //uses "fair" thread ordering policy
    private final ListOutputStream stream = new ListOutputStream().add(System.out);
    private final URL url;
    private String basePath;

    private CommandLine process = null;

    AppiumDriverLocalService(String ipAddress, File nodeJSExec,
                             int nodeJSPort, Duration startupTimeout,
                             List<String> nodeJSArgs, Map<String, String> nodeJSEnvironment
    ) throws IOException {
        super(nodeJSExec, nodeJSPort, startupTimeout, nodeJSArgs, nodeJSEnvironment);
        this.nodeJSExec = nodeJSExec;
        this.nodeJSArgs = nodeJSArgs;
        this.nodeJSEnvironment = nodeJSEnvironment;
        this.startupTimeout = startupTimeout;
        this.url = new URL(String.format(URL_MASK, ipAddress, nodeJSPort));
    }

    public static AppiumDriverLocalService buildDefaultService() {
        return buildService(new AppiumServiceBuilder());
    }

    public static AppiumDriverLocalService buildService(AppiumServiceBuilder builder) {
        return builder.build();
    }

    public AppiumDriverLocalService withBasePath(String basePath) {
        this.basePath = basePath;
        return this;
    }

    public String getBasePath() {
        return this.basePath;
    }

    @SneakyThrows
    private static URL addSuffix(URL url, String suffix) {
        return url.toURI().resolve("." + (suffix.startsWith("/") ? suffix : "/" + suffix)).toURL();
    }

    @SneakyThrows
    @SuppressWarnings("SameParameterValue")
    private static URL replaceHost(URL source, String oldHost, String newHost) {
        return new URL(source.toString().replaceFirst(oldHost, newHost));
    }

    /**
     * Base URL.
     *
     * @return The base URL for the managed appium server.
     */
    @Override
    public URL getUrl() {
        return basePath == null ? url : addSuffix(url, basePath);
    }

    @Override
    public boolean isRunning() {
        lock.lock();
        try {
            if (process == null || !process.isRunning()) {
                return false;
            }

            try {
                ping(IS_RUNNING_PING_TIMEOUT);
                return true;
            } catch (UrlChecker.TimeoutException e) {
                return false;
            } catch (MalformedURLException e) {
                throw new AppiumServerHasNotBeenStartedLocallyException(e.getMessage(), e);
            }
        } finally {
            lock.unlock();
        }

    }

    private void ping(Duration timeout) throws UrlChecker.TimeoutException, MalformedURLException {
        URL url = getUrl();
        String host = url.getHost();
        // The operating system will block direct access to the universal broadcast IP address
        if (host.equals(BROADCAST_IP4_ADDRESS)) {
            url = replaceHost(url, BROADCAST_IP4_ADDRESS, "127.0.0.1");
        } else if (host.equals(BROADCAST_IP6_ADDRESS)) {
            url = replaceHost(url, BROADCAST_IP6_ADDRESS, "::1");
        }
        URL status = addSuffix(url, "/status");
        new UrlChecker().waitUntilAvailable(timeout.toMillis(), TimeUnit.MILLISECONDS, status);
    }

    /**
     * Starts the defined appium server.
     *
     * @throws AppiumServerHasNotBeenStartedLocallyException If an error occurs while spawning the child process.
     * @see #stop()
     */
    public void start() throws AppiumServerHasNotBeenStartedLocallyException {
        lock.lock();
        try {
            if (isRunning()) {
                return;
            }

            try {
                process = new CommandLine(
                        this.nodeJSExec.getCanonicalPath(),
                        nodeJSArgs.toArray(new String[]{})
                );
                process.setEnvironmentVariables(nodeJSEnvironment);
                process.copyOutputTo(stream);
                process.executeAsync();
                ping(startupTimeout);
            } catch (Exception e) {
                final Optional<String> output = Optional.ofNullable(process)
                        .map(CommandLine::getStdOut)
                        .filter((o) -> !StringUtils.isBlank(o));
                destroyProcess();
                List<String> errorLines = new ArrayList<>();
                errorLines.add("The local appium server has not been started");
                errorLines.add(String.format("Reason: %s", e.getMessage()));
                if (e instanceof UrlChecker.TimeoutException) {
                    errorLines.add(String.format(
                            "Consider increasing the server startup timeout value (currently %sms)",
                            startupTimeout.toMillis()
                    ));
                }
                errorLines.add(
                        String.format("Node.js executable path: %s", nodeJSExec.getAbsolutePath())
                );
                errorLines.add(String.format("Arguments: %s", nodeJSArgs));
                output.ifPresent((o) -> errorLines.add(String.format("Output: %s", o)));
                throw new AppiumServerHasNotBeenStartedLocallyException(
                        StringUtils.joinWith("\n", errorLines), e
                );
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Stops this service is it is currently running. This method will attempt to block until the
     * server has been fully shutdown.
     *
     * @see #start()
     */
    @Override
    public void stop() {
        lock.lock();
        try {
            if (process != null) {
                destroyProcess();
            }
            process = null;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Destroys the service if it is running.
     *
     * @param timeout The maximum time to wait before the process will be force-killed.
     * @return The exit code of the process or zero if the process was not running.
     */
    private int destroyProcess(Duration timeout) {
        if (process == null || !process.isRunning()) {
            return 0;
        }

        // This all magic is necessary, because Selenium does not publicly expose
        // process killing timeouts. By default a process is killed forcibly if
        // it does not exit after two seconds, which is in most cases not enough for
        // Appium
        try {
            Object osProcess = ReflectionHelpers.getPrivateFieldValue(
                    process.getClass(), process, "process", Object.class
            );
            Object watchdog = ReflectionHelpers.getPrivateFieldValue(
                    osProcess.getClass(), osProcess, "executeWatchdog", Object.class
            );
            Process nativeProcess = ReflectionHelpers.getPrivateFieldValue(
                    watchdog.getClass(), watchdog, "process", Process.class
            );
            nativeProcess.destroy();
            nativeProcess.waitFor(timeout.toMillis(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            LOG.warn("No explicit timeout could be applied to the process termination", e);
        }

        return process.destroy();
    }

    /**
     * Destroys the service.
     * This methods waits up to `DESTROY_TIMEOUT` seconds for the Appium service
     * to exit gracefully.
     */
    private void destroyProcess() {
        destroyProcess(DESTROY_TIMEOUT);
    }

    /**
     * Logs as string.
     *
     * @return String logs if the server has been run. Null is returned otherwise.
     */
    @Nullable
    public String getStdOut() {
        if (process != null) {
            return process.getStdOut();
        }

        return null;
    }

    /**
     * Adds other output stream which should accept server output data.
     *
     * @param outputStream is an instance of {@link OutputStream}
     *                     that is ready to accept server output
     */
    public void addOutPutStream(OutputStream outputStream) {
        checkNotNull(outputStream, "outputStream parameter is NULL!");
        stream.add(outputStream);
    }

    /**
     * Adds other output streams which should accept server output data.
     *
     * @param outputStreams is a list of additional {@link OutputStream}
     *                      that are ready to accept server output
     */
    public void addOutPutStreams(List<OutputStream> outputStreams) {
        checkNotNull(outputStreams, "outputStreams parameter is NULL!");
        for (OutputStream stream : outputStreams) {
            addOutPutStream(stream);
        }
    }

    /**
     * Remove the outputStream which is receiving server output data.
     *
     * @return the outputStream has been removed if it is present
     */
    public Optional<OutputStream> removeOutPutStream(OutputStream outputStream) {
        checkNotNull(outputStream, "outputStream parameter is NULL!");
        return stream.remove(outputStream);
    }

    /**
     * Remove all existing server output streams.
     *
     * @return true if at least one output stream has been cleared
     */
    public boolean clearOutPutStreams() {
        return stream.clear();
    }

    /**
     * Enables server output data logging through
     * <a href="http://slf4j.org">SLF4J</a> loggers. This allow server output
     * data to be configured with your preferred logging frameworks (e.g.
     * java.util.logging, logback, log4j).
     *
     * <p>NOTE1: You might want to call method {@link #clearOutPutStreams()} before
     * calling this method.<br>
     * NOTE2: it is required that {@code --log-timestamp} server flag is
     * {@code false}.
     *
     * <p>By default log messages are:
     * <ul>
     * <li>logged at {@code INFO} level, unless log message is pre-fixed by
     * {@code [debug]} then logged at {@code DEBUG} level.</li>
     * <li>logged by a <a href="http://slf4j.org">SLF4J</a> logger instance with
     * a name corresponding to the appium sub module as prefixed in log message
     * (logger name is transformed to lower case, no spaces and prefixed with
     * "appium.service.").</li>
     * </ul>
     * Example log-message: "[ADB] Cannot read version codes of " is logged by
     * logger: {@code appium.service.adb} at level {@code INFO}.
     * <br>
     * Example log-message: "[debug] [XCUITest] Xcode version set to 'x.y.z' "
     * is logged by logger {@code appium.service.xcuitest} at level
     * {@code DEBUG}.
     * <br>
     *
     * @see #addSlf4jLogMessageConsumer(BiConsumer)
     */
    public void enableDefaultSlf4jLoggingOfOutputData() {
        addSlf4jLogMessageConsumer((logMessage, ctx) -> {
            if (ctx.getLevel().equals(DEBUG)) {
                ctx.getLogger().debug(logMessage);
            } else {
                ctx.getLogger().info(logMessage);
            }
        });
    }

    /**
     * When a complete log message is available (from server output data) that
     * message is parsed for its slf4j context (logger name, logger level etc.)
     * and the specified {@code BiConsumer} is invoked with the log message and
     * slf4j context.
     *
     * <p>Use this method only if you want a behavior that differentiates from the
     * default behavior as enabled by method
     * {@link #enableDefaultSlf4jLoggingOfOutputData()}.
     *
     * <p>NOTE: You might want to call method {@link #clearOutPutStreams()} before
     * calling this method.
     *
     * <p>implementation detail:
     * <ul>
     * <li>if log message begins with {@code [debug]} then log level is set to
     * {@code DEBUG}, otherwise log level is {@code INFO}.</li>
     * <li>the appium sub module name is parsed from the log message and used as
     * logger name (prefixed with "appium.service.", all lower case, spaces
     * removed). If no appium sub module is detected then "appium.service" is
     * used as logger name.</li>
     * </ul>
     * Example log-message: "[ADB] Cannot read version codes of " is logged by
     * {@code appium.service.adb} at level {@code INFO} <br>
     * Example log-message: "[debug] [XCUITest] Xcode version set to 'x.y.z' "
     * is logged by {@code appium.service.xcuitest} at level {@code DEBUG}
     * <br>
     *
     * @param slf4jLogMessageConsumer BiConsumer block to be executed when a log message is
     *                                available.
     */
    public void addSlf4jLogMessageConsumer(BiConsumer<String, Slf4jLogMessageContext> slf4jLogMessageConsumer) {
        checkNotNull(slf4jLogMessageConsumer, "slf4jLogMessageConsumer parameter is NULL!");
        addLogMessageConsumer(logMessage -> {
            slf4jLogMessageConsumer.accept(logMessage, parseSlf4jContextFromLogMessage(logMessage));
        });
    }

    @VisibleForTesting
    static Slf4jLogMessageContext parseSlf4jContextFromLogMessage(String logMessage) {
        Matcher m = LOGGER_CONTEXT_PATTERN.matcher(logMessage);
        String loggerName = APPIUM_SERVICE_SLF4J_LOGGER_PREFIX;
        Level level = INFO;
        if (m.find()) {
            loggerName += "." + m.group(2).toLowerCase().replaceAll("\\s+", "");
            if (m.group(1) != null) {
                level = DEBUG;
            }
        }
        return new Slf4jLogMessageContext(loggerName, level);
    }

    /**
     * When a complete log message is available (from server output data), the
     * specified {@code Consumer} is invoked with that log message.
     *
     * <p>NOTE: You might want to call method {@link #clearOutPutStreams()} before
     * calling this method.
     *
     * <p>If the Consumer fails and throws an exception the exception is logged (at
     * WARN level) and execution continues.
     * <br>
     *
     * @param consumer Consumer block to be executed when a log message is available.
     */
    public void addLogMessageConsumer(Consumer<String> consumer) {
        checkNotNull(consumer, "consumer parameter is NULL!");
        addOutPutStream(new OutputStream() {
            private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            @Override
            public void write(int chr) {
                try {
                    outputStream.write(chr);
                    if (chr == '\n') {
                        consumer.accept(outputStream.toString());
                        outputStream.reset();
                    }
                } catch (Exception e) {
                    // log error and continue
                    LOG.warn("Log message consumer crashed!", e);
                }
            }
        });
    }
}
