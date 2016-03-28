package io.appium.java_client.pagefactory_tests.widgets.selendroid.simple;


import io.appium.java_client.pagefactory.SelendroidFindBy;
import io.appium.java_client.pagefactory_tests.widgets.Movie;
import io.appium.java_client.pagefactory_tests.widgets.Movies;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SelendroidMovies extends Movies {

    @SelendroidFindBy(className = "android.widget.RelativeLayout") List<SelendroidMovie> movieList;

    /*
    There could be additional behavior.
    But for test it is enough.
     */

    protected SelendroidMovies(WebElement element) {
        super(element);
    }

    @Override public int getMovieCount() {
        return movieList.size();
    }

    @Override public Movie getMovie(int index) {
        return movieList.get(index);
    }
}
