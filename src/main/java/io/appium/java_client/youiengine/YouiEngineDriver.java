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

package io.appium.java_client.youiengine;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.youiengine.internal.JsonToYouiEngineElementConverter;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;

import java.net.URL;

public class YouiEngineDriver<T extends WebElement> extends AppiumDriver<T> {

    /** Constructor takes in the Appium Server URL and the capabilities you want to use for this
     * test execution. **/
    public YouiEngineDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(remoteAddress, desiredCapabilities, JsonToYouiEngineElementConverter.class);
    }

    @Override
    public void swipe(int startx, int starty, int endx, int endy, int duration) {
        // YouiEngine does not use duration
        TouchAction swipeAction = new TouchAction(this).press(startx, starty).moveTo(endx, endy)
                .release();
        swipeAction.perform();
    }
}
