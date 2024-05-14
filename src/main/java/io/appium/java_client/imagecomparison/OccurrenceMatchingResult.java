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

import lombok.Getter;
import org.openqa.selenium.Rectangle;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class OccurrenceMatchingResult extends ComparisonResult {
    private static final String RECT = "rect";
    private static final String SCORE = "score";

    private final boolean hasMultiple;

    public OccurrenceMatchingResult(Object input) {
        super(input);
        hasMultiple = input instanceof List;
    }

    private List<OccurrenceMatchingResult> getMultipleMatches(boolean throwIfEmpty) {
        if (!hasMultiple) {
            throw new IllegalStateException(String.format(
                    "This %s does not represent multiple matches. Did you set options properly?",
                    getClass().getSimpleName()
            ));
        }
        //noinspection unchecked
        var matches = ((List<Map<String, Object>>) commandResult).stream()
                .map(OccurrenceMatchingResult::new)
                .collect(Collectors.toList());
        if (matches.isEmpty() && throwIfEmpty) {
            throw new IllegalStateException("Zero matches have been found. Try the lookup with different options.");
        }
        return matches;
    }

    private OccurrenceMatchingResult getMatch(int index) {
        var matches = getMultipleMatches(true);
        if (index < 0 || index >= matches.size()) {
            throw new IndexOutOfBoundsException(String.format(
                    "The match #%s does not exist. The total number of found matches is %s",
                    index, matches.size()
            ));
        }
        return matches.get(index);
    }

    /**
     * Returns rectangle of the partial image occurrence.
     *
     * @return The region of the partial image occurrence on the full image.
     */
    public Rectangle getRect() {
        if (hasMultiple) {
            return getRect(0);
        }
        verifyPropertyPresence(RECT);
        //noinspection unchecked
        return mapToRect((Map<String, Object>) getResultAsMap().get(RECT));
    }

    /**
     * Returns rectangle of the partial image occurrence for the given match index.
     *
     * @param matchIndex Match index.
     * @return Matching rectangle.
     * @throws IllegalStateException If the current instance does not represent multiple matches.
     */
    public Rectangle getRect(int matchIndex) {
        return getMatch(matchIndex).getRect();
    }

    /**
     * Returns the score of the partial image occurrence.
     *
     * @return Matching score in range 0..1.
     */
    public double getScore() {
        if (hasMultiple) {
            return getScore(0);
        }
        verifyPropertyPresence(SCORE);
        var value = getResultAsMap().get(SCORE);
        if (value instanceof Long) {
            return ((Long) value).doubleValue();
        }
        return ((Double) value);
    }

    /**
     * Returns the score of the partial image occurrence for the given match index.
     *
     * @param matchIndex Match index.
     * @return Matching score in range 0..1.
     * @throws IllegalStateException If the current instance does not represent multiple matches.
     */
    public double getScore(int matchIndex) {
        return getMatch(matchIndex).getScore();
    }

    /**
     * Returns the visualization of the matching result.
     *
     * @return The visualization of the matching result represented as base64-encoded PNG image.
     */
    @Override
    public byte[] getVisualization() {
        return hasMultiple ? getVisualization(0) : super.getVisualization();
    }

    /**
     * Returns the visualization of the partial image occurrence for the given match index.
     *
     * @param matchIndex Match index.
     * @return The visualization of the matching result represented as base64-encoded PNG image.
     * @throws IllegalStateException If the current instance does not represent multiple matches.
     */
    public byte[] getVisualization(int matchIndex) {
        return getMatch(matchIndex).getVisualization();
    }

    /**
     * Stores visualization image into the given file.
     *
     * @param matchIndex Match index.
     * @param destination file to save image.
     * @throws IOException On file system I/O error.
     * @throws IllegalStateException If the current instance does not represent multiple matches.
     */
    public void storeVisualization(int matchIndex, File destination) throws IOException {
       getMatch(matchIndex).storeVisualization(destination);
    }

    /**
     * Returns the list of multiple matches (if any).
     * This property only works if the `multiple` option is enabled.
     *
     * @since Appium 1.21.0
     * @return The list containing properties of each single match or an empty list.
     * @throws IllegalStateException If the current instance does not represent multiple matches.
     */
    public List<OccurrenceMatchingResult> getMultiple() {
        return getMultipleMatches(false);
    }
}
