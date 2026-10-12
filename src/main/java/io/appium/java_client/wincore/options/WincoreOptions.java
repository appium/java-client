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

package io.appium.java_client.wincore.options;

import io.appium.java_client.android.options.context.SupportsWebviewDevtoolsPortOption;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.SupportsAppOption;
import io.appium.java_client.remote.options.SupportsDeviceNameOption;
import io.appium.java_client.remote.options.SupportsPostrunOption;
import io.appium.java_client.remote.options.SupportsPrerunOption;
import io.appium.java_client.windows.options.PowerShellData;
import io.appium.java_client.windows.options.SupportsAppArgumentsOption;
import io.appium.java_client.windows.options.SupportsAppTopLevelWindowOption;
import io.appium.java_client.windows.options.SupportsAppWorkingDirOption;
import io.appium.java_client.windows.options.SupportsMsExperimentalWebDriverOption;
import org.openqa.selenium.Capabilities;

import java.util.Map;
import java.util.Optional;

/**
 * Capabilities of the Wincore driver.
 * See <a href="https://github.com/appium/appium-wincore-driver#capabilities">https://github.com/appium/appium-wincore-driver#capabilities</a>
 * for more details.
 *
 * <p>The driver replaces appium-windows-driver, which is deprecated and not supported since Appium 4, see
 * {@link io.appium.java_client.windows.options.WindowsOptions}.</p>
 */
public class WincoreOptions extends BaseOptions<WincoreOptions> implements
        SupportsAppOption<WincoreOptions>,
        SupportsDeviceNameOption<WincoreOptions>,
        SupportsAppArgumentsOption<WincoreOptions>,
        SupportsAppTopLevelWindowOption<WincoreOptions>,
        SupportsAppWorkingDirOption<WincoreOptions>,
        SupportsWaitForAppLaunchOption<WincoreOptions>,
        SupportsMsExperimentalWebDriverOption<WincoreOptions>,
        SupportsMsForceQuitOption<WincoreOptions>,
        SupportsShouldCloseAppOption<WincoreOptions>,
        SupportsIsolatedScriptExecutionOption<WincoreOptions>,
        SupportsSmoothPointerMoveOption<WincoreOptions>,
        SupportsDelayBeforeClickOption<WincoreOptions>,
        SupportsDelayAfterClickOption<WincoreOptions>,
        SupportsWebviewEnabledOption<WincoreOptions>,
        SupportsWebviewDevtoolsPortOption<WincoreOptions>,
        SupportsChromedriverExecutablePathOption<WincoreOptions>,
        SupportsEdgedriverExecutablePathOption<WincoreOptions>,
        SupportsChromedriverCdnUrlOption<WincoreOptions>,
        SupportsEdgedriverCdnUrlOption<WincoreOptions>,
        SupportsFfmpegExecutablePathOption<WincoreOptions>,
        SupportsLogFileOption<WincoreOptions>,
        SupportsPerfMetricsOption<WincoreOptions>,
        SupportsPrerunOption<WincoreOptions, PowerShellData>,
        SupportsPostrunOption<WincoreOptions, PowerShellData> {
    /** Creates options with the default capabilities set. */
    public WincoreOptions() {
        setCommonOptions();
    }

    /**
     * Creates options from the given capabilities.
     *
     * @param source The capabilities to copy.
     */
    public WincoreOptions(Capabilities source) {
        super(source);
        setCommonOptions();
    }

    /**
     * Creates options from the given capabilities map.
     *
     * @param source The capabilities to copy.
     */
    public WincoreOptions(Map<String, ?> source) {
        super(source);
        setCommonOptions();
    }

    private void setCommonOptions() {
        setPlatformName(MobilePlatform.WINDOWS);
        setAutomationName(AutomationName.WINCORE);
    }

    /**
     * An object containing either script or command key. The value of
     * each key must be a valid PowerShell script or command to be
     * executed prior to the session startup.
     *
     * @param script E.g. {script: 'Get-Process outlook -ErrorAction SilentlyContinue'}.
     * @return self instance for chaining.
     */
    public WincoreOptions setPrerun(PowerShellData script) {
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
     * executed after the session is finished.
     *
     * @param script E.g. {script: 'Get-Process outlook -ErrorAction SilentlyContinue'}.
     * @return self instance for chaining.
     */
    public WincoreOptions setPostrun(PowerShellData script) {
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
