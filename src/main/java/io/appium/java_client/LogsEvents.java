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

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.serverevents.CommandEvent;
import io.appium.java_client.serverevents.CustomEvent;
import io.appium.java_client.serverevents.ServerEvents;
import io.appium.java_client.serverevents.TimedEvent;
import org.openqa.selenium.json.Json;
import org.openqa.selenium.remote.Response;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.appium.java_client.MobileCommand.GET_EVENTS;
import static io.appium.java_client.MobileCommand.LOG_EVENT;

public interface LogsEvents extends ExecutesMethod {

    /**
     * Log a custom event on the Appium server.
     *
     * @since Appium 1.16
     * @param event the event to log
     * @throws org.openqa.selenium.WebDriverException if there was a failure while executing the script
     */
    default void logEvent(CustomEvent event) {
        execute(LOG_EVENT, ImmutableMap.of("vendor", event.getVendor(), "event", event.getEventName()));
    }

    /**
     * Log a custom event on the Appium server.
     *
     * @since Appium 1.16
     * @return ServerEvents object wrapping up the various command and event timestamps
     * @throws org.openqa.selenium.WebDriverException if there was a failure while executing the script
     */
    default ServerEvents getEvents() {
        Response response = execute(GET_EVENTS);
        String jsonData = new Json().toJson(response.getValue());

        //noinspection unchecked
        Map<String, Object> value = (Map<String, Object>) response.getValue();

        //noinspection unchecked
        List<CommandEvent> commands = ((List<Map<String, Object>>) value.get("commands"))
            .stream()
            .map((Map<String, Object> cmd) -> new CommandEvent(
                (String) cmd.get("cmd"),
                ((Long) cmd.get("startTime")),
                ((Long) cmd.get("endTime"))
            ))
            .collect(Collectors.toList());

        List<TimedEvent> events = value.keySet().stream()
            .filter((String name) -> !name.equals("commands"))
            .map((String name) -> {
                //noinspection unchecked
                return new TimedEvent(name, (List<Long>) value.get(name));
            })
            .collect(Collectors.toList());

        return new ServerEvents(commands, events, jsonData);
    }
}
