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

package io.appium.java_client.imagecomparison;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

import static java.util.Optional.ofNullable;

public abstract class BaseComparisonOptions<T extends BaseComparisonOptions<T>> {
    private Boolean visualize;

    /**
     * Makes the endpoint to return an image,
     * which contains the visualized result of the corresponding
     * picture matching operation. This option is disabled by default.
     *
     * @return self instance for chaining
     */
    public T withEnabledVisualization() {
        visualize = true;
        //noinspection unchecked
        return (T) this;
    }

    /**
     * Builds a map, which is ready to be passed to the subordinated
     * Appium API.
     *
     * @return comparison options mapping.
     */
    public Map<String, Object> build() {
        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        ofNullable(visualize).map(x -> builder.put("visualize", x));
        return builder.build();
    }
}
