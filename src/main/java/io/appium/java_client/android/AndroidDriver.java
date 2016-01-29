/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.appium.java_client.android;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.AppiumSetting;
import io.appium.java_client.DisplayMetrics;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.NetworkConnectionSetting;
import io.appium.java_client.android.internal.JsonToAndroidElementConverter;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Response;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static io.appium.java_client.MobileCommand.ADB_INPUT_TEXT;
import static io.appium.java_client.MobileCommand.ADB_SWIPE;
import static io.appium.java_client.MobileCommand.GET_DATE;
import static io.appium.java_client.MobileCommand.GET_DISPLAY_METRICS;
import static io.appium.java_client.MobileCommand.GET_NAVIGATION_BAR_REGION;
import static io.appium.java_client.MobileCommand.HAS_ROOT;
import static io.appium.java_client.MobileCommand.SET_DATE;
import static io.appium.java_client.MobileCommand.SWIPE_UP_HOME_BUTTON;
import static io.appium.java_client.MobileCommand.BROADCAST_INTENT;
import static io.appium.java_client.MobileCommand.CLEAR_DATA;
import static io.appium.java_client.MobileCommand.CURRENT_ACTIVITY;
import static io.appium.java_client.MobileCommand.END_TEST_COVERAGE;
import static io.appium.java_client.MobileCommand.GET_NETWORK_CONNECTION;
import static io.appium.java_client.MobileCommand.IS_LOCKED;
import static io.appium.java_client.MobileCommand.IS_SOFT_KEYBOARD_PRESENT;
import static io.appium.java_client.MobileCommand.LIST_FILES;
import static io.appium.java_client.MobileCommand.OPEN_NOTIFICATIONS;
import static io.appium.java_client.MobileCommand.PRESS_KEY_CODE;
import static io.appium.java_client.MobileCommand.PUSH_FILE;
import static io.appium.java_client.MobileCommand.REMOVE_FILE;
import static io.appium.java_client.MobileCommand.REPLACE_APP;
import static io.appium.java_client.MobileCommand.SET_NETWORK_CONNECTION;
import static io.appium.java_client.MobileCommand.START_ACTIVITY;
import static io.appium.java_client.MobileCommand.STOP_APP;
import static io.appium.java_client.MobileCommand.TOGGLE_LOCATION_SERVICES;
import static io.appium.java_client.remote.MobileCapabilityType.APP_ACTIVITY;
import static io.appium.java_client.remote.MobileCapabilityType.APP_PACKAGE;
import static io.appium.java_client.remote.MobileCapabilityType.APP_WAIT_ACTIVITY;
import static io.appium.java_client.remote.MobileCapabilityType.APP_WAIT_PACKAGE;
import static io.appium.java_client.remote.MobileCapabilityType.DONT_STOP_APP_ON_RESET;

/**
 * @param <RequiredElementType> means the required type from the list of allowed types below 
 * that implement {@link WebElement} Instances of the defined type will be 
 * returned via findElement* and findElements*. 
 * Warning (!!!). Allowed types:<br/>
 * {@link WebElement}<br/>
 * {@link TouchableElement}<br/>
 * {@link RemoteWebElement}<br/>
 * {@link MobileElement}
 * {@link AndroidElement}
 */
