package io.appium.java_client.android;

import static io.appium.java_client.android.AndroidMobileCommandHelper.gsmCallCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.gsmSignalStrengthCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.gsmVoiceCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.networkSpeedCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.powerACCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.powerCapacityCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.sendSMSCommand;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;

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
     * @param gsmCallActions   One of available GSM call actions {@link GsmCallActions}.
     */
    default void gsmCall(String phoneNumber, GsmCallActions gsmCallActions) {
        CommandExecutionHelper.execute(this, gsmCallCommand(phoneNumber, gsmCallActions));
    }

    /**
     * Emulate GSM signal strength change event on the connected emulator.
     *
     * @param gsmSignalStrength   One of available GSM signal strength {@link GsmSignalStrength}.
     */
    default void gsmSignalStrength(GsmSignalStrength gsmSignalStrength) {
        CommandExecutionHelper.execute( this, gsmSignalStrengthCommand(gsmSignalStrength));
    }

    /**
     * Emulate GSM voice event on the connected emulator.
     *
     * @param gsmVoiceState   One of available GSM voice state {@link GsmVoiceState}.
     */
    default void gsmVoice(GsmVoiceState gsmVoiceState) {
        CommandExecutionHelper.execute(this, gsmVoiceCommand(gsmVoiceState));
    }

    /**
     * Emulate network speed change event on the connected emulator.
     *
     * @param networkSpeed   One of available Network Speed values {@link NetworkSpeed}.
     */
    default void networkSpeed(NetworkSpeed networkSpeed) {
        CommandExecutionHelper.execute(this, networkSpeedCommand(networkSpeed));
    }

    /**
     * Emulate power capacity change on the connected emulator.
     *
     * @param percent   Percentage value in range [0, 100].
     */
    default void powerCapacity(int percent) {
        CommandExecutionHelper.execute(this, powerCapacityCommand(percent));
    }

    /**
     * Emulate power state change on the connected emulator.
     *
     * @param powerACState   One of available Power AC state {@link PowerACState}.
     */
    default void powerAC(PowerACState powerACState) {
        CommandExecutionHelper.execute(this, powerACCommand(powerACState));
    }

}
