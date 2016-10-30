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

public enum MobileSelector {
    ACCESSIBILITY("accessibility id"),
    ANDROID_UI_AUTOMATOR("-android uiautomator"),
    IOS_UI_AUTOMATION("-ios uiautomation"),
    IOS_PREDICATE_STRING("-ios predicate string"),
    WINDOWS_UI_AUTOMATION("-windows uiautomation");

    private final String selector;

    MobileSelector(String selector) {
        this.selector = selector;
    }

    @Override public String toString() {
        return selector;
    }
}
