package io.appium.java_client.flutter.commands;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

import java.util.Map;

public abstract class FlutterCommandParameter {

    /**
     * Parses an Appium Flutter locator into a Map representation suitable for Flutter Integration Driver.
     *
     * @param by The FlutterBy instance representing the locator to parse.
     * @return A Map containing the parsed locator information with keys using and value.
     */
    protected static Map<String, Object> parseFlutterLocator(AppiumBy.FlutterBy by) {
        By.Remotable.Parameters parameters = by.getRemoteParameters();
        return Map.of(
                "using", parameters.using(),
                "value", parameters.value()
        );
    }

    public abstract Map<String, Object> toJson();
}
