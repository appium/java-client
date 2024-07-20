package io.appium.java_client.flutter.commands;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

import java.util.Map;

public interface FlutterCommandOptions {
    /**
     * Parses an {@link AppiumBy.FlutterBy} object into a Map of parameters
     * to be executed by flutter integration driver.
     *
     * @param by The {@link AppiumBy.FlutterBy} object to parse.
     * @return A {@link Map} containing the parsed parameters
     */
    default Map<String, Object> parseBy(AppiumBy.FlutterBy by) {
        By.Remotable.Parameters parameters = by.getRemoteParameters();
        return Map.of(
                "using", parameters.using(),
                "value", parameters.value()
        );
    }

    Map<String, Object> toJson();
}
