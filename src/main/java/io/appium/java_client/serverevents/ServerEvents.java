package io.appium.java_client.serverevents;

import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * The events log of a server session.
 */
@Data
public class ServerEvents {

    /** The executed commands. */
    public final List<CommandEvent> commands;
    /** The timed events. */
    public final List<TimedEvent> events;
    /** The raw JSON data returned by the server. */
    public final String jsonData;

    /**
     * Saves the raw JSON data to a file.
     *
     * @param output the file to write to
     * @throws IOException if the file cannot be written
     */
    public void save(Path output) throws IOException {
        Files.write(output, this.jsonData.getBytes());
    }
}