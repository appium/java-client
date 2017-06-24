package io.appium.java_client.pagefactory_tests.widget.tests;

import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;

import java.util.List;

public abstract class AbstractWidget extends Widget {
    protected AbstractWidget(WebElement element) {
        super(element);
    }

    public abstract <T extends Widget> T getSubWidget();

    public abstract <T extends Widget> List<T> getSubWidgets();

    public String toString() {
        return getWrappedElement().toString();
    }
}
