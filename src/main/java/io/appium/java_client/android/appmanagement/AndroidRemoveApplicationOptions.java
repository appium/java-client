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

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.appmanagement.BaseRemoveApplicationOptions;

import java.time.Duration;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.ofNullable;

public class AndroidRemoveApplicationOptions extends
        BaseRemoveApplicationOptions<AndroidRemoveApplicationOptions> {
    private Duration timeout;
    private Boolean keepData;

    /**
     * The time to wait until the app is removed (20000ms by default).
     *
     * @param timeout the actual timeout value. The minimum time resolution
     *                unit is one millisecond.
     * @return self instance for chaining.
     */
    public AndroidRemoveApplicationOptions withTimeout(Duration timeout) {
        checkArgument(!checkNotNull(timeout).isNegative(),
                "The timeout value cannot be negative");
        this.timeout = timeout;
        return this;
    }

    /**
     * Forces uninstall to keep the application data and caches.
     *
     * @return self instance for chaining.
     */
    public AndroidRemoveApplicationOptions withKeepDataEnabled() {
        this.keepData = true;
        return this;
    }

    /**
     * Forces uninstall to delete the application data and caches
     * (the default behavior).
     *
     * @return self instance for chaining.
     */
    public AndroidRemoveApplicationOptions withKeepDataDisabled() {
        this.keepData = false;
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> build() {
        final ImmutableMap.Builder builder = new ImmutableMap.Builder<String, Object>();
        ofNullable(timeout).map(x -> builder.put("timeout", x.toMillis()));
        ofNullable(keepData).map(x -> builder.put("keepData", x));
        return builder.build();
    }
}
