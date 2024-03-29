package io.appium.java_client;

import java.nio.file.Path;

import static io.appium.java_client.TestUtils.resourcePathToLocalPath;

public class TestResources {
    private TestResources() {
    }

    public static Path intentExampleApk() {
        return resourcePathToLocalPath("apps/IntentExample.apk");
    }

    public static Path helloAppiumHtml() {
        return resourcePathToLocalPath("html/hello appium - saved page.htm");
    }
}
