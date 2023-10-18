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

package io.appium.java_client.events.stubs;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ContextAware;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EmptyWebDriver implements WebDriver, ContextAware,
        JavascriptExecutor, HasCapabilities, TakesScreenshot {
    public EmptyWebDriver() {
    }

    private static List<StubWebElement> createStubList() {
        return List.of(new StubWebElement(), new StubWebElement());
    }

    public WebDriver context(String name) {
        return null;
    }

    public Set<String> getContextHandles() {
        return null;
    }

    public String getContext() {
        return "";
    }

    public void get(String url) {
    }

    public String getCurrentUrl() {
        return null;
    }

    public String getTitle() {
        return null;
    }

    public StubWebElement findElement(By by) {
        return new StubWebElement();
    }

    public StubWebElement findElement(String by, String using) throws WebDriverException, NoSuchElementException {
        return new StubWebElement();
    }

    public List findElements(By by) {
        return createStubList();
    }

    public List<StubWebElement> findElements(String by, String using) throws WebDriverException {
        return createStubList();
    }

    public String getPageSource() {
        throw new WebDriverException();
    }

    public void close() {
    }

    public void quit() {
    }

    public Set<String> getWindowHandles() {
        return null;
    }

    public String getWindowHandle() {
        throw new WebDriverException();
    }

    public TargetLocator switchTo() {
        return new EmptyWebDriver.StubTargetLocator(this);
    }

    public Navigation navigate() {
        return new EmptyWebDriver.StubNavigation();
    }

    public Options manage() {
        return new EmptyWebDriver.StubOptions();
    }

    public Object executeScript(String script, Object... args) {
        return null;
    }

    public Object executeAsyncScript(String script, Object... args) {
        return null;
    }

    public Capabilities getCapabilities() {
        Map<String, Object> map = new HashMap<>();
        map.put("0", "");
        map.put("1", "");
        return new DesiredCapabilities(map);
    }

    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return target.convertFromPngBytes(new byte[]{1, 2});
    }

    private class StubNavigation implements Navigation {
        private StubNavigation() {
        }

        public void back() {
        }

        public void forward() {
        }

        public void to(String url) {
        }

        public void to(URL url) {
        }

        public void refresh() {
        }
    }

    private class StubOptions implements Options {
        private StubOptions() {
        }

        public void addCookie(Cookie cookie) {
        }

        public void deleteCookieNamed(String name) {
        }

        public void deleteCookie(Cookie cookie) {
        }

        public void deleteAllCookies() {
        }

        public Set<Cookie> getCookies() {
            return null;
        }

        public Cookie getCookieNamed(String name) {
            return null;
        }

        public Timeouts timeouts() {
            return null;
        }

        public Window window() {
            return new StubWindow();
        }

        public Logs logs() {
            return null;
        }
    }

    private class StubTargetLocator implements TargetLocator {
        private final WebDriver driver;

        StubTargetLocator(WebDriver driver) {
            this.driver = driver;
        }

        public WebDriver frame(int index) {
            return this.driver;
        }

        public WebDriver frame(String nameOrId) {
            return this.driver;
        }

        public WebDriver frame(WebElement frameElement) {
            return this.driver;
        }

        public WebDriver parentFrame() {
            return this.driver;
        }

        public WebDriver window(String nameOrHandle) {
            return this.driver;
        }

        @Override
        public WebDriver newWindow(WindowType typeHint) {
            return null;
        }

        public WebDriver defaultContent() {
            return this.driver;
        }

        public WebElement activeElement() {
            return new StubWebElement();
        }

        public Alert alert() {
            return new StubAlert();
        }
    }
}
