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

import io.appium.java_client.MobileCommand;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.Map;

import static java.util.Locale.ROOT;

/**
 * This util class helps to prepare parameters of Android-specific JSONWP
 * commands.
 */
public class AndroidMobileCommandHelper extends MobileCommand {

    /**
     * This method forms a {@link Map} of parameters for the getting of the current activity.
     *
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> currentActivityCommand() {
        return Map.entry(CURRENT_ACTIVITY, Map.of());
    }

    /**
     * This method forms a {@link Map} of parameters for the getting of the current package.
     *
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> currentPackageCommand() {
        return Map.entry(GET_CURRENT_PACKAGE, Map.of());
    }

    /**
     * returns the information type of the system state which is supported to read
     * as like cpu, memory, network traffic, and battery.
     * @return output - array like below
     *                  [cpuinfo, batteryinfo, networkinfo, memoryinfo]
     *
     */
    public static Map.Entry<String, Map<String, ?>> getSupportedPerformanceDataTypesCommand() {
        return Map.entry(GET_SUPPORTED_PERFORMANCE_DATA_TYPES, Map.of());
    }

    /**
     * returns the resource usage information of the application. the resource is one of the system state
     * which means cpu, memory, network traffic, and battery.
     *
     * @param packageName the package name of the application
     * @param dataType the type of system state which wants to read.
     *                 It should be one of the supported performance data types,
     *                 the return value of the function "getSupportedPerformanceDataTypes"
     * @param dataReadTimeout the number of attempts to read
     * @return table of the performance data, The first line of the table represents the type of data.
     *          The remaining lines represent the values of the data.
     *          in case of battery info : [[power], [23]]
     *          in case of memory info :
     *              [[totalPrivateDirty, nativePrivateDirty, dalvikPrivateDirty, eglPrivateDirty, glPrivateDirty,
     *                        totalPss, nativePss, dalvikPss, eglPss, glPss, nativeHeapAllocatedSize, nativeHeapSize],
     *                        [18360, 8296, 6132, null, null, 42588, 8406, 7024, null, null, 26519, 10344]]
     *          in case of network info :
     *              [[bucketStart, activeTime, rxBytes, rxPackets, txBytes, txPackets, operations, bucketDuration,],
     *                        [1478091600000, null, 1099075, 610947, 928, 114362, 769, 0, 3600000],
     *                        [1478095200000, null, 1306300, 405997, 509, 46359, 370, 0, 3600000]]
     *          in case of network info :
     *              [[st, activeTime, rb, rp, tb, tp, op, bucketDuration],
     *                        [1478088000, null, null, 32115296, 34291, 2956805, 25705, 0, 3600],
     *                        [1478091600, null, null, 2714683, 11821, 1420564, 12650, 0, 3600],
     *                        [1478095200, null, null, 10079213, 19962, 2487705, 20015, 0, 3600],
     *                        [1478098800, null, null, 4444433, 10227, 1430356, 10493, 0, 3600]]
     *          in case of cpu info : [[user, kernel], [0.9, 1.3]]
     */
    public static Map.Entry<String, Map<String, ?>> getPerformanceDataCommand(
                    String packageName, String dataType, int dataReadTimeout) {
        return Map.entry(GET_PERFORMANCE_DATA, Map.of(
                "packageName", packageName,
                "dataType", dataType,
                "dataReadTimeout", dataReadTimeout
        ));
    }

