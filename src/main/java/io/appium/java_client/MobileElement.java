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

package io.appium.java_client;

import com.google.common.collect.ImmutableMap;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.remote.FileDetector;

import java.util.List;

@SuppressWarnings({"unchecked"})
public abstract class MobileElement
    extends DefaultGenericMobileElement<MobileElement> {

    protected FileDetector fileDetector;

    /**
     * Method returns central coordinates of an element.
     * @return The instance of the {@link org.openqa.selenium.Point}
     */
    public Point getCenter() {
        Point upperLeft = this.getLocation();
        Dimension dimensions = this.getSize();
        return new Point(upperLeft.getX() + dimensions.getWidth() / 2,
            upperLeft.getY() + dimensions.getHeight() / 2);
    }

    /**
     * This method is deprecated and it is going to be removed soon.
     */
    @Deprecated
    @Override public void tap(int fingers, int duration) {
        AppiumDriver.class.cast(parent).tap(fingers, this, duration);
    }

    /**
     * This method is deprecated and it is going to be removed soon.
     */
    @Deprecated
    @Override public void pinch() {
        AppiumDriver.class.cast(parent).pinch(this);
    }

    /**
     * This method is deprecated and it is going to be removed soon.
     */
    @Deprecated
    @Override public void zoom() {
        AppiumDriver.class.cast(parent).zoom(this);
    }

    /**
     * This method does nothing. It is going to be removed.
     */
    @Deprecated
    @Override public void swipe(SwipeElementDirection direction, int duration) {
        direction.swipe(AppiumDriver.class.cast(parent), this, 0, 0, duration);
    }

    /**
     * This method does nothing. It is going to be removed.
     */
    @Deprecated
    @Override public void swipe(SwipeElementDirection direction, int offsetFromStartBorder,
        int offsetFromEndBorder, int duration) throws IllegalCoordinatesException {
        direction.swipe(AppiumDriver.class.cast(parent), this, offsetFromStartBorder, offsetFromEndBorder, duration);
    }

    @Override public List<MobileElement> findElements(By by) {
        return super.findElements(by);
    }

    @Override public List<MobileElement> findElements(String by, String using) {
        return super.findElements(by, using);
    }

    @Override public List<MobileElement> findElementsById(String id) {
        return super.findElementsById(id);
    }

    public List<MobileElement> findElementsByLinkText(String using) {
        return super.findElementsByLinkText(using);
    }

    public List<MobileElement> findElementsByPartialLinkText(String using) {
        return super.findElementsByPartialLinkText(using);
    }

    public List<MobileElement> findElementsByTagName(String using) {
        return super.findElementsByTagName(using);
    }

    public List<MobileElement> findElementsByName(String using) {
        return super.findElementsByName(using);
    }

    public List<MobileElement> findElementsByClassName(String using) {
        return super.findElementsByClassName(using);
    }

    public List<MobileElement> findElementsByCssSelector(String using) {
        return super.findElementsByCssSelector(using);
    }

    public List<MobileElement> findElementsByXPath(String using) {
        return super.findElementsByXPath(using);
    }

    @Override public List<MobileElement> findElementsByAccessibilityId(String using) {
        return super.findElementsByAccessibilityId(using);
    }

    /**
     * This method sets the new value of the attribute "value".
     *
     * @param value is the new value which should be set
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void setValue(String value) {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        builder.put("id", id).put("value", value);
        execute(MobileCommand.SET_VALUE, builder.build());
    }
}
