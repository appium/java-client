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

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

public enum SwipeElementDirection {
    /**
     * Up from the center of the lower.
     */
    UP {
        @Override int getStartX(Point center, Point location, Dimension size, int ignored) {
            return center.getX();
        }

        @Override int getStartY(Point center, Point location, Dimension size, int offSet) {
            int result = location.getY() + size.getHeight() - offSet;
            checkYCoordinate(result, location, size, offSet);
            return result;
        }

        @Override int getEndX(Point center, Point location, Dimension size, int ignored) {
            return center.getX();
        }

        @Override int getEndY(Point center, Point location, Dimension size, int offSet) {
            int result = location.getY() + offSet;
            checkYCoordinate(result, location, size, offSet);
            return result;
        }

        @Override void checkDirection(int x1, int y1, int x2, int y2) {
            if (y1 < y2) {
                throw new IllegalCoordinatesException(
                        "Y1 " + y1 + " and Y2 " + y2 + " are inconsistent. It looks like you are "
                                + "trying to perform the swiping down");
            }
        }
    },
    /**
     * Down from the center of the upper.
     */
    DOWN {
        @Override int getStartX(Point center, Point location, Dimension size, int offSet) {
            return center.getX();
        }

        @Override int getStartY(Point center, Point location, Dimension size, int offSet) {
            return UP.getEndY(center, location, size, offSet);
        }

        @Override int getEndX(Point center, Point location, Dimension size, int offSet) {
            return center.getX();
        }

        @Override int getEndY(Point center, Point location, Dimension size, int offSet) {
            return UP.getStartY(center, location, size, offSet);
        }

        @Override void checkDirection(int x1, int y1, int x2, int y2) {
            if (y1 > y2) {
                throw new IllegalCoordinatesException(
                        "Y1 " + y1 + " and Y2 " + y2 + " are inconsistent. It looks like you are "
                                + "trying to perform the swiping up");
            }
        }
    },
    /**
     * To the left from the center of the rightmost.
     */
    LEFT {
        @Override int getStartX(Point center, Point location, Dimension size, int offSet) {
            int result = location.getX() + size.getWidth() - offSet;
            checkXCoordinate(result, location, size, offSet);
            return result;
        }

        @Override int getStartY(Point center, Point location, Dimension size, int offSet) {
            return center.getY();
        }

        @Override int getEndX(Point center, Point location, Dimension size, int offSet) {
            int result = location.getX() + offSet;
            checkXCoordinate(result, location, size, offSet);
            return result;
        }

        @Override int getEndY(Point center, Point location, Dimension size, int offSet) {
            return center.getY();
        }

        @Override void checkDirection(int x1, int y1, int x2, int y2) {
            if (x1 < x2) {
                throw new IllegalCoordinatesException(
                        "X1 " + x1 + " and X2 " + x2 + " are inconsistent. It looks like you are "
                                + "trying to perform the swiping right");
            }

        }
    },
    /**
     * To the right from the center of the leftmost.
     */
    RIGHT {
        @Override int getStartX(Point center, Point location, Dimension size, int offSet) {
            return LEFT.getEndX(center, location, size, offSet);
        }

        @Override int getStartY(Point center, Point location, Dimension size, int offSet) {
            return center.getY();
        }

        @Override int getEndX(Point center, Point location, Dimension size, int offSet) {
            return LEFT.getStartX(center, location, size, offSet);
        }

        @Override int getEndY(Point center, Point location, Dimension size, int offSet) {
            return center.getY();
        }

        @Override void checkDirection(int x1, int y1, int x2, int y2) {
            if (x1 > x2) {
                throw new IllegalCoordinatesException(
                        "X1 " + x1 + " and X2 " + x2 + " are inconsistent. It looks like you are "
                                + "trying to perform the swiping left");
            }
        }
    };

    static void checkYCoordinate(int y, Point location, Dimension size, int offSet)
        throws IllegalCoordinatesException {
        int bottom = location.getY() + size.getHeight();
        int top = location.getY();
        if (y > bottom) {
            throw new IllegalCoordinatesException(
                    "The result Y " + y + " is lower than target element bottom " + bottom);
        }
        if (y < top) {
            throw new IllegalCoordinatesException(
                    "The result Y " + y + " is higher than target element top " + top);
        }

    }

    static void checkXCoordinate(int x, Point location, Dimension size, int offSet)
        throws IllegalCoordinatesException {
        int right = location.getX() + size.getWidth();
        int left = location.getX();
        if (x > right) {
            throw new IllegalCoordinatesException(
                    "The result X " + x + " is righter than target element right border " + right);
        }
        if (x < left) {
            throw new IllegalCoordinatesException(
                    "The result X " + x + " is lefter than target element left border " + left);
        }

    }

    abstract int getStartX(Point center, Point location, Dimension size, int offSet);

    abstract int getStartY(Point center, Point location, Dimension size, int offSet);

    abstract int getEndX(Point center, Point location, Dimension size, int offSet);

    abstract int getEndY(Point center, Point location, Dimension size, int offSet);

    abstract void checkDirection(int x1, int y1, int x2, int y2);

    void swipe(AppiumDriver<?> driver, MobileElement element, int offset1, int offset2,
        int duration) throws IllegalCoordinatesException {
        Point p = element.getCenter();
        Point location = element.getLocation();
        Dimension size = element.getSize();
        int startX = getStartX(p, location, size, offset1);
        int startY = getStartY(p, location, size, offset1);
        int endX = getEndX(p, location, size, offset2);
        int endY = getEndY(p, location, size, offset2);
        checkDirection(startX, startY, endX, endY);

        driver.swipe(startX, startY, endX, endY, duration);
    }
}
