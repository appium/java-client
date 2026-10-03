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

package io.appium.java_client.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.util.function.Consumer;

public interface WebSocket extends Closeable {
    Logger LOG = LoggerFactory.getLogger(WebSocket.class);

    WebSocket send(Message message);

    default WebSocket sendText(CharSequence data) {
        return send(new TextMessage(data));
    }

    default WebSocket sendBinary(byte[] data) {
        return send(new BinaryMessage(data));
    }

    @Override
    void close();

    /**
     * Receives the events of a WebSocket. All methods have no-op defaults.
     */
    interface Listener extends Consumer<Message> {
        @Override
        default void accept(Message message) {
            if (message instanceof BinaryMessage) {
                onBinary(((BinaryMessage) message).data());
            } else if (message instanceof CloseMessage) {
                onClose(((CloseMessage) message).code(), ((CloseMessage) message).reason());
            } else if (message instanceof TextMessage) {
                onText(((TextMessage) message).text());
            }
        }

        default void onBinary(byte[] data) {
        }

        default void onClose(int code, String reason) {
        }

        default void onText(CharSequence data) {
        }

        /**
         * Handles a connection error. Logs a warning by default.
         *
         * @param cause the error
         */
        default void onError(Throwable cause) {
            String message = cause.getMessage();
            if (message == null && cause.getCause() != null) {
                message = cause.getCause().getMessage();
            }
            LOG.warn("Connection to the WebSocket server failed: {}", message, cause);
        }
    }
}
