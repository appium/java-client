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

public interface SupportsUdidOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability {
    /**
     * Set the id of the device.
     *
     * @param id is the unique device identifier.
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#UDID
     */
    default T setUdid(String id) {
        setCapability(MobileCapabilityType.UDID, id);
        //noinspection unchecked
        return (T) this;
    }

    /**
     * Get the id of the device.
     *
     * @return String representing the unique device identifier.
     * @see MobileCapabilityType#UDID
     */
    default String getUdid() {
        return (String) getCapability(MobileCapabilityType.UDID);
    }
}
