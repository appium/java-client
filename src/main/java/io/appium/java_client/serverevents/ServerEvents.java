package io.appium.java_client.serverevents;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.Data;

@Data
public class ServerEvents {

    public final List<CommandEvent> commands;
    public final List<TimedEvent> events;
    public final String jsonData;

    public void save(Path output) throws IOException  {
        Files.write(output, this.jsonData.getBytes());
    }
}