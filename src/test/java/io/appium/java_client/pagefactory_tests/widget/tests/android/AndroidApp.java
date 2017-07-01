package io.appium.java_client.pagefactory_tests.widget.tests.android;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.SelendroidFindBy;
import io.appium.java_client.pagefactory_tests.widget.tests.AbstractApp;

import java.util.List;

public class AndroidApp extends AbstractApp{

    public static String ANDROID_DEFAULT_WIDGET_LOCATOR = "SOME_ANDROID_DEFAULT_LOCATOR";
    public static String ANDROID_SELENDROID_WIDGET_LOCATOR = "SOME_SELENDROID_DEFAULT_LOCATOR";

    public static String ANDROID_EXTERNALLY_DEFINED_WIDGET_LOCATOR = "SOME_ANDROID_EXTERNALLY_DEFINED_LOCATOR";
    public static String SELENDROID_EXTERNALLY_DEFINED_WIDGET_LOCATOR = "SOME_SELENDROID_EXTERNALLY_DEFINED_LOCATOR";

    @AndroidFindBy(uiAutomator = "SOME_ANDROID_DEFAULT_LOCATOR")
    @SelendroidFindBy(linkText = "SOME_SELENDROID_DEFAULT_LOCATOR")
    private DefaultAndroidWidget singleAndroidWidget;

    @AndroidFindBy(uiAutomator = "SOME_ANDROID_DEFAULT_LOCATOR")
    @SelendroidFindBy(linkText = "SOME_SELENDROID_DEFAULT_LOCATOR")
    private List<DefaultAndroidWidget> multipleAndroidWidgets;

    private AnnotatedAndroidWidget singleAnnotatedAndroidWidget;

    private List<AnnotatedAndroidWidget> multipleAnnotatedAndroidWidgets;

    private ExtendedAndroidWidget singleExtendedAndroidWidget;

    private List<ExtendedAndroidWidget> multipleExtendedAndroidWidgets;

    @AndroidFindBy(uiAutomator = "SOME_ANDROID_EXTERNALLY_DEFINED_LOCATOR")
    @SelendroidFindBy(linkText = "SOME_SELENDROID_EXTERNALLY_DEFINED_LOCATOR")
    private ExtendedAndroidWidget singleOverriddenAndroidWidget;

    @AndroidFindBy(uiAutomator = "SOME_ANDROID_EXTERNALLY_DEFINED_LOCATOR")
    @SelendroidFindBy(linkText = "SOME_SELENDROID_EXTERNALLY_DEFINED_LOCATOR")
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
