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

import java.net.URL;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static io.appium.java_client.MobileCommand.CURRENT_ACTIVITY;
import static io.appium.java_client.MobileCommand.END_TEST_COVERAGE;
import static io.appium.java_client.MobileCommand.GET_NETWORK_CONNECTION;
import static io.appium.java_client.MobileCommand.IS_LOCKED;
import static io.appium.java_client.MobileCommand.OPEN_NOTIFICATIONS;
import static io.appium.java_client.MobileCommand.PRESS_KEY_CODE;
import static io.appium.java_client.MobileCommand.PUSH_FILE;
import static io.appium.java_client.MobileCommand.SET_NETWORK_CONNECTION;
import static io.appium.java_client.MobileCommand.START_ACTIVITY;
import static io.appium.java_client.MobileCommand.TOGGLE_LOCATION_SERVICES;
import static io.appium.java_client.remote.MobileCapabilityType.APP_ACTIVITY;
import static io.appium.java_client.remote.MobileCapabilityType.APP_PACKAGE;
import static io.appium.java_client.remote.MobileCapabilityType.APP_WAIT_ACTIVITY;
import static io.appium.java_client.remote.MobileCapabilityType.APP_WAIT_PACKAGE;
import static io.appium.java_client.remote.MobileCapabilityType.DONT_STOP_APP_ON_RESET;
import org.openqa.selenium.remote.http.HttpClient;

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
		AndroidDeviceActionShortcuts, HasNetworkConnection, PushesFiles,
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
    
    public AndroidDriver(URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
		super(remoteAddress, httpClientFactory, substituteMobilePlatform(desiredCapabilities,
				ANDROID_PLATFORM));
		this.setElementConverter(new JsonToAndroidElementConverter(this));
	}

    public AndroidDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        super(service, substituteMobilePlatform(desiredCapabilities,
                ANDROID_PLATFORM));
        this.setElementConverter(new JsonToAndroidElementConverter(this));
    }
    
    public AndroidDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(service, httpClientFactory, substituteMobilePlatform(desiredCapabilities, 
                ANDROID_PLATFORM));
        this.setElementConverter(new JsonToAndroidElementConverter(this));
    }

    public AndroidDriver(AppiumServiceBuilder builder, Capabilities desiredCapabilities) {
        super(builder, substituteMobilePlatform(desiredCapabilities,
                ANDROID_PLATFORM));
        this.setElementConverter(new JsonToAndroidElementConverter(this));
    }
    
    public AndroidDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(builder, httpClientFactory, substituteMobilePlatform(desiredCapabilities,
                ANDROID_PLATFORM));
        this.setElementConverter(new JsonToAndroidElementConverter(this));
    }
    
    public AndroidDriver(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(httpClientFactory, substituteMobilePlatform(desiredCapabilities,
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

}
