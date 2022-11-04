package io.appium.java_client.pagefactory_tests.widget.tests.android;

import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import io.appium.java_client.pagefactory_tests.widget.tests.ExtendedApp;
import io.appium.java_client.pagefactory_tests.widget.tests.WidgetTest;
import org.junit.jupiter.api.Test;

import static io.appium.java_client.AppiumBy.androidUIAutomator;
import static io.appium.java_client.pagefactory_tests.widget.tests.android.AndroidApp.ANDROID_DEFAULT_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.android.AndroidApp.ANDROID_EXTERNALLY_DEFINED_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.android.AnnotatedAndroidWidget.ANDROID_ROOT_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.android.DefaultAndroidWidget.ANDROID_SUB_WIDGET_LOCATOR;

public class AndroidWidgetTest extends WidgetTest {

    public AndroidWidgetTest() {
        super(new AndroidApp(), new AbstractStubWebDriver.StubAndroidDriver());
    }

    @Test
    @Override
    public void checkThatWidgetsAreCreatedCorrectly() {
        checkThatLocatorsAreCreatedCorrectly(app.getWidget(), app.getWidgets(),
                androidUIAutomator(ANDROID_DEFAULT_WIDGET_LOCATOR), androidUIAutomator(ANDROID_SUB_WIDGET_LOCATOR));
    }

    @Test
    @Override
    public void checkCaseWhenWidgetClassHasDeclaredLocatorAnnotation() {
        checkThatLocatorsAreCreatedCorrectly(((ExtendedApp) app).getAnnotatedWidget(),
                ((ExtendedApp) app).getAnnotatedWidgets(),
                androidUIAutomator(ANDROID_ROOT_WIDGET_LOCATOR), androidUIAutomator(ANDROID_SUB_WIDGET_LOCATOR));
    }

    @Test
    @Override
    public void checkCaseWhenWidgetClassHasNoDeclaredAnnotationButItHasSuperclass() {
        checkThatLocatorsAreCreatedCorrectly(((ExtendedApp) app).getExtendedWidget(),
                ((ExtendedApp) app).getExtendedWidgets(),
                androidUIAutomator(ANDROID_ROOT_WIDGET_LOCATOR), androidUIAutomator(ANDROID_SUB_WIDGET_LOCATOR));
    }

    @Test
    @Override
    public void checkCaseWhenBothWidgetFieldAndClassHaveDeclaredAnnotations() {
        checkThatLocatorsAreCreatedCorrectly(((ExtendedApp) app).getExtendedWidgetWithOverriddenLocators(),
                ((ExtendedApp) app).getExtendedWidgetsWithOverriddenLocators(),
                androidUIAutomator(ANDROID_EXTERNALLY_DEFINED_WIDGET_LOCATOR),
                androidUIAutomator(ANDROID_SUB_WIDGET_LOCATOR));
    }
}
