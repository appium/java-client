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

package io.appium.java_client.touch;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.HasIdentity;

import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class OptionsWithAbsolutePositioning<T extends OptionsWithAbsolutePositioning<T>>
        extends ActionOptions<T> {
    private String elementId = null;
    private Point absoluteOffset = null;
    private Point relativeOffset = null;

    /**
     * Set the destination element for the corresponding action.
     *
     * @param element the destination element.
     * @return this instance for chaining.
     */
    public T withElement(WebElement element) {
        checkNotNull(element);
        this.elementId = ((HasIdentity) element).getId();
        //noinspection unchecked
        return (T) this;
    }

    /**
     * Set the absolute offset for the corresponding action.
     *
     * @param xOffset the absolute distance from the left screen corner (the element must not be set).
     * @param yOffset the absolute distance from the top screen corner.
     * @return this instance for chaining.
     */
    public T withAbsoluteOffset(int xOffset, int yOffset) {
        this.absoluteOffset = new Point(xOffset, yOffset);
        //noinspection unchecked
        return (T) this;
    }

    /**
     * Set the relative offset for the corresponding action.
     *
     * @param xOffset the relative distance from the left element corner (the element must be set).
     * @param yOffset the relative distance from the top element corner (the element must be set).
     * @return this instance for chaining.
     */
    public T withRelativeOffset(int xOffset, int yOffset) {
        this.relativeOffset = new Point(xOffset, yOffset);
        //noinspection unchecked
        return (T) this;
    }

    @Override
    protected void verify() {
        if (elementId == null) {
            if (absoluteOffset != null) {
                throw new IllegalArgumentException("Absolute offset must not be defined if 'element' option is set");
            }
            if (relativeOffset == null) {
                throw new IllegalArgumentException("Relative offset must be defined if 'element' option is set");
            }
        } else {
            if (absoluteOffset == null) {
                throw new IllegalArgumentException("Absolute offset must be defined if 'element' option not set");
            }
            if (relativeOffset != null) {
                throw new IllegalArgumentException("Relative offset must not be defined if 'element' option not set");
            }
        }
    }

    @Override
    public Map<String, Object> build() {
        final Map<String, Object> result = super.build();
        if (absoluteOffset != null) {
            result.put("x", absoluteOffset.x);
            result.put("y", absoluteOffset.y);
        }
        if (relativeOffset != null) {
            result.put("x", relativeOffset.x);
            result.put("y", relativeOffset.y);
        }
        if (elementId != null) {
            result.put("element", elementId);
        }
        return result;
    }
}
