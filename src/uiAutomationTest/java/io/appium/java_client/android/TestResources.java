package io.appium.java_client.android;

import java.nio.file.Path;

import static io.appium.java_client.TestUtils.resourcePathToLocalPath;

public class TestResources {
    private TestResources() {
    }

    public static Path apiDemosApk() {
        return resourcePathToLocalPath("ApiDemos-debug.apk");
    }
}
