package io.appium.java_client.flutter.commands;

import io.appium.java_client.AppiumBy;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Accessors(chain = true)
@Getter
@Setter
public class WaitOptions implements FlutterCommandOptions {
    private WebElement element;
    private AppiumBy.FlutterBy locator;
    private Duration timeout;

    @Override
    public Map<String, Object> toJson() {
        Map<String, Object> args = new HashMap<>();
        args.put("element", element);
        if (locator != null) {
            args.put("locator", locator.toJson());
        }
        if (timeout != null) {
            args.put("timeout", timeout.getSeconds());
        }
        return Collections.unmodifiableMap(args);
    }
}