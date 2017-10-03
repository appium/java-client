package io.appium.java_client.pagefactory_tests.widget.tests.android;

import static io.appium.java_client.pagefactory_tests.widget.tests.android.AndroidApp.ANDROID_SELENDROID_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.android.AndroidApp.SELENDROID_EXTERNALLY_DEFINED_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.android.AnnotatedAndroidWidget.SELENDROID_ROOT_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.android.DefaultAndroidWidget.SELENDROID_SUB_WIDGET_LOCATOR;
import static org.openqa.selenium.By.linkText;

import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import io.appium.java_client.pagefactory_tests.widget.tests.ExtendedApp;
import io.appium.java_client.pagefactory_tests.widget.tests.ExtendedWidgetTest;

public class SelendroidWidgetTest extends ExtendedWidgetTest {
    public SelendroidWidgetTest() {
        super(new AndroidApp(), new AbstractStubWebDriver.StubSelendroidDriver());
    }

    @Override
    public void checkThatWidgetsAreCreatedCorrectly() {
        checkThatLocatorsAreCreatedCorrectly(app.getWidget(), app.getWidgets(),
                linkText(ANDROID_SELENDROID_WIDGET_LOCATOR), linkText(SELENDROID_SUB_WIDGET_LOCATOR));
    }

    @Override
    public void checkCaseWhenWidgetClassHasDeclaredLocatorAnnotation() {
        checkThatLocatorsAreCreatedCorrectly(((ExtendedApp) app).getAnnotatedWidget(),
                ((ExtendedApp) app).getAnnotatedWidgets(),
                linkText(SELENDROID_ROOT_WIDGET_LOCATOR), linkText(SELENDROID_SUB_WIDGET_LOCATOR));
    }

    @Override
    public void checkCaseWhenWidgetClassHasNoDeclaredAnnotationButItHasSuperclass() {
        checkThatLocatorsAreCreatedCorrectly(((ExtendedApp) app).getExtendedWidget(),
                ((ExtendedApp) app).getExtendedWidgets(),
                linkText(SELENDROID_ROOT_WIDGET_LOCATOR), linkText(SELENDROID_SUB_WIDGET_LOCATOR));
    }

    @Override
    public void checkCaseWhenBothWidgetFieldAndClassHaveDelaredAnnotations() {
        checkThatLocatorsAreCreatedCorrectly(((ExtendedApp) app).getExtendedWidgetWithOverriddenLocators(),
                ((ExtendedApp) app).getExtendedWidgetsWithOverriddenLocators(),
                linkText(SELENDROID_EXTERNALLY_DEFINED_WIDGET_LOCATOR), linkText(SELENDROID_SUB_WIDGET_LOCATOR));
    }
}
