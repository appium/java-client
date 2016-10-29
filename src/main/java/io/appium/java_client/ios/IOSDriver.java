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

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.FindsByIosNSPredicate;
import io.appium.java_client.FindsByIosUIAutomation;
import io.appium.java_client.ios.internal.JsonToIOSElementConverter;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.security.Credentials;

import java.net.URL;


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
        FindsByIosUIAutomation<T>, FindsByIosNSPredicate<T>, LocksIOSDevice {

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
