/*
 * Copyright 2015 Appium Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
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
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.net.UrlChecker;
import org.openqa.selenium.os.CommandLine;
import org.openqa.selenium.remote.service.DriverService;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


public final class AppiumDriverLocalService extends DriverService {

    private static final String URL_MASK = "http://%s:%d/wd/hub";
    private final File nodeJSExec;
    private final int nodeJSPort;
    private final ImmutableList<String> nodeJSArgs;
    private final ImmutableMap<String, String> nodeJSEnvironment;
    private final String ipAddress;
    private final long startupTimeout;
    private final TimeUnit timeUnit;


    private CommandLine process = null;

    AppiumDriverLocalService(String ipAddress, File nodeJSExec, int nodeJSPort,
                             ImmutableList<String> nodeJSArgs,
                             ImmutableMap<String, String> nodeJSEnvironment,
                             long startupTimeout,
                             TimeUnit timeUnit) throws IOException {
        super(nodeJSExec, nodeJSPort, nodeJSArgs, nodeJSEnvironment);
        this.ipAddress = ipAddress;
        this.nodeJSExec = nodeJSExec;
        this.nodeJSPort = nodeJSPort;
        this.nodeJSArgs = nodeJSArgs;
        this.nodeJSEnvironment = nodeJSEnvironment;
        this.startupTimeout = startupTimeout;
        this.timeUnit = timeUnit;
    }

    /**
     * @return The base URL for the managed appium server.
     */
    @Override
    public URL getUrl() {
        try {
            return new URL(String.format(URL_MASK, ipAddress,
                    nodeJSPort));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isRunning() {
        if (process == null)
            return false;

        try {
            ping(500, TimeUnit.MILLISECONDS);
            return true;
        } catch (UrlChecker.TimeoutException e) {
            return false;
        }

    }

    private void ping(long time, TimeUnit timeUnit) throws UrlChecker.TimeoutException{
        URL url = getUrl();
        try {
            URL status = new URL(url.toString() + "/status");
            new UrlChecker().waitUntilAvailable(time, timeUnit, status);
        } catch (MalformedURLException e) {
            throw new RuntimeException("There is something wrong with the URL " + url.toString().toString() + "/status");
        }
    }

    /**
     * Starts the defined appium server
     * @throws AppiumServerHasNotBeenStartedLocallyException If an error occurs while spawning the child process.
     * @see #stop()
     */
    public synchronized void start() throws AppiumServerHasNotBeenStartedLocallyException {

        if (isRunning())
            return;

        try {
            process = new CommandLine(this.nodeJSExec.getCanonicalPath(), nodeJSArgs.toArray(new String[] {}));
            process.setEnvironmentVariables(nodeJSEnvironment);
            process.copyOutputTo(System.err);
            process.executeAsync();
            ping(startupTimeout, timeUnit);
        } catch (Throwable e) {
            destroyProcess();
            String msgTxt = "The local appium server has not been started. " +
                    "The given Node.js executable: " + this.nodeJSExec.getAbsolutePath() + " Arguments: " + nodeJSArgs.toString() + " " + "\n";
            String processStream = process.getStdOut();
            if (!StringUtils.isBlank(processStream))
                msgTxt = msgTxt + "Process output: " + processStream + "\n";

            throw new AppiumServerHasNotBeenStartedLocallyException(msgTxt,
                    e);
        }
    }

    /**
     * Stops this service is it is currently running. This method will attempt to block until the
     * server has been fully shutdown.
     *
     * @see #start()
     */
    @Override
    public synchronized void stop() {
        destroyProcess();
    }


    private void destroyProcess(){
        if (process != null)
            process.destroy();
    }

    /**
     * @return String logs if the server has been run.
     * null is returned otherwise.
     */
    public String getStdOut() {
        if (process != null)
            return process.getStdOut();

        return null;
    }

    public static AppiumDriverLocalService buildDefaultService(){
        return buildService(new AppiumServiceBuilder());
    }

    public static  AppiumDriverLocalService buildService(AppiumServiceBuilder builder){
        return builder.build();
    }

}
