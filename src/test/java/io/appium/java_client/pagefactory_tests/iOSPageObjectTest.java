package io.appium.java_client.pagefactory_tests;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindAll;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class iOSPageObjectTest {

	private WebDriver driver;
	@FindBy(className = "UIAButton")
	private List<WebElement> uiButtons;

	@FindBy(className = "UIAButton")
	private List<WebElement> iosUIButtons;

	@iOSFindBy(uiAutomator = ".elements()[0]")
	private List<WebElement> iosUIAutomatorButtons;

	@iOSFindBy(uiAutomator = ".elements()[0]")
	@AndroidFindBy(className = "android.widget.TextView")
	private List<WebElement> androidOriOsTextViews;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/text1\")")
	private List<WebElement> androidUIAutomatorViews;

	@iOSFindBy(uiAutomator = ".elements()[0]")
	private List<MobileElement> mobileButtons;

	@FindBy(className = "UIAButton")
	private List<MobileElement> mobiletFindBy_Buttons;

	@iOSFindBy(uiAutomator = ".elements()[0]")
	private List<RemoteWebElement> remoteElementViews;

	@AndroidFindBys({
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/list\")"),
		@AndroidFindBy(className = "android.widget.TextView")
		})
	private List<WebElement> chainElementViews;


	@FindBy(className = "UIAButton")
	private WebElement uiButton;

	@FindBy(className = "UIAButton")
	private WebElement iosUIButton;

	@iOSFindBy(uiAutomator = ".elements()[0]")
	private WebElement iosUIAutomatorButton;

	@AndroidFindBy(className = "android.widget.TextView")
	@iOSFindBy(uiAutomator = ".elements()[0]")
	private WebElement androidOriOsTextView;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/text1\")")
	private WebElement androidUIAutomatorView;

	@iOSFindBy(uiAutomator = ".elements()[0]")
	private MobileElement mobileButton;

	@FindBy(className = "UIAButton")
	private MobileElement mobiletFindBy_Button;

	@iOSFindBy(uiAutomator = ".elements()[0]")
	private RemoteWebElement remotetextVieW;

	@AndroidFindBys({
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/list\")"),
		@AndroidFindBy(className = "android.widget.TextView")
		})
	private WebElement chainElementView;
	
	@iOSFindBy(uiAutomator = ".elements()[0]")
	private IOSElement iosButton;
	
	@iOSFindBy(uiAutomator = ".elements()[0]")
	private List<IOSElement> iosButtons;
	
	@iOSFindAll({
		@iOSFindBy(xpath = "ComputeSumButton_Test"),	
		@iOSFindBy(name = "ComputeSumButton")	//it is real locator
	})
	private WebElement findAllElement;
	
	@iOSFindAll({
		@iOSFindBy(xpath = "ComputeSumButton_Test"),	
		@iOSFindBy(name = "ComputeSumButton")	//it is real locator
	})
	private List<WebElement> findAllElements;

	@SuppressWarnings("rawtypes")
	@Before
	public void setUp() throws Exception {
	    File appDir = new File("src/test/java/io/appium/java_client");
	    File app = new File(appDir, "TestApp.app.zip");
	    DesiredCapabilities capabilities = new DesiredCapabilities();
	    capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
	    capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.1");
	    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
	    capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
	    driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void findByElementsTest() {
		Assert.assertNotEquals(0, uiButtons.size());
	}

	@Test
	public void findByElementTest() {
		Assert.assertNotEquals(null, uiButton.getText());
	}


	@Test
	public void iOSFindByElementsTest(){
		Assert.assertNotEquals(0, iosUIButtons.size());
	}

	@Test
	public void iosFindByElementTest(){
		Assert.assertNotEquals(null, iosUIButton.getText());
	}

	@Test
	public void checkThatElementsWereNotFoundByAndroidUIAutomator(){
		Assert.assertEquals(0, androidUIAutomatorViews.size());
	}

	@Test
	public void checkThatElementWasNotFoundByAndroidUIAutomator(){
		NoSuchElementException nsee = null;
		try{
			androidUIAutomatorView.getText();
		}
		catch (Exception e){
			nsee = (NoSuchElementException) e;
		}
		Assert.assertNotNull(nsee);
	}

	@Test
	public void androidOrIOSFindByElementsTest(){
		Assert.assertNotEquals(0, androidOriOsTextViews.size());
	}

	@Test
	public void androidOrIOSFindByElementTest(){
		Assert.assertNotEquals(null, androidOriOsTextView.getText());
	}

	@Test
	public void iOSFindByUIAutomatorElementsTest(){
		Assert.assertNotEquals(0, iosUIAutomatorButtons.size());
	}

	@Test
	public void iOSFindByUIAutomatorElementTest(){
		Assert.assertNotEquals(null, iosUIAutomatorButton.getText());
	}

	@Test
	public void areMobileElementsTest(){
		Assert.assertNotEquals(0, mobileButtons.size());
	}

	@Test
	public void isMobileElementTest(){
		Assert.assertNotEquals(null, mobileButton.getText());
	}

	@Test
	public void areMobileElements_FindByTest(){
		Assert.assertNotEquals(0, mobiletFindBy_Buttons.size());
	}

	@Test
	public void isMobileElement_FindByTest(){
		Assert.assertNotEquals(null, mobiletFindBy_Button.getText());
	}

	@Test
	public void areRemoteElementsTest(){
		Assert.assertNotEquals(0, remoteElementViews.size());
	}

	@Test
	public void isRemoteElementTest(){
		Assert.assertNotEquals(null, remotetextVieW.getText());
	}

	@Test
	public void checkThatElementsWereNotFoundByAndroidUIAutomator_Chain(){
		Assert.assertEquals(0, chainElementViews.size());
	}

	@Test
	public void checkThatElementWasNotFoundByAndroidUIAutomator_Chain(){
		NoSuchElementException nsee = null;
		try{
			chainElementView.getText();
		}
		catch (Exception e){
			nsee = (NoSuchElementException) e;
		}
		Assert.assertNotNull(nsee);
	}
	
	@Test
	public void isIOSElementTest(){
		Assert.assertNotEquals(null, iosButton.getText());
	}

	@Test
	public void areIOSElements_FindByTest(){
		Assert.assertNotEquals(0, iosButtons.size());
	}

	@Test
	public void findAllElementsTest(){
		Assert.assertNotEquals(0, findAllElements.size());
	}

	@Test
	public void findAllElementTest(){
		Assert.assertNotEquals(null, findAllElement.getText());
	}
}
