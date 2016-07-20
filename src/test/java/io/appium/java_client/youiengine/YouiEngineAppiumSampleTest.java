package io.appium.java_client.youiengine;

import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.YouiEngineCapabilityType;
import io.appium.java_client.youiengine.frames.YouiEngineAppiumSampleApp;
import io.appium.java_client.youiengine.util.TestUtility;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.not;

/**
 * This YouiEngineAppiumSampleTest class is available to illustrate how to create a simple test
 * class.
 *
 * <p>Within this test class you will find several tests that are designed to interact with the
 * YouiEngineAppiumSampleTest.
 *
 * <p>Unlike the SanityTest class, this class does not extend the AppiumTest utility class.
 *
 * <p>Uncompress the iOS YouiEngineAppiumSample.app.zip before using this with iOS as a target.
 * */
public class YouiEngineAppiumSampleTest {

    static {
        // Disable annoying cookie warnings.
        // WARNING: Invalid cookie header
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",
                "org.apache.commons.logging.impl.NoOpLog");
    }

    public static TestUtility utils;
    public static YouiEngineDriver driver;
    public static URL serverAddress;
    public static YouiEngineAppiumSampleApp app;

    private boolean isAndroid;

    private DesiredCapabilities capabilities;

    // Adjust the capabilities you wish to use in here.
    private void setupCaps(String appPath) {
        capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.APP, appPath);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "YouiEngine");

        if (isAndroid) {
            // The lines below can be modified to target a device or an AVD. Update accordingly.
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "device name");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "android");
            capabilities.setCapability(YouiEngineCapabilityType.APP_ADDRESS, "ip.add.res");
            //capabilities.setCapability(AndroidMobileCapabilityType.AVD, "AVD name goes here");

        } else {
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "device name");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
            capabilities.setCapability(YouiEngineCapabilityType.APP_ADDRESS, "ip.add.res");
            //capabilities.setCapability(MobileCapabilityType.UDID, "some UDID goes here");
        }
    }

    @Rule
    public TestRule printTests = new TestWatcher() {
        protected void starting(Description description) {
            System.out.print("  test: " + description.getMethodName());
        }

        protected void finished(Description description) {
            System.out.println();
        }
    };

    /**
     * Run before each test.
     **/
    @Before
    public void setUp() throws Exception {
        // Toggle this to switch between Android and iOS
        isAndroid = false;

        String currentPath = System.getProperty("user.dir");
        String javaClientPath = "src/test/java/io/appium/java_client/";
        String appName = "YouiEngineAppiumSample";
        String fullAppName = isAndroid ? appName + "-debug.apk" : appName + ".app.zip";

        String iosAppPath = Paths.get(currentPath, javaClientPath + fullAppName).toAbsolutePath()
                .toString();
        String androidAppPath = Paths.get(currentPath, javaClientPath + fullAppName)
                .toAbsolutePath().toString();
        String myAppPath = isAndroid ? androidAppPath : iosAppPath;

        setupCaps(myAppPath);

        serverAddress = new URL("http://127.0.0.1:4723/wd/hub");

        driver = new YouiEngineDriver(serverAddress, capabilities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        app = new YouiEngineAppiumSampleApp(driver);
    }

    /**
     * Run after each test.
     **/
    @After
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
    }

    // Retrieves the source from the app and that it is not empty.
    @org.junit.Test
    public void pageSourceTest() throws Exception {
        String source = driver.getPageSource();

        System.out.println("\nPageSource: " + source);

        Assert.assertThat(source, not(""));
    }

    // Sets the input field's value then retrieves that value to confirm it was set.
    @org.junit.Test
    public void findInputFieldAndSetGetTextTest() throws Exception {
        final String expected = "Something";
        app.goToTextEditScreen();

        // Set the value of the field by sending it a sequence of keys.
        // If it was not found, then we would get an ElementNodeFoundException which will fail the
        // test.
        app.textEditScreen.setTextEditValue(expected);
        utils.delayInSeconds(2);

        Assert.assertEquals(expected, app.textEditScreen.getTextEditValue());
    }

    /* Sets the password field's value then retrieves that value to confirm it was set but also
     * that we cannot read it. It is a password field after all!
     * */
    @org.junit.Test
    public void findPasswordFieldAndSetGetTextTest() throws Exception {
        final String expected = "Something";

        app.goToTextEditScreen();
        app.textEditScreen.setTextEditValue(expected);
        utils.delayInSeconds(2);

        // Text returned from a get should be the masked text, not the real text we set because
        // this is a password field.
        Assert.assertThat(expected, not(app.textEditScreen.getPasswordEditValue()));
    }

    /* Click on the button 10 times. The button in this app is designed to update its caption
     * with the number of clicks. We can confirm it was clicked 10 times by checking this caption
     * value.
     * */
    @org.junit.Test
    public void findPushButtonAndClickSeveralTimesTest() throws Exception {
        final String expected = "Pushed 10 Times";

        app.goToButtonsScreen();

        for (int i = 0; i < 10; i++) {
            app.buttonsScreen.getPushButton().click();
            utils.delayInSeconds(1);
        }

        Assert.assertEquals(expected, app.buttonsScreen.getPushButtonCaption());
    }

    /* Click on the toggle button and confirm it is on or off. This toggle button updates its
     * caption to reflect what state it is in.
     *  */
    @org.junit.Test
    public void findToggleButtonAndToggleSeveralTimesTest() throws Exception {
        final String toggleOn = "Toggled ON";
        final String toggleOff = "Toggled OFF";

        app.goToButtonsScreen();

        // Toggle the button on
        app.buttonsScreen.getToggleButton().click();
        utils.delayInSeconds(2);

        Assert.assertEquals(toggleOn, app.buttonsScreen.getToggleButtonCaption());

        // Toggle the button off
        app.buttonsScreen.getToggleButton().click();

        Assert.assertEquals(toggleOff, app.buttonsScreen.getToggleButtonCaption());
    }

    /* Set the text of an input field and delete a portion of it using BACK_SPACE.
     *  */
    @org.junit.Test
    public void sendKeysBackspaceTest() throws Exception {
        String expectedText = "You.i";
        String deletedText = " Engine";
        String sentText = expectedText + deletedText;

        WebElement field = driver.findElement(By.name("TextEdit"));
        field.sendKeys(sentText);
        utils.delayInSeconds(2);
        Assert.assertEquals(sentText, field.getText());

        for (int i = 0; i < deletedText.length(); ++i) {
            field.sendKeys(Keys.BACK_SPACE);
        }
        Assert.assertEquals(expectedText, field.getText());
    }

    /* Set the text of an input field and delete a portion of it using ARROW_LEFT and DELETE.
     *  */
    @org.junit.Test
    public void sendKeysDeleteTest() throws Exception {
        String expectedText = "You.i";
        String deletedText = " Engine";
        String sentText = expectedText + deletedText;

        WebElement field = driver.findElement(By.name("TextEdit"));
        field.sendKeys(sentText);
        utils.delayInSeconds(2);
        Assert.assertEquals(sentText, field.getText());

        // move the cursor to the front of the text to be deleted
        for (int i = 0; i < deletedText.length(); ++i) {
            field.sendKeys(Keys.ARROW_LEFT);
        }
        utils.delayInSeconds(2);
        Assert.assertEquals(sentText, field.getText());

        for (int i = 0; i < deletedText.length(); ++i) {
            field.sendKeys(Keys.DELETE);
        }
        Assert.assertEquals(expectedText, field.getText());
    }
}
