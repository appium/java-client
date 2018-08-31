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

import static com.google.common.base.Preconditions.checkNotNull;
import static org.slf4j.event.Level.DEBUG;
import static org.slf4j.event.Level.INFO;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import org.apache.commons.lang3.StringUtils;

import org.openqa.selenium.net.UrlChecker;
import org.openqa.selenium.os.CommandLine;
import org.openqa.selenium.remote.service.DriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

public final class AppiumDriverLocalService extends DriverService {

    private static final String URL_MASK = "http://%s:%d/wd/hub";
    private static final Logger LOG = LoggerFactory.getLogger(AppiumDriverLocalService.class);
    private static final Pattern LOG_MESSAGE_PATTERN = Pattern.compile("^(.*)\\R");
    private static final Pattern LOGGER_CONTEXT_PATTERN = Pattern.compile("^(\\[debug\\] )?\\[(.+?)\\]");
    private final File nodeJSExec;
    private final ImmutableList<String> nodeJSArgs;
    private final ImmutableMap<String, String> nodeJSEnvironment;
    private final long startupTimeout;
    private final TimeUnit timeUnit;
    private final ReentrantLock lock = new ReentrantLock(true); //uses "fair" thread ordering policy
    private final ListOutputStream stream = new ListOutputStream().add(System.out);
    private final URL url;

    private CommandLine process = null;

    AppiumDriverLocalService(String ipAddress, File nodeJSExec, int nodeJSPort,
        ImmutableList<String> nodeJSArgs, ImmutableMap<String, String> nodeJSEnvironment,
        long startupTimeout, TimeUnit timeUnit) throws IOException {
        super(nodeJSExec, nodeJSPort, nodeJSArgs, nodeJSEnvironment);
        this.nodeJSExec = nodeJSExec;
        this.nodeJSArgs = nodeJSArgs;
        this.nodeJSEnvironment = nodeJSEnvironment;
        this.startupTimeout = startupTimeout;
        this.timeUnit = timeUnit;
        this.url = new URL(String.format(URL_MASK, ipAddress, nodeJSPort));
    }

    public static AppiumDriverLocalService buildDefaultService() {
        return buildService(new AppiumServiceBuilder());
    }

    public static AppiumDriverLocalService buildService(AppiumServiceBuilder builder) {
        return builder.build();
    }

    /**
     * Base URL.
     *
     * @return The base URL for the managed appium server.
     */
    @Override public URL getUrl() {
        return url;
    }

