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
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;

/**
 * Deprecated. Use EventFiringDecorator and WebDriverListener instead.
 */
@Deprecated
public interface AlertEventListener extends Listener {

    /**
     * This action will be performed each time before {@link Alert#accept()}.
     *
     * @param driver WebDriver
     * @param alert {@link Alert} which is being accepted
     */
    void beforeAlertAccept(WebDriver driver, Alert alert);

    /**
     * This action will be performed each time after {@link Alert#accept()}.
     *
     * @param driver WebDriver
     * @param alert {@link Alert} which has been accepted
     */
    void afterAlertAccept(WebDriver driver, Alert alert);

    /**
     * This action will be performed each time before {@link Alert#dismiss()}.
     *
     * @param driver WebDriver
     * @param alert {@link Alert} which which is being dismissed
     */
    void afterAlertDismiss(WebDriver driver, Alert alert);

    /**
     * This action will be performed each time after {@link Alert#dismiss()}.
     *
     * @param driver WebDriver
     * @param alert {@link Alert} which has been dismissed
     */
    void beforeAlertDismiss(WebDriver driver, Alert alert);

    /**
     * This action will be performed each time before {@link Alert#sendKeys(String)}.
     *
     * @param driver WebDriver
     * @param alert {@link Alert} which is receiving keys
     * @param keys Keys which are being sent
     */
    void beforeAlertSendKeys(WebDriver driver, Alert alert, String keys);

    /**
     * This action will be performed each time after {@link Alert#sendKeys(String)}.
     *
     * @param driver WebDriver
     * @param alert {@link Alert} which has received keys
     * @param keys Keys which have been sent
     */
    void afterAlertSendKeys(WebDriver driver, Alert alert, String keys);
}
