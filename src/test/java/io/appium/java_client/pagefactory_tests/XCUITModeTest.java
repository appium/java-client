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
import static io.appium.java_client.pagefactory.LocatorGroupStrategy.CHAIN;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.AppIOSTest;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class XCUITModeTest extends AppIOSTest {

    private boolean populated = false;
    private WebDriverWait waiting = new WebDriverWait(driver, 10000);

    @HowToUseLocators(iOSXCUITAutomation = ALL_POSSIBLE)
    @iOSXCUITFindBy(iOSNsPredicate = "label contains 'Compute'")
    @iOSXCUITFindBy(className = "XCUIElementTypeButton")
    private MobileElement computeButton;

    @HowToUseLocators(iOSXCUITAutomation = CHAIN)
    @iOSXCUITFindBy(iOSNsPredicate = "name like 'Answer'")
    private WebElement answer;

    @iOSXCUITFindBy(iOSNsPredicate = "name = 'IntegerA'")
    private MobileElement textField1;

    @HowToUseLocators(iOSXCUITAutomation = ALL_POSSIBLE)
    @iOSXCUITFindBy(iOSNsPredicate = "name = 'IntegerB'")
    @iOSXCUITFindBy(accessibility = "IntegerB")
    private MobileElement textField2;

    @iOSXCUITFindBy(iOSNsPredicate = "name ENDSWITH 'Gesture'")
    private MobileElement gesture;

    @iOSXCUITFindBy(className = "XCUIElementTypeSlider")
    private MobileElement slider;

    @iOSXCUITFindBy(id = "locationStatus")
    private MobileElement locationStatus;

    @HowToUseLocators(iOSXCUITAutomation = CHAIN)
    @iOSXCUITFindBy(iOSNsPredicate = "name BEGINSWITH 'contact'")
    private MobileElement contactAlert;

    @HowToUseLocators(iOSXCUITAutomation = ALL_POSSIBLE)
    @iOSXCUITFindBy(iOSNsPredicate = "name BEGINSWITH 'location'")
    private MobileElement locationAlert;

    @iOSXCUITFindBy(iOSClassChain = "XCUIElementTypeWindow/*/XCUIElementTypeTextField[2]")
    private MobileElement secondTextField;

    @iOSXCUITFindBy(iOSClassChain = "XCUIElementTypeWindow/*/XCUIElementTypeButton[-1]")
    private MobileElement lastButton;

    @iOSXCUITFindBy(iOSClassChain = "XCUIElementTypeWindow/*/XCUIElementTypeButton")
    private List<MobileElement> allButtons;

    /**
     * The setting up.
     */
    @Before public void setUp() {
        if (!populated) {
            PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        }

        populated = true;
    }

    @Test public void findByXCUITSelectorTest() {
        assertNotEquals(null, computeButton.getText());
    }

    @Test public void findElementByNameTest() {
        assertEquals("TextField1", textField1.getText());
    }

    @Test public void findElementByClassNameTest() {
        assertEquals("50%", slider.getAttribute("value"));
    }

    @Test public void pageObjectChainingTest() {
        assertTrue(contactAlert.isDisplayed());
    }

    @Test public void findElementByIdTest() {
        assertTrue(locationStatus.isDisplayed());
    }

    @Test public void nativeSelectorTest() {
        assertTrue(locationAlert.isDisplayed());
    }

    @Test public void findElementByClassChain() {
        assertThat(secondTextField.getAttribute("name"), equalTo("IntegerB"));
    }

    @Test public void findElementByClassChainWithNegativeIndex() {
        assertThat(lastButton.getAttribute("name"), equalTo("Check calendar authorized"));
    }

    @Test public void findMultipleElementsByClassChain() {
        assertThat(allButtons.size(), is(greaterThan(1)));
    }

    @Test public void findElementByXUISelectorTest() {
        assertNotNull(gesture.getText());
    }

    @Test public void setValueTest() {
        textField1.setValue("2");
        textField2.setValue("4");
        driver.hideKeyboard();
        computeButton.click();
        assertEquals("6", answer.getText());
    }
}
