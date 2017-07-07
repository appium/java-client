package io.appium.java_client.pagefactory_tests.widget.tests.ios;

import static io.appium.java_client.MobileBy.iOSNsPredicateString;
import static io.appium.java_client.pagefactory_tests.widget.tests.ios.AnnotatedIosWidget.XCUIT_ROOT_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.ios.DefaultIosWidget.XCUIT_SUB_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.ios.IosApp.IOS_XCUIT_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.ios.IosApp.XCUIT_EXTERNALLY_DEFINED_WIDGET_LOCATOR;

import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import io.appium.java_client.pagefactory_tests.widget.tests.WidgetTest;

public class XCUITWidgetTest extends WidgetTest {

    public XCUITWidgetTest() {
        super(new IosApp(), new AbstractStubWebDriver.StubIOSXCUITDriver());
    }

    @Override
    public void checkACommonWidget() {
        defaultTest(app.getWidget(), app.getWidgets(),
                iOSNsPredicateString(IOS_XCUIT_WIDGET_LOCATOR), iOSNsPredicateString(XCUIT_SUB_WIDGET_LOCATOR));
    }

    @Override
    public void checkAnAnnotatedWidget() {
        defaultTest(app.getAnnotatedWidget(), app.getAnnotatedWidgets(),
                iOSNsPredicateString(XCUIT_ROOT_WIDGET_LOCATOR), iOSNsPredicateString(XCUIT_SUB_WIDGET_LOCATOR));
    }

    @Override
    public void checkAnExtendedWidget() {
        defaultTest(app.getExtendedWidget(), app.getExtendedWidgets(),
                iOSNsPredicateString(XCUIT_ROOT_WIDGET_LOCATOR), iOSNsPredicateString(XCUIT_SUB_WIDGET_LOCATOR));
    }

    @Override
    public void checkTheLocatorOverridingOnAWidget() {
        defaultTest(app.getExtendedWidgetWithOverriddenLocators(), app.getExtendedWidgetsWithOverriddenLocators(),
                iOSNsPredicateString(XCUIT_EXTERNALLY_DEFINED_WIDGET_LOCATOR), iOSNsPredicateString(XCUIT_SUB_WIDGET_LOCATOR));
    }
}
