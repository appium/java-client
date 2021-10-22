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

/**
 * Use the specific options class for your driver,
 * for example XCUITestOptions or UiAutomator2Options.
 *
 * @param <T> The child class for a proper chaining.
 */
@Deprecated
public class MobileOptions<T extends MobileOptions<T>> extends BaseOptions<T>
        implements SupportsAppOption<T>, SupportsAutoWebViewOption<T>,
        SupportsClearSystemFilesOption<T>, SupportsDeviceNameOption<T>,
        SupportsEnablePerformanceLoggingOption<T>, SupportsLanguageOption<T>,
        SupportsLocaleOption<T>, SupportsOrientationOption<T>, SupportsOtherAppsOption<T>,
        SupportsUdidOption<T> {

    /**
     * Creates new instance with no preset capabilities.
     */
    public MobileOptions() {
    }

    /**
     * Creates new instance with provided capabilities capabilities.
     *
     * @param source is Capabilities instance to merge into new instance
     */
    public MobileOptions(Capabilities source) {
        super(source);
    }
}
