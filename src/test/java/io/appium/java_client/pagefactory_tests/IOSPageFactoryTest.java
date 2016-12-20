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

import static io.appium.java_client.pagefactory.LocatorGroupStrategy.ALL_POSSIBLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import io.appium.java_client.MobileElement;

import io.appium.java_client.ios.AppIOSTest;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class IOSPageFactoryTest extends AppIOSTest {

    private boolean populated = false;

    @FindBy(className = "UIAButton")
    private List<WebElement> uiButtons;

    @FindBy(className = "UIAButton")
    private List<WebElement> iosUIButtons;

    @iOSFindBy(uiAutomator = ".elements()[0]")
    private List<WebElement> iosUIAutomatorButtons;

    @iOSFindBy(uiAutomator = ".elements()[0]")
    @AndroidFindBy(className = "android.widget.TextView")
    private List<WebElement> androidOriOsTextViews;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/text1\")")
    private List<WebElement> androidUIAutomatorViews;

    @iOSFindBy(uiAutomator = ".elements()[0]")
    private List<MobileElement> mobileButtons;

    @FindBy(className = "UIAButton")
    private List<MobileElement> mobiletFindByButtons;

    @iOSFindBy(uiAutomator = ".elements()[0]")
    private List<RemoteWebElement> remoteElementViews;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/list\")")
    @AndroidFindBy(className = "android.widget.TextView")
    private List<WebElement> chainElementViews;


    @FindBy(className = "UIAButton")
    private WebElement uiButton;

    @CacheLookup
    @FindBy(className = "UIAButton")
    private MobileElement cached;

    @FindBy(className = "UIAButton")
    private WebElement iosUIButton;

    @iOSFindBy(uiAutomator = ".elements()[0]")
    private WebElement iosUIAutomatorButton;

    @AndroidFindBy(className = "android.widget.TextView") @iOSFindBy(uiAutomator = ".elements()[0]")
    private WebElement androidOriOsTextView;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/text1\")")
    private WebElement androidUIAutomatorView;

    @iOSFindBy(uiAutomator = ".elements()[0]")
    private MobileElement mobileButton;

    @FindBy(className = "UIAButton")
    private MobileElement mobiletFindByButton;

    @iOSFindBy(uiAutomator = ".elements()[0]")
    private RemoteWebElement remotetextVieW;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/list\")")
    @AndroidFindBy(className = "android.widget.TextView")
    private WebElement chainElementView;

    @iOSFindBy(uiAutomator = ".elements()[0]")
    private IOSElement iosButton;

    @iOSFindBy(uiAutomator = ".elements()[0]")
    private List<IOSElement> iosButtons;

    @HowToUseLocators(iOSAutomation = ALL_POSSIBLE)
    @iOSFindBy(id = "ComputeSumButton_Test")
    @iOSFindBy(xpath = "//*[@name = \"ComputeSumButton\"]")
    private WebElement findAllElement;

    @HowToUseLocators(iOSAutomation = ALL_POSSIBLE)
    @iOSFindBy(id = "ComputeSumButton_Test")
    @iOSFindBy(xpath = "//*[@name = \"ComputeSumButton\"]")
    private List<WebElement> findAllElements;

    @AndroidFindBy(className = "android.widget.TextView") @FindBy(css = "e.e1.e2")
    private List<WebElement> elementsWhenAndroidLocatorIsNotDefinedAndThereIsInvalidFindBy;

    @AndroidFindBy(className = "android.widget.TextView") @FindBy(css = "e.e1.e2")
    private WebElement elementWhenAndroidLocatorIsNotDefinedAndThereIsInvalidFindBy;

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

    @Test public void checkCached() {
        assertEquals(cached.getId(), cached.getId());
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

    @Test public void areMobileElementsFindByTest() {
        assertNotEquals(0, mobiletFindByButtons.size());
    }

    @Test public void isMobileElementFindByTest() {
        assertNotEquals(null, mobiletFindByButton.getText());
    }

    @Test public void areRemoteElementsTest() {
        assertNotEquals(0, remoteElementViews.size());
    }

    @Test public void isRemoteElementTest() {
        assertNotEquals(null, remotetextVieW.getText());
    }

    @Test public void checkThatElementsWereNotFoundByAndroidUIAutomatorChain() {
        assertEquals(0, chainElementViews.size());
    }

    @Test public void checkThatElementWasNotFoundByAndroidUIAutomatorChain() {
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

    @Test public void areIOSElementsFindByTest() {
        assertNotEquals(0, iosButtons.size());
    }

    @Test public void findAllElementsTest() {
        assertNotEquals(0, findAllElements.size());
    }

    @Test public void findAllElementTest() {
        assertNotEquals(null, findAllElement.getText());
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

    @Test public void checkThatTestWillNotBeFailedBecauseOfInvalidFindByList() {
        assertEquals(0, elementsWhenAndroidLocatorIsNotDefinedAndThereIsInvalidFindBy.size());
    }
}
