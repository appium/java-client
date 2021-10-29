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

import java.util.List;
import java.util.Optional;

public interface SupportsChromedriverArgsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String CHROMEDRIVER_ARGS_OPTION = "chromedriverArgs";

    /**
     * Array of chromedriver [command line
     * arguments](http://www.assertselenium.com/java/list-of-chrome-driver-command-line-arguments/).
     * Note, that not all command line arguments that are available for the desktop
     * browser are also available for the mobile one.
     *
     * @param args Chromedriver command line arguments.
     * @return self instance for chaining.
     */
    default T setChromedriverArgs(List<String> args) {
        return amend(CHROMEDRIVER_ARGS_OPTION, args);
    }

    /**
     * Get the array of chromedriver CLI arguments.
     *
     * @return Arguments list.
     */
    default Optional<List<String>> getChromedriverArgs() {
        //noinspection unchecked
        return Optional.ofNullable((List<String>) getCapability(CHROMEDRIVER_ARGS_OPTION));
    }
}
