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

import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.remote.MobileBrowserType;
import org.junit.jupiter.api.BeforeAll;

import java.time.Duration;

public class BaseSafariTest extends BaseIOSTest {
    private static final Duration WEBVIEW_CONNECT_TIMEOUT = Duration.ofSeconds(30);

    @BeforeAll public static void beforeClass() {
        startAppiumServer();

        XCUITestOptions options = new XCUITestOptions()
                .withBrowserName(MobileBrowserType.SAFARI)
                .setDeviceName(DEVICE_NAME)
                .setPlatformVersion(PLATFORM_VERSION)
                .setWebviewConnectTimeout(WEBVIEW_CONNECT_TIMEOUT)
                .setWdaLaunchTimeout(WDA_LAUNCH_TIMEOUT);
        driver = new IOSDriver(service.getUrl(), options);
    }
}
