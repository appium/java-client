package io.appium.java_client.serverevents;

import lombok.Data;

@Data
public class CustomEvent {
    private String vendor;
    private String eventName;
}