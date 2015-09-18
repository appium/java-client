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

package io.appium.java_client.android;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;
import java.util.List;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.junit.*;
import org.openqa.selenium.remote.DesiredCapabilities;

public class AndroidAccessibilityTest {
	
	private AppiumDriver<MobileElement> driver;
    private static AppiumDriverLocalService service;

    @BeforeClass
    public static void beforeClass() throws Exception{
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();
    }

	@Before
	public void setUp() throws Exception {
        if (service == null || !service.isRunning())
            throw new RuntimeException("An appium server node is not started!");

	    File appDir = new File("src/test/java/io/appium/java_client");
	    File app = new File(appDir, "ApiDemos-debug.apk");
	    DesiredCapabilities capabilities = new DesiredCapabilities();
	    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
	    capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
	    driver = new AndroidDriver<MobileElement>(service.getUrl(), capabilities);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	  public void findElementsTest() {
	    List<MobileElement> elements = driver.findElementsByAccessibilityId("Accessibility");
	    assertTrue(elements.size() > 0);
	  }

	@Test
	  public void findElementTest() {
        //WebElement element =
	    MobileElement element = driver.findElementByAccessibilityId("Accessibility");
	    assertNotNull(element);
	  }

	@Test
	  public void MobileElementByTest() {
	    MobileElement element = driver.findElement(MobileBy.AccessibilityId("Accessibility"));
	    assertNotNull(element);
	  }

	@Test
	  public void MobileElementsByTest() {
	    List<MobileElement> elements = driver.findElements(MobileBy.AccessibilityId("Accessibility"));
	    assertTrue(elements.size() > 0);
	  }

    @AfterClass
    public static void afterClass(){
        if (service != null)
        service.stop();
    }

}
