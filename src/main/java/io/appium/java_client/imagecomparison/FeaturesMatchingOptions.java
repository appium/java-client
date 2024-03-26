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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Optional.ofNullable;

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
     * @param name the detector name for features matching.
     * @return self instance for chaining.
     */
    public FeaturesMatchingOptions withDetectorName(FeatureDetector name) {
        this.detectorName = name.name();
        return this;
    }

    /**
     * Sets the name of the matching function.
     * The default one is 'BruteForce'.
     *
     * @param name the name of the matching function.
     * @return self instance for chaining.
     */
    public FeaturesMatchingOptions withMatchFunc(MatchingFunction name) {
        this.matchFunc = name.toString();
        return this;
    }

    /**
     * Sets the maximum count of "good" matches (e. g. with minimal distances).
     *
     * @param factor the "good" matches factor
     * @return self instance for chaining.
     */
    public FeaturesMatchingOptions withGoodMatchesFactor(int factor) {
        checkArgument(factor > 1);
        this.goodMatchesFactor = factor;
        return this;
    }

    @Override
    public Map<String, Object> build() {
        final Map<String, Object> map = new HashMap<>(super.build());
        ofNullable(detectorName).ifPresent(x -> map.put("detectorName", x));
        ofNullable(matchFunc).ifPresent(x -> map.put("matchFunc", x));
        ofNullable(goodMatchesFactor).ifPresent(x -> map.put("goodMatchesFactor", x));
        return Collections.unmodifiableMap(map);
    }
}
