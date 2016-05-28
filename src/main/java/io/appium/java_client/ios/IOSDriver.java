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

import static io.appium.java_client.MobileCommand.HIDE_KEYBOARD;
import static io.appium.java_client.MobileCommand.LOCK;
import static io.appium.java_client.MobileCommand.SHAKE;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.FindsByAccessibilityId;
import io.appium.java_client.FindsByIosUIAutomation;
import io.appium.java_client.ScrollsTo;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.ios.internal.JsonToIOSElementConverter;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.http.HttpClient;

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
    implements IOSDeviceActionShortcuts, GetsNamedTextField<T>,
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
     * This method is deprecated because it is not consistent and it is going to be removed.
     * It is workaround actually.
     * Recommended to use instead:
     * {@link io.appium.java_client.AppiumDriver#swipe(int, int, int, int, int)}
     * {@link io.appium.java_client.MobileElement#swipe(SwipeElementDirection, int)}
     * {@link io.appium.java_client.MobileElement#swipe(SwipeElementDirection, int, int, int)}
     * or search for elements using {@link io.appium.java_client.MobileBy.ByIosUIAutomation}
     */
    @Deprecated
    @SuppressWarnings("unchecked")
    @Override
    public T scrollTo(String text) {
        return (T) ((ScrollsTo<?>) findElementByClassName("UIATableView"))
            .scrollTo(text);
    }

    /**
     * This method is deprecated because it is not consistent and it is going to be removed.
     * It is workaround actually.
     * Recommended to use instead:
     * {@link io.appium.java_client.AppiumDriver#swipe(int, int, int, int, int)}
     * {@link io.appium.java_client.MobileElement#swipe(SwipeElementDirection, int)}
     * {@link io.appium.java_client.MobileElement#swipe(SwipeElementDirection, int, int, int)}
     * or search for elements using {@link io.appium.java_client.MobileBy.ByIosUIAutomation}
     */
    @Deprecated
    @Override
    public T scrollToExact(String text) {
        return (T) ((ScrollsTo<?>) findElementByClassName("UIATableView"))
            .scrollToExact(text);
    }

    /**
     * @see IOSDeviceActionShortcuts#hideKeyboard(String, String).
     */
    @Override public void hideKeyboard(String strategy, String keyName) {
        String[] parameters = new String[] {"strategy", "key"};
        Object[] values = new Object[] {strategy, keyName};
        execute(HIDE_KEYBOARD, getCommandImmutableMap(parameters, values));
    }

    /**
     * @see IOSDeviceActionShortcuts#hideKeyboard(String).
     */
    @Override public void hideKeyboard(String keyName) {
        execute(HIDE_KEYBOARD, ImmutableMap.of("keyName", keyName));
    }

    /**
     * @see IOSDeviceActionShortcuts#shake().
     */
    @Override public void shake() {
        execute(SHAKE);
    }

    @Deprecated
    @SuppressWarnings("unchecked")
    @Override
    public T getNamedTextField(
        String name) {
        T element = findElementByAccessibilityId(name);
        if (element.getTagName() != "TextField") {
            return (T) ((FindsByAccessibilityId<?>) element)
                    .findElementByAccessibilityId(name);
        }
        return element;
    }

    /**
     * @throws org.openqa.selenium.WebDriverException
     *     This method is not applicable with browser/webview UI.
     */
    @SuppressWarnings("unchecked")
    @Override
    public T findElementByIosUIAutomation(String using)
        throws WebDriverException {
        return (T) findElement("-ios uiautomation", using);
    }

    /**
     * @throws WebDriverException This method is not applicable with browser/webview UI.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> findElementsByIosUIAutomation(String using)
        throws WebDriverException {
        return (List<T>) findElements("-ios uiautomation", using);
    }

    /**
     * Lock the device (bring it to the lock screen) for a given number of
     * seconds.
     *
     * @param seconds number of seconds to lock the screen for
     */
    public void lockDevice(int seconds) {
        execute(LOCK, ImmutableMap.of("seconds", seconds));
    }
}
