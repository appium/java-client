/*
 +Copyright 2014 Appium contributors
 +Copyright 2014 Software Freedom Conservancy
 +
 +Licensed under the Apache License, Version 2.0 (the "License");
 +you may not use this file except in compliance with the License.
 +You may obtain a copy of the License at
 +
 +     http://www.apache.org/licenses/LICENSE-2.0
 +
 +Unless required by applicable law or agreed to in writing, software
 +distributed under the License is distributed on an "AS IS" BASIS,
 +WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 +See the License for the specific language governing permissions and
 +limitations under the License.
 + */

package io.appium.java_client;

import static io.appium.java_client.MobileCommand.CLOSE_APP;
import static io.appium.java_client.MobileCommand.COMPLEX_FIND;
import static io.appium.java_client.MobileCommand.CURRENT_ACTIVITY;
import static io.appium.java_client.MobileCommand.END_TEST_COVERAGE;
import static io.appium.java_client.MobileCommand.GET_NETWORK_CONNECTION;
import static io.appium.java_client.MobileCommand.GET_SETTINGS;
import static io.appium.java_client.MobileCommand.GET_STRINGS;
import static io.appium.java_client.MobileCommand.HIDE_KEYBOARD;
import static io.appium.java_client.MobileCommand.INSTALL_APP;
import static io.appium.java_client.MobileCommand.IS_APP_INSTALLED;
import static io.appium.java_client.MobileCommand.IS_LOCKED;
import static io.appium.java_client.MobileCommand.KEY_EVENT;
import static io.appium.java_client.MobileCommand.LAUNCH_APP;
import static io.appium.java_client.MobileCommand.LOCK;
import static io.appium.java_client.MobileCommand.OPEN_NOTIFICATIONS;
import static io.appium.java_client.MobileCommand.PERFORM_MULTI_TOUCH;
import static io.appium.java_client.MobileCommand.PERFORM_TOUCH_ACTION;
import static io.appium.java_client.MobileCommand.PULL_FILE;
import static io.appium.java_client.MobileCommand.PULL_FOLDER;
import static io.appium.java_client.MobileCommand.PUSH_FILE;
import static io.appium.java_client.MobileCommand.REMOVE_APP;
import static io.appium.java_client.MobileCommand.RESET;
import static io.appium.java_client.MobileCommand.RUN_APP_IN_BACKGROUND;
import static io.appium.java_client.MobileCommand.SET_NETWORK_CONNECTION;
import static io.appium.java_client.MobileCommand.SET_SETTINGS;
import static io.appium.java_client.MobileCommand.SET_VALUE;
import static io.appium.java_client.MobileCommand.SHAKE;
import static io.appium.java_client.MobileCommand.START_ACTIVITY;
import io.appium.java_client.internal.JsonToMobileElementConverter;
import io.appium.java_client.remote.MobileCapabilityType;

