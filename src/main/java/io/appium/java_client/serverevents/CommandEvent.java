package io.appium.java_client.serverevents;

import lombok.Data;

@Data
public class CommandEvent {
    public final String name;
    public final long startTimestamp;
    public final long endTimestamp;
}
