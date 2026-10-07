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

package io.appium.java_client.remote;

/**
 * The names of the automation backends (drivers) supported by Appium.
 */
public interface AutomationName {
    // Officially supported drivers
    /** The automation name of the <a href="https://github.com/appium/appium-xcuitest-driver">XCUITest driver for iOS and tvOS</a>. */
    String IOS_XCUI_TEST = "XCuiTest";
    /** The automation name of the <a href="https://github.com/appium/appium-uiautomator2-driver">UiAutomator2 driver for Android</a>. */
    String ANDROID_UIAUTOMATOR2 = "UIAutomator2";
    /** The automation name of the <a href="https://github.com/appium/appium-espresso-driver">Espresso driver for Android</a>. */
    String ESPRESSO = "Espresso";
    /** The automation name of the <a href="https://github.com/appium/appium-mac2-driver">Mac2 driver for macOS</a>. */
    String MAC2 = "Mac2";
    /** The automation name of the <a href="https://github.com/appium/appium-windows-driver">Windows driver</a>. */
    String WINDOWS = "Windows";
    /** The automation name of the <a href="https://github.com/appium/appium-safari-driver">Safari driver</a>. */
    String SAFARI = "Safari";
    /** The automation name of the <a href="https://github.com/appium/appium-geckodriver">Gecko driver for Firefox</a>. */
    String GECKO = "Gecko";
    /** The automation name of the <a href="https://github.com/appium/appium-chromium-driver">Chromium driver</a>. */
    String CHROMIUM = "Chromium";

    // Third-party drivers
    /** The automation name of the <a href="https://github.com/YOU-i-Labs/appium-youiengine-driver">YouiEngine driver</a>. */
    String YOUI_ENGINE = "youiengine";
    /** The automation name of the <a href="https://github.com/AppiumTestDistribution/appium-flutter-integration-driver">Flutter Integration driver</a>. */
    String FLUTTER_INTEGRATION = "FlutterIntegration";
}
