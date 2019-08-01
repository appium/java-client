package io.appium.java_client.pagefactory_tests.widget.tests.android;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory_tests.widget.tests.ExtendedApp;

import java.util.List;

public class AndroidApp implements ExtendedApp {

    public static String ANDROID_DEFAULT_WIDGET_LOCATOR = "SOME_ANDROID_DEFAULT_LOCATOR";

    public static String ANDROID_EXTERNALLY_DEFINED_WIDGET_LOCATOR = "SOME_ANDROID_EXTERNALLY_DEFINED_LOCATOR";

    @AndroidFindBy(uiAutomator = "SOME_ANDROID_DEFAULT_LOCATOR")
    private DefaultAndroidWidget singleAndroidWidget;

    @AndroidFindBy(uiAutomator = "SOME_ANDROID_DEFAULT_LOCATOR")
    private List<DefaultAndroidWidget> multipleAndroidWidgets;

    /**
     * This class is annotated by {@link AndroidFindBy}
     * This field was added to check that locator is created correctly according to current platform
     * and current automation.
     */
    private AnnotatedAndroidWidget singleAnnotatedAndroidWidget;

    /**
     * This class is annotated by {@link AndroidFindBy}
     * This field was added to check that locator is created correctly according to current platform
     * and current automation.
     */
    private List<AnnotatedAndroidWidget> multipleAnnotatedAndroidWidgets;

    /**
     * This class is not annotated by {@link AndroidFindBy}
     * But the superclass is annotated by these annotations. This field was added to check that locator is
     * created correctly according to current platform and current automation.
     */
    private ExtendedAndroidWidget singleExtendedAndroidWidget;

    /**
     * This class is not annotated by {@link AndroidFindBy}
     * But the superclass is annotated by these annotations. This field was added to check that locator is
     * created correctly according to current platform and current automation.
     */
    private List<ExtendedAndroidWidget> multipleExtendedAndroidWidgets;

    /**
     * The superclass is annotated by {@link AndroidFindBy}
     * However there is the field which is annotated by this annotations.
     * This field was added to check that locator is
     * created correctly according to current platform and current automation and
     * annotations that mark the field.
     */
    @AndroidFindBy(uiAutomator = "SOME_ANDROID_EXTERNALLY_DEFINED_LOCATOR")
    private ExtendedAndroidWidget singleOverriddenAndroidWidget;

    /**
     * The superclass is annotated by {@link AndroidFindBy}
     * However there is the field which is annotated by this annotations.
     * This field was added to check that locator is
     * created correctly according to current platform and current automation and
     * annotations that mark the field.
     */
    @AndroidFindBy(uiAutomator = "SOME_ANDROID_EXTERNALLY_DEFINED_LOCATOR")
    private List<ExtendedAndroidWidget> multipleOverriddenAndroidWidgets;

    @Override
    public DefaultAndroidWidget getWidget() {
        return singleAndroidWidget;
    }

    @Override
    public List<DefaultAndroidWidget> getWidgets() {
        return multipleAndroidWidgets;
    }

    @Override
    public DefaultAndroidWidget getAnnotatedWidget() {
        return singleAnnotatedAndroidWidget;
    }

    @Override
    public List<AnnotatedAndroidWidget> getAnnotatedWidgets() {
        return multipleAnnotatedAndroidWidgets;
    }

    @Override
    public DefaultAndroidWidget getExtendedWidget() {
        return singleExtendedAndroidWidget;
    }

    @Override
    public List<ExtendedAndroidWidget> getExtendedWidgets() {
        return multipleExtendedAndroidWidgets;
    }

    @Override
    public DefaultAndroidWidget getExtendedWidgetWithOverriddenLocators() {
        return singleOverriddenAndroidWidget;
    }

    @Override
    public List<ExtendedAndroidWidget> getExtendedWidgetsWithOverriddenLocators() {
        return multipleOverriddenAndroidWidgets;
    }
}
