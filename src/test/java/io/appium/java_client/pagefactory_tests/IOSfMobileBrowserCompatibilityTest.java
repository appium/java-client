/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.appium.java_client.pagefactory_tests;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.service.local.AppiumDriverLocalService;
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
    private AppiumDriverLocalService service;
	
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
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();

        DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.SAFARI);
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.1");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
		driver = new IOSDriver<>(service.getUrl(), capabilities);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 5, TimeUnit.SECONDS), this);
	}

	@After
	public void tearDown() throws Exception {
        if (driver != null)
            driver.quit();

        if (service != null)
            service.stop();
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
