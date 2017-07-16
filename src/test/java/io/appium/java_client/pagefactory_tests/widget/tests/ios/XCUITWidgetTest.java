package io.appium.java_client.pagefactory_tests.widget.tests.ios;

import static io.appium.java_client.MobileBy.iOSNsPredicateString;
import static io.appium.java_client.pagefactory_tests.widget.tests.ios.AnnotatedIosWidget.XCUIT_ROOT_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.ios.DefaultIosWidget.XCUIT_SUB_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.ios.IosApp.IOS_XCUIT_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.ios.IosApp.XCUIT_EXTERNALLY_DEFINED_WIDGET_LOCATOR;

import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import io.appium.java_client.pagefactory_tests.widget.tests.ExtendedApp;
import io.appium.java_client.pagefactory_tests.widget.tests.ExtendedWidgetTest;

public class XCUITWidgetTest extends ExtendedWidgetTest {

    public XCUITWidgetTest() {
        super(new IosApp(), new AbstractStubWebDriver.StubIOSXCUITDriver());
    }

    @Override
    public void commonTestCase() {
        testLogigByDefault(app.getWidget(), app.getWidgets(),
                iOSNsPredicateString(IOS_XCUIT_WIDGET_LOCATOR), iOSNsPredicateString(XCUIT_SUB_WIDGET_LOCATOR));
    }

    @Override
    public void checkCaseWhenWidgetClassHasDeclaredLocatorAnnotation() {
        testLogigByDefault(((ExtendedApp) app).getAnnotatedWidget(), ((ExtendedApp) app).getAnnotatedWidgets(),
                iOSNsPredicateString(XCUIT_ROOT_WIDGET_LOCATOR), iOSNsPredicateString(XCUIT_SUB_WIDGET_LOCATOR));
    }

    @Override
    public void checkCaseWhenWidgetClassHasNoDeclaredAnnotationButItHasSuperclass() {
        testLogigByDefault(((ExtendedApp) app).getExtendedWidget(), ((ExtendedApp) app).getExtendedWidgets(),
                iOSNsPredicateString(XCUIT_ROOT_WIDGET_LOCATOR), iOSNsPredicateString(XCUIT_SUB_WIDGET_LOCATOR));
    }

    @Override
    public void checkCaseWhenBothWidgetFieldAndClassHaveDelaredAnnotations() {
        testLogigByDefault(((ExtendedApp) app).getExtendedWidgetWithOverriddenLocators(),
                ((ExtendedApp) app).getExtendedWidgetsWithOverriddenLocators(),
                iOSNsPredicateString(XCUIT_EXTERNALLY_DEFINED_WIDGET_LOCATOR),
                iOSNsPredicateString(XCUIT_SUB_WIDGET_LOCATOR));
    }
}
