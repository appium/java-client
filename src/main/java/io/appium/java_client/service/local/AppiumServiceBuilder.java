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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.appium.java_client.android.options.context.SupportsChromedriverExecutableOption;
import io.appium.java_client.android.options.signing.SupportsKeystoreOptions;
import io.appium.java_client.internal.process.ExecutableFinder;
import io.appium.java_client.remote.options.SupportsAppOption;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.appium.java_client.service.local.flags.ServerArgument;
import lombok.SneakyThrows;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static io.appium.java_client.internal.Preconditions.checkArgument;
import static io.appium.java_client.internal.Strings.isNullOrEmpty;
import static java.util.Locale.ROOT;
import static java.util.Objects.requireNonNull;

/**
 * Builds {@link AppiumDriverLocalService} instances.
 */
public final class AppiumServiceBuilder {

    /**
     * The environmental variable used to define
     * the path to executable appium.js (1.4.x and lower) or
     * main.js (1.5.x and higher).
     */
    public static final String APPIUM_PATH = "APPIUM_BINARY_PATH";

    /**
     * System property and environment variable name for the Node.js executable path
     * (node.exe on Windows, node on Linux/macOS).
     */
    public static final String NODE_PATH = "NODE_BINARY_PATH";

    /** The IPv4 address to listen on all network interfaces. */
    public static final String BROADCAST_IP4_ADDRESS = "0.0.0.0";
    /** The IPv6 address to listen on all network interfaces. */
    public static final String BROADCAST_IP6_ADDRESS = "::";
    private static final Path APPIUM_PATH_SUFFIX = Paths.get("appium", "build", "lib", "main.js");
    /** The default port of the Appium server. */
    public static final int DEFAULT_APPIUM_PORT = 4723;
    private static final Duration DEFAULT_STARTUP_TIMEOUT = Duration.ofSeconds(20);
    private final Map<String, String> serverArguments = new HashMap<>();
    private int port = 0;
    private File exe;
    private Map<String, String> environment = Collections.emptyMap();
    private File logFile;
    private Duration timeout = DEFAULT_STARTUP_TIMEOUT;
    private File appiumJS;
    private File node;
    private String ipAddress = BROADCAST_IP4_ADDRESS;
    private Capabilities capabilities;
    private boolean autoQuoteCapabilitiesOnWindows = false;
    private static final Function<File, String> APPIUM_JS_NOT_EXIST_ERROR = fullPath -> String.format(
            "The main Appium script does not exist at '%s'", fullPath.getAbsolutePath());
    private static final Function<File, String> NODE_JS_NOT_EXIST_ERROR = fullPath ->
            String.format("The main NodeJS executable does not exist at '%s'", fullPath.getAbsolutePath());

    private static final List<String> PATH_CAPABILITIES = List.of(
            SupportsChromedriverExecutableOption.CHROMEDRIVER_EXECUTABLE_OPTION,
            SupportsKeystoreOptions.KEYSTORE_PATH_OPTION,
            SupportsAppOption.APP_OPTION
    );

    /**
     * Creates a builder using the default port and the current process environment.
     */
    public AppiumServiceBuilder() {
        usingPort(DEFAULT_APPIUM_PORT);
        withEnvironment(System.getenv());
    }

    private static File validatePath(@Nullable String fullPath, String errMsg) {
        if (fullPath == null) {
            throw new InvalidServerInstanceException(errMsg);
        }
        File result = new File(fullPath);
        if (!result.exists()) {
            throw new InvalidServerInstanceException(errMsg);
        }
        return result;
    }

    private static File findBinary(String name, String errMsg) {
        return validatePath(new ExecutableFinder().find(name), errMsg);
    }

    private static File findNpm() {
        return findBinary("npm",
                "Node Package Manager (npm) is either not installed or its executable is not present in PATH");
    }

    private static File findMainScript() {
        File npm = findNpm();
        List<String> cmdLine = System.getProperty("os.name").toLowerCase(ROOT).contains("win")
                // npm is a batch script, so on windows we need to use cmd.exe in order to execute it
                ? Arrays.asList("cmd.exe", "/c", String.format("\"%s\" root -g", npm.getAbsolutePath()))
                : Arrays.asList(npm.getAbsolutePath(), "root", "-g");
        ProcessBuilder pb = new ProcessBuilder(cmdLine);
        String nodeModulesRoot;
        try {
            nodeModulesRoot = new String(pb.start().getInputStream().readAllBytes(), StandardCharsets.UTF_8).trim();
        } catch (IOException e) {
            throw new InvalidServerInstanceException(
                    "Cannot retrieve the path to the folder where NodeJS modules are located", e);
        }
        File mainAppiumJs = Paths.get(nodeModulesRoot, APPIUM_PATH_SUFFIX.toString()).toFile();
        if (!mainAppiumJs.exists()) {
            throw new InvalidServerInstanceException(APPIUM_JS_NOT_EXIST_ERROR.apply(mainAppiumJs));
        }
        return mainAppiumJs;
    }

