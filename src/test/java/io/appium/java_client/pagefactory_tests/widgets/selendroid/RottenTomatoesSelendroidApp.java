package io.appium.java_client.pagefactory_tests.widgets.selendroid;

import static org.junit.Assert.assertTrue;

import io.appium.java_client.pagefactory.SelendroidFindBy;
import io.appium.java_client.pagefactory_tests.widgets.Movie;
import io.appium.java_client.pagefactory_tests.widgets.RottenTomatoesAbstractApp;
import io.appium.java_client.pagefactory_tests.widgets.selendroid.annotated.AnnotatedSelendroidMovies;
import io.appium.java_client.pagefactory_tests.widgets.selendroid.annotated.AnnotatedSelendroidReview;
import io.appium.java_client.pagefactory_tests.widgets.selendroid.extended.ExtendedSelendroidMovies;
import io.appium.java_client.pagefactory_tests.widgets.selendroid.extended.ExtendedSelendroidReview;
import io.appium.java_client.pagefactory_tests.widgets.selendroid.simple.SelendroidMovies;
import io.appium.java_client.pagefactory_tests.widgets.selendroid.simple.SelendroidReview;
import org.apache.commons.lang3.StringUtils;

/**
 * This is the example of page object with declared Widgets
 * instead of WebElement.
 */
public class RottenTomatoesSelendroidApp implements RottenTomatoesAbstractApp {

    @SelendroidFindBy(id = "lvMovies") private SelendroidMovies simpleMovies;

    @SelendroidFindBy(className = "android.widget.RelativeLayout") private SelendroidReview
        simpleReview;

    private AnnotatedSelendroidMovies annotatedSelendroidMovies;

    private AnnotatedSelendroidReview annotatedSelendroidReview;

    private ExtendedSelendroidMovies extendedSelendroidMovies;

    private ExtendedSelendroidReview extendedSelendroidReview;

    @SelendroidFindBy(id = "fakeId") private ExtendedSelendroidMovies fakeMovies;

    @SelendroidFindBy(id = "fakeId") private ExtendedSelendroidReview fakeReview;

    /**
     * It gets movie count.
     */
    public int getSimpleMovieCount() {
        return simpleMovies.getMovieCount();
    }

    /**
     * @param index is the desired index.
     * @return a movie.
     */
    public Movie getASimpleMovie(int index) {
        return simpleMovies.getMovie(index);
    }

    /**
     * It reads a review.
     */
    public void checkSimpleReview() {
        assertTrue(!StringUtils.isBlank(simpleReview.title()));
        assertTrue(!StringUtils.isBlank(simpleReview.score()));
        assertTrue(!StringUtils.isBlank(simpleReview.info()));
        assertTrue(simpleReview.getPoster() != null);
    }

    /**
     * It gets movie count.
     */
    public int getAnnotatedMovieCount() {
        return annotatedSelendroidMovies.getMovieCount();
    }

    /**
     * @param index is the desired index.
     * @return a movie.
     */
    public Movie getAnAnnotatedMovie(int index) {
        return annotatedSelendroidMovies.getMovie(index);
    }

    /**
     * It reads a review.
     */
    public void checkAnnotatedReview() {
        assertTrue(!StringUtils.isBlank(annotatedSelendroidReview.title()));
        assertTrue(!StringUtils.isBlank(annotatedSelendroidReview.score()));
        assertTrue(!StringUtils.isBlank(annotatedSelendroidReview.info()));
        assertTrue(annotatedSelendroidReview.getPoster() != null);
    }

    /**
     * It gets movie count.
     */
    public int getExtendeddMovieCount() {
        return extendedSelendroidMovies.getMovieCount();
    }

    /**
     * @param index is the desired index.
     * @return a movie.
     */
    public Movie getAnExtendedMovie(int index) {
        return extendedSelendroidMovies.getMovie(index);
    }

    /**
     * It reads a review.
     */
    public void checkExtendedReview() {
        assertTrue(!StringUtils.isBlank(extendedSelendroidReview.title()));
        assertTrue(!StringUtils.isBlank(extendedSelendroidReview.score()));
        assertTrue(!StringUtils.isBlank(extendedSelendroidReview.info()));
        assertTrue(extendedSelendroidReview.getPoster() != null);
    }

    /**
     * It gets movie count.
     */
    public int getFakedMovieCount() {
        return fakeMovies.getMovieCount();
    }

    /**
     * It reads a review.
     */
    public void checkFakeReview() {
        assertTrue(!StringUtils.isBlank(fakeReview.title()));
        assertTrue(!StringUtils.isBlank(fakeReview.score()));
        assertTrue(!StringUtils.isBlank(fakeReview.info()));
        assertTrue(fakeReview.getPoster() != null);
    }
}
