package io.appium.java_client.android;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.ExecutesMethod;
import io.appium.java_client.MobileCommand;
import org.openqa.selenium.remote.RemoteWebElement;

public interface CanReplaceElementValue extends ExecutesMethod {
    /**
     * Replaces element value with the given one.
     *
     * @param element The destination element.
     * @param value The value to set.
     */
    default void replaceElementValue(RemoteWebElement element, String value) {
        this.execute(MobileCommand.REPLACE_VALUE, ImmutableMap.of(
                "id", element.getId(),
                "value", value
        ));
    }
}
