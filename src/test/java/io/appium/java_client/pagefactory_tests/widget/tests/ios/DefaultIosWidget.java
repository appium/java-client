package io.appium.java_client.pagefactory_tests.widget.tests.ios;

import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory_tests.widget.tests.AbstractWidget;
import org.openqa.selenium.WebElement;

import java.util.List;

public class DefaultIosWidget extends AbstractWidget {

    public static String IOS_SUB_WIDGET_LOCATOR = "SOME_SUB_LOCATOR";
    public static String XCUIT_SUB_WIDGET_LOCATOR = "XCUIT_SOME_SUB_LOCATOR";

    @iOSFindBy(uiAutomator = "SOME_SUB_LOCATOR")
    @iOSXCUITFindBy(iOSNsPredicate = "XCUIT_SOME_SUB_LOCATOR")
    private DefaultIosWidget singleWidget;

    @iOSFindBy(uiAutomator = "SOME_SUB_LOCATOR")
    @iOSXCUITFindBy(iOSNsPredicate = "XCUIT_SOME_SUB_LOCATOR")
    private List<DefaultIosWidget> multipleWidgets;

    protected DefaultIosWidget(WebElement element) {
        super(element);
    }

    @Override
    public DefaultIosWidget getSubWidget() {
        return singleWidget;
    }

    @Override
    public List<DefaultIosWidget> getSubWidgets() {
        return multipleWidgets;
    }
}
