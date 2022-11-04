package io.appium.java_client.pagefactory_tests.widget.tests.ios;

import io.appium.java_client.pagefactory_tests.widget.tests.AbstractStubWebDriver;
import io.appium.java_client.pagefactory_tests.widget.tests.ExtendedApp;
import io.appium.java_client.pagefactory_tests.widget.tests.WidgetTest;
import org.junit.jupiter.api.Test;

import static io.appium.java_client.AppiumBy.iOSNsPredicateString;
import static io.appium.java_client.pagefactory_tests.widget.tests.combined.DefaultIosXCUITWidget.XCUIT_SUB_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.ios.AnnotatedIosWidget.XCUIT_ROOT_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.ios.IosApp.IOS_XCUIT_WIDGET_LOCATOR;
import static io.appium.java_client.pagefactory_tests.widget.tests.ios.IosApp.XCUIT_EXTERNALLY_DEFINED_WIDGET_LOCATOR;

public class XCUITWidgetTest extends WidgetTest {

    public XCUITWidgetTest() {
        super(new IosApp(), new AbstractStubWebDriver.StubIOSXCUITDriver());
    }

    @Test
    @Override
    public void checkThatWidgetsAreCreatedCorrectly() {
        checkThatLocatorsAreCreatedCorrectly(app.getWidget(), app.getWidgets(),
                iOSNsPredicateString(IOS_XCUIT_WIDGET_LOCATOR), iOSNsPredicateString(XCUIT_SUB_WIDGET_LOCATOR));
    }

    @Test
    @Override
    public void checkCaseWhenWidgetClassHasDeclaredLocatorAnnotation() {
        checkThatLocatorsAreCreatedCorrectly(((ExtendedApp) app).getAnnotatedWidget(),
                ((ExtendedApp) app).getAnnotatedWidgets(),
                iOSNsPredicateString(XCUIT_ROOT_WIDGET_LOCATOR), iOSNsPredicateString(XCUIT_SUB_WIDGET_LOCATOR));
    }

    @Test
    @Override
    public void checkCaseWhenWidgetClassHasNoDeclaredAnnotationButItHasSuperclass() {
        checkThatLocatorsAreCreatedCorrectly(((ExtendedApp) app).getExtendedWidget(),
                ((ExtendedApp) app).getExtendedWidgets(),
                iOSNsPredicateString(XCUIT_ROOT_WIDGET_LOCATOR), iOSNsPredicateString(XCUIT_SUB_WIDGET_LOCATOR));
    }

    @Test
    @Override
    public void checkCaseWhenBothWidgetFieldAndClassHaveDeclaredAnnotations() {
        checkThatLocatorsAreCreatedCorrectly(((ExtendedApp) app).getExtendedWidgetWithOverriddenLocators(),
                ((ExtendedApp) app).getExtendedWidgetsWithOverriddenLocators(),
                iOSNsPredicateString(XCUIT_EXTERNALLY_DEFINED_WIDGET_LOCATOR),
                iOSNsPredicateString(XCUIT_SUB_WIDGET_LOCATOR));
    }
}
