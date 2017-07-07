package io.appium.java_client.pagefactory_tests.widget.tests.android;

import static io.appium.java_client.MobileBy.AndroidUIAutomator;
import static io.appium.java_client.pagefactory_tests.widget.tests.android.AndroidApp.ANDROID_DEFAULT_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.android.AndroidApp.ANDROID_EXTERNALLY_DEFINED_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.android.AnnotatedAndroidWidget.ANDROID_ROOT_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.android.DefaultAndroidWidget.ANDROID_SUB_WIDGET_LOCATOR;

import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import io.appium.java_client.pagefactory_tests.widget.tests.WidgetTest;

public class AndroidWidgetTest extends WidgetTest {

    public AndroidWidgetTest() {
        super(new AndroidApp(), new AbstractStubWebDriver.StubAndroidDriver());
    }

    @Override
    public void checkACommonWidget() {
        defaultTest(app.getWidget(), app.getWidgets(),
                AndroidUIAutomator(ANDROID_DEFAULT_WIDGET_LOCATOR), AndroidUIAutomator(ANDROID_SUB_WIDGET_LOCATOR));
    }

    @Override
    public void checkAnAnnotatedWidget() {
        defaultTest(app.getAnnotatedWidget(), app.getAnnotatedWidgets(),
                AndroidUIAutomator(ANDROID_ROOT_WIDGET_LOCATOR), AndroidUIAutomator(ANDROID_SUB_WIDGET_LOCATOR));
    }

    @Override
    public void checkAnExtendedWidget() {
        defaultTest(app.getExtendedWidget(), app.getExtendedWidgets(),
                AndroidUIAutomator(ANDROID_ROOT_WIDGET_LOCATOR), AndroidUIAutomator(ANDROID_SUB_WIDGET_LOCATOR));
    }

    @Override
    public void checkTheLocatorOverridingOnAWidget() {
        defaultTest(app.getExtendedWidgetWithOverriddenLocators(), app.getExtendedWidgetsWithOverriddenLocators(),
                AndroidUIAutomator(ANDROID_EXTERNALLY_DEFINED_WIDGET_LOCATOR), AndroidUIAutomator(ANDROID_SUB_WIDGET_LOCATOR));
    }
}
