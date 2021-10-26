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

package io.appium.java_client.ios.options.wda;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

public interface SupportsUpdatedWdaBundleIdOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String UPDATED_WDA_BUNDLE_ID_OPTION = "updatedWDABundleId";

    /**
     * Bundle id to update WDA to before building and launching on real devices.
     * This bundle id must be associated with a valid provisioning profile.
     *
     * @param identifier Bundle identifier.
     * @return self instance for chaining.
     */
    default T setUpdatedWdaBundleId(String identifier) {
        return amend(UPDATED_WDA_BUNDLE_ID_OPTION, identifier);
    }

    /**
     * Get the WDA bundle identifier.
     *
     * @return Identifier value.
     */
    default Optional<String> getUpdatedWdaBundleId() {
        return Optional.ofNullable((String) getCapability(UPDATED_WDA_BUNDLE_ID_OPTION));
    }
}
