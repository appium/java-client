package io.appium.java_client.pagefactory_tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.SelendroidFindAll;
import io.appium.java_client.pagefactory.SelendroidFindBy;
import io.appium.java_client.pagefactory.SelendroidFindBys;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;

import org.openqa.selenium.WebElement;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SelendroidModeTest {
	private static int SELENDROID_PORT = 9999;

    private WebDriver driver;

    @SelendroidFindBy(id = "text1")
    private WebElement textId;

    @AndroidFindBy(id = "Invalid Identifier")
    @SelendroidFindBy(id = "text1")
    private WebElement textSelendroidId;

    @SelendroidFindBy(name = "Accessibility")
    private WebElement textName;

    @AndroidFindBy(name = "Accessibility")
    private WebElement textNameAndroid;

    @FindBy(name = "Accessibility")
    private WebElement textNameDefault;

    @SelendroidFindBy(xpath = "//TextView[@value='Accessibility']")
    private WebElement textXpath;

    @SelendroidFindBys({
        @SelendroidFindBy(id = "text1")})
    private WebElement textIds;

    @SelendroidFindAll({
        @SelendroidFindBy(id = "text1")})
    private WebElement textAll;

    @SelendroidFindAll({
        @SelendroidFindBy(id = "text1")})
    private List<WebElement> textsAll;

    @SelendroidFindBy(className = "android.widget.TextView")
    private WebElement textClass;

    @SelendroidFindBy(tagName = "TextView")
    private WebElement textTag;
    
    @SelendroidFindBy(linkText = "Accessibility")
    private WebElement textLink;
    
    @SelendroidFindBy(partialLinkText = "ccessibilit")
    private WebElement textPartialLink;

	@Before
    public void setUp() throws Exception {
        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "ApiDemos-debug.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.SELENDROID);
        capabilities.setCapability(MobileCapabilityType.SELENDROID_PORT, SELENDROID_PORT);
        driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

        //This time out is set because test can be run on slow Android SDK emulator
        PageFactory.initElements(new AppiumFieldDecorator(driver, 5, TimeUnit.SECONDS), this);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void findByIdElementTest() {
        Assert.assertNotEquals(null, textId.getAttribute("text"));
    }
    
        @Test
    public void findBySelendroidSelectorTest() {
        Assert.assertNotEquals(null, textSelendroidId.getAttribute("text"));
    }

    @Test
    public void findByElementByNameTest() {
        Assert.assertEquals("Accessibility", textName.getText());
    }

    @Test
    public void findByElementByNameAndroidTest() {
        Assert.assertEquals("Accessibility", textNameAndroid.getText());
    }

    @Test
    public void findByElementByNameDefaultTest() {
        Assert.assertEquals("Accessibility", textNameDefault.getText());
    }

    @Test
    public void findByElementByXpathTest() {
        Assert.assertEquals("Accessibility", textXpath.getText());
    }

    @Test
    public void findByElementByIdsTest() {
        Assert.assertNotNull(textIds.getText());
    }

    @Test
    public void findByElementByTestAllTest() {
        Assert.assertNotNull(textAll.getText());
    }

    @Test
    public void findByElementByTextsAllTest() {
        Assert.assertTrue(textsAll.size() > 1);
    }

    @Test
    public void findByElementByCalssTest() {
        Assert.assertNotEquals(null, textClass.getAttribute("text"));
    }

    @Test
    public void findByElementByTagTest() {
        Assert.assertNotEquals(null, textTag.getAttribute("text"));
    }
    
    @Test
    public void findBySelendroidAnnotationOnlyTest() {
        Assert.assertNotEquals(null, textSelendroidId.getAttribute("text"));
    }
    
    @Test
    public void findBySelendroidLinkTextTest() {
        Assert.assertEquals("Accessibility", textLink.getText());

    }
    
    @Test
    public void findBySelendroidPartialLinkTextTest() {
        Assert.assertEquals("Accessibility", textPartialLink.getText());

    }
}
