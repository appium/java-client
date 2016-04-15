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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.flags.ServerArgument;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.openqa.selenium.Platform;
import org.openqa.selenium.os.CommandLine;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.service.DriverService;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public final class AppiumServiceBuilder
    extends DriverService.Builder<AppiumDriverLocalService, AppiumServiceBuilder> {

    /**
     * The environmental variable used to define
     * the path to executable appium.js (1.4.x and lower) or
     * main.js (1.5.x and higher).
     */
    public static final String APPIUM_PATH = "APPIUM_BINARY_PATH";

    /**
     * The environmental variable used to define
     * the path to executable NodeJS file (node.exe for WIN and
     * node for Linux/MacOS X).
     */
    public static final String NODE_PATH = "NODE_BINARY_PATH";
    public static final String DEFAULT_LOCAL_IP_ADDRESS = "0.0.0.0";
    private static final List<String> PATH_CAPABILITIES = new ArrayList<String>() {
        {
            add(AndroidMobileCapabilityType.KEYSTORE_PATH);
            add(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE);
            add(MobileCapabilityType.APP);
        }
    };
    private static final String APPIUM_FOLDER = "appium";
    private static final String BUILD_FOLDER = "build";
    private static final String LIB_FOLDER = "lib";
    private static final String MAIN_JS = "main.js";
    private static final String ERROR_NODE_NOT_FOUND = "There is no installed nodes! Please "
            + "install node via NPM (https://www.npmjs.com/package/appium#using-node-js) or download and "
            + "install Appium app (http://appium.io/downloads.html)";
    private static final String APPIUM_NODE_MASK =
        File.separator + BUILD_FOLDER
                + File.separator + LIB_FOLDER
                + File.separator + MAIN_JS;
    private static final int DEFAULT_APPIUM_PORT = 4723;
    private static final String BASH = "bash";
    private static final String CMD_EXE = "cmd.exe";
    private static final String NODE = "node";
    final Map<String, String> serverArguments = new HashMap<>();
    private File appiumJS;
    private String ipAddress = DEFAULT_LOCAL_IP_ADDRESS;
    private File npmScript;
    private File getNodeJSExecutable;
    private DesiredCapabilities capabilities;

    //The first starting is slow sometimes on some
    //environment
    private long startupTimeout = 120;
    private TimeUnit timeUnit = TimeUnit.SECONDS;

    public AppiumServiceBuilder() {
        usingPort(DEFAULT_APPIUM_PORT);
    }

    private static void validateNodeStructure(File node) {
        String absoluteNodePath = node.getAbsolutePath();

        if (!node.exists()) {
            throw new InvalidServerInstanceException(
                "The invalid appium node " + absoluteNodePath + " has been defined",
                new IOException("The node " + absoluteNodePath + "doesn't exist"));
        }
    }

    private static void disposeCachedFile(File file) throws Throwable {
        if (file != null) {
            FileUtils.forceDelete(file);
        }
    }

    private void setUpNPMScript() {
        if (npmScript != null) {
            return;
        }

        if (!Platform.getCurrent().is(Platform.WINDOWS)) {
            npmScript = Scripts.GET_PATH_TO_DEFAULT_NODE_UNIX.getScriptFile();
        }
    }

    private void setUpGetNodeJSExecutableScript() {
        if (getNodeJSExecutable != null) {
            return;
        }

        getNodeJSExecutable = Scripts.GET_NODE_JS_EXECUTABLE.getScriptFile();
    }

    private File findNodeInCurrentFileSystem() {
        setUpNPMScript();

        String instancePath;
        CommandLine commandLine;
        try {
            if (Platform.getCurrent().is(Platform.WINDOWS)) {
                commandLine = new CommandLine(CMD_EXE, "/C", "npm root -g");
            } else {
                commandLine = new CommandLine(BASH, "-l", npmScript.getAbsolutePath());
            }
            commandLine.execute();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        instancePath = (commandLine.getStdOut()).trim();
        try {
            File defaultAppiumNode;
            if (StringUtils.isBlank(instancePath) || !(defaultAppiumNode =
                new File(instancePath + File.separator
                    + APPIUM_FOLDER)).exists()) {
                String errorOutput = commandLine.getStdOut();
                throw new InvalidServerInstanceException(ERROR_NODE_NOT_FOUND,
                    new IOException(errorOutput));
            }
            //appium servers v1.5.x and higher
            File result;
            if ((result = new File(defaultAppiumNode, APPIUM_NODE_MASK)).exists()) {
                return result;
            }

            throw new InvalidServerInstanceException(ERROR_NODE_NOT_FOUND, new IOException(
                "Could not find a file " + APPIUM_NODE_MASK
                    + " in the "
                    + defaultAppiumNode + " directory"));
        } finally {
            commandLine.destroy();
        }
    }

    @Override protected File findDefaultExecutable() {

        String nodeJSExec = System.getProperty(NODE_PATH);
        if (StringUtils.isBlank(nodeJSExec)) {
            nodeJSExec = System.getenv(NODE_PATH);
        }
        if (!StringUtils.isBlank(nodeJSExec)) {
            File result = new File(nodeJSExec);
            if (result.exists()) {
                return result;
            }
        }

        CommandLine commandLine;
        setUpGetNodeJSExecutableScript();
        try {
            if (Platform.getCurrent().is(Platform.WINDOWS)) {
                commandLine = new CommandLine(NODE + ".exe", getNodeJSExecutable.getAbsolutePath());
            } else {
                commandLine = new CommandLine(NODE, getNodeJSExecutable.getAbsolutePath());
            }
            commandLine.execute();
        } catch (Throwable t) {
            throw new InvalidNodeJSInstance("Node.js is not installed!", t);
        }


        String filePath = (commandLine.getStdOut()).trim();

        try {
            if (StringUtils.isBlank(filePath) || !new File(filePath).exists()) {
                String errorOutput = commandLine.getStdOut();
                String errorMessage = "Can't get a path to the default Node.js instance";
                throw new InvalidNodeJSInstance(errorMessage, new IOException(errorOutput));
            }
            return new File(filePath);
        } finally {
            commandLine.destroy();
        }
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
     * @param argument is an instance which contains the argument name.
     * @param value    A non null string value. (Warn!!!) Boolean arguments have a special moment:
     *                 the presence of an arguments means "true". At this case an empty string
     *                 should be defined.
     * @return the self-reference.
     */
    public AppiumServiceBuilder withArgument(ServerArgument argument, String value) {
        serverArguments.put(argument.getArgument(), value);
        return this;
    }

    /**
     * @param capabilities is an instance of
     * {@link org.openqa.selenium.remote.DesiredCapabilities}.
     * @return the self-reference.
     */
    public AppiumServiceBuilder withCapabilities(DesiredCapabilities capabilities) {
        if (this.capabilities == null) {
            this.capabilities = capabilities;
        } else {
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.merge(this.capabilities).merge(capabilities);
            this.capabilities = desiredCapabilities;
        }
        return this;
    }

    /**
     * @param appiumJS an executable appium.js (1.4.x and lower) or
     *                 main.js (1.5.x and higher).
     * @return the self-reference.
     */
    public AppiumServiceBuilder withAppiumJS(File appiumJS) {
        this.appiumJS = appiumJS;
        return this;
    }

    public AppiumServiceBuilder withIPAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    /**
     * @param time     a time value for the service starting up.
     * @param timeUnit a time unit for the service starting up.
     * @return self-reference.
     */
    public AppiumServiceBuilder withStartUpTimeOut(long time, TimeUnit timeUnit) {
        checkNotNull(timeUnit);
        checkArgument(time > 0, "Time value should be greater than zero", time);
        this.startupTimeout = time;
        this.timeUnit = timeUnit;
        return this;
    }

    void checkAppiumJS() {
        if (appiumJS != null) {
            validateNodeStructure(appiumJS);
            return;
        }

        String appiumJS = System.getProperty(APPIUM_PATH);
        if (StringUtils.isBlank(appiumJS)) {
            appiumJS = System.getenv(APPIUM_PATH);
        }
        if (!StringUtils.isBlank(appiumJS)) {
            File node = new File(appiumJS);
            validateNodeStructure(node);
            this.appiumJS = node;
            return;
        }

        this.appiumJS = findNodeInCurrentFileSystem();
    }

    private String parseCapabilitiesIfWindows() {
        String result = StringUtils.EMPTY;

        if (capabilities != null) {
            Map<String, Object> capabilitiesMap = (Map<String, Object>) capabilities.asMap();
            Set<Map.Entry<String, Object>> entries = capabilitiesMap.entrySet();

            for (Map.Entry<String, Object> entry : entries) {
                Object value = entry.getValue();

                if (value == null) {
                    continue;
                }

                if (String.class.isAssignableFrom(value.getClass())) {
                    if (PATH_CAPABILITIES.contains(entry.getKey())) {
                        value = "\\\"" + String.valueOf(value).replace("\\", "/") + "\\\"";
                    } else {
                        value = "\\\"" + String.valueOf(value) + "\\\"";
                    }
                } else {
                    value = String.valueOf(value);
                }

                String key = "\\\"" + String.valueOf(entry.getKey()) + "\\\"";
                if (StringUtils.isBlank(result)) {
                    result = key + ": " + value;
                } else {
                    result = result + ", " + key + ": " + value;
                }
            }
        }

        return "{" + result + "}";
    }

    private String parseCapabilitiesIfUNIX() {
        String result = StringUtils.EMPTY;

        if (capabilities != null) {
            Map<String, Object> capabilitiesMap = (Map<String, Object>) capabilities.asMap();
            Set<Map.Entry<String, Object>> entries = capabilitiesMap.entrySet();

            for (Map.Entry<String, Object> entry : entries) {
                Object value = entry.getValue();

                if (value == null) {
                    continue;
                }

                if (String.class.isAssignableFrom(value.getClass())) {
                    value = "\"" + String.valueOf(value) + "\"";
                } else {
                    value = String.valueOf(value);
                }

                String key = "\"" + String.valueOf(entry.getKey()) + "\"";
                if (StringUtils.isBlank(result)) {
                    result = key + ": " + value;
                } else {
                    result = result + ", " + key + ": " + value;
                }
            }
        }

        return "{" + result + "}";
    }

    @SuppressWarnings("unchecked")
    private String parseCapabilities() {
        if (Platform.getCurrent().is(Platform.WINDOWS)) {
            return parseCapabilitiesIfWindows();
        }
        return parseCapabilitiesIfUNIX();
    }

    @Override protected ImmutableList<String> createArgs() {
        List<String> argList = new ArrayList<>();
        checkAppiumJS();
        argList.add(appiumJS.getAbsolutePath());
        argList.add("--port");
        argList.add(String.valueOf(getPort()));

        if (StringUtils.isBlank(ipAddress)) {
            ipAddress = DEFAULT_LOCAL_IP_ADDRESS;
        } else {
            InetAddressValidator validator = InetAddressValidator.getInstance();
            if (!validator.isValid(ipAddress) && !validator.isValidInet4Address(ipAddress)
                && !validator.isValidInet6Address(ipAddress)) {
                throw new IllegalArgumentException(
                        "The invalid IP address " + ipAddress + " is defined");
            }
        }
        argList.add("--address");
        argList.add(ipAddress);

        File log = getLogFile();
        if (log != null) {
            argList.add("--log");
            argList.add(log.getAbsolutePath());
        }

        Set<Map.Entry<String, String>> entries = serverArguments.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String argument = entry.getKey();
            String value = entry.getValue();
            if (StringUtils.isBlank(argument) || value == null) {
                continue;
            }

            argList.add(argument);
            if (!StringUtils.isBlank(value)) {
                argList.add(value);
            }
        }

        if (capabilities != null) {
            argList.add("--default-capabilities");
            argList.add(parseCapabilities());
        }

        return new ImmutableList.Builder<String>().addAll(argList).build();
    }

    /**
     * Sets which Node.js the builder will use.
     *
     * @param nodeJSExecutable The executable Node.js to use.
     * @return A self reference.
     */
    public AppiumServiceBuilder usingDriverExecutable(File nodeJSExecutable) {
        return super.usingDriverExecutable(nodeJSExecutable);
    }

    /**
     * Sets which port the appium server should be started on. A value of 0 indicates that any
     * free port may be used.
     *
     * @param port The port to use; must be non-negative.
     * @return A self reference.
     */
    public AppiumServiceBuilder usingPort(int port) {
        return super.usingPort(port);
    }

    /**
     * Configures the appium server to start on any available port.
     *
     * @return A self reference.
     */
    public AppiumServiceBuilder usingAnyFreePort() {
        return super.usingAnyFreePort();
    }

    /**
     * Defines the environment for the launched appium server.
     *
     * @param environment A map of the environment variables to launch the
     *                    appium server with.
     * @return A self reference.
     */
    @Override public AppiumServiceBuilder withEnvironment(Map<String, String> environment) {
        return super.withEnvironment(environment);
    }

    /**
     * Configures the appium server to write log to the given file.
     *
     * @param logFile A file to write log to.
     * @return A self reference.
     */
    public AppiumServiceBuilder withLogFile(File logFile) {
        return super.withLogFile(logFile);
    }

    @Override
    protected AppiumDriverLocalService createDriverService(File nodeJSExecutable, int nodeJSPort,
        ImmutableList<String> nodeArguments, ImmutableMap<String, String> nodeEnvironment) {
        try {
            return new AppiumDriverLocalService(ipAddress, nodeJSExecutable, nodeJSPort,
                nodeArguments, nodeEnvironment, startupTimeout, timeUnit);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override protected void finalize() throws Throwable {
        try {
            disposeCachedFile(npmScript);
            disposeCachedFile(getNodeJSExecutable);
        } finally {
            super.finalize();
        }
    }
}
