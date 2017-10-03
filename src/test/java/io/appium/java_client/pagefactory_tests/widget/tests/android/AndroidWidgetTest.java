package io.appium.java_client.pagefactory_tests.widget.tests.android;

import static io.appium.java_client.MobileBy.AndroidUIAutomator;
import static io.appium.java_client.pagefactory_tests.widget.tests.android.AndroidApp.ANDROID_DEFAULT_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.android.AndroidApp.ANDROID_EXTERNALLY_DEFINED_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.android.AnnotatedAndroidWidget.ANDROID_ROOT_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.android.DefaultAndroidWidget.ANDROID_SUB_WIDGET_LOCATOR;

import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import io.appium.java_client.pagefactory_tests.widget.tests.ExtendedApp;
import io.appium.java_client.pagefactory_tests.widget.tests.ExtendedWidgetTest;

public class AndroidWidgetTest extends ExtendedWidgetTest {

    public AndroidWidgetTest() {
        super(new AndroidApp(), new AbstractStubWebDriver.StubAndroidDriver());
    }

    @Override
    public void checkThatWidgetsAreCreatedCorrectly() {
        checkThatLocatorsAreCreatedCorrectly(app.getWidget(), app.getWidgets(),
                AndroidUIAutomator(ANDROID_DEFAULT_WIDGET_LOCATOR), AndroidUIAutomator(ANDROID_SUB_WIDGET_LOCATOR));
    }

    @Override
    public void checkCaseWhenWidgetClassHasDeclaredLocatorAnnotation() {
        checkThatLocatorsAreCreatedCorrectly(((ExtendedApp) app).getAnnotatedWidget(),
                ((ExtendedApp) app).getAnnotatedWidgets(),
                AndroidUIAutomator(ANDROID_ROOT_WIDGET_LOCATOR), AndroidUIAutomator(ANDROID_SUB_WIDGET_LOCATOR));
    }

    @Override
    public void checkCaseWhenWidgetClassHasNoDeclaredAnnotationButItHasSuperclass() {
        checkThatLocatorsAreCreatedCorrectly(((ExtendedApp) app).getExtendedWidget(),
                ((ExtendedApp) app).getExtendedWidgets(),
                AndroidUIAutomator(ANDROID_ROOT_WIDGET_LOCATOR), AndroidUIAutomator(ANDROID_SUB_WIDGET_LOCATOR));
    }

    @Override
    public void checkCaseWhenBothWidgetFieldAndClassHaveDelaredAnnotations() {
        checkThatLocatorsAreCreatedCorrectly(((ExtendedApp) app).getExtendedWidgetWithOverriddenLocators(),
                ((ExtendedApp) app).getExtendedWidgetsWithOverriddenLocators(),
                AndroidUIAutomator(ANDROID_EXTERNALLY_DEFINED_WIDGET_LOCATOR),
                AndroidUIAutomator(ANDROID_SUB_WIDGET_LOCATOR));
    }
}
