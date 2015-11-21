package io.appium.java_client.pagefactory_tests.widgets.combined.annotated;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory_tests.widgets.combined.simple.CombinedMovie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@AndroidFindBy(className = "android.widget.RelativeLayout")
@FindBy(className = "mb-movie")
public class AnnotatedCombinedMovie extends CombinedMovie {
    protected AnnotatedCombinedMovie(WebElement element) {
        super(element);
    }
}
