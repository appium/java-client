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

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.ofNullable;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.HasIdentity;

import java.util.Map;

public class RelativeOffsetOption extends ActionOptions<RelativeOffsetOption> {
    private String elementId = null;
    private Point relativeOffset = null;

    /**
     * It creates an instance of {@link RelativeOffsetOption } which takes an element
     * and x and y offsets. These offsets are relative to the given element.
     *
     * @param element is the target element
     * @param xOffset x-offset which is relative to the given element
     * @param yOffset y-offset which is relative to the given element
     * @return a built option
     */
    public static RelativeOffsetOption useRelative(WebElement element, int xOffset, int yOffset) {
        return new RelativeOffsetOption().withRelativeOffset(element, xOffset, yOffset);
    }

    /**
     *  * It creates an instance of {@link RelativeOffsetOption } which takes an element
     * and 0-value x and y offsets.
     *
     * @param element is the target element
     * @return a built option
     */
    public static RelativeOffsetOption useRelative(WebElement element) {
        return useRelative(element, 0, 0);
    }

    /**
     * It creates an instance of {@link RelativeOffsetOption } which takes x and y offsets.
     * These offsets are relative to to the previous chain element position.
     *
     * @param xOffset x offset that is relative to the previous chain element position.
     * @param yOffset y offset that is relative to the previous chain element position.
     * @return a built option
     */
    public static RelativeOffsetOption useRelative(int xOffset, int yOffset) {
        return new RelativeOffsetOption().withRelativeOffset(xOffset, yOffset);
    }

    /**
     * Set the relative offset for the corresponding action.
     *
     * @param element the destination element.
     * @param xOffset the relative distance from the left element corner
     *                (if set) or from the left corner of the preceding chain action.
     *                This value might be zero if it is necessary.
     * @param yOffset the relative distance from the top element corner
     *                (if set) or from the top corner of the preceding chain action.
     *                This value might be zero if it is necessary.
     * @return this instance for chaining.
     */
    public RelativeOffsetOption withRelativeOffset(WebElement element, int xOffset, int yOffset) {
        checkNotNull(element);
        this.elementId = ((HasIdentity) element).getId();
        this.relativeOffset = new Point(xOffset, yOffset);
        //noinspection unchecked
        return this;
    }

    /**
     * Set the relative offset for the corresponding action.
     *
     * @param xOffset is x-axis offset that is relative to the previous chain element position.
     * @param yOffset is y-axis offset that is relative to the previous chain element position.
     * @return this instance for chaining.
     */
    public RelativeOffsetOption withRelativeOffset(int xOffset, int yOffset) {
        this.relativeOffset = new Point(xOffset, yOffset);
        //noinspection unchecked
        return this;
    }

    @Override
    protected void verify() {
        if (elementId == null && relativeOffset == null) {
            throw new IllegalArgumentException("Either element or relative offset should be defined");
        }
    }

    @Override
    public Map<String, Object> build() {
        final Map<String, Object> result = super.build();
        ofNullable(relativeOffset).ifPresent(point -> {
            result.put("x", point.x);
            result.put("y", point.y);
        });

        ofNullable(elementId).ifPresent(s -> result.put("element", s));
        return result;
    }
}
