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

import java.util.Map;

public class SimilarityMatchingResult extends ComparisonResult {
    private static final String SCORE = "score";

    public SimilarityMatchingResult(Map<String, Object> input) {
        super(input);
    }

    /**a
     * @return The similarity score as a float number in range [0.0, 1.0].
     *     1.0 is the highest score (means both images are totally equal).
     */
    public double getScore() {
        verifyPropertyPresence(SCORE);
        //noinspection unchecked
        return (double) getCommandResult().get(SCORE);
    }
}
