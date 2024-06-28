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

package io.appium.java_client.clipboard;

import io.appium.java_client.CanRememberExtensionPresence;
import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;
import org.openqa.selenium.UnsupportedCommandException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import static io.appium.java_client.MobileCommand.GET_CLIPBOARD;
import static io.appium.java_client.MobileCommand.SET_CLIPBOARD;
import static java.util.Objects.requireNonNull;

public interface HasClipboard extends ExecutesMethod, CanRememberExtensionPresence {
    /**
     * Set the content of device's clipboard.
     *
     * @param contentType   one of supported content types.
     * @param base64Content base64-encoded content to be set.
     */
    default void setClipboard(ClipboardContentType contentType, byte[] base64Content) {
        final String extName = "mobile: setClipboard";
        var args = Map.of(
                "content", new String(requireNonNull(base64Content), StandardCharsets.UTF_8),
                "contentType", contentType.name().toLowerCase()
        );
        try {
            CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, args);
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            CommandExecutionHelper.execute(this, Map.entry(SET_CLIPBOARD, args));
        }
    }

    /**
     * Get the content of the clipboard.
     *
     * @param contentType one of supported content types.
     * @return the actual content of the clipboard as base64-encoded string or an empty string if the clipboard is empty
     */
    default String getClipboard(ClipboardContentType contentType) {
        final String extName = "mobile: getClipboard";
        var args = Map.of("contentType", contentType.name().toLowerCase());
        try {
            return CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, args);
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            return CommandExecutionHelper.execute(this, Map.entry(GET_CLIPBOARD, args));
        }
    }

    /**
     * Set the clipboard text.
     *
     * @param text The actual text to be set.
     */
    default void setClipboardText(String text) {
        setClipboard(ClipboardContentType.PLAINTEXT, Base64
                .getMimeEncoder()
                .encode(text.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * Get the clipboard text.
     *
     * @return Either the text, which is stored in the clipboard or an empty string if the clipboard is empty
     */
    default String getClipboardText() {
        byte[] base64decodedBytes = Base64
                .getMimeDecoder()
                .decode(getClipboard(ClipboardContentType.PLAINTEXT));
        return new String(base64decodedBytes, StandardCharsets.UTF_8);
    }
}
