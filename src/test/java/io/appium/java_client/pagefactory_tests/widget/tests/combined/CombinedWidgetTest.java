package io.appium.java_client.pagefactory_tests.widget.tests.combined;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.OverrideWidget;
import io.appium.java_client.pagefactory_tests.widget.tests.AbstractApp;
import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import io.appium.java_client.pagefactory_tests.widget.tests.DefaultStubWidget;
import io.appium.java_client.pagefactory_tests.widget.tests.android.DefaultAndroidWidget;
import io.appium.java_client.proxy.Helpers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.openqa.selenium.support.PageFactory.initElements;


@SuppressWarnings({"unchecked", "unused"})
public class CombinedWidgetTest {

    /**
     * Test data generation.
     *
     * @return test parameters
     */
    public static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of(new AppWithCombinedWidgets(),
                    new AbstractStubWebDriver.StubAndroidDriver(), DefaultAndroidWidget.class),
                Arguments.of(new AppWithCombinedWidgets(),
                    new AbstractStubWebDriver.StubIOSXCUITDriver(), DefaultIosXCUITWidget.class),
                Arguments.of(new AppWithCombinedWidgets(),
                    new AbstractStubWebDriver.StubBrowserDriver(), DefaultFindByWidget.class),
                Arguments.of(new AppWithCombinedWidgets(),
                    new AbstractStubWebDriver.StubAndroidBrowserOrWebViewDriver(), DefaultFindByWidget.class),
                Arguments.of(new AppWithPartiallyCombinedWidgets(),
                    new AbstractStubWebDriver.StubAndroidDriver(), DefaultAndroidWidget.class),
                Arguments.of(new AppWithPartiallyCombinedWidgets(),
                    new AbstractStubWebDriver.StubIOSXCUITDriver(), DefaultStubWidget.class),
                Arguments.of(new AppWithPartiallyCombinedWidgets(),
                    new AbstractStubWebDriver.StubWindowsDriver(), DefaultStubWidget.class),
                Arguments.of(new AppWithPartiallyCombinedWidgets(),
                    new AbstractStubWebDriver.StubBrowserDriver(), DefaultFindByWidget.class),
                Arguments.of(new AppWithPartiallyCombinedWidgets(),
                    new AbstractStubWebDriver.StubAndroidBrowserOrWebViewDriver(), DefaultFindByWidget.class)
        );
    }

    @ParameterizedTest
    @MethodSource("data")
    void checkThatWidgetsAreCreatedCorrectly(AbstractApp app, WebDriver driver, Class<?> widgetClass) {
        assertProxyClassCacheGrowth();
        initElements(new AppiumFieldDecorator(driver), app);
        assertThat("Expected widget class was " + widgetClass.getName(),
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
                iOSXCUITAutomation = DefaultIosXCUITWidget.class
        )
        private DefaultStubWidget singleWidget;

        @OverrideWidget(html = DefaultFindByWidget.class,
                androidUIAutomator = DefaultAndroidWidget.class,
                iOSXCUITAutomation = DefaultIosXCUITWidget.class
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


    /**
     * Assert proxy class cache growth for this test class.
     * The (@link io.appium.java_client.proxy.Helpers#CACHED_PROXY_CLASSES) should be populated during these tests.
     * Prior to the Caching issue being resolved
     * - the CACHED_PROXY_CLASSES would grow indefinitely, resulting in an Out Of Memory exception.
     * - this ParameterizedTest would have the CACHED_PROXY_CLASSES grow to 266 entries.
     */
    private void assertProxyClassCacheGrowth() {
        System.gc(); //Trying to force a collection for more accurate check numbers
        int thresholdSize = 50;
        assertThat(
            "Proxy Class Cache threshold is " + thresholdSize,
            Helpers.getCachedProxyClassesSize(),
            lessThan(thresholdSize)
        );
    }
}
