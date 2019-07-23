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

import org.openqa.seleniumone.Rectangle;

import java.util.Map;

public class OccurrenceMatchingResult extends ComparisonResult {
    private static final String RECT = "rect";

    public OccurrenceMatchingResult(Map<String, Object> input) {
        super(input);
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
}
