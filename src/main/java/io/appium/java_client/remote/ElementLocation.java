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

package io.appium.java_client.remote;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * Finds elements either by the server or, for locators that cannot be sent to the server,
 * by the locator itself. Adapted from Selenium's {@code ElementLocation} (Apache License 2.0).
 */
class ElementLocation {
    private final Map<Class<? extends By>, ElementFinder> finders = new HashMap<>();

    ElementLocation() {
        finders.put(By.cssSelector("a").getClass(), ElementFinder.REMOTE);
        finders.put(By.linkText("a").getClass(), ElementFinder.REMOTE);
        finders.put(By.partialLinkText("a").getClass(), ElementFinder.REMOTE);
        finders.put(By.tagName("a").getClass(), ElementFinder.REMOTE);
        finders.put(By.xpath("//a").getClass(), ElementFinder.REMOTE);
    }

    WebElement findElement(AppiumRemoteWebDriver driver, SearchContext context,
                           BiFunction<String, Object, CommandPayload> createPayload, By locator) {
        requireNonNull(driver, "WebDriver");
        requireNonNull(context, "Context for finding elements");
        requireNonNull(createPayload, "Method for creating remote requests");
        requireNonNull(locator, "Locator");

        ElementFinder mechanism = finders.get(locator.getClass());
        if (mechanism != null) {
            return mechanism.findElement(driver, context, createPayload, locator);
        }

        // The remote version is preferred if possible
        if (locator instanceof By.Remotable) {
            try {
                WebElement element = ElementFinder.REMOTE.findElement(driver, context, createPayload, locator);
                finders.put(locator.getClass(), ElementFinder.REMOTE);
                return element;
            } catch (NoSuchElementException e) {
                finders.put(locator.getClass(), ElementFinder.REMOTE);
                throw e;
            } catch (InvalidArgumentException e) {
                // Fall through to the locator itself
            }
        }

        try {
            WebElement element = ElementFinder.CONTEXT.findElement(driver, context, createPayload, locator);
            finders.put(locator.getClass(), ElementFinder.CONTEXT);
            return element;
        } catch (NoSuchElementException e) {
            finders.put(locator.getClass(), ElementFinder.CONTEXT);
            throw e;
        }
    }

    List<WebElement> findElements(AppiumRemoteWebDriver driver, SearchContext context,
                                  BiFunction<String, Object, CommandPayload> createPayload, By locator) {
        requireNonNull(driver, "WebDriver");
        requireNonNull(context, "Context for finding elements");
        requireNonNull(createPayload, "Method for creating remote requests");
        requireNonNull(locator, "Locator");

        ElementFinder finder = finders.get(locator.getClass());
        if (finder != null) {
            return finder.findElements(driver, context, createPayload, locator);
        }

        if (locator instanceof By.Remotable) {
            try {
                List<WebElement> elements = ElementFinder.REMOTE.findElements(driver, context, createPayload, locator);
                finders.put(locator.getClass(), ElementFinder.REMOTE);
                return elements;
            } catch (NoSuchElementException e) {
                finders.put(locator.getClass(), ElementFinder.REMOTE);
                throw e;
            } catch (InvalidArgumentException e) {
                // Fall through to the locator itself
            }
        }

        List<WebElement> elements = ElementFinder.CONTEXT.findElements(driver, context, createPayload, locator);
        // Only remember the finder if it completed successfully
        finders.put(locator.getClass(), ElementFinder.CONTEXT);
        return elements;
    }

    private enum ElementFinder {
        CONTEXT {
            @Override
            WebElement findElement(AppiumRemoteWebDriver driver, SearchContext context,
                                   BiFunction<String, Object, CommandPayload> createPayload, By locator) {
                WebElement element = locator.findElement(context);
                if (element == null) {
                    throw new NoSuchElementException("Unable to find element with locator " + locator);
                }
                return massage(driver, context, element, locator);
            }

            @Override
            List<WebElement> findElements(AppiumRemoteWebDriver driver, SearchContext context,
                                          BiFunction<String, Object, CommandPayload> createPayload, By locator) {
                List<WebElement> elements = locator.findElements(context);
                if (elements == null) {
                    return Collections.emptyList();
                }
                return elements.stream().map(e -> massage(driver, context, e, locator)).collect(Collectors.toList());
            }
        },

        REMOTE {
            @Override
            WebElement findElement(AppiumRemoteWebDriver driver, SearchContext context,
                                   BiFunction<String, Object, CommandPayload> createPayload, By locator) {
                By.Remotable.Parameters params = ((By.Remotable) locator).getRemoteParameters();
                Response response = driver.execute(createPayload.apply(params.using(), params.value()));
                Object element = response.getValue();
                if (element == null) {
                    throw new NoSuchElementException("Unable to find element with locator " + locator);
                }
                return massage(driver, context, element, locator);
            }

            @Override
            List<WebElement> findElements(AppiumRemoteWebDriver driver, SearchContext context,
                                          BiFunction<String, Object, CommandPayload> createPayload, By locator) {
                By.Remotable.Parameters params = ((By.Remotable) locator).getRemoteParameters();
                Response response = driver.execute(createPayload.apply(params.using(), params.value()));
                List<?> elements = (List<?>) response.getValue();
                if (elements == null) {
                    return Collections.emptyList();
                }
                return elements.stream().map(e -> massage(driver, context, e, locator)).collect(Collectors.toList());
            }
        };

        abstract WebElement findElement(AppiumRemoteWebDriver driver, SearchContext context,
                                        BiFunction<String, Object, CommandPayload> createPayload, By locator);

        abstract List<WebElement> findElements(AppiumRemoteWebDriver driver, SearchContext context,
                                               BiFunction<String, Object, CommandPayload> createPayload, By locator);

        WebElement massage(AppiumRemoteWebDriver driver, SearchContext context, Object element, By locator) {
            if (!(element instanceof WebElement)) {
                String hint;
                if (element instanceof Map<?, ?>) {
                    hint = ((Map<?, ?>) element).keySet().toString();
                } else if (element != null) {
                    hint = element.getClass().getName();
                } else {
                    hint = "null";
                }
                throw new WebDriverException("unexpected driver response: " + hint);
            }
            if (!(element instanceof AppiumWebElement)) {
                return (WebElement) element;
            }

            AppiumWebElement remoteElement = (AppiumWebElement) element;
            if (locator instanceof By.Remotable) {
                By.Remotable.Parameters params = ((By.Remotable) locator).getRemoteParameters();
                remoteElement.setFoundBy(context, params.using(), String.valueOf(params.value()));
            }
            remoteElement.setParent(driver);
            return remoteElement;
        }
    }
}
