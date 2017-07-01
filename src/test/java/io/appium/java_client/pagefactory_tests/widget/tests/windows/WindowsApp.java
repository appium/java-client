package io.appium.java_client.pagefactory_tests.widget.tests.windows;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory_tests.widget.tests.AbstractApp;

import java.util.List;

public class WindowsApp extends AbstractApp{

    public static String WINDOWS_DEFAULT_WIDGET_LOCATOR = "SOME_WINDOWS_DEFAULT_LOCATOR";

    public static String WINDOWS_EXTERNALLY_DEFINED_WIDGET_LOCATOR = "WINDOWS_EXTERNALLY_DEFINED_WIDGET_LOCATOR";

    @WindowsFindBy(windowsAutomation = "SOME_WINDOWS_DEFAULT_LOCATOR")
    @iOSXCUITFindBy(iOSNsPredicate = "SOME_XCUIT_DEFAULT_LOCATOR")
    private DefaultWindowsWidget singleIosWidget;

    @WindowsFindBy(windowsAutomation = "SOME_WINDOWS_DEFAULT_LOCATOR")
    @iOSXCUITFindBy(iOSNsPredicate = "SOME_XCUIT_DEFAULT_LOCATOR")
    private List<DefaultWindowsWidget> multipleIosWidgets;

    private AnnotatedWindowsWidget singleAnnotatedIosWidget;

    private List<AnnotatedWindowsWidget> multipleIosIosWidgets;

    private ExtendedWindowsWidget singleExtendedIosWidget;

    private List<ExtendedWindowsWidget> multipleExtendedIosWidgets;

    @WindowsFindBy(windowsAutomation = "WINDOWS_EXTERNALLY_DEFINED_WIDGET_LOCATOR")
    @iOSXCUITFindBy(iOSNsPredicate = "SOME_XCUIT_EXTERNALLY_DEFINED_LOCATOR")
    private ExtendedWindowsWidget singleOverriddenIosWidget;

    @WindowsFindBy(windowsAutomation = "WINDOWS_EXTERNALLY_DEFINED_WIDGET_LOCATOR")
    @iOSXCUITFindBy(iOSNsPredicate = "SOME_XCUIT_EXTERNALLY_DEFINED_LOCATOR")
    private List<ExtendedWindowsWidget> multipleOverriddenIosWidgets;

    @Override
    public DefaultWindowsWidget getWidget() {
        return singleIosWidget;
    }

    @Override
    public List<DefaultWindowsWidget> getWidgets() {
        return multipleIosWidgets;
    }

    @Override
    public DefaultWindowsWidget getAnnotatedWidget() {
        return singleAnnotatedIosWidget;
    }

    @Override
    public List<AnnotatedWindowsWidget> getAnnotatedWidgets() {
        return multipleIosIosWidgets;
    }

    @Override
    public DefaultWindowsWidget getExtendedWidget() {
        return singleExtendedIosWidget;
    }

    @Override
    public List<ExtendedWindowsWidget> getExtendedWidgets() {
        return multipleExtendedIosWidgets;
    }

    @Override
    public DefaultWindowsWidget getExtendedWidgetWithOverriddenLocators() {
        return singleOverriddenIosWidget;
    }

    @Override
    public List<ExtendedWindowsWidget> getExtendedWidgetsWithOverriddenLocators() {
        return multipleOverriddenIosWidgets;
    }
}
