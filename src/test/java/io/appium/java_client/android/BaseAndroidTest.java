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

package io.appium.java_client.android;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static io.appium.java_client.TestResources.apiDemosApk;

@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public class BaseAndroidTest {
    public static final String APP_ID = "io.appium.android.apis";
    protected static final int PORT = 4723;

    private static AppiumDriverLocalService service;
    protected static AndroidDriver driver;

    /**
     * initialization.
     */
    @BeforeAll public static void beforeClass() {
        service = new AppiumServiceBuilder()
                .withIPAddress("127.0.0.1")
                .usingPort(PORT)
                .build();
        service.start();

        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("Android Emulator")
                .setApp(apiDemosApk().toAbsolutePath().toString())
                .eventTimings();
        driver = new AndroidDriver(service.getUrl(), options);
    }

    /**
     * finishing.
     */
    @AfterAll public static void afterClass() {
        if (driver != null) {
            driver.quit();
        }
        if (service != null) {
            service.stop();
        }
    }

    public static void startActivity(String name) {
        driver.executeScript(
                "mobile: startActivity",
                ImmutableMap.of(
                        "component", String.format("%s/%s", APP_ID, name)
                )
        );
    }
}
