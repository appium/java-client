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

import static nu.pattern.OpenCV.loadShared;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class ScreenshotState {
    private static final Duration DEFAULT_INTERVAL_MS = Duration.ofMillis(500);

    private Optional<BufferedImage> previousScreenshot = Optional.empty();
    private Supplier<BufferedImage> stateProvider;

    private Duration comparisonInterval = DEFAULT_INTERVAL_MS;

    /**
     * The class constructor accepts single argument, which is
     * lambda function, that provides the screenshot of the necessary
     * screen area to be verified for similarity.
     * This lambda method is NOT called upon class creation.
     * One has to invoke {@link #remember()} method in order to call it.
     *
     * <p>Examples of provider function with Appium driver:
     * <code>
     * () -&gt; {
     * final byte[] srcImage = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
     * return ImageIO.read(new ByteArrayInputStream(srcImage));
     * }
     * </code>
     * or
     * <code>
     * () -&gt; {
     * final byte[] srcImage = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
     * final BufferedImage screenshot = ImageIO.read(new ByteArrayInputStream(srcImage));
     * final WebElement element = driver.findElement(locator);
     * // Can be simplified in Selenium 3.0+ by using getRect method of WebElement interface
     * final Point elementLocation = element.getLocation();
     * final Dimension elementSize = element.getSize();
     * return screenshot.getSubimage(
     * new Rectangle(elementLocation.x, elementLocation.y, elementSize.width, elementSize.height);
     * }
     * </code>
     *
     * @param stateProvider lambda function, which returns a screenshot for further comparison
     */
    public ScreenshotState(Supplier<BufferedImage> stateProvider) {
        this.stateProvider = stateProvider;
    }

    /**
     * Gets the interval value in ms between similarity verification rounds in <em>verify*</em> methods.
     *
     * @return current interval value in ms
     */
    public Duration getComparisonInterval() {
        return comparisonInterval;
    }

    /**
     * Sets the interval between similarity verification rounds in <em>verify*</em> methods.
     *
     * @param comparisonInterval interval value. 500 ms by default
     * @return self instance for chaining
     */
    public ScreenshotState setComparisonInterval(Duration comparisonInterval) {
        this.comparisonInterval = comparisonInterval;
        return this;
    }

    /**
     * Call this method to save the initial screenshot state.
     * It is mandatory to call before any <em>verify*</em> method is invoked.
     *
     * @return self instance for chaining
     */
    public ScreenshotState remember() {
        this.previousScreenshot = Optional.of(stateProvider.get());
        return this;
    }

    /**
     * This method allows to pass a custom bitmap for further comparison
     * instead of taking one using screenshot provider function. This might
     * be useful in some advanced cases.
     *
     * @param customInitialState valid bitmap
     * @return self instance for chaining
     */
    public ScreenshotState remember(BufferedImage customInitialState) {
        this.previousScreenshot = Optional.of(customInitialState);
        return this;
    }

    public static class ScreenshotComparisonError extends RuntimeException {
        private static final long serialVersionUID = -7011854909939194466L;

        ScreenshotComparisonError(Throwable reason) {
            super(reason);
        }

        ScreenshotComparisonError(String message) {
            super(message);
        }
    }

    public static class ScreenshotComparisonTimeout extends RuntimeException {
        private static final long serialVersionUID = 6336247721154252476L;
        private double currentScore = Double.NaN;

        ScreenshotComparisonTimeout(String message, double currentScore) {
            super(message);
            this.currentScore = currentScore;
        }

        public double getCurrentScore() {
            return currentScore;
        }
    }

    private ScreenshotState checkState(Function<Double, Boolean> checkerFunc, Duration timeout) {
        return checkState(checkerFunc, timeout, ResizeMode.NO_RESIZE);
    }

    private ScreenshotState checkState(Function<Double, Boolean> checkerFunc, Duration timeout, ResizeMode resizeMode) {
        final LocalDateTime started = LocalDateTime.now();
        double score;
        do {
            final BufferedImage currentState = stateProvider.get();
            score = getOverlapScore(this.previousScreenshot
                    .orElseThrow(() -> new ScreenshotComparisonError("Initial screenshot state is not set. "
                            + "Nothing to compare")), currentState, resizeMode);
            if (checkerFunc.apply(score)) {
                return this;
            }
            try {
                Thread.sleep(comparisonInterval.toMillis());
            } catch (InterruptedException e) {
                throw new ScreenshotComparisonError(e);
            }
        }
        while (Duration.between(started, LocalDateTime.now()).compareTo(timeout) <= 0);
        throw new ScreenshotComparisonTimeout(
                String.format("Screenshot comparison timed out after %s ms. Actual similarity score: %.5f",
                        timeout.toMillis(), score), score);
    }

    /**
     * Verifies whether the state of the screenshot provided by stateProvider lambda function
     * is changed within the given timeout.
     *
     * @param timeout  timeout value
     * @param minScore the value in range (0.0, 1.0)
     * @return self instance for chaining
     * @throws ScreenshotComparisonTimeout if the calculated score is still
     *                                     greater or equal to the given score after timeout happens
     * @throws ScreenshotComparisonError   if {@link #remember()} method has not been invoked yet
     */
    public ScreenshotState verifyChanged(Duration timeout, double minScore) {
        return checkState((x) -> x < minScore, timeout);
    }

    /**
     * Verifies whether the state of the screenshot provided by stateProvider lambda function
     * is changed within the given timeout.
     *
     * @param timeout    timeout value
     * @param minScore   the value in range (0.0, 1.0)
     * @param resizeMode one of <em>ResizeMode</em> enum values.
     *                   Set it to a value different from <em>NO_RESIZE</em>
     *                   if the actual screenshot is expected to have different
     *                   dimensions in comparison to the previously remembered one
     * @return self instance for chaining
     * @throws ScreenshotComparisonTimeout if the calculated score is still
     *                                     greater or equal to the given score after timeout happens
     * @throws ScreenshotComparisonError   if {@link #remember()} method has not been invoked yet
     */
    public ScreenshotState verifyChanged(Duration timeout, double minScore, ResizeMode resizeMode) {
        return checkState((x) -> x < minScore, timeout, resizeMode);
    }

    /**
     * Verifies whether the state of the screenshot provided by stateProvider lambda function
     * is not changed within the given timeout.
     *
     * @param timeout   timeout value
     * @param minScore  the value in range (0.0, 1.0)
     * @return self instance for chaining
     * @throws ScreenshotComparisonTimeout if the calculated score is still
     *                                     less than the given score after timeout happens
     * @throws ScreenshotComparisonError   if {@link #remember()} method has not been invoked yet
     */
    public ScreenshotState verifyNotChanged(Duration timeout, double minScore) {
        return checkState((x) -> x >= minScore, timeout);
    }

    /**
     * Verifies whether the state of the screenshot provided by stateProvider lambda function
     * is changed within the given timeout.
     *
     * @param timeout    timeout value
     * @param minScore   the value in range (0.0, 1.0)
     * @param resizeMode one of <em>ResizeMode</em> enum values.
     *                   Set it to a value different from <em>NO_RESIZE</em>
     *                   if the actual screenshot is expected to have different
     *                   dimensions in comparison to the previously remembered one
     * @return self instance for chaining
     * @throws ScreenshotComparisonTimeout if the calculated score is still
     *                                     less than the given score after timeout happens
     * @throws ScreenshotComparisonError   if {@link #remember()} method has not been invoked yet
     */
    public ScreenshotState verifyNotChanged(Duration timeout, double minScore, ResizeMode resizeMode) {
        return checkState((x) -> x >= minScore, timeout, resizeMode);
    }

    private static Mat prepareImageForComparison(BufferedImage srcImage) {
        final BufferedImage normalizedBitmap = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(),
                BufferedImage.TYPE_3BYTE_BGR);
        final Graphics2D g = normalizedBitmap.createGraphics();
        try {
            g.setComposite(AlphaComposite.Src);
            g.drawImage(srcImage, 0, 0, null);
        } finally {
            g.dispose();
        }
        final byte[] pixels = ((DataBufferByte) normalizedBitmap.getRaster().getDataBuffer()).getData();
        final Mat result = new Mat(normalizedBitmap.getHeight(), normalizedBitmap.getWidth(), CvType.CV_8UC3);
        result.put(0, 0, pixels);
        return result;
    }

    private static Mat resizeFirstMatrixToSecondMatrixResolution(Mat first, Mat second) {
        if (first.width() != second.width() || first.height() != second.height()) {
            final Mat result = new Mat();
            final Size sz = new Size(second.width(), second.height());
            Imgproc.resize(first, result, sz);
            return result;
        }
        return first;
    }

    /**
     * A shortcut to {@link #getOverlapScore(BufferedImage, BufferedImage, ResizeMode)} method
     * for the case if both reference and template images are expected to have the same dimensions.
     *
     * @param refImage reference image
     * @param tplImage template
     * @return similarity score value in range (-1.0, 1.0). 1.0 is returned if the images are equal
     * @throws ScreenshotComparisonError if provided images are not valid or have different resolution
     */
    public static double getOverlapScore(BufferedImage refImage, BufferedImage tplImage) {
        return getOverlapScore(refImage, tplImage, ResizeMode.NO_RESIZE);
    }

    /**
     * Compares two valid java bitmaps and calculates similarity score between them.
     *
     * @param refImage   reference image
     * @param tplImage   template
     * @param resizeMode one of possible enum values. Set it either to <em>TEMPLATE_TO_REFERENCE_RESOLUTION</em> or
     *                   <em>REFERENCE_TO_TEMPLATE_RESOLUTION</em> if given bitmaps have different dimensions
     * @return similarity score value in range (-1.0, 1.0). 1.0 is returned if the images are equal
     * @throws ScreenshotComparisonError if provided images are not valid or have
     *                                   different resolution, but resizeMode has been set to <em>NO_RESIZE</em>
     */
    public static double getOverlapScore(BufferedImage refImage, BufferedImage tplImage, ResizeMode resizeMode) {
        Mat ref = prepareImageForComparison(refImage);
        if (ref.empty()) {
            throw new ScreenshotComparisonError("Reference image cannot be converted for further comparison");
        }
        Mat tpl = prepareImageForComparison(tplImage);
        if (tpl.empty()) {
            throw new ScreenshotComparisonError("Template image cannot be converted for further comparison");
        }
        switch (resizeMode) {
            case TEMPLATE_TO_REFERENCE_RESOLUTION:
                tpl = resizeFirstMatrixToSecondMatrixResolution(tpl, ref);
                break;
            case REFERENCE_TO_TEMPLATE_RESOLUTION:
                ref = resizeFirstMatrixToSecondMatrixResolution(ref, tpl);
                break;
            default:
                // do nothing
        }

        if (ref.width() != tpl.width() || ref.height() != tpl.height()) {
            throw new ScreenshotComparisonError(
                    "Resolutions of template and reference images are expected to be equal. "
                            + "Try different resizeMode value."
            );
        }

        Mat res = new Mat(ref.rows() - tpl.rows() + 1, ref.cols() - tpl.cols() + 1, CvType.CV_32FC1);
        Imgproc.matchTemplate(ref, tpl, res, Imgproc.TM_CCOEFF_NORMED);
        return Core.minMaxLoc(res).maxVal;
    }

    public enum ResizeMode {
        NO_RESIZE, TEMPLATE_TO_REFERENCE_RESOLUTION, REFERENCE_TO_TEMPLATE_RESOLUTION
    }

    static {
        loadShared();
    }
}
