package io.appium.java_client.ios;

import io.appium.java_client.MobileBy;
import io.appium.java_client.remote.MobileCapabilityType;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.junit.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test -ios uiautomation locator strategy
 */
public class IosUIAutomationTest {

  private IOSDriver<WebElement> driver;
  private static AppiumDriverLocalService service;

  @BeforeClass
  public static void beforeClass() throws Exception{
    service = AppiumDriverLocalService.buildDefaultService();
    service.start();
  }

  @Before
  public void setup() throws Exception {
    if (service == null || !service.isRunning())
      throw new RuntimeException("An appium server node is not started!");

    File appDir = new File("src/test/java/io/appium/java_client");
    File app = new File(appDir, "UICatalog.app.zip");
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
    capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.1");
    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
    capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
    driver = new IOSDriver<WebElement>(service.getUrl(), capabilities);
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
    List<WebElement> elements = driver.findElementsByIosUIAutomation(".elements()");
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

  @AfterClass
  public static void afterClass(){
    if (service != null)
      service.stop();
  }
}
