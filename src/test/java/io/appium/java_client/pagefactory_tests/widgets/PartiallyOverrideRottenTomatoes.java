package io.appium.java_client.pagefactory_tests.widgets;

import static org.junit.Assert.assertTrue;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;
import io.appium.java_client.pagefactory.OverrideWidget;
import io.appium.java_client.pagefactory_tests.widgets.html.annotated.AnnotatedHtmlMovies;
import io.appium.java_client.pagefactory_tests.widgets.html.annotated.AnnotatedHtmlReview;
import io.appium.java_client.pagefactory_tests.widgets.html.extended.ExtendedHtmlMovies;
import io.appium.java_client.pagefactory_tests.widgets.html.extended.ExtendedHtmlReview;
import io.appium.java_client.pagefactory_tests.widgets.html.simple.HtmlMovies;
import io.appium.java_client.pagefactory_tests.widgets.html.simple.HtmlReview;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.support.FindBy;

public class PartiallyOverrideRottenTomatoes implements RottenTomatoesAbstractApp {

    @AndroidFindBy(id = "com.codepath.example.rottentomatoes:id/lvMovies")
    @FindBy(id = "movies-collection")
    @OverrideWidget(html = HtmlMovies.class)
    private Movies simpleMovies;

    @FindBy(id = "main_container")
    @AndroidFindBys({@AndroidFindBy(id = "android:id/content"),
            @AndroidFindBy(className = "android.widget.RelativeLayout")})
    @OverrideWidget(html = HtmlReview.class)
    private Review simpleReview;

    @OverrideWidget(html = AnnotatedHtmlMovies.class)
    private Movies annotatedMovies;

    @OverrideWidget(html = AnnotatedHtmlReview.class)
    private Review annotatedReview;

    @OverrideWidget(html = ExtendedHtmlMovies.class)
    private Movies extendedMovies;

    @OverrideWidget(html = ExtendedHtmlReview.class)
    private Review extendedReview;


    @FindBy(id = "Fake_ID_For_All_Platforms")
    @OverrideWidget(html = ExtendedHtmlMovies.class)
    private Movies fakeMovies;

    @FindBy(id = "Fake_ID_For_All_Platforms")
    @OverrideWidget(html = ExtendedHtmlReview.class)
    private Review fakeReview;

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
        return annotatedMovies.getMovieCount();
    }

    /**
     * @param index is the desired index.
     * @return a movie.
     */
    public Movie getAnAnnotatedMovie(int index) {
        return annotatedMovies.getMovie(index);
    }

    /**
     * It reads a review.
     */
    public void checkAnnotatedReview() {
        assertTrue(!StringUtils.isBlank(annotatedReview.title()));
        assertTrue(!StringUtils.isBlank(annotatedReview.score()));
        assertTrue(!StringUtils.isBlank(annotatedReview.info()));
        assertTrue(annotatedReview.getPoster() != null);
    }

    /**
     * It gets movie count.
     */
    public int getExtendeddMovieCount() {
        return extendedMovies.getMovieCount();
    }

    /**
     * @param index is the desired index.
     * @return a movie.
     */
    public Movie getAnExtendedMovie(int index) {
        return extendedMovies.getMovie(index);
    }

    /**
     * It reads a review.
     */
    public void checkExtendedReview() {
        assertTrue(!StringUtils.isBlank(extendedReview.title()));
        assertTrue(!StringUtils.isBlank(extendedReview.score()));
        assertTrue(!StringUtils.isBlank(extendedReview.info()));
        assertTrue(extendedReview.getPoster() != null);
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
