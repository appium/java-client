package io.appium.java_client.pagefactory_tests.widget.tests.android;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.selenium.WebElement;

@AndroidFindBy(uiAutomator = "SOME_ROOT_LOCATOR")
public class AnnotatedAndroidWidget extends DefaultAndroidWidget {
    public static String ANDROID_ROOT_WIDGET_LOCATOR = "SOME_ROOT_LOCATOR";

    protected AnnotatedAndroidWidget(WebElement element) {
        super(element);
    }
}
