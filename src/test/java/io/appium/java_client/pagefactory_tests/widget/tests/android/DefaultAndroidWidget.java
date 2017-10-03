package io.appium.java_client.pagefactory_tests.widget.tests.android;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.SelendroidFindBy;
import io.appium.java_client.pagefactory_tests.widget.tests.DefaultStubWidget;
import org.openqa.selenium.WebElement;

import java.util.List;

public class DefaultAndroidWidget extends DefaultStubWidget {

    public static String ANDROID_SUB_WIDGET_LOCATOR = "SOME_SUB_LOCATOR";
    public static String SELENDROID_SUB_WIDGET_LOCATOR = "SELENDROID_SOME_SUB_LOCATOR";

    @AndroidFindBy(uiAutomator = "SOME_SUB_LOCATOR")
    @SelendroidFindBy(linkText = "SELENDROID_SOME_SUB_LOCATOR")
    private DefaultAndroidWidget singleWidget;

    @AndroidFindBy(uiAutomator = "SOME_SUB_LOCATOR")
    @SelendroidFindBy(linkText = "SELENDROID_SOME_SUB_LOCATOR")
    private List<DefaultAndroidWidget> multipleWidgets;

    protected DefaultAndroidWidget(WebElement element) {
        super(element);
    }

    @Override
    public DefaultAndroidWidget getSubWidget() {
        return singleWidget;
    }

    @Override
    public List<DefaultAndroidWidget> getSubWidgets() {
        return multipleWidgets;
    }
}
