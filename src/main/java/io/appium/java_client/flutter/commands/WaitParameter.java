package io.appium.java_client.flutter.commands;

import io.appium.java_client.AppiumBy;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Require;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Accessors(chain = true)
@Getter
@Setter
public class WaitParameter extends FlutterCommandParameter {
    private WebElement element;
    private AppiumBy.FlutterBy locator;
    private Duration timeout;

    @Override
    public Map<String, Object> toJson() {
        Require.precondition(element != null || locator != null,
                "Must supply a valid element or locator to wait for");
        Map<String, Object> params = new HashMap<>();
        Optional.ofNullable(element)
                .ifPresent(element -> params.put("element", element));
        Optional.ofNullable(locator)
                .ifPresent(locator -> params.put("locator", parseFlutterLocator(locator)));
        Optional.ofNullable(timeout)
                .ifPresent(timeout -> params.put("timeout", timeout));

        return Collections.unmodifiableMap(params);
    }
}
