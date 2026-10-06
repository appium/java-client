package io.appium.java_client.android;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;

import java.util.Map;

import static java.util.Locale.ROOT;

public interface SupportsSpecialEmulatorCommands extends ExecutesMethod {

    /**
     * Emulate send SMS event on the connected emulator.
     *
     * @param phoneNumber The phone number of message sender.
     * @param message   The message content.
     */
    default void sendSMS(String phoneNumber, String message) {
        final String extName = "mobile: sendSms";
        CommandExecutionHelper.executeScript(this, extName, Map.of(
                "phoneNumber", phoneNumber,
                "message", message
        ));
    }

    /**
     * Emulate GSM call event on the connected emulator.
     *
     * @param phoneNumber The phone number of the caller.
     * @param gsmCallAction   One of available {@link GsmCallActions} values.
     */
    default void makeGsmCall(String phoneNumber, GsmCallActions gsmCallAction) {
        final String extName = "mobile: gsmCall";
        CommandExecutionHelper.executeScript(this, extName, Map.of(
                "phoneNumber", phoneNumber,
                "action", gsmCallAction.toString().toLowerCase(ROOT)
        ));
    }

    /**
     * Emulate GSM signal strength change event on the connected emulator.
     *
     * @param gsmSignalStrength   One of available {@link GsmSignalStrength} values.
     */
    default void setGsmSignalStrength(GsmSignalStrength gsmSignalStrength) {
        final String extName = "mobile: gsmSignal";
        CommandExecutionHelper.executeScript(this, extName, Map.of(
                "strength", gsmSignalStrength.ordinal()
        ));
    }

    /**
     * Emulate GSM voice event on the connected emulator.
     *
     * @param gsmVoiceState   One of available {@link GsmVoiceState} values.
     */
    default void setGsmVoice(GsmVoiceState gsmVoiceState) {
        final String extName = "mobile: gsmVoice";
        CommandExecutionHelper.executeScript(this, extName, Map.of(
                "state", gsmVoiceState.toString().toLowerCase(ROOT)
        ));
    }

    /**
     * Emulate network speed change event on the connected emulator.
     *
     * @param networkSpeed   One of available {@link NetworkSpeed} values.
     */
    default void setNetworkSpeed(NetworkSpeed networkSpeed) {
        final String extName = "mobile: networkSpeed";
        CommandExecutionHelper.executeScript(this, extName, Map.of(
                "speed", networkSpeed.toString().toLowerCase(ROOT)
        ));
    }

    /**
     * Emulate power capacity change on the connected emulator.
     *
     * @param percent   Percentage value in range [0, 100].
     */
    default void setPowerCapacity(int percent) {
        final String extName = "mobile: powerCapacity";
        CommandExecutionHelper.executeScript(this, extName, Map.of(
                "percent", percent
        ));
    }

    /**
     * Emulate power state change on the connected emulator.
     *
     * @param powerACState   One of available {@link PowerACState} values.
     */
    default void setPowerAC(PowerACState powerACState) {
        final String extName = "mobile: powerAc";
        CommandExecutionHelper.executeScript(this, extName, Map.of(
                "state", powerACState.toString().toLowerCase(ROOT)
        ));
    }

}
