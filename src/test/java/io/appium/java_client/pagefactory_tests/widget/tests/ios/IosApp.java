package io.appium.java_client.pagefactory_tests.widget.tests.ios;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.SelendroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory_tests.widget.tests.AbstractApp;

import java.util.List;

public class IosApp extends AbstractApp{

    public static String IOS_DEFAULT_WIDGET_LOCATOR = "SOME_IOS_DEFAULT_LOCATOR";
    public static String IOS_XCUIT_WIDGET_LOCATOR = "SOME_XCUIT_DEFAULT_LOCATOR";

    public static String IOS_EXTERNALLY_DEFINED_WIDGET_LOCATOR = "IOS_EXTERNALLY_DEFINED_WIDGET_LOCATOR";
    public static String XCUIT_EXTERNALLY_DEFINED_WIDGET_LOCATOR = "SOME_XCUIT_EXTERNALLY_DEFINED_LOCATOR";

    @iOSFindBy(uiAutomator = "SOME_IOS_DEFAULT_LOCATOR")
    @iOSXCUITFindBy(iOSNsPredicate = "SOME_XCUIT_DEFAULT_LOCATOR")
    private DefaultIosWidget singleIosWidget;

    @iOSFindBy(uiAutomator = "SOME_IOS_DEFAULT_LOCATOR")
    @iOSXCUITFindBy(iOSNsPredicate = "SOME_XCUIT_DEFAULT_LOCATOR")
    private List<DefaultIosWidget> multipleIosWidgets;

    private AnnotatedIosWidget singleAnnotatedIosWidget;

    private List<AnnotatedIosWidget> multipleIosIosWidgets;

    private ExtendedIosWidget singleExtendedIosWidget;

    private List<ExtendedIosWidget> multipleExtendedIosWidgets;

    @iOSFindBy(uiAutomator = "IOS_EXTERNALLY_DEFINED_WIDGET_LOCATOR")
    @iOSXCUITFindBy(iOSNsPredicate = "SOME_XCUIT_EXTERNALLY_DEFINED_LOCATOR")
    private ExtendedIosWidget singleOverriddenIosWidget;

    @iOSFindBy(uiAutomator = "IOS_EXTERNALLY_DEFINED_WIDGET_LOCATOR")
    @iOSXCUITFindBy(iOSNsPredicate = "SOME_XCUIT_EXTERNALLY_DEFINED_LOCATOR")
    private List<ExtendedIosWidget> multipleOverriddenIosWidgets;

    @Override
    public DefaultIosWidget getWidget() {
        return singleIosWidget;
    }

    @Override
    public List<DefaultIosWidget> getWidgets() {
        return multipleIosWidgets;
    }

    @Override
    public DefaultIosWidget getAnnotatedWidget() {
        return singleAnnotatedIosWidget;
    }

    @Override
    public List<AnnotatedIosWidget> getAnnotatedWidgets() {
        return multipleIosIosWidgets;
    }

    @Override
    public DefaultIosWidget getExtendedWidget() {
        return singleExtendedIosWidget;
    }

    @Override
    public List<ExtendedIosWidget> getExtendedWidgets() {
        return multipleExtendedIosWidgets;
    }

    @Override
    public DefaultIosWidget getExtendedWidgetWithOverriddenLocators() {
        return singleOverriddenIosWidget;
    }

    @Override
    public List<ExtendedIosWidget> getExtendedWidgetsWithOverriddenLocators() {
        return multipleOverriddenIosWidgets;
    }
}
