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

package io.appium.java_client.android;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import io.appium.java_client.imagecomparison.FeatureDetector;
import io.appium.java_client.imagecomparison.FeaturesMatchingOptions;
import io.appium.java_client.imagecomparison.FeaturesMatchingResult;
import io.appium.java_client.imagecomparison.MatchingFunction;
import io.appium.java_client.imagecomparison.OccurrenceMatchingOptions;
import io.appium.java_client.imagecomparison.OccurrenceMatchingResult;
import io.appium.java_client.imagecomparison.SimilarityMatchingOptions;
import io.appium.java_client.imagecomparison.SimilarityMatchingResult;
import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.OutputType;

public class ImagesComparisonTest extends BaseAndroidTest {

    @Before
    public void setUp() {
        driver.resetApp();
    }

    @Test
    public void verifyFeaturesMatching() {
        byte[] screenshot = Base64.encodeBase64(driver.getScreenshotAs(OutputType.BYTES));
        FeaturesMatchingResult result = driver
                .matchImagesFeatures(screenshot, screenshot, new FeaturesMatchingOptions()
                        .withDetectorName(FeatureDetector.ORB)
                        .withGoodMatchesFactor(40)
                        .withMatchFunc(MatchingFunction.BRUTE_FORCE_HAMMING)
                        .withEnabledVisualization());
        assertThat(result.getVisualization().length, is(greaterThan(0)));
        assertThat(result.getCount(), is(greaterThan(0)));
        assertThat(result.getTotalCount(), is(greaterThan(0)));
        assertFalse(result.getPoints1().isEmpty());
        assertNotNull(result.getRect1());
        assertFalse(result.getPoints2().isEmpty());
        assertNotNull(result.getRect2());
    }

    @Test
    public void verifyOccurrencesSearch() {
        byte[] screenshot = Base64.encodeBase64(driver.getScreenshotAs(OutputType.BYTES));
        OccurrenceMatchingResult result = driver
                .findImageOccurrence(screenshot, screenshot, new OccurrenceMatchingOptions()
                        .withEnabledVisualization());
        assertThat(result.getVisualization().length, is(greaterThan(0)));
        assertNotNull(result.getRect());
    }

    @Test
    public void verifySimilarityCalculation() {
        byte[] screenshot = Base64.encodeBase64(driver.getScreenshotAs(OutputType.BYTES));
        SimilarityMatchingResult result = driver
                .getImagesSimilarity(screenshot, screenshot, new SimilarityMatchingOptions()
                        .withEnabledVisualization());
        assertThat(result.getVisualization().length, is(greaterThan(0)));
        assertThat(result.getScore(), is(greaterThan(0.0)));
    }
}
