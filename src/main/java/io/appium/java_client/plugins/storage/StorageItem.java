package io.appium.java_client.plugins.storage;

import lombok.Value;

/**
 * An item stored in the Appium server storage.
 */
@Value
public class StorageItem {
    String name;
    String path;
    long size;
}
