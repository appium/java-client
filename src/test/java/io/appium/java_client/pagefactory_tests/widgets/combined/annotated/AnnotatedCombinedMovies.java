package io.appium.java_client.pagefactory_tests.widgets.combined.annotated;


import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.SelendroidFindBy;
import io.appium.java_client.pagefactory_tests.widgets.Movie;
import io.appium.java_client.pagefactory_tests.widgets.Movies;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@AndroidFindBy(id = "com.codepath.example.rottentomatoes:id/lvMovies")
@SelendroidFindBy(id = "lvMovies")
@FindBy(id = "movies-collection")
public class AnnotatedCombinedMovies extends Movies {

    List<AnnotatedCombinedMovie> movieList;

    /*
    There could be additional behavior.
    But for test it is enough.
     */

    protected AnnotatedCombinedMovies(WebElement element) {
        super(element);
    }

    @Override public int getMovieCount() {
        return movieList.size();
    }

    @Override public Movie getMovie(int index) {
        return movieList.get(index);
    }
}
