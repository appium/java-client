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

package io.appium.java_client.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.appium.java_client.events.stubs.EmptyWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;

public class EventsFiringTest {
    private final WebDriver emptyWebDriver = new EmptyWebDriver();
    private CustomListener listener;
    private WebDriver decorated;

    @BeforeEach
    public void beforeTest() {
        listener = new CustomListener();
        decorated = new EventFiringDecorator(listener).decorate(emptyWebDriver);
    }

    @Test
    public void checkBasicEventsFiring() {
        decorated.get("http://example.com/");
        assertTrue(listener.isDidCallBeforeGet());
        assertTrue(listener.isDidCallAfterGet());
        assertEquals(listener.getUrl(), "http://example.com/");
    }

    @Test
    public void checkAnyWebDriverEventsFiring() {
        decorated.get("http://example.com/");
        assertTrue(listener.isDidCallBeforeAnyWebDriverCall());
        assertTrue(listener.isDidCallAfterWebDriverAnyCall());
    }
}
