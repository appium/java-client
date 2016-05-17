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

package io.appium.java_client;

/**
 * for use with setting Network Connections on a mobile device.
 * Each network option can be enabled or disabled.
 * Current network options available: Airplane Mode, Wifi, Data
 * This class is deprecated and it is going to be removed in the next release
 */
@Deprecated
public class NetworkConnectionSetting {

    public int value = 0;
    private int airplaneMode = 1;
    private int wifi = 2;
    private int data = 4;

    /**
     * @param airplaneMode boolean for airplane mode enabled.
     * @param wifi         boolean for wifi enabled.
     * @param data         boolean for data enabled.
     */
    public NetworkConnectionSetting(boolean airplaneMode, boolean wifi, boolean data) {
        int a = airplaneMode ? this.airplaneMode : 0;
        int b = wifi ? this.wifi : 0;
        int c = data ? this.data : 0;

        value = a | b | c;
    }

    /**
     * Instantiate Network Connection Settings with the straight-up bitmask.
     * See the Mobile JSON Wire Protocol spec for details.
     *
     * @param bitmask a straight-up bitmask.
     */
    public NetworkConnectionSetting(int bitmask) {
        value = bitmask;
    }

    public boolean airplaneModeEnabled() {
        return (value & airplaneMode) != 0;
    }

    public boolean wifiEnabled() {
        return (value & wifi) != 0;
    }

    public boolean dataEnabled() {
        return (value & data) != 0;
    }

    /**
     * it is used for the switching on/off airplane mode.
     * @param enable if flag that twitches on/off airplane mode
     */
    public void setAirplaneMode(boolean enable) {
        if (enable) {
            value = value | airplaneMode;
        } else {
            value = value & ~airplaneMode;
        }
    }

    /**
     * it is used for the switching on/off Wi-Fi mode.
     * @param enable if flag that twitches on/off Wi-Fi mode
     */
    public void setWifi(boolean enable) {
        if (enable) {
            value = value | wifi;
        } else {
            value = value & ~wifi;
        }
    }

    /**
     * it is used for the switching on/off mobile internet.
     * @param enable if flag that twitches on/off mobile internet
     */
    public void setData(boolean enable) {
        if (enable) {
            value = value | data;
        } else {
            value = value & ~data;
        }
    }

    public int hashCode() {
        return value;
    }

    /**
     * {@link java.lang.Object#equals(Object)}
     * it compares integer bitmask values.
     */
    public boolean equals(Object obj) {
        if (obj instanceof Integer) {
            return value == (Integer) obj;
        }
        if (obj instanceof NetworkConnectionSetting) {
            return value == ((NetworkConnectionSetting) obj).value;
        } else {
            return false;
        }
    }

    public String toString() {
        return "{ AirplaneMode: " + airplaneModeEnabled() + ", Wifi: " + wifiEnabled() + ", Data: "
            + dataEnabled() + "}";
    }

}
