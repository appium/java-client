package io.appium.java_client.pagefactory_tests.widget.tests.combined;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import io.appium.java_client.pagefactory.OverrideWidget;
import io.appium.java_client.pagefactory_tests.widget.tests.AbstractApp;
import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import io.appium.java_client.pagefactory_tests.widget.tests.AbstractWidget;
import io.appium.java_client.pagefactory_tests.widget.tests.WidgetTest;
import io.appium.java_client.pagefactory_tests.widget.tests.android.DefaultAndroidWidget;
import io.appium.java_client.pagefactory_tests.widget.tests.ios.DefaultIosWidget;
import io.appium.java_client.pagefactory_tests.widget.tests.windows.DefaultWindowsWidget;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@RunWith(Parameterized.class)
public class CombinedWidgetTest extends WidgetTest {

    /**
     * Test data generation.
     *
     * @return test parameters
     */
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return asList(new Object[][] {
                { new AppWithCombinedWidgets(),
                    new AbstractStubWebDriver.StubAndroidDriver(), DefaultAndroidWidget.class},
                { new AppWithCombinedWidgets(),
                    new AbstractStubWebDriver.StubSelendroidDriver(), DefaultSelendroidWidget.class},
                { new AppWithCombinedWidgets(),
                    new AbstractStubWebDriver.StubIOSDriver(), DefaultIosWidget.class},
                { new AppWithCombinedWidgets(),
                    new AbstractStubWebDriver.StubIOSXCUITDriver(), DefaultIosXCUITWidget.class},
                { new AppWithCombinedWidgets(),
                    new AbstractStubWebDriver.StubWindowsDriver(), DefaultWindowsWidget.class},
                { new AppWithCombinedWidgets(),
                    new AbstractStubWebDriver.StubBrowserDriver(), DefaultFindByWidget.class},
                { new AppWithCombinedWidgets(),
                    new AbstractStubWebDriver.StubAndroidBrowserOrWebViewDriver(), DefaultFindByWidget.class},
                { new AppWithPartiallyCombinedWidgets(),
                    new AbstractStubWebDriver.StubAndroidDriver(), DefaultAndroidWidget.class},
                { new AppWithPartiallyCombinedWidgets(),
                    new AbstractStubWebDriver.StubSelendroidDriver(), DefaultSelendroidWidget.class},
                { new AppWithPartiallyCombinedWidgets(),
                    new AbstractStubWebDriver.StubIOSDriver(), AbstractWidget.class},
                { new AppWithPartiallyCombinedWidgets(),
                    new AbstractStubWebDriver.StubIOSXCUITDriver(), AbstractWidget.class},
                { new AppWithPartiallyCombinedWidgets(),
                    new AbstractStubWebDriver.StubWindowsDriver(), AbstractWidget.class},
                { new AppWithPartiallyCombinedWidgets(),
                    new AbstractStubWebDriver.StubBrowserDriver(), DefaultFindByWidget.class},
                { new AppWithPartiallyCombinedWidgets(),
                    new AbstractStubWebDriver.StubAndroidBrowserOrWebViewDriver(), DefaultFindByWidget.class}
        });
    }

    private final Class<?> widgetClass;

    public CombinedWidgetTest(AbstractApp app, WebDriver driver, Class<?> widgetClass) {
        super(app, driver);
        this.widgetClass = widgetClass;
    }

    @Override
    public void checkACommonWidget() {
        assertThat("Excpected widget class was " + widgetClass.getName(),
                app.getWidget().getSubWidget().getSelfReference().getClass(),
                equalTo(widgetClass));

        List<Class<?>> classes = new ArrayList<>();

        app.getWidgets().forEach(abstractWidget ->
                classes.addAll(abstractWidget.getSubWidgets().stream()
                        .map(widget -> widget.getSelfReference().getClass()).collect(toList())));

        assertThat(classes,
                contains(widgetClass, widgetClass, widgetClass, widgetClass));
    }

    public static class CombinedWidget extends AbstractWidget {

        @OverrideWidget(html = DefaultFindByWidget.class,
                androidUIAutomator = DefaultAndroidWidget.class,
                selendroid = DefaultSelendroidWidget.class,
                iOSUIAutomation = DefaultIosWidget.class,
                iOSXCUITAutomation = DefaultIosXCUITWidget.class,
                windowsAutomation = DefaultWindowsWidget.class
        )
        private AbstractWidget singleWidget;

        @OverrideWidget(html = DefaultFindByWidget.class,
                androidUIAutomator = DefaultAndroidWidget.class,
                selendroid = DefaultSelendroidWidget.class,
                iOSUIAutomation = DefaultIosWidget.class,
                iOSXCUITAutomation = DefaultIosXCUITWidget.class,
                windowsAutomation = DefaultWindowsWidget.class
        )
        private List<AbstractWidget> multipleWidget;


        protected CombinedWidget(WebElement element) {
            super(element);
        }

        @Override
        public AbstractWidget getSubWidget() {
            return singleWidget;
        }

        @Override
        public List<AbstractWidget> getSubWidgets() {
            return multipleWidget;
        }
    }

    public static class PartiallyCombinedWidget extends AbstractWidget {

        @OverrideWidget(html = DefaultFindByWidget.class,
                androidUIAutomator = DefaultAndroidWidget.class,
                selendroid = DefaultSelendroidWidget.class
        )
        private AbstractWidget singleWidget;

        @OverrideWidget(html = DefaultFindByWidget.class,
                androidUIAutomator = DefaultAndroidWidget.class,
                selendroid = DefaultSelendroidWidget.class
        )
        private List<AbstractWidget> multipleWidget;


        protected PartiallyCombinedWidget(WebElement element) {
            super(element);
        }

        @Override
        public AbstractWidget getSubWidget() {
            return singleWidget;
        }

        @Override
        public List<AbstractWidget> getSubWidgets() {
            return multipleWidget;
        }
    }

    public static class AppWithCombinedWidgets implements AbstractApp {

        private CombinedWidget singleWidget;

        private List<CombinedWidget> multipleWidgets;

        @Override
        public CombinedWidget getWidget() {
            return singleWidget;
        }

        @Override
        public List<CombinedWidget> getWidgets() {
            return multipleWidgets;
        }
    }

    public static class AppWithPartiallyCombinedWidgets implements AbstractApp {

        private PartiallyCombinedWidget singleWidget;

        private List<PartiallyCombinedWidget> multipleWidgets;

        @Override
        public PartiallyCombinedWidget getWidget() {
            return singleWidget;
        }

        @Override
        public List<PartiallyCombinedWidget> getWidgets() {
            return multipleWidgets;
        }
    }
}
