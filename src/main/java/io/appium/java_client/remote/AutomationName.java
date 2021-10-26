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

public interface AutomationName {
    // Officially supported drivers
    @Deprecated
    String APPIUM = "Appium";
    @Deprecated
    String SELENDROID = "Selendroid";
    // https://github.com/appium/appium-xcuitest-driver
    String IOS_XCUI_TEST = "XCuiTest";
    // https://github.com/appium/appium-uiautomator2-driver
    String ANDROID_UIAUTOMATOR2 = "UIAutomator2";
    // https://github.com/appium/appium-espresso-driver
    String ESPRESSO = "Espresso";
    // https://github.com/appium/appium-mac2-driver
    String MAC2 = "Mac2";
    // https://github.com/appium/appium-windows-driver
    String WINDOWS = "Windows";
    // https://github.com/appium/appium-safari-driver
    String SAFARI = "Safari";
    // https://github.com/appium/appium-geckodriver
    String GECKO = "Gecko";

    // Third-party drivers
    // https://github.com/YOU-i-Labs/appium-youiengine-driver
    String YOUI_ENGINE = "youiengine";
}
