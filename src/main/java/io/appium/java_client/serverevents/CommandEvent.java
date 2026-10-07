package io.appium.java_client.serverevents;

import lombok.Data;

/**
 * A server command with its execution time range.
 */
@Data
public class CommandEvent {
    /** The command name. */
    public final String name;
    /** The command start time as a Unix timestamp. */
    public final long startTimestamp;
    /** The command end time as a Unix timestamp. */
    public final long endTimestamp;
}
