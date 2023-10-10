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

public interface StartsActivity extends ExecutesMethod, CanRememberExtensionPresence {
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
