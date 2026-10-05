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

package io.appium.java_client.selenium;

import io.appium.java_client.remote.AppiumRemoteWebDriver;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.Response;

import java.io.IOException;
import java.util.LinkedHashMap;

import static java.util.Objects.requireNonNull;

/**
 * Executes Selenium commands with the command executor of an Appium driver, so that both drivers share the
 * same session and HTTP client. The new session command is answered with the existing session, which lets the
 * Selenium driver take it over through its public constructor.
 */
final class SeleniumCommandExecutor implements CommandExecutor {
    private final AppiumRemoteWebDriver delegate;

    SeleniumCommandExecutor(AppiumRemoteWebDriver delegate) {
        this.delegate = delegate;
    }

    @Override
    @Nullable
    public Response execute(Command command) throws IOException {
        if (DriverCommand.NEW_SESSION.equals(command.getName())) {
            return existingSession();
        }
        var sessionId = command.getSessionId() == null
                ? null : new io.appium.java_client.remote.SessionId(command.getSessionId().toString());
        var response = delegate.getCommandExecutor().execute(
                new io.appium.java_client.remote.Command(sessionId, command.getName(), command.getParameters()));
        if (response == null) {
            return null;
        }
        var result = new Response();
        result.setSessionId(response.getSessionId());
        result.setState(response.getState());
        result.setValue(response.getValue());
        return result;
    }

    private Response existingSession() {
        var capabilities = new LinkedHashMap<>(delegate.getCapabilities().asMap());
        // The Selenium driver parses the platform name from a string
        capabilities.computeIfPresent("platformName",
                (key, value) -> value instanceof Platform ? ((Platform) value).name() : value);
        var response = new Response(new org.openqa.selenium.remote.SessionId(
                requireNonNull(delegate.getSessionId(), "The Appium driver does not have a session").toString()));
        response.setState("success");
        response.setValue(capabilities);
        return response;
    }
}
