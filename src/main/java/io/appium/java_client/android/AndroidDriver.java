package io.appium.java_client.android;

import static com.google.common.base.Preconditions.checkArgument;
import static io.appium.java_client.MobileCommand.CURRENT_ACTIVITY;
import static io.appium.java_client.MobileCommand.END_TEST_COVERAGE;
import static io.appium.java_client.MobileCommand.GET_NETWORK_CONNECTION;
import static io.appium.java_client.MobileCommand.GET_STRINGS;
import static io.appium.java_client.MobileCommand.IS_LOCKED;
import static io.appium.java_client.MobileCommand.KEY_EVENT;
import static io.appium.java_client.MobileCommand.OPEN_NOTIFICATIONS;
import static io.appium.java_client.MobileCommand.PUSH_FILE;
import static io.appium.java_client.MobileCommand.SET_NETWORK_CONNECTION;
import static io.appium.java_client.MobileCommand.START_ACTIVITY;
import static io.appium.java_client.remote.MobileCapabilityType.APP_ACTIVITY;
import static io.appium.java_client.remote.MobileCapabilityType.APP_PACKAGE;
import static io.appium.java_client.remote.MobileCapabilityType.APP_WAIT_ACTIVITY;
import static io.appium.java_client.remote.MobileCapabilityType.APP_WAIT_PACKAGE;
import io.appium.java_client.AndroidKeyCode;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.AppiumSetting;
import io.appium.java_client.NetworkConnectionSetting;
import io.appium.java_client.remote.MobilePlatform;

import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.Response;

import com.google.common.collect.ImmutableMap;

