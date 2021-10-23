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

package io.appium.java_client.android.options;

import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.SupportsAppOption;
import io.appium.java_client.remote.options.SupportsAutoWebViewOption;
import io.appium.java_client.remote.options.SupportsClearSystemFilesOption;
import io.appium.java_client.remote.options.SupportsDeviceNameOption;
import io.appium.java_client.remote.options.SupportsEnablePerformanceLoggingOption;
import io.appium.java_client.remote.options.SupportsLanguageOption;
import io.appium.java_client.remote.options.SupportsLocaleOption;
import io.appium.java_client.remote.options.SupportsOrientationOption;
import io.appium.java_client.remote.options.SupportsOtherAppsOption;
import io.appium.java_client.remote.options.SupportsUdidOption;
import org.openqa.selenium.Capabilities;

public class UiAutomator2Options extends BaseOptions<UiAutomator2Options> implements
        SupportsAppOption<UiAutomator2Options>, SupportsAutoWebViewOption<UiAutomator2Options>,
        SupportsClearSystemFilesOption<UiAutomator2Options>, SupportsDeviceNameOption<UiAutomator2Options>,
        SupportsEnablePerformanceLoggingOption<UiAutomator2Options>, SupportsLanguageOption<UiAutomator2Options>,
        SupportsLocaleOption<UiAutomator2Options>, SupportsOrientationOption<UiAutomator2Options>,
        SupportsOtherAppsOption<UiAutomator2Options>, SupportsUdidOption<UiAutomator2Options> {
    public UiAutomator2Options() {
        setCommonOptions();
    }

    public UiAutomator2Options(Capabilities source) {
        super(source);
        setCommonOptions();
    }

    private void setCommonOptions() {
        setPlatformName(MobilePlatform.ANDROID);
        setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2);
    }
}
