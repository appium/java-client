package io.appium.java_client.pagefactory_tests.widget.tests.windows;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory_tests.widget.tests.DefaultStubWidget;
import org.openqa.selenium.WebElement;

import java.util.List;

public class DefaultWindowsWidget extends DefaultStubWidget {

    public static String WINDOWS_SUB_WIDGET_LOCATOR = "SOME_SUB_LOCATOR";

    @WindowsFindBy(windowsAutomation = "SOME_SUB_LOCATOR")
    @iOSXCUITFindBy(iOSNsPredicate = "XCUIT_SOME_SUB_LOCATOR")
    private DefaultWindowsWidget singleWidget;

    @WindowsFindBy(windowsAutomation = "SOME_SUB_LOCATOR")
    @iOSXCUITFindBy(iOSNsPredicate = "XCUIT_SOME_SUB_LOCATOR")
    private List<DefaultWindowsWidget> multipleWidgets;

    protected DefaultWindowsWidget(WebElement element) {
        super(element);
    }

    @Override
    public DefaultWindowsWidget getSubWidget() {
        return singleWidget;
    }

    @Override
    public List<DefaultWindowsWidget> getSubWidgets() {
        return multipleWidgets;
    }
}
