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
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.SessionNotCreatedException;

import java.time.Duration;
import java.util.function.Supplier;

import static io.appium.java_client.TestUtils.resourcePathToLocalPath;

public class BaseIOSWebViewTest extends BaseIOSTest {
    private static final Duration WEB_VIEW_DETECT_INTERVAL = Duration.ofSeconds(1);
    private static final Duration WEB_VIEW_DETECT_DURATION = Duration.ofSeconds(15);

    @BeforeAll
    public static void beforeClass() {
        startAppiumServer();

        XCUITestOptions options = new XCUITestOptions()
                .setDeviceName(DEVICE_NAME)
                .setWdaLaunchTimeout(WDA_LAUNCH_TIMEOUT)
                .setCommandTimeouts(Duration.ofSeconds(240))
                .setApp(resourcePathToLocalPath("vodqa.zip").toAbsolutePath().toString());
        Supplier<IOSDriver> createDriver = () -> new IOSDriver(service.getUrl(), options);
        try {
            driver = createDriver.get();
        } catch (SessionNotCreatedException e) {
            // Sometimes WDA session creation freezes unexpectedly on CI:
            // https://dev.azure.com/srinivasansekar/java-client/_build/results?buildId=356&view=ms.vss-test-web.build-test-results-tab
            options.useNewWDA();
            driver = createDriver.get();
        }
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
            //noinspection BusyWait
            Thread.sleep(WEB_VIEW_DETECT_INTERVAL.toMillis());
        }
        throw new IllegalStateException(String.format("No web views have been detected within %sms timeout",
                WEB_VIEW_DETECT_DURATION.toMillis()));
    }
}
