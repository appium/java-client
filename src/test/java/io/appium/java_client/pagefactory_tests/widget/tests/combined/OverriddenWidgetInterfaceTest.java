package io.appium.java_client.pagefactory_tests.widget.tests.combined;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.OverrideWidget;
import io.appium.java_client.pagefactory.Widget;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import io.appium.java_client.support.FindBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Stream;

import static io.appium.java_client.support.PageFactory.initElements;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        assertEquals(2, page.widgets.size());
        page.widgets.forEach(widget -> assertEquals(expected, widget.platformAction()));
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

    public interface ActionWidget {
        String platformAction();
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
    }

    public static class HtmlWidget extends Widget implements ActionWidget {
        protected HtmlWidget(WebElement element) {
            super(element);
        }

        @Override
        public String platformAction() {
            return "html";
        }
    }

    public static class UnrelatedWidget extends Widget {
        protected UnrelatedWidget(WebElement element) {
            super(element);
        }
    }
}
