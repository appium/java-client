package io.appium.java_client.android;

import lombok.Data;
import lombok.experimental.Accessors;
import okhttp3.Interceptor;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * This is a simple POJO class to support the {@link StartsActivity}.
 */
@Accessors(chain = true)
@Data
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

}
