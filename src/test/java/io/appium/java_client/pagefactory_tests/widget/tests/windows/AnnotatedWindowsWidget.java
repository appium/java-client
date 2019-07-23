package io.appium.java_client.pagefactory_tests.widget.tests.windows;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.seleniumone.WebElement;

@WindowsFindBy(windowsAutomation = "SOME_ROOT_LOCATOR")
@iOSXCUITFindBy(iOSNsPredicate = "XCUIT_SOME_ROOT_LOCATOR")
public class AnnotatedWindowsWidget extends DefaultWindowsWidget {
    public static String WINDOWS_ROOT_WIDGET_LOCATOR = "SOME_ROOT_LOCATOR";

    protected AnnotatedWindowsWidget(WebElement element) {
        super(element);
    }
}
