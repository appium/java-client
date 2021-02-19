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

import org.openqa.selenium.Rectangle;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OccurrenceMatchingResult extends ComparisonResult {
    private static final String RECT = "rect";
    private static final String MULTIPLE = "multiple";

    private final boolean isAtRoot;

    public OccurrenceMatchingResult(Map<String, Object> input) {
        this(input, true);
    }

    private OccurrenceMatchingResult(Map<String, Object> input, boolean isAtRoot) {
        super(input);
        this.isAtRoot = isAtRoot;
    }

    /**
     * Returns rectangle of partial image occurrence.
     *
     * @return The region of the partial image occurrence on the full image.
     */
    public Rectangle getRect() {
        verifyPropertyPresence(RECT);
        //noinspection unchecked
        return mapToRect((Map<String, Object>) getCommandResult().get(RECT));
    }

    /**
     * Returns the list of multiple matches (if any).
     * This property only works if the `multiple` option is enabled.
     *
     * @since Appium 1.21.0
     * @return The list containing properties of each single match or an empty list.
     * @throws IllegalStateException If the accessor is called on a non-root match instance.
     */
    public List<OccurrenceMatchingResult> getMultiple() {
        if (!isAtRoot) {
            throw new IllegalStateException("Only the root match could contain multiple submatches");
        }
        verifyPropertyPresence(MULTIPLE);

        //noinspection unchecked
        List<Map<String, Object>> multiple = (List<Map<String, Object>>) getCommandResult().get(MULTIPLE);
        return multiple.stream()
                .map((m) -> new OccurrenceMatchingResult(m, false))
                .collect(Collectors.toList());
    }
}
