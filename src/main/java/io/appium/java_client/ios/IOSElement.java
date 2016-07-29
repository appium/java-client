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

import io.appium.java_client.FindsByIosUIAutomation;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebDriverException;

import java.util.List;

public class IOSElement extends MobileElement
    implements FindsByIosUIAutomation<MobileElement> {
    /**
     * @throws WebDriverException
     * This method is not applicable with browser/webview UI.
     */
    @Override public MobileElement findElementByIosUIAutomation(String using)
        throws WebDriverException {
        return findElement("-ios uiautomation", using);
    }

    /**
     * @throws WebDriverException
     * This method is not applicable with browser/webview UI.
     */
    @Override public List<MobileElement> findElementsByIosUIAutomation(String using)
        throws WebDriverException {
        return findElements("-ios uiautomation", using);
    }
}
