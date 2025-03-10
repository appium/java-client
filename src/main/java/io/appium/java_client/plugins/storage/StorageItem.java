package io.appium.java_client.plugins.storage;

import lombok.Value;

@Value
public class StorageItem {
    String name;
    String path;
    long size;
}