public class AndroidDriver<RequiredElementType extends WebElement> extends AppiumDriver<RequiredElementType> implements
		AndroidDeviceActionShortcuts, HasNetworkConnection, PushesFiles, CustomCommands,
		StartsActivity, FindsByAndroidUIAutomator<RequiredElementType> {

	private static final String ANDROID_PLATFORM = MobilePlatform.ANDROID;

	private final String METASTATE_PARAM = "metastate";
	private final String CONNECTION_NAME_PARAM = "name";
	private final String CONNECTION_PARAM_PARAM = "parameters";
	private final String DATA_PARAM = "data";
	private final String INTENT_PARAM = "intent";

	private final String CONNECTION_NAME_VALUE = "network_connection";

	public AndroidDriver(URL remoteAddress, Capabilities desiredCapabilities) {
		super(remoteAddress, substituteMobilePlatform(desiredCapabilities,
				ANDROID_PLATFORM));
		this.setElementConverter(new JsonToAndroidElementConverter(this));
	}

    public AndroidDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        super(service, substituteMobilePlatform(desiredCapabilities,
                ANDROID_PLATFORM));
        this.setElementConverter(new JsonToAndroidElementConverter(this));
    }

    public AndroidDriver(AppiumServiceBuilder builder, Capabilities desiredCapabilities) {
        super(builder, substituteMobilePlatform(desiredCapabilities,
                ANDROID_PLATFORM));
        this.setElementConverter(new JsonToAndroidElementConverter(this));
    }

    public AndroidDriver(Capabilities desiredCapabilities) {
        super(substituteMobilePlatform(desiredCapabilities,
                ANDROID_PLATFORM));
        this.setElementConverter(new JsonToAndroidElementConverter(this));
    }

  /**
   * Scroll forward to the element which has a description or name which contains the input text.
   * The scrolling is performed on the first scrollView present on the UI
   * @param text
   */
  @Override
  public RequiredElementType scrollTo(String text) {
    String uiScrollables = UiScrollable("new UiSelector().descriptionContains(\"" + text + "\")") +
                           UiScrollable("new UiSelector().textContains(\"" + text + "\")");
    return findElementByAndroidUIAutomator(uiScrollables);
  }

  /**
   * Scroll forward to the element which has a description or name which exactly matches the input text.
   * The scrolling is performed on the first scrollView present on the UI
   * @param text
   */
  @Override
  public RequiredElementType scrollToExact(String text) {
    String uiScrollables = UiScrollable("new UiSelector().description(\"" + text + "\")") +
                           UiScrollable("new UiSelector().text(\"" + text + "\")");
    return findElementByAndroidUIAutomator(uiScrollables);
  }

  static String UiScrollable(String uiSelector) {
    return "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(" + uiSelector + ".instance(0));";
  }

	/**
	 * Send a key event to the device
	 *
	 * @param key
	 *            code for the key pressed on the device
	 */
	@Override
	public void pressKeyCode(int key) {
		execute(PRESS_KEY_CODE, getCommandImmutableMap(KEY_CODE, key));
	}

  /**
	 * @param key
	 *            code for the key pressed on the Android device
	 * @param metastate
	 *            metastate for the keypress
	 * 
	 * @see AndroidKeyCode
	 * @see AndroidKeyMetastate
	 * @see AndroidDeviceActionShortcuts#pressKeyCode(int, Integer)
	 */
	@Override
	public void pressKeyCode(int key, Integer metastate) {
		String[] parameters = new String[] { KEY_CODE, METASTATE_PARAM };
		Object[] values = new Object[] { key, metastate };
		execute(PRESS_KEY_CODE, getCommandImmutableMap(parameters, values));
	}

	/**
	 * Send a long key event to the device
	 *
	 * @param key
	 *            code for the long key pressed on the device
	 */
	@Override
	public void longPressKeyCode(int key) {
		execute(PRESS_KEY_CODE, getCommandImmutableMap(KEY_CODE, key));
	}

	/**
	 * @param key
	 *            code for the long key pressed on the Android device
	 * @param metastate
	 *            metastate for the long key press
	 *
	 * @see AndroidKeyCode
	 * @see AndroidKeyMetastate
	 * @see AndroidDeviceActionShortcuts#pressKeyCode(int, Integer)
	 */
	@Override
	public void longPressKeyCode(int key, Integer metastate) {
		String[] parameters = new String[] { KEY_CODE, METASTATE_PARAM };
		Object[] values = new Object[] { key, metastate };
		execute(PRESS_KEY_CODE, getCommandImmutableMap(parameters, values));
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
	 * @param stopApp
	 * 			  If true, target app will be stopped. [Optional]
	 * @example driver.startActivity("com.foo.bar", ".MyActivity", null, null, true);
	 * 
	 * @see StartsActivity#startActivity(String, String, String, String)
	 */
	public void startActivity(String appPackage, String appActivity,
			String appWaitPackage, String appWaitActivity, boolean stopApp)
			throws IllegalArgumentException {

		checkArgument(
				(_isNotNullOrEmpty(appPackage) && _isNotNullOrEmpty(appActivity)),
				String.format("'%s' and '%s' are required.", APP_PACKAGE,
						APP_ACTIVITY));

		appWaitPackage = _isNotNullOrEmpty(appWaitPackage) ? appWaitPackage
				: "";
		appWaitActivity = _isNotNullOrEmpty(appWaitActivity) ? appWaitActivity
				: "";

		ImmutableMap<String, ?> parameters = ImmutableMap.of(APP_PACKAGE,
				appPackage, APP_ACTIVITY, appActivity, APP_WAIT_PACKAGE,
				appWaitPackage, APP_WAIT_ACTIVITY, appWaitActivity,
				DONT_STOP_APP_ON_RESET, !stopApp);

		execute(START_ACTIVITY, parameters);
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

		this.startActivity(appPackage, appActivity, null, null, true);
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

	public void toggleLocationServices() {
		execute(TOGGLE_LOCATION_SERVICES);
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

    /**
     * @throws org.openqa.selenium.WebDriverException This method is not applicable with browser/webview UI.
     */
	@SuppressWarnings("unchecked")
	@Override
	public RequiredElementType findElementByAndroidUIAutomator(String using) throws WebDriverException {
		return (RequiredElementType) findElement("-android uiautomator", using);
	}

    /**
     * @throws WebDriverException This method is not applicable with browser/webview UI.
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<RequiredElementType> findElementsByAndroidUIAutomator(String using) throws WebDriverException {
		return (List<RequiredElementType>) findElements("-android uiautomator", using);
	}

	public void stopApp() {
		execute(STOP_APP);
	}

	public void stopApp(String pkg) {
		execute(STOP_APP, ImmutableMap.of("pkg", pkg));
	}

	public void replaceApp(String appPath) {
		execute(REPLACE_APP, ImmutableMap.of("appPath", appPath));
	}

	public List<String> listFiles(String dir) {
		Response response = execute(LIST_FILES, ImmutableMap.of(PATH, dir));
		return (List<String>) response.getValue();
	}

	public void removeFile(String path) {
		execute(REMOVE_FILE, ImmutableMap.of(PATH, path));
	}

	public boolean isSoftKeyboardPresent() {
		Response response = execute(IS_SOFT_KEYBOARD_PRESENT);
		return (boolean) response.getValue();
	}

	public void broadcastIntent(String intent, String receiver, HashMap<String, Object> keys) {
		String[] parameters = new String[] { "intent", "receiver", "keys" };
		Object[] values = new Object[] { intent, receiver, keys };
		execute(BROADCAST_INTENT, getCommandImmutableMap(parameters, values));
	}

	public void clearData() {
		execute(CLEAR_DATA);
	}

	public void clearData(String pkg) {
		execute(CLEAR_DATA, ImmutableMap.of("pkg", pkg));
	}

	public void swipeUpHomeButton() {
		execute(SWIPE_UP_HOME_BUTTON);
	}

	public void adbSwipe(int x1, int y1, int x2, int y2, int duration) {
		String[] parameters = new String[] { "x1", "y1", "x2", "y2", "duration" };
		Object[] values = new Object[] { x1, y1, x2, y2, duration };
		execute(ADB_SWIPE, getCommandImmutableMap(parameters, values));
	}

	public void adbSwipe(int x1, int y1, int x2, int y2, int duration, int sleep) {
		String[] parameters = new String[] { "x1", "y1", "x2", "y2", "duration", "sleep"};
		Object[] values = new Object[] { x1, y1, x2, y2, duration, sleep };
		execute(ADB_SWIPE, getCommandImmutableMap(parameters, values));
	}

	public Rectangle getNavigationBarRegion() {
		Response response = execute(GET_NAVIGATION_BAR_REGION);
		ArrayList<String> data = (ArrayList<String>) response.getValue();
		int x = Integer.parseInt(data.get(0));
		int y = Integer.parseInt(data.get(1));
		int width = Integer.parseInt(data.get(2)) - x;
		int height = Integer.parseInt(data.get(3)) - y;
		return new Rectangle(x, y, width, height);
	}

	public DisplayMetrics getDisplayMetrics() {
		Response response = execute(GET_DISPLAY_METRICS);
		Map<String, String> map = (Map<String, String>) response.getValue();
		return new DisplayMetrics(map);
	}

	public boolean hasRoot() {
		Response response = execute(HAS_ROOT);
		return (boolean) response.getValue();
	}

	public long getDate() {
		Response response = execute(GET_DATE);
		return (long) response.getValue();
	}

	public void setDate(long time) {
		execute(SET_DATE, ImmutableMap.of("time", time));
	}

	public void adbInputText(String text) {
		execute(ADB_INPUT_TEXT, ImmutableMap.of("text", text));
	}
}
