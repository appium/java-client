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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.service.local.flags.ServerArgument;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.service.DriverService;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


public final class AppiumServiceBuilder extends DriverService.Builder<AppiumDriverLocalService, AppiumServiceBuilder> {

    private static final String NODE_MODULES_FOLDER = "node_modules";
    private static final String APPIUM_FOLDER = "appium";
    private static final String BIN_FOLDER = "bin";
    private static final String APPIUM_JS = "appium.js";
    private static final String APPIUM_NODE_MASK =  File.separator +
            APPIUM_FOLDER + File.separator + BIN_FOLDER + File.separator + APPIUM_JS;

    public static final String APPIUM_NODE_PROPERTY = "appium.node.path";
    public static final String DEFAULT_LOCAL_IP_ADDRESS = "0.0.0.0";

    private static final int DEFAULT_APPIUM_PORT = 4723;

    private static final String NODE_COMMAND_PREFIX = defineNodeCommandPrefix();

    private final static String COMMAND_WHICH_EXTRACTS_DEFAULT_PATH_TO_APPIUM = NODE_COMMAND_PREFIX +
            " npm -g ls --depth=0";
    private final static String COMMAND_WHICH_EXTRACTS_DEFAULT_PATH_TO_APPIUM_WIN = NODE_COMMAND_PREFIX +
            " npm.cmd -g ls --depth=0";

    private static final int REQUIRED_MAJOR_NODE_JS = 0;
    private static final int REQUIRED_MINOR_NODE_JS = 0;


    final Map<String, String> serverArguments = new HashMap<>();
    private File appiumJS;
    private String ipAddress = DEFAULT_LOCAL_IP_ADDRESS;

    //The first starting is slow sometimes on some
    //environment
    private long startupTimeout = 120;
    private TimeUnit timeUnit = TimeUnit.SECONDS;


    private static String returnCommandThatSearchesForDefaultNode(){
        if (Platform.getCurrent().is(Platform.WINDOWS))
            return COMMAND_WHICH_EXTRACTS_DEFAULT_PATH_TO_APPIUM_WIN;
        return COMMAND_WHICH_EXTRACTS_DEFAULT_PATH_TO_APPIUM;
    }

