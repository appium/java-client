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

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.CanRememberExtensionPresence;
import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;
import org.openqa.selenium.UnsupportedCommandException;

import javax.annotation.Nullable;

import java.util.AbstractMap;

import static io.appium.java_client.MobileCommand.CURRENT_ACTIVITY;
import static io.appium.java_client.MobileCommand.GET_CURRENT_PACKAGE;
import static io.appium.java_client.android.AndroidMobileCommandHelper.startActivityCommand;

public interface StartsActivity extends ExecutesMethod, CanRememberExtensionPresence {
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
     * @deprecated Use 'mobile: startActivity' extension instead
     */
    @Deprecated
    default void startActivity(Activity activity) {
        CommandExecutionHelper.execute(this,
                startActivityCommand(activity.getAppPackage(), activity.getAppActivity(),
                        activity.getAppWaitPackage(), activity.getAppWaitActivity(),
                        activity.getIntentAction(), activity.getIntentCategory(), activity.getIntentFlags(),
                        activity.getOptionalIntentArguments(), activity.isStopApp())
        );
    }

    /**
     * Get the current activity being run on the mobile device.
     *
     * @return a current activity being run on the mobile device.
     */
    @Nullable
    default String currentActivity() {
        final String extName = "mobile: getCurrentActivity";
        try {
            return CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName);
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            return CommandExecutionHelper.execute(
                    markExtensionAbsence(extName),
                    new AbstractMap.SimpleEntry<>(CURRENT_ACTIVITY, ImmutableMap.of())
            );
        }
    }

    /**
     * Get the current package being run on the mobile device.
     *
     * @return a current package being run on the mobile device.
     */
    @Nullable
    default String getCurrentPackage() {
        final String extName = "mobile: getCurrentPackage";
        try {
            return CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName);
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            return CommandExecutionHelper.execute(
                    markExtensionAbsence(extName),
                    new AbstractMap.SimpleEntry<>(GET_CURRENT_PACKAGE, ImmutableMap.of())
            );
        }
    }
}
