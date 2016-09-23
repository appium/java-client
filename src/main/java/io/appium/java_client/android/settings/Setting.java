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

package io.appium.java_client.android.settings;

/**
 * Enums defining constants for Appium Settings which can be set and toggled during a test session.
 */
public enum Setting {

    IGNORE_UNIMPORTANT_VIEWS("ignoreUnimportantViews");

    private String name;

    Setting(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    /**
     * Contains possible values of the `ignoreUnimportantViews` setting.
     * It helps to define whether Android devices should use `setCompressedLayoutHeirarchy()`
     * which ignores all views that are marked IMPORTANT_FOR_ACCESSIBILITY_NO
     * or IMPORTANT_FOR_ACCESSIBILITY_AUTO (and have been deemed not important
     * by the system), in an attempt to make things less confusing or faster.
     */
    public static enum IgnoreUnimportantViews {
        YES(true),
        NO(false);

        private final Boolean ignore;

        IgnoreUnimportantViews(Boolean ignore) {
            this.ignore = ignore;
        }

        /**
         * @return value which means to ignore unimportant views if true,
         * doesn't ignore otherwise.
         */
        public Boolean value() {
            return ignore;
        }
    }

}
