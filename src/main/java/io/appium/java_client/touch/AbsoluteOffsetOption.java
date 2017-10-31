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

import java.util.Map;

import static java.util.Optional.ofNullable;

public class AbsoluteOffsetOption extends ActionOptions<AbsoluteOffsetOption> {
    private Point absoluteOffset = null;

    /**
     * It creates an instance of {@link AbsoluteOffsetOption } which takes absolute
     * x and y offsets.
     *
     * @param xOffset the absolute distance from the left screen corner.
     * @param yOffset the absolute distance from the top screen corner.
     * @return a built option
     */
    public static AbsoluteOffsetOption useAbsolute(int xOffset, int yOffset) {
        return new AbsoluteOffsetOption().withAbsoluteOffset(xOffset, yOffset);
    }

    /**
     * Set the absolute offset for the corresponding action.
     *
     * @param xOffset the absolute distance from the left screen corner.
     * @param yOffset the absolute distance from the top screen corner.
     * @return this instance for chaining.
     */
    public AbsoluteOffsetOption withAbsoluteOffset(int xOffset, int yOffset) {
        this.absoluteOffset = new Point(xOffset, yOffset);
        //noinspection unchecked
        return this;
    }

    @Override
    protected void verify() {
        ofNullable(absoluteOffset).orElseThrow(() -> new IllegalArgumentException(
                "Absolute offset must not be defined"));
    }

    @Override
    public Map<String, Object> build() {
        final Map<String, Object> result = super.build();
        ofNullable(absoluteOffset).ifPresent(point -> {
            result.put("x", point.x);
            result.put("y", point.y);
        });
        return result;
    }
}
