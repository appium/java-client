package io.appium.java_client.pagefactory_tests.widget.tests.combined;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.OverrideWidget;
import io.appium.java_client.pagefactory.Widget;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.PageFactory.initElements;

class AbstractWidgetMethodTest {

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
    void delegatesAbstractMethodToPlatformSpecificWidget(WebDriver driver, String expected) {
        var page = new Page();
        initElements(new AppiumFieldDecorator(driver), page);

        assertEquals(expected, page.widget.platformAction());
        assertEquals(2, page.widgets.size());
        page.widgets.forEach(widget -> assertEquals(expected, widget.platformAction()));
    }

    static class Page {
        @OverrideWidget(html = HtmlWidget.class,
                androidUIAutomator = AndroidWidget.class,
                iOSXCUITAutomation = IosWidget.class)
        @FindBy(id = "widget")
        private AbstractActionWidget widget;

        @OverrideWidget(html = HtmlWidget.class,
                androidUIAutomator = AndroidWidget.class,
                iOSXCUITAutomation = IosWidget.class)
        @FindBy(id = "widget")
        private List<AbstractActionWidget> widgets;
    }

    public abstract static class AbstractActionWidget extends Widget {
        protected AbstractActionWidget(WebElement element) {
            super(element);
        }

        public abstract String platformAction();
    }

    @AndroidFindBy(uiAutomator = "widget")
    public static class AndroidWidget extends AbstractActionWidget {
        protected AndroidWidget(WebElement element) {
            super(element);
        }

        @Override
        public String platformAction() {
            return "android";
        }
    }

    @iOSXCUITFindBy(iOSNsPredicate = "widget")
    public static class IosWidget extends AbstractActionWidget {
        protected IosWidget(WebElement element) {
            super(element);
        }

        @Override
        public String platformAction() {
            return "ios";
        }
    }

    public static class HtmlWidget extends AbstractActionWidget {
        protected HtmlWidget(WebElement element) {
            super(element);
        }

        @Override
        public String platformAction() {
            return "html";
        }
    }
}
