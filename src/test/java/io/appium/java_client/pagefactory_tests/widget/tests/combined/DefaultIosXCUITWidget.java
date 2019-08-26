package io.appium.java_client.pagefactory_tests.widget.tests.combined;

import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory_tests.widget.tests.DefaultStubWidget;
import io.appium.java_client.selenium.WebElement;

import java.util.List;

public class DefaultIosXCUITWidget extends DefaultStubWidget {

    public static String XCUIT_SUB_WIDGET_LOCATOR = "XCUIT_SOME_SUB_LOCATOR";

    @iOSXCUITFindBy(iOSNsPredicate = "XCUIT_SOME_SUB_LOCATOR")
    private DefaultIosXCUITWidget singleWidget;

    @iOSXCUITFindBy(iOSNsPredicate = "XCUIT_SOME_SUB_LOCATOR")
    private List<DefaultIosXCUITWidget> multipleWidgets;

    protected DefaultIosXCUITWidget(WebElement element) {
        super(element);
    }

    @Override
    public DefaultIosXCUITWidget getSubWidget() {
        return singleWidget;
    }

    @Override
    public List<DefaultIosXCUITWidget> getSubWidgets() {
        return multipleWidgets;
    }
}
