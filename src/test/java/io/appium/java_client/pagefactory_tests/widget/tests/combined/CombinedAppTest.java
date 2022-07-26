package io.appium.java_client.pagefactory_tests.widget.tests.combined;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

import io.appium.java_client.pagefactory.OverrideWidget;
import io.appium.java_client.pagefactory_tests.widget.tests.AbstractApp;
import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import io.appium.java_client.pagefactory_tests.widget.tests.DefaultStubWidget;
import io.appium.java_client.pagefactory_tests.widget.tests.WidgetTest;
import io.appium.java_client.pagefactory_tests.widget.tests.android.DefaultAndroidWidget;
import io.appium.java_client.pagefactory_tests.widget.tests.windows.DefaultWindowsWidget;
import org.junit.jupiter.params.provider.Arguments;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings({"unused", "unchecked"})

public class CombinedAppTest extends WidgetTest {

    private final Class<?> widgetClass;

    /**
     * Test data generation.
     *
     * @return test parameters
     */

    public static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of(new CombinedApp(), new AbstractStubWebDriver.StubAndroidDriver(), DefaultAndroidWidget.class),
                Arguments.of(new CombinedApp(), new AbstractStubWebDriver.StubIOSXCUITDriver(),
                    DefaultIosXCUITWidget.class),
                Arguments.of(new CombinedApp(), new AbstractStubWebDriver.StubWindowsDriver(), DefaultWindowsWidget.class),
                Arguments.of(new CombinedApp(), new AbstractStubWebDriver.StubBrowserDriver(), DefaultFindByWidget.class),
                Arguments.of(new CombinedApp(), new AbstractStubWebDriver.StubAndroidBrowserOrWebViewDriver(),
                    DefaultFindByWidget.class),
                Arguments.of(new PartiallyCombinedApp(), new AbstractStubWebDriver.StubAndroidDriver(),
                    DefaultAndroidWidget.class),
                Arguments.of(new PartiallyCombinedApp(), new AbstractStubWebDriver.StubIOSXCUITDriver(),
                    DefaultStubWidget.class),
                Arguments.of(new PartiallyCombinedApp(), new AbstractStubWebDriver.StubWindowsDriver(),
                    DefaultStubWidget.class),
                Arguments.of(new PartiallyCombinedApp(), new AbstractStubWebDriver.StubBrowserDriver(),
                    DefaultFindByWidget.class),
                Arguments.of(new PartiallyCombinedApp(), new AbstractStubWebDriver.StubAndroidBrowserOrWebViewDriver(),
                    DefaultFindByWidget.class)
        );
    }

    public CombinedAppTest(AbstractApp app, WebDriver driver, Class<? extends DefaultStubWidget> widgetClass) {
        super(app, driver);
        this.widgetClass = widgetClass;
    }

    @Override
    public void checkThatWidgetsAreCreatedCorrectly() {
        assertThat("Expected widget class was " + widgetClass.getName(),
                app.getWidget().getSelfReference().getClass(),
                equalTo(widgetClass));

        List<Class<?>> classes = app.getWidgets().stream().map(abstractWidget -> abstractWidget
                .getSelfReference().getClass())
                .collect(toList());
        assertThat(classes,
                contains(widgetClass, widgetClass));
    }

    public static class CombinedApp implements AbstractApp {

        @OverrideWidget(html = DefaultFindByWidget.class,
                androidUIAutomator = DefaultAndroidWidget.class,
                iOSXCUITAutomation = DefaultIosXCUITWidget.class,
                windowsAutomation = DefaultWindowsWidget.class)
        private DefaultStubWidget singleWidget;

        @OverrideWidget(html = DefaultFindByWidget.class,
                androidUIAutomator = DefaultAndroidWidget.class,
                iOSXCUITAutomation = DefaultIosXCUITWidget.class,
                windowsAutomation = DefaultWindowsWidget.class)
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
