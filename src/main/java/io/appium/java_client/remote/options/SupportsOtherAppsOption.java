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

package io.appium.java_client.remote.options;

import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Capabilities;

public interface SupportsOtherAppsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability {


    /**
     * Set the location of the app(s) to install before running a test.
     *
     * @param apps is the apps to install.
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#OTHER_APPS
     */
    default T setOtherApps(String apps) {
        setCapability(MobileCapabilityType.OTHER_APPS, apps);
        //noinspection unchecked
        return (T) this;
    }

    /**
     * Get the list of apps to install before running a test.
     *
     * @return String of apps to install.
     * @see MobileCapabilityType#OTHER_APPS
     */
    default String getOtherApps() {
        return (String) getCapability(MobileCapabilityType.OTHER_APPS);
    }
}