    private static String getProcessOutput(InputStream stream ) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream));
        String result = reader.readLine();
        reader.close();
        return result;
    }

    private static void validateNodeJSVersion(){
        Runtime rt = Runtime.getRuntime();
        String result = null;
        Process p = null;
        try {
            p = rt.exec(NODE_COMMAND_PREFIX + " node -v");
            p.waitFor();
            result = getProcessOutput(p.getInputStream());
        } catch (Exception e) {
            throw new InvalidNodeJSInstance("Node.js is not installed", e);
        }
        finally {
            if (p != null)
                p.destroy();
        }

        String versionNum =  result.replace("v","");
        String[] tokens = versionNum.split("\\.");
        if (Integer.parseInt(tokens[0]) < REQUIRED_MAJOR_NODE_JS ||
                Integer.parseInt(tokens[1]) < REQUIRED_MINOR_NODE_JS)
            throw new InvalidNodeJSInstance("Current node.js version " + versionNum + "is lower than " +
                    "required (" + REQUIRED_MAJOR_NODE_JS + "." + REQUIRED_MINOR_NODE_JS + " or greater)");
    }

    private static File findNodeInCurrentFileSystem(){
        Runtime rt = Runtime.getRuntime();
        String instancePath;
        Process p = null;
        try {
            p = rt.exec(returnCommandThatSearchesForDefaultNode());
            p.waitFor();
            instancePath = getProcessOutput(p.getInputStream());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            if (p != null)
                p.destroy();
        }

        File result;
        if (StringUtils.isBlank(instancePath) || !(result = new File(instancePath + File.separator + NODE_MODULES_FOLDER +
                APPIUM_NODE_MASK)).exists())
            throw new InvalidServerInstanceException( "There is no installed nodes! Please install " +
                    " node via NPM (https://www.npmjs.com/package/appium#using-node-js) or download and " +
                    "install Appium app (http://appium.io/downloads.html)",
                    new IOException("The installed appium node package has not been found."));

        return result;
    }

    private static void validateNodeStructure(File node){
        String absoluteNodePath = node.getAbsolutePath();

        if (!node.exists())
            throw new InvalidServerInstanceException("The invalid appium node " + absoluteNodePath + " has been defined",
                    new IOException("The node " + absoluteNodePath + "doesn't exist"));

        if (!absoluteNodePath.endsWith(APPIUM_NODE_MASK))
            throw new InvalidServerInstanceException("It is probably there is the corrupted appium server installation. Path " +
                    absoluteNodePath + "doesn't match " + APPIUM_NODE_MASK);
    }

    private static String defineNodeCommandPrefix() {
        if (Platform.getCurrent().is(Platform.WINDOWS))
            return  "cmd.exe /C";
        else
            return  "/bin/bash -l -c";
    }

    public AppiumServiceBuilder() {
        usingPort(DEFAULT_APPIUM_PORT);
    }

    @Override
    protected File findDefaultExecutable() {
        validateNodeJSVersion();
        Runtime rt = Runtime.getRuntime();
        Process p;
        try {
            p = rt.exec(NODE_COMMAND_PREFIX + " node");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            OutputStream outputStream = p.getOutputStream();
            PrintStream out = new PrintStream(outputStream) ;
            out.println("console.log(process.execPath);") ;
            out.close();

            return new File(getProcessOutput(p.getInputStream()));
        }
        catch (Throwable t){
            throw new RuntimeException(t);
        }
        finally {
            p.destroy();
        }
    }

    /**
     * Boolean arguments have a special moment:
     *              the presence of an arguments means "true". This method
     *              was designed for these cases
     * @param argument is an instance which contains the argument name
     * @return the self-reference
     */
    public AppiumServiceBuilder withArgument(ServerArgument argument) {
        serverArguments.put(argument.getArgument(), "");
        return this;
    }

    /**
     *
     * @param argument is an instance which contains the argument name
     * @param value A non null string value. (Warn!!!) Boolean arguments have a special moment:
     *              the presence of an arguments means "true". At this case an empty string
     *              should be defined
     * @return the self-reference
     */
    public AppiumServiceBuilder withArgument(ServerArgument argument, String value){
        serverArguments.put(argument.getArgument(), value);
        return this;
    }

    public AppiumServiceBuilder withAppiumJS(File appiumJS){
        this.appiumJS = appiumJS;
        return this;
    }

    public AppiumServiceBuilder withIPAddress(String ipAddress){
        this.ipAddress = ipAddress;
        return this;
    }

    public AppiumServiceBuilder withStartUpTimeOut(long time, TimeUnit timeUnit){
        checkNotNull(timeUnit);
        checkArgument(time > 0, "Time value should be greater than zero", time);
        this.startupTimeout = time;
        this.timeUnit = timeUnit;
        return this;
    }


    void checkAppiumJS(){
        if (appiumJS != null){
            validateNodeStructure(appiumJS);
            return;
        }

        String appiumJS = System.getProperty(APPIUM_NODE_PROPERTY);
        if (appiumJS !=null){
            File node = new File(appiumJS);
            validateNodeStructure(node);
            this.appiumJS =  node;
            return;
        }

        this.appiumJS = findNodeInCurrentFileSystem();
    }

    @Override
    protected ImmutableList<String> createArgs() {
        List<String> argList = new ArrayList<>();
        checkAppiumJS();
        argList.add(appiumJS.getAbsolutePath());
        argList.add("--port");
        argList.add(String.valueOf(getPort()));

        if (StringUtils.isBlank(ipAddress))
            ipAddress = DEFAULT_LOCAL_IP_ADDRESS;
        else {
            InetAddressValidator validator = InetAddressValidator.getInstance();
            if (!validator.isValid(ipAddress) && !validator.isValidInet4Address(ipAddress) &&
                    !validator.isValidInet6Address(ipAddress))
                throw new IllegalArgumentException("The invalid IP address " + ipAddress + " is defined");
        }
        argList.add("--address");
        argList.add(ipAddress);

        File log = getLogFile();
        if (log != null){
            argList.add("--log");
            argList.add(log.getAbsolutePath());
        }

        Set<Map.Entry<String, String>> entries = serverArguments.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> entry = iterator.next();
            String argument = entry.getKey();
            String value = entry.getValue();
            if (StringUtils.isBlank(argument) || value == null)
                continue;

            argList.add(argument);
            if (!StringUtils.isBlank(value))
                argList.add(value);
        }

        ImmutableList<String> result = new ImmutableList.Builder<String>().addAll(argList).build();
        return result;
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
     *     appium server with.
     * @return A self reference.
     */
    @Override
    public AppiumServiceBuilder withEnvironment(Map<String, String> environment) {
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
    protected AppiumDriverLocalService createDriverService(File nodeJSExecutable, int nodeJSPort, ImmutableList<String> nodeArguments,
                                                           ImmutableMap<String, String> nodeEnvironment) {
        try {
            return new AppiumDriverLocalService(ipAddress, nodeJSExecutable, nodeJSPort, nodeArguments, nodeEnvironment,
                    startupTimeout, timeUnit);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
