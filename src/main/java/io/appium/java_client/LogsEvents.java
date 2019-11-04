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

import static io.appium.java_client.MobileCommand.LOG_EVENT;

import java.util.HashMap;
import java.util.Map;

public interface LogsEvents extends ExecutesMethod {

    /**
     * Log a custom event on the Appium server.
     *
     * @since Appium 1.16
     * @param vendor the name of the vendor prefix to avoid event collisions
     * @param eventName the name of the event
     * @throws org.openqa.selenium.WebDriverException if there was a failure while executing the script
     */
    default void logEvent(String vendor, String eventName) {
        Map<String, Object> data = new HashMap<>();
        data.put("vendor", vendor);
        data.put("event", eventName);
        execute(LOG_EVENT, data);
    }
}
