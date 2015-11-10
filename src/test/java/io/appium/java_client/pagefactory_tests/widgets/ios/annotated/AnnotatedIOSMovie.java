package io.appium.java_client.pagefactory_tests.widgets.ios.annotated;

import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.pagefactory_tests.widgets.Movie;
import io.appium.java_client.pagefactory_tests.widgets.ios.simple.IOSMovie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

@iOSFindBy(className = "UIATableCell")
public class AnnotatedIOSMovie extends IOSMovie {
    protected AnnotatedIOSMovie(WebElement element) {
        super(element);
    }
}
