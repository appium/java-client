package io.appium.java_client.pagefactory_tests.widget.tests.ios;

import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory_tests.widget.tests.ExtendedApp;

import java.util.List;

public class IosApp implements ExtendedApp {

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

    /**
     * This class is annotated by {@link io.appium.java_client.pagefactory.iOSFindBy} and
     * {@link io.appium.java_client.pagefactory.iOSXCUITFindBy}.
     * This field was added to check that locator is created correctly according to current platform
     * and current automation.
     */
    private AnnotatedIosWidget singleAnnotatedIosWidget;

    /**
     * This class is annotated by {@link io.appium.java_client.pagefactory.iOSFindBy} and
     * {@link io.appium.java_client.pagefactory.iOSXCUITFindBy}.
     * This field was added to check that locator is created correctly according to current platform
     * and current automation.
     */
    private List<AnnotatedIosWidget> multipleIosIosWidgets;

    /**
     * This class is not annotated by {@link io.appium.java_client.pagefactory.iOSFindBy} and
     * {@link io.appium.java_client.pagefactory.iOSXCUITFindBy}.
     * But the superclass is annotated by these annotations. This field was added to check that locator is
     * created correctly according to current platform and current automation.
     */
    private ExtendedIosWidget singleExtendedIosWidget;

    /**
     * This class is not annotated by {@link io.appium.java_client.pagefactory.iOSFindBy} and
     * {@link io.appium.java_client.pagefactory.iOSXCUITFindBy}.
     * But the superclass is annotated by these annotations. This field was added to check that locator is
     * created correctly according to current platform and current automation.
     */
    private List<ExtendedIosWidget> multipleExtendedIosWidgets;

    /**
     * The superclass is annotated by {@link io.appium.java_client.pagefactory.iOSFindBy} and
     * {@link io.appium.java_client.pagefactory.iOSXCUITFindBy}.
     * However there is the field which is annotated by this annotations.
     * This field was added to check that locator is
     * created correctly according to current platform and current automation and
     * annotations that mark the field.
     */
    @iOSFindBy(uiAutomator = "IOS_EXTERNALLY_DEFINED_WIDGET_LOCATOR")
    @iOSXCUITFindBy(iOSNsPredicate = "SOME_XCUIT_EXTERNALLY_DEFINED_LOCATOR")
    private ExtendedIosWidget singleOverriddenIosWidget;

    /**
     * The superclass is annotated by {@link io.appium.java_client.pagefactory.iOSFindBy} and
     * {@link io.appium.java_client.pagefactory.iOSXCUITFindBy}.
     * However there is the field which is annotated by this annotations.
     * This field was added to check that locator is
     * created correctly according to current platform and current automation and
     * annotations that mark the field.
     */
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
