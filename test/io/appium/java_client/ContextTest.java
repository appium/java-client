package io.appium.java_client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.assertEquals;

/**
 * Test context-related features
 */
public class ContextTest {

  private AppiumDriver driver;

  @Before
  public void setup() throws Exception {
    File appDir = new File("test/io/appium/java_client");
    File app = new File(appDir, "WebViewApp.app");
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
  public void testGetContext() {
    boolean assertion = driver.getContext() == null;
    assert(assertion);
  }

  @Test
  public void testGetContextHandles() {
    assertEquals(driver.getContextHandles().size(), 2);
  }

  @Test
  public void testSwitchContext() {
    driver.getContextHandles();
    driver.context("WEBVIEW_1");
    assertEquals(driver.getContext(), "WEBVIEW_1");
  }

  @Test(expected = NoSuchContextException.class)
  public void testContextError() {
    driver.context("Planet of the Ape-ium");
  }

}
