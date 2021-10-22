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
import org.openqa.selenium.ScreenOrientation;

public interface SupportsOrientationOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability {

    /**
     * Set the orientation of the screen.
     *
     * @param orientation is the screen orientation.
     * @return this MobileOptions, for chaining.
     * @see MobileCapabilityType#ORIENTATION
     */
    default T setOrientation(ScreenOrientation orientation) {
        setCapability(MobileCapabilityType.ORIENTATION, orientation);
        //noinspection unchecked
        return (T) this;
    }

    /**
     * Get the orientation of the screen.
     *
     * @return ScreenOrientation of the app.
     * @see MobileCapabilityType#ORIENTATION
     */
    default ScreenOrientation getOrientation() {
        return (ScreenOrientation) getCapability(MobileCapabilityType.ORIENTATION);
    }
}
