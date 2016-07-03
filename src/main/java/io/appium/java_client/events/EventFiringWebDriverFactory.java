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


import io.appium.java_client.events.api.Listener;
import org.openqa.selenium.WebDriver;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

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
        return getEventFiringWebDriver(driver, Collections.<Listener>emptyList());
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
        return getEventFiringWebDriver(driver, Arrays.asList(listeners));
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
    @SuppressWarnings("unchecked")
    public static <T extends WebDriver> T getEventFiringWebDriver(T driver, Collection<Listener> listeners) {
        List<Listener> listenerList = new ArrayList<>();
        Iterator<Listener> providers = ServiceLoader.load(
            Listener.class).iterator();

        while (providers.hasNext()) {
            listenerList.add(providers.next());
        }

        listenerList.addAll(listeners);

        AbstractApplicationContext context = new AnnotationConfigApplicationContext(
            DefaultBeanConfiguration.class);
        return (T) context.getBean(
            DefaultBeanConfiguration.WEB_DRIVER_BEAN, driver, listenerList, context);
    }
}
