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

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ScreenOrientation;

import java.util.Optional;

public interface SupportsOrientationOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String ORIENTATION_OPTION = "orientation";

    /**
     * Set the orientation of the screen.
     *
     * @param orientation is the screen orientation.
     * @return self instance for chaining.
     */
    default T setOrientation(ScreenOrientation orientation) {
        return amend(ORIENTATION_OPTION, orientation.name());
    }

    /**
     * Get the orientation of the screen.
     *
     * @return ScreenOrientation of the app.
     */
    default Optional<ScreenOrientation> getOrientation() {
        return Optional.ofNullable(getCapability(ORIENTATION_OPTION))
                .map(v -> v instanceof ScreenOrientation
                        ? (ScreenOrientation) v
                        : ScreenOrientation.valueOf((String.valueOf(v)).toUpperCase())
                );
    }
}
