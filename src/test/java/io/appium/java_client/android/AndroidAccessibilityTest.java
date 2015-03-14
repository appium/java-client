package io.appium.java_client.android;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

public class AndroidAccessibilityTest {
	
	private AppiumDriver driver;

	@Before
	public void setUp() throws Exception {
	    File appDir = new File("src/test/java/io/appium/java_client");
	    File app = new File(appDir, "ApiDemos-debug.apk");
	    DesiredCapabilities capabilities = new DesiredCapabilities();
	    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
	    capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
	    driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	  public void findElementsTest() {
	    List<WebElement> elements = driver.findElementsByAccessibilityId("Accessibility");
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
	    WebElement element = driver.findElement(MobileBy.AccessibilityId("Accessibility"));
	    assertNotNull(element);
	  }

	@Test
	  public void MobileElementsByTest() {
	    List<WebElement> elements = driver.findElements(MobileBy.AccessibilityId("Accessibility"));
	    assertTrue(elements.size() > 0);
	  }

}
