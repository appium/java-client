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

import static io.appium.java_client.android.AndroidMobileCommandHelper.replaceElementValueCommand;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.FindsByAndroidDataMatcher;
import io.appium.java_client.FindsByAndroidViewMatcher;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.FindsByAndroidViewTag;
import io.appium.java_client.MobileElement;

public class AndroidElement extends MobileElement
    implements FindsByAndroidUIAutomator<MobileElement>, FindsByAndroidDataMatcher<MobileElement>,
        FindsByAndroidViewMatcher<MobileElement>, FindsByAndroidViewTag<MobileElement> {
    /**
     * This method replace current text value.
     * @param value a new value
     */
    public void replaceValue(String value) {
        CommandExecutionHelper.execute(this, replaceElementValueCommand(this, value));
    }
}
