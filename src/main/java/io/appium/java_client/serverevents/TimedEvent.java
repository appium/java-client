package io.appium.java_client.serverevents;

import java.util.List;
import lombok.Data;

@Data
public class TimedEvent {
    public final String name;
    public final List<Long> occurrences;
}
