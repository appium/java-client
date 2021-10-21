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

package io.appium.java_client.mac;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.HasSettings;
import io.appium.java_client.internal.CapabilityHelpers;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.http.HttpClient;

import java.net.URL;

import static io.appium.java_client.remote.MobilePlatform.MAC;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;

/**
 * Mac2Driver is an officially supported Appium driver
 * created to automate Mac OS apps. The driver uses W3C
 * WebDriver protocol and is built on top of Apple's XCTest
 * automation framework. Read https://github.com/appium/appium-mac2-driver
 * for more details on how to configure and use it.
 *
 * @since Appium 1.20.0
 */
public class Mac2Driver<T extends WebElement> extends AppiumDriver<T> implements CanRecordScreen, HasSettings {
    public Mac2Driver(HttpCommandExecutor executor, Capabilities capabilities) {
        super(executor, prepareCaps(capabilities));
    }

    public Mac2Driver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(remoteAddress, prepareCaps(desiredCapabilities));
    }

    public Mac2Driver(URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(remoteAddress, httpClientFactory, prepareCaps(desiredCapabilities));
    }

    public Mac2Driver(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        super(service, prepareCaps(desiredCapabilities));
    }

    public Mac2Driver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory,
                      Capabilities desiredCapabilities) {
        super(service, httpClientFactory, prepareCaps(desiredCapabilities));
    }

    public Mac2Driver(AppiumServiceBuilder builder, Capabilities desiredCapabilities) {
        super(builder, prepareCaps(desiredCapabilities));
    }

    public Mac2Driver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory,
                      Capabilities desiredCapabilities) {
        super(builder, httpClientFactory, prepareCaps(desiredCapabilities));
    }

    public Mac2Driver(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(httpClientFactory, prepareCaps(desiredCapabilities));
    }

    public Mac2Driver(Capabilities desiredCapabilities) {
        super(prepareCaps(desiredCapabilities));
    }

    private static Capabilities prepareCaps(Capabilities originalCaps) {
        DesiredCapabilities dc = new DesiredCapabilities(originalCaps);
        if (originalCaps.getCapability(PLATFORM_NAME) == null) {
            dc.setCapability(PLATFORM_NAME, MAC);
        }
        String automationName = CapabilityHelpers.getCapability(originalCaps,
                MobileCapabilityType.AUTOMATION_NAME, String.class);
        if (!AutomationName.MAC2.equalsIgnoreCase(automationName)) {
            dc.setCapability(CapabilityHelpers.APPIUM_PREFIX
                    + MobileCapabilityType.AUTOMATION_NAME, AutomationName.MAC2);
        }
        return dc;
    }
}
