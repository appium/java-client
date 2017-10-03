package io.appium.java_client.android;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * This is a simple POJO class to support the {@link StartsActivity}.
 */
public class Activity {
    private final String appPackage;
    private final String appActivity;
    private String appWaitPackage;
    private String appWaitActivity;
    private String intentAction;
    private String intentCategory;
    private String intentFlags;
    private String optionalIntentArguments;
    private boolean stopApp;

    /**
     * A constructor with two mandatory parameters.
     *
     * @param appPackage  The value for the app package.
     * @param appActivity The value for the app activity.
     */
    public Activity(String appPackage, String appActivity) {
        checkArgument(!isBlank(appPackage),
            "App package should be defined as not empty or null string");
        checkArgument(!isBlank(appActivity),
            "App activity should be defined as not empty or null string");
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
     * Gets the app activity value.
     *
     * @return The app activity value.
     */
    public String getAppActivity() {
        return appActivity;
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
     * @return self reference
     */
    public Activity setAppWaitPackage(String appWaitPackage) {
        this.appWaitPackage = appWaitPackage;
        return this;
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
     * @return self reference
     */
    public Activity setAppWaitActivity(String appWaitActivity) {
        this.appWaitActivity = appWaitActivity;
        return this;
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
     * @return self reference
     */
    public Activity setIntentAction(String intentAction) {
        this.intentAction = intentAction;
        return this;
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
     * @return self reference
     */
    public Activity setIntentCategory(String intentCategory) {
        this.intentCategory = intentCategory;
        return this;
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
     * @return self reference
     */
    public Activity setIntentFlags(String intentFlags) {
        this.intentFlags = intentFlags;
        return this;
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
     * @return self reference
     */
    public Activity setOptionalIntentArguments(String optionalIntentArguments) {
        this.optionalIntentArguments = optionalIntentArguments;
        return this;
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
     * @return self reference
     */
    public Activity setStopApp(boolean stopApp) {
        this.stopApp = stopApp;
        return this;
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
