package io.appium.java_client.pagefactory_tests.widget.tests.ios;

import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

@iOSXCUITFindBy(iOSNsPredicate = "XCUIT_SOME_ROOT_LOCATOR")
public class AnnotatedIosWidget extends DefaultIosWidget {
    public static String IOS_ROOT_WIDGET_LOCATOR = "SOME_ROOT_LOCATOR";
    public static String XCUIT_ROOT_WIDGET_LOCATOR = "XCUIT_SOME_ROOT_LOCATOR";

    protected AnnotatedIosWidget(WebElement element) {
        super(element);
    }
}
