package io.appium.java_client.pagefactory_tests.widgets.selendroid.simple;


import io.appium.java_client.pagefactory.SelendroidFindBy;
import io.appium.java_client.pagefactory_tests.widgets.Review;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

public class SelendroidReview extends Review {

    @SelendroidFindBy(id = "tvTitle") private RemoteWebElement title;

    @SelendroidFindBy(id = "tvCriticsScore") private RemoteWebElement score;

    @SelendroidFindBy(id = "tvSynopsis") private RemoteWebElement synopsis;

    @SelendroidFindBy(id = "ivPosterImage") private RemoteWebElement poster;


    protected SelendroidReview(WebElement element) {
        super(element);
    }

    @Override public String title() {
        return title.getText();
    }

    @Override public String score() {
        return score.getText();
    }

    @Override public String info() {
        return synopsis.getText();
    }

    @Override public Object getPoster() {
        return poster.getSize();
    }
}
