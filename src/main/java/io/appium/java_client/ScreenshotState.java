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

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.ofNullable;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.imageio.ImageIO;
@Accessors(chain = true)
public class ScreenshotState {
    private static final Duration DEFAULT_INTERVAL_MS = Duration.ofMillis(500);

    private BufferedImage previousScreenshot;
    private final Supplier<BufferedImage> stateProvider;
    private final ComparesImages comparator;
    /**
     * Gets the interval value in ms between similarity verification rounds in <em>verify*</em> methods.
     *
     * @param comparisonInterval interval value. 500 ms by default
     * @return current interval value in ms
     */
    @Getter @Setter private Duration comparisonInterval = DEFAULT_INTERVAL_MS;

    /**
     * The class constructor accepts two arguments. The first one is image comparator, the second
     * parameter is lambda function, that provides the screenshot of the necessary
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
     * @param comparator image comparator
     * @param stateProvider lambda function, which returns a screenshot for further comparison
     */
    public ScreenshotState(ComparesImages comparator, Supplier<BufferedImage> stateProvider) {
        this.comparator = checkNotNull(comparator);
        this.stateProvider = stateProvider;
    }

    public ScreenshotState(ComparesImages comparator) {
        this(comparator, null);
    }


    /**
     * Call this method to save the initial screenshot state.
     * It is mandatory to call before any <em>verify*</em> method is invoked.
     *
     * @return self instance for chaining
     */
    public ScreenshotState remember() {
        this.previousScreenshot = stateProvider.get();
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
        this.previousScreenshot = checkNotNull(customInitialState);
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
        private final double currentScore;

        ScreenshotComparisonTimeout(String message, double currentScore) {
            super(message);
            this.currentScore = currentScore;
        }

        public double getCurrentScore() {
            return currentScore;
        }
    }

    private ScreenshotState checkState(Function<Double, Boolean> checkerFunc, Duration timeout) {
        final LocalDateTime started = LocalDateTime.now();
        double score;
        do {
            final BufferedImage currentState = stateProvider.get();
            score = getOverlapScore(ofNullable(this.previousScreenshot)
                    .orElseThrow(() -> new ScreenshotComparisonError("Initial screenshot state is not set. "
                            + "Nothing to compare")), currentState);
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
     * Compares two valid java bitmaps and calculates similarity score between them.
     * Both images are expected to be of the same size/resolution. The method
     * implicitly invokes {@link ComparesImages#getImagesSimilarity(byte[], byte[])}.
     *
     * @param refImage   reference image
     * @param tplImage   template
     * @return similarity score value in range (-1.0, 1.0]. 1.0 is returned if the images are equal
     * @throws ScreenshotComparisonError if provided images are not valid or have
     *                                   different resolution
     */
    public double getOverlapScore(BufferedImage refImage, BufferedImage tplImage) {
        try (ByteArrayOutputStream img1 = new ByteArrayOutputStream();
             ByteArrayOutputStream img2 = new ByteArrayOutputStream()) {
            ImageIO.write(refImage, "png", img1);
            ImageIO.write(tplImage, "png", img2);
            return comparator
                    .getImagesSimilarity(Base64.getEncoder().encode(img1.toByteArray()),
                            Base64.getEncoder().encode(img2.toByteArray()))
                    .getScore();
        } catch (IOException e) {
            throw new ScreenshotComparisonError(e);
        }
    }
}
