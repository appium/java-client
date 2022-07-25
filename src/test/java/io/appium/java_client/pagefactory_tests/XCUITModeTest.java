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

import io.appium.java_client.ios.AppIOSTest;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static io.appium.java_client.pagefactory.LocatorGroupStrategy.ALL_POSSIBLE;
import static io.appium.java_client.pagefactory.LocatorGroupStrategy.CHAIN;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class XCUITModeTest extends AppIOSTest {

    private boolean populated = false;

    @HowToUseLocators(iOSXCUITAutomation = ALL_POSSIBLE)
    @iOSXCUITFindBy(iOSNsPredicate = "label contains 'Compute'")
    @iOSXCUITFindBy(className = "XCUIElementTypeButton")
    private WebElement computeButton;

    @HowToUseLocators(iOSXCUITAutomation = CHAIN)
    @iOSXCUITFindBy(iOSNsPredicate = "name like 'Answer'")
    private WebElement answer;

    @iOSXCUITFindBy(iOSNsPredicate = "name = 'IntegerA'")
    private WebElement textField1;

    @HowToUseLocators(iOSXCUITAutomation = ALL_POSSIBLE)
    @iOSXCUITFindBy(iOSNsPredicate = "name = 'IntegerB'")
    @iOSXCUITFindBy(accessibility = "IntegerB")
    private WebElement textField2;

    @iOSXCUITFindBy(iOSNsPredicate = "name ENDSWITH 'Gesture'")
    private WebElement gesture;

    @iOSXCUITFindBy(className = "XCUIElementTypeSlider")
    private WebElement slider;

    @iOSXCUITFindBy(id = "locationStatus")
    private WebElement locationStatus;

    @HowToUseLocators(iOSXCUITAutomation = CHAIN)
    @iOSXCUITFindBy(iOSNsPredicate = "name BEGINSWITH 'contact'")
    private WebElement contactAlert;

    @HowToUseLocators(iOSXCUITAutomation = ALL_POSSIBLE)
    @iOSXCUITFindBy(iOSNsPredicate = "name BEGINSWITH 'location'")
    private WebElement locationAlert;

    @iOSXCUITFindBy(iOSClassChain = "XCUIElementTypeWindow/*/XCUIElementTypeTextField[2]")
    private WebElement secondTextField;

    @iOSXCUITFindBy(iOSClassChain = "XCUIElementTypeWindow/*/XCUIElementTypeButton[-1]")
    private WebElement lastButton;

    @iOSXCUITFindBy(iOSClassChain = "XCUIElementTypeWindow/*/XCUIElementTypeButton")
    private List<WebElement> allButtons;

    /**
     * The setting up.
     */
    @BeforeEach
    public void setUp() {
        if (!populated) {
            PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        }

        populated = true;
    }

    @Test
    public void findByXCUITSelectorTest() {
        assertNotEquals(null, computeButton.getText());
    }

    @Test
    public void findElementByNameTest() {
        assertEquals("TextField1", textField1.getText());
    }

    @Test
    public void findElementByClassNameTest() {
        assertEquals("50%", slider.getAttribute("value"));
    }

    @Test
    public void pageObjectChainingTest() {
        assertTrue(contactAlert.isDisplayed());
    }

    @Test
    public void findElementByIdTest() {
        assertTrue(locationStatus.isDisplayed());
    }

    @Test
    public void nativeSelectorTest() {
        assertTrue(locationAlert.isDisplayed());
    }

    @Test
    public void findElementByClassChain() {
        assertThat(secondTextField.getAttribute("name"), equalTo("IntegerB"));
    }

    @Test
    public void findElementByClassChainWithNegativeIndex() {
        assertThat(lastButton.getAttribute("name"), equalTo("Check calendar authorized"));
    }

    @Test
    public void findMultipleElementsByClassChain() {
        assertThat(allButtons.size(), is(greaterThan(1)));
    }

    @Test
    public void findElementByXUISelectorTest() {
        assertNotNull(gesture.getText());
    }

    @Test
    public void setValueTest() {
        textField1.sendKeys("2");
        textField2.sendKeys("4");
        driver.hideKeyboard();
        computeButton.click();
        assertEquals("6", answer.getText());
    }
}
