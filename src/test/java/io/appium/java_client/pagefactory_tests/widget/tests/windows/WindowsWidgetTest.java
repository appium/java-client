package io.appium.java_client.pagefactory_tests.widget.tests.windows;

import static io.appium.java_client.MobileBy.windowsAutomation;
import static io.appium.java_client.pagefactory_tests.widget.tests.windows.AnnotatedWindowsWidget.WINDOWS_ROOT_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.windows.DefaultWindowsWidget.WINDOWS_SUB_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.windows.WindowsApp.WINDOWS_DEFAULT_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.windows.WindowsApp.WINDOWS_EXTERNALLY_DEFINED_WIDGET_LOCATOR;

import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import io.appium.java_client.pagefactory_tests.widget.tests.ExtendedApp;
import io.appium.java_client.pagefactory_tests.widget.tests.ExtendedWidgetTest;
import org.junit.jupiter.api.Test;

public class WindowsWidgetTest extends ExtendedWidgetTest {

    public WindowsWidgetTest() {
        super(new WindowsApp(), new AbstractStubWebDriver.StubWindowsDriver());
    }

    @Test
    @Override
    public void checkThatWidgetsAreCreatedCorrectly() {
        checkThatLocatorsAreCreatedCorrectly(app.getWidget(), app.getWidgets(),
                windowsAutomation(WINDOWS_DEFAULT_WIDGET_LOCATOR), windowsAutomation(WINDOWS_SUB_WIDGET_LOCATOR));
    }

    @Test
    @Override
    public void checkCaseWhenWidgetClassHasDeclaredLocatorAnnotation() {
        checkThatLocatorsAreCreatedCorrectly(((ExtendedApp) app).getAnnotatedWidget(),
                ((ExtendedApp) app).getAnnotatedWidgets(),
                windowsAutomation(WINDOWS_ROOT_WIDGET_LOCATOR), windowsAutomation(WINDOWS_SUB_WIDGET_LOCATOR));
    }

    @Test
    @Override
    public void checkCaseWhenWidgetClassHasNoDeclaredAnnotationButItHasSuperclass() {
        checkThatLocatorsAreCreatedCorrectly(((ExtendedApp) app).getExtendedWidget(),
                ((ExtendedApp) app).getExtendedWidgets(),
                windowsAutomation(WINDOWS_ROOT_WIDGET_LOCATOR), windowsAutomation(WINDOWS_SUB_WIDGET_LOCATOR));
    }

    @Test
    @Override
    public void checkCaseWhenBothWidgetFieldAndClassHaveDeclaredAnnotations() {
        checkThatLocatorsAreCreatedCorrectly(((ExtendedApp) app).getExtendedWidgetWithOverriddenLocators(),
                ((ExtendedApp) app).getExtendedWidgetsWithOverriddenLocators(),
                windowsAutomation(WINDOWS_EXTERNALLY_DEFINED_WIDGET_LOCATOR),
                windowsAutomation(WINDOWS_SUB_WIDGET_LOCATOR));
    }
}
