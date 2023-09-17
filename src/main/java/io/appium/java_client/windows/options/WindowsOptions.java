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
import io.appium.java_client.remote.options.SupportsPostrunOption;
import io.appium.java_client.remote.options.SupportsPrerunOption;
import org.openqa.selenium.Capabilities;

import java.util.Map;
import java.util.Optional;

/**
 * <a href="https://github.com/appium/appium-windows-driver#usage">https://github.com/appium/appium-windows-driver#usage</a>
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
        SupportsPrerunOption<WindowsOptions, PowerShellData>,
        SupportsPostrunOption<WindowsOptions, PowerShellData> {
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

    /**
     * An object containing either script or command key. The value of
     * each key must be a valid PowerShell script or command to be
     * executed prior to the WinAppDriver session startup.
     * See
     * <a href="https://github.com/appium/appium-windows-driver#power-shell-commands-execution">https://github.com/appium/appium-windows-driver#power-shell-commands-execution</a>
     * for more details.
     *
     * @param script E.g. {script: 'Get-Process outlook -ErrorAction SilentlyContinue'}.
     * @return self instance for chaining.
     */
    public WindowsOptions setPrerun(PowerShellData script) {
        return amend(PRERUN_OPTION, script.toMap());
    }

    /**
     * Get the prerun script.
     *
     * @return Prerun script.
     */
    public Optional<PowerShellData> getPrerun() {
        //noinspection unchecked
        return Optional.ofNullable(getCapability(PRERUN_OPTION))
                .map(v -> new PowerShellData((Map<String, Object>) v));
    }

    /**
     * An object containing either script or command key. The value of
     * each key must be a valid PowerShell script or command to be
     * executed after an WinAppDriver session is finished.
     * See
     * <a href="https://github.com/appium/appium-windows-driver#power-shell-commands-execution">https://github.com/appium/appium-windows-driver#power-shell-commands-execution</a>
     * for more details.
     *
     * @param script E.g. {script: 'Get-Process outlook -ErrorAction SilentlyContinue'}.
     * @return self instance for chaining.
     */
    public WindowsOptions setPostrun(PowerShellData script) {
        return amend(POSTRUN_OPTION, script.toMap());
    }

    /**
     * Get the postrun script.
     *
     * @return Postrun script.
     */
    public Optional<PowerShellData> getPostrun() {
        //noinspection unchecked
        return Optional.ofNullable(getCapability(POSTRUN_OPTION))
                .map(v -> new PowerShellData((Map<String, Object>) v));
    }
}
