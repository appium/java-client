package io.appium.java_client.pagefactory_tests.widgets.html;

import static org.junit.Assert.assertTrue;

import io.appium.java_client.pagefactory_tests.widgets.Movie;
import io.appium.java_client.pagefactory_tests.widgets.RottenTomatoesAbstractApp;
import io.appium.java_client.pagefactory_tests.widgets.html.annotated.AnnotatedHtmlMovies;
import io.appium.java_client.pagefactory_tests.widgets.html.annotated.AnnotatedHtmlReview;
import io.appium.java_client.pagefactory_tests.widgets.html.extended.ExtendedHtmlMovies;
import io.appium.java_client.pagefactory_tests.widgets.html.extended.ExtendedHtmlReview;
import io.appium.java_client.pagefactory_tests.widgets.html.simple.HtmlMovies;
import io.appium.java_client.pagefactory_tests.widgets.html.simple.HtmlReview;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.support.FindBy;

/**
 * This is the example of page object with declared Widgets
 * instead of WebElement.
 */
public class RottenTomatoesSite implements RottenTomatoesAbstractApp {

    @FindBy(id = "movies-collection") private HtmlMovies simpleMovies;

    @FindBy(id = "main_container") private HtmlReview simpleReview;

    private AnnotatedHtmlMovies annotatedHtmlMovies;

    private AnnotatedHtmlReview annotatedHtmlReview;

    private ExtendedHtmlMovies extendedHtmlMovies;

    private ExtendedHtmlReview extendedHtmlReview;

    @FindBy(id = "fakeId") private ExtendedHtmlMovies fakeMovies;

    @FindBy(id = "fakeId") private ExtendedHtmlReview fakeReview;

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
        return annotatedHtmlMovies.getMovieCount();
    }

    /**
     * @param index is the desired index.
     * @return a movie.
     */
    public Movie getAnAnnotatedMovie(int index) {
        return annotatedHtmlMovies.getMovie(index);
    }

    /**
     * It reads a review.
     */
    public void checkAnnotatedReview() {
        assertTrue(!StringUtils.isBlank(annotatedHtmlReview.title()));
        assertTrue(!StringUtils.isBlank(annotatedHtmlReview.score()));
        assertTrue(!StringUtils.isBlank(annotatedHtmlReview.info()));
        assertTrue(annotatedHtmlReview.getPoster() != null);
    }

    /**
     * It gets movie count.
     */
    public int getExtendeddMovieCount() {
        return extendedHtmlMovies.getMovieCount();
    }

    /**
     * @param index is the desired index.
     * @return a movie.
     */
    public Movie getAnExtendedMovie(int index) {
        return extendedHtmlMovies.getMovie(index);
    }

    /**
     * It reads a review.
     */
    public void checkExtendedReview() {
        assertTrue(!StringUtils.isBlank(extendedHtmlReview.title()));
        assertTrue(!StringUtils.isBlank(extendedHtmlReview.score()));
        assertTrue(!StringUtils.isBlank(extendedHtmlReview.info()));
        assertTrue(extendedHtmlReview.getPoster() != null);
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
