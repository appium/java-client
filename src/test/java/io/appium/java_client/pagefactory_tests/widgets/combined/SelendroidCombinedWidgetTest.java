package io.appium.java_client.pagefactory_tests.widgets.combined;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.TimeOutDuration;
import io.appium.java_client.pagefactory_tests.widgets.Movie;
import io.appium.java_client.pagefactory_tests.widgets.WidgetTest;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.commons.lang3.StringUtils;
import org.junit.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SelendroidCombinedWidgetTest implements WidgetTest{
    private static int SELENDROID_PORT = 9999;

    private AndroidDriver<?> driver;
    private static AppiumDriverLocalService service;
    private RottenTomatoesAppWithCombinedWidgets rottenTomatoes;
    private TimeOutDuration duration;

    @BeforeClass
    public static void beforeClass() throws Exception {
        AppiumServiceBuilder builder = new AppiumServiceBuilder().withArgument(GeneralServerFlag.AUTOMATION_NAME, AutomationName.SELENDROID);
        service = builder.build();
        service.start();
    }

    @Before
    public void setUp() throws Exception {
        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "android-rottentomatoes-demo-debug.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability(MobileCapabilityType.SELENDROID_PORT, SELENDROID_PORT);
        driver = new AndroidDriver<>(service.getUrl(), capabilities);

        duration = new TimeOutDuration(20, TimeUnit.SECONDS);
        rottenTomatoes = new RottenTomatoesAppWithCombinedWidgets();
        PageFactory.initElements(new AppiumFieldDecorator(driver, duration), rottenTomatoes);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        if (service != null)
            service.stop();
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
        driver.getPageSource();  //forcing the refreshing hierarchy
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
        driver.getPageSource();  //forcing the refreshing hierarchy
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
        driver.getPageSource();  //forcing the refreshing hierarchy
        rottenTomatoes.checkExtendedReview();
    }

    @Override
    @Test
    public void checkTheLocatorOverridingOnAWidget() {
        duration.setTime(5);
        try {
            assertTrue(rottenTomatoes.getFakedMovieCount() == 0);
        }
        catch (Exception e){
            if (!NoSuchElementException.class.isAssignableFrom(e.getClass()))
                throw e;
        }

        rottenTomatoes.getASimpleMovie(0).goToReview();
        driver.getPageSource();  //forcing the refreshing hierarchy
        try {
            rottenTomatoes.checkFakeReview();
        }
        catch (Exception e){
            if (NoSuchElementException.class.isAssignableFrom(e.getClass()))
                return;
            else
                throw e;
        }
        throw new RuntimeException("Any exception was expected");
    }
}
