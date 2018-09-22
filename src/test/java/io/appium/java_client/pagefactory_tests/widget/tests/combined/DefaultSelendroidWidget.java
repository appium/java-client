package io.appium.java_client.pagefactory_tests.widget.tests.combined;

import io.appium.java_client.pagefactory.SelendroidFindBy;
import io.appium.java_client.pagefactory_tests.widget.tests.DefaultStubWidget;
import org.openqa.selenium.WebElement;

import java.util.List;

@Deprecated
public class DefaultSelendroidWidget extends DefaultStubWidget {

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
