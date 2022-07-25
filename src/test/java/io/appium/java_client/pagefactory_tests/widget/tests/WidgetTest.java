package io.appium.java_client.pagefactory_tests.widget.tests;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;

import static org.openqa.selenium.support.PageFactory.initElements;

public abstract class WidgetTest {

    protected final AbstractApp app;

    protected WidgetTest(AbstractApp app, WebDriver driver) {
        this.app = app;
        initElements(new AppiumFieldDecorator(driver), app);
    }

    @ParameterizedTest
    @MethodSource("data")
    public abstract void checkThatWidgetsAreCreatedCorrectly();
}
