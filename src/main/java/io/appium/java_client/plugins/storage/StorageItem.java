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

    /**
     * Creates a new storage item.
     *
     * @param name the item name
     * @param path the item path on the server
     * @param size the item size in bytes
     */
    public StorageItem(String name, String path, long size) {
        this.name = name;
        this.path = path;
        this.size = size;
    }
}
