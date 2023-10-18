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

import io.appium.java_client.CanRememberExtensionPresence;
import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;
import org.openqa.selenium.UnsupportedCommandException;

import java.util.Map;

import static io.appium.java_client.android.AndroidMobileCommandHelper.getNetworkConnectionCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.setConnectionCommand;
import static java.util.Objects.requireNonNull;

public interface HasNetworkConnection extends ExecutesMethod, CanRememberExtensionPresence {

    /**
     * Set the network connection of the device.
     *
     * @param connection The bitmask of the desired connection
     * @return Connection object, which represents the resulting state
     */
    default ConnectionState setConnection(ConnectionState connection) {
        final String extName = "mobile: setConnectivity";
        try {
            CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, Map.of(
                    "wifi", connection.isWiFiEnabled(),
                    "data", connection.isDataEnabled(),
                    "airplaneMode", connection.isAirplaneModeEnabled()
            ));
            return getConnection();
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            return new ConnectionState(
                    requireNonNull(
                            CommandExecutionHelper.execute(
                                    markExtensionAbsence(extName),
                                    setConnectionCommand(connection.getBitMask())
                            )
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
        final String extName = "mobile: getConnectivity";
        try {
            Map<String, Object> result = requireNonNull(
                    CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName)
            );
            return new ConnectionState(
                    ((boolean) result.get("wifi") ? ConnectionState.WIFI_MASK : 0)
                    | ((boolean) result.get("data") ? ConnectionState.DATA_MASK : 0)
                    | ((boolean) result.get("airplaneMode") ? ConnectionState.AIRPLANE_MODE_MASK  : 0)
            );
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            return new ConnectionState(
                    requireNonNull(
                            CommandExecutionHelper.execute(
                                    markExtensionAbsence(extName),
                                    getNetworkConnectionCommand()
                            )
                    )
            );
        }
    }
}
