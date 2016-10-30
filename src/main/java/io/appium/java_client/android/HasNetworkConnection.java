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

import static io.appium.java_client.android.AndroidMobileCommandHelper.getNetworkConnectionCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.setConnectionCommand;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;
import org.openqa.selenium.WebDriverException;

public interface HasNetworkConnection extends ExecutesMethod {

    /**
     * Set the network connection of the device.
     *
     * @param connection The bitmask of the desired connection
     */
    default void setConnection(Connection connection) {
        CommandExecutionHelper.execute(this, setConnectionCommand(connection));
    }


    /**
     * Get the current network settings of the device.
     *
     * @return Connection object will let you inspect the status
     *     of None, AirplaneMode, Wifi, Data and All connections
     */
    default Connection getConnection() {
        long bitMask = CommandExecutionHelper.execute(this, getNetworkConnectionCommand());
        Connection[] types = Connection.values();

        for (Connection connection: types) {
            if (connection.bitMask == bitMask) {
                return connection;
            }
        }
        throw new WebDriverException("The unknown network connection "
            + "type has been returned. The bitmask is " + bitMask);
    }
}
