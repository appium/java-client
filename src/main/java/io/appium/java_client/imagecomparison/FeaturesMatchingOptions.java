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

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Optional.ofNullable;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class FeaturesMatchingOptions extends BaseComparisonOptions<FeaturesMatchingOptions> {
    private String detectorName;
    private String matchFunc;
    private Integer goodMatchesFactor;

    /**
     * Sets the detector name for features matching
     * algorithm. Some of these detectors (FAST, AGAST, GFTT, FAST, SIFT and MSER) are not available
     * in the default OpenCV installation and have to be enabled manually before
     * library compilation. The default detector name is 'ORB'.
     *
     * @return self instance for chaining.
     */
    public FeaturesMatchingOptions withDetectorName(FeatureDetector name) {
        this.detectorName = name.name();
        return this;
    }

    /**
     * The name of the matching function.
     * The default one is 'BruteForce'.
     *
     * @return self instance for chaining.
     */
    public FeaturesMatchingOptions withMatchFunc(MatchingFunction name) {
        this.matchFunc = name.toString();
        return this;
    }

    /**
     * The maximum count of "good" matches (e. g. with minimal distances).
     *
     * @return self instance for chaining.
     */
    public FeaturesMatchingOptions withGoodMatchesFactor(int factor) {
        checkArgument(factor > 1);
        this.goodMatchesFactor = factor;
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> build() {
        final ImmutableMap.Builder builder = new ImmutableMap.Builder<String, Object>();
        builder.putAll(super.build());
        ofNullable(detectorName).map(x -> builder.put("detectorName", x));
        ofNullable(matchFunc).map(x -> builder.put("matchFunc", x));
        ofNullable(goodMatchesFactor).map(x -> builder.put("goodMatchesFactor", x));
        return builder.build();
    }
}
