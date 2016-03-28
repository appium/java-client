package io.appium.java_client.pagefactory_tests.widgets.ios.annotated;

import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.pagefactory_tests.widgets.Movie;
import io.appium.java_client.pagefactory_tests.widgets.Movies;
import org.openqa.selenium.WebElement;

import java.util.List;

@iOSFindBy(className = "UIATableView") public class AnnotatedIOSMovies extends Movies {

    List<AnnotatedIOSMovie> movieList;

    /*
    There could be additional behavior.
    But for test it is enough.
     */

    protected AnnotatedIOSMovies(WebElement element) {
        super(element);
    }

    @Override public int getMovieCount() {
        return movieList.size();
    }

    @Override public Movie getMovie(int index) {
        return movieList.get(index);
    }
}
