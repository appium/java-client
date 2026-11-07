package io.appium.java_client.pagefactory_tests.widget.tests.combined;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.OverrideWidget;
import io.appium.java_client.pagefactory.Widget;
import io.appium.java_client.pagefactory.WidgetInterceptor;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import io.appium.java_client.proxy.HasMethodCallListeners;
import io.appium.java_client.proxy.MethodCallListener;
import io.appium.java_client.support.FindBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

import static io.appium.java_client.support.PageFactory.initElements;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OverriddenWidgetInterfaceTest {

    static Stream<Arguments> drivers() {
        return Stream.of(
                Arguments.of(new AbstractStubWebDriver.StubAndroidDriver(), "android"),
                Arguments.of(new AbstractStubWebDriver.StubIOSXCUITDriver(), "ios"),
                Arguments.of(new AbstractStubWebDriver.StubBrowserDriver(), "html"),
                Arguments.of(new AbstractStubWebDriver.StubAndroidBrowserOrWebViewDriver(), "html")
        );
    }

    @ParameterizedTest
    @MethodSource("drivers")
    void delegatesInterfaceMethodToPlatformWidget(WebDriver driver, String expected) {
        var page = new Page();
        initElements(new AppiumFieldDecorator(driver), page);

        assertEquals(expected, page.widget.platformAction());
        assertEquals(expected + "-title", page.widget.title());
        assertEquals(2, page.widgets.size());
        page.widgets.forEach(widget -> assertEquals(expected, widget.platformAction()));
    }

    @Test
    void defaultMethodNotOverriddenRunsOnTheRealWidgetForSingleAndList() {
        var page = new Page();
        initElements(new AppiumFieldDecorator(new AbstractStubWebDriver.StubAndroidDriver()), page);

        assertEquals("AndroidWidget:android", page.widget.described());
        assertEquals("AndroidWidget:android", page.widgets.get(0).described());
    }

    @Test
    void singleFieldObjectMethodsUseTheLocatorAndProxyIdentity() {
        var page = new Page();
        initElements(new AppiumFieldDecorator(new AbstractStubWebDriver.StubAndroidDriver()), page);

        assertTrue(page.widget.toString().contains("Located by"));
        assertTrue(page.widget.equals(page.widget));
        assertFalse(page.widget.equals(null));
        assertEquals(page.widget.hashCode(), page.widget.hashCode());
    }

    @Test
    void listItemObjectMethodsAreDispatchedToTheWidget() {
        var page = new IdentityPage();
        initElements(new AppiumFieldDecorator(new AbstractStubWebDriver.StubAndroidDriver()), page);
        IdentityContract item = page.widgets.get(0);

        assertEquals("id-widget", item.toString());
        assertEquals(17, item.hashCode());
        assertTrue(item.equals("token"));
        assertFalse(item.equals("other"));
    }

    @Test
    void listItemWorksAfterItsWeakElementReferenceIsCleared() throws Exception {
        var page = new Page();
        initElements(new AppiumFieldDecorator(new AbstractStubWebDriver.StubAndroidDriver()), page);
        ActionWidget item = page.widgets.get(0);
        clearWeakElementReference(item);

        assertEquals("android", item.platformAction());
    }

    @Test
    void nestedFindByOnTheConcreteWidgetIsInitialized() {
        var page = new NestedPage();
        initElements(new AppiumFieldDecorator(new AbstractStubWebDriver.StubAndroidDriver()), page);

        assertTrue(page.widget.labelText().contains("label"));
    }

    @Test
    void packagePrivateInterfaceFailsBeforeProxyGeneration() {
        var page = new HiddenPage();

        IllegalArgumentException error = assertThrows(
                IllegalArgumentException.class,
                () -> initElements(new AppiumFieldDecorator(new AbstractStubWebDriver.StubAndroidDriver()), page)
        );
        assertTrue(error.getMessage().contains("public"));
        assertNull(error.getCause());
    }

    private static void clearWeakElementReference(Object proxy) throws Exception {
        MethodCallListener listener = ((HasMethodCallListeners) proxy).getMethodCallListeners()[0];
        Field reference = WidgetInterceptor.class.getDeclaredField("cachedElementReference");
        reference.setAccessible(true);
        reference.set(listener, new WeakReference<WebElement>(null));
    }

    @Test
    void usesLocatorDeclaredOnThePlatformWidgetClass() {
        var page = new ClassLocatedPage();
        initElements(new AppiumFieldDecorator(new AbstractStubWebDriver.StubAndroidDriver()), page);

        assertEquals("android", page.widget.platformAction());
        assertTrue(page.widget.toString().contains("android-root"));
        assertEquals(2, page.widgets.size());
        page.widgets.forEach(widget -> assertEquals("android", widget.platformAction()));
    }

    @Test
    void allowsPartialOverrideForTheCurrentContent() {
        var page = new IosOnlyPage();
        initElements(new AppiumFieldDecorator(new AbstractStubWebDriver.StubIOSXCUITDriver()), page);

        assertEquals("ios", page.widget.platformAction());
    }

    @Test
    void missingContentOverrideFailsWhenThatContentIsUsed() {
        var page = new IosOnlyPage();
        initElements(new AppiumFieldDecorator(new AbstractStubWebDriver.StubAndroidDriver()), page);

        InstantiationException error = assertThrows(InstantiationException.class, () -> page.widget.platformAction());
        assertTrue(error.getMessage().contains("NATIVE_MOBILE_SPECIFIC"));
    }

    @Test
    void rejectsOverrideClassThatDoesNotImplementTheInterface() {
        var page = new MismatchedPage();

        IllegalArgumentException error = assertThrows(
                IllegalArgumentException.class,
                () -> initElements(new AppiumFieldDecorator(new AbstractStubWebDriver.StubAndroidDriver()), page)
        );
        assertTrue(error.getCause().getMessage().contains("is not assignable from"));
    }

    @Test
    void leavesUnannotatedInterfaceFieldUnset() {
        var page = new PlainPage();
        initElements(new AppiumFieldDecorator(new AbstractStubWebDriver.StubAndroidDriver()), page);

        assertNull(page.untouched);
    }

    static class Page {
        @OverrideWidget(html = HtmlWidget.class,
                androidUIAutomator = AndroidWidget.class,
                iOSXCUITAutomation = IosWidget.class)
        @FindBy(id = "widget")
        private ActionWidget widget;

        @OverrideWidget(html = HtmlWidget.class,
                androidUIAutomator = AndroidWidget.class,
                iOSXCUITAutomation = IosWidget.class)
        @FindBy(id = "widget")
        private List<ActionWidget> widgets;
    }

    static class ClassLocatedPage {
        @OverrideWidget(html = HtmlWidget.class,
                androidUIAutomator = AndroidWidget.class,
                iOSXCUITAutomation = IosWidget.class)
        private ActionWidget widget;

        @OverrideWidget(html = HtmlWidget.class,
                androidUIAutomator = AndroidWidget.class,
                iOSXCUITAutomation = IosWidget.class)
        private List<ActionWidget> widgets;
    }

    static class IosOnlyPage {
        @OverrideWidget(iOSXCUITAutomation = IosWidget.class)
        @FindBy(id = "widget")
        private ActionWidget widget;
    }

    static class MismatchedPage {
        @OverrideWidget(androidUIAutomator = UnrelatedWidget.class,
                iOSXCUITAutomation = IosWidget.class,
                html = HtmlWidget.class)
        @FindBy(id = "widget")
        private ActionWidget widget;
    }

    static class PlainPage {
        private ActionWidget untouched;
    }

    static class IdentityPage {
        @OverrideWidget(html = IdentityHtmlWidget.class,
                androidUIAutomator = IdentityAndroidWidget.class,
                iOSXCUITAutomation = IdentityIosWidget.class)
        @FindBy(id = "widget")
        private List<IdentityContract> widgets;
    }

    static class NestedPage {
        @OverrideWidget(html = NestedHtmlWidget.class,
                androidUIAutomator = NestedAndroidWidget.class,
                iOSXCUITAutomation = NestedIosWidget.class)
        private NestedContract widget;
    }

    static class HiddenPage {
        @OverrideWidget(html = HiddenWidget.class,
                androidUIAutomator = HiddenWidget.class,
                iOSXCUITAutomation = HiddenWidget.class)
        @FindBy(id = "widget")
        private PackagePrivateWidget widget;
    }

    public interface ActionWidget {
        String platformAction();

        default String title() {
            return "default";
        }

        /**
         * Not overridden by the platform widgets. {@code getClass} shows which instance ran it.
         */
        default String described() {
            return getClass().getSimpleName() + ":" + platformAction();
        }
    }

    public interface IdentityContract {
        String platformAction();
    }

    public interface NestedContract {
        String labelText();
    }

    @AndroidFindBy(uiAutomator = "android-root")
    public static class AndroidWidget extends Widget implements ActionWidget {
        protected AndroidWidget(WebElement element) {
            super(element);
        }

        @Override
        public String platformAction() {
            return "android";
        }

        @Override
        public String title() {
            return "android-title";
        }
    }

    @iOSXCUITFindBy(iOSNsPredicate = "ios-root")
    public static class IosWidget extends Widget implements ActionWidget {
        protected IosWidget(WebElement element) {
            super(element);
        }

        @Override
        public String platformAction() {
            return "ios";
        }

        @Override
        public String title() {
            return "ios-title";
        }
    }

    public static class HtmlWidget extends Widget implements ActionWidget {
        protected HtmlWidget(WebElement element) {
            super(element);
        }

        @Override
        public String platformAction() {
            return "html";
        }

        @Override
        public String title() {
            return "html-title";
        }
    }

    public static class UnrelatedWidget extends Widget {
        protected UnrelatedWidget(WebElement element) {
            super(element);
        }
    }

    public static class IdentityAndroidWidget extends Widget implements IdentityContract {
        protected IdentityAndroidWidget(WebElement element) {
            super(element);
        }

        @Override
        public String platformAction() {
            return "android";
        }

        @Override
        public String toString() {
            return "id-widget";
        }

        @Override
        public int hashCode() {
            return 17;
        }

        @Override
        public boolean equals(Object obj) {
            return "token".equals(obj);
        }
    }

    public static class IdentityIosWidget extends IdentityAndroidWidget {
        protected IdentityIosWidget(WebElement element) {
            super(element);
        }
    }

    public static class IdentityHtmlWidget extends IdentityAndroidWidget {
        protected IdentityHtmlWidget(WebElement element) {
            super(element);
        }
    }

    @AndroidFindBy(uiAutomator = "android-root")
    public static class NestedAndroidWidget extends Widget implements NestedContract {
        @FindBy(id = "label")
        private WebElement label;

        protected NestedAndroidWidget(WebElement element) {
            super(element);
        }

        @Override
        public String labelText() {
            return label.toString();
        }
    }

    public static class NestedIosWidget extends Widget implements NestedContract {
        protected NestedIosWidget(WebElement element) {
            super(element);
        }

        @Override
        public String labelText() {
            return "ios";
        }
    }

    public static class NestedHtmlWidget extends Widget implements NestedContract {
        protected NestedHtmlWidget(WebElement element) {
            super(element);
        }

        @Override
        public String labelText() {
            return "html";
        }
    }

    public static class HiddenWidget extends Widget implements PackagePrivateWidget {
        protected HiddenWidget(WebElement element) {
            super(element);
        }

        @Override
        public String platformAction() {
            return "hidden";
        }
    }
}
