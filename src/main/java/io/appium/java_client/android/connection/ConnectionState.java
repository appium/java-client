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

public class ConnectionState {
    public static final int AIRPLANE_MODE_MASK = 0b001;
    public static final int WIFI_MASK = 0b010;
    public static final int DATA_MASK = 0b100;

    private final int bitMask;

    public int getBitMask() {
        return bitMask;
    }

    public ConnectionState(int bitMask) {
        this.bitMask = bitMask;
    }

    /**
     * @return true if airplane mode is enabled.
     */
    public boolean isAirplaneModeEnabled() {
        return (bitMask & AIRPLANE_MODE_MASK) != 0;
    }

    /**
     * @return true if Wi-Fi connection is enabled.
     */
    public boolean isWiFiEnabled() {
        return (bitMask & WIFI_MASK) != 0;
    }

    /**
     * @return true if data connection is enabled.
     */
    public boolean isDataEnabled() {
        return (bitMask & DATA_MASK) != 0;
    }
}

