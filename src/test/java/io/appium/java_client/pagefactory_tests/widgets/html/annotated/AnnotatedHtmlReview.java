package io.appium.java_client.pagefactory_tests.widgets.html.annotated;


import io.appium.java_client.pagefactory_tests.widgets.html.simple.HtmlReview;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(id = "main_container") public class AnnotatedHtmlReview extends HtmlReview {
    protected AnnotatedHtmlReview(WebElement element) {
        super(element);
    }
}
