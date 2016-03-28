package io.appium.java_client.pagefactory_tests.widgets.html.simple;

import io.appium.java_client.pagefactory_tests.widgets.Movie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HtmlMovie extends Movie {

    @FindBy(className = "movieTitle") private WebElement title;

    @FindBy(className = "tMeterScore") private WebElement score;

    @FindBy(className = "poster_container") private WebElement poster;

    @FindBy(xpath = ".//*[@class=\"movie_info\"]/a/h3") private WebElement linkToMovie;



    protected HtmlMovie(WebElement element) {
        super(element);
    }

    @Override public String title() {
        return title.getText();
    }

    @Override public String score() {
        return score.getText();
    }

    @Override public Object getPoster() {
        return poster.getSize();
    }

    @Override public void goToReview() {
        linkToMovie.click();
    }
}
