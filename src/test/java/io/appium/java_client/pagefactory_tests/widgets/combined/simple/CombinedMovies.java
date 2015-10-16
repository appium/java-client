package io.appium.java_client.pagefactory_tests.widgets.combined.simple;


import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory_tests.widgets.Movie;
import io.appium.java_client.pagefactory_tests.widgets.Movies;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CombinedMovies extends Movies {

    @AndroidFindBy(className = "android.widget.RelativeLayout") @FindBy(className = "mb-movie")
    List<CombinedMovie> movieList;

    /*
    There could be additional behavior.
    But for test it is enough.
     */

    protected CombinedMovies(WebElement element) {
        super(element);
    }

    @Override public int getMovieCount() {
        return movieList.size();
    }

    @Override public Movie getMovie(int index) {
        return movieList.get(index);
    }
}
