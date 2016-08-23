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

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"unchecked", "rawtypes"})
abstract class DefaultGenericMobileDriver<T extends WebElement> extends RemoteWebDriver
    implements MobileDriver<T> {

    public DefaultGenericMobileDriver(CommandExecutor executor, Capabilities desiredCapabilities) {
        super(executor, desiredCapabilities);
    }

    @Override public Response execute(String driverCommand, Map<String, ?> parameters) {
        return super.execute(driverCommand, parameters);
    }

    @Override public List findElements(By by) {
        return super.findElements(by);
    }

    @Override public List findElements(String by, String using) {
        return super.findElements(by, using);
    }

    @Override public T findElement(By by) {
        return (T) super.findElement(by);
    }

    @Override public T findElement(String by, String using) {
        return (T) super.findElement(by, using);
    }

    @Override public List findElementsById(String id) {
        return super.findElementsById(id);
    }

    @Override public T findElementById(String id) {
        return (T) super.findElementById(id);
    }

    /**
     * @throws WebDriverException his method doesn't work against native app UI.
     */
    public T findElementByLinkText(String using) throws WebDriverException {
        return (T) super.findElementByLinkText(using);
    }

    /**
     * @throws WebDriverException This method doesn't work against native app UI.
     */
    public List findElementsByLinkText(String using) throws WebDriverException {
        return super.findElementsByLinkText(using);
    }

    /**
     * @throws WebDriverException his method doesn't work against native app UI.
     */
    public T findElementByPartialLinkText(String using) throws WebDriverException {
        return (T) super.findElementByPartialLinkText(using);
    }

    /**
     * @throws WebDriverException This method doesn't work against native app UI.
     */
    public List findElementsByPartialLinkText(String using) throws WebDriverException {
        return super.findElementsByPartialLinkText(using);
    }

    public T findElementByTagName(String using) {
        return (T) super.findElementByTagName(using);
    }

    public List findElementsByTagName(String using) {
        return super.findElementsByTagName(using);
    }

    public T findElementByName(String using) {
        return (T) super.findElementByName(using);
    }

    public List findElementsByName(String using) {
        return super.findElementsByName(using);
    }

    public T findElementByClassName(String using) {
        return (T) super.findElementByClassName(using);
    }

    public List findElementsByClassName(String using) {
        return super.findElementsByClassName(using);
    }

    /**
     * @throws WebDriverException his method doesn't work against native app UI.
     */
    public T findElementByCssSelector(String using) throws WebDriverException {
        return (T) super.findElementByCssSelector(using);
    }

    /**
     * @throws WebDriverException This method doesn't work against native app UI.
     */
    public List findElementsByCssSelector(String using) throws WebDriverException {
        return super.findElementsByCssSelector(using);
    }

    public T findElementByXPath(String using) {
        return (T) super.findElementByXPath(using);
    }

    public List findElementsByXPath(String using) {
        return super.findElementsByXPath(using);
    }

    /**
    * @throws WebDriverException This method is not applicable with browser/webview UI.
    */
    @Override public T findElementByAccessibilityId(String using) throws WebDriverException {
        return (T) findElement(MobileSelector.ACCESSIBILITY.toString(), using);
    }

    /**
     * @throws WebDriverException This method is not applicable with browser/webview UI.
     */
    @Override public List findElementsByAccessibilityId(String using) throws WebDriverException {
        return (List<T>) findElements(MobileSelector.ACCESSIBILITY.toString(), using);
    }

    /**
     * Mouse doesn't work on mobile devices and emulators.
     */
    @Deprecated public Mouse getMouse() {
        return super.getMouse();
    }
}
