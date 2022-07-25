package io.appium.java_client;

import static io.appium.java_client.TestUtils.resourcePathToLocalPath;

import java.nio.file.Path;

public class TestResources {
    public static Path apiDemosApk() {
        return resourcePathToLocalPath("apps/ApiDemos-debug.apk");
    }

    public static Path testAppZip() {
        return resourcePathToLocalPath("apps/TestApp.app.zip");
    }

    public static Path uiCatalogAppZip() {
        return resourcePathToLocalPath("apps/UICatalog.app.zip");
    }

    public static Path vodQaAppZip() {
        return resourcePathToLocalPath("apps/vodqa.zip");
    }

    public static Path intentExampleApk() {
        return resourcePathToLocalPath("apps/IntentExample.apk");
    }

    public static Path helloAppiumHtml() {
        return resourcePathToLocalPath("html/hello appium - saved page.htm");
    }
}
