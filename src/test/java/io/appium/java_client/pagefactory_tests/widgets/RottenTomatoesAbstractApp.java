package io.appium.java_client.pagefactory_tests.widgets;

public interface RottenTomatoesAbstractApp {

    /**
     * It gets movie count.
     */
    int getSimpleMovieCount();

    /**
     * @param index is the desired index.
     * @return a movie.
     */
    Movie getASimpleMovie(int index);

    /**
     * It reads a review.
     */
    void checkSimpleReview();

    /**
     * It gets movie count.
     */
    int getAnnotatedMovieCount();

    /**
     * @param index is the desired index.
     * @return a movie.
     */
    Movie getAnAnnotatedMovie(int index);

    /**
     * It reads a review.
     */
    void checkAnnotatedReview();

    /**
     * It gets movie count.
     */
    int getExtendeddMovieCount();

    /**
     * @param index is the desired index.
     * @return a movie.
     */
    Movie getAnExtendedMovie(int index);

    /**
     * It reads a review.
     */
    void checkExtendedReview();

    /**
     * It gets movie count.
     */
    int getFakedMovieCount();

    /**
     * It reads a review.
     */
    void checkFakeReview();
}
