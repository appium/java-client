package io.appium.java_client.ios;

import java.nio.file.Path;

import static io.appium.java_client.TestUtils.resourcePathToLocalPath;

public class TestResources {
    private TestResources() {
    }

    public static Path testAppZip() {
        return resourcePathToLocalPath("TestApp.app.zip");
    }

    public static Path uiCatalogAppZip() {
        return resourcePathToLocalPath("UICatalog.app.zip");
    }

    public static Path vodQaAppZip() {
        return resourcePathToLocalPath("vodqa.zip");
    }
}
