package io.appium.java_client.pagefactory_tests.widget.tests.combined;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.OverrideWidget;
import io.appium.java_client.pagefactory_tests.widget.tests.AbstractApp;
import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import io.appium.java_client.pagefactory_tests.widget.tests.DefaultStubWidget;
import io.appium.java_client.pagefactory_tests.widget.tests.android.DefaultAndroidWidget;
import org.hamcrest.Matchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.openqa.selenium.support.PageFactory.initElements;

@SuppressWarnings({"unused", "unchecked"})
public class CombinedAppTest {

    /**
     * Test data generation.
     *
     * @return test parameters
     */
    public static Stream<Arguments> data() {
        return Stream.of(
                arguments(new CombinedApp(), new AbstractStubWebDriver.StubAndroidDriver(), DefaultAndroidWidget.class),
                arguments(new CombinedApp(), new AbstractStubWebDriver.StubIOSXCUITDriver(),
                        DefaultIosXCUITWidget.class),
                arguments(new CombinedApp(), new AbstractStubWebDriver.StubBrowserDriver(), DefaultFindByWidget.class),
                arguments(new CombinedApp(), new AbstractStubWebDriver.StubAndroidBrowserOrWebViewDriver(),
                    DefaultFindByWidget.class),
                arguments(new PartiallyCombinedApp(), new AbstractStubWebDriver.StubAndroidDriver(),
                        DefaultAndroidWidget.class),
                arguments(new PartiallyCombinedApp(), new AbstractStubWebDriver.StubIOSXCUITDriver(),
                        DefaultStubWidget.class),
                arguments(new PartiallyCombinedApp(), new AbstractStubWebDriver.StubWindowsDriver(),
                        DefaultStubWidget.class),
                arguments(new PartiallyCombinedApp(), new AbstractStubWebDriver.StubBrowserDriver(),
                        DefaultFindByWidget.class),
                arguments(new PartiallyCombinedApp(), new AbstractStubWebDriver.StubAndroidBrowserOrWebViewDriver(),
                    DefaultFindByWidget.class)
        );
    }

    @ParameterizedTest
    @MethodSource("data")
    void checkThatWidgetsAreCreatedCorrectly(AbstractApp app, WebDriver driver,
                                             Class<? extends DefaultStubWidget> widgetClass) {
        initElements(new AppiumFieldDecorator(driver), app);
        assertThat("Expected widget class was " + widgetClass.getName(),
                app.getWidget().getSelfReference().getClass(),
                equalTo(widgetClass));
        assertThat(app.getWidget().getSelfReference().getClass(),
                Matchers.instanceOf(WebElement.class));

        List<Class<?>> classes = app.getWidgets().stream().map(abstractWidget -> abstractWidget
                .getSelfReference().getClass())
                .collect(toList());
        assertThat(classes,
                contains(widgetClass, widgetClass));
    }

    public static class CombinedApp implements AbstractApp {

        @OverrideWidget(html = DefaultFindByWidget.class,
                androidUIAutomator = DefaultAndroidWidget.class,
                iOSXCUITAutomation = DefaultIosXCUITWidget.class)
        private DefaultStubWidget singleWidget;

        @OverrideWidget(html = DefaultFindByWidget.class,
                androidUIAutomator = DefaultAndroidWidget.class,
                iOSXCUITAutomation = DefaultIosXCUITWidget.class)
        private List<DefaultStubWidget> multipleWidget;

        @Override
        public DefaultStubWidget getWidget() {
            return singleWidget;
        }

        @Override
        public List<DefaultStubWidget> getWidgets() {
            return multipleWidget;
        }
    }

    public static class PartiallyCombinedApp implements AbstractApp {

        @OverrideWidget(html = DefaultFindByWidget.class,
                androidUIAutomator = DefaultAndroidWidget.class)
        private DefaultStubWidget singleWidget;

        @OverrideWidget(html = DefaultFindByWidget.class,
                androidUIAutomator = DefaultAndroidWidget.class)
        private List<DefaultStubWidget> multipleWidget;

        @Override
        public DefaultStubWidget getWidget() {
            return singleWidget;
        }

        @Override
        public List<DefaultStubWidget> getWidgets() {
            return multipleWidget;
        }
    }
}
