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

public enum MatchingFunction {
    FLANN_BASED("FlannBased"), BRUTE_FORCE("BruteForce"), BRUTE_FORCE1("BruteForceL1"),
    BRUTE_FORCE_HAMMING("BruteForceHamming"), BRUTE_FORCE_HAMMING_LUT("BruteForceHammingLut"),
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
