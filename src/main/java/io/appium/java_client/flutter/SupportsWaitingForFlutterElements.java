package io.appium.java_client.flutter;

import io.appium.java_client.flutter.commands.WaitParameter;

public interface SupportsWaitingForFlutterElements extends CanExecuteFlutterScripts {

    /**
     * Waits for an element to become visible on the screen.
     *
     * @param parameter The parameters for waiting, specifying timeout and element details.
     */
    default void waitForVisible(WaitParameter parameter) {
        executeFlutterCommand("waitForVisible", parameter);
    }

    /**
     * Waits for an element to become absent on the screen.
     *
     * @param parameter The parameters for waiting, specifying timeout and element details.
     */
    default void waitForInVisible(WaitParameter parameter) {
        executeFlutterCommand("waitForAbsent", parameter);
    }

}
