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

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.Widget;
import io.appium.java_client.pagefactory.bys.ContentMappedBy;
import io.appium.java_client.pagefactory.bys.builder.ByChained;
import io.appium.java_client.pagefactory.iOSXCUITBy;
import io.appium.java_client.pagefactory.iOSXCUITFindAll;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBys;
import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import io.appium.java_client.support.FindBy;
import io.appium.java_client.support.pagefactory.ByAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static io.appium.java_client.pagefactory.LocatorGroupStrategy.ALL_POSSIBLE;
import static io.appium.java_client.support.PageFactory.initElements;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ByFieldDecorationTest {

    public static class CrossPlatformByPage {
        @AndroidFindBy(id = "something_android")
        @iOSXCUITFindBy(accessibility = "something_ios")
        private By somethingBy;

        @AndroidFindBy(id = "appium_android")
        @iOSXCUITFindBy(accessibility = "appium_ios")
        private AppiumBy somethingAppiumBy;

        @FindBy(id = "web_only")
        @AndroidFindBy(id = "native_android")
        @iOSXCUITFindBy(accessibility = "native_ios")
        private By hybridBy;

        @FindBy(css = ".web-button")
        private By webFallbackBy;

        @AndroidFindBy(id = "chain1")
        @AndroidFindBy(id = "chain2")
        @iOSXCUITFindBy(id = "chain1")
        @iOSXCUITFindBy(id = "chain2")
        private By multiLocatorBy;

        @HowToUseLocators(androidAutomation = ALL_POSSIBLE, iOSXCUITAutomation = ALL_POSSIBLE)
        @AndroidFindBy(id = "any1")
        @AndroidFindBy(id = "any2")
        @iOSXCUITFindBy(id = "any1")
        @iOSXCUITFindBy(id = "any2")
        private By allPossibleBy;

        @AndroidFindBys({@AndroidBy(id = "parent"), @AndroidBy(id = "child")})
        @iOSXCUITFindBys({@iOSXCUITBy(id = "parent"), @iOSXCUITBy(id = "child")})
        private By findBysField;

        @AndroidFindAll({@AndroidBy(id = "opt1"), @AndroidBy(id = "opt2")})
        @iOSXCUITFindAll({@iOSXCUITBy(id = "opt1"), @iOSXCUITBy(id = "opt2")})
        private By findAllField;

        @FindBy(id = "mapped_web")
        @AndroidFindBy(id = "mapped_android")
        private ContentMappedBy contentMappedBy;

        @AndroidFindBy(id = "widget_root")
        @iOSXCUITFindBy(id = "widget_root")
        private ByHoldingWidget widget;

        private final By preInitializedBy = By.id("constant_id");

        private By unannotatedNullBy;
    }

    public static class ByHoldingWidget extends Widget {
        @AndroidFindBy(accessibility = "inner_android")
        @iOSXCUITFindBy(iOSNsPredicate = "name == 'inner_ios'")
        private AppiumBy innerAppiumBy;

        protected ByHoldingWidget(WebElement element) {
            super(element);
        }

        public AppiumBy getInnerAppiumBy() {
            return innerAppiumBy;
        }
    }

    public static class IncompatibleByTypePage {
        @AndroidFindBy(xpath = "//android.widget.TextView")
        private AppiumBy xpathAssignedToAppiumBy;
    }

    @Test
    public void decoratesByAndAppiumByFieldsOnAndroid() {
        CrossPlatformByPage page = new CrossPlatformByPage();
        initElements(new AppiumFieldDecorator(new AbstractStubWebDriver.StubAndroidDriver()), page);

        assertEquals(AppiumBy.id("something_android"), page.somethingBy);
        assertEquals(AppiumBy.id("appium_android"), page.somethingAppiumBy);
        assertEquals(AppiumBy.id("native_android"), page.hybridBy);
        assertEquals(By.cssSelector(".web-button"), page.webFallbackBy);
        assertInstanceOf(ByChained.class, page.multiLocatorBy);
        assertInstanceOf(ByAll.class, page.allPossibleBy);
        assertInstanceOf(ByChained.class, page.findBysField);
        assertInstanceOf(ByAll.class, page.findAllField);
        assertNotNull(page.contentMappedBy);
        assertEquals(AppiumBy.accessibilityId("inner_android"), page.widget.getInnerAppiumBy());
        assertEquals(By.id("constant_id"), page.preInitializedBy);
        assertNull(page.unannotatedNullBy);
    }

    @Test
    public void decoratesByAndAppiumByFieldsOnIOS() {
        CrossPlatformByPage page = new CrossPlatformByPage();
        initElements(new AppiumFieldDecorator(new AbstractStubWebDriver.StubIOSXCUITDriver()), page);

        assertEquals(AppiumBy.accessibilityId("something_ios"), page.somethingBy);
        assertEquals(AppiumBy.accessibilityId("appium_ios"), page.somethingAppiumBy);
        assertEquals(AppiumBy.accessibilityId("native_ios"), page.hybridBy);
        assertEquals(By.cssSelector(".web-button"), page.webFallbackBy);
        assertInstanceOf(ByChained.class, page.multiLocatorBy);
        assertInstanceOf(ByAll.class, page.allPossibleBy);
        assertInstanceOf(ByChained.class, page.findBysField);
        assertInstanceOf(ByAll.class, page.findAllField);
        assertEquals(AppiumBy.iOSNsPredicateString("name == 'inner_ios'"), page.widget.getInnerAppiumBy());
        assertEquals(By.id("constant_id"), page.preInitializedBy);
        assertNull(page.unannotatedNullBy);
    }

    @Test
    public void selectsDefaultByInBrowserOrWebViewContext() {
        CrossPlatformByPage browserPage = new CrossPlatformByPage();
        initElements(new AppiumFieldDecorator(new AbstractStubWebDriver.StubBrowserDriver()), browserPage);

        assertNull(browserPage.somethingBy);
        assertNull(browserPage.somethingAppiumBy);
        assertEquals(By.id("web_only"), browserPage.hybridBy);
        assertEquals(By.cssSelector(".web-button"), browserPage.webFallbackBy);
        assertEquals(By.id("constant_id"), browserPage.preInitializedBy);

        CrossPlatformByPage webViewPage = new CrossPlatformByPage();
        initElements(
                new AppiumFieldDecorator(new AbstractStubWebDriver.StubAndroidBrowserOrWebViewDriver()),
                webViewPage
        );
        assertEquals(By.id("web_only"), webViewPage.hybridBy);
        assertEquals(AppiumBy.id("something_android"), webViewPage.somethingBy);
    }

    @Test
    public void respectsProgrammaticDefaultLocatorGroupStrategyForByFields() {
        CrossPlatformByPage page = new CrossPlatformByPage();
        initElements(
                new AppiumFieldDecorator(new AbstractStubWebDriver.StubAndroidDriver(), ALL_POSSIBLE),
                page
        );
        assertInstanceOf(ByAll.class, page.multiLocatorBy);
    }

    @Test
    public void throwsWhenBuiltByDoesNotMatchDeclaredSubType() {
        IncompatibleByTypePage page = new IncompatibleByTypePage();
        assertThrows(
                IllegalArgumentException.class,
                () -> initElements(new AppiumFieldDecorator(new AbstractStubWebDriver.StubAndroidDriver()), page)
        );
    }
}

