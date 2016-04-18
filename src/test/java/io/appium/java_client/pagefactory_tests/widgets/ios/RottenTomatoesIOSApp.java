package io.appium.java_client.pagefactory_tests.widgets.ios;

import static org.junit.Assert.assertTrue;

import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.pagefactory_tests.widgets.Movie;
import io.appium.java_client.pagefactory_tests.widgets.RottenTomatoesAbstractApp;
import io.appium.java_client.pagefactory_tests.widgets.ios.annotated.AnnotatedIOSMovies;
import io.appium.java_client.pagefactory_tests.widgets.ios.annotated.AnnotatedIOSReview;
import io.appium.java_client.pagefactory_tests.widgets.ios.extended.ExtendedIOSMovies;
import io.appium.java_client.pagefactory_tests.widgets.ios.extended.ExtendedIOSReview;
import io.appium.java_client.pagefactory_tests.widgets.ios.simple.IOSMovies;
import io.appium.java_client.pagefactory_tests.widgets.ios.simple.IOSReview;
import org.apache.commons.lang3.StringUtils;

/**
 * This is the example of page object with declared Widgets
 * instead of WebElement.
 */
public class RottenTomatoesIOSApp implements RottenTomatoesAbstractApp {

    @iOSFindBy(className = "UIATableView") private IOSMovies simpleMovies;

    @iOSFindBy(xpath = ".//UIAWindow[2]") private IOSReview simpleReview;

    private AnnotatedIOSMovies annotatedIOSMovies;

    private AnnotatedIOSReview annotatedIOSReview;

    private ExtendedIOSMovies extendedIOSMovies;

    private ExtendedIOSReview extendedIOSReview;

    @iOSFindBy(id = "fakeId") private ExtendedIOSMovies fakeMovies;

    @iOSFindBy(id = "fakeId") private ExtendedIOSReview fakeReview;

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
        return annotatedIOSMovies.getMovieCount();
    }

    /**
     * @param index is the desired index.
     * @return a movie.
     */
    public Movie getAnAnnotatedMovie(int index) {
        return annotatedIOSMovies.getMovie(index);
    }

    /**
     * It reads a review.
     */
    public void checkAnnotatedReview() {
        assertTrue(!StringUtils.isBlank(annotatedIOSReview.title()));
        assertTrue(!StringUtils.isBlank(annotatedIOSReview.score()));
        assertTrue(!StringUtils.isBlank(annotatedIOSReview.info()));
        assertTrue(annotatedIOSReview.getPoster() != null);
    }

    /**
     * It gets movie count.
     */
    public int getExtendeddMovieCount() {
        return extendedIOSMovies.getMovieCount();
    }

    /**
     * @param index is the desired index.
     * @return a movie.
     */
    public Movie getAnExtendedMovie(int index) {
        return extendedIOSMovies.getMovie(index);
    }

    /**
     * It reads a review.
     */
    public void checkExtendedReview() {
        assertTrue(!StringUtils.isBlank(extendedIOSReview.title()));
        assertTrue(!StringUtils.isBlank(extendedIOSReview.score()));
        assertTrue(!StringUtils.isBlank(extendedIOSReview.info()));
        assertTrue(extendedIOSReview.getPoster() != null);
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
