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

    /**
     * Creates a new instance with the default Flutter driver options.
     */
    public FlutterDriverOptions() {
        setDefaultOptions();
    }

    /**
     * Creates a new instance from the given capabilities with the default Flutter driver options.
     *
     * @param source the capabilities to copy
     */
    public FlutterDriverOptions(Capabilities source) {
        super(source);
        setDefaultOptions();
    }

    /**
     * Creates a new instance from the given map with the default Flutter driver options.
     *
     * @param source the capabilities to copy
     */
    public FlutterDriverOptions(Map<String, ?> source) {
        super(source);
        setDefaultOptions();
    }

    /**
     * Merges the given UiAutomator2 options into these options.
     *
     * @param uiAutomator2Options the Android options to merge
     * @return self instance for chaining
     */
    public FlutterDriverOptions setUiAutomator2Options(UiAutomator2Options uiAutomator2Options) {
        return setDefaultOptions(merge(uiAutomator2Options));
    }

    /**
     * Merges the given XCUITest options into these options.
     *
     * @param xcuiTestOptions the iOS options to merge
     * @return self instance for chaining
     */
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