    /**
     * Locates the Node.js executable: the configured one, then {@link #NODE_PATH}, then the system PATH.
     *
     * @return the Node.js executable
     * @throws InvalidServerInstanceException if the executable cannot be found
     */
    protected File findDefaultExecutable() {
        if (this.node != null) {
            validatePath(this.node.getAbsolutePath(), NODE_JS_NOT_EXIST_ERROR.apply(this.node));
            return this.node;
        }

        File node = loadPathFromEnv(NODE_PATH);
        if (node != null) {
            validatePath(node.getAbsolutePath(), NODE_JS_NOT_EXIST_ERROR.apply(node));
            this.node = node;
            return this.node;
        }

        this.node = findBinary("node",
                "NodeJS is either not installed or its executable not present in PATH");
        return this.node;
    }

    /**
     * Boolean arguments have a special moment:
     * the presence of an arguments means "true". This method
     * was designed for these cases.
     *
     * @param argument is an instance which contains the argument name.
     * @return the self-reference.
     */
    public AppiumServiceBuilder withArgument(ServerArgument argument) {
        serverArguments.put(argument.getArgument(), "");
        return this;
    }

    /**
     * Adds a server argument.
     *
     * @param argument is an instance which contains the argument name.
     * @param value    A non null string value. (Warn!!!) Boolean arguments have a special moment:
     *                 the presence of an arguments means "true". At this case an empty string
     *                 should be defined.
     * @return the self-reference.
     */
    public AppiumServiceBuilder withArgument(ServerArgument argument, String value) {
        String argName = argument.getArgument();
        switch (argName) {
            case "--port":
            case "-p":
                usingPort(Integer.parseInt(value));
                break;
            case "--address":
            case "-a":
                withIPAddress(value);
                break;
            case "--log":
            case "-g":
                withLogFile(new File(value));
                break;
            case "--base-path":
                serverArguments.put(argName, sanitizeBasePath(value));
                break;
            default:
                serverArguments.put(argName, value);
                break;
        }
        return this;
    }

    private static String sanitizeBasePath(String basePath) {
        basePath = requireNonNull(basePath).trim();
        checkArgument(
                !basePath.isEmpty(),
                "Given base path is not valid - blank or empty values are not allowed for base path"
        );
        return basePath.endsWith("/") ? basePath : basePath + "/";
    }

    /**
     * Adds capabilities.
     *
     * @param capabilities is an instance of {@link Capabilities}.
     * @return the self-reference.
     */
    public AppiumServiceBuilder withCapabilities(Capabilities capabilities) {
        this.capabilities = (this.capabilities == null ? capabilities : this.capabilities)
                .merge(capabilities);
        return this;
    }

    /**
     * Adds capabilities.
     *
     * @param capabilities                   is an instance of {@link Capabilities}.
     * @param autoQuoteCapabilitiesOnWindows automatically escape quote all
     *                                       capabilities when calling appium.
     *                                       This is required on windows systems only.
     * @return the self-reference.
     */
    public AppiumServiceBuilder withCapabilities(Capabilities capabilities,
                                                 boolean autoQuoteCapabilitiesOnWindows) {
        this.autoQuoteCapabilitiesOnWindows = autoQuoteCapabilitiesOnWindows;
        return withCapabilities(capabilities);
    }

    /**
     * Sets an executable appium.js.
     *
     * @param appiumJS an executable appium.js (1.4.x and lower) or
     *                 main.js (1.5.x and higher).
     * @return the self-reference.
     */
    public AppiumServiceBuilder withAppiumJS(File appiumJS) {
        this.appiumJS = appiumJS;
        return this;
    }

    /**
     * Sets the IP address the server listens on.
     *
     * @param ipAddress the IP address
     * @return the self-reference.
     */
    public AppiumServiceBuilder withIPAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    @Nullable
    private static File loadPathFromEnv(String envVarName) {
        String fullPath = System.getProperty(envVarName);
        if (isNullOrEmpty(fullPath)) {
            fullPath = System.getenv(envVarName);
        }
        return isNullOrEmpty(fullPath) ? null : new File(fullPath);
    }

    private void loadPathToMainScript() {
        if (this.appiumJS != null) {
            validatePath(this.appiumJS.getAbsolutePath(), APPIUM_JS_NOT_EXIST_ERROR.apply(this.appiumJS));
            return;
        }

        File mainScript = loadPathFromEnv(APPIUM_PATH);
        if (mainScript != null) {
            validatePath(mainScript.getAbsolutePath(), APPIUM_JS_NOT_EXIST_ERROR.apply(mainScript));
            this.appiumJS = mainScript;
            return;
        }

        this.appiumJS = findMainScript();
    }

    private String capabilitiesToQuotedCmdlineArg() {
        if (capabilities == null) {
            return "{}";
        }
        StringBuilder result = new StringBuilder();
        Map<String, ?> capabilitiesMap = capabilities.asMap();
        Set<? extends Map.Entry<String, ?>> entries = capabilitiesMap.entrySet();

        for (Map.Entry<String, ?> entry : entries) {
            Object value = entry.getValue();

            if (value == null) {
                continue;
            }

            if (value instanceof String) {
                String valueString = (String) value;
                if (PATH_CAPABILITIES.contains(entry.getKey())) {
                    value = "\\\"" + valueString.replace("\\", "/") + "\\\"";
                } else {
                    value = "\\\"" + valueString + "\\\"";
                }
            } else {
                value = String.valueOf(value);
            }

            String key = "\\\"" + entry.getKey() + "\\\"";
            if (result.length() > 0) {
                result.append(", ");
            }
            result.append(key).append(": ").append(value);
        }

        return "{" + result + "}";
    }

