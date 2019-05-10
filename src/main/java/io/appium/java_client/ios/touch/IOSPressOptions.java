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

package io.appium.java_client.ios.touch;

import static java.util.Optional.ofNullable;

import io.appium.java_client.touch.offset.AbstractOptionCombinedWithPosition;

import java.util.Map;

public class IOSPressOptions extends AbstractOptionCombinedWithPosition<IOSPressOptions> {
    private Double pressure = null;

    /**
     * It creates an empty instance of {@link IOSPressOptions}.
     *
     * @return an empty instance of {@link IOSPressOptions}
     */
    public static IOSPressOptions iosPressOptions() {
        return new IOSPressOptions();
    }

    /**
     * Set the pressure value. This allows to simulate force/3D touch on
     * devices, that support it.
     *
     * @param pressure the value to set.
     *                 See the description of `force` property on Apple's UITouch class
     *                 (https://developer.apple.com/documentation/uikit/uitouch?language=objc)
     *                 for more details on possible value ranges.
     *
     * @return this instance for chaining.
     */
    public IOSPressOptions withPressure(double pressure) {
        this.pressure = pressure;
        return this;
    }

    @Override
    public Map<String, Object> build() {
        final Map<String, Object> result = super.build();
        ofNullable(pressure).ifPresent(x -> result.put("pressure", x));
        return result;
    }
}
