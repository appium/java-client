package io.appium.java_client.pagefactory_tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory_tests.blocks.AndroidPageBlock;
import io.appium.java_client.pagefactory_tests.blocks.FindAllBlock;
import io.appium.java_client.pagefactory_tests.blocks.FindBysBlock;
import io.appium.java_client.pagefactory_tests.blocks.ListItemBlock;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AndroidPageObjectFindBysBlocksTest {

	private WebDriver driver;

	FindBysBlock findBysBlock;

	@SuppressWarnings("rawtypes")
	@Before
	public void setUp() throws Exception {
	    File appDir = new File("src/test/java/io/appium/java_client");
	    File app = new File(appDir, "ApiDemos-debug.apk");
	    DesiredCapabilities capabilities = new DesiredCapabilities();
	    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
	    capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
	    driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

	    //This time out is set because test can be run on slow Android SDK emulator
		PageFactory.initElements(new AppiumFieldDecorator(driver, 5, TimeUnit.SECONDS), this);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void findBysBlockBlocksTest() {
		Assert.assertNotEquals(null, findBysBlock.items.get(0).getAttribute("text"));
	}

	@Test
	public void findBysBlockItemTest() {
		Assert.assertNotEquals(null, findBysBlock.item.getAttribute("text"));
	}

}
