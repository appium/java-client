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

package io.appium.java_client.ios;


import static org.junit.jupiter.api.Assertions.assertEquals;

import io.appium.java_client.Setting;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class SettingTest extends AppIOSTest {

    @Test
    public void testSetShouldUseCompactResponses() {
        assertEquals(true, driver.getSettings()
                .get(Setting.SHOULD_USE_COMPACT_RESPONSES.toString()));
        driver.setShouldUseCompactResponses(false);
        assertEquals(false, driver.getSettings()
                .get(Setting.SHOULD_USE_COMPACT_RESPONSES.toString()));
    }

    @Test
    public void testSetElementResponseAttributes() {
        assertEquals("", driver.getSettings()
                .get(Setting.ELEMENT_RESPONSE_ATTRIBUTES.toString()));
        driver.setElementResponseAttributes("type,label");
        assertEquals("type,label", driver.getSettings()
                .get(Setting.ELEMENT_RESPONSE_ATTRIBUTES.toString()));
    }

    @Test
    public void testSetMjpegServerScreenshotQuality() {
        assertEquals(25L, driver.getSettings()
                .get(Setting.MJPEG_SERVER_SCREENSHOT_QUALITY.toString()));
        driver.setMjpegServerScreenshotQuality(0);
        assertEquals(0L, driver.getSettings()
                .get(Setting.MJPEG_SERVER_SCREENSHOT_QUALITY.toString()));
    }

    @Test
    public void testSetMjpegServerFramerate() {
        assertEquals(10L, driver.getSettings()
                .get(Setting.MJPEG_SERVER_FRAMERATE.toString()));
        driver.setMjpegServerFramerate(60);
        assertEquals(60L, driver.getSettings()
                .get(Setting.MJPEG_SERVER_FRAMERATE.toString()));
    }

    @Test
    public void testSetScreenshotQuality() {
        assertEquals(1L, driver.getSettings()
                .get(Setting.SCREENSHOT_QUALITY.toString()));
        driver.setScreenshotQuality(2);
        assertEquals(2L, driver.getSettings()
                .get(Setting.SCREENSHOT_QUALITY.toString()));
    }

    @Test
    public void testSetMjpegScalingFactor() {
        driver.setMjpegScalingFactor(1);
        assertEquals(1L, driver.getSettings()
                .get(Setting.MJPEG_SCALING_FACTOR.toString()));
    }

    @Test
    public void testSetKeyboardAutocorrection() {
        driver.setKeyboardAutocorrection(true);
        assertEquals(true, driver.getSettings()
                .get(Setting.KEYBOARD_AUTOCORRECTION.toString()));
    }

    @Test
    public void testSetKeyboardPrediction() {
        driver.setKeyboardPrediction(true);
        assertEquals(true, driver.getSettings()
                .get(Setting.KEYBOARD_PREDICTION.toString()));
    }

    @Test
    public void testSettingByString() {
        assertEquals(true, driver.getSettings()
                .get("shouldUseCompactResponses"));
        driver.setSetting("shouldUseCompactResponses", false);
        assertEquals(false, driver.getSettings()
                .get("shouldUseCompactResponses"));
        driver.setSetting("shouldUseCompactResponses", true);
        assertEquals(true, driver.getSettings()
                .get("shouldUseCompactResponses"));
    }

    @Test
    public void setMultipleSettings() {
        EnumMap<Setting, Object> enumSettings = new EnumMap<>(Setting.class);
        enumSettings.put(Setting.IGNORE_UNIMPORTANT_VIEWS, true);
        enumSettings.put(Setting.ELEMENT_RESPONSE_ATTRIBUTES, "type,label");
        driver.setSettings(enumSettings);
        Map<String, Object> actual = driver.getSettings();
        assertEquals(true, actual.get(Setting.IGNORE_UNIMPORTANT_VIEWS.toString()));
        assertEquals("type,label", actual.get(Setting.ELEMENT_RESPONSE_ATTRIBUTES.toString()));

        Map<String, Object> mapSettings = new HashMap<>();
        mapSettings.put(Setting.IGNORE_UNIMPORTANT_VIEWS.toString(), false);
        mapSettings.put(Setting.ELEMENT_RESPONSE_ATTRIBUTES.toString(), "");
        driver.setSettings(mapSettings);
        actual = driver.getSettings();
        assertEquals(false, actual.get(Setting.IGNORE_UNIMPORTANT_VIEWS.toString()));
        assertEquals("", actual.get(Setting.ELEMENT_RESPONSE_ATTRIBUTES.toString()));
    }
}
