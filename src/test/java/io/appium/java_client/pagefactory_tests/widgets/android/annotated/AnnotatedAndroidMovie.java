package io.appium.java_client.pagefactory_tests.widgets.android.annotated;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory_tests.widgets.android.simple.AndroidMovie;
import org.openqa.selenium.WebElement;

@AndroidFindBy(className = "android.widget.RelativeLayout") public class AnnotatedAndroidMovie
    extends AndroidMovie {
    protected AnnotatedAndroidMovie(WebElement element) {
        super(element);
    }
}
