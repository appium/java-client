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

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;
import org.openqa.selenium.UnsupportedCommandException;

import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.appium.java_client.android.AndroidMobileCommandHelper.getNetworkConnectionCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.setConnectionCommand;

public interface HasNetworkConnection extends ExecutesMethod {

    /**
     * Set the network connection of the device.
     *
     * @param connection The bitmask of the desired connection
     * @return Connection object, which represents the resulting state
     */
    default ConnectionState setConnection(ConnectionState connection) {
        try {
            CommandExecutionHelper.executeScript(this, "mobile: setConnectivity", ImmutableMap.of(
                    "wifi", connection.isWiFiEnabled(),
                    "data", connection.isDataEnabled(),
                    "airplaneMode", connection.isAirplaneModeEnabled()
            ));
            return getConnection();
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            return new ConnectionState(
                    checkNotNull(
                            CommandExecutionHelper.execute(this, setConnectionCommand(connection.getBitMask()))
                    )
            );
        }
    }

    /**
     * Get the current network settings of the device.
     *
     * @return Connection object, which lets you to inspect the current status
     */
    default ConnectionState getConnection() {
        try {
            Map<String, Object> result = checkNotNull(
                    CommandExecutionHelper.executeScript(this, "mobile: getConnectivity")
            );
            return new ConnectionState(
                    ((boolean) result.get("wifi") ? ConnectionState.WIFI_MASK : 0)
                    | ((boolean) result.get("data") ? ConnectionState.DATA_MASK : 0)
                    | ((boolean) result.get("airplaneMode") ? ConnectionState.AIRPLANE_MODE_MASK  : 0)
            );
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            return new ConnectionState(
                    checkNotNull(
                            CommandExecutionHelper.execute(this, getNetworkConnectionCommand())
                    )
            );
        }
    }
}
