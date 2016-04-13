package io.appium.java_client.pagefactory_tests.widgets.android;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;
import io.appium.java_client.pagefactory_tests.widgets.Movie;
import io.appium.java_client.pagefactory_tests.widgets.android.annotated.AnnotatedAndroidMovies;
import io.appium.java_client.pagefactory_tests.widgets.android.annotated.AnnotatedAndroidReview;
import io.appium.java_client.pagefactory_tests.widgets.android.extended.ExtendedAndroidMovies;
import io.appium.java_client.pagefactory_tests.widgets.android.extended.ExtendedAndroidReview;
import io.appium.java_client.pagefactory_tests.widgets.android.simple.AndroidMovies;
import io.appium.java_client.pagefactory_tests.widgets.android.simple.AndroidReview;
import org.apache.commons.lang3.StringUtils;

import static org.junit.Assert.assertTrue;

/**
 * This is the example of page object with declared Widgets
 * instead of WebElement.
 */
public class RottenTomatoesApp {

    @AndroidFindBy(id = "com.codepath.example.rottentomatoes:id/lvMovies") private AndroidMovies
        simpleMovies;

    @AndroidFindBys({@AndroidFindBy(id = "android:id/content"),
        @AndroidFindBy(className = "android.widget.RelativeLayout")}) private AndroidReview
        simpleReview;

    private AnnotatedAndroidMovies annotatedAndroidMovies;

    private AnnotatedAndroidReview annotatedAndroidReview;

    private ExtendedAndroidMovies extendedAndroidMovies;

    private ExtendedAndroidReview extendedAndroidReview;

    @AndroidFindBy(id = "fakeId") private ExtendedAndroidMovies fakeMovies;

    @AndroidFindBy(id = "fakeId") private ExtendedAndroidReview fakeReview;


    public int getSimpleMovieCount() {
        return simpleMovies.getMovieCount();
    }

    public Movie getASimpleMovie(int index) {
        return simpleMovies.getMovie(index);
    }

    public void checkSimpleReview() {
        assertTrue(!StringUtils.isBlank(simpleReview.title()));
        assertTrue(!StringUtils.isBlank(simpleReview.score()));
        assertTrue(!StringUtils.isBlank(simpleReview.info()));
        assertTrue(simpleReview.getPoster() != null);
    }

    /////////////////////////////////////////////////////////
    public int getAnnotatedMovieCount() {
        return annotatedAndroidMovies.getMovieCount();
    }

    public Movie getAnAnnotatedMovie(int index) {
        return annotatedAndroidMovies.getMovie(index);
    }

    public void checkAnnotatedReview() {
        assertTrue(!StringUtils.isBlank(annotatedAndroidReview.title()));
        assertTrue(!StringUtils.isBlank(annotatedAndroidReview.score()));
        assertTrue(!StringUtils.isBlank(annotatedAndroidReview.info()));
        assertTrue(annotatedAndroidReview.getPoster() != null);
    }
    /////////////////////////////////////////////////////////

    public int getExtendeddMovieCount() {
        return extendedAndroidMovies.getMovieCount();
    }

    public Movie getAnExtendedMovie(int index) {
        return extendedAndroidMovies.getMovie(index);
    }

    public void checkExtendedReview() {
        assertTrue(!StringUtils.isBlank(extendedAndroidReview.title()));
        assertTrue(!StringUtils.isBlank(extendedAndroidReview.score()));
        assertTrue(!StringUtils.isBlank(extendedAndroidReview.info()));
        assertTrue(extendedAndroidReview.getPoster() != null);
    }

    /////////////////////////////////////////////////////////

    public int getFakedMovieCount() {
        return fakeMovies.getMovieCount();
    }

    public void checkFakeReview() {
        assertTrue(!StringUtils.isBlank(fakeReview.title()));
        assertTrue(!StringUtils.isBlank(fakeReview.score()));
        assertTrue(!StringUtils.isBlank(fakeReview.info()));
        assertTrue(fakeReview.getPoster() != null);
    }
}
