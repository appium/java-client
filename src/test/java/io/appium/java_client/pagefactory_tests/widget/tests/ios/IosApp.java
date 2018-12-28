package io.appium.java_client.pagefactory_tests.widget.tests.ios;

import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory_tests.widget.tests.ExtendedApp;
import io.appium.java_client.pagefactory_tests.widget.tests.combined.DefaultIosXCUITWidget;

import java.util.List;

public class IosApp implements ExtendedApp {

    public static String IOS_DEFAULT_WIDGET_LOCATOR = "SOME_IOS_DEFAULT_LOCATOR";
    public static String IOS_XCUIT_WIDGET_LOCATOR = "SOME_XCUIT_DEFAULT_LOCATOR";

    public static String IOS_EXTERNALLY_DEFINED_WIDGET_LOCATOR = "IOS_EXTERNALLY_DEFINED_WIDGET_LOCATOR";
    public static String XCUIT_EXTERNALLY_DEFINED_WIDGET_LOCATOR = "SOME_XCUIT_EXTERNALLY_DEFINED_LOCATOR";

    @iOSXCUITFindBy(iOSNsPredicate = "SOME_XCUIT_DEFAULT_LOCATOR")
    private DefaultIosXCUITWidget singleIosWidget;

    @iOSXCUITFindBy(iOSNsPredicate = "SOME_XCUIT_DEFAULT_LOCATOR")
    private List<DefaultIosXCUITWidget> multipleIosWidgets;

    /**
     * This class is annotated by {@link io.appium.java_client.pagefactory.iOSXCUITFindBy}.
     * This field was added to check that locator is created correctly according to current platform
     * and current automation.
     */
    private AnnotatedIosWidget singleAnnotatedIosWidget;

    /**
     * This class is annotated by {@link io.appium.java_client.pagefactory.iOSXCUITFindBy}.
     * This field was added to check that locator is created correctly according to current platform
     * and current automation.
     */
    private List<AnnotatedIosWidget> multipleIosIosWidgets;

    /**
     * This class is not annotated by {@link io.appium.java_client.pagefactory.iOSXCUITFindBy}.
     * But the superclass is annotated by these annotations. This field was added to check that locator is
     * created correctly according to current platform and current automation.
     */
    private ExtendedIosWidget singleExtendedIosWidget;

    /**
     * This class is not annotated by {@link io.appium.java_client.pagefactory.iOSXCUITFindBy}.
     * But the superclass is annotated by these annotations. This field was added to check that locator is
     * created correctly according to current platform and current automation.
     */
    private List<ExtendedIosWidget> multipleExtendedIosWidgets;

    /**
     * The superclass is annotated by {@link io.appium.java_client.pagefactory.iOSXCUITFindBy}.
     * However there is the field which is annotated by this annotations.
     * This field was added to check that locator is
     * created correctly according to current platform and current automation and
     * annotations that mark the field.
     */
    @iOSXCUITFindBy(iOSNsPredicate = "SOME_XCUIT_EXTERNALLY_DEFINED_LOCATOR")
    private ExtendedIosWidget singleOverriddenIosWidget;

    /**
     * The superclass is annotated by {@link io.appium.java_client.pagefactory.iOSXCUITFindBy}.
     * However there is the field which is annotated by this annotations.
     * This field was added to check that locator is
     * created correctly according to current platform and current automation and
     * annotations that mark the field.
     */
    @iOSXCUITFindBy(iOSNsPredicate = "SOME_XCUIT_EXTERNALLY_DEFINED_LOCATOR")
    private List<ExtendedIosWidget> multipleOverriddenIosWidgets;

    @Override
    public DefaultIosXCUITWidget getWidget() {
        return singleIosWidget;
    }

    @Override
    public List<DefaultIosXCUITWidget> getWidgets() {
        return multipleIosWidgets;
    }

    @Override
    public DefaultIosXCUITWidget getAnnotatedWidget() {
        return singleAnnotatedIosWidget;
    }

    @Override
    public List<AnnotatedIosWidget> getAnnotatedWidgets() {
        return multipleIosIosWidgets;
    }

    @Override
    public DefaultIosXCUITWidget getExtendedWidget() {
        return singleExtendedIosWidget;
    }

    @Override
    public List<ExtendedIosWidget> getExtendedWidgets() {
        return multipleExtendedIosWidgets;
    }

    @Override
    public DefaultIosXCUITWidget getExtendedWidgetWithOverriddenLocators() {
        return singleOverriddenIosWidget;
    }

    @Override
    public List<ExtendedIosWidget> getExtendedWidgetsWithOverriddenLocators() {
        return multipleOverriddenIosWidgets;
    }
}
