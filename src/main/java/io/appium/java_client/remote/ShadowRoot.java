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
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.appium.java_client.remote.DriverCommand.FIND_ELEMENTS_FROM_SHADOW_ROOT;
import static io.appium.java_client.remote.DriverCommand.FIND_ELEMENT_FROM_SHADOW_ROOT;
import static java.util.Objects.requireNonNull;

/**
 * A shadow root of an element. Adapted from Selenium's {@code ShadowRoot} (Apache License 2.0).
 * It is package private, so callers use the {@link SearchContext} API.
 */
class ShadowRoot implements SearchContext, WrapsDriver {
    /**
     * The key of a shadow root reference in the W3C protocol.
     */
    static final String SHADOW_ROOT_KEY = "shadow-6066-11e4-a52e-4f735466cecf";

    private final AppiumRemoteWebDriver parent;
    private final String id;

    ShadowRoot(AppiumRemoteWebDriver parent, String id) {
        this.parent = requireNonNull(parent, "Owning driver");
        this.id = requireNonNull(id, "Shadow root ID");
    }

    @Override
    public List<WebElement> findElements(By by) {
        return parent.findElements(this, (using, value) -> FIND_ELEMENTS_FROM_SHADOW_ROOT(id, using, value), by);
    }

    @Override
    public WebElement findElement(By by) {
        return parent.findElement(this, (using, value) -> FIND_ELEMENT_FROM_SHADOW_ROOT(id, using, value), by);
    }

    @Override
    public WebDriver getWrappedDriver() {
        return parent;
    }

    String getId() {
        return id;
    }

    Map<String, Object> toJson() {
        return Map.of(SHADOW_ROOT_KEY, id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShadowRoot that = (ShadowRoot) o;
        return Objects.equals(parent, that.parent) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, id);
    }
}
