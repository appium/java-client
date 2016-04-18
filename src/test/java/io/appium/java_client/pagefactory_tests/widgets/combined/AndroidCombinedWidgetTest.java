package io.appium.java_client.pagefactory_tests.widgets.combined;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.TimeOutDuration;
import io.appium.java_client.pagefactory_tests.widgets.Movie;
import io.appium.java_client.pagefactory_tests.widgets.WidgetTest;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.apache.commons.lang3.StringUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class AndroidCombinedWidgetTest implements WidgetTest {

    private static AndroidDriver<?> driver;
    private static AppiumDriverLocalService service;
    private static RottenTomatoesAppWithCombinedWidgets rottenTomatoes;

    /**
     * initialization.
     */
    @BeforeClass
    public static void beforeClass() throws Exception {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();

        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "android-rottentomatoes-demo-debug.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        driver = new AndroidDriver<>(service.getUrl(), capabilities);

        rottenTomatoes = new RottenTomatoesAppWithCombinedWidgets();
        PageFactory.initElements(
            new AppiumFieldDecorator(driver, new TimeOutDuration(5, TimeUnit.SECONDS)),
            rottenTomatoes);
    }

    /**
     * finishing.
     */
    @AfterClass
    public static void afterClass() throws Exception {
        if (driver != null) {
            driver.quit();
        }

        if (service != null) {
            service.stop();
        }
    }

    /**
     * The setting up.
     */
    @Before
    public void setUp() throws Exception {
        if (driver != null) {
            driver.startActivity("com.codepath.example.rottentomatoes", "BoxOfficeActivity");
        }
    }

    @Test
    @Override
    public void checkACommonWidget() {
        assertTrue(rottenTomatoes.getSimpleMovieCount() >= 1);
        Movie movie = rottenTomatoes.getASimpleMovie(0);
        assertTrue(!StringUtils.isBlank(movie.title()));
        assertTrue(!StringUtils.isBlank(movie.score()));
        assertNotNull(movie.getPoster());
        movie.goToReview();

        rottenTomatoes.checkSimpleReview();
    }

    @Override
    @Test
    public void checkAnAnnotatedWidget() {
        assertTrue(rottenTomatoes.getAnnotatedMovieCount() >= 1);
        Movie movie = rottenTomatoes.getAnAnnotatedMovie(0);
        assertTrue(!StringUtils.isBlank(movie.title()));
        assertTrue(!StringUtils.isBlank(movie.score()));
        assertNotNull(movie.getPoster());
        movie.goToReview();

        rottenTomatoes.checkAnnotatedReview();
    }


    @Override
    @Test
    public void checkAnExtendedWidget() {
        assertTrue(rottenTomatoes.getExtendeddMovieCount() >= 1);
        Movie movie = rottenTomatoes.getAnExtendedMovie(0);
        assertTrue(!StringUtils.isBlank(movie.title()));
        assertTrue(!StringUtils.isBlank(movie.score()));
        assertNotNull(movie.getPoster());
        movie.goToReview();

        rottenTomatoes.checkExtendedReview();
    }

    @Override
    @Test
    public void checkTheLocatorOverridingOnAWidget() {
        try {
            assertTrue(rottenTomatoes.getFakedMovieCount() == 0);
        } catch (Exception e) {
            if (!NoSuchElementException.class.isAssignableFrom(e.getClass())) {
                throw e;
            }
        }

        rottenTomatoes.getASimpleMovie(0).goToReview();

        try {
            rottenTomatoes.checkFakeReview();
        } catch (Exception e) {
            if (NoSuchElementException.class.isAssignableFrom(e.getClass())) {
                return;
            } else {
                throw e;
            }
        }
        throw new RuntimeException("Any exception was expected");
    }
}
