package io.appium.java_client.pagefactory_tests.widget.tests.windows;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory_tests.widget.tests.ExtendedApp;

import java.util.List;

public class WindowsApp implements ExtendedApp {

    public static String WINDOWS_DEFAULT_WIDGET_LOCATOR = "SOME_WINDOWS_DEFAULT_LOCATOR";

    public static String WINDOWS_EXTERNALLY_DEFINED_WIDGET_LOCATOR = "WINDOWS_EXTERNALLY_DEFINED_WIDGET_LOCATOR";

    @WindowsFindBy(windowsAutomation = "SOME_WINDOWS_DEFAULT_LOCATOR")
    @iOSXCUITFindBy(iOSNsPredicate = "SOME_XCUIT_DEFAULT_LOCATOR")
    private DefaultWindowsWidget singleIosWidget;

    @WindowsFindBy(windowsAutomation = "SOME_WINDOWS_DEFAULT_LOCATOR")
    @iOSXCUITFindBy(iOSNsPredicate = "SOME_XCUIT_DEFAULT_LOCATOR")
    private List<DefaultWindowsWidget> multipleIosWidgets;

    /**
     * This class is annotated by {@link WindowsFindBy} and
     * {@link io.appium.java_client.pagefactory.iOSXCUITFindBy}.
     * This field was added to check that locator is created correctly according to current platform.
     * It is expected that the root element and sub-elements are found using
     * {@link io.appium.java_client.MobileBy#windowsAutomation(String)}
     */
    private AnnotatedWindowsWidget singleAnnotatedIosWidget;

    /**
     * This class is annotated by {@link WindowsFindBy} and
     * {@link io.appium.java_client.pagefactory.iOSXCUITFindBy}.
     * This field was added to check that locator is created correctly according to current platform.
     * It is expected that the root element and sub-elements are found using
     * {@link io.appium.java_client.MobileBy#windowsAutomation(String)}.
     */
    private List<AnnotatedWindowsWidget> multipleIosIosWidgets;

    /**
     * This class is not annotated by {@link WindowsFindBy} and
     * {@link io.appium.java_client.pagefactory.iOSXCUITFindBy}.
     * But the superclass is annotated by these annotations. This field was added to check that locator is
     * created correctly according to current platform.
     * It is expected that the root element and sub-elements are found using
     * {@link io.appium.java_client.MobileBy#windowsAutomation(String)}.
     */
    private ExtendedWindowsWidget singleExtendedIosWidget;

    /**
     * This class is not annotated by {@link WindowsFindBy} and
     * {@link io.appium.java_client.pagefactory.iOSXCUITFindBy}.
     * But the superclass is annotated by these annotations. This field was added to check that locator is
     * created correctly according to current platform.
     * It is expected that the root element and sub-elements are found using
     * {@link io.appium.java_client.MobileBy#windowsAutomation(String)}.
     */
    private List<ExtendedWindowsWidget> multipleExtendedIosWidgets;

    /**
     * This class is not annotated by {@link WindowsFindBy} and
     * {@link io.appium.java_client.pagefactory.iOSXCUITFindBy}.
     * But the superclass is annotated by these annotations. This field was added to check that locator is
     * created correctly according to current platform.
     * It is expected that the root element and sub-elements are found using
     * {@link io.appium.java_client.MobileBy#windowsAutomation(String)}.
     */
    @WindowsFindBy(windowsAutomation = "WINDOWS_EXTERNALLY_DEFINED_WIDGET_LOCATOR")
    @iOSXCUITFindBy(iOSNsPredicate = "SOME_XCUIT_EXTERNALLY_DEFINED_LOCATOR")
    private ExtendedWindowsWidget singleOverriddenIosWidget;

    /**
     * This class is not annotated by {@link WindowsFindBy} and
     * {@link io.appium.java_client.pagefactory.iOSXCUITFindBy}.
     * But the superclass is annotated by these annotations. This field was added to check that locator is
     * created correctly according to current platform.
     * It is expected that the root element and sub-elements are found using
     * {@link io.appium.java_client.MobileBy#windowsAutomation(String)}.
     */
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
