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

package io.appium.java_client.ws;

/**
 * This is the basic interface for all web socket message handlers.
 */
public interface MessagesHandler<T> {
    /**
     * This event is fired when the client is
     * successfully connected to a web socket.
     */
    void onConnected();

    /**
     * This event is fired when the client is
     * disconnected from a web socket.
     */
    void onDisconnected();

    /**
     * This event is fired when there is an error
     * in the web socket connection.
     *
     * @param cause the actual error reason.
     */
    void onError(Throwable cause);

    /**
     * This event is fired when the client receives
     * a new message from a web socket.
     *
     * @param message the actual web socket message content
     */
    void onMessage(T message);
}
