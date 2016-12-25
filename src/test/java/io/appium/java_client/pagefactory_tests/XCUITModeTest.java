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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.AppXCUITTest;
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

import java.util.function.Supplier;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class XCUITModeTest extends AppXCUITTest {

    private boolean populated = false;
    private WebDriverWait waiting = new WebDriverWait(driver, 10000);

    @HowToUseLocators(iOSAutomation = ALL_POSSIBLE)
    @iOSXCUITFindBy(iOSNsPredicate = "label contains 'Compute'")
    @iOSXCUITFindBy(className = "XCUIElementTypeButton")
    private MobileElement computeButton;

    @HowToUseLocators(iOSAutomation = CHAIN)
    @iOSXCUITFindBy(className = "XCUIElementTypeOther")
    @iOSXCUITFindBy(iOSNsPredicate = "name like 'Answer'")
    private WebElement answer;

    @iOSXCUITFindBy(iOSNsPredicate = "name = 'IntegerA'")
    private MobileElement textField1;

    @HowToUseLocators(iOSAutomation = ALL_POSSIBLE)
    @iOSXCUITFindBy(iOSNsPredicate = "name = 'IntegerB'")
    @iOSXCUITFindBy(accessibility = "IntegerB")
    private MobileElement textField2;

    @iOSXCUITFindBy(iOSNsPredicate = "name ENDSWITH 'Gesture'")
    private MobileElement gesture;

    @iOSXCUITFindBy(className = "XCUIElementTypeSlider")
    private MobileElement slider;

    /**
     * The setting up.
     */
    @Before public void setUp() throws Exception {
        if (!populated) {
            PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        }

        populated = true;
    }

    @Test public void dismissAlertTest() {
        Supplier<Boolean> dismissAlert = () -> {
            driver.findElement(MobileBy
                .iOSNsPredicateString("name CONTAINS 'Slow Down'")).click();
            waiting.until(alertIsPresent());
            driver.switchTo().alert().dismiss();
            return true;
        };
        assertTrue(dismissAlert.get());
    }

    @Test public void findByXCUITSelectorTest() {
        assertNotEquals(null, computeButton.getText());
    }

    @Test public void findElementByNameTest() {
        assertNull(textField1.getText());
    }

    @Test public void findElementByClassNameTest() {
        assertEquals("50%", slider.getAttribute("Value"));
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
