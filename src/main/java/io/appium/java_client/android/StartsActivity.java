/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.appium.java_client.android;

import static io.appium.java_client.android.AndroidMobileCommandHelper.currentActivityCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.startActivityCommand;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;

public interface StartsActivity extends ExecutesMethod {
    /**
     * This method should start arbitrary activity during a test. If the activity belongs to
     * another application, that application is started and the activity is opened.
     * <p>
     * Usage:
     * </p>
     * <pre>
     *     {@code
     *     Activity activity = new Activity();
     *     activity.setAppPackage("com.foo");
     *     activity.setAppActivity(".bar");
     *     driver.startActivity(activity);
     *     }
     * </pre>
     *
     * @param activity The {@link Activity} object
     */
    default void startActivity(Activity activity) {
        CommandExecutionHelper.execute(this,
            startActivityCommand(activity.getAppPackage(), activity.getAppActivity(),
                activity.getAppWaitPackage(), activity.getAppWaitActivity(),
                activity.getIntentAction(), activity.getIntentCategory(), activity.getIntentFlags(),
                activity.getOptionalIntentArguments(), activity.isStopApp()));
    }

    /**
     * This method should start arbitrary activity during a test. If the activity belongs to
     * another application, that application is started and the activity is opened.
     *
     * @param appPackage      The package containing the activity. [Required]
     * @param appActivity     The activity to start. [Required]
     * @param appWaitPackage  Automation will begin after this package starts. [Optional]
     * @param appWaitActivity Automation will begin after this activity starts. [Optional]
     * @param stopApp         If true, target app will be stopped. [Optional]
     * @deprecated Instead use {@link #startActivity(Activity)}
     */
    @Deprecated
    default void startActivity(String appPackage, String appActivity, String appWaitPackage,
        String appWaitActivity, boolean stopApp) throws IllegalArgumentException {
        this.startActivity(appPackage,appActivity,appWaitPackage,
            appWaitActivity,null,null,null,null,stopApp);
    }

    /**
     * This method should start arbitrary activity during a test. If the activity belongs to
     * another application, that application is started and the activity is opened.
     *
     * @param appPackage      The package containing the activity. [Required]
     * @param appActivity     The activity to start. [Required]
     * @param appWaitPackage  Automation will begin after this package starts. [Optional]
     * @param appWaitActivity Automation will begin after this activity starts. [Optional]
     * @deprecated Instead use {@link #startActivity(Activity)}
     */
    @Deprecated
    default void startActivity(String appPackage, String appActivity, String appWaitPackage,
        String appWaitActivity) throws IllegalArgumentException {
        this.startActivity(appPackage, appActivity,
            appWaitPackage, appWaitActivity,null,null,null,null,true);
    }

    /**
     * This method should start arbitrary activity during a test. If the activity belongs to
     * another application, that application is started and the activity is opened.
     *
     * @param appPackage  The package containing the activity. [Required]
     * @param appActivity The activity to start. [Required]
     * @deprecated Instead use {@link #startActivity(Activity)}
     */
    @Deprecated
    default void startActivity(String appPackage, String appActivity) throws IllegalArgumentException {
        this.startActivity(appPackage, appActivity, null, null,
            null,null,null,null,true);
    }

    /**
     * This method should start arbitrary activity during a test. If the activity belongs to
     * another application, that application is started and the activity is opened.
     *
     * @param appPackage      The package containing the activity. [Required]
     * @param appActivity     The activity to start. [Required]
     * @param appWaitPackage  Automation will begin after this package starts. [Optional]
     * @param appWaitActivity Automation will begin after this activity starts. [Optional]
     * @param intentAction  Intent action which will be used to start activity [Optional]
     * @param intentCategory  Intent category which will be used to start activity [Optional]
     * @param intentFlags  Flags that will be used to start activity [Optional]
     * @param intentOptionalArgs Additional intent arguments that will be used to
     *                                start activity [Optional]
     * @deprecated Instead use {@link #startActivity(Activity)}
     */
    @Deprecated
    default void startActivity(String appPackage, String appActivity,
        String appWaitPackage, String appWaitActivity,
        String intentAction, String intentCategory,
        String intentFlags, String intentOptionalArgs)
        throws IllegalArgumentException {
        this.startActivity(appPackage,appActivity,
            appWaitPackage,appWaitActivity,
            intentAction,intentCategory,intentFlags,intentOptionalArgs,true);
    }

    /**
     * This method should start arbitrary activity during a test. If the activity belongs to
     * another application, that application is started and the activity is opened.
     *
     * @param appPackage      The package containing the activity. [Required]
     * @param appActivity     The activity to start. [Required]
     * @param appWaitPackage  Automation will begin after this package starts. [Optional]
     * @param appWaitActivity Automation will begin after this activity starts. [Optional]
     * @param intentAction  Intent action which will be used to start activity [Optional]
     * @param intentCategory  Intent category which will be used to start activity [Optional]
     * @param intentFlags  Flags that will be used to start activity [Optional]
     * @param optionalIntentArguments Additional intent arguments that will be used to
     *                                start activity [Optional]
     * @param stopApp         If true, target app will be stopped. [Optional]
     * @deprecated Instead use {@link #startActivity(Activity)}
     */
    @Deprecated
    default void startActivity(String appPackage, String appActivity, String appWaitPackage,
        String appWaitActivity, String intentAction,
        String intentCategory, String intentFlags,
        String optionalIntentArguments,boolean stopApp )
        throws IllegalArgumentException {
        CommandExecutionHelper.execute(this, startActivityCommand(appPackage, appActivity,
            appWaitPackage, appWaitActivity, intentAction, intentCategory, intentFlags,
            optionalIntentArguments, stopApp));
    }

    /**
     * Get the current activity being run on the mobile device.
     *
     * @return a current activity being run on the mobile device.
     */
    default String currentActivity() {
        return CommandExecutionHelper.execute(this, currentActivityCommand());
    }
}
