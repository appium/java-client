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
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.seleniumone.Capabilities;
import org.openqa.seleniumone.WebElement;
import org.openqa.seleniumone.remote.HttpCommandExecutor;
import org.openqa.seleniumone.remote.http.HttpClient;

import java.net.URL;

public class WindowsDriver<T extends WebElement>
        extends AppiumDriver<T> implements PressesKeyCode, HidesKeyboardWithKeyName,
        FindsByWindowsAutomation<T> {

    public WindowsDriver(HttpCommandExecutor executor, Capabilities capabilities) {
        super(executor, updateDefaultPlatformName(capabilities, WINDOWS));
    }

    public WindowsDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(remoteAddress, updateDefaultPlatformName(desiredCapabilities, WINDOWS));
    }

    public WindowsDriver(URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(remoteAddress, httpClientFactory, updateDefaultPlatformName(desiredCapabilities, WINDOWS));
    }

    public WindowsDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        super(service, updateDefaultPlatformName(desiredCapabilities, WINDOWS));
    }

    public WindowsDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory,
                         Capabilities desiredCapabilities) {
        super(service, httpClientFactory, updateDefaultPlatformName(desiredCapabilities, WINDOWS));
    }

    public WindowsDriver(AppiumServiceBuilder builder, Capabilities desiredCapabilities) {
        super(builder, updateDefaultPlatformName(desiredCapabilities, WINDOWS));
    }

    public WindowsDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory,
                         Capabilities desiredCapabilities) {
        super(builder, httpClientFactory, updateDefaultPlatformName(desiredCapabilities, WINDOWS));
    }

    public WindowsDriver(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(httpClientFactory, updateDefaultPlatformName(desiredCapabilities, WINDOWS));
    }

    public WindowsDriver(Capabilities desiredCapabilities) {
        super(updateDefaultPlatformName(desiredCapabilities, WINDOWS));
    }
}
