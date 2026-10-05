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

import io.appium.java_client.http.HttpMethod;
import io.appium.java_client.http.HttpRequest;

/**
 * Translates commands into HTTP requests.
 */
public interface CommandCodec {
    /**
     * Encodes the command.
     *
     * @param command the command to encode
     * @return the HTTP request that executes the command
     * @throws org.openqa.selenium.UnsupportedCommandException if the command is not defined
     */
    HttpRequest encode(Command command);

    boolean isSupported(String commandName);

    /**
     * Defines a command.
     *
     * @param name the command name
     * @param method the HTTP method
     * @param pathPattern the URI path pattern. Segments starting with ':' are replaced with the
     *                    command parameter of the same name.
     */
    void defineCommand(String name, HttpMethod method, String pathPattern);
}
