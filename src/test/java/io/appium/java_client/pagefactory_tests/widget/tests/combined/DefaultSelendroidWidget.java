package io.appium.java_client.pagefactory_tests.widget.tests.combined;

import io.appium.java_client.pagefactory.SelendroidFindBy;
import io.appium.java_client.pagefactory_tests.widget.tests.AbstractWidget;
import org.openqa.selenium.WebElement;

import java.util.List;

public class DefaultSelendroidWidget extends AbstractWidget {

    @SelendroidFindBy(linkText = "SELENDROID_SOME_SUB_LOCATOR")
    private DefaultSelendroidWidget singleWidget;

    @SelendroidFindBy(linkText = "SELENDROID_SOME_SUB_LOCATOR")
    private List<DefaultSelendroidWidget> multipleWidgets;

    protected DefaultSelendroidWidget(WebElement element) {
        super(element);
    }

    @Override
    public DefaultSelendroidWidget getSubWidget() {
        return singleWidget;
    }

    @Override
    public List<DefaultSelendroidWidget> getSubWidgets() {
        return multipleWidgets;
    }
}
