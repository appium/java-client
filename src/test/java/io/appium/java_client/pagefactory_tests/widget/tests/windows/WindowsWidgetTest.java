package io.appium.java_client.pagefactory_tests.widget.tests.windows;

import static io.appium.java_client.MobileBy.windowsAutomation;
import static io.appium.java_client.pagefactory_tests.widget.tests.windows.AnnotatedWindowsWidget.WINDOWS_ROOT_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.windows.DefaultWindowsWidget.WINDOWS_SUB_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.windows.WindowsApp.WINDOWS_DEFAULT_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.windows.WindowsApp.WINDOWS_EXTERNALLY_DEFINED_WIDGET_LOCATOR;

import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import io.appium.java_client.pagefactory_tests.widget.tests.ExtendedApp;
import io.appium.java_client.pagefactory_tests.widget.tests.ExtendedWidgetTest;

public class WindowsWidgetTest extends ExtendedWidgetTest {

    public WindowsWidgetTest() {
        super(new WindowsApp(), new AbstractStubWebDriver.StubWindowsDriver());
    }

    @Override
    public void checkACommonWidget() {
        defaultTest(app.getWidget(), app.getWidgets(),
                windowsAutomation(WINDOWS_DEFAULT_WIDGET_LOCATOR), windowsAutomation(WINDOWS_SUB_WIDGET_LOCATOR));
    }

    @Override
    public void checkAnAnnotatedWidget() {
        defaultTest(((ExtendedApp) app).getAnnotatedWidget(), ((ExtendedApp) app).getAnnotatedWidgets(),
                windowsAutomation(WINDOWS_ROOT_WIDGET_LOCATOR), windowsAutomation(WINDOWS_SUB_WIDGET_LOCATOR));
    }

    @Override
    public void checkAnExtendedWidget() {
        defaultTest(((ExtendedApp) app).getExtendedWidget(), ((ExtendedApp) app).getExtendedWidgets(),
                windowsAutomation(WINDOWS_ROOT_WIDGET_LOCATOR), windowsAutomation(WINDOWS_SUB_WIDGET_LOCATOR));
    }

    @Override
    public void checkTheLocatorOverridingOnAWidget() {
        defaultTest(((ExtendedApp) app).getExtendedWidgetWithOverriddenLocators(),
                ((ExtendedApp) app).getExtendedWidgetsWithOverriddenLocators(),
                windowsAutomation(WINDOWS_EXTERNALLY_DEFINED_WIDGET_LOCATOR),
                windowsAutomation(WINDOWS_SUB_WIDGET_LOCATOR));
    }
}
