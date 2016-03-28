package io.appium.java_client.pagefactory_tests.widgets.selendroid.annotated;

import io.appium.java_client.pagefactory.SelendroidFindBy;
import io.appium.java_client.pagefactory_tests.widgets.selendroid.simple.SelendroidMovie;
import org.openqa.selenium.WebElement;

@SelendroidFindBy(className = "android.widget.RelativeLayout") public class AnnotatedSelendroidMovie
    extends SelendroidMovie {

    protected AnnotatedSelendroidMovie(WebElement element) {
        super(element);
    }
}
