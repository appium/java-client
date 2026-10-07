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

/**
 * Image comparison modes supported by the server.
 */
public enum ComparisonMode {
    /** Matches features of the images. */
    MATCH_FEATURES("matchFeatures"),
    /** Calculates the similarity score of the images. */
    GET_SIMILARITY("getSimilarity"),
    /** Finds the occurrence of a partial image in the full one. */
    MATCH_TEMPLATE("matchTemplate");

    private final String name;

    ComparisonMode(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
