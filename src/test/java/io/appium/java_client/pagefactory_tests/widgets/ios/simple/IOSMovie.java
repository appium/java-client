package io.appium.java_client.pagefactory_tests.widgets.ios.simple;

import io.appium.java_client.TouchableElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.pagefactory_tests.widgets.Movie;
import org.openqa.selenium.WebElement;

import java.util.List;

public class IOSMovie extends Movie {

    @iOSFindBy(className = "UIAStaticText") private List<IOSElement> staticTexts;

    protected IOSMovie(WebElement element) {
        super(element);
    }

    @Override public String title() {
        return staticTexts.get(0).getText();
    }

    @Override public String score() {
        return staticTexts.get(3).getText();
    }

    @Override public Object getPoster() {
        return getWrappedElement().getSize();
    }

    @Override public void goToReview() {
        ((TouchableElement) getWrappedElement()).tap(1, 1500);
    }
}
