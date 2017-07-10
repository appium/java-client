package io.appium.java_client.pagefactory_tests.widget.tests.combined;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import io.appium.java_client.pagefactory.Widget;
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

import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class CombinedAppTest extends WidgetTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return asList(new Object[][] {
                { new CombinedApp(), new AbstractStubWebDriver.StubAndroidDriver(), DefaultAndroidWidget.class},
                { new CombinedApp(), new AbstractStubWebDriver.StubSelendroidDriver(), DefaultSelendroidWidget.class},
                { new CombinedApp(), new AbstractStubWebDriver.StubIOSDriver(), DefaultIosWidget.class},
                { new CombinedApp(), new AbstractStubWebDriver.StubIOSXCUITDriver(), DefaultIosXCUITWidget.class},
                { new CombinedApp(), new AbstractStubWebDriver.StubWindowsDriver(), DefaultWindowsWidget.class},
                { new CombinedApp(), new AbstractStubWebDriver.StubBrowserDriver(), DefaultFindByWidget.class},
                { new CombinedApp(), new AbstractStubWebDriver.StubAndroidBrowserOrWebViewDriver(), DefaultFindByWidget.class}
        });
    }

    private final Class<?> widgetClass;

    public CombinedAppTest(AbstractApp app, WebDriver driver, Class<? extends AbstractWidget> widgetClass) {
        super(app, driver);
        this.widgetClass = widgetClass;
    }

    @Override
    public void checkACommonWidget() {
        assertThat("Excpected widget class was " + widgetClass.getName(),
                app.getWidget().getSelfReference().getClass(),
                equalTo(widgetClass));

        List<Class<?>> classes = app.getWidgets().stream().map(abstractWidget -> abstractWidget.getSelfReference().getClass())
                .collect(toList());
        assertThat(classes,
                contains(widgetClass, widgetClass));
    }
}
