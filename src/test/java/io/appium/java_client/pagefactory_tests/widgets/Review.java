package io.appium.java_client.pagefactory_tests.widgets;

import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;

public abstract class Review extends Widget {

    protected Review(WebElement element) {
        super(element);
    }

    public abstract String title();

    public abstract String score();

    public abstract String info();

    public abstract Object getPoster();

    @Override public Review getSelfReference() {
        return (Review) super.getSelfReference();
    }
}
