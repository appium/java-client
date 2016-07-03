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

package io.appium.java_client.events.api.general;

import io.appium.java_client.events.api.Listener;
import org.openqa.selenium.WebDriver;

public interface JavaScriptEventListener extends Listener {
    /**
     * Called before
     * {@link org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String,
     * java.lang.Object[]) }
     *
     * @param driver WebDriver
     * @param script the script to be executed
     */
    void beforeScript(String script, WebDriver driver);

    /**
     * Called after
     * {@link org.openqa.selenium.remote.RemoteWebDriver#executeScript(java.lang.String,
     * java.lang.Object[]) }.
     * Not called if an exception is thrown
     *
     * @param driver WebDriver
     * @param script the script that was executed
     */
    void afterScript(String script, WebDriver driver);
}
