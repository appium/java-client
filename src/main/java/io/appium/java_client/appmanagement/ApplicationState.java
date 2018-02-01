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

public enum ApplicationState {
    NOT_INSTALLED(0), NOT_RUNNING(1), RUNNING_IN_BACKGROUND_SUSPENDED(2),
    RUNNING_IN_BACKGROUND(3), RUNNING_IN_FOREGROUND(4);

    private int code;

    ApplicationState(int code) {
        this.code = code;
    }

    /**
     * Creates {@link ApplicationState} instance based on the code.
     *
     * @param code the code received from state querying endpoint.
     * @return {@link ApplicationState} instance.
     */
    public static ApplicationState ofCode(int code) {
        return Arrays.stream(ApplicationState.values())
                .filter(x -> code == x.code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Application state %s is unknown", code))
                );
    }
}
