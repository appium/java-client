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

import io.appium.java_client.TestUtils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory.Widget;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static io.appium.java_client.pagefactory.LocatorGroupStrategy.ALL_POSSIBLE;
import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver;
import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DesktopBrowserCompatibilityTest {
    private static final String HELLO_APPIUM_HTML =
            TestUtils.resourcePathToAbsolutePath("html/hello appium - saved page.htm").toUri().toString();

    @HowToUseLocators(iOSXCUITAutomation = ALL_POSSIBLE)
    @AndroidFindBy(className = "someClass")
    @iOSXCUITFindBy(xpath = "//selector[1]") @iOSXCUITFindBy(xpath = "//someTag")
    @FindBys({@FindBy(id = "main"), @FindBy(tagName = "p")})
    private List<WebElement> foundLinks;
    private List<WebElement> main; //this list is located by id="main"
    private WebDriver trap1;
    private List<AndroidDriver> trap2;

    /**
     * The starting.
     */
    @BeforeAll public static void beforeClass() {
        chromedriver().setup();
    }

    @Test public void chromeTest() {
        WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments("--headless=new"));
        try {
            PageFactory.initElements(new AppiumFieldDecorator(driver, ofSeconds(15)), this);
            driver.get(HELLO_APPIUM_HTML);
            assertNotEquals(0, foundLinks.size());
            assertNotEquals(0, main.size());
            assertNull(trap1);
            assertNull(trap2);
            foundLinks.forEach(element -> assertFalse(Widget.class.isAssignableFrom(element.getClass())));
        } finally {
            driver.quit();
        }
    }
}
