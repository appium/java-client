package io.appium.java_client.pagefactory_tests.widgets.android.annotated;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;
import io.appium.java_client.pagefactory_tests.widgets.android.simple.AndroidReview;
import org.openqa.selenium.WebElement;

@AndroidFindBys({@AndroidFindBy(id = "android:id/content"),
    @AndroidFindBy(className = "android.widget.RelativeLayout")})
public class AnnotatedAndroidReview extends AndroidReview {

    protected AnnotatedAndroidReview(WebElement element) {
        super(element);
    }
}
