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
     *     Activity activity = new Activity("app package goes here", "app activity goes here");
     *     activity.setWaitAppPackage("app wait package goes here");
     *     activity.setWaitAppActivity("app wait activity goes here");
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
     * Get the current activity being run on the mobile device.
     *
     * @return a current activity being run on the mobile device.
     */
    default String currentActivity() {
        return CommandExecutionHelper.execute(this, currentActivityCommand());
    }
}
