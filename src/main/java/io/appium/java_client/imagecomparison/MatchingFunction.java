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
 * Matching functions available for the features matching.
 */
public enum MatchingFunction {
    /** The FLANN-based matcher. */
    FLANN_BASED("FlannBased"),
    /** The brute-force matcher. */
    BRUTE_FORCE("BruteForce"),
    /** The brute-force matcher using the L1 norm. */
    BRUTE_FORCE1("BruteForceL1"),
    /** The brute-force matcher using the Hamming distance. */
    BRUTE_FORCE_HAMMING("BruteForceHamming"),
    /** The brute-force matcher using the Hamming distance with a lookup table. */
    BRUTE_FORCE_HAMMING_LUT("BruteForceHammingLut"),
    /** The brute-force matcher using the squared L2 norm. */
    BRUTE_FORCE_SL2("BruteForceSL2");

    private final String name;

    MatchingFunction(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