    /**
     * This method forms a {@link Map} of parameters to retrieve the display density of the Android device.
     *
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> getDisplayDensityCommand() {
        return Map.entry(GET_DISPLAY_DENSITY, Map.of());
    }

    /**
     * This method forms a {@link Map} of parameters for the getting of a network connection value.
     *
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> getNetworkConnectionCommand() {
        return Map.entry(GET_NETWORK_CONNECTION, Map.of());
    }

    /**
     * This method forms a {@link Map} of parameters to retrieve visibility and bounds information of the status and
     * navigation bars.
     *
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> getSystemBarsCommand() {
        return Map.entry(GET_SYSTEM_BARS, Map.of());
    }

    /**
     * This method forms a {@link Map} of parameters for the finger print authentication invocation.
     *
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> isLockedCommand() {
        return Map.entry(IS_LOCKED, Map.of());
    }

    /**
     * This method forms a {@link Map} of parameters for the finger print authentication invocation.
     *
     * @param fingerPrintId finger prints stored in Android Keystore system (from 1 to 10)
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> fingerPrintCommand(int fingerPrintId) {
        return Map.entry(FINGER_PRINT, Map.of("fingerprintId", fingerPrintId));
    }

    /**
     * This method forms a {@link Map} of parameters for the notification opening.
     *
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> openNotificationsCommand() {
        return Map.entry(OPEN_NOTIFICATIONS, Map.of());
    }

    /**
     * This method forms a {@link Map} of parameters for the setting of device network connection.
     *
     * @param bitMask The bitmask of the desired connection
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> setConnectionCommand(long bitMask) {
        return Map.entry(SET_NETWORK_CONNECTION, Map.of(
                "name", "network_connection",
                "parameters", Map.of("type", bitMask)
        ));
    }

    /**
     * This method forms a {@link Map} of parameters for the toggling of  location services.
     *
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> toggleLocationServicesCommand() {
        return Map.entry(TOGGLE_LOCATION_SERVICES, Map.of());
    }

    /**
     * This method forms a {@link Map} of parameters for the element.
     *
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> unlockCommand() {
        return Map.entry(UNLOCK, Map.of());
    }


    /**
     * This method forms a {@link Map} of parameters for the element
     * value replacement. It is used against input elements
     *
     * @param remoteWebElement an instance which contains an element ID
     * @param value a new value
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> replaceElementValueCommand(
        RemoteWebElement remoteWebElement, String value) {
        return Map.entry(REPLACE_VALUE, Map.of(
                "id", remoteWebElement.getId(),
                "value", value
        ));
    }

    /**
     * This method forms a {@link Map} of parameters for the element
     * value replacement. It is used against input elements
     *
     * @param phoneNumber The phone number of message sender
     * @param message The message content
     *
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> sendSMSCommand(
            String phoneNumber, String message) {
        return Map.entry(SEND_SMS, Map.of(
                "phoneNumber", phoneNumber,
                "message", message
        ));
    }

    /**
     * This method forms a {@link Map} of parameters for the element
     * value replacement. It is used against input elements
     *
     * @param phoneNumber The phone number of message sender
     * @param gsmCallActions One of available GSM call actions
     *
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> gsmCallCommand(
            String phoneNumber, GsmCallActions gsmCallActions) {
        return Map.entry(GSM_CALL, Map.of(
                "phoneNumber", phoneNumber,
                "action", gsmCallActions.name().toLowerCase(ROOT)
        ));
    }

    /**
     * This method forms a {@link Map} of parameters for the element
     * value replacement. It is used against input elements
     *
     * @param gsmSignalStrength One of available GSM signal strength
     *
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> gsmSignalStrengthCommand(
            GsmSignalStrength gsmSignalStrength) {
        return Map.entry(GSM_SIGNAL,
                Map.of(
                        // https://github.com/appium/appium/issues/12234
                        "signalStrengh", gsmSignalStrength.ordinal(),
                        "signalStrength", gsmSignalStrength.ordinal()
                ));
    }

    /**
     * This method forms a {@link Map} of parameters for the element
     * value replacement. It is used against input elements
     *
     * @param gsmVoiceState One of available GSM voice state
     *
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> gsmVoiceCommand(
            GsmVoiceState gsmVoiceState) {
        return Map.entry(GSM_VOICE, Map.of("state", gsmVoiceState.name().toLowerCase(ROOT)));
    }

    /**
     * This method forms a {@link Map} of parameters for the element
     * value replacement. It is used against input elements
     *
     * @param networkSpeed One of possible NETWORK_SPEED values
     *
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> networkSpeedCommand(
            NetworkSpeed networkSpeed) {
        return Map.entry(NETWORK_SPEED, Map.of("netspeed", networkSpeed.name().toLowerCase(ROOT)));
    }

    /**
     * This method forms a {@link Map} of parameters for the element
     * value replacement. It is used against input elements
     *
     * @param percent A number in range [0, 4]
     *
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> powerCapacityCommand(
            int percent) {
        return Map.entry(POWER_CAPACITY, Map.of("percent", percent));
    }

    /**
     * This method forms a {@link Map} of parameters for the element
     * value replacement. It is used against input elements
     *
     * @param powerACState One of available power AC state
     *
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> powerACCommand(
            PowerACState powerACState) {
        return Map.entry(POWER_AC_STATE, Map.of("state", powerACState.name().toLowerCase(ROOT)));
    }

    /**
     * This method forms a {@link Map} of parameters for the toggling of  wifi.
     *
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> toggleWifiCommand() {
        return Map.entry(TOGGLE_WIFI, Map.of());
    }

    /**
     * This method forms a {@link Map} of parameters for the toggle airplane mode.
     *
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> toggleAirplaneCommand() {
        return Map.entry(TOGGLE_AIRPLANE_MODE, Map.of());
    }

    /**
     * This method forms a {@link Map} of parameters for the toggle data.
     *
     * @return a key-value pair. The key is the command name. The value is a {@link Map} command arguments.
     */
    @Deprecated
    public static Map.Entry<String, Map<String, ?>> toggleDataCommand() {
        return Map.entry(TOGGLE_DATA, Map.of());
    }
}
