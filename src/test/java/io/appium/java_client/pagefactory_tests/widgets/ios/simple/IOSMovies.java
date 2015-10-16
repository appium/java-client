package io.appium.java_client.pagefactory_tests.widgets.ios.simple;

import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.pagefactory_tests.widgets.Movie;
import io.appium.java_client.pagefactory_tests.widgets.Movies;
import org.openqa.selenium.WebElement;

import java.util.List;

//classNme = UIATableView
public class IOSMovies extends Movies {

    @iOSFindBy(className = "UIATableCell") List<IOSMovie> movieList;

    /*
    There could be additional behavior.
    But for test it is enough.
     */

    protected IOSMovies(WebElement element) {
        super(element);
    }

    @Override public int getMovieCount() {
        return movieList.size();
    }

    @Override public Movie getMovie(int index) {
        return movieList.get(index);
    }
}
