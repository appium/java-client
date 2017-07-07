package io.appium.java_client.pagefactory_tests.widget.tests.android;

import static io.appium.java_client.pagefactory_tests.widget.tests.android.AndroidApp.ANDROID_SELENDROID_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.android.AndroidApp.SELENDROID_EXTERNALLY_DEFINED_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.android.AnnotatedAndroidWidget.SELENDROID_ROOT_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.android.DefaultAndroidWidget.SELENDROID_SUB_WIDGET_LOCATOR;
import static org.openqa.selenium.By.linkText;

import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import io.appium.java_client.pagefactory_tests.widget.tests.WidgetTest;

public class SelendroidWidgetTest extends WidgetTest {
    public SelendroidWidgetTest() {
        super(new AndroidApp(), new AbstractStubWebDriver.StubSelendroidDriver());
    }

    @Override
    public void checkACommonWidget() {
        defaultTest(app.getWidget(), app.getWidgets(),
                linkText(ANDROID_SELENDROID_WIDGET_LOCATOR), linkText(SELENDROID_SUB_WIDGET_LOCATOR));
    }

    @Override
    public void checkAnAnnotatedWidget() {
        defaultTest(app.getAnnotatedWidget(), app.getAnnotatedWidgets(),
                linkText(SELENDROID_ROOT_WIDGET_LOCATOR), linkText(SELENDROID_SUB_WIDGET_LOCATOR));
    }

    @Override
    public void checkAnExtendedWidget() {
        defaultTest(app.getExtendedWidget(), app.getExtendedWidgets(),
                linkText(SELENDROID_ROOT_WIDGET_LOCATOR), linkText(SELENDROID_SUB_WIDGET_LOCATOR));
    }

    @Override
    public void checkTheLocatorOverridingOnAWidget() {
        defaultTest(app.getExtendedWidgetWithOverriddenLocators(), app.getExtendedWidgetsWithOverriddenLocators(),
                linkText(SELENDROID_EXTERNALLY_DEFINED_WIDGET_LOCATOR), linkText(SELENDROID_SUB_WIDGET_LOCATOR));
    }
}
