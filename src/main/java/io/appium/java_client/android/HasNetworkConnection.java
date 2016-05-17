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

import io.appium.java_client.NetworkConnectionSetting;

public interface HasNetworkConnection {

    /**
     * Toggles FigthtMode ON/OFF on the device. This is an Android-only method
     */
    void toggleFlightMode();

    /**
     * Toggles Mobile Data ON/OFF on the device. This is an Android-only method
     */
    void toggleData();

    /**
     * Toggles Wifi ON/OFF on the device. This is an Android-only method
     */
    void toggleWiFi();

    /**
    * Get the current network settings of the device.
    *
    * @return NetworkConnectionSetting objects will let you inspect the status
    *     of AirplaneMode, Wifi, Data connections
    */
    @Deprecated
    NetworkConnectionSetting getNetworkConnection();

    /**
     * Set the network connection of the device. This is an Android-only method
     * This method is deprecated and it is going to be removed in the next release.
     * Use {@link #setConnection(Connection)} instead.
     *
     * @param connection The NetworkConnectionSetting configuration to use for the device
     */
    @Deprecated
    void setNetworkConnection(NetworkConnectionSetting connection);


    /**
     * Set the network connection of the device.
     *
     * @param connection The bitmask of the desired connection
     */
    void setConnection(Connection connection);


    /**
     * Get the current network settings of the device.
     *
     * @return Connection object will let you inspect the status
     *     of None, AirplaneMode, Wifi, Data and All connections
     */
    Connection getConnection();
}
