package io.appium.java_client.flutter;

import io.appium.java_client.flutter.options.SupportsFlutterServerLaunchTimeoutOption;
import io.appium.java_client.flutter.options.SupportsFlutterSystemPortOption;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.options.BaseOptions;
import org.openqa.selenium.Capabilities;

import java.util.Map;

/**
 * https://github.com/AppiumTestDistribution/appium-flutter-integration-driver#capabilities-for-appium-flutter-integration-driver
 */
public class FlutterDriverOptions extends BaseOptions<FlutterDriverOptions> implements
        SupportsFlutterSystemPortOption<FlutterDriverOptions>,
        SupportsFlutterServerLaunchTimeoutOption<FlutterDriverOptions> {

    public FlutterDriverOptions() {
        setCommonOptions();
    }

    public FlutterDriverOptions(Capabilities source) {
        super(source);
        setCommonOptions();
    }

    public FlutterDriverOptions(Map<String, ?> source) {
        super(source);
        setCommonOptions();
    }

    private void setCommonOptions() {
        setAutomationName(AutomationName.FLUTTER_INTEGRATION);
    }
}
