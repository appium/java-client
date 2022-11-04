package io.appium.java_client.android;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;

import static io.appium.java_client.android.AndroidMobileCommandHelper.gsmCallCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.gsmSignalStrengthCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.gsmVoiceCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.networkSpeedCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.powerACCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.powerCapacityCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.sendSMSCommand;

public interface SupportsSpecialEmulatorCommands extends ExecutesMethod {

    /**
     * Emulate send SMS event on the connected emulator.
     *
     * @param phoneNumber The phone number of message sender.
     * @param message   The message content.
     */
    default void sendSMS(String phoneNumber, String message) {
        CommandExecutionHelper.execute(this, sendSMSCommand(phoneNumber, message));
    }

    /**
     * Emulate GSM call event on the connected emulator.
     *
     * @param phoneNumber The phone number of the caller.
     * @param gsmCallActions   One of available {@link GsmCallActions} values.
     */
    default void makeGsmCall(String phoneNumber, GsmCallActions gsmCallActions) {
        CommandExecutionHelper.execute(this, gsmCallCommand(phoneNumber, gsmCallActions));
    }

    /**
     * Emulate GSM signal strength change event on the connected emulator.
     *
     * @param gsmSignalStrength   One of available {@link GsmSignalStrength} values.
     */
    default void setGsmSignalStrength(GsmSignalStrength gsmSignalStrength) {
        CommandExecutionHelper.execute(this, gsmSignalStrengthCommand(gsmSignalStrength));
    }

    /**
     * Emulate GSM voice event on the connected emulator.
     *
     * @param gsmVoiceState   One of available {@link GsmVoiceState} values.
     */
    default void setGsmVoice(GsmVoiceState gsmVoiceState) {
        CommandExecutionHelper.execute(this, gsmVoiceCommand(gsmVoiceState));
    }

    /**
     * Emulate network speed change event on the connected emulator.
     *
     * @param networkSpeed   One of available {@link NetworkSpeed} values.
     */
    default void setNetworkSpeed(NetworkSpeed networkSpeed) {
        CommandExecutionHelper.execute(this, networkSpeedCommand(networkSpeed));
    }

    /**
     * Emulate power capacity change on the connected emulator.
     *
     * @param percent   Percentage value in range [0, 100].
     */
    default void setPowerCapacity(int percent) {
        CommandExecutionHelper.execute(this, powerCapacityCommand(percent));
    }

    /**
     * Emulate power state change on the connected emulator.
     *
     * @param powerACState   One of available {@link PowerACState} values.
     */
    default void setPowerAC(PowerACState powerACState) {
        CommandExecutionHelper.execute(this, powerACCommand(powerACState));
    }

}
