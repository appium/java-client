package io.appium.java_client.pagefactory_tests.widget.tests;

import static java.util.Arrays.copyOf;
import static org.openqa.seleniumone.support.PageFactory.initElements;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.junit.Test;
import org.openqa.seleniumone.WebDriver;

public abstract class WidgetTest {

    protected final AbstractApp app;

    protected static Object[] dataArray(Object...objects) {
        return copyOf(objects, objects.length);
    }

    protected WidgetTest(AbstractApp app, WebDriver driver) {
        this.app = app;
        initElements(new AppiumFieldDecorator(driver), app);
    }

    @Test
    public abstract void checkThatWidgetsAreCreatedCorrectly();
}
