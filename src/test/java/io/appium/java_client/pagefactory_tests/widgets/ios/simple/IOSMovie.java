package io.appium.java_client.pagefactory_tests.widgets.ios.simple;

import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.pagefactory_tests.widgets.Movie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

public class IOSMovie extends Movie{

    @iOSFindBy(className = "UIAStaticText")
    private IOSElement staticText;

    protected IOSMovie(WebElement element) {
        super(element);
    }

    @Override
    public String title() {
        return staticText.getText().split(",")[0];
    }

    @Override
    public String score() {
        return staticText.getText().split(",")[3];
    }

    @Override
    public Object getPoster() {
        return ((RemoteWebElement) getWrappedElement()).getSize();
    }

    @Override
    public void goToReview() {
        ((IOSElement) getWrappedElement()).tap(1, 1500);
    }
}
