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

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Map;

public class TapOptions extends OptionsWithAbsolutePositioning<TapOptions> {
    private Integer tapsCount = null;

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
        if (tapsCount != null) {
            result.put("count", this.tapsCount);
        }
        return result;
    }
}
