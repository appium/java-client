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

package io.appium.java_client.ios.options.simulator;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

public interface SupportsSimulatorDevicesSetPathOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SIMULATOR_DEVICES_SET_PATH_OPTION = "simulatorDevicesSetPath";

    /**
     * This capability allows to set an alternative path to the simulator devices
     * set in case you have multiple sets deployed on your local system. Such
     * feature could be useful if you, for example, would like to save disk space
     * on the main system volume.
     *
     * @param path Alternative path to the simulator devices set.
     * @return self instance for chaining.
     */
    default T setSimulatorDevicesSetPath(String path) {
        return amend(SIMULATOR_DEVICES_SET_PATH_OPTION, path);
    }

    /**
     * Get the alternative path to the simulator devices set.
     *
     * @return Path string.
     */
    default Optional<String> getSimulatorDevicesSetPath() {
        return Optional.ofNullable((String) getCapability(SIMULATOR_DEVICES_SET_PATH_OPTION));
    }
}
