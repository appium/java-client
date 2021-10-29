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

package io.appium.java_client.android.options.app;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

public interface SupportsAppActivityOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String APP_ACTIVITY_OPTION = "appActivity";

    /**
     * Main application activity identifier. If not provided then UiAutomator2
     * will try to detect it automatically from the package provided by the app capability.
     *
     * @param appActivity Fully qualified app activity class name.
     * @return self instance for chaining.
     */
    default T setAppActivity(String appActivity) {
        return amend(APP_ACTIVITY_OPTION, appActivity);
    }

    /**
     * Get the name of the main app activity.
     *
     * @return Activity class name.
     */
    default Optional<String> getAppActivity() {
        return Optional.ofNullable((String) getCapability(APP_ACTIVITY_OPTION));
    }
}
