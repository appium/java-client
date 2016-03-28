package io.appium.java_client.pagefactory_tests.widgets.selendroid.annotated;


import io.appium.java_client.pagefactory.SelendroidFindBy;
import io.appium.java_client.pagefactory_tests.widgets.Movie;
import io.appium.java_client.pagefactory_tests.widgets.Movies;
import org.openqa.selenium.WebElement;

import java.util.List;

@SelendroidFindBy(id = "lvMovies") public class AnnotatedSelendroidMovies extends Movies {

    List<AnnotatedSelendroidMovie> movieList;

    /*
    There could be additional behavior.
    But for test it is enough.
     */

    protected AnnotatedSelendroidMovies(WebElement element) {
        super(element);
    }

    @Override public int getMovieCount() {
        return movieList.size();
    }

    @Override public Movie getMovie(int index) {
        return movieList.get(index);
    }
}
