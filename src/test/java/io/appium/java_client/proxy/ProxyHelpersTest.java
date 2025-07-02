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

package io.appium.java_client.proxy;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import static io.appium.java_client.proxy.Helpers.createProxy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProxyHelpersTest {

    public static class FakeIOSDriver extends IOSDriver {
        public FakeIOSDriver(URL url, Capabilities caps) {
            super(url, caps);
        }

        @Override
        protected void startSession(Capabilities capabilities) {
        }

        @Override
        public WebElement findElement(By locator) {
            RemoteWebElement webElement = new RemoteWebElement();
            webElement.setId(locator.toString());
            webElement.setParent(this);
            return webElement;
        }

        @Override
        public List<WebElement> findElements(By locator) {
            List<WebElement> webElements = new ArrayList<>();

            RemoteWebElement webElement1 = new RemoteWebElement();
            webElement1.setId("1234");
            webElement1.setParent(this);
            webElements.add(webElement1);

            RemoteWebElement webElement2 = new RemoteWebElement();
            webElement2.setId("5678");
            webElement2.setParent(this);
            webElements.add(webElement2);

            return webElements;
        }
    }

    @Test
    void shouldFireBeforeAndAfterEvents() {
        final StringBuilder acc = new StringBuilder();
        MethodCallListener listener = new MethodCallListener() {
            @Override
            public void beforeCall(Object target, Method method, Object[] args) {
                acc.append("beforeCall ").append(method.getName()).append("\n");
                // should be ignored
                throw new IllegalStateException();
            }

            @Override
            public void afterCall(Object target, Method method, Object[] args, Object result) {
                acc.append("afterCall ").append(method.getName()).append("\n");
                // should be ignored
                throw new IllegalStateException();
            }
        };
        RemoteWebDriver driver = createProxy(RemoteWebDriver.class, Collections.singletonList(listener));

        assertThrows(
                UnreachableBrowserException.class,
                () -> driver.get("http://example.com/")
        );

        assertThat(acc.toString().trim(), is(equalTo(
                String.join("\n",
                        "beforeCall get",
                        "beforeCall getSessionId",
                        "afterCall getSessionId",
                        "beforeCall getCapabilities",
                        "afterCall getCapabilities",
                        "beforeCall getCapabilities",
                        "afterCall getCapabilities")
        )));
    }

    @Test
    void shouldFireErrorEvents() {
        MethodCallListener listener = new MethodCallListener() {
            @Override
            public Object onError(Object obj, Method method, Object[] args, Throwable e) {
                throw new IllegalStateException();
            }
        };
        RemoteWebDriver driver = createProxy(RemoteWebDriver.class, Collections.singletonList(listener));
        assertThrows(
                IllegalStateException.class,
                () -> driver.get("http://example.com/")
        );
    }

    @Test
    void shouldFireCallEvents() throws MalformedURLException {
        final StringBuilder acc = new StringBuilder();
        MethodCallListener listener = new MethodCallListener() {
            @Override
            public Object call(Object obj, Method method, Object[] args, Callable<?> original) {
                acc.append("onCall ").append(method.getName()).append("\n");
                throw new IllegalStateException();
            }

            @Override
            public Object onError(Object obj, Method method, Object[] args, Throwable e) throws Throwable {
                acc.append("onError ").append(method.getName()).append("\n");
                throw e;
            }
        };
        FakeIOSDriver driver = createProxy(
                FakeIOSDriver.class,
                new Object[] {new URL("http://localhost:4723/"), new XCUITestOptions()},
                new Class[] {URL.class, Capabilities.class},
                listener
        );

        assertThrows(
                IllegalStateException.class,
                () -> driver.get("http://example.com/")
        );

        assertThat(acc.toString().trim(), is(equalTo(
                String.join("\n",
                        "onCall get",
                        "onError get")
        )));
    }


    @Test
    void shouldFireEventsForRemoteWebElement() throws MalformedURLException {
        final StringBuilder acc = new StringBuilder();
        MethodCallListener listener = new MethodCallListener() {
            @Override
            public void beforeCall(Object target, Method method, Object[] args) {
                acc.append("beforeCall ").append(method.getName()).append("\n");
            }
        };

        RemoteWebElementListener remoteWebElementListener = new RemoteWebElementListener(listener);

        FakeIOSDriver driver = createProxy(
                FakeIOSDriver.class,
                new Object[] {new URL("http://localhost:4723/"), new XCUITestOptions()},
                new Class[] {URL.class, Capabilities.class},
                List.of(remoteWebElementListener, listener)
        );

        WebElement element = driver.findElement(By.id("button"));

        assertThrows(
                NoSuchSessionException.class,
                element::click
        );

        List<WebElement> elements = driver.findElements(By.id("button"));

        assertThrows(
                NoSuchSessionException.class,
                () -> elements.get(1).isSelected()
        );

        assertThat(acc.toString().trim(), is(equalTo(
                String.join("\n",
                        "beforeCall findElement",
                        "beforeCall click",
                        "beforeCall getSessionId",
                        "beforeCall getCapabilities",
                        "beforeCall getCapabilities",
                        "beforeCall findElements",
                        "beforeCall isSelected",
                        "beforeCall getSessionId",
                        "beforeCall getCapabilities",
                        "beforeCall getCapabilities"
                )
        )));
    }
}
