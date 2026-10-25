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

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.Widget;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.time.Duration;

import static io.appium.java_client.pagefactory.LocatorGroupStrategy.ALL_POSSIBLE;
import static io.appium.java_client.pagefactory.LocatorGroupStrategy.CHAIN;
import static io.appium.java_client.support.PageFactory.initElements;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultLocatorGroupStrategyTest {

    private static String unwrapByString(WebElement proxy) {
        return proxy.toString();
    }

    public static class UnannotatedPage {
        @AndroidFindBy(id = "submit")
        @AndroidFindBy(id = "done")
        @iOSXCUITFindBy(id = "submit")
        @iOSXCUITFindBy(id = "done")
        private WebElement submitButton;

        @HowToUseLocators(androidAutomation = CHAIN, iOSXCUITAutomation = CHAIN)
        @AndroidFindBy(id = "parent")
        @AndroidFindBy(id = "child")
        @iOSXCUITFindBy(id = "parent")
        @iOSXCUITFindBy(id = "child")
        private WebElement explicitChainElement;

        @AndroidFindBy(id = "root")
        @iOSXCUITFindBy(id = "root")
        private SubWidget widget;
    }

    public static class SubWidget extends Widget {
        @AndroidFindBy(id = "inner1")
        @AndroidFindBy(id = "inner2")
        @iOSXCUITFindBy(id = "inner1")
        @iOSXCUITFindBy(id = "inner2")
        private WebElement innerButton;

        protected SubWidget(WebElement element) {
            super(element);
        }

        public WebElement getInnerButton() {
            return innerButton;
        }
    }

    @HowToUseLocators(androidAutomation = ALL_POSSIBLE, iOSXCUITAutomation = ALL_POSSIBLE)
    public static class ClassAnnotatedPage {
        @AndroidFindBy(id = "submit")
        @AndroidFindBy(id = "done")
        @iOSXCUITFindBy(id = "submit")
        @iOSXCUITFindBy(id = "done")
        private WebElement submitButton;

        @HowToUseLocators(androidAutomation = CHAIN, iOSXCUITAutomation = CHAIN)
        @AndroidFindBy(id = "parent")
        @AndroidFindBy(id = "child")
        @iOSXCUITFindBy(id = "parent")
        @iOSXCUITFindBy(id = "child")
        private WebElement explicitChainElement;
    }

    public static class InheritedClassAnnotatedPage extends ClassAnnotatedPage {
        @AndroidFindBy(id = "cancel")
        @AndroidFindBy(id = "close")
        @iOSXCUITFindBy(id = "cancel")
        @iOSXCUITFindBy(id = "close")
        private WebElement cancelButton;
    }

    @Test
    public void defaultsToChainWhenNotConfigured() {
        UnannotatedPage androidPage = new UnannotatedPage();
        initElements(new AppiumFieldDecorator(new AbstractStubWebDriver.StubAndroidDriver()), androidPage);
        assertEquals(
                "Located by By.chained({AppiumBy.id: submit,AppiumBy.id: done})",
                unwrapByString(androidPage.submitButton)
        );

        UnannotatedPage iosPage = new UnannotatedPage();
        initElements(new AppiumFieldDecorator(new AbstractStubWebDriver.StubIOSXCUITDriver()), iosPage);
        assertEquals(
                "Located by By.chained({AppiumBy.id: submit,AppiumBy.id: done})",
                unwrapByString(iosPage.submitButton)
        );
    }

    @Test
    public void programmaticDefaultLocatorStrategyAppliesToUnannotatedFieldsAndWidgets() {
        UnannotatedPage androidPage = new UnannotatedPage();
        initElements(
                new AppiumFieldDecorator(new AbstractStubWebDriver.StubAndroidDriver(), ALL_POSSIBLE),
                androidPage
        );
        assertEquals(
                "Located by By.all({AppiumBy.id: submit,AppiumBy.id: done})",
                unwrapByString(androidPage.submitButton)
        );
        assertEquals(
                "Located by By.chained({AppiumBy.id: parent,AppiumBy.id: child})",
                unwrapByString(androidPage.explicitChainElement)
        );
        assertEquals(
                "Located by By.all({AppiumBy.id: inner1,AppiumBy.id: inner2})",
                unwrapByString(androidPage.widget.getInnerButton())
        );

        UnannotatedPage iosPage = new UnannotatedPage();
        initElements(
                new AppiumFieldDecorator(
                        new AbstractStubWebDriver.StubIOSXCUITDriver(),
                        Duration.ZERO,
                        ALL_POSSIBLE
                ),
                iosPage
        );
        assertEquals(
                "Located by By.all({AppiumBy.id: submit,AppiumBy.id: done})",
                unwrapByString(iosPage.submitButton)
        );
        assertEquals(
                "Located by By.chained({AppiumBy.id: parent,AppiumBy.id: child})",
                unwrapByString(iosPage.explicitChainElement)
        );
        assertEquals(
                "Located by By.all({AppiumBy.id: inner1,AppiumBy.id: inner2})",
                unwrapByString(iosPage.widget.getInnerButton())
        );
    }

    @Test
    public void classLevelHowToUseLocatorsAppliesToFieldsAndSubclasses() {
        InheritedClassAnnotatedPage androidPage = new InheritedClassAnnotatedPage();
        initElements(new AppiumFieldDecorator(new AbstractStubWebDriver.StubAndroidDriver()), androidPage);
        assertEquals(
                "Located by By.all({AppiumBy.id: submit,AppiumBy.id: done})",
                unwrapByString(((ClassAnnotatedPage) androidPage).submitButton)
        );
        assertEquals(
                "Located by By.chained({AppiumBy.id: parent,AppiumBy.id: child})",
                unwrapByString(((ClassAnnotatedPage) androidPage).explicitChainElement)
        );
        assertEquals(
                "Located by By.all({AppiumBy.id: cancel,AppiumBy.id: close})",
                unwrapByString(androidPage.cancelButton)
        );
    }
}
