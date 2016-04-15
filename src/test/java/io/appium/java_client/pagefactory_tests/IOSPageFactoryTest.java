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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchableElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindAll;
import io.appium.java_client.pagefactory.iOSFindBy;

import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.util.List;

public class IOSPageFactoryTest {

    private static WebDriver driver;
    private static AppiumDriverLocalService service;
    private boolean populated = false;

    @FindBy(className = "UIAButton") private List<WebElement> uiButtons;

    @FindBy(className = "UIAButton") private List<WebElement> iosUIButtons;

    @iOSFindBy(uiAutomator = ".elements()[0]") private List<WebElement> iosUIAutomatorButtons;

    @iOSFindBy(uiAutomator = ".elements()[0]") @AndroidFindBy(className = "android.widget.TextView")
    private List<WebElement> androidOriOsTextViews;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/text1\")")
    private List<WebElement> androidUIAutomatorViews;

    @iOSFindBy(uiAutomator = ".elements()[0]") private List<MobileElement> mobileButtons;

    @FindBy(className = "UIAButton") private List<MobileElement> mobiletFindByButtons;

    @iOSFindBy(uiAutomator = ".elements()[0]") private List<RemoteWebElement> remoteElementViews;

    @AndroidFindBys({
            @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/list\")"),
            @AndroidFindBy(className = "android.widget.TextView")}) private List<WebElement>
            chainElementViews;


    @FindBy(className = "UIAButton") private WebElement uiButton;

    @FindBy(className = "UIAButton") private WebElement iosUIButton;

    @iOSFindBy(uiAutomator = ".elements()[0]") private WebElement iosUIAutomatorButton;

    @AndroidFindBy(className = "android.widget.TextView") @iOSFindBy(uiAutomator = ".elements()[0]")
    private WebElement androidOriOsTextView;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/text1\")")
    private WebElement androidUIAutomatorView;

    @iOSFindBy(uiAutomator = ".elements()[0]") private MobileElement mobileButton;

    @iOSFindBy(uiAutomator = ".elements()[0]") private TouchableElement touchableButton;

    @iOSFindBy(uiAutomator = ".elements()[0]") private List<TouchableElement> touchableButtons;

    @FindBy(className = "UIAButton") private MobileElement mobiletFindByButton;

    @iOSFindBy(uiAutomator = ".elements()[0]") private RemoteWebElement remotetextVieW;

    @AndroidFindBys({
            @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/list\")"),
            @AndroidFindBy(className = "android.widget.TextView")})
    private WebElement chainElementView;

    @iOSFindBy(uiAutomator = ".elements()[0]") private IOSElement iosButton;

    @iOSFindBy(uiAutomator = ".elements()[0]") private List<IOSElement> iosButtons;

    @iOSFindAll({@iOSFindBy(id = "ComputeSumButton_Test"),
            @iOSFindBy(xpath = "//*[@name = \"ComputeSumButton\"]")  //it is real locator
    }) private WebElement findAllElement;

    @iOSFindAll({@iOSFindBy(id = "ComputeSumButton_Test"),
            @iOSFindBy(xpath = "//*[@name = \"ComputeSumButton\"]")  //it is real locator
    }) private List<WebElement> findAllElements;

    @AndroidFindBy(className = "android.widget.TextView") @FindBy(css = "e.e1.e2")
    private List<WebElement> elementsWhenAndroidLocatorIsNotDefinedAndThereIsInvalidFindBy;

    @AndroidFindBy(className = "android.widget.TextView") @FindBy(css = "e.e1.e2")
    private WebElement elementWhenAndroidLocatorIsNotDefinedAndThereIsInvalidFindBy;

    /**
     * initialization.
     */
    @BeforeClass public static void beforeClass() throws Exception {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();

        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "TestApp.app.zip");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.2");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
        //sometimes environment has performance problems
        capabilities.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 500000);
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        driver = new IOSDriver(service.getUrl(), capabilities);
    }

    /**
     * finishing.
     */
    @AfterClass public static void afterClass() throws Exception {
        if (driver != null) {
            driver.quit();
        }

        if (service != null) {
            service.stop();
        }
    }

    /**
     * The setting up.
     */
    @Before public void setUp() throws Exception {
        if (!populated) {
            PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        }

        populated = true;
    }

    @Test public void findByElementsTest() {
        assertNotEquals(0, uiButtons.size());
    }

    @Test public void findByElementTest() {
        assertNotEquals(null, uiButton.getText());
    }


    @Test public void iosFindByElementsTest() {
        assertNotEquals(0, iosUIButtons.size());
    }

    @Test public void iosFindByElementTest() {
        assertNotEquals(null, iosUIButton.getText());
    }

    @Test public void checkThatElementsWereNotFoundByAndroidUIAutomator() {
        assertEquals(0, androidUIAutomatorViews.size());
    }

    @Test public void checkThatElementWasNotFoundByAndroidUIAutomator() {
        NoSuchElementException nsee = null;
        try {
            androidUIAutomatorView.getText();
        } catch (Exception e) {
            nsee = (NoSuchElementException) e;
        }
        assertNotNull(nsee);
    }

    @Test public void androidOriOSFindByElementsTest() {
        assertNotEquals(0, androidOriOsTextViews.size());
    }

    @Test public void androidOrIOSFindByElementTest() {
        assertNotEquals(null, androidOriOsTextView.getText());
    }

    @Test public void iosFindByUIAutomatorElementsTest() {
        assertNotEquals(0, iosUIAutomatorButtons.size());
    }

    @Test public void iosFindByUIAutomatorElementTest() {
        assertNotEquals(null, iosUIAutomatorButton.getText());
    }

    @Test public void areMobileElementsTest() {
        assertNotEquals(0, mobileButtons.size());
    }

    @Test public void isMobileElementTest() {
        assertNotEquals(null, mobileButton.getText());
    }

    @Test public void areMobileElements_FindByTest() {
        assertNotEquals(0, mobiletFindByButtons.size());
    }

    @Test public void isMobileElement_FindByTest() {
        assertNotEquals(null, mobiletFindByButton.getText());
    }

    @Test public void areRemoteElementsTest() {
        assertNotEquals(0, remoteElementViews.size());
    }

    @Test public void isRemoteElementTest() {
        assertNotEquals(null, remotetextVieW.getText());
    }

    @Test public void checkThatElementsWereNotFoundByAndroidUIAutomator_Chain() {
        assertEquals(0, chainElementViews.size());
    }

    @Test public void checkThatElementWasNotFoundByAndroidUIAutomator_Chain() {
        NoSuchElementException nsee = null;
        try {
            chainElementView.getText();
        } catch (Exception e) {
            nsee = (NoSuchElementException) e;
        }
        assertNotNull(nsee);
    }

    @Test public void isIOSElementTest() {
        assertNotEquals(null, iosButton.getText());
    }

    @Test public void areIOSElements_FindByTest() {
        assertNotEquals(0, iosButtons.size());
    }

    @Test public void findAllElementsTest() {
        assertNotEquals(0, findAllElements.size());
    }

    @Test public void findAllElementTest() {
        assertNotEquals(null, findAllElement.getText());
    }

    @Test public void isTouchAbleElement() {
        assertNotEquals(null, touchableButton.getText());
    }

    @Test public void areTouchAbleElements() {
        assertNotEquals(0, touchableButtons.size());
    }

    @SuppressWarnings("unused")
    @Test public void isTheFieldIOSElement() {
        IOSElement iOSElement =
                (IOSElement) mobileButton; //declared as MobileElement
        iOSElement = (IOSElement) iosUIAutomatorButton; //declared as WebElement
        iOSElement = (IOSElement) remotetextVieW;  //declared as RemoteWebElement
        iOSElement = (IOSElement) touchableButton; //declared as TouchABLEElement
    }

    @Test public void checkThatTestWillNotBeFailedBecauseOfInvalidFindBy() {
        try {
            assertNotEquals(null,
                    elementWhenAndroidLocatorIsNotDefinedAndThereIsInvalidFindBy
                            .getAttribute("text"));
        } catch (NoSuchElementException ignored) {
            return;
        }
        throw new RuntimeException(NoSuchElementException.class.getName() + " has been expected.");
    }

    @Test public void checkThatTestWillNotBeFailedBecauseOfInvalidFindBy_List() {
        assertEquals(0, elementsWhenAndroidLocatorIsNotDefinedAndThereIsInvalidFindBy.size());
    }
}
