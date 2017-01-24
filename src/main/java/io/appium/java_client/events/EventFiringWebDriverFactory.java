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

import static io.appium.java_client.events.EventFiringObjectFactory.getEventFiringObject;

import io.appium.java_client.events.api.Listener;
import org.openqa.selenium.WebDriver;

import java.util.Collection;

public class EventFiringWebDriverFactory {

    /**
     * This method makes an event firing instance of {@link org.openqa.selenium.WebDriver}
     *
     * @param driver an original instance of {@link org.openqa.selenium.WebDriver} that is
     *               supposed to be listenable
     * @param <T> T
     * @return an instance of {@link org.openqa.selenium.WebDriver} that fires events
     */
    public static <T extends WebDriver> T getEventFiringWebDriver(T driver) {
        return getEventFiringObject(driver, driver);
    }

    /**
     * This method makes an event firing instance of {@link org.openqa.selenium.WebDriver}
     *
     * @param driver an original instance of {@link org.openqa.selenium.WebDriver} that is
     *               supposed to be listenable
     * @param listeners is a set of {@link io.appium.java_client.events.api.Listener} that
     *                  is supposed to be used for the event firing
     * @param <T> T
     * @return an instance of {@link org.openqa.selenium.WebDriver} that fires events
     */
    public static <T extends WebDriver> T getEventFiringWebDriver(T driver, Listener ... listeners) {
        return getEventFiringObject(driver, driver, listeners);
    }

    /**
     * This method makes an event firing instance of {@link org.openqa.selenium.WebDriver}
     *
     * @param driver an original instance of {@link org.openqa.selenium.WebDriver} that is
     *               supposed to be listenable
     * @param listeners is a collection of {@link io.appium.java_client.events.api.Listener} that
     *                  is supposed to be used for the event firing
     * @param <T> T
     * @return an instance of {@link org.openqa.selenium.WebDriver} that fires events
     */
    public static <T extends WebDriver> T getEventFiringWebDriver(T driver, Collection<Listener> listeners) {
        return getEventFiringObject(driver, driver, listeners);
    }
}
