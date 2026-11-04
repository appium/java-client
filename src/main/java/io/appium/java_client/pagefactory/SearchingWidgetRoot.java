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

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.WrapsElement;

import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

import static io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility.unpackWebDriverFromSearchContext;

/**
 * Widget root whose nested lookups try later alternative roots.
 * Actions other than finding elements stay on the first matching root, and the
 * widget method that triggered the lookup is not invoked again.
 */
class SearchingWidgetRoot implements WebElement, WrapsDriver, WrapsElement {

    private final WebElement resolved;
    private final Supplier<Iterator<WebElement>> furtherRoots;

    SearchingWidgetRoot(WebElement resolved, Supplier<Iterator<WebElement>> furtherRoots) {
        this.resolved = resolved;
        this.furtherRoots = furtherRoots;
    }

    @Override
    public WebElement findElement(By by) {
        try {
            return resolved.findElement(by);
        } catch (NoSuchElementException missed) {
            NoSuchElementException firstMiss = missed;
            Iterator<WebElement> laterRoots = furtherRoots.get();
            while (laterRoots.hasNext()) {
                try {
                    return laterRoots.next().findElement(by);
                } catch (NoSuchElementException laterMiss) {
                    firstMiss.addSuppressed(laterMiss);
                }
            }
            throw firstMiss;
        }
    }

    @Override
    public List<WebElement> findElements(By by) {
        List<WebElement> found = resolved.findElements(by);
        if (!found.isEmpty()) {
            return found;
        }
        Iterator<WebElement> laterRoots = furtherRoots.get();
        while (laterRoots.hasNext()) {
            List<WebElement> later = laterRoots.next().findElements(by);
            if (!later.isEmpty()) {
                return later;
            }
        }
        return found;
    }

    @Override
    public WebElement getWrappedElement() {
        return resolved;
    }

    @Override
    public WebDriver getWrappedDriver() {
        return unpackWebDriverFromSearchContext(resolved);
    }

    @Override
    public void click() {
        resolved.click();
    }

    @Override
    public void submit() {
        resolved.submit();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        resolved.sendKeys(keysToSend);
    }

    @Override
    public void clear() {
        resolved.clear();
    }

    @Override
    public String getTagName() {
        return resolved.getTagName();
    }

    @Override
    public String getAttribute(String name) {
        return resolved.getAttribute(name);
    }

    @Override
    public boolean isSelected() {
        return resolved.isSelected();
    }

    @Override
    public boolean isEnabled() {
        return resolved.isEnabled();
    }

    @Override
    public String getText() {
        return resolved.getText();
    }

    @Override
    public boolean isDisplayed() {
        return resolved.isDisplayed();
    }

    @Override
    public Point getLocation() {
        return resolved.getLocation();
    }

    @Override
    public Dimension getSize() {
        return resolved.getSize();
    }

    @Override
    public Rectangle getRect() {
        return resolved.getRect();
    }

    @Override
    public String getCssValue(String propertyName) {
        return resolved.getCssValue(propertyName);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return resolved.getScreenshotAs(target);
    }

    @Override
    public String toString() {
        return resolved.toString();
    }
}
