package io.appium.java_client.pagefactory_tests.widgets.android;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.Activity;
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

public class AndroidWidgetTest implements WidgetTest {

    private static AndroidDriver<MobileElement> driver;
    private static AppiumDriverLocalService service;
    private static RottenTomatoesApp rottenTomatoesApp;

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

        rottenTomatoesApp = new RottenTomatoesApp();
        PageFactory.initElements(
            new AppiumFieldDecorator(driver, new TimeOutDuration(5, TimeUnit.SECONDS)),
            rottenTomatoesApp);
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
            Activity activity = new Activity("com.codepath.example.rottentomatoes",
                "BoxOfficeActivity");
            driver.startActivity(activity);
        }
    }

    @Test
    @Override
    public void checkACommonWidget() {
        assertTrue(rottenTomatoesApp.getSimpleMovieCount() >= 1);
        Movie movie = rottenTomatoesApp.getASimpleMovie(0);
        assertTrue(!StringUtils.isBlank(movie.title()));
        assertTrue(!StringUtils.isBlank(movie.score()));
        assertNotNull(movie.getPoster());
        movie.goToReview();

        rottenTomatoesApp.checkSimpleReview();
    }

    @Override
    @Test
    public void checkAnAnnotatedWidget() {
        assertTrue(rottenTomatoesApp.getAnnotatedMovieCount() >= 1);
        Movie movie = rottenTomatoesApp.getAnAnnotatedMovie(0);
        assertTrue(!StringUtils.isBlank(movie.title()));
        assertTrue(!StringUtils.isBlank(movie.score()));
        assertNotNull(movie.getPoster());
        movie.goToReview();

        rottenTomatoesApp.checkAnnotatedReview();
    }


    @Override
    @Test
    public void checkAnExtendedWidget() {
        assertTrue(rottenTomatoesApp.getExtendeddMovieCount() >= 1);
        Movie movie = rottenTomatoesApp.getAnExtendedMovie(0);
        assertTrue(!StringUtils.isBlank(movie.title()));
        assertTrue(!StringUtils.isBlank(movie.score()));
        assertNotNull(movie.getPoster());
        movie.goToReview();

        rottenTomatoesApp.checkExtendedReview();
    }

    @Override
    @Test
    public void checkTheLocatorOverridingOnAWidget() {
        try {
            assertTrue(rottenTomatoesApp.getFakedMovieCount() == 0);
        } catch (Exception e) {
            if (!NoSuchElementException.class.isAssignableFrom(e.getClass())) {
                throw e;
            }
        }

        rottenTomatoesApp.getASimpleMovie(0).goToReview();

        try {
            rottenTomatoesApp.checkFakeReview();
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
