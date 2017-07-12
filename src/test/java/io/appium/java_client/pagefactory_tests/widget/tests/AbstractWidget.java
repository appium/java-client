package io.appium.java_client.pagefactory_tests.widget.tests;

import static com.google.common.collect.ImmutableList.of;

import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AbstractWidget extends Widget {
    protected AbstractWidget(WebElement element) {
        super(element);
    }

    public <T extends Widget> T getSubWidget() {
        return null;
    }

    public <T extends Widget> List<T> getSubWidgets() {
        return of();
    }

    public String toString() {
        return getWrappedElement().toString();
    }
}
