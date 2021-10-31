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
import io.appium.java_client.HidesKeyboardWithKeyName;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.http.HttpClient;

import java.net.URL;

public class WindowsDriver extends AppiumDriver implements
        PressesKeyCode,
        HidesKeyboardWithKeyName,
        CanRecordScreen {
    private static final String PLATFORM_NAME = Platform.WINDOWS.name();

    public WindowsDriver(HttpCommandExecutor executor, Capabilities capabilities) {
        super(executor, ensurePlatformName(capabilities, WINDOWS));
    }

    public WindowsDriver(URL remoteAddress, Capabilities capabilities) {
        super(remoteAddress, ensurePlatformName(capabilities, WINDOWS));
    }

    public WindowsDriver(URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(remoteAddress, httpClientFactory, ensurePlatformName(capabilities, WINDOWS));
    }

    public WindowsDriver(AppiumDriverLocalService service, Capabilities capabilities) {
        super(service, ensurePlatformName(capabilities, WINDOWS));
    }

    public WindowsDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory,
                         Capabilities capabilities) {
        super(service, httpClientFactory, ensurePlatformName(capabilities, WINDOWS));
    }

    public WindowsDriver(AppiumServiceBuilder builder, Capabilities capabilities) {
        super(builder, ensurePlatformName(capabilities, WINDOWS));
    }

    public WindowsDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory,
                         Capabilities capabilities) {
        super(builder, httpClientFactory, ensurePlatformName(capabilities, WINDOWS));
    }

    public WindowsDriver(HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(httpClientFactory, ensurePlatformName(capabilities, WINDOWS));
    }

    public WindowsDriver(Capabilities capabilities) {
        super(ensurePlatformName(capabilities, WINDOWS));
    }
}
