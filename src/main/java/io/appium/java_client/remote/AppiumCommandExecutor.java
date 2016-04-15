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

package io.appium.java_client.remote;


import com.google.common.base.Throwables;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandInfo;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.internal.ApacheHttpClient;
import org.openqa.selenium.remote.service.DriverService;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.util.Map;

public class AppiumCommandExecutor extends HttpCommandExecutor {

    private final DriverService service;

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands,
        URL addressOfRemoteServer, HttpClient.Factory httpClientFactory) {
        super(additionalCommands, addressOfRemoteServer, httpClientFactory);
        service = null;
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands, DriverService service,
        HttpClient.Factory httpClientFactory) {
        super(additionalCommands, service.getUrl(), httpClientFactory);
        this.service = service;
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands,
        URL addressOfRemoteServer) {
        this(additionalCommands, addressOfRemoteServer, new ApacheHttpClient.Factory());
    }

    public AppiumCommandExecutor(Map<String, CommandInfo> additionalCommands,
        DriverService service) {
        this(additionalCommands, service, new ApacheHttpClient.Factory());
    }

    @Override public Response execute(Command command) throws IOException, WebDriverException {
        if (DriverCommand.NEW_SESSION.equals(command.getName()) && service != null) {
            service.start();
        }

        try {
            return super.execute(command);
        } catch (Throwable t) {
            Throwable rootCause = Throwables.getRootCause(t);
            if (rootCause instanceof ConnectException
                && rootCause.getMessage().contains("Connection refused")
                && service != null) {
                if (service.isRunning()) {
                    throw new WebDriverException("The session is closed!", t);
                }

                if (!service.isRunning()) {
                    throw new WebDriverException("The appium server has accidentally died!", t);
                }
            }
            Throwables.propagateIfPossible(t);
            throw new WebDriverException(t);
        } finally {
            if (DriverCommand.QUIT.equals(command.getName()) && service != null) {
                service.stop();
            }
        }
    }

}
