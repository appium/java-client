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

package io.appium.java_client.imagecomparison;

import lombok.Getter;
import org.apache.commons.codec.binary.Base64;

import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public abstract class ComparisonResult {
    private static final String VISUALIZATION = "visualization";

    @Getter private final Map<String, Object> commandResult;

    public ComparisonResult(Map<String, Object> commandResult) {
        this.commandResult = commandResult;
    }

    /**
     * Verifies if the corresponding property is present in the commend result
     * and throws an exception if not.
     *
     * @param propertyName the actual property name to be verified for presence
     */
    protected void verifyPropertyPresence(String propertyName) {
        if (!commandResult.containsKey(propertyName)) {
            throw new IllegalStateException(
                    String.format("There is no '%s' attribute in the resulting command output %s. "
                            + "Did you set the options properly?", propertyName, commandResult));
        }
    }

    /**
     * Returns the visualization of the matching result.
     *
     * @return The visualization of the matching result represented as base64-encoded PNG image.
     */
    public byte[] getVisualization() {
        verifyPropertyPresence(VISUALIZATION);
        return ((String) getCommandResult().get(VISUALIZATION)).getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Stores visualization image into the given file.
     *
     * @param destination file to save image.
     */
    public void storeVisualization(File destination) throws IOException {
        final byte[] data = Base64.decodeBase64(getVisualization());
        try (OutputStream stream = new FileOutputStream(destination)) {
            stream.write(data);
        }
    }

    /**
     * Converts float OpenCV coordinates to Selenium-compatible format.
     *
     * @param openCVCoordinate the original coordinate value
     * @return The converted value
     */
    private static int toSeleniumCoordinate(Object openCVCoordinate) {
        if (openCVCoordinate instanceof Long) {
            return ((Long) openCVCoordinate).intValue();
        }
        if (openCVCoordinate instanceof Double) {
            return ((Double) openCVCoordinate).intValue();
        }
        return (int) openCVCoordinate;
    }

    /**
     * Transforms a map into {@link Rectangle} object.
     *
     * @param map the source map.
     * @return {@link Rectangle} object
     */
    public static Rectangle mapToRect(Map<String, Object> map) {
        return new Rectangle(toSeleniumCoordinate(map.get("x")),
                toSeleniumCoordinate(map.get("y")),
                toSeleniumCoordinate(map.get("height")),
                toSeleniumCoordinate(map.get("width")));
    }

    /**
     * Transforms a map into {@link Point} object.
     *
     * @param map the source map.
     * @return {@link Point} object
     */
    public static Point mapToPoint(Map<String, Object> map) {
        return new Point(toSeleniumCoordinate(map.get("x")), toSeleniumCoordinate(map.get("y")));
    }
}
