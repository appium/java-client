package io.appium.java_client.flutter;

import io.appium.java_client.flutter.commands.FlutterCommandParameter;
import org.openqa.selenium.JavascriptExecutor;

import java.util.Map;

public interface CanExecuteFlutterScripts extends JavascriptExecutor {

    /**
     * Executes a Flutter-specific script using JavascriptExecutor.
     *
     * @param scriptName The name of the Flutter script to execute.
     * @param parameter  The parameters for the Flutter command.
     * @return The result of executing the script.
     */
    default Object executeFlutterCommand(String scriptName, FlutterCommandParameter parameter) {
        return executeFlutterCommand(scriptName, parameter.toJson());
    }

    /**
     * Executes a Flutter-specific script using JavascriptExecutor.
     *
     * @param scriptName The name of the Flutter script to execute.
     * @param args       The args for the Flutter command in Map format.
     * @return The result of executing the script.
     */
    default Object executeFlutterCommand(String scriptName, Map<String, Object> args) {
        String commandName = String.format("flutter: %s", scriptName);
        return executeScript(commandName, args);
    }

}
