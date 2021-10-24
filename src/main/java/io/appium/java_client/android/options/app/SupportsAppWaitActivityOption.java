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

public interface SupportsAppWaitActivityOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String APP_WAIT_ACTIVITY_OPTION = "appWaitActivity";

    /**
     * Identifier of the activity that the driver should wait for
     * (not necessarily the main one).
     * If not provided then defaults to appium:appActivity.
     *
     * @param appWaitActivity Fully qualified app activity class name.
     * @return self instance for chaining.
     */
    default T setAppWaitActivity(String appWaitActivity) {
        return amend(APP_WAIT_ACTIVITY_OPTION, appWaitActivity);
    }

    /**
     * Get the name of the app activity to wait for.
     *
     * @return Activity class name.
     */
    default Optional<String> getAppWaitActivity() {
        return Optional.ofNullable((String) getCapability(APP_WAIT_ACTIVITY_OPTION));
    }
}
