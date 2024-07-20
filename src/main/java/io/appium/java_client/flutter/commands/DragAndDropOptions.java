package io.appium.java_client.flutter.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;
import org.openqa.selenium.WebElement;

import java.util.Map;

@Accessors(chain = true)
@Getter
@AllArgsConstructor
public class DragAndDropOptions implements FlutterCommandOptions {
    @NonNull
    WebElement source;
    @NonNull
    WebElement target;

    private DragAndDropOptions() {
    }

    @Override
    public Map<String, Object> toJson() {
        return Map.of("source", source, "target", target);
    }
}