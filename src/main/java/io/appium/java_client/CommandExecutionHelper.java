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

package io.appium.java_client;

import org.openqa.selenium.remote.Response;

import java.util.Map;

public final class CommandExecutionHelper {

    public static <T extends Object> T execute(MobileElement element,
        Map.Entry<String, Map<String, ?>> keyValuePair) {
        return handleResponse(element.execute(keyValuePair.getKey(), keyValuePair.getValue()));
    }

    public static <T extends Object> T execute(MobileDriver driver,
        Map.Entry<String, Map<String, ?>> keyValuePair) {
        return handleResponse(driver.execute(keyValuePair.getKey(), keyValuePair.getValue()));
    }

    private static <T extends Object> T handleResponse(Response responce) {
        if (responce != null) {
            return (T) responce.getValue();
        }
        return null;
    }
}
