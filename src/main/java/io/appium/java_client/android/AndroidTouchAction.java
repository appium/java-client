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

import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;


/**
 * Android-specific touch action.
 *
 * @deprecated Touch actions are deprecated.
 *     Please use W3C Actions instead or the corresponding
 *     extension methods for the driver (if available).
 *     Check
 *     - https://www.youtube.com/watch?v=oAJ7jwMNFVU
 *     - https://appiumpro.com/editions/30-ios-specific-touch-action-methods
 *     - https://appiumpro.com/editions/29-automating-complex-gestures-with-the-w3c-actions-api
 *     for more details.
 */
@Deprecated
public class AndroidTouchAction extends TouchAction<AndroidTouchAction> {

    public AndroidTouchAction(PerformsTouchActions performsTouchActions) {
        super(performsTouchActions);
    }

}
