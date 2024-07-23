package io.appium.java_client.flutter.commands;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.openqa.selenium.WebElement;

import java.util.Map;

@Accessors(chain = true)
@Getter
public class DragAndDropParameter extends FlutterCommandParameter {
    private final WebElement source;
    private final WebElement target;

    public DragAndDropParameter(WebElement source, WebElement target) {
        Preconditions.checkArgument(source != null && target != null,
                "Must supply valid source and target element to perform drag and drop event");
        this.source = source;
        this.target = target;
    }

    @Override
    public Map<String, Object> toJson() {
        return Map.of("source", source, "target", target);
    }
}