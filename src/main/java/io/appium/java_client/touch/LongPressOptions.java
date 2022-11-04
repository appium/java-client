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

import java.time.Duration;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.ofNullable;

public class LongPressOptions extends AbstractOptionCombinedWithPosition<LongPressOptions> {
    protected Duration duration = null;

    /**
     * It creates an empty instance of {@link LongPressOptions}.
     *
     * @return an empty instance of {@link LongPressOptions}
     */
    public static LongPressOptions longPressOptions() {
        return new LongPressOptions();
    }

    /**
     * Set the long press duration.
     *
     * @param duration the value to set.
     *                 Time resolution unit is 1 ms.
     * @return this instance for chaining.
     */
    public LongPressOptions withDuration(Duration duration) {
        checkNotNull(duration);
        checkArgument(duration.toMillis() >= 0,
                "Duration value should be greater or equal to zero");
        this.duration = duration;
        return this;
    }

    @Override
    public Map<String, Object> build() {
        final Map<String, Object> result = super.build();
        ofNullable(duration).ifPresent(durationParam ->
                result.put("duration", durationParam.toMillis()));
        return result;
    }
}
