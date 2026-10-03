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

package io.appium.java_client.support;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.function.Function;

final class Stubs {

    private Stubs() {
    }

    static WebElement element(String text) {
        return (WebElement) Proxy.newProxyInstance(
                Stubs.class.getClassLoader(),
                new Class<?>[]{WebElement.class},
                (proxy, method, args) -> {
                    if ("getText".equals(method.getName())) {
                        return text;
                    }
                    switch (method.getName()) {
                        case "toString":
                            return "element " + text;
                        case "hashCode":
                            return System.identityHashCode(proxy);
                        case "equals":
                            return proxy == args[0];
                        default:
                            break;
                    }
                    throw new UnsupportedOperationException(method.getName());
                });
    }

    static SearchContext context(Function<By, List<WebElement>> finder) {
        return new SearchContext() {
            @Override
            public List<WebElement> findElements(By by) {
                return finder.apply(by);
            }

            @Override
            public WebElement findElement(By by) {
                return finder.apply(by).get(0);
            }
        };
    }
}
