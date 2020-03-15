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

package io.appium.java_client.screenrecording;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.ofNullable;

import com.google.common.collect.ImmutableMap;

import java.time.Duration;
import java.util.Map;

public abstract class BaseStartScreenRecordingOptions<T extends BaseStartScreenRecordingOptions<T>>
        extends BaseScreenRecordingOptions<BaseStartScreenRecordingOptions<T>> {
    protected Boolean forceRestart;
    private Duration timeLimit;

    /**
     * The maximum recording time.
     *
     * @param timeLimit The actual time limit of the recorded video.
     * @return self instance for chaining.
     */
    public T withTimeLimit(Duration timeLimit) {
        this.timeLimit = checkNotNull(timeLimit);
        //noinspection unchecked
        return (T) this;
    }

    /**
     * Whether to ignore the result of previous capture and start a new recording
     * immediately.
     *
     * @return self instance for chaining.
     */
    public T enableForcedRestart() {
        this.forceRestart = true;
        //noinspection unchecked
        return (T) this;
    }

    @Override
    public Map<String, Object> build() {
        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        builder.putAll(super.build());
        ofNullable(timeLimit).map(x -> builder.put("timeLimit", x.getSeconds()));
        ofNullable(forceRestart).map(x -> builder.put("forceRestart", x));
        return builder.build();
    }
}
