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

package io.appium.java_client.chromium.options;

import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.SupportsBrowserNameOption;
import org.openqa.selenium.Capabilities;

import java.util.Map;

/**
 * <p>Options class that sets options for Chromium when testing websites.</p>
 * <br>
 * @see <a href='https://github.com/appium/appium-chromium-driver#usage'>appium-chromium-driver usage section</a>
 */
public class ChromiumOptions extends BaseOptions<ChromiumOptions> implements
        SupportsBrowserNameOption<ChromiumOptions>,
        SupportsChromeDrivePortOption<ChromiumOptions>,
        SupportsExecutableOption<ChromiumOptions>,
        SupportsExecutableDirOption<ChromiumOptions>,
        SupportsVerboseOption<ChromiumOptions>,
        SupportsLogPathOption<ChromiumOptions>,
        SupportsBuildCheckOption<ChromiumOptions>,
        SupportsAutodownloadOption<ChromiumOptions>,
        SupportsUseSystemExecutableOption<ChromiumOptions> {
    public ChromiumOptions() {
        setCommonOptions();
    }

    public ChromiumOptions(Capabilities source) {
        super(source);
        setCommonOptions();
    }

    public ChromiumOptions(Map<String, ?> source) {
        super(source);
        setCommonOptions();
    }

    private void setCommonOptions() {
        setAutomationName(AutomationName.CHROMIUM);
    }
}
