package io.appium.java_client.pagefactory_tests.widget.tests.combined;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import io.appium.java_client.pagefactory.OverrideWidget;
import io.appium.java_client.pagefactory_tests.widget.tests.AbstractApp;
import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import io.appium.java_client.pagefactory_tests.widget.tests.DefaultStubWidget;
import io.appium.java_client.pagefactory_tests.widget.tests.WidgetTest;
import io.appium.java_client.pagefactory_tests.widget.tests.android.DefaultAndroidWidget;
import io.appium.java_client.pagefactory_tests.widget.tests.ios.DefaultIosWidget;
import io.appium.java_client.pagefactory_tests.widget.tests.windows.DefaultWindowsWidget;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class CombinedAppTest extends WidgetTest {

    private final Class<?> widgetClass;

    /**
     * Test data generation.
     *
     * @return test parameters
     */
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return asList(
                dataArray(new CombinedApp(), new AbstractStubWebDriver.StubAndroidDriver(), DefaultAndroidWidget.class),
                dataArray(new CombinedApp(), new AbstractStubWebDriver.StubSelendroidDriver(),
                        DefaultSelendroidWidget.class),
                dataArray(new CombinedApp(), new AbstractStubWebDriver.StubIOSDriver(), DefaultIosWidget.class),
                dataArray(new CombinedApp(), new AbstractStubWebDriver.StubIOSXCUITDriver(),
                        DefaultIosXCUITWidget.class),
                dataArray(new CombinedApp(), new AbstractStubWebDriver.StubWindowsDriver(), DefaultWindowsWidget.class),
                dataArray(new CombinedApp(), new AbstractStubWebDriver.StubBrowserDriver(), DefaultFindByWidget.class),
                dataArray(new CombinedApp(), new AbstractStubWebDriver.StubAndroidBrowserOrWebViewDriver(),
                    DefaultFindByWidget.class),
                dataArray(new PartiallyCombinedApp(), new AbstractStubWebDriver.StubAndroidDriver(),
                        DefaultAndroidWidget.class),
                dataArray(new PartiallyCombinedApp(), new AbstractStubWebDriver.StubSelendroidDriver(),
                    DefaultSelendroidWidget.class),
                dataArray(new PartiallyCombinedApp(), new AbstractStubWebDriver.StubIOSDriver(),
                        DefaultStubWidget.class),
                dataArray(new PartiallyCombinedApp(), new AbstractStubWebDriver.StubIOSXCUITDriver(),
                        DefaultStubWidget.class),
                dataArray(new PartiallyCombinedApp(), new AbstractStubWebDriver.StubWindowsDriver(),
                        DefaultStubWidget.class),
                dataArray(new PartiallyCombinedApp(), new AbstractStubWebDriver.StubBrowserDriver(),
                        DefaultFindByWidget.class),
                dataArray(new PartiallyCombinedApp(), new AbstractStubWebDriver.StubAndroidBrowserOrWebViewDriver(),
                    DefaultFindByWidget.class)
        );
    }

    public CombinedAppTest(AbstractApp app, WebDriver driver, Class<? extends DefaultStubWidget> widgetClass) {
        super(app, driver);
        this.widgetClass = widgetClass;
    }

    @Override
    public void checkThatWidgetsAreCreatedCorrectly() {
        assertThat("Excpected widget class was " + widgetClass.getName(),
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
                selendroid = DefaultSelendroidWidget.class,
                iOSUIAutomation = DefaultIosWidget.class,
                iOSXCUITAutomation = DefaultIosXCUITWidget.class,
                windowsAutomation = DefaultWindowsWidget.class
        )
        private DefaultStubWidget singleWidget;

        @OverrideWidget(html = DefaultFindByWidget.class,
                androidUIAutomator = DefaultAndroidWidget.class,
                selendroid = DefaultSelendroidWidget.class,
                iOSUIAutomation = DefaultIosWidget.class,
                iOSXCUITAutomation = DefaultIosXCUITWidget.class,
                windowsAutomation = DefaultWindowsWidget.class
        )
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
                androidUIAutomator = DefaultAndroidWidget.class,
                selendroid = DefaultSelendroidWidget.class
        )
        private DefaultStubWidget singleWidget;

        @OverrideWidget(html = DefaultFindByWidget.class,
                androidUIAutomator = DefaultAndroidWidget.class,
                selendroid = DefaultSelendroidWidget.class
        )
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
