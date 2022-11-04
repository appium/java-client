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

package io.appium.java_client.touch;

import io.appium.java_client.touch.offset.AbstractOptionCombinedWithPosition;

import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Optional.ofNullable;

public class TapOptions extends AbstractOptionCombinedWithPosition<TapOptions> {
    private Integer tapsCount = null;

    /**
     * It creates an empty instance of {@link TapOptions}.
     *
     * @return the empty instance of {@link TapOptions}
     */
    public static TapOptions tapOptions() {
        return new TapOptions();
    }

    /**
     * Set the count of taps to perform.
     *
     * @param tapsCount the taps count to perform.
     *                  The value should be greater than zero.
     * @return this instance for chaining.
     */
    public TapOptions withTapsCount(int tapsCount) {
        checkArgument(tapsCount > 0, "Taps count should be greater than zero");
        this.tapsCount = tapsCount;
        return this;
    }

    @Override
    public Map<String, Object> build() {
        final Map<String, Object> result = super.build();
        ofNullable(tapsCount).ifPresent(integer -> result.put("count", integer));
        return result;
    }
}
