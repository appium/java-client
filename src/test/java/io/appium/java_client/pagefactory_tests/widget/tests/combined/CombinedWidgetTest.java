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
import io.appium.java_client.pagefactory_tests.widget.tests.windows.DefaultWindowsWidget;
import io.appium.java_client.selenium.WebDriver;
import io.appium.java_client.selenium.WebElement;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@RunWith(Parameterized.class)
public class CombinedWidgetTest extends WidgetTest {

    private final Class<?> widgetClass;

    /**
     * Test data generation.
     *
     * @return test parameters
     */
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return asList(
                dataArray(new AppWithCombinedWidgets(),
                    new AbstractStubWebDriver.StubAndroidDriver(), DefaultAndroidWidget.class),
                dataArray(new AppWithCombinedWidgets(),
                    new AbstractStubWebDriver.StubIOSXCUITDriver(), DefaultIosXCUITWidget.class),
                dataArray(new AppWithCombinedWidgets(),
                    new AbstractStubWebDriver.StubWindowsDriver(), DefaultWindowsWidget.class),
                dataArray(new AppWithCombinedWidgets(),
                    new AbstractStubWebDriver.StubBrowserDriver(), DefaultFindByWidget.class),
                dataArray(new AppWithCombinedWidgets(),
                    new AbstractStubWebDriver.StubAndroidBrowserOrWebViewDriver(), DefaultFindByWidget.class),
                dataArray(new AppWithPartiallyCombinedWidgets(),
                    new AbstractStubWebDriver.StubAndroidDriver(), DefaultAndroidWidget.class),
                dataArray(new AppWithPartiallyCombinedWidgets(),
                    new AbstractStubWebDriver.StubIOSDriver(), DefaultStubWidget.class),
                dataArray(new AppWithPartiallyCombinedWidgets(),
                    new AbstractStubWebDriver.StubIOSXCUITDriver(), DefaultStubWidget.class),
                dataArray(new AppWithPartiallyCombinedWidgets(),
                    new AbstractStubWebDriver.StubWindowsDriver(), DefaultStubWidget.class),
                dataArray(new AppWithPartiallyCombinedWidgets(),
                    new AbstractStubWebDriver.StubBrowserDriver(), DefaultFindByWidget.class),
                dataArray(new AppWithPartiallyCombinedWidgets(),
                    new AbstractStubWebDriver.StubAndroidBrowserOrWebViewDriver(), DefaultFindByWidget.class)
        );
    }

    public CombinedWidgetTest(AbstractApp app, WebDriver driver, Class<?> widgetClass) {
        super(app, driver);
        this.widgetClass = widgetClass;
    }

    @Override
    public void checkThatWidgetsAreCreatedCorrectly() {
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

    public static class CombinedWidget extends DefaultStubWidget {

        @OverrideWidget(html = DefaultFindByWidget.class,
                androidUIAutomator = DefaultAndroidWidget.class,
                iOSXCUITAutomation = DefaultIosXCUITWidget.class,
                windowsAutomation = DefaultWindowsWidget.class
        )
        private DefaultStubWidget singleWidget;

        @OverrideWidget(html = DefaultFindByWidget.class,
                androidUIAutomator = DefaultAndroidWidget.class,
                iOSXCUITAutomation = DefaultIosXCUITWidget.class,
                windowsAutomation = DefaultWindowsWidget.class
        )
        private List<DefaultStubWidget> multipleWidget;


        protected CombinedWidget(WebElement element) {
            super(element);
        }

        @Override
        public DefaultStubWidget getSubWidget() {
            return singleWidget;
        }

        @Override
        public List<DefaultStubWidget> getSubWidgets() {
            return multipleWidget;
        }
    }

    public static class PartiallyCombinedWidget extends DefaultStubWidget {

        @OverrideWidget(html = DefaultFindByWidget.class,
                androidUIAutomator = DefaultAndroidWidget.class)
        private DefaultStubWidget singleWidget;

        @OverrideWidget(html = DefaultFindByWidget.class,
                androidUIAutomator = DefaultAndroidWidget.class)
        private List<DefaultStubWidget> multipleWidget;


        protected PartiallyCombinedWidget(WebElement element) {
            super(element);
        }

        @Override
        public DefaultStubWidget getSubWidget() {
            return singleWidget;
        }

        @Override
        public List<DefaultStubWidget> getSubWidgets() {
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
