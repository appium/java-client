package io.appium.java_client.pagefactory_tests;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

public class IOSfMobileBrowserCompatibilityTest {

	private WebDriver driver;
	
	@FindBy(name = "q")
	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/someId\")")
	@iOSFindBy(className = "someClass")
	private WebElement searchTextField;
	
	@AndroidFindBys({
		@AndroidFindBy(className = "someClass"),
		@AndroidFindBy(xpath = "//someTag")})
	@iOSFindBy(className = "someClass")
	@FindBy(name="btnG")
	private RemoteWebElement searchButton;
	
	@AndroidFindBy(className = "someClass")	
	@FindBys({@FindBy(className = "r"), @FindBy(tagName = "a")})
	@iOSFindBy(className = "someClass")
	private List<WebElement> foundLinks;
	
	@Before
	public void setUp() throws Exception {
		 DesiredCapabilities capabilities = new DesiredCapabilities();
		 capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.SAFARI);
		 capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.1");
		 capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
		 driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		 PageFactory.initElements(new AppiumFieldDecorator(driver, 5, TimeUnit.SECONDS), this);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void test() {
		driver.get("https://www.google.com");

		searchTextField.sendKeys("Hello");
		searchButton.click();
		Assert.assertNotEquals(0, foundLinks.size());
		searchTextField.clear();
		searchTextField.sendKeys("Hello, Appium!");
		searchButton.click();
	}

}
