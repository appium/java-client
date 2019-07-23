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

import org.openqa.seleniumone.Point;
import org.openqa.seleniumone.Rectangle;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FeaturesMatchingResult extends ComparisonResult {
    private static final String COUNT = "count";
    private static final String TOTAL_COUNT = "totalCount";
    private static final String POINTS1 = "points1";
    private static final String RECT1 = "rect1";
    private static final String POINTS2 = "points2";
    private static final String RECT2 = "rect2";

    public FeaturesMatchingResult(Map<String, Object> input) {
        super(input);
    }

    /**
     * Returns a count of matched edges on both images.
     *
     * @return The count of matched edges on both images.
     *     The more matching edges there are no both images the more similar they are.
     */
    public int getCount() {
        verifyPropertyPresence(COUNT);
        return ((Long) getCommandResult().get(COUNT)).intValue();
    }

    /**
     * Returns a count of matched edges on both images.
     *
     * @return The total count of matched edges on both images.
     *     It is equal to `count` if `goodMatchesFactor` does not limit the matches,
     *     otherwise it contains the total count of matches before `goodMatchesFactor` is
     *     applied.
     */
    public int getTotalCount() {
        verifyPropertyPresence(TOTAL_COUNT);
        return ((Long) getCommandResult().get(TOTAL_COUNT)).intValue();
    }

    /**
     * Returns a list of matching points on the first image.
     *
     * @return The list of matching points on the first image.
     */
    public List<Point> getPoints1() {
        verifyPropertyPresence(POINTS1);
        //noinspection unchecked
        return ((List<Map<String, Object>>) getCommandResult().get(POINTS1)).stream()
                .map(ComparisonResult::mapToPoint)
                .collect(Collectors.toList());
    }

    /**
     * Returns a rect for the `points1` list.
     *
     * @return The bounding rect for the `points1` list or a zero rect if not enough matching points were found.
     */
    public Rectangle getRect1() {
        verifyPropertyPresence(RECT1);
        //noinspection unchecked
        return mapToRect((Map<String, Object>) getCommandResult().get(RECT1));
    }

    /**
     * Returns a list of matching points on the second image.
     *
     * @return The list of matching points on the second image.
     */
    public List<Point> getPoints2() {
        verifyPropertyPresence(POINTS2);
        //noinspection unchecked
        return ((List<Map<String, Object>>) getCommandResult().get(POINTS2)).stream()
                .map(ComparisonResult::mapToPoint)
                .collect(Collectors.toList());
    }

    /**
     * Returns a rect for the `points2` list.
     *
     * @return The bounding rect for the `points2` list or a zero rect if not enough matching points were found.
     */
    public Rectangle getRect2() {
        verifyPropertyPresence(RECT2);
        //noinspection unchecked
        return mapToRect((Map<String, Object>) getCommandResult().get(RECT2));
    }
}
