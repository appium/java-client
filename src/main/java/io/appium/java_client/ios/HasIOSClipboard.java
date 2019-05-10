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

import static com.google.common.base.Preconditions.checkNotNull;

import io.appium.java_client.clipboard.ClipboardContentType;
import io.appium.java_client.clipboard.HasClipboard;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.imageio.ImageIO;

public interface HasIOSClipboard extends HasClipboard {
    /**
     * Set an image to the clipboard.
     *
     * @param img the actual image to be set.
     * @throws IOException if the image cannot be decoded in PNG representation
     */
    default void setClipboardImage(BufferedImage img) throws IOException {
        try (final ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ImageIO.write(checkNotNull(img), "png", os);
            setClipboard(ClipboardContentType.IMAGE, Base64
                    .getMimeEncoder()
                    .encode(os.toByteArray()));
        }
    }

    /**
     * Get an image from the clipboard.
     *
     * @return the actual image instance.
     * @throws IOException If the returned image cannot be decoded or if the clipboard is empty.
     */
    default BufferedImage getClipboardImage() throws IOException {
        final byte[] base64decodedBytes = Base64
                .getMimeDecoder()
                .decode(getClipboard(ClipboardContentType.IMAGE));
        return ImageIO.read(new ByteArrayInputStream(base64decodedBytes));
    }

    /**
     * Set an URL to the clipboard.
     *
     * @param url the actual URL to set.
     */
    default void setClipboardUrl(URL url) {
        setClipboard(ClipboardContentType.URL, Base64
                .getMimeEncoder()
                .encode(checkNotNull(url).toString().getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * Get an URL from the clipboard.
     *
     * @return the actual URL instance.
     * @throws MalformedURLException if the URL in the clipboard is not valid or if the clipboard is empty.
     */
    default URL getClipboardUrl() throws MalformedURLException {
        final byte[] base64decodedBytes = Base64
                .getMimeDecoder()
                .decode(getClipboard(ClipboardContentType.URL));
        return new URL(new String(base64decodedBytes, StandardCharsets.UTF_8));
    }
}
