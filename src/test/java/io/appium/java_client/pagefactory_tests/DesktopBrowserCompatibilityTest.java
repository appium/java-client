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

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.pagefactory.iOSFindBys;
import org.junit.Test;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DesktopBrowserCompatibilityTest {

	private static enum AvailableDrivers {
		FIREFOX(FirefoxDriver.class, new ArrayList<Platform>() {
			private static final long serialVersionUID = 1L;
			{
				add(Platform.WINDOWS);
				add(Platform.MAC);
			}

		}, new HashMap<Platform, File>(), null),

        CHROME(ChromeDriver.class,
				new ArrayList<Platform>() {
					private static final long serialVersionUID = 1L;
					{
						add(Platform.WINDOWS);
						add(Platform.MAC);
					}

				}, new HashMap<Platform, File>() {
					private static final long serialVersionUID = 1L;

					{
						put(Platform.WINDOWS,
								new File(
										"src/test/java/io/appium/java_client/pagefactory_tests/chromedriver.exe"));
						put(Platform.MAC,
								new File(
										"src/test/java/io/appium/java_client/pagefactory_tests/chromedriver"));
					}
				}, ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY),
        SAFARI(
				SafariDriver.class, new ArrayList<Platform>() {
					private static final long serialVersionUID = 1L;
					{
						add(Platform.MAC);
					}

				}, new HashMap<Platform, File>(), null);
		// TODO Linux can be added if is necessary

		private final Class<? extends WebDriver> driverClazz;
		private final List<Platform> platformCompatible;
		private final Map<Platform, File> serviceBinaries;
		private final String propertyName;

		private AvailableDrivers(Class<? extends WebDriver> driverClazz,
				List<Platform> platformCompatible,
				Map<Platform, File> serviceBinaries, String property) {
			this.driverClazz = driverClazz;
			this.platformCompatible = platformCompatible;
			this.serviceBinaries = serviceBinaries;
			this.propertyName = property;
		}

		private static AvailableDrivers getAvailableDriver(
				Class<? extends WebDriver> driverClass, Platform p) {
			AvailableDrivers[] availableDrivers = AvailableDrivers.values();
			for (AvailableDrivers availableDriver : availableDrivers) {
				if (!availableDriver.driverClazz.equals(driverClass)){
					continue;
				}
				
				for (Platform compatible: availableDriver.platformCompatible){
					if (p.is(compatible)){
						return availableDriver;
					}
				}
			}
			return null;
		}

		private void setSystemProperty(Platform p) {
			Platform platform = null;
			for (Platform compatible: platformCompatible){
				if (p.is(compatible)){
					platform = compatible;
					break;
				}
			}
			
			if ((platform != null) && (propertyName != null)
					&& (serviceBinaries.get(platform) != null)) {
				System.setProperty(propertyName, serviceBinaries.get(platform)
						.getAbsolutePath());
			}
		}
	}
	

	public void setUp(Class<? extends WebDriver> driverClass) {
		AvailableDrivers availableDriver = AvailableDrivers.getAvailableDriver(driverClass, current);
		if (availableDriver != null){
			availableDriver.setSystemProperty(current);
		}
	}
	
	private final Platform current = Platform.getCurrent();
	private final long IMPLICITLY_WAIT = 15;
	


	
	@AndroidFindBy(className = "someClass")
	@iOSFindBys({
		@iOSFindBy(xpath = "//selector[1]"),
		@iOSFindBy(xpath = "//someTag")})
	@FindBys({@FindBy(id="main"), @FindBy(tagName = "p")})
	private List<WebElement> foundLinks;
	
	private List<WebElement> main; //this list is located by id="main"

	private WebDriver trap1;
	private List<AndroidDriver<?>> trap2;
	
	private void test(WebDriver driver){
		try {
			PageFactory.initElements(new AppiumFieldDecorator(driver, IMPLICITLY_WAIT, TimeUnit.SECONDS), this);
			driver.get("file:///" + new File("src/test/java/io/appium/java_client/hello appium - saved page.htm").getAbsolutePath());
			assertNotEquals(0, foundLinks.size());
			assertNotEquals(0, main.size());
			assertEquals(null, trap1);
			assertEquals(null, trap2);
		} finally {
			driver.quit();
		}
	}

	@Test
	public void fireFoxTest() {
		if (AvailableDrivers.getAvailableDriver(FirefoxDriver.class, current)!=null){
			setUp(FirefoxDriver.class);
			test(new FirefoxDriver());
		}		
	}
	
	@Test
	public void chromeTest() {
		System.getProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY);
		if (AvailableDrivers.getAvailableDriver(ChromeDriver.class, current)!=null){
			setUp(ChromeDriver.class);
			test(new ChromeDriver());
		}		
	}
	
	@Test
	public void safariTest() {
		if (AvailableDrivers.getAvailableDriver(SafariDriver.class, current)!=null){
			setUp(SafariDriver.class);
			test(new SafariDriver());
		}		
	}		

}