import java.net.URL;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ContextAware;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.html5.LocationContext;
import org.openqa.selenium.remote.CommandInfo;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.ErrorHandler;
import org.openqa.selenium.remote.ExecuteMethod;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.html5.RemoteLocationContext;
import org.openqa.selenium.remote.http.HttpMethod;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class AppiumDriver extends RemoteWebDriver implements MobileDriver,
		ContextAware, Rotatable, FindsByIosUIAutomation,
		FindsByAndroidUIAutomator, FindsByAccessibilityId, LocationContext,
		DeviceActionShortcuts, TouchShortcuts, InteractsWithFiles, InteractsWithApps {

	private final static ErrorHandler errorHandler = new ErrorHandler(
			new ErrorCodesMobile(), true);
	private URL remoteAddress;
	private RemoteLocationContext locationContext;
	private ExecuteMethod executeMethod;

	//frequently used command parameters
	protected final String KEY_CODE = "keycode";
	protected final String PATH = "path";
	private final String SETTINGS = "settings";
	
	/**
	 * @param originalCapabilities
	 *            the given {@link Capabilities}
	 * @param newPlatform
	 *            a {@link MobileCapabilityType#PLATFORM_NAME} value which has
	 *            to be set up
	 * @return {@link Capabilities} with changed mobile platform value
	 */
	protected static Capabilities substituteMobilePlatform(
			Capabilities originalCapabilities, String newPlatform) {
		DesiredCapabilities dc = new DesiredCapabilities(originalCapabilities);
		dc.setCapability(MobileCapabilityType.PLATFORM_NAME, newPlatform);
		return dc;
	}

	/**
	 * @param param 
	 *            is a parameter name
	 * @param value
	 *            is the parameter value
	 * @return built {@link ImmutableMap}
	 */
	protected static ImmutableMap<String, Object> getCommandImmutableMap(
			String param, Object value) {
		ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
		builder.put(param, value);
		return builder.build();
	}
	
	/**
	 * 
	 * @param params is the array with parameter names
	 * @param values is the array with parameter values
	 * @return built {@link ImmutableMap}
	 */
	protected static ImmutableMap<String, Object> getCommandImmutableMap(
			String[] params, Object[] values) {
		ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
		for (int i=0; i < params.length; i++ ){
			if (_isNotNullOrEmpty(params[i])){
				builder.put(params[i], values[i]);
			}
		}
		return builder.build();
	}	

	public AppiumDriver(URL remoteAddress, Capabilities desiredCapabilities) {

		super(remoteAddress, desiredCapabilities);
		this.setElementConverter(new JsonToMobileElementConverter(this));

		this.executeMethod = new AppiumExecutionMethod(this);
		this.remoteAddress = remoteAddress;
		locationContext = new RemoteLocationContext(executeMethod);

		ImmutableMap.Builder<String, CommandInfo> builder = ImmutableMap
				.builder();
		builder.put(RESET, postC("/session/:sessionId/appium/app/reset"))
				.put(GET_STRINGS,
						postC("/session/:sessionId/appium/app/strings"))
				.put(KEY_EVENT,
						postC("/session/:sessionId/appium/device/keyevent"))
				.put(CURRENT_ACTIVITY,
						getC("/session/:sessionId/appium/device/current_activity"))
				.put(SET_VALUE,
						postC("/session/:sessionId/appium/element/:id/value"))
				.put(PULL_FILE,
						postC("/session/:sessionId/appium/device/pull_file"))
				.put(PULL_FOLDER,
						postC("/session/:sessionId/appium/device/pull_folder"))
				.put(HIDE_KEYBOARD,
						postC("/session/:sessionId/appium/device/hide_keyboard"))
				.put(PUSH_FILE,
						postC("/session/:sessionId/appium/device/push_file"))
				.put(RUN_APP_IN_BACKGROUND,
						postC("/session/:sessionId/appium/app/background"))
				.put(PERFORM_TOUCH_ACTION,
						postC("/session/:sessionId/touch/perform"))
				.put(PERFORM_MULTI_TOUCH,
						postC("/session/:sessionId/touch/multi/perform"))
				.put(IS_APP_INSTALLED,
						postC("/session/:sessionId/appium/device/app_installed"))
				.put(INSTALL_APP,
						postC("/session/:sessionId/appium/device/install_app"))
				.put(REMOVE_APP,
						postC("/session/:sessionId/appium/device/remove_app"))
				.put(LAUNCH_APP, postC("/session/:sessionId/appium/app/launch"))
				.put(CLOSE_APP, postC("/session/:sessionId/appium/app/close"))
				.put(END_TEST_COVERAGE,
						postC("/session/:sessionId/appium/app/end_test_coverage"))
				.put(LOCK, postC("/session/:sessionId/appium/device/lock"))
				.put(IS_LOCKED,
						postC("/session/:sessionId/appium/device/is_locked"))
				.put(SHAKE, postC("/session/:sessionId/appium/device/shake"))
				.put(COMPLEX_FIND,
						postC("/session/:sessionId/appium/app/complex_find"))
				.put(OPEN_NOTIFICATIONS,
						postC("/session/:sessionId/appium/device/open_notifications"))
				.put(GET_NETWORK_CONNECTION,
						getC("/session/:sessionId/network_connection"))
				.put(SET_NETWORK_CONNECTION,
						postC("/session/:sessionId/network_connection"))
				.put(GET_SETTINGS, getC("/session/:sessionId/appium/settings"))
				.put(SET_SETTINGS, postC("/session/:sessionId/appium/settings"))
				.put(START_ACTIVITY,
						postC("/session/:sessionId/appium/device/start_activity"));
		ImmutableMap<String, CommandInfo> mobileCommands = builder.build();

		HttpCommandExecutor mobileExecutor = new HttpCommandExecutor(
				mobileCommands, remoteAddress);
		super.setCommandExecutor(mobileExecutor);

		super.setErrorHandler(errorHandler);
	}

	@Override
	public Response execute(String driverCommand, Map<String, ?> parameters) {

		return super.execute(driverCommand, parameters);
	}

	@Override
	protected Response execute(String command) {
		return execute(command, ImmutableMap.<String, Object> of());
	}

	@Override
	public ExecuteMethod getExecuteMethod() {
		return executeMethod;
	}

	/**
	 * @see InteractsWithApps#resetApp()
	 */
	@Override
	public void resetApp() {
		execute(MobileCommand.RESET);
	}

	/**
	 * Send a key event to the device
	 * 
	 * @param key
	 *            code for the key pressed on the device
	 */
	@Override
	public void sendKeyEvent(int key) {
		execute(KEY_EVENT, getCommandImmutableMap(KEY_CODE, key));
	}

	/**
	 * Checks if a string is null, empty, or whitespace.
	 * 
	 * @param str
	 *            String to check.
	 * 
	 * @return True if str is not null or empty.
	 */
	protected static boolean _isNotNullOrEmpty(String str) {
		return str != null && !str.isEmpty() && str.trim().length() > 0;
	}

	/**
	 * @see InteractsWithFiles#pullFile(String)
	 */
	@Override
	public byte[] pullFile(String remotePath) {
		Response response = execute(PULL_FILE,
				ImmutableMap.of(PATH, remotePath));
		String base64String = response.getValue().toString();

		return DatatypeConverter.parseBase64Binary(base64String);
	}

	/**        
	 * @see InteractsWithFiles#pullFolder(String)        
	 */
	@Override
	public byte[] pullFolder(String remotePath) {
		Response response = execute(PULL_FOLDER,
				ImmutableMap.of(PATH, remotePath));
		String base64String = response.getValue().toString();

		return DatatypeConverter.parseBase64Binary(base64String);
	}

	/**
	 * @see DeviceActionShortcuts#hideKeyboard()
	 */
	@Override
	public void hideKeyboard() {
		execute(HIDE_KEYBOARD);
	}

	/**
	 * @see InteractsWithApps#runAppInBackground(int)
	 */
	@Override
	public void runAppInBackground(int seconds) {
		execute(RUN_APP_IN_BACKGROUND, ImmutableMap.of("seconds", seconds));
	}

	/**
	 * @see PerformsTouchActions#performTouchAction(TouchAction)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public TouchAction performTouchAction(TouchAction touchAction) {
		ImmutableMap<String, ImmutableList> parameters = touchAction
				.getParameters();
		touchAction.clearParameters();
		execute(PERFORM_TOUCH_ACTION, parameters);

		return touchAction;
	}

	/**
	 * @see PerformsTouchActions#performMultiTouchAction(MultiTouchAction)
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void performMultiTouchAction(MultiTouchAction multiAction) {
		ImmutableMap<String, ImmutableList> parameters = multiAction
				.getParameters();
		execute(PERFORM_MULTI_TOUCH, parameters);
	}

	/**
	 *@see TouchShortcuts#tap(int, WebElement, int)
	 */
	@Override
	public void tap(int fingers, WebElement element, int duration) {
		MultiTouchAction multiTouch = new MultiTouchAction(this);

		for (int i = 0; i < fingers; i++) {
			multiTouch.add(createTap(element, duration));
		}

		multiTouch.perform();
	}

	/**
	 * @see TouchShortcuts#tap(int, int, int, int)
	 */
	@Override
	public void tap(int fingers, int x, int y, int duration) {
		MultiTouchAction multiTouch = new MultiTouchAction(this);

		for (int i = 0; i < fingers; i++) {
			multiTouch.add(createTap(x, y, duration));
		}

		multiTouch.perform();
	}

	/**
	 * @see TouchShortcuts#swipe(int, int, int, int, int)
	 */
	@Override
	public void swipe(int startx, int starty, int endx, int endy, int duration) {
		TouchAction touchAction = new TouchAction(this);

		// appium converts press-wait-moveto-release to a swipe action
		touchAction.press(startx, starty).waitAction(duration)
				.moveTo(endx, endy).release();

		touchAction.perform();
	}

	/**
	 * @see TouchShortcuts#pinch(WebElement)
	 */
	@Override
	public void pinch(WebElement el) {
		MultiTouchAction multiTouch = new MultiTouchAction(this);

		Dimension dimensions = el.getSize();
		Point upperLeft = el.getLocation();
		Point center = new Point(upperLeft.getX() + dimensions.getWidth() / 2,
				upperLeft.getY() + dimensions.getHeight() / 2);

		TouchAction action0 = new TouchAction(this)
				.press(el, center.getX(), center.getY() - 100).moveTo(el)
				.release();
		TouchAction action1 = new TouchAction(this)
				.press(el, center.getX(), center.getY() + 100).moveTo(el)
				.release();

		multiTouch.add(action0).add(action1);

		multiTouch.perform();
	}

	/**
	 * @see TouchShortcuts#pinch(int, int)
	 */
	@Override
	public void pinch(int x, int y) {
		MultiTouchAction multiTouch = new MultiTouchAction(this);

		TouchAction action0 = new TouchAction(this).press(x, y - 100)
				.moveTo(x, y).release();
		TouchAction action1 = new TouchAction(this).press(x, y + 100)
				.moveTo(x, y).release();

		multiTouch.add(action0).add(action1);

		multiTouch.perform();
	}

	/**
	 *  @see TouchShortcuts#zoom(WebElement)
	 */  
	@Override
	public void zoom(WebElement el) {
		MultiTouchAction multiTouch = new MultiTouchAction(this);

		Dimension dimensions = el.getSize();
		Point upperLeft = el.getLocation();
		Point center = new Point(upperLeft.getX() + dimensions.getWidth() / 2,
				upperLeft.getY() + dimensions.getHeight() / 2);

		TouchAction action0 = new TouchAction(this).press(el)
				.moveTo(el, center.getX(), center.getY() - 100).release();
		TouchAction action1 = new TouchAction(this).press(el)
				.moveTo(el, center.getX(), center.getY() + 100).release();

		multiTouch.add(action0).add(action1);

		multiTouch.perform();
	}

	/**
	 * @see TouchShortcuts#zoom(int, int)
	 */
	@Override
	public void zoom(int x, int y) {
		MultiTouchAction multiTouch = new MultiTouchAction(this);

		TouchAction action0 = new TouchAction(this).press(x, y)
				.moveTo(x, y - 100).release();
		TouchAction action1 = new TouchAction(this).press(x, y)
				.moveTo(x, y + 100).release();

		multiTouch.add(action0).add(action1);

		multiTouch.perform();
	}

	/**
	 * @see InteractsWithApps#isAppInstalled(String)
	 */
	@Override
	public boolean isAppInstalled(String bundleId) {
		Response response = execute(IS_APP_INSTALLED,
				ImmutableMap.of("bundleId", bundleId));

		return Boolean.parseBoolean(response.getValue().toString());
	}

	/**
	 * @see InteractsWithApps#installApp(String)
	 */
	@Override
	public void installApp(String appPath) {
		execute(INSTALL_APP, ImmutableMap.of("appPath", appPath));
	}

	/**
	 * @see InteractsWithApps#removeApp(String)
	 */
	@Override
	public void removeApp(String bundleId) {
		execute(REMOVE_APP, ImmutableMap.of("bundleId", bundleId));
	}

	/**
	 * @see InteractsWithApps#launchApp()
	 */
	@Override
	public void launchApp() {
		execute(LAUNCH_APP);
	}

	/**
	 * @see InteractsWithApps#closeApp()
	 */
	@Override
	public void closeApp() {
		execute(CLOSE_APP);
	}

	/**
	 * Get settings stored for this test session It's probably better to use a
	 * convenience function, rather than use this function directly. Try finding
	 * the method for the specific setting you want to read
	 * 
	 * @return JsonObject, a straight-up hash of settings
	 */
	public JsonObject getSettings() {
		Response response = execute(GET_SETTINGS);

		JsonParser parser = new JsonParser();
		JsonObject settings = (JsonObject) parser.parse(response.getValue()
				.toString());

		return settings;
	}

	/**
	 * Set settings for this test session It's probably better to use a
	 * convenience function, rather than use this function directly. Try finding
	 * the method for the specific setting you want to change
	 * 
	 * @param settings
	 *            Map of setting keys and values
	 */
	private void setSettings(ImmutableMap<?, ?> settings) {
		execute(SET_SETTINGS, getCommandImmutableMap(SETTINGS, settings));
	}

	/**
	 * Set a setting for this test session It's probably better to use a
	 * convenience function, rather than use this function directly. Try finding
	 * the method for the specific setting you want to change
	 * 
	 * @param setting
	 *            AppiumSetting you wish to set
	 * @param value
	 *            value of the setting
	 */
	protected void setSetting(AppiumSetting setting, Object value) {
		setSettings(getCommandImmutableMap(setting.toString(), value));
	}

	@Override
	public WebDriver context(String name) {
		if (!_isNotNullOrEmpty(name)) {
			throw new IllegalArgumentException("Must supply a context name");
		}

		execute(DriverCommand.SWITCH_TO_CONTEXT, ImmutableMap.of("name", name));
		return AppiumDriver.this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> getContextHandles() {
		Response response = execute(DriverCommand.GET_CONTEXT_HANDLES);
		Object value = response.getValue();
		try {
			List<String> returnedValues = (List<String>) value;
			return new LinkedHashSet<String>(returnedValues);
		} catch (ClassCastException ex) {
			throw new WebDriverException(
					"Returned value cannot be converted to List<String>: "
							+ value, ex);
		}
	}

	@Override
	public String getContext() {
		String contextName = String.valueOf(execute(
				DriverCommand.GET_CURRENT_CONTEXT_HANDLE).getValue());
		if (contextName.equals("null")) {
			return null;
		}
		return contextName;
	}

	@Override
	public void rotate(ScreenOrientation orientation) {
		execute(DriverCommand.SET_SCREEN_ORIENTATION, ImmutableMap.of(
				"orientation", orientation.value().toUpperCase()));
	}

	@Override
	public ScreenOrientation getOrientation() {
		Response response = execute(DriverCommand.GET_SCREEN_ORIENTATION);
		String orientation = response.getValue().toString().toLowerCase();
		if (orientation.equals(ScreenOrientation.LANDSCAPE.value())) {
			return ScreenOrientation.LANDSCAPE;
		} else if (orientation.equals(ScreenOrientation.PORTRAIT.value())) {
			return ScreenOrientation.PORTRAIT;
		} else {
			throw new WebDriverException("Unexpected orientation returned: "
					+ orientation);
		}
	}

	@Override
	public WebElement findElementByIosUIAutomation(String using) {
		return findElement("-ios uiautomation", using);
	}

	@Override
	public List<WebElement> findElementsByIosUIAutomation(String using) {
		return findElements("-ios uiautomation", using);
	}

	@Override
	public WebElement findElementByAndroidUIAutomator(String using) {
		return findElement("-android uiautomator", using);
	}

	@Override
	public List<WebElement> findElementsByAndroidUIAutomator(String using) {
		return findElements("-android uiautomator", using);
	}

	@Override
	public WebElement findElementByAccessibilityId(String using) {
		return findElement("accessibility id", using);
	}

	@Override
	public List<WebElement> findElementsByAccessibilityId(String using) {
		return findElements("accessibility id", using);
	}

	@Override
	public Location location() {
		return locationContext.location();
	}

	@Override
	public void setLocation(Location location) {
		locationContext.setLocation(location);
	}

	private TouchAction createTap(WebElement element, int duration) {
		TouchAction tap = new TouchAction(this);
		return tap.press(element).waitAction(duration).release();
	}

	private TouchAction createTap(int x, int y, int duration) {
		TouchAction tap = new TouchAction(this);
		return tap.press(x, y).waitAction(duration).release();
	}

	private static CommandInfo getC(String url) {
		return new CommandInfo(url, HttpMethod.GET);
	}

	private static CommandInfo postC(String url) {
		return new CommandInfo(url, HttpMethod.POST);
	}

	@SuppressWarnings("unused")
	private static CommandInfo deleteC(String url) {
		return new CommandInfo(url, HttpMethod.DELETE);
	}

	public URL getRemoteAddress() {
		return remoteAddress;
	}
}