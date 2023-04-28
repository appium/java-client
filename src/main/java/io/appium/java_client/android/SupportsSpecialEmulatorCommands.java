package io.appium.java_client.android;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;
import org.openqa.selenium.UnsupportedCommandException;

import static io.appium.java_client.MobileCommand.GSM_CALL;
import static io.appium.java_client.MobileCommand.GSM_SIGNAL;
import static io.appium.java_client.MobileCommand.GSM_VOICE;
import static io.appium.java_client.MobileCommand.NETWORK_SPEED;
import static io.appium.java_client.MobileCommand.POWER_AC_STATE;
import static io.appium.java_client.MobileCommand.POWER_CAPACITY;
import static io.appium.java_client.MobileCommand.SEND_SMS;

public interface SupportsSpecialEmulatorCommands extends ExecutesMethod {

    /**
     * Emulate send SMS event on the connected emulator.
     *
     * @param phoneNumber The phone number of message sender.
     * @param message   The message content.
     */
    default void sendSMS(String phoneNumber, String message) {
        try {
            CommandExecutionHelper.executeScript(this, "mobile: sendSms", ImmutableMap.of(
               "phoneNumber", phoneNumber,
               "message", message
            ));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            this.execute(SEND_SMS,  ImmutableMap.of(
                    "phoneNumber", phoneNumber,
                    "message", message
            ));
        }
    }

    /**
     * Emulate GSM call event on the connected emulator.
     *
     * @param phoneNumber The phone number of the caller.
     * @param gsmCallAction   One of available {@link GsmCallActions} values.
     */
    default void makeGsmCall(String phoneNumber, GsmCallActions gsmCallAction) {
        try {
            CommandExecutionHelper.executeScript(this, "mobile: gsmCall", ImmutableMap.of(
                    "phoneNumber", phoneNumber,
                    "action", gsmCallAction.toString().toLowerCase()
            ));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            this.execute(GSM_CALL, ImmutableMap.of(
                    "phoneNumber", phoneNumber,
                    "action", gsmCallAction.toString().toLowerCase()
            ));
        }
    }

    /**
     * Emulate GSM signal strength change event on the connected emulator.
     *
     * @param gsmSignalStrength   One of available {@link GsmSignalStrength} values.
     */
    default void setGsmSignalStrength(GsmSignalStrength gsmSignalStrength) {
        try {
            CommandExecutionHelper.executeScript(this, "mobile: gsmSignal", ImmutableMap.of(
               "strength", gsmSignalStrength.ordinal()
            ));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            this.execute(GSM_SIGNAL, ImmutableMap.of(
                    "signalStrengh", gsmSignalStrength.ordinal(),
                    "signalStrength", gsmSignalStrength.ordinal()
            ));
        }
    }

    /**
     * Emulate GSM voice event on the connected emulator.
     *
     * @param gsmVoiceState   One of available {@link GsmVoiceState} values.
     */
    default void setGsmVoice(GsmVoiceState gsmVoiceState) {
        try {
            CommandExecutionHelper.executeScript(this, "mobile: gsmVoice", ImmutableMap.of(
                    "state", gsmVoiceState.toString().toLowerCase()
            ));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            this.execute(GSM_VOICE, ImmutableMap.of(
                    "state", gsmVoiceState.name().toLowerCase()
            ));
        }
    }

    /**
     * Emulate network speed change event on the connected emulator.
     *
     * @param networkSpeed   One of available {@link NetworkSpeed} values.
     */
    default void setNetworkSpeed(NetworkSpeed networkSpeed) {
        try {
            CommandExecutionHelper.executeScript(this, "mobile: networkSpeed", ImmutableMap.of(
               "speed", networkSpeed.toString().toLowerCase()
            ));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            this.execute(NETWORK_SPEED, ImmutableMap.of(
                    "netspeed", networkSpeed.name().toLowerCase()
            ));
        }
    }

    /**
     * Emulate power capacity change on the connected emulator.
     *
     * @param percent   Percentage value in range [0, 100].
     */
    default void setPowerCapacity(int percent) {
        try {
            CommandExecutionHelper.executeScript(this, "mobile: powerCapacity", ImmutableMap.of(
                    "percent", percent
            ));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            this.execute(POWER_CAPACITY, ImmutableMap.of(
                    "percent", percent
            ));
        }
    }

    /**
     * Emulate power state change on the connected emulator.
     *
     * @param powerACState   One of available {@link PowerACState} values.
     */
    default void setPowerAC(PowerACState powerACState) {
        try {
            CommandExecutionHelper.executeScript(this, "mobile: powerAC", ImmutableMap.of(
                    "state", powerACState.toString().toLowerCase()
            ));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            this.execute(POWER_AC_STATE, ImmutableMap.of(
                    "state", powerACState.name().toLowerCase()
            ));
        }
    }

}
