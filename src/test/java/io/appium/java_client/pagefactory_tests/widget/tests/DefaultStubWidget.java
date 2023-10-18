package io.appium.java_client.pagefactory_tests.widget.tests;

import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;

import java.util.List;

public class DefaultStubWidget extends Widget {
    protected DefaultStubWidget(WebElement element) {
        super(element);
    }

    public <T extends Widget> T getSubWidget() {
        return null;
    }

    public <T extends Widget> List<T> getSubWidgets() {
        return List.of();
    }

    @Override
    public String toString() {
        return getWrappedElement().toString();
    }
}
