package io.appium.java_client.flutter.commands;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Require;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Accessors(chain = true)
@Setter
@Getter
public class DoubleClickParameter extends FlutterCommandParameter {
    private WebElement element;
    private Point offset;


    @Override
    public Map<String, Object> toJson() {
        Require.precondition(element != null || offset != null,
                "Must supply a valid element or offset to perform flutter gesture event");

        Map<String, Object> params = new HashMap<>();
        Optional.ofNullable(element).ifPresent(element -> params.put("origin", element));
        Optional.ofNullable(offset).ifPresent(offset ->
                params.put("offset", Map.of("x", offset.getX(), "y", offset.getY())));
        return Collections.unmodifiableMap(params);
    }
}
