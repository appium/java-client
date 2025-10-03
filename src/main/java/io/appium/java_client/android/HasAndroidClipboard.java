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

package io.appium.java_client.android;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.clipboard.ClipboardContentType;
import io.appium.java_client.clipboard.HasClipboard;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import static io.appium.java_client.MobileCommand.SET_CLIPBOARD;
import static java.util.Locale.ROOT;
import static java.util.Objects.requireNonNull;

public interface HasAndroidClipboard extends HasClipboard {
    /**
     * Set the content of device's clipboard.
     *
     * @param label clipboard data label.
     * @param contentType one of supported content types.
     * @param base64Content base64-encoded content to be set.
     */
    default void setClipboard(String label, ClipboardContentType contentType, byte[] base64Content) {
        CommandExecutionHelper.execute(this, Map.entry(SET_CLIPBOARD,
                Map.of(
                        "content", new String(requireNonNull(base64Content), StandardCharsets.UTF_8),
                        "contentType", contentType.name().toLowerCase(ROOT),
                        "label", requireNonNull(label)
                )
        ));
    }

    /**
     * Set the clipboard text.
     *
     * @param label clipboard data label.
     * @param text The actual text to be set.
     */
    default void setClipboardText(String label, String text) {
        setClipboard(label, ClipboardContentType.PLAINTEXT, Base64
                .getEncoder()
                .encode(text.getBytes(StandardCharsets.UTF_8)));
    }
}
