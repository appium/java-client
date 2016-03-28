package io.appium.java_client.pagefactory_tests.widgets.android.annotated;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory_tests.widgets.Movie;
import io.appium.java_client.pagefactory_tests.widgets.Movies;
import org.openqa.selenium.WebElement;

import java.util.List;

@AndroidFindBy(id = "com.codepath.example.rottentomatoes:id/lvMovies")
public class AnnotatedAndroidMovies extends Movies {

    List<AnnotatedAndroidMovie> movieList;

    /*
    There could be additional behavior.
    But for test it is enough.
     */

    protected AnnotatedAndroidMovies(WebElement element) {
        super(element);
    }

    @Override public int getMovieCount() {
        return movieList.size();
    }

    @Override public Movie getMovie(int index) {
        return movieList.get(index);
    }
}
