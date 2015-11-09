package io.appium.java_client.pagefactory_tests.widgets;


import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;
import io.appium.java_client.pagefactory.OverrideWidget;
import io.appium.java_client.pagefactory.SelendroidFindBy;
import io.appium.java_client.pagefactory_tests.widgets.android.annotated.AnnotatedAndroidMovies;
import io.appium.java_client.pagefactory_tests.widgets.android.annotated.AnnotatedAndroidReview;
import io.appium.java_client.pagefactory_tests.widgets.android.extended.ExtendedAndroidMovies;
import io.appium.java_client.pagefactory_tests.widgets.android.extended.ExtendedAndroidReview;
import io.appium.java_client.pagefactory_tests.widgets.android.simple.AndroidMovies;
import io.appium.java_client.pagefactory_tests.widgets.android.simple.AndroidReview;
import io.appium.java_client.pagefactory_tests.widgets.html.annotated.AnnotatedHtmlMovies;
import io.appium.java_client.pagefactory_tests.widgets.html.annotated.AnnotatedHtmlReview;
import io.appium.java_client.pagefactory_tests.widgets.html.extended.ExtendedHtmlMovies;
import io.appium.java_client.pagefactory_tests.widgets.html.extended.ExtendedHtmlReview;
import io.appium.java_client.pagefactory_tests.widgets.html.simple.HtmlMovies;
import io.appium.java_client.pagefactory_tests.widgets.html.simple.HtmlReview;
import io.appium.java_client.pagefactory_tests.widgets.selendroid.annotated.AnnotatedSelendroidMovies;
import io.appium.java_client.pagefactory_tests.widgets.selendroid.annotated.AnnotatedSelendroidReview;
import io.appium.java_client.pagefactory_tests.widgets.selendroid.extended.ExtendedSelendroidMovies;
import io.appium.java_client.pagefactory_tests.widgets.selendroid.extended.ExtendedSelendroidReview;
import io.appium.java_client.pagefactory_tests.widgets.selendroid.simple.SelendroidMovies;
import io.appium.java_client.pagefactory_tests.widgets.selendroid.simple.SelendroidReview;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.support.FindBy;

import static junit.framework.Assert.assertTrue;

public class RottenTomatoes {

    @FindBy(id = "movies-collection")
    @AndroidFindBy(id = "com.codepath.example.rottentomatoes:id/lvMovies")
    @SelendroidFindBy(id = "lvMovies")
    @OverrideWidget(html = HtmlMovies.class, selendroid = SelendroidMovies.class,
    androidUIAutomator = AndroidMovies.class)
    private Movies simpleMovies;


    @AndroidFindBys({@AndroidFindBy(id = "android:id/content"),
            @AndroidFindBy(className = "android.widget.RelativeLayout")})
    @FindBy(id = "main_container")
    @SelendroidFindBy(className = "android.widget.RelativeLayout")
    @OverrideWidget(html = HtmlReview.class, selendroid = SelendroidReview.class,
            androidUIAutomator = AndroidReview.class)
    private Review simpleReview;

    @OverrideWidget(html = AnnotatedHtmlMovies.class, selendroid = AnnotatedSelendroidMovies.class,
    androidUIAutomator = AnnotatedAndroidMovies.class)
    private Movies annotatedMovies;

    @OverrideWidget(html = AnnotatedHtmlReview.class, selendroid = AnnotatedSelendroidReview.class,
            androidUIAutomator = AnnotatedAndroidReview.class)
    private Review annotatedReview;

    @OverrideWidget(html = ExtendedHtmlMovies.class, selendroid = ExtendedSelendroidMovies.class,
            androidUIAutomator = ExtendedAndroidMovies.class)
    private Movies extendedMovies;

    @OverrideWidget(html = ExtendedHtmlReview.class, selendroid = ExtendedSelendroidReview.class,
            androidUIAutomator = ExtendedAndroidReview.class)
    private Review extendedReview;


    @FindBy(id = "Fake_ID_For_All_Platforms")
    @AndroidFindBy(id = "Fake_ID_For_All_Platforms")
    @SelendroidFindBy(id = "Fake_ID_For_All_Platforms")
    @OverrideWidget(html = ExtendedHtmlMovies.class, selendroid = ExtendedSelendroidMovies.class,
            androidUIAutomator = ExtendedAndroidMovies.class)
    private Movies fakeMovies;

    @FindBy(id = "Fake_ID_For_All_Platforms")
    @AndroidFindBy(id = "Fake_ID_For_All_Platforms")
    @SelendroidFindBy(id = "Fake_ID_For_All_Platforms")
    @OverrideWidget(html = ExtendedHtmlReview.class, selendroid = ExtendedSelendroidReview.class,
            androidUIAutomator = ExtendedAndroidReview.class)
    private Review fakeReview;



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
        return annotatedMovies.getMovieCount();
    }

    public Movie getAnAnnotatedMovie(int index){
        return annotatedMovies.getMovie(index);
    }

    public void checkAnnotatedReviev(){
        assertTrue(!StringUtils.isBlank(annotatedReview.title()));
        assertTrue(!StringUtils.isBlank(annotatedReview.score()));
        assertTrue(!StringUtils.isBlank(annotatedReview.info()));
        assertTrue(annotatedReview.getPoster() != null);
    }
    /////////////////////////////////////////////////////////

    public int getExtendeddMovieCount(){
        return extendedMovies.getMovieCount();
    }

    public Movie getAnExtendedMovie(int index){
        return extendedMovies.getMovie(index);
    }

    public void checkExtendedReviev(){
        assertTrue(!StringUtils.isBlank(extendedReview.title()));
        assertTrue(!StringUtils.isBlank(extendedReview.score()));
        assertTrue(!StringUtils.isBlank(extendedReview.info()));
        assertTrue(extendedReview.getPoster() != null);
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
