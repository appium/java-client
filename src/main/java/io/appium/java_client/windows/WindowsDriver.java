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

package io.appium.java_client.windows;

import static io.appium.java_client.remote.MobilePlatform.WINDOWS;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.FindsByWindowsAutomation;
import io.appium.java_client.HidesKeyboardWithKeyName;
import io.appium.java_client.PressesKeyCode;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.http.HttpClient;

import java.net.URL;

public class WindowsDriver<T extends WebElement>
        extends AppiumDriver<T> implements PressesKeyCode, HidesKeyboardWithKeyName,
        FindsByWindowsAutomation<T> {

    public WindowsDriver(HttpCommandExecutor executor, Capabilities capabilities) {
        super(executor, substituteMobilePlatform(capabilities, WINDOWS));
    }

    public WindowsDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(remoteAddress, substituteMobilePlatform(desiredCapabilities, WINDOWS));
    }

    public WindowsDriver(URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(remoteAddress, httpClientFactory, substituteMobilePlatform(desiredCapabilities, WINDOWS));
    }

    public WindowsDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        super(service, substituteMobilePlatform(desiredCapabilities, WINDOWS));
    }

    public WindowsDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory,
                         Capabilities desiredCapabilities) {
        super(service, httpClientFactory, substituteMobilePlatform(desiredCapabilities, WINDOWS));
    }

    public WindowsDriver(AppiumServiceBuilder builder, Capabilities desiredCapabilities) {
        super(builder, substituteMobilePlatform(desiredCapabilities, WINDOWS));
    }

    public WindowsDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory,
                         Capabilities desiredCapabilities) {
        super(builder, httpClientFactory, substituteMobilePlatform(desiredCapabilities, WINDOWS));
    }

    public WindowsDriver(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(httpClientFactory, substituteMobilePlatform(desiredCapabilities, WINDOWS));
    }

    public WindowsDriver(Capabilities desiredCapabilities) {
        super(substituteMobilePlatform(desiredCapabilities, WINDOWS));
    }
}
