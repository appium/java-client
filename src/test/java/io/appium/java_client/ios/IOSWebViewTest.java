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

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class IOSWebViewTest extends BaseIOSWebViewTest {

    @Test public void webViewPageTestCase() throws Throwable {
        driver.findElementByXPath("//UIATextField[@value='Enter URL']")
            .sendKeys("www.google.com");
        driver.findElementByClassName("UIAButton").click();
        driver.findElementByClassName("UIAWebView").click();
        Thread.sleep(10000);
        driver.context("WEBVIEW");
        Thread.sleep(10000);
        WebElement el = driver.findElementByClassName("gsfi");
        el.sendKeys("Appium");
        el.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        assertEquals(true, driver.getTitle().contains("Appium"));
    }
}
