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

public class OccurrenceMatchingOptions extends BaseComparisonOptions<OccurrenceMatchingOptions> {
    private Double threshold;
    private Boolean multiple;
    private Integer matchNeighbourThreshold;

    /**
     * At what normalized threshold to reject an occurrence.
     *
     * @param threshold value in range 0..1. 0.5 is the default value.
     * @return self instance for chaining.
     */
    public OccurrenceMatchingOptions withThreshold(double threshold) {
        this.threshold = threshold;
        return this;
    }

    /**
     * Whether to enable the support of multiple image occurrences.
     *
     * @since Appium 1.21.0
     * @return self instance for chaining.
     */
    public OccurrenceMatchingOptions enableMultiple() {
        this.multiple = true;
        return this;
    }

    /**
     * The pixel distance between matches we consider
     * to be part of the same template match. This option is only
     * considered if multiple matches mode is enabled.
     * 10 pixels by default.
     *
     * @since Appium 1.21.0
     * @param threshold The threshold value in pixels.
     * @return self instance for chaining.
     */
    public OccurrenceMatchingOptions withMatchNeighbourThreshold(int threshold) {
        this.matchNeighbourThreshold = threshold;
        return this;
    }

    @Override
    public Map<String, Object> build() {
        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        builder.putAll(super.build());
        ofNullable(threshold).ifPresent(x -> builder.put("threshold", x));
        ofNullable(matchNeighbourThreshold).ifPresent(x -> builder.put("matchNeighbourThreshold", x));
        ofNullable(multiple).ifPresent(x -> builder.put("multiple", x));
        return builder.build();
    }
}
