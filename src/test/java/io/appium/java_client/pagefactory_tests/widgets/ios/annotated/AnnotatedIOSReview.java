package io.appium.java_client.pagefactory_tests.widgets.ios.annotated;


import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.pagefactory.iOSFindBys;
import io.appium.java_client.pagefactory_tests.widgets.Review;
import org.openqa.selenium.WebElement;

@iOSFindBy(className = "UIAWindow")
public class AnnotatedIOSReview extends Review{

    @iOSFindBy(className = "UIANavigationBar")
    private IOSElement navigationBar;

    @iOSFindBys({@iOSFindBy(className = "UIAScrollView"),
    @iOSFindBy(className = "UIAStaticText")})
    private IOSElement synopsis;

    @iOSFindBy(className = "UIAImage")
    private IOSElement poster;


    protected AnnotatedIOSReview(WebElement element) {
        super(element);
    }

    @Override
    public String title() {
        return navigationBar.getText();
    }

    @Override
    public String score() {
        return "100";
    }

    @Override
    public String info() {
        return synopsis.getText();
    }

    @Override
    public Object getPoster() {
        return poster.getSize();
    }
}
