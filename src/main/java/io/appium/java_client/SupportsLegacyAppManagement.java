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

package io.appium.java_client;

import static io.appium.java_client.MobileCommand.CLOSE_APP;
import static io.appium.java_client.MobileCommand.LAUNCH_APP;
import static io.appium.java_client.MobileCommand.RESET;

@Deprecated
public interface SupportsLegacyAppManagement extends ExecutesMethod {
    /**
     * Launches the app, which was provided in the capabilities at session creation,
     * and (re)starts the session.
     *
     * @deprecated This method is deprecated and will be removed.
     *     See https://github.com/appium/appium/issues/15807
     */
    @Deprecated
    default void launchApp() {
        execute(LAUNCH_APP);
    }

    /**
     * Resets the currently running app together with the session.
     *
     * @deprecated This method is deprecated and will be removed.
     *     See https://github.com/appium/appium/issues/15807
     */
    @Deprecated
    default void resetApp() {
        execute(RESET);
    }

    /**
     * Close the app which was provided in the capabilities at session creation
     * and quits the session.
     *
     * @deprecated This method is deprecated and will be removed.
     *     See https://github.com/appium/appium/issues/15807
     */
    @Deprecated
    default void closeApp() {
        execute(CLOSE_APP);
    }
}
