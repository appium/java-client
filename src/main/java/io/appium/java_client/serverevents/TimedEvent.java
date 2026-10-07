package io.appium.java_client.serverevents;

import lombok.Data;

import java.util.List;

/**
 * A named server event with the timestamps of its occurrences.
 */
@Data
public class TimedEvent {
    /** The event name. */
    public final String name;
    /** The Unix timestamps of the event occurrences. */
    public final List<Long> occurrences;

    /**
     * Creates a new timed event.
     *
     * @param name the event name
     * @param occurrences the Unix timestamps of the event occurrences
     */
    public TimedEvent(String name, List<Long> occurrences) {
        this.name = name;
        this.occurrences = occurrences;
    }
}
