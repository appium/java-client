package io.appium.java_client.pagefactory_tests.widgets;

import static org.junit.Assert.assertTrue;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;
import io.appium.java_client.pagefactory.OverrideWidget;
import io.appium.java_client.pagefactory.SelendroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;

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
import io.appium.java_client.pagefactory_tests.widgets.ios.annotated.AnnotatedIOSMovies;
import io.appium.java_client.pagefactory_tests.widgets.ios.annotated.AnnotatedIOSReview;
import io.appium.java_client.pagefactory_tests.widgets.ios.extended.ExtendedIOSMovies;
import io.appium.java_client.pagefactory_tests.widgets.ios.extended.ExtendedIOSReview;
import io.appium.java_client.pagefactory_tests.widgets.ios.simple.IOSMovies;
import io.appium.java_client.pagefactory_tests.widgets.ios.simple.IOSReview;
import io.appium.java_client.pagefactory_tests.widgets.selendroid.annotated.AnnotatedSelendroidMovies;
import io.appium.java_client.pagefactory_tests.widgets.selendroid.annotated.AnnotatedSelendroidReview;
import io.appium.java_client.pagefactory_tests.widgets.selendroid.extended.ExtendedSelendroidMovies;
import io.appium.java_client.pagefactory_tests.widgets.selendroid.extended.ExtendedSelendroidReview;
import io.appium.java_client.pagefactory_tests.widgets.selendroid.simple.SelendroidMovies;
import io.appium.java_client.pagefactory_tests.widgets.selendroid.simple.SelendroidReview;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.support.FindBy;

public class RottenTomatoes implements RottenTomatoesAbstractApp {

    @FindBy(id = "movies-collection")
    @AndroidFindBy(id = "com.codepath.example.rottentomatoes:id/lvMovies")
    @SelendroidFindBy(id = "lvMovies") @iOSFindBy(className = "UIATableView")
    @OverrideWidget(html = HtmlMovies.class, selendroid = SelendroidMovies.class,
        androidUIAutomator = AndroidMovies.class,
        iOSUIAutomation = IOSMovies.class) private Movies
        simpleMovies;


    @AndroidFindBys({@AndroidFindBy(id = "android:id/content"),
        @AndroidFindBy(className = "android.widget.RelativeLayout")})
    @FindBy(id = "main_container")
    @SelendroidFindBy(className = "android.widget.RelativeLayout")
    @iOSFindBy(className = "UIAWindow")
    @OverrideWidget(html = HtmlReview.class, selendroid = SelendroidReview.class,
        androidUIAutomator = AndroidReview.class,
        iOSUIAutomation = IOSReview.class) private Review
        simpleReview;

    @OverrideWidget(html = AnnotatedHtmlMovies.class,
        selendroid = AnnotatedSelendroidMovies.class,
        androidUIAutomator = AnnotatedAndroidMovies.class,
        iOSUIAutomation = AnnotatedIOSMovies.class)
    private Movies annotatedMovies;

    @OverrideWidget(html = AnnotatedHtmlReview.class,
        selendroid = AnnotatedSelendroidReview.class,
        androidUIAutomator = AnnotatedAndroidReview.class,
        iOSUIAutomation = AnnotatedIOSReview.class)
    private Review annotatedReview;

    @OverrideWidget(html = ExtendedHtmlMovies.class,
        selendroid = ExtendedSelendroidMovies.class,
        androidUIAutomator = ExtendedAndroidMovies.class,
        iOSUIAutomation = ExtendedIOSMovies.class)
    private Movies extendedMovies;

    @OverrideWidget(html = ExtendedHtmlReview.class,
        selendroid = ExtendedSelendroidReview.class,
        androidUIAutomator = ExtendedAndroidReview.class,
        iOSUIAutomation = ExtendedIOSReview.class)
    private Review extendedReview;


    @FindBy(id = "Fake_ID_For_All_Platforms") @AndroidFindBy(id = "Fake_ID_For_All_Platforms")
    @SelendroidFindBy(id = "Fake_ID_For_All_Platforms")
    @iOSFindBy(id = "Fake_ID_For_All_Platforms")
    @OverrideWidget(html = ExtendedHtmlMovies.class,
        selendroid = ExtendedSelendroidMovies.class,
        androidUIAutomator = ExtendedAndroidMovies.class,
        iOSUIAutomation = ExtendedIOSMovies.class)
    private Movies fakeMovies;

    @FindBy(id = "Fake_ID_For_All_Platforms")
    @AndroidFindBy(id = "Fake_ID_For_All_Platforms")
    @SelendroidFindBy(id = "Fake_ID_For_All_Platforms")
    @iOSFindBy(id = "Fake_ID_For_All_Platforms")
    @OverrideWidget(html = ExtendedHtmlReview.class,
        selendroid = ExtendedSelendroidReview.class,
        androidUIAutomator = ExtendedAndroidReview.class,
        iOSUIAutomation = ExtendedIOSReview.class)
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
