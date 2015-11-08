package io.appium.java_client.pagefactory_tests.widgets.html;

import io.appium.java_client.pagefactory_tests.widgets.Movie;
import io.appium.java_client.pagefactory_tests.widgets.html.annotated.AnnotatedHtmlMovies;
import io.appium.java_client.pagefactory_tests.widgets.html.annotated.AnnotatedHtmlReview;
import io.appium.java_client.pagefactory_tests.widgets.html.extended.ExtendedHtmlMovies;
import io.appium.java_client.pagefactory_tests.widgets.html.extended.ExtendedHtmlReview;
import io.appium.java_client.pagefactory_tests.widgets.html.simple.HtmlMovies;
import io.appium.java_client.pagefactory_tests.widgets.html.simple.HtmlReview;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.support.FindBy;

import static junit.framework.Assert.assertTrue;

/**
 * This is the example of page object with declared Widgets
 * instead of WebElement
 */
public class RottenTomatoesSite {

    @FindBy(id = "movies-collection")
    private HtmlMovies simpleMovies;

    @FindBy(id = "main_container")
    private HtmlReview simpleReview;

    private AnnotatedHtmlMovies annotatedHtmlMovies;

    private AnnotatedHtmlReview annotatedHtmlReview;

    private ExtendedHtmlMovies extendedHtmlMovies;

    private ExtendedHtmlReview extendedHtmlReview;

    @FindBy(id = "fakeId")
    private ExtendedHtmlMovies fakeMovies;

    @FindBy(id = "fakeId")
    private ExtendedHtmlReview fakeReview;


    public int getSimpleMovieCount(){
        return simpleMovies.getMovieCount();
    }

    public Movie getASimpleMovie(int index){
        return simpleMovies.getMovie(index);
    }

    public void checkSimpleReviev(){
        assertTrue(!StringUtils.isBlank(simpleReview.title()));
        assertTrue(!StringUtils.isBlank(simpleReview.score()));
        assertTrue(!StringUtils.isBlank(simpleReview.info()));
        assertTrue(simpleReview.getPoster() != null);
    }

    /////////////////////////////////////////////////////////
    public int getAnnotatedMovieCount(){
        return annotatedHtmlMovies.getMovieCount();
    }

    public Movie getAnAnnotatedMovie(int index){
        return annotatedHtmlMovies.getMovie(index);
    }

    public void checkAnnotatedReviev(){
        assertTrue(!StringUtils.isBlank(annotatedHtmlReview.title()));
        assertTrue(!StringUtils.isBlank(annotatedHtmlReview.score()));
        assertTrue(!StringUtils.isBlank(annotatedHtmlReview.info()));
        assertTrue(annotatedHtmlReview.getPoster() != null);
    }
    /////////////////////////////////////////////////////////

    public int getExtendeddMovieCount(){
        return extendedHtmlMovies.getMovieCount();
    }

    public Movie getAnExtendedMovie(int index){
        return extendedHtmlMovies.getMovie(index);
    }

    public void checkExtendedReviev(){
        assertTrue(!StringUtils.isBlank(extendedHtmlReview.title()));
        assertTrue(!StringUtils.isBlank(extendedHtmlReview.score()));
        assertTrue(!StringUtils.isBlank(extendedHtmlReview.info()));
        assertTrue(extendedHtmlReview.getPoster() != null);
    }

    /////////////////////////////////////////////////////////

    public int getFakedMovieCount(){
        return fakeMovies.getMovieCount();
    }

    public void checkFakeReviev(){
        assertTrue(!StringUtils.isBlank(fakeReview.title()));
        assertTrue(!StringUtils.isBlank(fakeReview.score()));
        assertTrue(!StringUtils.isBlank(fakeReview.info()));
        assertTrue(fakeReview.getPoster() != null);
    }
}
