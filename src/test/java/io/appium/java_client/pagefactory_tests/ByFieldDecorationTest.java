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
import io.appium.java_client.pagefactory.WithTimeout;
import io.appium.java_client.pagefactory.bys.ContentMappedBy;
import io.appium.java_client.pagefactory.bys.builder.ByChained;
import io.appium.java_client.pagefactory.iOSXCUITBy;
import io.appium.java_client.pagefactory.iOSXCUITFindAll;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBys;
import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import io.appium.java_client.pagefactory_tests.widget.tests.StubWebElement;
import io.appium.java_client.remote.SupportsContextSwitching;
import io.appium.java_client.support.ByIdOrName;
import io.appium.java_client.support.CacheLookup;
import io.appium.java_client.support.FindAll;
import io.appium.java_client.support.FindBy;
import io.appium.java_client.support.FindBys;
import io.appium.java_client.support.pagefactory.ByAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.appium.java_client.pagefactory.LocatorGroupStrategy.ALL_POSSIBLE;
import static io.appium.java_client.pagefactory.bys.ContentType.HTML_OR_DEFAULT;
import static io.appium.java_client.pagefactory.bys.ContentType.NATIVE_MOBILE_SPECIFIC;
import static io.appium.java_client.support.PageFactory.initElements;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ByFieldDecorationTest {

    private interface ByRecorder {
        List<By> recordedBys();
    }

    private static class RecordingStubElement extends StubWebElement {
        private final List<By> recordedBys;

        RecordingStubElement(WebDriver driver, By by, List<By> recordedBys) {
            super(driver, by);
            this.recordedBys = recordedBys;
        }

        @Override
        public RecordingStubElement findElement(By by) {
            if (by instanceof ContentMappedBy) {
                return (RecordingStubElement) by.findElement(this);
            }
            recordedBys.add(by);
            return new RecordingStubElement(getWrappedDriver(), by, recordedBys);
        }

        @Override
        public List<WebElement> findElements(By by) {
            if (by instanceof ContentMappedBy) {
                return by.findElements(this);
            }
            recordedBys.add(by);
            return List.of(
                    new RecordingStubElement(getWrappedDriver(), by, recordedBys),
                    new RecordingStubElement(getWrappedDriver(), by, recordedBys)
            );
        }
    }

    private static class ContextSwitchingAndroidDriver
            extends AbstractStubWebDriver.StubAndroidDriver
            implements SupportsContextSwitching, ByRecorder {
        private final List<By> recordedBys = new ArrayList<>();
        private String currentContext = "NATIVE_APP";
        private int contextCheckCount;

        @Override
        public List<By> recordedBys() {
            return recordedBys;
        }

        @Override
        public WebDriver context(String name) {
            this.currentContext = name;
            return this;
        }

        @Override
        public boolean isBrowser() {
            contextCheckCount++;
            return !"NATIVE_APP".equals(currentContext);
        }

        @Override
        public String getContext() {
            contextCheckCount++;
            return currentContext;
        }

        int getContextCheckCount() {
            return contextCheckCount;
        }

        @Override
        public StubWebElement findElement(By by) {
            if (by instanceof ContentMappedBy) {
                return (StubWebElement) by.findElement(this);
            }
            recordedBys.add(by);
            return new RecordingStubElement(this, by, recordedBys);
        }

        @Override
        public List<WebElement> findElements(By by) {
            if (by instanceof ContentMappedBy) {
                return by.findElements(this);
            }
            recordedBys.add(by);
            return List.of(
                    new RecordingStubElement(this, by, recordedBys),
                    new RecordingStubElement(this, by, recordedBys)
            );
        }
    }

    private static class RecordingIOSDriver
            extends AbstractStubWebDriver.StubIOSXCUITDriver
            implements ByRecorder {
        private final List<By> recordedBys = new ArrayList<>();

        @Override
        public List<By> recordedBys() {
            return recordedBys;
        }

        @Override
        public StubWebElement findElement(By by) {
            if (by instanceof ContentMappedBy) {
                return (StubWebElement) by.findElement(this);
            }
            recordedBys.add(by);
            return new RecordingStubElement(this, by, recordedBys);
        }

        @Override
        public List<WebElement> findElements(By by) {
            if (by instanceof ContentMappedBy) {
                return by.findElements(this);
            }
            recordedBys.add(by);
            return List.of(
                    new RecordingStubElement(this, by, recordedBys),
                    new RecordingStubElement(this, by, recordedBys)
            );
        }
    }

    private static By lastRecordedBy(ByRecorder recorder, Runnable lookup) {
        recorder.recordedBys().clear();
        lookup.run();
        List<By> bys = recorder.recordedBys();
        return bys.get(bys.size() - 1);
    }

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
        private ByChained multiLocatorBy;

        @HowToUseLocators(androidAutomation = ALL_POSSIBLE, iOSXCUITAutomation = ALL_POSSIBLE)
        @AndroidFindBy(id = "any1")
        @AndroidFindBy(id = "any2")
        @iOSXCUITFindBy(id = "any1")
        @iOSXCUITFindBy(id = "any2")
        private ByAll allPossibleBy;

        @AndroidFindBys({@AndroidBy(id = "parent"), @AndroidBy(id = "child")})
        @iOSXCUITFindBys({@iOSXCUITBy(id = "parent"), @iOSXCUITBy(id = "child")})
        private ByChained findBysField;

        @AndroidFindAll({@AndroidBy(id = "opt1"), @AndroidBy(id = "opt2")})
        @iOSXCUITFindAll({@iOSXCUITBy(id = "opt1"), @iOSXCUITBy(id = "opt2")})
        private ByAll findAllField;

        @FindBy(id = "mapped_web")
        @AndroidFindBy(id = "mapped_android")
        private ContentMappedBy contentMappedBy;

        private ContentMappedBy unannotatedContentMappedBy;

        @FindBy(id = "ignored_mapped_web")
        @AndroidFindBy(id = "ignored_mapped_native")
        private ContentMappedBy preInitializedContentMappedBy = new ContentMappedBy(Map.of(
                HTML_OR_DEFAULT, By.id("preset_mapped_web"),
                NATIVE_MOBILE_SPECIFIC, AppiumBy.id("preset_mapped_native")
        ));

        @CacheLookup
        @WithTimeout(time = 5, chronoUnit = ChronoUnit.SECONDS)
        @FindBy(id = "cached_web")
        @AndroidFindBy(id = "cached_android")
        private By cachedAndTimedBy;

        @AndroidFindBy(id = "widget_root")
        @iOSXCUITFindBy(id = "widget_root")
        private ByHoldingWidget widget;

        @FindBy(id = "ignored_final")
        private final By finalAnnotatedBy = By.id("constant_id");

        @FindBy(id = "ignored_static")
        private static By staticAnnotatedBy = By.id("static_id");

        @FindBy(id = "ignored_pre_initialized")
        @AndroidFindBy(id = "ignored_pre_initialized_android")
        @iOSXCUITFindBy(accessibility = "ignored_pre_initialized_ios")
        private By preInitializedBy = By.id("preserved_id");

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

    public static class IncompatibleBySubTypePage {
        @AndroidFindBy(xpath = "//android.widget.TextView")
        private AppiumBy xpathAssignedToAppiumBy;

        @AndroidFindBy(id = "first")
        @AndroidFindBy(id = "second")
        private AppiumBy chainedAssignedToAppiumBy;

        @AndroidFindBy(id = "valid_android")
        private AppiumBy validAppiumBy;
    }

    public static class ConflictingSeleniumAnnotationsByPage {
        @FindBy(id = "one")
        @FindBys({@FindBy(id = "two"), @FindBy(id = "three")})
        private By conflictingBy;
    }

    public static class ConflictingSeleniumOnAppiumByPage {
        @FindBy(id = "one")
        @FindAll({@FindBy(id = "two"), @FindBy(id = "three")})
        private AppiumBy conflictingAppiumBy;
    }

    @Test
    public void decoratesByAndAppiumByFieldsAndSwitchesContextLazilyOnAndroid() {
        ContextSwitchingAndroidDriver driver = new ContextSwitchingAndroidDriver();
        CrossPlatformByPage page = new CrossPlatformByPage();
        initElements(new AppiumFieldDecorator(driver), page);

        // No remote context round-trips happen at initElements time
        assertEquals(0, driver.getContextCheckCount());

        assertInstanceOf(ContentMappedBy.class, page.somethingBy);
        assertInstanceOf(ContentMappedBy.class, page.hybridBy);
        assertInstanceOf(ContentMappedBy.class, page.webFallbackBy);
        assertInstanceOf(ContentMappedBy.class, page.contentMappedBy);
        assertInstanceOf(ContentMappedBy.class, page.cachedAndTimedBy);

        assertEquals(AppiumBy.id("appium_android"), page.somethingAppiumBy);
        assertInstanceOf(ByChained.class, page.multiLocatorBy);
        assertInstanceOf(ByAll.class, page.allPossibleBy);
        assertInstanceOf(ByChained.class, page.findBysField);
        assertInstanceOf(ByAll.class, page.findAllField);
        assertEquals(AppiumBy.accessibilityId("inner_android"), page.widget.getInnerAppiumBy());

        // Pre-initialized, final, static, and unannotated By / ContentMappedBy fields are left untouched
        assertEquals(By.id("constant_id"), page.finalAnnotatedBy);
        assertEquals(By.id("static_id"), CrossPlatformByPage.staticAnnotatedBy);
        assertEquals(By.id("preserved_id"), page.preInitializedBy);
        assertNull(page.unannotatedNullBy);
        assertNull(page.unannotatedContentMappedBy);
        assertEquals(
                AppiumBy.id("preset_mapped_native"),
                lastRecordedBy(driver, () -> driver.findElement(page.preInitializedContentMappedBy))
        );

        final int initialHashCode = page.hybridBy.hashCode();

        // In NATIVE_APP context, hybridBy and somethingBy resolve to native locators
        assertEquals(
                new ByChained(new By[]{AppiumBy.id("native_android")}).toString(),
                lastRecordedBy(driver, () -> driver.findElement(page.hybridBy)).toString()
        );
        assertEquals(
                new ByChained(new By[]{AppiumBy.id("something_android")}).toString(),
                lastRecordedBy(driver, () -> driver.findElement(page.somethingBy)).toString()
        );

        // Switching to WEBVIEW_1 updates the same By and ContentMappedBy fields lazily
        driver.context("WEBVIEW_1");
        assertEquals(
                By.id("web_only"),
                lastRecordedBy(driver, () -> driver.findElement(page.hybridBy))
        );
        assertEquals(
                By.id("web_only"),
                lastRecordedBy(driver, () -> driver.findElements(page.hybridBy))
        );
        assertEquals(
                By.id("mapped_web"),
                lastRecordedBy(driver, () -> driver.findElement(page.contentMappedBy))
        );
        // Field with only @AndroidFindBy falls back to ByIdOrName in webview context
        assertEquals(
                new ByIdOrName("somethingBy").toString(),
                lastRecordedBy(driver, () -> driver.findElement(page.somethingBy)).toString()
        );

        // Switching back to NATIVE_APP restores native locators, and hashCode remains stable
        driver.context("NATIVE_APP");
        assertEquals(
                new ByChained(new By[]{AppiumBy.id("native_android")}).toString(),
                lastRecordedBy(driver, () -> driver.findElements(page.hybridBy)).toString()
        );
        assertEquals(initialHashCode, page.hybridBy.hashCode());
    }

    @Test
    public void explicitUseContentIsHonoredWithoutExtraContextChecksOrHashCodeMutation() {
        Map<io.appium.java_client.pagefactory.bys.ContentType, By> locatorMap = Map.of(
                HTML_OR_DEFAULT, By.id("web_target"),
                NATIVE_MOBILE_SPECIFIC, AppiumBy.id("native_target")
        );
        ContentMappedBy mappedBy = new ContentMappedBy(locatorMap);
        ContentMappedBy webPinnedBy = new ContentMappedBy(locatorMap);
        webPinnedBy.useContent(HTML_OR_DEFAULT);

        assertTrue(mappedBy.toString().contains("HTML_OR_DEFAULT="));
        assertTrue(mappedBy.toString().contains("NATIVE_MOBILE_SPECIFIC="));

        // Explicitly select native content even when driver is in WEBVIEW_1
        ContextSwitchingAndroidDriver driver = new ContextSwitchingAndroidDriver();
        driver.context("WEBVIEW_1");
        mappedBy.useContent(NATIVE_MOBILE_SPECIFIC);
        assertEquals(AppiumBy.id("native_target").toString(), mappedBy.toString());
        assertNotEquals(webPinnedBy, mappedBy);

        final int pinnedHash = mappedBy.hashCode();
        final int checksBefore = driver.getContextCheckCount();
        assertEquals(
                AppiumBy.id("native_target"),
                lastRecordedBy(driver, () -> driver.findElement(mappedBy))
        );
        assertEquals(checksBefore, driver.getContextCheckCount());
        assertEquals(pinnedHash, mappedBy.hashCode());
    }

    @Test
    public void decoratesByAndAppiumByFieldsOnIOS() {
        RecordingIOSDriver driver = new RecordingIOSDriver();
        CrossPlatformByPage page = new CrossPlatformByPage();
        initElements(new AppiumFieldDecorator(driver), page);

        assertEquals(AppiumBy.accessibilityId("appium_ios"), page.somethingAppiumBy);
        assertEquals(
                new ByChained(new By[]{AppiumBy.accessibilityId("something_ios")}).toString(),
                lastRecordedBy(driver, () -> driver.findElement(page.somethingBy)).toString()
        );
        assertEquals(
                new ByChained(new By[]{AppiumBy.accessibilityId("native_ios")}).toString(),
                lastRecordedBy(driver, () -> driver.findElement(page.hybridBy)).toString()
        );
        assertEquals(
                By.cssSelector(".web-button"),
                lastRecordedBy(driver, () -> driver.findElement(page.webFallbackBy))
        );
        assertInstanceOf(ByChained.class, page.multiLocatorBy);
        assertInstanceOf(ByAll.class, page.allPossibleBy);
        assertInstanceOf(ByChained.class, page.findBysField);
        assertInstanceOf(ByAll.class, page.findAllField);
        assertEquals(AppiumBy.iOSNsPredicateString("name == 'inner_ios'"), page.widget.getInnerAppiumBy());
        assertEquals(By.id("constant_id"), page.finalAnnotatedBy);
        assertEquals(By.id("preserved_id"), page.preInitializedBy);
        assertNull(page.unannotatedNullBy);
        assertNull(page.unannotatedContentMappedBy);
    }

    @Test
    public void respectsProgrammaticDefaultLocatorGroupStrategyForByFields() {
        ContextSwitchingAndroidDriver driver = new ContextSwitchingAndroidDriver();
        CrossPlatformByPage page = new CrossPlatformByPage();
        initElements(new AppiumFieldDecorator(driver, ALL_POSSIBLE), page);

        assertEquals(
                new ByAll(new By[]{AppiumBy.id("native_android")}).toString(),
                lastRecordedBy(driver, () -> driver.findElement(page.hybridBy)).toString()
        );
        assertEquals(
                new ByAll(new By[]{AppiumBy.id("something_android")}).toString(),
                lastRecordedBy(driver, () -> driver.findElement(page.somethingBy)).toString()
        );
    }

    @Test
    public void leavesIncompatibleNarrowSubTypeFieldsNullWithoutAbortingPageInit() {
        IncompatibleBySubTypePage page = new IncompatibleBySubTypePage();
        initElements(new AppiumFieldDecorator(new AbstractStubWebDriver.StubAndroidDriver()), page);

        assertNull(page.xpathAssignedToAppiumBy);
        assertNull(page.chainedAssignedToAppiumBy);
        assertEquals(AppiumBy.id("valid_android"), page.validAppiumBy);
    }

    @Test
    public void throwsOnConflictingAnnotationsOnByFields() {
        assertThrows(
                IllegalArgumentException.class,
                () -> initElements(
                        new AppiumFieldDecorator(new AbstractStubWebDriver.StubAndroidDriver()),
                        new ConflictingSeleniumAnnotationsByPage()
                )
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> initElements(
                        new AppiumFieldDecorator(new AbstractStubWebDriver.StubAndroidDriver()),
                        new ConflictingSeleniumOnAppiumByPage()
                )
        );
    }
}
