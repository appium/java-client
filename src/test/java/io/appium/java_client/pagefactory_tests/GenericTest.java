package io.appium.java_client.pagefactory_tests;


import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.*;
import io.appium.java_client.pagefactory_tests.widgets.RottenTomatoes;
import io.appium.java_client.pagefactory_tests.widgets.ios.simple.IOSMovie;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.junit.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class GenericTest {

    private static AppiumDriverLocalService service;
    private static RottenTomatoes rottenTomatoes;
    public IOSDriver<?> driver;

    static class NewArrivalsPage<T> {

        @iOSFindBy(className = "UIATableCell") List<IOSMovie> movieList;

        public List<T> getNewMovies() {
            return (List<T>) movieList;
        }

    }

    static class NewArrivals extends NewArrivalsPage<Object> {

        @iOSFindBy(xpath = "//UIAButton[@name='New Arrivals']")
        public MobileElement newlyArrived;

    }

    @Test
    public void genericTestCase() {
        NewArrivals newArrivals = new NewArrivals();
        PageFactory.initElements(new AppiumFieldDecorator(driver),
            newArrivals);
        newArrivals.newlyArrived.click();
        assertTrue(newArrivals.getNewMovies().size() >= 1);

    }

    /**
     * initialization.
     */
    @BeforeClass
    public static void beforeClass() throws Exception {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();
    }

    /**
     * finishing.
     */
    @AfterClass
    public static void afterClass() throws Exception {
        if (service != null) {
            service.stop();
        }
    }

    /**
     * The setting up.
     */
    @Before
    public void setUp() throws Exception {
        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "RottenTomatoes.zip");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.3");
        //sometimes environment has performance problems
        capabilities.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 500000);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        driver = new IOSDriver<>(service.getUrl(), capabilities);
    }

    /**
     * finishing.
     */
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
