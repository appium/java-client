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

package io.appium.java_client.android.connection;

import static io.appium.java_client.android.connection.ConnectionState.AIRPLANE_MODE_MASK;
import static io.appium.java_client.android.connection.ConnectionState.DATA_MASK;
import static io.appium.java_client.android.connection.ConnectionState.WIFI_MASK;

public class ConnectionStateBuilder {
    private int bitMask;

    /**
     * Initializes connection state builder with the default value (all off).
     */
    public ConnectionStateBuilder() {
        this.bitMask = 0;
    }

    /**
     * Initializes connection state builder with the the predefined bit mask.
     * This constructor might be handy to change an existing connection state.
     *
     * @param bitMask the actual initial state bit mask to set
     */
    public ConnectionStateBuilder(int bitMask) {
        this.bitMask = bitMask;
    }

    /**
     * Initializes connection state builder with the the predefined bit mask.
     * This constructor might be handy to change an existing connection state.
     *
     * @param state the actual initial state to set
     */
    public ConnectionStateBuilder(ConnectionState state) {
        this(state.getBitMask());
    }

    /**
     * Sets airplane mode to enabled state if it was disabled.
     * This option only works up to Android 6.
     *
     * @return self instance for chaining
     */
    public ConnectionStateBuilder withAirplaneModeEnabled() {
        this.bitMask = bitMask | AIRPLANE_MODE_MASK;
        return this;
    }

    /**
     * Sets airplane mode to disabled state if it was enabled.
     * This option only works up to Android 6.
     *
     * @return self instance for chaining
     */
    public ConnectionStateBuilder withAirplaneModeDisabled() {
        this.bitMask = bitMask & ~AIRPLANE_MODE_MASK;
        return this;
    }

    /**
     * Sets Wi-Fi connection mode to enabled state if it was disabled.
     *
     * @return self instance for chaining
     */
    public ConnectionStateBuilder withWiFiEnabled() {
        this.bitMask = bitMask | WIFI_MASK;
        return this;
    }

    /**
     * Sets Wi-Fi connection mode to disabled state if it was enabled.
     *
     * @return self instance for chaining
     */
    public ConnectionStateBuilder withWiFiDisabled() {
        this.bitMask = bitMask & ~WIFI_MASK;
        return this;
    }

    /**
     * Sets data connection mode to enabled state if it was disabled.
     * This option only works on rooted devices or on emulators.
     *
     * @return self instance for chaining
     */
    public ConnectionStateBuilder withDataEnabled() {
        this.bitMask = bitMask | DATA_MASK;
        return this;
    }

    /**
     * Sets data connection mode to disabled state if it was enabled.
     * This option only works on rooted devices or on emulators.
     *
     * @return self instance for chaining
     */
    public ConnectionStateBuilder withDataDisabled() {
        this.bitMask = bitMask & ~DATA_MASK;
        return this;
    }

    /**
     * Builds connection state instance, which is ready to be passed as Appium server parameter.
     *
     * @return ConnectionState instance
     */
    public ConnectionState build() {
        return new ConnectionState(bitMask);
    }
}
