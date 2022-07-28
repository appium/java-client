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

package io.appium.java_client.android;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.appium.java_client.serverevents.CommandEvent;
import io.appium.java_client.serverevents.CustomEvent;
import io.appium.java_client.serverevents.TimedEvent;
import io.appium.java_client.serverevents.ServerEvents;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class LogEventTest extends BaseAndroidTest {

    @Test
    public void verifyLoggingCustomEvents() {
        CustomEvent evt = new CustomEvent();
        evt.setEventName("funEvent");
        evt.setVendor("appium");
        driver.logEvent(evt);
        ServerEvents events = driver.getEvents();
        boolean hasCustomEvent = events.events.stream().anyMatch((TimedEvent event) ->
            event.name.equals("appium:funEvent") &&
            event.occurrences.get(0).intValue() > 0
        );
        boolean hasCommandName = events.commands.stream().anyMatch((CommandEvent event) ->
            event.name.equals("logCustomEvent")
        );
        assertTrue(hasCustomEvent);
        assertTrue(hasCommandName);
        assertThat(events.jsonData, Matchers.containsString("\"appium:funEvent\""));
    }
}
