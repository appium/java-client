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

package io.appium.java_client.ios.options.webview;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Provides getters and setters for the {@code ignoredWebviewBundleIds} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsIgnoredWebviewBundleIdsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code ignoredWebviewBundleIds} capability.
     */
    String IGNORED_WEBVIEW_BUNDLE_IDS_OPTION = "ignoredWebviewBundleIds";

    /**
     * Array of bundle identifiers to exclude from webview context detection, such as
     * {@code com.apple.amsengagementd}. Defaults to an empty list.
     *
     * @param identifiers Bundle identifiers list to ignore.
     * @return self instance for chaining.
     */
    default T setIgnoredWebviewBundleIds(List<String> identifiers) {
        return amend(IGNORED_WEBVIEW_BUNDLE_IDS_OPTION, identifiers);
    }

    /**
     * Get the list of bundle identifiers excluded from webview context detection.
     * JSON array strings are parsed into a list of bundle identifiers.
     *
     * @return Bundle identifiers list.
     */
    @SuppressWarnings("unchecked")
    default Optional<List<String>> getIgnoredWebviewBundleIds() {
        Object value = getCapability(IGNORED_WEBVIEW_BUNDLE_IDS_OPTION);
        if (value == null) {
            return Optional.empty();
        }
        if (value instanceof List<?>) {
            return Optional.of((List<String>) value);
        }
        if (value instanceof String) {
            return Optional.of(parseIgnoredWebviewBundleIds((String) value));
        }
        throw new IllegalArgumentException(String.format(
                "Cannot parse '%s' capability value of type %s as a list of strings",
                IGNORED_WEBVIEW_BUNDLE_IDS_OPTION,
                value.getClass().getName()
        ));
    }

    private static List<String> parseIgnoredWebviewBundleIds(String rawJson) {
        JsonElement parsed;
        try {
            parsed = JsonParser.parseString(rawJson);
        } catch (JsonSyntaxException e) {
            throw new IllegalArgumentException(String.format(
                    "Invalid JSON array string for '%s': %s",
                    IGNORED_WEBVIEW_BUNDLE_IDS_OPTION,
                    rawJson
            ), e);
        }
        if (!parsed.isJsonArray()) {
            throw new IllegalArgumentException(String.format(
                    "Expected JSON array string for '%s', got: %s",
                    IGNORED_WEBVIEW_BUNDLE_IDS_OPTION,
                    rawJson
            ));
        }
        List<String> bundleIds = new ArrayList<>();
        for (JsonElement element : parsed.getAsJsonArray()) {
            if (!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString()) {
                throw new IllegalArgumentException(String.format(
                        "Expected string elements in JSON array for '%s', got: %s",
                        IGNORED_WEBVIEW_BUNDLE_IDS_OPTION,
                        element
                ));
            }
            bundleIds.add(element.getAsString());
        }
        return Collections.unmodifiableList(bundleIds);
    }
}
