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

package io.appium.java_client;

import static io.appium.java_client.MobileCommand.compareImagesCommand;

import io.appium.java_client.imagecomparison.ComparisonMode;
import io.appium.java_client.imagecomparison.FeaturesMatchingOptions;
import io.appium.java_client.imagecomparison.FeaturesMatchingResult;
import io.appium.java_client.imagecomparison.OccurrenceMatchingOptions;
import io.appium.java_client.imagecomparison.OccurrenceMatchingResult;
import io.appium.java_client.imagecomparison.SimilarityMatchingOptions;
import io.appium.java_client.imagecomparison.SimilarityMatchingResult;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.annotation.Nullable;

public interface ComparesImages extends ExecutesMethod {

    /**
     * Performs images matching by features with default options. Read
     * https://docs.opencv.org/3.0-beta/doc/py_tutorials/py_feature2d/py_matcher/py_matcher.html
     * for more details on this topic.
     *
     * @param base64image1 base64-encoded representation of the first image
     * @param base64Image2 base64-encoded representation of the second image
     * @return The matching result.
     */
    default FeaturesMatchingResult matchImagesFeatures(byte[] base64image1, byte[] base64Image2) {
        return matchImagesFeatures(base64image1, base64Image2, null);
    }

    /**
     * Performs images matching by features. Read
     * https://docs.opencv.org/3.0-beta/doc/py_tutorials/py_feature2d/py_matcher/py_matcher.html
     * for more details on this topic.
     *
     * @param base64image1 base64-encoded representation of the first image
     * @param base64Image2 base64-encoded representation of the second image
     * @param options comparison options
     * @return The matching result. The configuration of fields in the result depends on comparison options.
     */
    default FeaturesMatchingResult matchImagesFeatures(byte[] base64image1, byte[] base64Image2,
                                                       @Nullable FeaturesMatchingOptions options) {
        Object response = CommandExecutionHelper.execute(this,
                compareImagesCommand(ComparisonMode.MATCH_FEATURES, base64image1, base64Image2, options));
        //noinspection unchecked
        return new FeaturesMatchingResult((Map<String, Object>) response);
    }

    /**
     * Performs images matching by features with default options. Read
     * https://docs.opencv.org/3.0-beta/doc/py_tutorials/py_feature2d/py_matcher/py_matcher.html
     * for more details on this topic.
     *
     * @param image1 The location of the first image
     * @param image2 The location of the second image
     * @return The matching result.
     * @throws IOException On file system I/O error.
     */
    default FeaturesMatchingResult matchImagesFeatures(File image1, File image2) throws IOException {
        return matchImagesFeatures(image1, image2, null);
    }

    /**
     * Performs images matching by features. Read
     * https://docs.opencv.org/3.0-beta/doc/py_tutorials/py_feature2d/py_matcher/py_matcher.html
     * for more details on this topic.
     *
     * @param image1 The location of the first image
     * @param image2 The location of the second image
     * @param options comparison options
     * @return The matching result. The configuration of fields in the result depends on comparison options.
     * @throws IOException On file system I/O error.
     */
    default FeaturesMatchingResult matchImagesFeatures(File image1, File image2,
                                                       @Nullable FeaturesMatchingOptions options) throws IOException {
        return matchImagesFeatures(Base64.encodeBase64(FileUtils.readFileToByteArray(image1)),
                Base64.encodeBase64(FileUtils.readFileToByteArray(image2)), options);
    }

    /**
     * Performs images matching by template to find possible occurrence of the partial image
     * in the full image with default options. Read
     * https://docs.opencv.org/2.4/doc/tutorials/imgproc/histograms/template_matching/template_matching.html
     * for more details on this topic.
     *
     * @param fullImage base64-encoded representation of the full image
     * @param partialImage base64-encoded representation of the partial image
     * @return The matching result.
     */
    default OccurrenceMatchingResult findImageOccurrence(byte[] fullImage, byte[] partialImage) {
        return findImageOccurrence(fullImage, partialImage, null);
    }

    /**
     * Performs images matching by template to find possible occurrence of the partial image
     * in the full image. Read
     * https://docs.opencv.org/2.4/doc/tutorials/imgproc/histograms/template_matching/template_matching.html
     * for more details on this topic.
     *
     * @param fullImage base64-encoded representation of the full image
     * @param partialImage base64-encoded representation of the partial image
     * @param options comparison options
     * @return The matching result. The configuration of fields in the result depends on comparison options.
     */
    default OccurrenceMatchingResult findImageOccurrence(byte[] fullImage, byte[] partialImage,
                                                         @Nullable OccurrenceMatchingOptions options) {
        Object response = CommandExecutionHelper.execute(this,
                compareImagesCommand(ComparisonMode.MATCH_TEMPLATE, fullImage, partialImage, options));
        //noinspection unchecked
        return new OccurrenceMatchingResult((Map<String, Object>) response);
    }

