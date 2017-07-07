package io.appium.java_client.pagefactory_tests.widget.tests.ios;

import static io.appium.java_client.MobileBy.IosUIAutomation;
import static io.appium.java_client.pagefactory_tests.widget.tests.ios.AnnotatedIosWidget.IOS_ROOT_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.ios.DefaultIosWidget.IOS_SUB_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.ios.IosApp.IOS_DEFAULT_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.ios.IosApp.IOS_EXTERNALLY_DEFINED_WIDGET_LOCATOR;

import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import io.appium.java_client.pagefactory_tests.widget.tests.WidgetTest;

public class IosWidgetTest extends WidgetTest {

    public IosWidgetTest() {
        super(new IosApp(), new AbstractStubWebDriver.StubIOSDriver());
    }

    @Override
    public void checkACommonWidget() {
        defaultTest(app.getWidget(), app.getWidgets(),
                IosUIAutomation(IOS_DEFAULT_WIDGET_LOCATOR), IosUIAutomation(IOS_SUB_WIDGET_LOCATOR));
    }

    @Override
    public void checkAnAnnotatedWidget() {
        defaultTest(app.getAnnotatedWidget(), app.getAnnotatedWidgets(),
                IosUIAutomation(IOS_ROOT_WIDGET_LOCATOR), IosUIAutomation(IOS_SUB_WIDGET_LOCATOR));
    }

    @Override
    public void checkAnExtendedWidget() {
        defaultTest(app.getExtendedWidget(), app.getExtendedWidgets(),
                IosUIAutomation(IOS_ROOT_WIDGET_LOCATOR), IosUIAutomation(IOS_SUB_WIDGET_LOCATOR));
    }

    @Override
    public void checkTheLocatorOverridingOnAWidget() {
        defaultTest(app.getExtendedWidgetWithOverriddenLocators(), app.getExtendedWidgetsWithOverriddenLocators(),
                IosUIAutomation(IOS_EXTERNALLY_DEFINED_WIDGET_LOCATOR), IosUIAutomation(IOS_SUB_WIDGET_LOCATOR));
    }
}
