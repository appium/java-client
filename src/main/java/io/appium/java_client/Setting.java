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

/**
 * Enums defining constants for Appium Settings which can be set and toggled during a test session.
 * http://appium.io/docs/en/advanced-concepts/settings/
 */
public enum Setting {

    // Android
    IGNORE_UNIMPORTANT_VIEWS("ignoreUnimportantViews"),
    WAIT_FOR_IDLE_TIMEOUT("waitForIdleTimeout"),
    WAIT_FOR_SELECTOR_TIMEOUT("waitForSelectorTimeout"),
    WAIT_SCROLL_ACKNOWLEDGMENT_TIMEOUT("scrollAcknowledgmentTimeout"),
    WAIT_ACTION_ACKNOWLEDGMENT_TIMEOUT("actionAcknowledgmentTimeout"),
    ALLOW_INVISIBLE_ELEMENTS("allowInvisibleElements"),
    ENABLE_NOTIFICATION_LISTENER("enableNotificationListener"),
    NORMALIZE_TAG_NAMES("normalizeTagNames"),
    KEY_INJECTION_DELAY("keyInjectionDelay"),
    // iOS
    MJPEG_SERVER_SCREENSHOT_QUALITY("mjpegServerScreenshotQuality"),
    MJPEG_SERVER_FRAMERATE("mjpegServerFramerate"),
    SCREENSHOT_QUALITY("screenshotQuality"),
    NATIVE_WEB_TAP("nativeWebTap"),
    // Android and iOS
    SHOULD_USE_COMPACT_RESPONSES("shouldUseCompactResponses"),
    ELEMENT_RESPONSE_ATTRIBUTES("elementResponseAttributes"),
    // All platforms
    IMAGE_ELEMENT_TAP_STRATEGY("imageElementTapStrategy"),
    IMAGE_MATCH_THRESHOLD("imageMatchThreshold"),
    FIX_IMAGE_FIND_SCREENSHOT_DIMENSIONS("fixImageFindScreenshotDims"),
    FIX_IMAGE_TEMPLATE_SIZE("fixImageTemplateSize"),
    CHECK_IMAGE_ELEMENT_STALENESS("checkForImageElementStaleness"),
    UPDATE_IMAGE_ELEMENT_POSITION("autoUpdateImageElementPosition");

    private final String name;

    Setting(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