public class AndroidDriver extends AppiumDriver implements
		AndroidDeviceActionShortcuts, HasAppStrings, HasNetworkConnection, PushesFiles, 
		StartsActivity {

	private static final String ANDROID_PLATFORM = MobilePlatform.ANDROID;

	private final String METASTATE_PARAM = "metastate";
	private final String LANGUAGE_PARAM = "language";
	private final String CONNECTION_NAME_PARAM = "name";
	private final String CONNECTION_PARAM_PARAM = "parameters";
	private final String DATA_PARAM = "data";
	private final String INTENT_PARAM = "intent";

	private final String CONNECTION_NAME_VALUE = "network_connection";

	public AndroidDriver(URL remoteAddress, Capabilities desiredCapabilities) {
		super(remoteAddress, substituteMobilePlatform(desiredCapabilities,
				ANDROID_PLATFORM));
	}

	/**
	 * @param key
	 *            code for the key pressed on the Android device
	 * @param metastate
	 *            metastate for the keypress
	 * 
	 * @see AndroidKeyCode
	 * @see AndroidKeyMetastate
	 * @see AndroidDeviceActionShortcuts#sendKeyEvent(int, Integer)
	 */
	@Override
	public void sendKeyEvent(int key, Integer metastate) {
		String[] parameters = new String[] { KEY_CODE, METASTATE_PARAM };
		Object[] values = new Object[] { key, metastate };
		execute(KEY_EVENT, getCommandImmutableMap(parameters, values));
	}

	/**
	 * @see HasAppStrings#getAppStrings()
	 */
	@Override
	public String getAppStrings() {
		Response response = execute(GET_STRINGS);
		return response.getValue().toString();
	}

	/**
	 * @param language
	 *            strings language code
	 * @return a string of all the localized strings defined in the app
	 * 
	 * @see HasAppStrings#getAppStrings(String)
	 */
	@Override
	public String getAppStrings(String language) {
		Response response = execute(GET_STRINGS,
				getCommandImmutableMap(LANGUAGE_PARAM, language));
		return response.getValue().toString();
	}

	/**
	 * @see HasNetworkConnection#getNetworkConnection()
	 */
	@Override
	public NetworkConnectionSetting getNetworkConnection() {
		Response response = execute(GET_NETWORK_CONNECTION);
		return new NetworkConnectionSetting(Integer.parseInt(response
				.getValue().toString()));
	}

	/**
	 * @param connection
	 *            The NetworkConnectionSetting configuration to use for the
	 *            device
	 *            
	 * @see HasNetworkConnection#setNetworkConnection(NetworkConnectionSetting)           
	 */
	@Override
	public void setNetworkConnection(NetworkConnectionSetting connection) {
		// the new version of the webdriver protocol is going forward with
		// sending JSON message which look like
		// {name: "name of endpoint", parameters: "JSON parameters"}
		// this is for webdrivers which run on protocols besides HTTP (like TCP)
		// we're implementing that pattern here, for this new method, but
		// haven't translated it to all other commands yet
		String[] parameters = new String[] { CONNECTION_NAME_PARAM,
				CONNECTION_PARAM_PARAM };
		Object[] values = new Object[] { CONNECTION_NAME_VALUE,
				ImmutableMap.of("type", connection.value) };
		execute(SET_NETWORK_CONNECTION,
				getCommandImmutableMap(parameters, values));
	}
	
	/**
	 * @param remotePath
	 *            Path to file to write data to on remote device
	 * @param base64Data
	 *            Base64 encoded byte array of data to write to remote device
	 * @see PushesFiles#pushFile(String, byte[])                      
	 */
	@Override
	public void pushFile(String remotePath, byte[] base64Data) {
		String[] parameters = new String[] { PATH, DATA_PARAM };
		Object[] values = new Object[] { remotePath, base64Data };
		execute(PUSH_FILE, getCommandImmutableMap(parameters, values));
	}

	/**
	 * @param appPackage
	 *            The package containing the activity. [Required]
	 * @param appActivity
	 *            The activity to start. [Required]
	 * @param appWaitPackage
	 *            Automation will begin after this package starts. [Optional]
	 * @param appWaitActivity
	 *            Automation will begin after this activity starts. [Optional]
	 * @example driver.startActivity("com.foo.bar", ".MyActivity", null, null);
	 * 
	 * @see StartsActivity#startActivity(String, String, String, String)
	 */
	public void startActivity(String appPackage, String appActivity,
			String appWaitPackage, String appWaitActivity)
			throws IllegalArgumentException {

		checkArgument(
				(_isNotNullOrEmpty(appPackage) && _isNotNullOrEmpty(appActivity)),
				String.format("'%s' and '%s' are required.", APP_PACKAGE,
						APP_ACTIVITY));

		appWaitPackage = _isNotNullOrEmpty(appWaitPackage) ? appWaitPackage
				: "";
		appWaitActivity = _isNotNullOrEmpty(appWaitActivity) ? appWaitActivity
				: "";

		ImmutableMap<String, String> parameters = ImmutableMap.of(APP_PACKAGE,
				appPackage, APP_ACTIVITY, appActivity, APP_WAIT_PACKAGE,
				appWaitPackage, APP_WAIT_ACTIVITY, appWaitActivity);

		execute(START_ACTIVITY, parameters);
	}
	
	/**
	 * @param appPackage The package containing the activity. [Required]
	 * @param appActivity The activity to start. [Required]
	 * @example
	 * *.startActivity("com.foo.bar", ".MyActivity");
	 * @see StartsActivity#startActivity(String, String)
	 */	
	@Override
	public void startActivity(String appPackage, String appActivity)
			throws IllegalArgumentException {
		this.startActivity(appPackage, appActivity, null, null);		
	}
	
	/**
	 * Get test-coverage data
	 * 
	 * @param intent
	 *            intent to broadcast
	 * @param path
	 *            path to .ec file
	 */	
	public void endTestCoverage(String intent, String path) {	
		String[] parameters = new String[] { INTENT_PARAM, PATH };
		Object[] values = new Object[] { intent, path };
		execute(END_TEST_COVERAGE, getCommandImmutableMap(parameters, values));
	}
	
	/**
	 * Get the current activity being run on the mobile device
	 */
	public String currentActivity() {
		Response response = execute(CURRENT_ACTIVITY);
		return response.getValue().toString();
	}	
	
	/**
	 * Open the notification shade, on Android devices.
	 */
	public void openNotifications() {
		execute(OPEN_NOTIFICATIONS);
	}	
	
	/**
	 * Check if the device is locked.
	 * 
	 * @return true if device is locked. False otherwise
	 */
	public boolean isLocked() {
		Response response = execute(IS_LOCKED);
		return Boolean.parseBoolean(response.getValue().toString());
	}
	
	/**
	 * Set the `ignoreUnimportantViews` setting. *Android-only method*
	 * 
	 * Sets whether Android devices should use `setCompressedLayoutHeirarchy()`
	 * which ignores all views which are marked IMPORTANT_FOR_ACCESSIBILITY_NO
	 * or IMPORTANT_FOR_ACCESSIBILITY_AUTO (and have been deemed not important
	 * by the system), in an attempt to make things less confusing or faster.
	 * 
	 * @param compress
	 *            ignores unimportant views if true, doesn't ignore otherwise.
	 */
	// Should be moved to the subclass
	public void ignoreUnimportantViews(Boolean compress) {
		setSetting(AppiumSetting.IGNORE_UNIMPORTANT_VIEWS, compress);
	}	

}
