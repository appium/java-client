package io.appium.java_client.serverevents;

import lombok.Data;

/**
 * A custom event to be logged on the server.
 */
@Data
public class CustomEvent {
    private String vendor;
    private String eventName;
}