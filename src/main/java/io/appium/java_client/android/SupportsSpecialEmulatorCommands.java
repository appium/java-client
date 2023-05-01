package io.appium.java_client.android;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.CanRememberExtensionPresence;
import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;
import org.openqa.selenium.UnsupportedCommandException;

import java.util.AbstractMap;

import static io.appium.java_client.MobileCommand.GSM_CALL;
import static io.appium.java_client.MobileCommand.GSM_SIGNAL;
import static io.appium.java_client.MobileCommand.GSM_VOICE;
import static io.appium.java_client.MobileCommand.NETWORK_SPEED;
import static io.appium.java_client.MobileCommand.POWER_AC_STATE;
import static io.appium.java_client.MobileCommand.POWER_CAPACITY;
import static io.appium.java_client.MobileCommand.SEND_SMS;

public interface SupportsSpecialEmulatorCommands extends ExecutesMethod, CanRememberExtensionPresence {

    /**
     * Emulate send SMS event on the connected emulator.
     *
     * @param phoneNumber The phone number of message sender.
     * @param message   The message content.
     */
    default void sendSMS(String phoneNumber, String message) {
        final String extName = "mobile: sendSms";
        try {
            CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, ImmutableMap.of(
                    "phoneNumber", phoneNumber,
                    "message", message
            ));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            CommandExecutionHelper.execute(
                    markExtensionAbsence(extName),
                    new AbstractMap.SimpleEntry<>(SEND_SMS,  ImmutableMap.of(
                            "phoneNumber", phoneNumber,
                            "message", message
                    ))
            );
        }
    }

    /**
     * Emulate GSM call event on the connected emulator.
     *
     * @param phoneNumber The phone number of the caller.
     * @param gsmCallAction   One of available {@link GsmCallActions} values.
     */
    default void makeGsmCall(String phoneNumber, GsmCallActions gsmCallAction) {
        final String extName = "mobile: gsmCall";
        try {
            CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, ImmutableMap.of(
                    "phoneNumber", phoneNumber,
                    "action", gsmCallAction.toString().toLowerCase()
            ));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            CommandExecutionHelper.execute(
                    markExtensionAbsence(extName),
                    new AbstractMap.SimpleEntry<>(GSM_CALL, ImmutableMap.of(
                            "phoneNumber", phoneNumber,
                            "action", gsmCallAction.toString().toLowerCase()
                    ))
            );
        }
    }

    /**
     * Emulate GSM signal strength change event on the connected emulator.
     *
     * @param gsmSignalStrength   One of available {@link GsmSignalStrength} values.
     */
    default void setGsmSignalStrength(GsmSignalStrength gsmSignalStrength) {
        final String extName = "mobile: gsmSignal";
        try {
            CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, ImmutableMap.of(
                    "strength", gsmSignalStrength.ordinal()
            ));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            CommandExecutionHelper.execute(
                    markExtensionAbsence(extName),
                    new AbstractMap.SimpleEntry<>(GSM_SIGNAL, ImmutableMap.of(
                            "signalStrengh", gsmSignalStrength.ordinal(),
                            "signalStrength", gsmSignalStrength.ordinal()
                    ))
            );
        }
    }

    /**
     * Emulate GSM voice event on the connected emulator.
     *
     * @param gsmVoiceState   One of available {@link GsmVoiceState} values.
     */
    default void setGsmVoice(GsmVoiceState gsmVoiceState) {
        final String extName = "mobile: gsmVoice";
        try {
            CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, ImmutableMap.of(
                    "state", gsmVoiceState.toString().toLowerCase()
            ));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            CommandExecutionHelper.execute(
                    markExtensionAbsence(extName),
                    new AbstractMap.SimpleEntry<>(GSM_VOICE, ImmutableMap.of(
                            "state", gsmVoiceState.name().toLowerCase()
                    ))
            );
        }
    }

    /**
     * Emulate network speed change event on the connected emulator.
     *
     * @param networkSpeed   One of available {@link NetworkSpeed} values.
     */
    default void setNetworkSpeed(NetworkSpeed networkSpeed) {
        final String extName = "mobile: networkSpeed";
        try {
            CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, ImmutableMap.of(
                    "speed", networkSpeed.toString().toLowerCase()
            ));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            CommandExecutionHelper.execute(
                    markExtensionAbsence(extName),
                    new AbstractMap.SimpleEntry<>(NETWORK_SPEED, ImmutableMap.of(
                            "netspeed", networkSpeed.name().toLowerCase()
                    ))
            );
        }
    }

    /**
     * Emulate power capacity change on the connected emulator.
     *
     * @param percent   Percentage value in range [0, 100].
     */
    default void setPowerCapacity(int percent) {
        final String extName = "mobile: powerCapacity";
        try {
            CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, ImmutableMap.of(
                    "percent", percent
            ));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            CommandExecutionHelper.execute(
                    markExtensionAbsence(extName),
                    new AbstractMap.SimpleEntry<>(POWER_CAPACITY, ImmutableMap.of(
                            "percent", percent
                    ))
            );
        }
    }

    /**
     * Emulate power state change on the connected emulator.
     *
     * @param powerACState   One of available {@link PowerACState} values.
     */
    default void setPowerAC(PowerACState powerACState) {
        final String extName = "mobile: powerAC";
        try {
            CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, ImmutableMap.of(
                    "state", powerACState.toString().toLowerCase()
            ));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            CommandExecutionHelper.execute(
                    markExtensionAbsence(extName),
                    new AbstractMap.SimpleEntry<>(POWER_AC_STATE, ImmutableMap.of(
                            "state", powerACState.name().toLowerCase()
                    ))
            );
        }
    }

}
