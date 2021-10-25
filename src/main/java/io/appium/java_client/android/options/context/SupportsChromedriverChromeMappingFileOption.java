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

package io.appium.java_client.android.options.context;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

public interface SupportsChromedriverChromeMappingFileOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String CHROMEDRIVER_CHROME_MAPPING_FILE_OPTION = "chromedriverChromeMappingFile";

    /**
     * Full path to the chromedrivers mapping file. This file is used to statically
     * map webview/browser versions to the chromedriver versions that are capable
     * of automating them. Read [Automatic Chromedriver Discovery](https://github.com/
     * appium/appium/blob/master/docs/en/writing-running-appium/web/
     * chromedriver.md#automatic-discovery-of-compatible-chromedriver)
     * article for more details.
     *
     * @param path Path to chromedrivers mapping file.
     * @return self instance for chaining.
     */
    default T setChromedriverChromeMappingFile(String path) {
        return amend(CHROMEDRIVER_CHROME_MAPPING_FILE_OPTION, path);
    }

    /**
     * Get full path to the chromedrivers mapping file is located.
     *
     * @return Path to chromedrivers mapping file.
     */
    default Optional<String> getChromedriverChromeMappingFile() {
        return Optional.ofNullable((String) getCapability(CHROMEDRIVER_CHROME_MAPPING_FILE_OPTION));
    }
}
