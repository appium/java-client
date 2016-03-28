package io.appium.java_client.pagefactory_tests.widgets.selendroid.annotated;


import io.appium.java_client.pagefactory.SelendroidFindBy;
import io.appium.java_client.pagefactory_tests.widgets.selendroid.simple.SelendroidReview;
import org.openqa.selenium.WebElement;


@SelendroidFindBy(className = "android.widget.RelativeLayout")
public class AnnotatedSelendroidReview extends SelendroidReview {

    protected AnnotatedSelendroidReview(WebElement element) {
        super(element);
    }
}