    private String capabilitiesToCmdlineArg() {
        if (autoQuoteCapabilitiesOnWindows && Platform.getCurrent().is(Platform.WINDOWS)) {
            return capabilitiesToQuotedCmdlineArg();
        }
        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .serializeNulls()
                .create();
        // Selenium internally uses org.apache.commons.exec.CommandLine
        // which has the following known bug in its arguments parser:
        // https://issues.apache.org/jira/browse/EXEC-54
        return gson.toJson(capabilities.asMap());
    }

    List<String> createArgs() {
        List<String> argList = new ArrayList<>();
        loadPathToMainScript();
        argList.add(appiumJS.getAbsolutePath());
        argList.add("--port");
        argList.add(String.valueOf(port));

        if (isNullOrEmpty(ipAddress)) {
            ipAddress = BROADCAST_IP4_ADDRESS;
        }
        argList.add("--address");
        argList.add(ipAddress);

        if (logFile != null) {
            argList.add("--log");
            argList.add(logFile.getAbsolutePath());
        }

        Set<Map.Entry<String, String>> entries = serverArguments.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String argument = entry.getKey();
            String value = entry.getValue();
            if (isNullOrEmpty(argument) || value == null) {
                continue;
            }

            argList.add(argument);
            if (!isNullOrEmpty(value)) {
                argList.add(value);
            }
        }

        if (capabilities != null) {
            argList.add("--default-capabilities");
            argList.add(capabilitiesToCmdlineArg());
        }

        return Collections.unmodifiableList(argList);
    }


    /**
     * Sets which Node.js the builder will use.
     *
     * @param nodeJSExecutable The executable Node.js to use.
     * @return A self reference.
     */
    public AppiumServiceBuilder usingDriverExecutable(File nodeJSExecutable) {
        this.exe = requireNonNull(nodeJSExecutable, "Node.js executable file is NULL!");
        return this;
    }

    /**
     * Sets which port the appium server should be started on. A value of 0 indicates that any
     * free port may be used.
     *
     * @param port The port to use; must be non-negative.
     * @return A self reference.
     */
    public AppiumServiceBuilder usingPort(int port) {
        checkArgument(port >= 0, "Port number must be greater than or equal to 0");
        this.port = port;
        return this;
    }

    /**
     * Configures the appium server to start on any available port.
     *
     * @return A self reference.
     */
    public AppiumServiceBuilder usingAnyFreePort() {
        this.port = 0;
        return this;
    }

    /**
     * Defines the environment for the launched appium server.
     *
     * @param environment A map of the environment variables to launch the
     *                    appium server with.
     * @return A self reference.
     */
    public AppiumServiceBuilder withEnvironment(Map<String, String> environment) {
        this.environment = Map.copyOf(environment);
        return this;
    }

    /**
     * Configures the appium server to write log to the given file.
     *
     * @param logFile A file to write log to.
     * @return A self reference.
     */
    public AppiumServiceBuilder withLogFile(File logFile) {
        this.logFile = logFile;
        return this;
    }

    /**
     * Configures the timeout waiting for the appium server to start.
     *
     * @param timeout The startup timeout; the default one is used if null.
     * @return A self reference.
     */
    public AppiumServiceBuilder withTimeout(Duration timeout) {
        this.timeout = timeout;
        return this;
    }

    /**
     * Creates a new service to manage the appium server. If the port is not set
     * explicitly, a free one is picked.
     *
     * @return The new service object.
     */
    public AppiumDriverLocalService build() {
        if (port == 0) {
            port = findFreePort();
        }
        if (timeout == null) {
            timeout = DEFAULT_STARTUP_TIMEOUT;
        }
        if (exe == null) {
            usingDriverExecutable(findDefaultExecutable());
        }
        List<String> args = createArgs();

        AppiumDriverLocalService service = createDriverService(exe, port, timeout, args, environment);
        // Reset the port to allow reusing this builder
        port = 0;
        return service;
    }

    private static int findFreePort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot find a free port", e);
        }
    }

    @SneakyThrows
    private AppiumDriverLocalService createDriverService(File nodeJSExecutable, int nodeJSPort,
                                                           Duration startupTimeout,
                                                           List<String> nodeArguments,
                                                           Map<String, String> nodeEnvironment) {
        String basePath = serverArguments.getOrDefault(
                GeneralServerFlag.BASEPATH.getArgument(), serverArguments.get("-pa"));
        return new AppiumDriverLocalService(ipAddress, nodeJSExecutable, nodeJSPort, startupTimeout, nodeArguments,
                nodeEnvironment).withBasePath(basePath);
    }
}
