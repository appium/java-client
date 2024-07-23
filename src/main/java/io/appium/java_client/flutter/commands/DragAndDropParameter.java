package io.appium.java_client.flutter.commands;

import com.google.common.base.Preconditions;
import lombok.experimental.Accessors;
import org.openqa.selenium.WebElement;

import java.util.Map;

@Accessors(chain = true)
public class DragAndDropParameter extends FlutterCommandParameter {
    private final WebElement source;
    private final WebElement target;

    /**
     * Constructs a new instance of {@code DragAndDropParameter} with the given source and target {@link WebElement}s.
     * Throws an {@link IllegalArgumentException} if either {@code source} or {@code target} is {@code null}.
     *
     * @param source The source {@link WebElement} from which the drag operation starts.
     * @param target The target {@link WebElement} where the drag operation ends.
     * @throws IllegalArgumentException if {@code source} or {@code target} is {@code null}.
     */
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
