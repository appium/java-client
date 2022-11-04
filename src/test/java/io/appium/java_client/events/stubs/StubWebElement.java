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

import com.google.common.collect.ImmutableList;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class StubWebElement implements WebElement {
    public StubWebElement() {
    }

    private static List<WebElement> createStubSubElementList() {
        return new ArrayList<>(ImmutableList.of(new StubWebElement(), new StubWebElement()));
    }

    public void click() {
    }

    public void submit() {
    }

    public void sendKeys(CharSequence... keysToSend) {
    }

    public void clear() {
    }

    public String getTagName() {
        return null;
    }

    public String getAttribute(String name) {
        return null;
    }

    public boolean isSelected() {
        return false;
    }

    public boolean isEnabled() {
        return false;
    }

    public String getText() {
        return null;
    }

    public List<WebElement> findElements(By by) {
        return createStubSubElementList();
    }

    public WebElement findElement(By by) {
        return new StubWebElement();
    }

    public boolean isDisplayed() {
        return false;
    }

    public Point getLocation() {
        return null;
    }

    public Dimension getSize() {
        return null;
    }

    public Rectangle getRect() {
        throw new WebDriverException();
    }

    public String getCssValue(String propertyName) {
        return null;
    }

    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return target.convertFromPngBytes(new byte[]{1, 2});
    }

    public String toString() {
        return this.getClass().getCanonicalName();
    }
}

