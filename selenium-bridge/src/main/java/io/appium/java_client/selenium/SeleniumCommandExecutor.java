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

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.Response;

import java.io.IOException;

/**
 * Executes Selenium commands with the command executor of an Appium driver, so that both
 * drivers share the same session and HTTP client.
 */
final class SeleniumCommandExecutor implements CommandExecutor {
    private final io.appium.java_client.remote.CommandExecutor delegate;

    SeleniumCommandExecutor(io.appium.java_client.remote.CommandExecutor delegate) {
        this.delegate = delegate;
    }

    @Override
    @Nullable
    public Response execute(Command command) throws IOException {
        var sessionId = command.getSessionId() == null
                ? null : new io.appium.java_client.remote.SessionId(command.getSessionId().toString());
        var response = delegate.execute(
                new io.appium.java_client.remote.Command(sessionId, command.getName(), command.getParameters()));
        if (response == null) {
            return null;
        }
        var result = new Response();
        result.setSessionId(response.getSessionId());
        result.setStatus(response.getStatus());
        result.setState(response.getState());
        result.setValue(response.getValue());
        return result;
    }
}
