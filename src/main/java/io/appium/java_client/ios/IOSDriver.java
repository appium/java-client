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

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.FindsByAccessibilityId;
import io.appium.java_client.FindsByIosUIAutomation;
import io.appium.java_client.ScrollsTo;
import io.appium.java_client.ios.internal.JsonToIOSElementConverter;
import io.appium.java_client.remote.MobilePlatform;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.net.URL;
import java.util.List;

import static io.appium.java_client.MobileCommand.*;
import org.openqa.selenium.remote.http.HttpClient;

/**
 * @param <RequiredElementType> the required type of class which implement {@link org.openqa.selenium.WebElement}.
 * Instances of the defined type will be returned via findElement* and findElements*.
 * Warning (!!!). Allowed types:
 * {@link org.openqa.selenium.WebElement}
 * {@link io.appium.java_client.TouchableElement}
 * {@link org.openqa.selenium.remote.RemoteWebElement}
 * {@link io.appium.java_client.MobileElement}
 * {@link io.appium.java_client.ios.IOSElement}
 */
public class IOSDriver<RequiredElementType extends WebElement> extends AppiumDriver<RequiredElementType> implements IOSDeviceActionShortcuts, GetsNamedTextField<RequiredElementType>, 
FindsByIosUIAutomation<RequiredElementType>{
    private static final String IOS_PLATFORM = MobilePlatform.IOS;

    public IOSDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(remoteAddress, substituteMobilePlatform(desiredCapabilities,
                IOS_PLATFORM));
        this.setElementConverter(new JsonToIOSElementConverter(this));
    }
    
    public IOSDriver(URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(remoteAddress, httpClientFactory, substituteMobilePlatform(desiredCapabilities,
                IOS_PLATFORM));
        this.setElementConverter(new JsonToIOSElementConverter(this));
    }

    public IOSDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        super(service, substituteMobilePlatform(desiredCapabilities,
                IOS_PLATFORM));
        this.setElementConverter(new JsonToIOSElementConverter(this));
    }
    
    public IOSDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(service, httpClientFactory, substituteMobilePlatform(desiredCapabilities,
                IOS_PLATFORM));
        this.setElementConverter(new JsonToIOSElementConverter(this));
    }

    public IOSDriver(AppiumServiceBuilder builder, Capabilities desiredCapabilities) {
        super(builder, substituteMobilePlatform(desiredCapabilities,
                IOS_PLATFORM));
        this.setElementConverter(new JsonToIOSElementConverter(this));
    }
    
    public IOSDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(builder, httpClientFactory, substituteMobilePlatform(desiredCapabilities,
                IOS_PLATFORM));
        this.setElementConverter(new JsonToIOSElementConverter(this));
    }
    
    public IOSDriver(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(httpClientFactory, substituteMobilePlatform(desiredCapabilities,
                IOS_PLATFORM));
        this.setElementConverter(new JsonToIOSElementConverter(this));
    }

    public IOSDriver(Capabilities desiredCapabilities) {
        super(substituteMobilePlatform(desiredCapabilities,
                IOS_PLATFORM));
        this.setElementConverter(new JsonToIOSElementConverter(this));
    }

  /**
   * Scroll to the element whose 'text' attribute contains the input text.
   * This scrolling happens within the first UIATableView on the UI. Use the method on IOSElement to scroll from a different ScrollView.
   * @param text input text contained in text attribute
   */
   @SuppressWarnings("unchecked")
   @Override
   public RequiredElementType scrollTo(String text) {
     return (RequiredElementType) ((ScrollsTo<?>) 
             findElementByClassName("UIATableView")).scrollTo(text);
   }

   /**
   * Scroll to the element whose 'text' attribute is equal to the input text.
   * This scrolling happens within the first UIATableView on the UI. Use the method on IOSElement to scroll from a different ScrollView.
   * @param text input text to match
   */
   @SuppressWarnings("unchecked")
   @Override
   public RequiredElementType scrollToExact(String text) {
      return (RequiredElementType) ((ScrollsTo<?>)
              findElementByClassName("UIATableView")).scrollToExact(text);
   }

   /**
     * @see IOSDeviceActionShortcuts#hideKeyboard(String, String)
     */
    @Override
    public void hideKeyboard(String strategy, String keyName) {
        String[] parameters = new String[] { "strategy", "key" };
        Object[] values = new Object[] { strategy, keyName };
        execute(HIDE_KEYBOARD, getCommandImmutableMap(parameters, values));
    }

    /**
     * @see IOSDeviceActionShortcuts#hideKeyboard(String)
     */
    @Override
    public void hideKeyboard(String keyName) {
        execute(HIDE_KEYBOARD, ImmutableMap.of("keyName", keyName));
    }

    /**
     * @see IOSDeviceActionShortcuts#shake()
     */
    @Override
    public void shake() {
        execute(SHAKE);
    }

    /**
     * @see GetsNamedTextField#getNamedTextField(String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public RequiredElementType getNamedTextField(String name) {
        RequiredElementType element = findElementByAccessibilityId(name);
        if (element.getTagName() != "TextField") {
            return (RequiredElementType) ((FindsByAccessibilityId<?>) element).
                    findElementByAccessibilityId(name);
        }
        return element;
    }

    /**
     * @throws org.openqa.selenium.WebDriverException This method is not applicable with browser/webview UI.
     */
    @SuppressWarnings("unchecked")
    @Override
    public RequiredElementType findElementByIosUIAutomation(String using) throws WebDriverException {
        return (RequiredElementType) findElement("-ios uiautomation", using);
    }

    /**
     * @throws WebDriverException This method is not applicable with browser/webview UI.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<RequiredElementType> findElementsByIosUIAutomation(String using) throws WebDriverException {
        return (List<RequiredElementType>) findElements("-ios uiautomation", using);
    }
}
