package io.appium.java_client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test -ios uiautomation locator strategy
 */
public class IosUIAutomationTest {

  private AppiumDriver driver;

  @Before
  public void setup() throws Exception {
    File appDir = new File("test/io/appium/java_client");
    File app = new File(appDir, "UICatalog.app.zip");
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
    capabilities.setCapability(CapabilityType.VERSION, "7.1");
    capabilities.setCapability(CapabilityType.PLATFORM, "Mac");
    capabilities.setCapability("device", "iPhone Simulator");
    capabilities.setCapability("app", app.getAbsolutePath());
    driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
  }

  @Test
  public void findElementTest() {
    WebElement element = driver.findElementByIosUIAutomation(".elements()[0]");
    assertEquals(element.getAttribute("name"), "UICatalog");
  }

  @Test
  public void findElementsTest() {
    List<WebElement> elements = driver.findElementsByIosUIAutomation("elements()");
    assertEquals(3, elements.size());
  }

  @Test
  public void MobileElementByTest() {
    WebElement element = driver.findElement(MobileBy.IosUIAutomation(".elements()[0]"));
    assertEquals(element.getAttribute("name"), "UICatalog");
  }

  @Test
  public void MobileElementsByTest() {
    List<WebElement> elements = driver.findElements(MobileBy.IosUIAutomation(".elements()"));
    assertEquals(3, elements.size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void ErrorTest() {
    driver.findElementByIosUIAutomation(null);
  }
}
