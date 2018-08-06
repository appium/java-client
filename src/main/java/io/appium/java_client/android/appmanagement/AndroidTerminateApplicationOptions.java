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

package io.appium.java_client.android.appmanagement;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.ofNullable;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.appmanagement.BaseTerminateApplicationOptions;

import java.time.Duration;
import java.util.Map;

public class AndroidTerminateApplicationOptions extends
        BaseTerminateApplicationOptions<AndroidTerminateApplicationOptions> {
    private Duration timeout;

    /**
     * The time to wait until the app is terminated (500ms by default).
     *
     * @param timeout the actual timeout value. The minimum time resolution
     *                unit is one millisecond.
     * @return self instance for chaining.
     */
    public AndroidTerminateApplicationOptions withTimeout(Duration timeout) {
        checkArgument(!checkNotNull(timeout).isNegative(), "The timeout value cannot be negative");
        this.timeout = timeout;
        return this;
    }

    @Override
    public Map<String, Object> build() {
        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        ofNullable(timeout).map(x -> builder.put("timeout", x.toMillis()));
        return builder.build();
    }
}
