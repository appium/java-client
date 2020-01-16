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

import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import org.junit.BeforeClass;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;

public class BaseIOSWebViewTest extends BaseIOSTest {
    private static final Duration WEB_VIEW_DETECT_INTERVAL = Duration.ofSeconds(1);
    private static final Duration WEB_VIEW_DETECT_DURATION = Duration.ofSeconds(15);

    @BeforeClass
    public static void beforeClass() throws IOException {
        final String ip = startAppiumServer();

        if (service == null || !service.isRunning()) {
            throw new AppiumServerHasNotBeenStartedLocallyException("An appium server node is not started!");
        }

        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "vodqa.zip");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, PLATFORM_VERSION);
        //sometimes environment has performance problems
        capabilities.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 500000);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        driver = new IOSDriver<>(new URL("http://" + ip + ":" + PORT + "/wd/hub"), capabilities);
    }

    protected void findAndSwitchToWebView() throws InterruptedException {
        final long msStarted = System.currentTimeMillis();
        while (System.currentTimeMillis() - msStarted <= WEB_VIEW_DETECT_DURATION.toMillis()) {
            for (String handle : driver.getContextHandles()) {
                if (handle.contains("WEBVIEW")) {
                    driver.context(handle);
                    return;
                }
            }
            Thread.sleep(WEB_VIEW_DETECT_INTERVAL.toMillis());
        }
        throw new IllegalStateException(String.format("No web views have been detected within %sms timeout",
                WEB_VIEW_DETECT_DURATION.toMillis()));
    }
}
