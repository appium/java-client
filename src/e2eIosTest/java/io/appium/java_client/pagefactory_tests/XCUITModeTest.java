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
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class XCUITModeTest extends AppIOSTest {

    private boolean populated = false;

    @HowToUseLocators(iOSXCUITAutomation = ALL_POSSIBLE)
    @iOSXCUITFindBy(iOSNsPredicate = "name == \"assets/assets/vodqa.png\"")
    @iOSXCUITFindBy(className = "XCUIElementTypeImage")
    private WebElement logoImageAllPossible;

    @HowToUseLocators(iOSXCUITAutomation = CHAIN)
    @iOSXCUITFindBy(iOSNsPredicate = "name CONTAINS 'vodqa'")
    private WebElement logoImageChain;

    @iOSXCUITFindBy(iOSNsPredicate = "name == 'username'")
    private WebElement usernameFieldPredicate;

    @iOSXCUITFindBy(iOSNsPredicate = "name ENDSWITH '.png'")
    private WebElement logoImagePredicate;

    @iOSXCUITFindBy(className = "XCUIElementTypeImage")
    private WebElement logoImageClass;

    @iOSXCUITFindBy(accessibility = "login")
    private WebElement loginLinkAccId;

    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeTextField[`name == \"username\"`]")
    private WebElement usernameFieldClassChain;

    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeSecureTextField[`name == \"password\"`][-1]")
    private WebElement passwordFieldClassChain;

    @iOSXCUITFindBy(iOSClassChain = "**/*[`type CONTAINS \"TextField\"`]")
    private List<WebElement> allTextFields;

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
        assertTrue(logoImageAllPossible.isDisplayed());
    }

    @Test
    public void findElementByNameTest() {
        assertTrue(usernameFieldPredicate.isDisplayed());
    }

    @Test
    public void findElementByClassNameTest() {
        assertTrue(logoImageClass.isDisplayed());
    }

    @Test
    public void pageObjectChainingTest() {
        assertTrue(logoImageChain.isDisplayed());
    }

    @Test
    public void findElementByIdTest() {
        assertTrue(loginLinkAccId.isDisplayed());
    }

    @Test
    public void nativeSelectorTest() {
        assertTrue(logoImagePredicate.isDisplayed());
    }

    @Test
    public void findElementByClassChain() {
        assertTrue(usernameFieldClassChain.isDisplayed());
    }

    @Test
    public void findElementByClassChainWithNegativeIndex() {
        assertTrue(passwordFieldClassChain.isDisplayed());
    }

    @Test
    public void findMultipleElementsByClassChain() {
        assertThat(allTextFields.size(), is(greaterThan(1)));
    }
}
