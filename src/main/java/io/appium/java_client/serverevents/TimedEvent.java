package io.appium.java_client.serverevents;

import lombok.Data;

import java.util.List;

@Data
public class TimedEvent {
    public final String name;
    public final List<Long> occurrences;
}
