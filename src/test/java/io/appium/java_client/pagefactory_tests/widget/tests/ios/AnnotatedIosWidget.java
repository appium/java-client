package io.appium.java_client.pagefactory_tests.widget.tests.ios;

import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory_tests.widget.tests.combined.DefaultIosXCUITWidget;
import org.openqa.selenium.WebElement;

@iOSXCUITFindBy(iOSNsPredicate = "XCUIT_SOME_ROOT_LOCATOR")
public class AnnotatedIosWidget extends DefaultIosXCUITWidget {
    public static String XCUIT_ROOT_WIDGET_LOCATOR = "XCUIT_SOME_ROOT_LOCATOR";

    protected AnnotatedIosWidget(WebElement element) {
        super(element);
    }
}
