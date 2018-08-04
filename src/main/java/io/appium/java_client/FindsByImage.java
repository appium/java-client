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

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

public interface FindsByImage<T extends WebElement> extends FindsByFluentSelector<T> {
    /**
     * Performs the lookup for a single element by matching its image template
     * to the current full screen shot. This type of locator requires OpenCV libraries
     * and bindings for NodeJS to be installed on the server machine. Lookup options
     * fine-tuning might be done via {@link HasSettings#setSetting(Setting, Object)}.
     *
     * @param b64Template base64-encoded template image string. Supported image formats are the same
     *                    as for OpenCV library.
     * @return The first element that matches the given selector
     * @throws NoSuchElementException when no element is found
     * @see <a href="https://github.com/appium/appium/blob/master/docs/en/writing-running-appium/image-comparison.md">
     * The documentation on Image Comparison Features</a>
     * @see <a href="https://github.com/appium/appium-base-driver/blob/master/lib/basedriver/device-settings.js">
     * The settings available for lookup fine-tuning</a>
     * @since Appium 1.8.2
     */
    default T findElementByImage(String b64Template) {
        return findElement(MobileSelector.IMAGE.toString(), b64Template);
    }

    /**
     * Performs the lookup for a list of elements by matching them to image template
     * in the current full screen shot. This type of locator requires OpenCV libraries
     * and bindings for NodeJS to be installed on the server machine. Lookup options
     * fine-tuning might be done via {@link HasSettings#setSetting(Setting, Object)}.
     *
     * @param b64Template base64-encoded template image string. Supported image formats are the same
     *                    as for OpenCV library.
     * @return a list of elements that match the given selector or an empty list
     * @see <a href="https://github.com/appium/appium/blob/master/docs/en/writing-running-appium/image-comparison.md">
     * The documentation on Image Comparison Features</a>
     * @see <a href="https://github.com/appium/appium-base-driver/blob/master/lib/basedriver/device-settings.js">
     * The settings available for lookup fine-tuning</a>
     * @since Appium 1.8.2
     */
    default List<T> findElementsByImage(String b64Template) {
        return findElements(MobileSelector.IMAGE.toString(), b64Template);
    }
}
