package io.appium.java_client.pagefactory_tests;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import io.appium.java_client.pagefactory.bys.ContentMappedBy;
import io.appium.java_client.pagefactory.bys.builder.ByChained;
import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import io.appium.java_client.pagefactory_tests.widget.tests.StubWebElement;
import io.appium.java_client.remote.AppiumWebElement;
import io.appium.java_client.remote.SupportsContextSwitching;
import io.appium.java_client.support.AbstractFindByBuilder;
import io.appium.java_client.support.FindBy;
import io.appium.java_client.support.PageFactoryFinder;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static io.appium.java_client.support.PageFactory.initElements;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomFindByAnnotationTest {

    private static String buildScrollIntoViewExpression(String resourceId) {
        return "new UiScrollable(new UiSelector().scrollable(true))"
                + ".scrollIntoView(new UiSelector().resourceId(\"" + resourceId + "\"));";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.TYPE})
    @Repeatable(ScrollingAndroidFindBys.class)
    @PageFactoryFinder(ScrollingAndroidFindBy.Builder.class)
    @interface ScrollingAndroidFindBy {
        String scrollingResourceId();

        class Builder extends AbstractFindByBuilder<ScrollingAndroidFindBy> {
            static Field lastSeenField = AbstractStubWebDriver.class.getDeclaredFields()[0];

            @Override
            public By buildIt(ScrollingAndroidFindBy annotation, @Nullable Field field) {
                lastSeenField = field;
                return AppiumBy.androidUIAutomator(
                        buildScrollIntoViewExpression(annotation.scrollingResourceId())
                );
            }
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.TYPE})
    @PageFactoryFinder(ScrollingAndroidFindBys.Builder.class)
    @interface ScrollingAndroidFindBys {
        ScrollingAndroidFindBy[] value();

        class Builder extends AbstractFindByBuilder<ScrollingAndroidFindBys> {
            @Override
            public By buildIt(ScrollingAndroidFindBys annotation, @Nullable Field field) {
                ScrollingAndroidFindBy[] items = annotation.value();
                By[] bys = new By[items.length];
                ScrollingAndroidFindBy.Builder singleBuilder = new ScrollingAndroidFindBy.Builder();
                for (int i = 0; i < items.length; i++) {
                    bys[i] = singleBuilder.buildIt(items[i], field);
                }
                return new ByChained(bys);
            }
        }
    }

    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.TYPE})
    @PageFactoryFinder(DataTestIdBuilder.class)
    @interface DataTestId {
        String value() default "";
    }

    private static final class DataTestIdBuilder extends AbstractFindByBuilder<DataTestId> {
        @Override
        public By buildIt(DataTestId annotation, @Nullable Field field) {
            String testId = annotation.value().isEmpty() && field != null
                    ? field.getName()
                    : annotation.value();
            return By.cssSelector("[data-testid='" + testId + "']");
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.TYPE})
    @org.openqa.selenium.support.PageFactoryFinder(LegacyAccessibilityBuilder.class)
    @interface LegacyAccessibility {
        String value();
    }

    static final class LegacyAccessibilityBuilder
            extends org.openqa.selenium.support.AbstractFindByBuilder<LegacyAccessibility> {
        @Override
        public By buildIt(LegacyAccessibility annotation, Field field) {
            return AppiumBy.accessibilityId(annotation.value());
        }
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

    private static class RecordingAndroidDriver
            extends AbstractStubWebDriver.StubAndroidDriver
            implements SupportsContextSwitching {
        private final List<By> recordedBys = new ArrayList<>();
        private String currentContext = "NATIVE_APP";

        @Override
        public WebDriver context(String name) {
            this.currentContext = name;
            return this;
        }

        @Override
        public boolean isBrowser() {
            return !"NATIVE_APP".equals(currentContext);
        }

        @Override
        public String getContext() {
            return currentContext;
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

        By captureFirstBy(Runnable action) {
            recordedBys.clear();
            action.run();
            return recordedBys.get(0);
        }
    }

    public static class ChildButtonWidget extends Widget {
        @ScrollingAndroidFindBy(scrollingResourceId = "com.test:id/innerAction")
        private WebElement innerAction;

        protected ChildButtonWidget(WebElement element) {
            super(element);
        }

        public void tapInner() {
            innerAction.click();
        }
    }

    @ScrollingAndroidFindBy(scrollingResourceId = "com.test:id/classAnnotatedCard")
    public static class ClassAnnotatedCardWidget extends Widget {
        protected ClassAnnotatedCardWidget(WebElement element) {
            super(element);
        }

        public void tapRoot() {
            getWrappedElement().click();
        }
    }

    @DataTestId("inherited-card")
    public static class InheritedBaseWidget extends Widget {
        protected InheritedBaseWidget(WebElement element) {
            super(element);
        }

        public void tapRoot() {
            getWrappedElement().click();
        }
    }

    public static class InheritedSubWidget extends InheritedBaseWidget {
        protected InheritedSubWidget(WebElement element) {
            super(element);
        }
    }

    @DataTestId("overridden-sub-card")
    public static class OverridingSubWidget extends InheritedBaseWidget {
        protected OverridingSubWidget(WebElement element) {
            super(element);
        }
    }

    static class CustomFinderPage {
        @ScrollingAndroidFindBy(scrollingResourceId = "com.test:id/buttonNotInView")
        WebElement scrollingButton;

        @ScrollingAndroidFindBy(scrollingResourceId = "com.test:id/appiumButton")
        AppiumWebElement scrollingAppiumButton;

        @ScrollingAndroidFindBy(scrollingResourceId = "com.test:id/listItem")
        List<WebElement> scrollingItems;

        @ScrollingAndroidFindBy(scrollingResourceId = "com.test:id/container")
        @ScrollingAndroidFindBy(scrollingResourceId = "com.test:id/nestedRow")
        WebElement repeatedScrollingRow;

        @ScrollingAndroidFindBy(scrollingResourceId = "com.test:id/byTarget")
        By scrollingBy;

        @ScrollingAndroidFindBy(scrollingResourceId = "com.test:id/appiumByTarget")
        AppiumBy scrollingAppiumBy;

        @DataTestId
        WebElement inferredFieldIdButton;

        @DataTestId("web-checkout")
        @AndroidFindBy(id = "com.test:id/native_checkout")
        WebElement hybridCheckoutButton;

        @DataTestId("web-checkout-by")
        @AndroidFindBy(id = "com.test:id/native_checkout_by")
        By hybridCheckoutBy;

        @ScrollingAndroidFindBy(scrollingResourceId = "com.test:id/widgetContainer")
        ChildButtonWidget fieldAnnotatedWidget;

        @ScrollingAndroidFindBy(scrollingResourceId = "com.test:id/widgetRow")
        List<ChildButtonWidget> fieldAnnotatedWidgets;

        ClassAnnotatedCardWidget classAnnotatedWidget;

        InheritedSubWidget inheritedWidget;

        OverridingSubWidget overridingWidget;

        @LegacyAccessibility("legacy_submit")
        WebElement legacyElement;

        @LegacyAccessibility("legacy_list")
        List<WebElement> legacyList;

        @LegacyAccessibility("legacy_widget")
        ChildButtonWidget legacyWidget;

        @LegacyAccessibility("legacy_by")
        AppiumBy legacyAppiumBy;
    }

    static class ConflictingCustomAndFindByPage {
        @ScrollingAndroidFindBy(scrollingResourceId = "com.test:id/one")
        @FindBy(id = "two")
        WebElement invalidField;
    }

    static class ConflictingTwoCustomFindersPage {
        @ScrollingAndroidFindBy(scrollingResourceId = "com.test:id/one")
        @DataTestId("two")
        By invalidByField;
    }

    static class ConflictingCustomAndSeleniumFindByPage {
        @ScrollingAndroidFindBy(scrollingResourceId = "com.test:id/one")
        @org.openqa.selenium.support.FindBy(id = "two")
        WebElement invalidField;
    }

    @ScrollingAndroidFindBy(scrollingResourceId = "com.test:id/one")
    @FindBy(id = "two")
    public static class ConflictingClassWidget extends Widget {
        protected ConflictingClassWidget(WebElement element) {
            super(element);
        }
    }

    static class ConflictingWidgetClassPage {
        ConflictingClassWidget invalidWidget;
    }

    @Test
    void supportsCustomPageFactoryFinderAcrossElementsListsByFieldsAndWidgets() {
        RecordingAndroidDriver driver = new RecordingAndroidDriver();
        CustomFinderPage page = new CustomFinderPage();
        initElements(new AppiumFieldDecorator(driver, Duration.ZERO), page);

        By expectedButtonBy = AppiumBy.androidUIAutomator(
                buildScrollIntoViewExpression("com.test:id/buttonNotInView")
        );
        assertEquals(expectedButtonBy, driver.captureFirstBy(page.scrollingButton::click));

        assertEquals(
                AppiumBy.androidUIAutomator(buildScrollIntoViewExpression("com.test:id/appiumButton")),
                driver.captureFirstBy(page.scrollingAppiumButton::click)
        );

        assertEquals(2, page.scrollingItems.size());
        assertEquals(
                AppiumBy.androidUIAutomator(buildScrollIntoViewExpression("com.test:id/listItem")),
                driver.recordedBys.get(driver.recordedBys.size() - 1)
        );

        By expectedChained = new ByChained(new By[]{
            AppiumBy.androidUIAutomator(buildScrollIntoViewExpression("com.test:id/container")),
            AppiumBy.androidUIAutomator(buildScrollIntoViewExpression("com.test:id/nestedRow"))
        });
        assertEquals(expectedChained.toString(), driver.captureFirstBy(page.repeatedScrollingRow::click).toString());

        assertInstanceOf(ContentMappedBy.class, page.scrollingBy);
        assertEquals(
                AppiumBy.androidUIAutomator(buildScrollIntoViewExpression("com.test:id/byTarget")),
                driver.captureFirstBy(() -> driver.findElement(page.scrollingBy))
        );
        assertEquals(
                AppiumBy.androidUIAutomator(buildScrollIntoViewExpression("com.test:id/appiumByTarget")),
                page.scrollingAppiumBy
        );

        assertEquals(
                By.cssSelector("[data-testid='inferredFieldIdButton']"),
                driver.captureFirstBy(page.inferredFieldIdButton::click)
        );

        driver.recordedBys.clear();
        page.fieldAnnotatedWidget.tapInner();
        assertEquals(
                List.of(
                        AppiumBy.androidUIAutomator(buildScrollIntoViewExpression("com.test:id/widgetContainer")),
                        AppiumBy.androidUIAutomator(buildScrollIntoViewExpression("com.test:id/innerAction"))
                ),
                driver.recordedBys
        );

        assertEquals(2, page.fieldAnnotatedWidgets.size());
        driver.recordedBys.clear();
        page.fieldAnnotatedWidgets.get(0).tapInner();
        assertEquals(
                List.of(
                        AppiumBy.androidUIAutomator(buildScrollIntoViewExpression("com.test:id/widgetRow")),
                        AppiumBy.androidUIAutomator(buildScrollIntoViewExpression("com.test:id/innerAction"))
                ),
                driver.recordedBys
        );
    }

    static class ClassOnlyWidgetPage {
        ClassAnnotatedCardWidget classAnnotatedWidget;
    }

    @Test
    void supportsClassLevelInheritedAndOverriddenWidgetCustomAnnotations() {
        RecordingAndroidDriver driver = new RecordingAndroidDriver();

        ClassOnlyWidgetPage classPage = new ClassOnlyWidgetPage();
        ScrollingAndroidFindBy.Builder.lastSeenField = CustomFinderPage.class.getDeclaredFields()[0];
        initElements(new AppiumFieldDecorator(driver, Duration.ZERO), classPage);
        assertNull(ScrollingAndroidFindBy.Builder.lastSeenField);

        assertEquals(
                AppiumBy.androidUIAutomator(buildScrollIntoViewExpression("com.test:id/classAnnotatedCard")),
                driver.captureFirstBy(classPage.classAnnotatedWidget::tapRoot)
        );

        CustomFinderPage page = new CustomFinderPage();
        initElements(new AppiumFieldDecorator(driver, Duration.ZERO), page);
        assertEquals(
                By.cssSelector("[data-testid='inherited-card']"),
                driver.captureFirstBy(page.inheritedWidget::tapRoot)
        );
        assertEquals(
                By.cssSelector("[data-testid='overridden-sub-card']"),
                driver.captureFirstBy(page.overridingWidget::tapRoot)
        );
    }

    @Test
    void combinesCustomFinderWithMobileNativeAnnotationsAcrossContexts() {
        RecordingAndroidDriver driver = new RecordingAndroidDriver();
        CustomFinderPage page = new CustomFinderPage();
        initElements(new AppiumFieldDecorator(driver, Duration.ZERO), page);

        assertEquals(
                new ByChained(new By[]{AppiumBy.id("com.test:id/native_checkout")}).toString(),
                driver.captureFirstBy(page.hybridCheckoutButton::click).toString()
        );
        assertEquals(
                new ByChained(new By[]{AppiumBy.id("com.test:id/native_checkout_by")}).toString(),
                driver.captureFirstBy(() -> driver.findElement(page.hybridCheckoutBy)).toString()
        );

        driver.context("WEBVIEW_1");
        assertEquals(
                By.cssSelector("[data-testid='web-checkout']"),
                driver.captureFirstBy(page.hybridCheckoutButton::click)
        );
        assertEquals(
                By.cssSelector("[data-testid='web-checkout-by']"),
                driver.captureFirstBy(() -> driver.findElement(page.hybridCheckoutBy))
        );
        assertEquals(
                AppiumBy.androidUIAutomator(buildScrollIntoViewExpression("com.test:id/buttonNotInView")),
                driver.captureFirstBy(page.scrollingButton::click)
        );
    }

    @Test
    void supportsLegacySeleniumPageFactoryFinderAnnotations() {
        RecordingAndroidDriver driver = new RecordingAndroidDriver();
        CustomFinderPage page = new CustomFinderPage();
        initElements(new AppiumFieldDecorator(driver, Duration.ZERO), page);

        assertEquals(
                AppiumBy.accessibilityId("legacy_submit"),
                driver.captureFirstBy(page.legacyElement::click)
        );
        assertEquals(2, page.legacyList.size());
        assertEquals(
                AppiumBy.accessibilityId("legacy_list"),
                driver.recordedBys.get(driver.recordedBys.size() - 1)
        );

        driver.recordedBys.clear();
        page.legacyWidget.tapInner();
        assertEquals(
                List.of(
                        AppiumBy.accessibilityId("legacy_widget"),
                        AppiumBy.androidUIAutomator(buildScrollIntoViewExpression("com.test:id/innerAction"))
                ),
                driver.recordedBys
        );

        assertEquals(AppiumBy.accessibilityId("legacy_by"), page.legacyAppiumBy);
    }

    @Test
    void rejectsConflictingCustomAndBuiltinFinderAnnotations() {
        RecordingAndroidDriver driver = new RecordingAndroidDriver();

        IllegalArgumentException e1 = assertThrows(
                IllegalArgumentException.class,
                () -> initElements(new AppiumFieldDecorator(driver), new ConflictingCustomAndFindByPage())
        );
        assertTrue(e1.getMessage().contains("@ScrollingAndroidFindBy"), e1.getMessage());
        assertTrue(e1.getMessage().contains("@FindBy"), e1.getMessage());

        IllegalArgumentException e2 = assertThrows(
                IllegalArgumentException.class,
                () -> initElements(new AppiumFieldDecorator(driver), new ConflictingTwoCustomFindersPage())
        );
        assertTrue(e2.getMessage().contains("@ScrollingAndroidFindBy"), e2.getMessage());
        assertTrue(e2.getMessage().contains("@DataTestId"), e2.getMessage());

        IllegalArgumentException e3 = assertThrows(
                IllegalArgumentException.class,
                () -> initElements(new AppiumFieldDecorator(driver), new ConflictingCustomAndSeleniumFindByPage())
        );
        assertTrue(e3.getMessage().contains("@ScrollingAndroidFindBy"), e3.getMessage());
        assertTrue(e3.getMessage().contains("@FindBy"), e3.getMessage());

        IllegalArgumentException e4 = assertThrows(
                IllegalArgumentException.class,
                () -> initElements(new AppiumFieldDecorator(driver), new ConflictingWidgetClassPage())
        );
        assertTrue(e4.getMessage().contains("@ScrollingAndroidFindBy"), e4.getMessage());
        assertTrue(e4.getMessage().contains("@FindBy"), e4.getMessage());
    }
}
