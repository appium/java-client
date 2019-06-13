package io.appium.java_client;

public class AppiumServerStatus {

    AppiumServerStatus(String version) {
        this.version = version;
    }

    String version;

    public String getVersion() {
        return version;
    }
}
