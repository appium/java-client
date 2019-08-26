package io.appium.java_client.pagefactory_tests.widget.tests;

import static java.util.Arrays.copyOf;
import static io.appium.java_client.selenium.support.PageFactory.initElements;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.selenium.WebDriver;
import org.junit.Test;

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
