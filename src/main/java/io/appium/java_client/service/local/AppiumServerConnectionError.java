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

package io.appium.java_client.service.local;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Optional;

@Getter
public class AppiumServerConnectionError extends RuntimeException {
    private static final int MAX_PAYLOAD_LEN = 500;

    private final URL statusUrl;
    private final int responseCode;
    private final Optional<String> payload;

    public AppiumServerConnectionError(URL statusUrl, int responseCode, InputStream body) {
        super(AppiumServerConnectionError.class.getSimpleName());
        this.statusUrl = statusUrl;
        this.responseCode = responseCode;
        this.payload = readResponseStreamSafely(body);
    }

    private static Optional<String> readResponseStreamSafely(InputStream is) {
        try (var br = new BufferedReader(new InputStreamReader(is))) {
            var result = new StringBuilder();
            String currentLine;
            var didCut = false;
            while ((currentLine = br.readLine()) != null) {
                result.append(currentLine).append("\n");
                if (result.toString().length() >= MAX_PAYLOAD_LEN) {
                    didCut = true;
                    break;
                }
            }
            var s = didCut ? (result.toString().trim() + "…") : result.toString().trim();
            return s.trim().isEmpty() ? Optional.empty() : Optional.of(s);
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
