package io.appium.java_client.pagefactory_tests.widget.tests;

import static org.openqa.selenium.support.PageFactory.initElements;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

public abstract class WidgetTest {

    protected final AbstractApp app;

    protected WidgetTest(AbstractApp app, WebDriver driver) {
        this.app = app;
        initElements(new AppiumFieldDecorator(driver), app);
    }

    @Test
    public abstract void checkThatWidgetsAreCreatedCorrectly();
}
