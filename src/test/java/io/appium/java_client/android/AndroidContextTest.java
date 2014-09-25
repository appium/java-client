package io.appium.java_client.android;

import static org.junit.Assert.assertEquals;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.NoSuchContextException;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

public class AndroidContextTest {

	private AppiumDriver driver;

	@Before
	public void setUp() throws Exception {
		File appDir = new File("src/test/java/io/appium/java_client");
		File app = new File(appDir, "selendroid-test-app-0.9.0.apk");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
		capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Selendroid");
		capabilities.setCapability(MobileCapabilityType.SELENDROID_PORT, "5000");
		
		//TODO When Selendroid is launched there is no possibility to change activity
		//TODO To be investigated. Is it bug?
		
		//capabilities.setCapability(MobileCapabilityType.APP_PACKAGE,"io.selendroid.testapp");
		//capabilities.setCapability(MobileCapabilityType.APP_ACTIVITY,"WebViewActivity");
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),
				capabilities);
		
		//TODO this is workaround to run the test.
		driver.findElement(By.id("buttonStartWebview")).click();
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void testGetContext() {
		boolean assertion = driver.getContext() == null;
		assert (assertion);
	}

	@Test
	public void testGetContextHandles() {
		assertEquals(driver.getContextHandles().size(), 2);
	}

	@Test
	public void testSwitchContext() {
		driver.getContextHandles();
		driver.context("WEBVIEW_0");
		assertEquals(driver.getContext(), "WEBVIEW_0");
	}

	@Test(expected = NoSuchContextException.class)
	public void testContextError() {
		driver.context("This is the fake context! Ha-ha-ha >:(");
	}

}
