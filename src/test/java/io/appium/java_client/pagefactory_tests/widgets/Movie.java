package io.appium.java_client.pagefactory_tests.widgets;

import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;

public abstract class Movie extends Widget {
    protected Movie(WebElement element) {
        super(element);
    }

    public abstract String title();

    public abstract String score();

    public abstract Object getPoster();

    public abstract void goToReview();

    @Override public Movie getSelfReference() {
        return (Movie) super.getSelfReference();
    }
}
