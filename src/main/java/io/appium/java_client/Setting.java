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
 * <br>
 * <a href="https://appium.io/docs/en/advanced-concepts/settings/">
 *   https://appium.io/docs/en/advanced-concepts/settings/</a>
 */
public enum Setting {

    // Android
    /** Whether to ignore the views that are not important for accessibility (Android). */
    IGNORE_UNIMPORTANT_VIEWS("ignoreUnimportantViews"),
    /** The time to wait for the app to become idle before an action (Android). */
    WAIT_FOR_IDLE_TIMEOUT("waitForIdleTimeout"),
    /** The time to wait for a selector to match an element (Android). */
    WAIT_FOR_SELECTOR_TIMEOUT("waitForSelectorTimeout"),
    /** The time to wait for the scroll action acknowledgment (Android). */
    WAIT_SCROLL_ACKNOWLEDGMENT_TIMEOUT("scrollAcknowledgmentTimeout"),
    /** The time to wait for the action acknowledgment (Android). */
    WAIT_ACTION_ACKNOWLEDGMENT_TIMEOUT("actionAcknowledgmentTimeout"),
    /** Whether invisible elements are included in the page source and lookups (Android). */
    ALLOW_INVISIBLE_ELEMENTS("allowInvisibleElements"),
    /** Whether to enable the notification (toast) listener (Android). */
    ENABLE_NOTIFICATION_LISTENER("enableNotificationListener"),
    /** Whether to normalize the tag names of the page source elements (Android). */
    NORMALIZE_TAG_NAMES("normalizeTagNames"),
    /** The delay between key injections while typing (Android). */
    KEY_INJECTION_DELAY("keyInjectionDelay"),
    /** Whether to shut down the server when the device power is disconnected (Android). */
    SHUTDOWN_ON_POWER_DISCONNECT("shutdownOnPowerDisconnect"),
    /** Whether to track the scroll events (Android). */
    TRACK_SCROLL_EVENTS("trackScrollEvents"),
    // iOS
    /** The quality of the screenshots in the MJPEG stream (iOS). */
    MJPEG_SERVER_SCREENSHOT_QUALITY("mjpegServerScreenshotQuality"),
    /** The frame rate of the MJPEG stream (iOS). */
    MJPEG_SERVER_FRAMERATE("mjpegServerFramerate"),
    /** The quality of the screenshots taken by the driver (iOS). */
    SCREENSHOT_QUALITY("screenshotQuality"),
    /** Whether web taps are converted into x/y taps (iOS). */
    NATIVE_WEB_TAP("nativeWebTap"),
    /** The scaling factor of the MJPEG stream screenshots (iOS). */
    MJPEG_SCALING_FACTOR("mjpegScalingFactor"),
    /** Whether to enable the keyboard autocorrection (iOS). */
    KEYBOARD_AUTOCORRECTION("keyboardAutocorrection"),
    /** Whether to enable the keyboard prediction (iOS). */
    KEYBOARD_PREDICTION("keyboardPrediction"),
    /** Whether to bind the elements by their index (iOS). */
    BOUND_ELEMENTS_BY_INDEX("boundElementsByIndex"),
    // Android and iOS
    /** Whether to return compact responses from the element lookups (Android and iOS). */
    SHOULD_USE_COMPACT_RESPONSES("shouldUseCompactResponses"),
    /** The element attributes included in the element lookup responses (Android and iOS). */
    ELEMENT_RESPONSE_ATTRIBUTES("elementResponseAttributes"),
    // All platforms
    /** The strategy used to tap the image elements. */
    IMAGE_ELEMENT_TAP_STRATEGY("imageElementTapStrategy"),
    /** The minimum similarity score of an image match. */
    IMAGE_MATCH_THRESHOLD("imageMatchThreshold"),
    /** Whether to fix the dimensions of the screenshot used for the image lookup. */
    FIX_IMAGE_FIND_SCREENSHOT_DIMENSIONS("fixImageFindScreenshotDims"),
    /** Whether to fix the size of the image template used for the image lookup. */
    FIX_IMAGE_TEMPLATE_SIZE("fixImageTemplateSize"),
    /** Whether to check the image elements for staleness. */
    CHECK_IMAGE_ELEMENT_STALENESS("checkForImageElementStaleness"),
    /** Whether to update the position of the image elements automatically. */
    UPDATE_IMAGE_ELEMENT_POSITION("autoUpdateImageElementPosition"),
    /** Whether to fix the scale of the image template. */
    FIX_IMAGE_TEMPLATE_SCALE("fixImageTemplateScale"),
    /** The default scale of the image template. */
    DEFAULT_IMAGE_TEMPLATE_SCALE("defaultImageTemplateScale"),
    /** Whether to return the matched image in the image lookup result. */
    GET_MATCHED_IMAGE_RESULT("getMatchedImageResult");

    private final String name;

    Setting(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