    @Override public boolean isRunning() {
        lock.lock();
        try {
            if (process == null) {
                return false;
            }

            if (!process.isRunning()) {
                return false;
            }

            try {
                ping(1500, TimeUnit.MILLISECONDS);
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

    private void ping(long time, TimeUnit timeUnit) throws UrlChecker.TimeoutException, MalformedURLException {
        URL status = new URL(url.toString() + "/status");
        new UrlChecker().waitUntilAvailable(time, timeUnit, status);
    }

    /**
     * Starts the defined appium server.
     *
     * @throws AppiumServerHasNotBeenStartedLocallyException
     * If an error occurs while spawning the child process.
     * @see #stop()
     */
    public void start() throws AppiumServerHasNotBeenStartedLocallyException {
        lock.lock();
        try {
            if (isRunning()) {
                return;
            }

            try {
                process = new CommandLine(this.nodeJSExec.getCanonicalPath(),
                    nodeJSArgs.toArray(new String[] {}));
                process.setEnvironmentVariables(nodeJSEnvironment);
                process.copyOutputTo(stream);
                process.executeAsync();
                ping(startupTimeout, timeUnit);
            } catch (Throwable e) {
                destroyProcess();
                String msgTxt = "The local appium server has not been started. "
                    + "The given Node.js executable: " + this.nodeJSExec.getAbsolutePath()
                    + " Arguments: " + nodeJSArgs.toString() + " " + "\n";
                if (process != null) {
                    String processStream = process.getStdOut();
                    if (!StringUtils.isBlank(processStream)) {
                        msgTxt = msgTxt + "Process output: " + processStream + "\n";
                    }
                }

                throw new AppiumServerHasNotBeenStartedLocallyException(msgTxt, e);
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
    @Override public void stop() {
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

    private void destroyProcess() {
        if (process.isRunning()) {
            process.destroy();
        }
    }

    /**
     * Logs as string.
     *
     * @return String logs if the server has been run.
     *     null is returned otherwise.
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
     * @param outputStream is an instance of {@link OutputStream}
     *                     that is ready to accept server output
     */
    public void addOutPutStream(OutputStream outputStream) {
        checkNotNull(outputStream, "outputStream parameter is NULL!");
        stream.add(outputStream);
    }

    /**
     * Adds other output streams which should accept server output data.
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
     * java.util.logging, logback, log4j).</br>
     * </br>
     * NOTE1: You might want to call method {@link #clearOutPutStreams()} before
     * calling this method. </br>
     * NOTE2: it is required that {@link --log-timestamp} server flag is
     * {@link false}. </br>
     * </br>
     * By default log messages are:
     * <li>logged at {@code INFO} level, unless log message is pre-fixed by
     * {@code [debug]} then logged at {@code DEBUG} level.</li>
     * <li>logged by a <a href="http://slf4j.org">SLF4J</a> logger instance with
     * a name corresponding to the appium sub module as prefixed in log message
     * (logger name is transformed to lower case, no spaces and prefixed with
     * "appium.service.").</li> </br>
     * Example log-message: "[ADB] Cannot read version codes of " is logged by
     * logger: {@code appium.service.adb} at level {@code INFO}. </br>
     * Example log-message: "[debug] [XCUITest] Xcode version set to 'x.y.z' "
     * is logged by logger {@code appium.service.xcuitest} at level
     * {@code DEBUG}.
     * 
     * </br>
     * </br>
     * 
     * @see #addSlf4jLogMessageConsumer(BiConsumer)
     */
    public void enableDefaultSlf4jLoggingOfOutputData() {
        addSlf4jLogMessageConsumer((logMessage, ctx) -> {
            if (ctx.level().equals(DEBUG)) {
                ctx.logger().debug(logMessage);
            } else {
                ctx.logger().info(logMessage);
            }
        });
    }

    /**
     * When a complete log message is available (from server output data) that
     * message is parsed for its slf4j context (logger name, logger level etc.)
     * and the specified {@code BiConsumer} is invoked with the log message and
     * slf4j context.</br>
     * </br>
     * Use this method only if you want a behavior that differentiates from the
     * default behavior as enabled by method
     * {@link #enableDefaultSlf4jLoggingOfOutputData()}. </br>
     * </br>
     * NOTE: You might want to call method {@link #clearOutPutStreams()} before
     * calling this method. </br>
     * </br>
     * implementation detail:
     * <li>if log message begins with {@code [debug]} then log level is set to
     * {@code DEBUG}, otherwise log level is {@code INFO}.</li>
     * <li>the appium sub module name is parsed from the log message and used as
     * logger name (prefixed with "appium.service.", all lower case, spaces
     * removed). If no appium sub module is detected then "appium.service" is
     * used as logger name.</li> </br>
     * Example log-message: "[ADB] Cannot read version codes of " is logged by
     * {@code appium.service.adb} at level {@code INFO} </br>
     * Example log-message: "[debug] [XCUITest] Xcode version set to 'x.y.z' "
     * is logged by {@code appium.service.xcuitest} at level {@code DEBUG}
     * 
     * @param slf4jLogMessageConsumer
     *            BiConsumer block to be executed when a log message is
     *            available.
     */
    public void addSlf4jLogMessageConsumer(
            BiConsumer<String, Slf4jLogMessageContext> slf4jLogMessageConsumer) {
        addLogMessageConsumer(logMessage -> {
            slf4jLogMessageConsumer.accept(logMessage, parseSlf4jContextFromLogMessage(logMessage));
        });
    }

    static Slf4jLogMessageContext parseSlf4jContextFromLogMessage(String logMessage) {
        Matcher m = LOGGER_CONTEXT_PATTERN.matcher(logMessage);
        String loggerName = "appium.service";
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
     * specified {@code Consumer} is invoked with that log message. </br>
     * </br>
     * NOTE: You might want to call method {@link #clearOutPutStreams()} before
     * calling this method. </br>
     * </br>
     * If the Consumer fails and throws an exception the exception is logged (at
     * WARN level) and execution continues.
     * 
     * @param consumer
     *            Consumer block to be executed when a log message is available.
     * 
     */
    public void addLogMessageConsumer(Consumer<String> consumer) {
        addOutPutStream(new OutputStream() {
            StringBuilder lineBuilder = new StringBuilder();

            @Override
            public void write(int chr) throws IOException {
                try {
                    lineBuilder.append((char) chr);
                    Matcher matcher = LOG_MESSAGE_PATTERN.matcher(lineBuilder.toString());
                    if (matcher.matches()) {
                        consumer.accept(matcher.group(1));
                        lineBuilder = new StringBuilder();
                    }
                } catch (Exception e) {
                    // log error and continue
                    LOG.warn("Log message consumer crashed!", e);
                }
            }
        });
    }
}
