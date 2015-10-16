package io.appium.java_client.pagefactory_tests.widgets.html.annotated;

import io.appium.java_client.pagefactory_tests.widgets.html.simple.HtmlMovie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(className = "mb-movie") public class AnnotatedHtmlMovie extends HtmlMovie {
    protected AnnotatedHtmlMovie(WebElement element) {
        super(element);
    }
}