    /**
     * Performs images matching by template to find possible occurrence of the partial image
     * in the full image with default options. Read
     * https://docs.opencv.org/2.4/doc/tutorials/imgproc/histograms/template_matching/template_matching.html
     * for more details on this topic.
     *
     * @param fullImage The location of the full image
     * @param partialImage The location of the partial image
     * @return The matching result. The configuration of fields in the result depends on comparison options.
     * @throws IOException On file system I/O error.
     */
    default OccurrenceMatchingResult findImageOccurrence(File fullImage, File partialImage) throws IOException {
        return findImageOccurrence(fullImage, partialImage, null);
    }

    /**
     * Performs images matching by template to find possible occurrence of the partial image
     * in the full image. Read
     * https://docs.opencv.org/2.4/doc/tutorials/imgproc/histograms/template_matching/template_matching.html
     * for more details on this topic.
     *
     * @param fullImage The location of the full image
     * @param partialImage The location of the partial image
     * @param options comparison options
     * @return The matching result. The configuration of fields in the result depends on comparison options.
     * @throws IOException On file system I/O error.
     */
    default OccurrenceMatchingResult findImageOccurrence(File fullImage, File partialImage,
                                                         @Nullable OccurrenceMatchingOptions options)
            throws IOException {
        return findImageOccurrence(Base64.encodeBase64(FileUtils.readFileToByteArray(fullImage)),
                Base64.encodeBase64(FileUtils.readFileToByteArray(partialImage)), options);
    }

    /**
     * Performs images matching to calculate the similarity score between them
     * with default options. The flow there is similar to the one used in
     * {@link #findImageOccurrence(byte[], byte[], OccurrenceMatchingOptions)},
     * but it is mandatory that both images are of equal size.
     *
     * @param base64image1 base64-encoded representation of the first image
     * @param base64Image2 base64-encoded representation of the second image
     * @return Matching result. The configuration of fields in the result depends on comparison options.
     */
    default SimilarityMatchingResult getImagesSimilarity(byte[] base64image1, byte[] base64Image2) {
        return getImagesSimilarity(base64image1, base64Image2, null);
    }

    /**
     * Performs images matching to calculate the similarity score between them.
     * The flow there is similar to the one used in
     * {@link #findImageOccurrence(byte[], byte[], OccurrenceMatchingOptions)},
     * but it is mandatory that both images are of equal size.
     *
     * @param base64image1 base64-encoded representation of the first image
     * @param base64Image2 base64-encoded representation of the second image
     * @param options comparison options
     * @return Matching result. The configuration of fields in the result depends on comparison options.
     */
    default SimilarityMatchingResult getImagesSimilarity(byte[] base64image1, byte[] base64Image2,
                                                         @Nullable SimilarityMatchingOptions options) {
        Object response = CommandExecutionHelper.execute(this,
                compareImagesCommand(ComparisonMode.GET_SIMILARITY, base64image1, base64Image2, options));
        //noinspection unchecked
        return new SimilarityMatchingResult((Map<String, Object>) response);
    }

    /**
     * Performs images matching to calculate the similarity score between them
     * with default options. The flow there is similar to the one used in
     * {@link #findImageOccurrence(byte[], byte[], OccurrenceMatchingOptions)},
     * but it is mandatory that both images are of equal size.
     *
     * @param image1 The location of the full image
     * @param image2 The location of the partial image
     * @return Matching result. The configuration of fields in the result depends on comparison options.
     * @throws IOException On file system I/O error.
     */
    default SimilarityMatchingResult getImagesSimilarity(File image1, File image2) throws IOException {
        return getImagesSimilarity(image1, image2, null);
    }

    /**
     * Performs images matching to calculate the similarity score between them.
     * The flow there is similar to the one used in
     * {@link #findImageOccurrence(byte[], byte[], OccurrenceMatchingOptions)},
     * but it is mandatory that both images are of equal size.
     *
     * @param image1 The location of the full image
     * @param image2 The location of the partial image
     * @param options comparison options
     * @return Matching result. The configuration of fields in the result depends on comparison options.
     * @throws IOException On file system I/O error.
     */
    default SimilarityMatchingResult getImagesSimilarity(File image1, File image2,
                                                         @Nullable SimilarityMatchingOptions options)
            throws IOException {
        return getImagesSimilarity(Base64.encodeBase64(FileUtils.readFileToByteArray(image1)),
                Base64.encodeBase64(FileUtils.readFileToByteArray(image2)), options);
    }
}