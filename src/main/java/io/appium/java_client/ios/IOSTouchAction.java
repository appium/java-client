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

package io.appium.java_client.ios;

import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.touch.IOSPressOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

public class IOSTouchAction extends TouchAction<IOSTouchAction> {

    public IOSTouchAction(PerformsTouchActions performsTouchActions) {
        super(performsTouchActions);
    }

    /**
     * Double taps using coordinates.
     *
     * @param doubleTapOption see {@link PointOption} and {@link ElementOption}..
     * @return self-reference
     */
    public IOSTouchAction doubleTap(PointOption doubleTapOption) {
        ActionParameter action = new ActionParameter("doubleTap",
                doubleTapOption);
        parameterBuilder.add(action);
        return this;
    }

    /**
     * Press action on the screen.
     *
     * @param pressOptions see {@link IOSPressOptions}
     * @return this TouchAction, for chaining.
     */
    public IOSTouchAction press(IOSPressOptions pressOptions) {
        parameterBuilder.add(new ActionParameter("press", pressOptions));
        return this;
    }
}
