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


/**
 * This enum is deprecated. Was moved to
 * {@link io.appium.java_client.android.Setting}.
 */
@Deprecated
public enum AppiumSetting {

    IGNORE_UNIMPORTANT_VIEWS("ignoreUnimportantViews");
    private String name;

    private AppiumSetting(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

}
