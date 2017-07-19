package io.appium.java_client.pagefactory_tests.widget.tests.ios;

import static io.appium.java_client.MobileBy.IosUIAutomation;
import static io.appium.java_client.pagefactory_tests.widget.tests.ios.AnnotatedIosWidget.IOS_ROOT_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.ios.DefaultIosWidget.IOS_SUB_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.ios.IosApp.IOS_DEFAULT_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.ios.IosApp.IOS_EXTERNALLY_DEFINED_WIDGET_LOCATOR;

import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import io.appium.java_client.pagefactory_tests.widget.tests.ExtendedApp;
import io.appium.java_client.pagefactory_tests.widget.tests.ExtendedWidgetTest;

public class IosWidgetTest extends ExtendedWidgetTest {

    public IosWidgetTest() {
        super(new IosApp(), new AbstractStubWebDriver.StubIOSDriver());
    }

    @Override
    public void checkThatWidgetsAreCreatedCorrectly() {
        checkThatLocatorsAreCreatedCorrectly(app.getWidget(), app.getWidgets(),
                IosUIAutomation(IOS_DEFAULT_WIDGET_LOCATOR), IosUIAutomation(IOS_SUB_WIDGET_LOCATOR));
    }

    @Override
    public void checkCaseWhenWidgetClassHasDeclaredLocatorAnnotation() {
        checkThatLocatorsAreCreatedCorrectly(((ExtendedApp) app).getAnnotatedWidget(),
                ((ExtendedApp) app).getAnnotatedWidgets(),
                IosUIAutomation(IOS_ROOT_WIDGET_LOCATOR), IosUIAutomation(IOS_SUB_WIDGET_LOCATOR));
    }

    @Override
    public void checkCaseWhenWidgetClassHasNoDeclaredAnnotationButItHasSuperclass() {
        checkThatLocatorsAreCreatedCorrectly(((ExtendedApp) app).getExtendedWidget(),
                ((ExtendedApp) app).getExtendedWidgets(),
                IosUIAutomation(IOS_ROOT_WIDGET_LOCATOR), IosUIAutomation(IOS_SUB_WIDGET_LOCATOR));
    }

    @Override
    public void checkCaseWhenBothWidgetFieldAndClassHaveDelaredAnnotations() {
        checkThatLocatorsAreCreatedCorrectly(((ExtendedApp) app).getExtendedWidgetWithOverriddenLocators(),
                ((ExtendedApp) app).getExtendedWidgetsWithOverriddenLocators(),
                IosUIAutomation(IOS_EXTERNALLY_DEFINED_WIDGET_LOCATOR), IosUIAutomation(IOS_SUB_WIDGET_LOCATOR));
    }
}
