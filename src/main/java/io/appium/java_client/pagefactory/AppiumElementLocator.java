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

package io.appium.java_client.pagefactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.ui.FluentWait;
import com.google.common.base.Function;

class AppiumElementLocator implements ElementLocator {

    // This function waits for not empty element list using all defined by
    private static class WaitingFunction implements
            Function<By, List<WebElement>> {
        private final SearchContext searchContext;
        private final static String INVALID_SELECTOR_PATTERN = "Invalid locator strategy:";

        private WaitingFunction(SearchContext searchContext) {
            this.searchContext = searchContext;
        }

        public List<WebElement> apply(By by) {
            List<WebElement> result = new ArrayList<WebElement>();
            try {
                result.addAll(searchContext.findElements(by));
            } catch (StaleElementReferenceException ignored) {
            }
            catch (RuntimeException e){
                if (!isInvalidSelectorRootCause(e))
                    throw e;
            }
            if (result.size() > 0) {
                return result;
            } else {
                return null;
            }
        }

        private static boolean isInvalidSelectorRootCause(Throwable e){
            if (e == null)
                return false;

            if (String.valueOf(e.getMessage()).contains(INVALID_SELECTOR_PATTERN))
                return true;

            return isInvalidSelectorRootCause(e.getCause());
        }
    }

    private final SearchContext searchContext;
    final boolean shouldCache;
    final By by;
    private WebElement cachedElement;
    private List<WebElement> cachedElementList;
    final TimeOutDuration timeOutDuration;
    final WebDriver originalWebDriver;

    /**
     * Creates a new mobile element locator. It instantiates {@link WebElement}
     * using @AndroidFindBy (-s), @iOSFindBy (-s) and @FindBy (-s) annotation
     * sets
     *  @param searchContext
     *            The context to use when finding the element
     * @param by a By locator strategy
     * @param shouldCache is the flag that signalizes that elements which are found once should be cached
     * @param duration is a POJO which contains timeout parameters
     * @param originalWebDriver
     */
    public AppiumElementLocator(SearchContext searchContext, By by, boolean shouldCache, TimeOutDuration duration, WebDriver originalWebDriver) {
        this.searchContext = searchContext;
        this.shouldCache = shouldCache;
        this.timeOutDuration = duration;
        this.by = by;
        this.originalWebDriver = originalWebDriver;
    }

    private void changeImplicitlyWaitTimeOut(long newTimeOut,
                                             TimeUnit newTimeUnit) {
        originalWebDriver.manage().timeouts().implicitlyWait(newTimeOut, newTimeUnit);
    }

    // This method waits for not empty element list using all defined by
    private List<WebElement> waitFor() {
        // When we use complex By strategies (like ChainedBy or ByAll)
        // there are some problems (StaleElementReferenceException, implicitly
        // wait time out
        // for each chain By section, etc)
        try {
            changeImplicitlyWaitTimeOut(0, TimeUnit.SECONDS);
            FluentWait<By> wait = new FluentWait<By>(by);
            wait.withTimeout(timeOutDuration.getTime(),
                    timeOutDuration.getTimeUnit());
            return wait.until(new WaitingFunction(searchContext));
        } catch (TimeoutException e) {
            return new ArrayList<WebElement>();
        } finally {
            changeImplicitlyWaitTimeOut(timeOutDuration.getTime(),
                    timeOutDuration.getTimeUnit());
        }
    }

    /**
     * Find the element.
     */
    public WebElement findElement() {
        if (cachedElement != null && shouldCache) {
            return cachedElement;
        }
        List<WebElement> result = waitFor();
        if (result.size() == 0) {
            String message = "Can't locate an element by this strategy: "
                    + by.toString();
            throw new NoSuchElementException(message);
        }
        if (shouldCache) {
            cachedElement = result.get(0);
        }
        return result.get(0);
    }

    /**
     * Find the element list.
     */
    public List<WebElement> findElements() {
        if (cachedElementList != null && shouldCache) {
            return cachedElementList;
        }
        List<WebElement> result = waitFor();
        if (shouldCache) {
            cachedElementList = result;
        }
        return result;
    }
}
