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

package io.appium.java_client.ios;

import static io.appium.java_client.MobileCommand.prepareArguments;
import static io.appium.java_client.ios.IOSMobileCommandHelper.hideKeyboardCommand;
import static io.appium.java_client.ios.IOSMobileCommandHelper.lockDeviceCommand;
import static io.appium.java_client.ios.IOSMobileCommandHelper.shakeCommand;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.FindsByIosUIAutomation;
import io.appium.java_client.MobileSelector;
import io.appium.java_client.ios.internal.JsonToIOSElementConverter;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.security.Credentials;

import java.net.URL;
import java.util.List;


/**
 * @param <T> the required type of class which implement
 *            {@link org.openqa.selenium.WebElement}.
 *            Instances of the defined type will be returned via findElement* and findElements*.
 *            Warning (!!!). Allowed types:
 * {@link org.openqa.selenium.WebElement}
 * {@link io.appium.java_client.TouchableElement}
 * {@link org.openqa.selenium.remote.RemoteWebElement}
 * {@link io.appium.java_client.MobileElement}
 * {@link io.appium.java_client.ios.IOSElement}
 */
public class IOSDriver<T extends WebElement>
    extends AppiumDriver<T>
    implements IOSDeviceActionShortcuts,
        FindsByIosUIAutomation<T> {

    private static final String IOS_PLATFORM = MobilePlatform.IOS;

    /**
     * @param executor is an instance of {@link org.openqa.selenium.remote.HttpCommandExecutor}
     *                 or class that extends it. Default commands or another vendor-specific
     *                 commands may be specified there.
     * @param capabilities take a look
     *                     at {@link org.openqa.selenium.Capabilities}
     */
    public IOSDriver(HttpCommandExecutor executor, Capabilities capabilities) {
        super(executor, capabilities, JsonToIOSElementConverter.class);
    }

    /**
     * @param remoteAddress is the address
     *                      of remotely/locally started Appium server
     * @param desiredCapabilities take a look
     *                            at {@link org.openqa.selenium.Capabilities}
     */
    public IOSDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(remoteAddress, substituteMobilePlatform(desiredCapabilities, IOS_PLATFORM),
                JsonToIOSElementConverter.class);
    }

    /**
     * @param remoteAddress is the address
     *                      of remotely/locally started Appium server
     * @param httpClientFactory take a look
     *                          at {@link org.openqa.selenium.remote.http.HttpClient.Factory}
     * @param desiredCapabilities take a look
     *                            at {@link org.openqa.selenium.Capabilities}
     */
    public IOSDriver(URL remoteAddress, HttpClient.Factory httpClientFactory,
        Capabilities desiredCapabilities) {
        super(remoteAddress, httpClientFactory,
            substituteMobilePlatform(desiredCapabilities, IOS_PLATFORM),
                JsonToIOSElementConverter.class);
    }

    /**
     * @param service take a look
     *                at {@link io.appium.java_client.service.local.AppiumDriverLocalService}
     * @param desiredCapabilities take a look
     *                            at {@link org.openqa.selenium.Capabilities}
     */
    public IOSDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        super(service, substituteMobilePlatform(desiredCapabilities, IOS_PLATFORM),
                JsonToIOSElementConverter.class);
    }

    /**
     * @param service take a look
     *                at {@link io.appium.java_client.service.local.AppiumDriverLocalService}
     * @param httpClientFactory take a look
     *                          at {@link org.openqa.selenium.remote.http.HttpClient.Factory}
     * @param desiredCapabilities take a look
     *                            at {@link org.openqa.selenium.Capabilities}
     */
    public IOSDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory,
        Capabilities desiredCapabilities) {
        super(service, httpClientFactory,
            substituteMobilePlatform(desiredCapabilities, IOS_PLATFORM),
                JsonToIOSElementConverter.class);
    }

    /**
     * @param builder take a look
     *                at {@link io.appium.java_client.service.local.AppiumServiceBuilder}
     * @param desiredCapabilities take a look
     *                            at {@link org.openqa.selenium.Capabilities}
     */
    public IOSDriver(AppiumServiceBuilder builder, Capabilities desiredCapabilities) {
        super(builder, substituteMobilePlatform(desiredCapabilities, IOS_PLATFORM),
                JsonToIOSElementConverter.class);
    }

    /**
     * @param builder take a look
     *                at {@link io.appium.java_client.service.local.AppiumServiceBuilder}
     * @param httpClientFactory take a look
     *                          at {@link org.openqa.selenium.remote.http.HttpClient.Factory}
     * @param desiredCapabilities take a look
     *                            at {@link org.openqa.selenium.Capabilities}
     */
    public IOSDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory,
        Capabilities desiredCapabilities) {
        super(builder, httpClientFactory,
            substituteMobilePlatform(desiredCapabilities, IOS_PLATFORM),
                JsonToIOSElementConverter.class);
    }

    /**
     * @param httpClientFactory take a look
     *                          at {@link org.openqa.selenium.remote.http.HttpClient.Factory}
     * @param desiredCapabilities take a look
     *                            at {@link org.openqa.selenium.Capabilities}
     */
    public IOSDriver(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(httpClientFactory, substituteMobilePlatform(desiredCapabilities, IOS_PLATFORM),
                JsonToIOSElementConverter.class);
    }

    /**
     * @param desiredCapabilities take a look
     *                            at {@link org.openqa.selenium.Capabilities}
     */
    public IOSDriver(Capabilities desiredCapabilities) {
        super(substituteMobilePlatform(desiredCapabilities, IOS_PLATFORM),
                JsonToIOSElementConverter.class);
    }

    /**
     * @see io.appium.java_client.TouchShortcuts#swipe(int, int, int, int, int).
     */
    @Override public void swipe(int startx, int starty, int endx, int endy, int duration) {
        doSwipe(startx, starty, endx - startx, endy - starty, duration);
    }

    /**
     * @see IOSDeviceActionShortcuts#hideKeyboard(String, String).
     */
    @Override public void hideKeyboard(String strategy, String keyName) {
        CommandExecutionHelper.execute(this, hideKeyboardCommand(strategy, keyName));
    }

    /**
     * @see IOSDeviceActionShortcuts#hideKeyboard(String).
     */
    @Override public void hideKeyboard(String keyName) {
        CommandExecutionHelper.execute(this, hideKeyboardCommand(keyName));
    }

    /**
     * @see IOSDeviceActionShortcuts#shake().
     */
    @Override public void shake() {
        CommandExecutionHelper.execute(this, shakeCommand());
    }

    /**
     * @throws WebDriverException
     *     This method is not applicable with browser/webview UI.
     */
    @Override
    public T findElementByIosUIAutomation(String using)
        throws WebDriverException {
        return findElement(MobileSelector.IOS_UI_AUTOMATION.toString(), using);
    }

    /**
     * @throws WebDriverException This method is not applicable with browser/webview UI.
     */
    @Override
    public List<T> findElementsByIosUIAutomation(String using)
        throws WebDriverException {
        return findElements(MobileSelector.IOS_UI_AUTOMATION.toString(), using);
    }

    /**
     * Lock the device (bring it to the lock screen) for a given number of
     * seconds.
     *
     * @param seconds number of seconds to lock the screen for
     */
    public void lockDevice(int seconds) {
        CommandExecutionHelper.execute(this, lockDeviceCommand(seconds));
    }

    @Override public TargetLocator switchTo() {
        return new InnerTargetLocator();
    }

    private class InnerTargetLocator extends RemoteTargetLocator {
        @Override public Alert alert() {
            return new IOSAlert(super.alert());
        }
    }


    class IOSAlert implements Alert {

        private final Alert alert;

        IOSAlert(Alert alert) {
            this.alert = alert;
        }

        @Override public void dismiss() {
            execute(DriverCommand.DISMISS_ALERT);
        }

        @Override public void accept() {
            execute(DriverCommand.ACCEPT_ALERT);
        }

        @Override public String getText() {
            Response response = execute(DriverCommand.GET_ALERT_TEXT);
            return response.getValue().toString();
        }

        @Override public void sendKeys(String keysToSend) {
            execute(DriverCommand.SET_ALERT_VALUE, prepareArguments("value", keysToSend));
        }

        @Override public void setCredentials(Credentials credentials) {
            alert.setCredentials(credentials);
        }

        @Override public void authenticateUsing(Credentials credentials) {
            alert.authenticateUsing(credentials);
        }

    }
}
