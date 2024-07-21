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
public class DragAndDropParameter extends FlutterCommandParameter {
    @NonNull
    WebElement source;
    @NonNull
    WebElement target;

    private DragAndDropParameter() {
    }

    @Override
    public Map<String, Object> toJson() {
        return Map.of("source", source, "target", target);
    }
}
