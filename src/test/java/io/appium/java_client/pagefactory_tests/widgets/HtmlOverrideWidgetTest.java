package io.appium.java_client.pagefactory_tests.widgets;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.TimeOutDuration;
import io.appium.java_client.pagefactory_tests.widgets.html.annotated.AnnotatedHtmlMovie;
import io.appium.java_client.pagefactory_tests.widgets.html.simple.HtmlMovie;
import org.apache.commons.lang3.StringUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class HtmlOverrideWidgetTest implements WidgetTest {

    private static ChromeDriver driver;
    private static RottenTomatoes rottenTomatoes;

    /**
     * initialization.
     */
    @BeforeClass
    public static void beforeClass() throws Exception {

        if (Platform.getCurrent().is(Platform.WINDOWS)) {
            System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,
                "src/test/java/io/appium/java_client/pagefactory_tests/chromedriver.exe");
        } else {
            System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,
                "src/test/java/io/appium/java_client/pagefactory_tests/chromedriver");
        }

        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        rottenTomatoes = new RottenTomatoes();
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
    }

    /**
     * The setting up.
     */
    @Before
    public void setUp() throws Exception {
        if (driver != null) {
            driver.get(
                    new File("src/test/java/io/appium/java_client/RottenTomatoesSnapshot.html")
                        .toURI().toString());
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
        assertTrue(movie.getSelfReference().getClass().equals(HtmlMovie.class));
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
        assertTrue(movie.getSelfReference().getClass().equals(AnnotatedHtmlMovie.class));
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
        assertTrue(movie.getSelfReference().getClass().equals(AnnotatedHtmlMovie.class));
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
