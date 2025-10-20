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
import org.openqa.selenium.By;
import org.openqa.selenium.SessionNotCreatedException;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static io.appium.java_client.AppiumBy.accessibilityId;
import static io.appium.java_client.AppiumBy.iOSClassChain;
import static io.appium.java_client.AppiumBy.iOSNsPredicateString;
import static io.appium.java_client.utils.TestUtils.IOS_SIM_VODQA_RELEASE_URL;

public class AppIOSTest extends BaseIOSTest {
    protected static final String BUNDLE_ID = "org.reactjs.native.example.VodQAReactNative";
    protected static final By LOGIN_LINK_ID = accessibilityId("login");
    protected static final By USERNAME_EDIT_PREDICATE = iOSNsPredicateString("name == \"username\"");
    protected static final By PASSWORD_EDIT_PREDICATE = iOSNsPredicateString("name == \"password\"");
    protected static final By SLIDER_MENU_ITEM_PREDICATE = iOSNsPredicateString("name == \"slider1\"");
    protected static final By VODQA_LOGO_CLASS_CHAIN = iOSClassChain(
            "**/XCUIElementTypeImage[`name CONTAINS \"vodqa\"`]"
    );

    @BeforeAll
    public static void beforeClass() throws MalformedURLException {
        startAppiumServer();

        XCUITestOptions options = new XCUITestOptions()
                .setPlatformVersion(PLATFORM_VERSION)
                .setDeviceName(DEVICE_NAME)
                .setCommandTimeouts(Duration.ofSeconds(240))
                .setApp(new URL(IOS_SIM_VODQA_RELEASE_URL))
                .enableBiDi()
                .setWdaLaunchTimeout(WDA_LAUNCH_TIMEOUT);
        if (PREBUILT_WDA_PATH != null) {
            options.usePreinstalledWda().setPrebuiltWdaPath(PREBUILT_WDA_PATH);
        }
        try {
            driver = new IOSDriver(service.getUrl(), options);
        } catch (SessionNotCreatedException e) {
            options.useNewWDA();
            driver = new IOSDriver(service.getUrl(), options);
        }
    }
}
