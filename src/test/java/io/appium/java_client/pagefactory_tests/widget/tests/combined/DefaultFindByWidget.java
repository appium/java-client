package io.appium.java_client.pagefactory_tests.widget.tests.combined;

import io.appium.java_client.pagefactory_tests.widget.tests.DefaultStubWidget;
import org.openqa.seleniumone.WebElement;
import org.openqa.seleniumone.support.FindBy;

import java.util.List;

public class DefaultFindByWidget extends DefaultStubWidget {

    @FindBy(id = "SOME_SUB_LOCATOR")
    private DefaultFindByWidget singleWidget;

    @FindBy(id = "SOME_SUB_LOCATOR")
    private List<DefaultFindByWidget> multipleWidgets;


    protected DefaultFindByWidget(WebElement element) {
        super(element);
    }

    @Override
    public DefaultFindByWidget getSubWidget() {
        return singleWidget;
    }

    @Override
    public List<DefaultFindByWidget> getSubWidgets() {
        return multipleWidgets;
    }
}
