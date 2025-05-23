package io.appium.java_client.flutter;

import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.flutter.options.SupportsFlutterElementWaitTimeoutOption;
import io.appium.java_client.flutter.options.SupportsFlutterEnableMockCamera;
import io.appium.java_client.flutter.options.SupportsFlutterServerLaunchTimeoutOption;
import io.appium.java_client.flutter.options.SupportsFlutterSystemPortOption;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.options.BaseOptions;
import org.openqa.selenium.Capabilities;

import java.util.Map;

/**
 * Provides options specific to the Appium Flutter Integration Driver.
 *
 * <p>For more details, refer to the
 * <a href="https://github.com/AppiumTestDistribution/appium-flutter-integration-driver#capabilities-for-appium-flutter-integration-driver">capabilities documentation</a></p>
 */
public class FlutterDriverOptions extends BaseOptions<FlutterDriverOptions> implements
        SupportsFlutterSystemPortOption<FlutterDriverOptions>,
        SupportsFlutterServerLaunchTimeoutOption<FlutterDriverOptions>,
        SupportsFlutterElementWaitTimeoutOption<FlutterDriverOptions>,
        SupportsFlutterEnableMockCamera<FlutterDriverOptions> {

    public FlutterDriverOptions() {
        setDefaultOptions();
    }

    public FlutterDriverOptions(Capabilities source) {
        super(source);
        setDefaultOptions();
    }

    public FlutterDriverOptions(Map<String, ?> source) {
        super(source);
        setDefaultOptions();
    }

    public FlutterDriverOptions setUiAutomator2Options(UiAutomator2Options uiAutomator2Options) {
        return setDefaultOptions(merge(uiAutomator2Options));
    }

    public FlutterDriverOptions setXCUITestOptions(XCUITestOptions xcuiTestOptions) {
        return setDefaultOptions(merge(xcuiTestOptions));
    }

    private void setDefaultOptions() {
        setDefaultOptions(this);
    }

    private FlutterDriverOptions setDefaultOptions(FlutterDriverOptions flutterDriverOptions) {
        return flutterDriverOptions.setAutomationName(AutomationName.FLUTTER_INTEGRATION);
    }
}
