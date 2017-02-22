package io.appium.java_client.android;

/**
 * This is a simple POJO class to support the {@link StartsActivity}.
 */
public class Activity {
    private String appPackage;
    private String appActivity;
    private String appWaitPackage;
    private String appWaitActivity;
    private String intentAction;
    private String intentCategory;
    private String intentFlags;
    private String optionalIntentArguments;
    private boolean stopApp;

    public Activity(String appPackage, String appActivity) {
        this.appPackage = appPackage;
        this.appActivity = appActivity;
        this.stopApp = true;
    }

    /**
     * Gets the app package value.
     *
     * @return The app package value.
     */
    public String getAppPackage() {
        return appPackage;
    }

    /**
     * Sets the app package value.
     *
     * @param appPackage The app package value.
     */
    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    /**
     * Gets the app activity value.
     *
     * @return The app activity value.
     */
    public String getAppActivity() {
        return appActivity;
    }

    /**
     * Sets the app activity value.
     *
     * @param appActivity The app activity value.
     */
    public void setAppActivity(String appActivity) {
        this.appActivity = appActivity;
    }

    /**
     * Gets the app wait package value.
     *
     * @return The app wait package value.
     */
    public String getAppWaitPackage() {
        return appWaitPackage;
    }

    /**
     * Sets the app wait package value.
     *
     * @param appWaitPackage The app wait package value.
     */
    public void setAppWaitPackage(String appWaitPackage) {
        this.appWaitPackage = appWaitPackage;
    }

    /**
     * Gets the app wait activity value.
     *
     * @return The app wait activity value.
     */
    public String getAppWaitActivity() {
        return appWaitActivity;
    }

    /**
     * Sets the app wait activity value.
     *
     * @param appWaitActivity The app wait activity value.
     */
    public void setAppWaitActivity(String appWaitActivity) {
        this.appWaitActivity = appWaitActivity;
    }

    /**
     * Gets the intent action value.
     *
     * @return The intent action value.
     */
    public String getIntentAction() {
        return intentAction;
    }

    /**
     * Sets the intent action value.
     *
     * @param intentAction The intent action value.
     */
    public void setIntentAction(String intentAction) {
        this.intentAction = intentAction;
    }

    /**
     * Gets the intent category value.
     *
     * @return The intent category value.
     */
    public String getIntentCategory() {
        return intentCategory;
    }

    /**
     * Sets the intent category value.
     *
     * @param intentCategory The intent category value.
     */
    public void setIntentCategory(String intentCategory) {
        this.intentCategory = intentCategory;
    }

    /**
     * Gets the intent flags value.
     *
     * @return The intent flags value.
     */
    public String getIntentFlags() {
        return intentFlags;
    }

    /**
     * Sets the intent flags value.
     *
     * @param intentFlags The intent flags value.
     */
    public void setIntentFlags(String intentFlags) {
        this.intentFlags = intentFlags;
    }

    /**
     * Gets the optional intent arguments value.
     *
     * @return The optional intent arguments value.
     */
    public String getOptionalIntentArguments() {
        return optionalIntentArguments;
    }

    /**
     * Sets the optional intent arguments value.
     *
     * @param optionalIntentArguments The optional intent arguments value.
     */
    public void setOptionalIntentArguments(String optionalIntentArguments) {
        this.optionalIntentArguments = optionalIntentArguments;
    }

    /**
     * Gets the stop app value.
     *
     * @return The stop app value.
     */
    public boolean isStopApp() {
        return stopApp;
    }

    /**
     * Sets the stop app value.
     *
     * @param stopApp The stop app value.
     */
    public void setStopApp(boolean stopApp) {
        this.stopApp = stopApp;
    }

    @Override public String toString() {
        return "Activity{" + "appPackage='" + appPackage + '\'' + ", appActivity='" + appActivity
            + '\'' + ", appWaitPackage='" + appWaitPackage + '\'' + ", appWaitActivity='"
            + appWaitActivity + '\'' + ", intentAction='" + intentAction + '\''
            + ", intentCategory='" + intentCategory + '\'' + ", intentFlags='" + intentFlags + '\''
            + ", optionalIntentArguments='" + optionalIntentArguments + '\'' + ", stopApp="
            + stopApp + '}';
    }
}
