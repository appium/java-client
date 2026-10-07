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

package io.appium.java_client.appmanagement;

import java.util.Arrays;

/**
 * Possible states of an application on the device under test.
 */
public enum ApplicationState {
    /** The application is not installed. */
    NOT_INSTALLED,
    /** The application is installed, but not running. */
    NOT_RUNNING,
    /** The application is running in the background and is suspended. */
    RUNNING_IN_BACKGROUND_SUSPENDED,
    /** The application is running in the background. */
    RUNNING_IN_BACKGROUND,
    /** The application is running in the foreground. */
    RUNNING_IN_FOREGROUND;

    /**
     * Creates {@link ApplicationState} instance based on the code.
     *
     * @param code the code received from state querying endpoint.
     * @return {@link ApplicationState} instance.
     */
    public static ApplicationState ofCode(long code) {
        return Arrays.stream(ApplicationState.values())
                .filter(x -> code == x.ordinal())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Application state %s is unknown", code))
                );
    }
}
