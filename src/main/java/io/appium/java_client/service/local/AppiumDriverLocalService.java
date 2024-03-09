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
import lombok.SneakyThrows;
import org.openqa.selenium.os.ExternalProcess;
import org.openqa.selenium.remote.service.DriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Strings.isNullOrEmpty;
import static io.appium.java_client.service.local.AppiumServiceBuilder.BROADCAST_IP4_ADDRESS;
import static io.appium.java_client.service.local.AppiumServiceBuilder.BROADCAST_IP6_ADDRESS;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
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
    private final AppiumServerAvailabilityChecker availabilityChecker = new AppiumServerAvailabilityChecker();
    private final URL url;
    private String basePath;

    private ExternalProcess process = null;

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
            if (process == null || !process.isAlive()) {
                return false;
            }

            try {
                return ping(IS_RUNNING_PING_TIMEOUT);
            } catch (AppiumServerAvailabilityChecker.ConnectionTimeout
                     | AppiumServerAvailabilityChecker.ConnectionError e) {
                return false;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } finally {
            lock.unlock();
        }
    }

    private boolean ping(Duration timeout) throws InterruptedException {
        var baseURL = fixBroadcastAddresses(getUrl());
        var statusUrl = addSuffix(baseURL, "/status");
        return availabilityChecker.waitUntilAvailable(statusUrl, timeout);
    }

    private URL fixBroadcastAddresses(URL url) {
        var host = url.getHost();
        // The operating system will block direct access to the universal broadcast IP address
        if (host.equals(BROADCAST_IP4_ADDRESS)) {
            return replaceHost(url, BROADCAST_IP4_ADDRESS, "127.0.0.1");
        }
        if (host.equals(BROADCAST_IP6_ADDRESS)) {
            return replaceHost(url, BROADCAST_IP6_ADDRESS, "::1");
        }
        return url;
    }

    /**
     * Starts the defined appium server.
     *
     * @throws AppiumServerHasNotBeenStartedLocallyException If an error occurs on Appium server startup.
     * @see #stop()
     */
    @Override
    public void start() throws AppiumServerHasNotBeenStartedLocallyException {
        lock.lock();
        try {
            if (isRunning()) {
                return;
            }

            try {
                var processBuilder = ExternalProcess.builder()
                        .command(this.nodeJSExec.getCanonicalPath(), nodeJSArgs)
                        .copyOutputTo(stream);
                nodeJSEnvironment.forEach(processBuilder::environment);
                process = processBuilder.start();
            } catch (IOException e) {
                throw new AppiumServerHasNotBeenStartedLocallyException(e);
            }

            var didPingSucceed = false;
            try {
                ping(startupTimeout);
                didPingSucceed = true;
            } catch (AppiumServerAvailabilityChecker.ConnectionTimeout
                     | AppiumServerAvailabilityChecker.ConnectionError e) {
                var errorLines = new ArrayList<>(generateDetailedErrorMessagePrefix(e));
                errorLines.addAll(retrieveServerDebugInfo());
                throw new AppiumServerHasNotBeenStartedLocallyException(
                        String.join("\n", errorLines), e
                );
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                if (!didPingSucceed) {
                    destroyProcess();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    private List<String> generateDetailedErrorMessagePrefix(RuntimeException e) {
        var errorLines = new ArrayList<String>();
        if (e instanceof AppiumServerAvailabilityChecker.ConnectionTimeout) {
            errorLines.add(String.format(
                    "Appium HTTP server is not listening at %s after %s ms timeout. "
                            + "Consider increasing the server startup timeout value and "
                            + "check the server log for possible error messages.",
                    getUrl(),
                    ((AppiumServerAvailabilityChecker.ConnectionTimeout) e).getTimeout().toMillis()
            ));
        } else if (e instanceof AppiumServerAvailabilityChecker.ConnectionError) {
            var statusCode = ((AppiumServerAvailabilityChecker.ConnectionError) e).getResponseCode();
            var statusUrl = ((AppiumServerAvailabilityChecker.ConnectionError) e).getStatusUrl();
            var payload = ((AppiumServerAvailabilityChecker.ConnectionError) e).getPayload();
            errorLines.add(String.format(
                    "Appium HTTP server has started and is listening although we were "
                            + "unable to get an OK response from %s. Make sure both the client "
                            + "and the server use the same base path '%s' and check the server log for possible "
                            + "error messages.", statusUrl, Optional.ofNullable(basePath).orElse("/")
            ));
            errorLines.add(String.format("Response status code: %s", statusCode));
            payload.ifPresent(p -> errorLines.add(String.format("Response payload: %s", p)));
        }
        return errorLines;
    }

    private List<String> retrieveServerDebugInfo() {
        var result = new ArrayList<String>();
        result.add(String.format("Node.js executable path: %s", nodeJSExec.getAbsolutePath()));
        result.add(String.format("Arguments: %s", nodeJSArgs));
        ofNullable(process)
                .map(ExternalProcess::getOutput)
                .filter(o -> !isNullOrEmpty(o))
                .ifPresent(o -> result.add(String.format("Server process output: %s", o)));
        return result;
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
     * Destroys the service.
     * This method waits up to `DESTROY_TIMEOUT` seconds for the Appium service
     * to exit gracefully.
     */
    private void destroyProcess() {
        if (process == null || !process.isAlive()) {
            return;
        }
        process.shutdown(DESTROY_TIMEOUT);
    }

    /**
     * Logs as string.
     *
     * @return String logs if the server has been run. Null is returned otherwise.
     */
    @Nullable
    public String getStdOut() {
        return ofNullable(process).map(ExternalProcess::getOutput).orElse(null);
    }

    /**
     * Adds other output stream which should accept server output data.
     *
     * @param outputStream is an instance of {@link OutputStream}
     *                     that is ready to accept server output
     */
    public void addOutPutStream(OutputStream outputStream) {
        requireNonNull(outputStream, "outputStream parameter is NULL!");
        stream.add(outputStream);
    }

    /**
     * Adds other output streams which should accept server output data.
     *
     * @param outputStreams is a list of additional {@link OutputStream}
     *                      that are ready to accept server output
     */
    public void addOutPutStreams(List<OutputStream> outputStreams) {
        requireNonNull(outputStreams, "outputStreams parameter is NULL!");
        for (OutputStream outputStream : outputStreams) {
            addOutPutStream(outputStream);
        }
    }

    /**
     * Remove the outputStream which is receiving server output data.
     *
     * @return the outputStream has been removed if it is present
     */
    public Optional<OutputStream> removeOutPutStream(OutputStream outputStream) {
        requireNonNull(outputStream, "outputStream parameter is NULL!");
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
        requireNonNull(slf4jLogMessageConsumer, "slf4jLogMessageConsumer parameter is NULL!");
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
        requireNonNull(consumer, "consumer parameter is NULL!");
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
