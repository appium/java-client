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

package io.appium.java_client.internal.webdriver;

import java.util.Map;

/**
 * Thrown if the server response cannot be interpreted as a W3C response.
 */
public class InvalidResponseException extends IllegalArgumentException {
    private static final String W3C_ERRORS_URL = "https://www.w3.org/TR/webdriver2/#errors";

    public InvalidResponseException(String message, Map<String, Object> response) {
        super(String.format("%s: %s%nSee %s", message, response, W3C_ERRORS_URL));
    }
}
