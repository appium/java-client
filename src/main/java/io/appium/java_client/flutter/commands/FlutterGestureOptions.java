package io.appium.java_client.flutter.commands;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Accessors(chain = true)
@Getter
@Setter
public class FlutterGestureOptions implements FlutterCommandOptions {
    WebElement element;
    Point point;

    @Override
    public Map<String, Object> toJson() {
        Map<String, Object> args = new HashMap<>();
        args.put("origin", element);
        if (point != null) {
            args.put("offset", Map.of("x", point.getX(), "y", point.getY()));
        }
        return Collections.unmodifiableMap(args);
    }
}
