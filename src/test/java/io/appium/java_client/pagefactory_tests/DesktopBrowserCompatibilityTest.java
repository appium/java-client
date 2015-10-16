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

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.pagefactory.iOSFindBys;
import org.junit.Test;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DesktopBrowserCompatibilityTest {


    private final Platform current = Platform.getCurrent();
    @AndroidFindBy(className = "someClass")
    @iOSFindBys({@iOSFindBy(xpath = "//selector[1]"), @iOSFindBy(xpath = "//someTag")})
    @FindBys({@FindBy(id = "main"), @FindBy(tagName = "p")}) private List<WebElement> foundLinks;
    private List<WebElement> main; //this list is located by id="main"
    private WebDriver trap1;
    private List<AndroidDriver<?>> trap2;

    /**
     * The starting.
     */
    public void setUp() {
        if (current.is(Platform.WINDOWS)) {
            System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,
                "src/test/java/io/appium/java_client/pagefactory_tests/chromedriver.exe");
        } else {
            System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,
                "src/test/java/io/appium/java_client/pagefactory_tests/chromedriver");
        }
    }

    private void test() {
        WebDriver driver = new ChromeDriver();
        try {
            PageFactory
                .initElements(new AppiumFieldDecorator(driver, 15, TimeUnit.SECONDS),
                    this);
            driver.get(new File("src/test/java/io/appium/java_client/hello appium - saved page.htm")
                .toURI().toString());
            assertNotEquals(0, foundLinks.size());
            assertNotEquals(0, main.size());
            assertEquals(null, trap1);
            assertEquals(null, trap2);
        } finally {
            driver.quit();
        }
    }

    @Test public void chromeTest() {
        setUp();
        test();
    }
}
