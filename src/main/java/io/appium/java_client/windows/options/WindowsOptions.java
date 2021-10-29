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

package io.appium.java_client.windows.options;

import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.SupportsAppOption;
import org.openqa.selenium.Capabilities;

import java.util.Map;

/**
 * https://github.com/appium/appium-windows-driver#usage
 */
public class WindowsOptions extends BaseOptions<WindowsOptions> implements
        SupportsAppOption<WindowsOptions>,
        SupportsAppArgumentsOption<WindowsOptions>,
        SupportsAppTopLevelWindowOption<WindowsOptions>,
        SupportsAppWorkingDirOption<WindowsOptions>,
        SupportsCreateSessionTimeoutOption<WindowsOptions>,
        SupportsMsWaitForAppLaunchOption<WindowsOptions>,
        SupportsMsExperimentalWebDriverOption<WindowsOptions>,
        SupportsSystemPortOption<WindowsOptions>,
        SupportsPrerunOption<WindowsOptions>,
        SupportsPostrunOption<WindowsOptions> {
    public WindowsOptions() {
        setCommonOptions();
    }

    public WindowsOptions(Capabilities source) {
        super(source);
        setCommonOptions();
    }

    public WindowsOptions(Map<String, ?> source) {
        super(source);
        setCommonOptions();
    }

    private void setCommonOptions() {
        setPlatformName(MobilePlatform.WINDOWS);
        setAutomationName(AutomationName.WINDOWS);
    }
}
