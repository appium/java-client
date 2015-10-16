package io.appium.java_client.pagefactory_tests.widgets;

import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;

public abstract class Movies extends Widget {

    protected Movies(WebElement element) {
        super(element);
    }

    public abstract int getMovieCount();

    public abstract Movie getMovie(int index);

    @Override public Movies getSelfReference() {
        return (Movies) super.getSelfReference();
    }
}
