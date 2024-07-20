package io.appium.java_client.flutter.ios;

import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.remote.AutomationName;
import org.openqa.selenium.Capabilities;

import java.util.Map;

public class FlutterIOSOptions extends XCUITestOptions {

    public FlutterIOSOptions() {
        super();
        addDefaultOptions();
    }

    public FlutterIOSOptions(Capabilities source) {
        super(source);
        addDefaultOptions();
    }

    public FlutterIOSOptions(Map<String, ?> source) {
        super(source);
        addDefaultOptions();
    }

    private void addDefaultOptions() {
        setAutomationName(AutomationName.FLUTTER_INTEGRATION);
    }
}
