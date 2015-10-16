package io.appium.java_client.pagefactory_tests.widgets.combined;

import static org.junit.Assert.assertTrue;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;
import io.appium.java_client.pagefactory.SelendroidFindBy;
import io.appium.java_client.pagefactory_tests.widgets.Movie;
import io.appium.java_client.pagefactory_tests.widgets.RottenTomatoesAbstractApp;
import io.appium.java_client.pagefactory_tests.widgets.combined.annotated.AnnotatedCombinedMovies;
import io.appium.java_client.pagefactory_tests.widgets.combined.annotated.AnnotatedCombinedReview;
import io.appium.java_client.pagefactory_tests.widgets.combined.extended.ExtendedCombinedMovies;
import io.appium.java_client.pagefactory_tests.widgets.combined.extended.ExtendedCombinedReview;
import io.appium.java_client.pagefactory_tests.widgets.combined.simple.CombinedMovies;
import io.appium.java_client.pagefactory_tests.widgets.combined.simple.CombinedReview;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.support.FindBy;

/**
 * This is the example of page object with declared Widgets
 * instead of WebElement.
 */
public class RottenTomatoesAppWithCombinedWidgets implements RottenTomatoesAbstractApp {

    @AndroidFindBy(id = "com.codepath.example.rottentomatoes:id/lvMovies")
    @SelendroidFindBy(id = "lvMovies") @FindBy(id = "movies-collection") private CombinedMovies
        simpleMovies;

    @AndroidFindBys({@AndroidFindBy(id = "android:id/content"),
        @AndroidFindBy(className = "android.widget.RelativeLayout")})
    @FindBy(id = "main_container")
    @SelendroidFindBy(className = "android.widget.RelativeLayout") private CombinedReview
        simpleReview;

    private AnnotatedCombinedMovies annotatedCombinedMovies;

    private AnnotatedCombinedReview annotatedCombinedReview;

    private ExtendedCombinedMovies extendedCombinedMovies;

    private ExtendedCombinedReview extendedCombinedReview;

    @AndroidFindBy(id = "fakeId") @FindBy(id = "fakeId") private ExtendedCombinedMovies fakeMovies;

    @AndroidFindBy(id = "fakeId") @FindBy(id = "fakeId") private ExtendedCombinedReview fakeReview;

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
        return annotatedCombinedMovies.getMovieCount();
    }

    /**
     * @param index is the desired index.
     * @return a movie.
     */
    public Movie getAnAnnotatedMovie(int index) {
        return annotatedCombinedMovies.getMovie(index);
    }

    /**
     * It reads a review.
     */
    public void checkAnnotatedReview() {
        assertTrue(!StringUtils.isBlank(annotatedCombinedReview.title()));
        assertTrue(!StringUtils.isBlank(annotatedCombinedReview.score()));
        assertTrue(!StringUtils.isBlank(annotatedCombinedReview.info()));
        assertTrue(annotatedCombinedReview.getPoster() != null);
    }

    /**
     * It gets movie count.
     */
    public int getExtendeddMovieCount() {
        return extendedCombinedMovies.getMovieCount();
    }

    /**
     * @param index is the desired index.
     * @return a movie.
     */
    public Movie getAnExtendedMovie(int index) {
        return extendedCombinedMovies.getMovie(index);
    }

    /**
     * It reads a review.
     */
    public void checkExtendedReview() {
        assertTrue(!StringUtils.isBlank(extendedCombinedReview.title()));
        assertTrue(!StringUtils.isBlank(extendedCombinedReview.score()));
        assertTrue(!StringUtils.isBlank(extendedCombinedReview.info()));
        assertTrue(extendedCombinedReview.getPoster() != null);
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
