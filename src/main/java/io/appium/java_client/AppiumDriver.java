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


package io.appium.java_client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.appium.java_client.remote.AppiumCommandExecutor;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.*;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.remote.html5.RemoteLocationContext;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.http.HttpMethod;

import javax.xml.bind.DatatypeConverter;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.appium.java_client.MobileCommand.*;

/**
 * @param <RequiredElementType> the required type of class which implement {@link org.openqa.selenium.WebElement}.
 *                              Instances of the defined type will be returned via findElement* and findElements*.
 *                              Warning (!!!). Allowed types:
 *                              {@link org.openqa.selenium.WebElement}
 *                              {@link io.appium.java_client.TouchableElement}
 *                              {@link org.openqa.selenium.remote.RemoteWebElement}
 *                              {@link io.appium.java_client.MobileElement} and its subclasses that designed specifically for each target mobile OS (still Android and iOS)
 */
@SuppressWarnings("unchecked")
public abstract class AppiumDriver<RequiredElementType extends WebElement>
    extends DefaultGenericMobileDriver<RequiredElementType> {

    private final static ErrorHandler errorHandler = new ErrorHandler(new ErrorCodesMobile(), true);
    // frequently used command parameters
    protected final String KEY_CODE = "keycode";
    protected final String PATH = "path";
    private final String SETTINGS = "settings";
    private final String LANGUAGE_PARAM = "language";
    private final String STRING_FILE_PARAM = "stringFile";
    private URL remoteAddress;
    private RemoteLocationContext locationContext;
    private ExecuteMethod executeMethod;

    private AppiumDriver(HttpCommandExecutor executor, Capabilities capabilities) {
        super(executor, capabilities);
        this.executeMethod = new AppiumExecutionMethod(this);
        locationContext = new RemoteLocationContext(executeMethod);
        super.setErrorHandler(errorHandler);
        this.remoteAddress = executor.getAddressOfRemoteServer();
    }

    public AppiumDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        this(new AppiumCommandExecutor(MobileCommand.commandRepository, remoteAddress),
            desiredCapabilities);
    }

    public AppiumDriver(URL remoteAddress, HttpClient.Factory httpClientFactory,
        Capabilities desiredCapabilities) {
        this(new AppiumCommandExecutor(MobileCommand.commandRepository, remoteAddress,
            httpClientFactory), desiredCapabilities);
    }

    public AppiumDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        this(new AppiumCommandExecutor(MobileCommand.commandRepository, service),
            desiredCapabilities);
    }

    public AppiumDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory,
        Capabilities desiredCapabilities) {
        this(new AppiumCommandExecutor(MobileCommand.commandRepository, service, httpClientFactory),
            desiredCapabilities);
    }

    public AppiumDriver(AppiumServiceBuilder builder, Capabilities desiredCapabilities) {
        this(builder.build(), desiredCapabilities);
    }

    public AppiumDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory,
        Capabilities desiredCapabilities) {
        this(builder.build(), httpClientFactory, desiredCapabilities);
    }

    public AppiumDriver(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        this(AppiumDriverLocalService.buildDefaultService(), httpClientFactory,
            desiredCapabilities);
    }

    public AppiumDriver(Capabilities desiredCapabilities) {
        this(AppiumDriverLocalService.buildDefaultService(), desiredCapabilities);
    }

    /**
     * @param originalCapabilities the given {@link Capabilities}
     * @param newPlatform          a {@link MobileCapabilityType#PLATFORM_NAME} value which has
     *                             to be set up
     * @return {@link Capabilities} with changed mobile platform value
     */
    protected static Capabilities substituteMobilePlatform(Capabilities originalCapabilities,
        String newPlatform) {
        DesiredCapabilities dc = new DesiredCapabilities(originalCapabilities);
        dc.setCapability(MobileCapabilityType.PLATFORM_NAME, newPlatform);
        return dc;
    }

    /**
     * @param param is a parameter name
     * @param value is the parameter value
     * @return built {@link ImmutableMap}
     */
    protected static ImmutableMap<String, Object> getCommandImmutableMap(String param,
        Object value) {
        ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        builder.put(param, value);
        return builder.build();
    }

    /**
     * @param params is the array with parameter names
     * @param values is the array with parameter values
     * @return built {@link ImmutableMap}
     */
    protected static ImmutableMap<String, Object> getCommandImmutableMap(String[] params,
        Object[] values) {
        ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        for (int i = 0; i < params.length; i++) {
            if (_isNotNullOrEmpty(params[i]) && _isNotNullOrEmpty(values[i])) {
                builder.put(params[i], values[i]);
            }
        }
        return builder.build();
    }

    @SuppressWarnings("unused") private static CommandInfo deleteC(String url) {
        return new CommandInfo(url, HttpMethod.DELETE);
    }

    /**
     * Checks if a string is null, empty, or whitespace.
     *
     * @param str String to check.
     * @return True if str is not null or empty.
     */
    protected static boolean _isNotNullOrEmpty(String str) {
        return str != null && !str.isEmpty() && str.trim().length() > 0;
    }

    protected static boolean _isNotNullOrEmpty(Object ob) {
        return ob != null;
    }

    @Override public List<RequiredElementType> findElements(By by) {
        return super.findElements(by);
    }

    @Override public List<RequiredElementType> findElementsById(String id) {
        return super.findElementsById(id);
    }

    public List<RequiredElementType> findElementsByLinkText(String using) {
        return super.findElementsByLinkText(using);
    }

    public List<RequiredElementType> findElementsByPartialLinkText(String using) {
        return super.findElementsByPartialLinkText(using);
    }

    public List<RequiredElementType> findElementsByTagName(String using) {
        return super.findElementsByTagName(using);
    }

    public List<RequiredElementType> findElementsByName(String using) {
        return super.findElementsByName(using);
    }

    public List<RequiredElementType> findElementsByClassName(String using) {
        return super.findElementsByClassName(using);
    }

    public List<RequiredElementType> findElementsByCssSelector(String using) {
        return super.findElementsByCssSelector(using);
    }

    public List<RequiredElementType> findElementsByXPath(String using) {
        return super.findElementsByXPath(using);
    }

    @Override public List<RequiredElementType> findElementsByAccessibilityId(String using) {
        return (List<RequiredElementType>) findElements("accessibility id", using);
    }

    @Override protected Response execute(String command) {
        return super.execute(command, ImmutableMap.<String, Object>of());
    }

    @Override public ExecuteMethod getExecuteMethod() {
        return executeMethod;
    }

    /**
     * @see InteractsWithApps#resetApp()
     */
    @Override public void resetApp() {
        execute(MobileCommand.RESET);
    }

    /**
     * @see InteractsWithApps#isAppInstalled(String)
     */
    @Override public boolean isAppInstalled(String bundleId) {
        Response response = execute(IS_APP_INSTALLED, ImmutableMap.of("bundleId", bundleId));

        return Boolean.parseBoolean(response.getValue().toString());
    }

    /**
     * @see InteractsWithApps#installApp(String)
     */
    @Override public void installApp(String appPath) {
        execute(INSTALL_APP, ImmutableMap.of("appPath", appPath));
    }

    /**
     * @see InteractsWithApps#removeApp(String)
     */
    @Override public void removeApp(String bundleId) {
        execute(REMOVE_APP, ImmutableMap.of("bundleId", bundleId));
    }

    /**
     * @see InteractsWithApps#launchApp()
     */
    @Override public void launchApp() {
        execute(LAUNCH_APP);
    }

    /**
     * @see InteractsWithApps#closeApp()
     */
    @Override public void closeApp() {
        execute(CLOSE_APP);
    }

    /**
     * @see InteractsWithApps#runAppInBackground(int)
     */
    @Override public void runAppInBackground(int seconds) {
        execute(RUN_APP_IN_BACKGROUND, ImmutableMap.of("seconds", seconds));
    }

    /**
     * @see DeviceActionShortcuts#getDeviceTime()
     */
    @Override public String getDeviceTime() {
        Response response = execute(GET_DEVICE_TIME);
        return response.getValue().toString();
    }

    /**
     * @see DeviceActionShortcuts#hideKeyboard()
     */
    @Override public void hideKeyboard() {
        execute(HIDE_KEYBOARD);
    }

    /**
     * @see InteractsWithFiles#pullFile(String)
     */
    @Override public byte[] pullFile(String remotePath) {
        Response response = execute(PULL_FILE, ImmutableMap.of(PATH, remotePath));
        String base64String = response.getValue().toString();

        return DatatypeConverter.parseBase64Binary(base64String);
    }

    /**
     * @see InteractsWithFiles#pullFolder(String)
     */
    @Override public byte[] pullFolder(String remotePath) {
        Response response = execute(PULL_FOLDER, ImmutableMap.of(PATH, remotePath));
        String base64String = response.getValue().toString();

        return DatatypeConverter.parseBase64Binary(base64String);
    }

    /**
     * @see PerformsTouchActions#performTouchAction(TouchAction)
     */
    @SuppressWarnings("rawtypes") @Override public TouchAction performTouchAction(
        TouchAction touchAction) {
        ImmutableMap<String, ImmutableList> parameters = touchAction.getParameters();
        execute(PERFORM_TOUCH_ACTION, parameters);
        return touchAction;
    }

    /**
     * @see PerformsTouchActions#performMultiTouchAction(MultiTouchAction)
     */
    @Override @SuppressWarnings({"rawtypes"}) public void performMultiTouchAction(
        MultiTouchAction multiAction) {
        ImmutableMap<String, ImmutableList> parameters = multiAction.getParameters();
        execute(PERFORM_MULTI_TOUCH, parameters);
    }

    /**
     * @see TouchShortcuts#tap(int, WebElement, int)
     */
    @Override public void tap(int fingers, WebElement element, int duration) {
        MultiTouchAction multiTouch = new MultiTouchAction(this);

        for (int i = 0; i < fingers; i++) {
            multiTouch.add(createTap(element, duration));
        }

        multiTouch.perform();
    }

    /**
     * @see TouchShortcuts#tap(int, int, int, int)
     */
    @Override public void tap(int fingers, int x, int y, int duration) {
        MultiTouchAction multiTouch = new MultiTouchAction(this);

        for (int i = 0; i < fingers; i++) {
            multiTouch.add(createTap(x, y, duration));
        }

        multiTouch.perform();
    }

    /**
     * @see TouchShortcuts#swipe(int, int, int, int, int)
     */
    @Override public void swipe(int startx, int starty, int endx, int endy, int duration) {
        TouchAction touchAction = new TouchAction(this);

        // appium converts press-wait-moveto-release to a swipe action
        touchAction.press(startx, starty).waitAction(duration).moveTo(endx, endy).release();

        touchAction.perform();
    }

    /**
     * Convenience method for pinching an element on the screen.
     * "pinching" refers to the action of two appendages pressing the screen and sliding towards each other.
     * NOTE:
     * This convenience method places the initial touches around the element, if this would happen to place one of them
     * off the screen, appium with return an outOfBounds error. In this case, revert to using the MultiTouchAction api
     * instead of this method.
     *
     * @param el The element to pinch
     */
    public void pinch(WebElement el) {
        MultiTouchAction multiTouch = new MultiTouchAction(this);

        Dimension dimensions = el.getSize();
        Point upperLeft = el.getLocation();
        Point center = new Point(upperLeft.getX() + dimensions.getWidth() / 2,
            upperLeft.getY() + dimensions.getHeight() / 2);
        int yOffset = center.getY() - upperLeft.getY();

        TouchAction action0 =
            new TouchAction(this).press(el, center.getX(), center.getY() - yOffset).moveTo(el)
                .release();
        TouchAction action1 =
            new TouchAction(this).press(el, center.getX(), center.getY() + yOffset).moveTo(el)
                .release();

        multiTouch.add(action0).add(action1);

        multiTouch.perform();
    }

    /**
     * Convenience method for pinching an element on the screen.
     * "pinching" refers to the action of two appendages pressing the screen and sliding towards each other.
     * NOTE:
     * This convenience method places the initial touches around the element at a distance, if this would happen to place
     * one of them off the screen, appium will return an outOfBounds error. In this case, revert to using the
     * MultiTouchAction api instead of this method.
     *
     * @param x x coordinate to terminate the pinch on
     * @param y y coordinate to terminate the pinch on
     */
    public void pinch(int x, int y) {
        MultiTouchAction multiTouch = new MultiTouchAction(this);

        int scrHeight = manage().window().getSize().getHeight();
        int yOffset = 100;

        if (y - 100 < 0) {
            yOffset = y;
        } else if (y + 100 > scrHeight) {
            yOffset = scrHeight - y;
        }

        TouchAction action0 = new TouchAction(this).press(x, y - yOffset).moveTo(x, y).release();
        TouchAction action1 = new TouchAction(this).press(x, y + yOffset).moveTo(x, y).release();

        multiTouch.add(action0).add(action1);

        multiTouch.perform();
    }

    /**
     * Convenience method for "zooming in" on an element on the screen.
     * "zooming in" refers to the action of two appendages pressing the screen and sliding away from each other.
     * NOTE:
     * This convenience method slides touches away from the element, if this would happen to place one of them
     * off the screen, appium will return an outOfBounds error. In this case, revert to using the MultiTouchAction api
     * instead of this method.
     *
     * @param el The element to pinch
     */
    public void zoom(WebElement el) {
        MultiTouchAction multiTouch = new MultiTouchAction(this);

        Dimension dimensions = el.getSize();
        Point upperLeft = el.getLocation();
        Point center = new Point(upperLeft.getX() + dimensions.getWidth() / 2,
            upperLeft.getY() + dimensions.getHeight() / 2);
        int yOffset = center.getY() - upperLeft.getY();

        TouchAction action0 = new TouchAction(this).press(center.getX(), center.getY())
            .moveTo(el, center.getX(), center.getY() - yOffset).release();
        TouchAction action1 = new TouchAction(this).press(center.getX(), center.getY())
            .moveTo(el, center.getX(), center.getY() + yOffset).release();

        multiTouch.add(action0).add(action1);

        multiTouch.perform();
    }

    /**
     * Convenience method for "zooming in" on an element on the screen.
     * "zooming in" refers to the action of two appendages pressing the screen and sliding away from each other.
     * NOTE:
     * This convenience method slides touches away from the element, if this would happen to place one of them
     * off the screen, appium will return an outOfBounds error. In this case, revert to using the MultiTouchAction api
     * instead of this method.
     *
     * @param x x coordinate to start zoom on
     * @param y y coordinate to start zoom on
     */
    public void zoom(int x, int y) {
        MultiTouchAction multiTouch = new MultiTouchAction(this);

        int scrHeight = manage().window().getSize().getHeight();
        int yOffset = 100;

        if (y - 100 < 0) {
            yOffset = y;
        } else if (y + 100 > scrHeight) {
            yOffset = scrHeight - y;
        }

        TouchAction action0 = new TouchAction(this).press(x, y).moveTo(0, -yOffset).release();
        TouchAction action1 = new TouchAction(this).press(x, y).moveTo(0, yOffset).release();

        multiTouch.add(action0).add(action1);

        multiTouch.perform();
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
        JsonObject settings = (JsonObject) parser.parse(response.getValue().toString());

        return settings;
    }

    /**
     * Set settings for this test session It's probably better to use a
     * convenience function, rather than use this function directly. Try finding
     * the method for the specific setting you want to change
     *
     * @param settings Map of setting keys and values
     */
    private void setSettings(ImmutableMap<?, ?> settings) {
        execute(SET_SETTINGS, getCommandImmutableMap(SETTINGS, settings));
    }

    /**
     * Set a setting for this test session It's probably better to use a
     * convenience function, rather than use this function directly. Try finding
     * the method for the specific setting you want to change
     *
     * @param setting AppiumSetting you wish to set
     * @param value   value of the setting
     */
    protected void setSetting(AppiumSetting setting, Object value) {
        setSettings(getCommandImmutableMap(setting.toString(), value));
    }

    @Deprecated
    /**
     * This method works incorrectly. It is deprecated and it is going to be removed further.
     * Be careful.
     *
     * Since Appium node 1.5.x you are free to use
     * IOSDriver.lockDevice(int seconds) or AndroidDriver.lockDevice()...AndroidDriver.unlockDevice() instead
     */ public void lockScreen(int seconds) {
        execute(LOCK, ImmutableMap.of("seconds", seconds));
    }

    @Override public WebDriver context(String name) {
        if (!_isNotNullOrEmpty(name)) {
            throw new IllegalArgumentException("Must supply a context name");
        }

        execute(DriverCommand.SWITCH_TO_CONTEXT, ImmutableMap.of("name", name));
        return AppiumDriver.this;
    }

    @Override public Set<String> getContextHandles() {
        Response response = execute(DriverCommand.GET_CONTEXT_HANDLES);
        Object value = response.getValue();
        try {
            List<String> returnedValues = (List<String>) value;
            return new LinkedHashSet<String>(returnedValues);
        } catch (ClassCastException ex) {
            throw new WebDriverException(
                "Returned value cannot be converted to List<String>: " + value, ex);
        }
    }

    @Override public String getContext() {
        String contextName =
            String.valueOf(execute(DriverCommand.GET_CURRENT_CONTEXT_HANDLE).getValue());
        if (contextName.equals("null")) {
            return null;
        }
        return contextName;
    }

    @Override public void rotate(ScreenOrientation orientation) {
        execute(DriverCommand.SET_SCREEN_ORIENTATION,
            ImmutableMap.of("orientation", orientation.value().toUpperCase()));
    }

    @Override public ScreenOrientation getOrientation() {
        Response response = execute(DriverCommand.GET_SCREEN_ORIENTATION);
        String orientation = response.getValue().toString().toLowerCase();
        if (orientation.equals(ScreenOrientation.LANDSCAPE.value())) {
            return ScreenOrientation.LANDSCAPE;
        } else if (orientation.equals(ScreenOrientation.PORTRAIT.value())) {
            return ScreenOrientation.PORTRAIT;
        } else {
            throw new WebDriverException("Unexpected orientation returned: " + orientation);
        }
    }

    @Override public Location location() {
        return locationContext.location();
    }

    @Override public void setLocation(Location location) {
        locationContext.setLocation(location);
    }

    @Deprecated
    /**
     * This method is deprecated. It is going to be removed in the next release.
     * Be careful.
     */ public String getAppStrings() {
        Response response = execute(GET_STRINGS);
        return response.getValue().toString();
    }

    @Deprecated
    /**
     * This method is deprecated. It is going to be removed in the next release.
     * Be careful.
     */ public String getAppStrings(String language) {
        Response response = execute(GET_STRINGS, getCommandImmutableMap(LANGUAGE_PARAM, language));
        return response.getValue().toString();
    }

    /**
     * @return a map with localized strings defined in the app
     * @see HasAppStrings#getAppStringMap()
     */
    @Override public Map<String, String> getAppStringMap() {
        Response response = execute(GET_STRINGS);
        return (Map<String, String>) response.getValue();
    }

    /**
     * @param language strings language code
     * @return a map with localized strings defined in the app
     * @see HasAppStrings#getAppStringMap(String)
     */
    @Override public Map<String, String> getAppStringMap(String language) {
        Response response = execute(GET_STRINGS, getCommandImmutableMap(LANGUAGE_PARAM, language));
        return (Map<String, String>) response.getValue();
    }

    /**
     * @param language   strings language code
     * @param stringFile strings filename
     * @return a map with localized strings defined in the app
     * @see HasAppStrings#getAppStringMap(String, String)
     */
    @Override public Map<String, String> getAppStringMap(String language, String stringFile) {
        String[] parameters = new String[] {LANGUAGE_PARAM, STRING_FILE_PARAM};
        Object[] values = new Object[] {language, stringFile};
        Response response = execute(GET_STRINGS, getCommandImmutableMap(parameters, values));
        return (Map<String, String>) response.getValue();
    }

    private TouchAction createTap(WebElement element, int duration) {
        TouchAction tap = new TouchAction(this);
        return tap.press(element).waitAction(duration).release();
    }

    private TouchAction createTap(int x, int y, int duration) {
        TouchAction tap = new TouchAction(this);
        return tap.press(x, y).waitAction(duration).release();
    }

    public URL getRemoteAddress() {
        return remoteAddress;
    }

    @Override public abstract RequiredElementType scrollTo(String text);

    @Override public abstract RequiredElementType scrollToExact(String text);
}
