package io.appium.java_client.pagefactory_tests.widgets.selendroid;

import io.appium.java_client.pagefactory.SelendroidFindBy;
import io.appium.java_client.pagefactory.SelendroidFindBys;
import io.appium.java_client.pagefactory_tests.widgets.Movie;
import io.appium.java_client.pagefactory_tests.widgets.selendroid.annotated.AnnotatedSelendroidMovies;
import io.appium.java_client.pagefactory_tests.widgets.selendroid.annotated.AnnotatedSelendroidReview;
import io.appium.java_client.pagefactory_tests.widgets.selendroid.extended.ExtendedSelendroidMovies;
import io.appium.java_client.pagefactory_tests.widgets.selendroid.extended.ExtendedSelendroidReview;
import io.appium.java_client.pagefactory_tests.widgets.selendroid.simple.SelendroidMovies;
import io.appium.java_client.pagefactory_tests.widgets.selendroid.simple.SelendroidReview;
import org.apache.commons.lang3.StringUtils;

import static junit.framework.Assert.assertTrue;

/**
 * This is the example of page object with declared Widgets
 * instead of WebElement
 */
public class RottenTomatoesSelendroidApp {

    @SelendroidFindBy(id = "lvMovies")
    private SelendroidMovies simpleMovies;

    @SelendroidFindBy(className = "android.widget.RelativeLayout")
    private SelendroidReview simpleReview;

    private AnnotatedSelendroidMovies annotatedSelendroidMovies;

    private AnnotatedSelendroidReview annotatedSelendroidReview;

    private ExtendedSelendroidMovies extendedSelendroidMovies;

    private ExtendedSelendroidReview extendedSelendroidReview;

    @SelendroidFindBy(id = "fakeId")
    private ExtendedSelendroidMovies fakeMovies;

    @SelendroidFindBy(id = "fakeId")
    private ExtendedSelendroidReview fakeReview;


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
        return annotatedSelendroidMovies.getMovieCount();
    }

    public Movie getAnAnnotatedMovie(int index){
        return annotatedSelendroidMovies.getMovie(index);
    }

    public void checkAnnotatedReviev(){
        assertTrue(!StringUtils.isBlank(annotatedSelendroidReview.title()));
        assertTrue(!StringUtils.isBlank(annotatedSelendroidReview.score()));
        assertTrue(!StringUtils.isBlank(annotatedSelendroidReview.info()));
        assertTrue(annotatedSelendroidReview.getPoster() != null);
    }
    /////////////////////////////////////////////////////////

    public int getExtendeddMovieCount(){
        return extendedSelendroidMovies.getMovieCount();
    }

    public Movie getAnExtendedMovie(int index){
        return extendedSelendroidMovies.getMovie(index);
    }

    public void checkExtendedReviev(){
        assertTrue(!StringUtils.isBlank(extendedSelendroidReview.title()));
        assertTrue(!StringUtils.isBlank(extendedSelendroidReview.score()));
        assertTrue(!StringUtils.isBlank(extendedSelendroidReview.info()));
        assertTrue(extendedSelendroidReview.getPoster() != null);
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
