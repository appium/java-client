package io.appium.java_client.utils;

import io.appium.java_client.ScreenshotState;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;

public class ScreenshotStateTests {
    private static final Random rand = new Random();
    private static final Duration ONE_SECOND = Duration.ofSeconds(1);
    private static final double MAX_SCORE = 0.999;

    private ImagesGenerator randomImageOfStaticSize;
    private ImagesGenerator randomImageOfRandomSize;
    private ImagesGenerator staticImage;

    private static class ImagesGenerator {
        private boolean isRandom;
        private boolean isSizeStatic;

        private static final int DEFAULT_WIDTH = 100;
        private static final int MIN_WIDTH = 50;
        private static final int DEFAULT_HEIGHT = 100;
        private static final int MIN_HEIGHT = 50;

        ImagesGenerator(boolean isRandom, boolean isSizeStatic) {
            this.isRandom = isRandom;
            this.isSizeStatic = isSizeStatic;
        }

        private BufferedImage generate() {
            final int width = isSizeStatic ? DEFAULT_WIDTH : MIN_WIDTH + rand.nextInt(DEFAULT_WIDTH);
            final int height = isSizeStatic ? DEFAULT_HEIGHT : MIN_HEIGHT + rand.nextInt(DEFAULT_HEIGHT);
            final BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            final Graphics2D g2 = result.createGraphics();
            try {
                g2.setColor(isRandom ? new Color(rand.nextInt(256), rand.nextInt(256),
                        rand.nextInt(256)) : Color.red);
                g2.fill(new Rectangle2D.Float(0, 0,
                        isRandom ? rand.nextInt(DEFAULT_WIDTH) : DEFAULT_WIDTH / 2,
                        isRandom ? rand.nextInt(DEFAULT_HEIGHT) : DEFAULT_HEIGHT / 2));
            } finally {
                g2.dispose();
            }
            return result;
        }
    }

    @Before
    public void setUp() {
        randomImageOfStaticSize = new ImagesGenerator(true, true);
        randomImageOfRandomSize = new ImagesGenerator(true, false);
        staticImage = new ImagesGenerator(false, true);
    }

    //region Positive Tests
    @Test
    public void testBasicComparisonScenario() {
        final ScreenshotState currentState = new ScreenshotState(staticImage::generate)
                .remember();
        assertThat(currentState.verifyNotChanged(ONE_SECOND, MAX_SCORE), is(notNullValue()));
    }

    @Test
    public void testChangedImageVerification() {
        final ScreenshotState currentState = new ScreenshotState(randomImageOfStaticSize::generate)
                .remember();
        assertThat(currentState.verifyChanged(ONE_SECOND, MAX_SCORE), is(notNullValue()));
    }

    @Test
    public void testChangedImageVerificationWithDifferentSize() {
        final ScreenshotState currentState = new ScreenshotState(randomImageOfRandomSize::generate)
                .remember();
        assertThat(currentState.verifyChanged(ONE_SECOND, MAX_SCORE,
                ScreenshotState.ResizeMode.REFERENCE_TO_TEMPLATE_RESOLUTION), is(notNullValue()));
    }

    @Test
    public void testChangedImageVerificationWithCustomRememberedImage() {
        final ScreenshotState currentState = new ScreenshotState(randomImageOfRandomSize::generate)
                .remember(randomImageOfRandomSize.generate());
        assertThat(currentState.verifyChanged(ONE_SECOND, MAX_SCORE,
                ScreenshotState.ResizeMode.REFERENCE_TO_TEMPLATE_RESOLUTION), is(notNullValue()));
    }

    @Test
    public void testChangedImageVerificationWithCustomInterval() {
        final ScreenshotState currentState = new ScreenshotState(randomImageOfRandomSize::generate)
                .setComparisonInterval(Duration.ofMillis(100)).remember();
        assertThat(currentState.verifyChanged(ONE_SECOND, MAX_SCORE,
                ScreenshotState.ResizeMode.REFERENCE_TO_TEMPLATE_RESOLUTION), is(notNullValue()));
    }

    @Test
    public void testDirectOverlapScoreCalculation() {
        final BufferedImage anImage = staticImage.generate();
        final double score = ScreenshotState.getOverlapScore(anImage, anImage);
        assertThat(score, is(greaterThanOrEqualTo(MAX_SCORE)));
    }

    @Test
    public void testScreenshotComparisonUsingStaticMethod() {
        BufferedImage img1 = randomImageOfStaticSize.generate();
        // ImageIO.write(img1, "png", new File("img1.png"));
        BufferedImage img2 = randomImageOfStaticSize.generate();
        // ImageIO.write(img2, "png", new File("img2.png"));
        assertThat(ScreenshotState.getOverlapScore(img1, img2), is(lessThan(MAX_SCORE)));
    }
    //endregion

    //region Negative Tests
    @Test(expected = ScreenshotState.ScreenshotComparisonError.class)
    public void testDifferentSizeOfTemplates() {
        new ScreenshotState(randomImageOfRandomSize::generate).remember().verifyChanged(ONE_SECOND, MAX_SCORE);
    }

    @Test(expected = NullPointerException.class)
    public void testInvalidProvider() {
        new ScreenshotState(() -> null).remember();
    }

    @Test(expected = ScreenshotState.ScreenshotComparisonTimeout.class)
    public void testImagesComparisonExpectationFailed() {
        new ScreenshotState(randomImageOfStaticSize::generate).remember().verifyNotChanged(ONE_SECOND, MAX_SCORE);
    }

    @Test(expected = ScreenshotState.ScreenshotComparisonTimeout.class)
    public void testImagesComparisonExpectationFailed2() {
        new ScreenshotState(staticImage::generate).remember().verifyChanged(ONE_SECOND, MAX_SCORE);
    }

    @Test(expected = ScreenshotState.ScreenshotComparisonError.class)
    public void testScreenshotInitialStateHasNotBeenRemembered() {
        new ScreenshotState(staticImage::generate).verifyNotChanged(ONE_SECOND, MAX_SCORE);
    }
    //endregion
}
