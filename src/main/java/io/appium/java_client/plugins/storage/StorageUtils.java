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

package io.appium.java_client.plugins.storage;

import org.openqa.selenium.remote.http.WebSocket;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class StorageUtils {
    private static final int BUFFER_SIZE = 0xFFFF;

    private StorageUtils() {
    }

    /**
     * Calculates SHA1 hex digest of the given file.
     *
     * @param source The file instance to calculate the hash for.
     * @return Hash digest represented as a string of hexadecimal numbers.
     */
    public static String calcSha1Digest(File source) {
        MessageDigest sha1sum;
        try {
            sha1sum = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
        var buffer = new byte[BUFFER_SIZE];
        int bytesRead;
        try (var in = new BufferedInputStream(new FileInputStream(source))) {
            while ((bytesRead = in.read(buffer)) != -1) {
                sha1sum.update(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return byteToHex(sha1sum.digest());
    }

    /**
     * Feeds the content of the given file to the provided web socket.
     *
     * @param source The source file instance.
     * @param socket The destination web socket.
     */
    public static void streamFileToWebSocket(File source, WebSocket socket) {
        var buffer = new byte[BUFFER_SIZE];
        int bytesRead;
        try (var in = new BufferedInputStream(new FileInputStream(source))) {
            while ((bytesRead = in.read(buffer)) != -1) {
                var currentBuffer = new byte[bytesRead];
                System.arraycopy(buffer, 0, currentBuffer, 0, bytesRead);
                socket.sendBinary(currentBuffer);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static String byteToHex(final byte[] hash) {
        var formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        var result = formatter.toString();
        formatter.close();
        return result;
    }
}
