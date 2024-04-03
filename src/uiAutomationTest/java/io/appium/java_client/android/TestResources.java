package io.appium.java_client.android;

import java.nio.file.Path;

import static io.appium.java_client.TestUtils.resourcePathToLocalPath;

public class TestResources {
    public static final Path API_DEMOS_APK = resourcePathToLocalPath("ApiDemos-debug.apk");

    private TestResources() {
    }
}
