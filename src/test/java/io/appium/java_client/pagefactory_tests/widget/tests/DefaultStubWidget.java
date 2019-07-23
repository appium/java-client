package io.appium.java_client.pagefactory_tests.widget.tests;

import com.google.common.collect.ImmutableList;

import io.appium.java_client.pagefactory.Widget;
import org.openqa.seleniumone.WebElement;

import java.util.List;

public class DefaultStubWidget extends Widget {
    protected DefaultStubWidget(WebElement element) {
        super(element);
    }

    public <T extends Widget> T getSubWidget() {
        return null;
    }

    public <T extends Widget> List<T> getSubWidgets() {
        return ImmutableList.of();
    }

    @Override
    public String toString() {
        return getWrappedElement().toString();
    }
}
