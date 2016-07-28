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

import static com.google.common.base.Preconditions.checkNotNull;

import static io.appium.java_client.MobileCommand.CLOSE_APP;
import static io.appium.java_client.MobileCommand.GET_DEVICE_TIME;
import static io.appium.java_client.MobileCommand.GET_SESSION;
import static io.appium.java_client.MobileCommand.GET_SETTINGS;
import static io.appium.java_client.MobileCommand.GET_STRINGS;
import static io.appium.java_client.MobileCommand.HIDE_KEYBOARD;
import static io.appium.java_client.MobileCommand.INSTALL_APP;
import static io.appium.java_client.MobileCommand.IS_APP_INSTALLED;
import static io.appium.java_client.MobileCommand.LAUNCH_APP;
import static io.appium.java_client.MobileCommand.PERFORM_MULTI_TOUCH;
import static io.appium.java_client.MobileCommand.PERFORM_TOUCH_ACTION;
import static io.appium.java_client.MobileCommand.PULL_FILE;
import static io.appium.java_client.MobileCommand.PULL_FOLDER;
import static io.appium.java_client.MobileCommand.REMOVE_APP;
import static io.appium.java_client.MobileCommand.RUN_APP_IN_BACKGROUND;
import static io.appium.java_client.MobileCommand.SET_SETTINGS;
import static io.appium.java_client.MobileCommand.prepareArguments;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.appium.java_client.remote.AppiumCommandExecutor;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.Location;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.ErrorHandler;
import org.openqa.selenium.remote.ExecuteMethod;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.html5.RemoteLocationContext;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.internal.JsonToWebElementConverter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.bind.DatatypeConverter;

/**
* @param <T> the required type of class which implement {@link org.openqa.selenium.WebElement}.
 *          Instances of the defined type will be returned via findElement* and findElements*
 *          Warning (!!!). Allowed types:
 *          {@link org.openqa.selenium.WebElement}
 *          {@link io.appium.java_client.TouchableElement}
 *          {@link org.openqa.selenium.remote.RemoteWebElement}
 *          {@link io.appium.java_client.MobileElement} and its subclasses that designed
 *          specifically
 *          for each target mobile OS (still Android and iOS)
*/
@SuppressWarnings("unchecked")
public abstract class AppiumDriver<T extends WebElement>
    extends DefaultGenericMobileDriver<T> {

    private static final ErrorHandler errorHandler = new ErrorHandler(new ErrorCodesMobile(), true);
    // frequently used command parameters
    private URL remoteAddress;
    private RemoteLocationContext locationContext;
    private ExecuteMethod executeMethod;

    /**
     * @param executor is an instance of {@link org.openqa.selenium.remote.HttpCommandExecutor}
     *                 or class that extends it. Default commands or another vendor-specific
     *                 commands may be specified there.
     * @param capabilities take a look
     *                     at {@link org.openqa.selenium.Capabilities}
     * @param converterClazz is an instance of a class that extends
     * {@link org.openqa.selenium.remote.internal.JsonToWebElementConverter}. It converts
     *                       JSON response to an instance of
     *                       {@link org.openqa.selenium.WebElement}
     */
    protected AppiumDriver(HttpCommandExecutor executor, Capabilities capabilities,
        Class<? extends JsonToWebElementConverter> converterClazz) {
        super(executor, capabilities);
        this.executeMethod = new AppiumExecutionMethod(this);
        locationContext = new RemoteLocationContext(executeMethod);
        super.setErrorHandler(errorHandler);
        this.remoteAddress = executor.getAddressOfRemoteServer();
        try {
            Constructor<? extends JsonToWebElementConverter> constructor =
                    converterClazz.getConstructor(RemoteWebDriver.class);
            this.setElementConverter(constructor.newInstance(this));
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException
                | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public AppiumDriver(URL remoteAddress, Capabilities desiredCapabilities,
        Class<? extends JsonToWebElementConverter> converterClazz) {
        this(new AppiumCommandExecutor(MobileCommand.commandRepository, remoteAddress),
            desiredCapabilities, converterClazz);
    }

    public AppiumDriver(URL remoteAddress, HttpClient.Factory httpClientFactory,
        Capabilities desiredCapabilities,
                        Class<? extends JsonToWebElementConverter> converterClazz) {
        this(new AppiumCommandExecutor(MobileCommand.commandRepository, remoteAddress,
            httpClientFactory), desiredCapabilities, converterClazz);
    }

    public AppiumDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities,
        Class<? extends JsonToWebElementConverter> converterClazz) {
        this(new AppiumCommandExecutor(MobileCommand.commandRepository, service),
            desiredCapabilities, converterClazz);
    }

    public AppiumDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory,
        Capabilities desiredCapabilities,
                        Class<? extends JsonToWebElementConverter> converterClazz) {
        this(new AppiumCommandExecutor(MobileCommand.commandRepository, service, httpClientFactory),
            desiredCapabilities, converterClazz);
    }

    public AppiumDriver(AppiumServiceBuilder builder, Capabilities desiredCapabilities,
        Class<? extends JsonToWebElementConverter> converterClazz) {
        this(builder.build(), desiredCapabilities, converterClazz);
    }

    public AppiumDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory,
        Capabilities desiredCapabilities,
                        Class<? extends JsonToWebElementConverter> converterClazz) {
        this(builder.build(), httpClientFactory, desiredCapabilities, converterClazz);
    }

    public AppiumDriver(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities,
        Class<? extends JsonToWebElementConverter> converterClazz) {
        this(AppiumDriverLocalService.buildDefaultService(), httpClientFactory,
            desiredCapabilities, converterClazz);
    }

    public AppiumDriver(Capabilities desiredCapabilities,
        Class<? extends JsonToWebElementConverter> converterClazz) {
        this(AppiumDriverLocalService.buildDefaultService(), desiredCapabilities, converterClazz);
    }

    /**
     * @param originalCapabilities the given {@link Capabilities}.
     * @param newPlatform a {@link MobileCapabilityType#PLATFORM_NAME} value which has
     *                    to be set up
     * @return {@link Capabilities} with changed mobile platform value
     */
    protected static Capabilities substituteMobilePlatform(Capabilities originalCapabilities,
        String newPlatform) {
        DesiredCapabilities dc = new DesiredCapabilities(originalCapabilities);
        dc.setCapability(MobileCapabilityType.PLATFORM_NAME, newPlatform);
        return dc;
    }

    @Override public List<T> findElements(By by) {
        return super.findElements(by);
    }

    @Override public List<T> findElements(String by, String using) {
        return super.findElements(by, using);
    }

    @Override public List<T> findElementsById(String id) {
        return super.findElementsById(id);
    }

    public List<T> findElementsByLinkText(String using) {
        return super.findElementsByLinkText(using);
    }

    public List<T> findElementsByPartialLinkText(String using) {
        return super.findElementsByPartialLinkText(using);
    }

    public List<T> findElementsByTagName(String using) {
        return super.findElementsByTagName(using);
    }

    public List<T> findElementsByName(String using) {
        return super.findElementsByName(using);
    }

    public List<T> findElementsByClassName(String using) {
        return super.findElementsByClassName(using);
    }

    public List<T> findElementsByCssSelector(String using) {
        return super.findElementsByCssSelector(using);
    }

    public List<T> findElementsByXPath(String using) {
        return super.findElementsByXPath(using);
    }

    @Override public List<T> findElementsByAccessibilityId(String using) {
        return super.findElementsByAccessibilityId(using);
    }

    @Override protected Response execute(String command) {
        return super.execute(command, ImmutableMap.<String, Object>of());
    }

    @Override public ExecuteMethod getExecuteMethod() {
        return executeMethod;
    }

    /**
     * @see InteractsWithApps#resetApp().
     */
    @Override public void resetApp() {
        execute(MobileCommand.RESET);
    }

    /**
     * @see InteractsWithApps#isAppInstalled(String).
     */
    @Override public boolean isAppInstalled(String bundleId) {
        Response response = execute(IS_APP_INSTALLED, ImmutableMap.of("bundleId", bundleId));

        return Boolean.parseBoolean(response.getValue().toString());
    }

    /**
     * @see InteractsWithApps#installApp(String).
     */
    @Override public void installApp(String appPath) {
        execute(INSTALL_APP, ImmutableMap.of("appPath", appPath));
    }

    /**
     * @see InteractsWithApps#removeApp(String).
     */
    @Override public void removeApp(String bundleId) {
        execute(REMOVE_APP, ImmutableMap.of("bundleId", bundleId));
    }

    /**
     * @see InteractsWithApps#launchApp().
     */
    @Override public void launchApp() {
        execute(LAUNCH_APP);
    }

    /**
     * @see InteractsWithApps#closeApp().
     */
    @Override public void closeApp() {
        execute(CLOSE_APP);
    }

    /**
     * @see InteractsWithApps#runAppInBackground(int).
     */
    @Override public void runAppInBackground(int seconds) {
        execute(RUN_APP_IN_BACKGROUND, ImmutableMap.of("seconds", seconds));
    }

    /**
     * @see DeviceActionShortcuts#getDeviceTime().
     */
    @Override public String getDeviceTime() {
        Response response = execute(GET_DEVICE_TIME);
        return response.getValue().toString();
    }

    /**
     * @see DeviceActionShortcuts#hideKeyboard().
     */
    @Override public void hideKeyboard() {
        execute(HIDE_KEYBOARD);
    }

    /**
     * @see InteractsWithFiles#pullFile(String).
     */
    @Override public byte[] pullFile(String remotePath) {
        Response response = execute(PULL_FILE, ImmutableMap.of("path", remotePath));
        String base64String = response.getValue().toString();

        return DatatypeConverter.parseBase64Binary(base64String);
    }

    /**
     * @see InteractsWithFiles#pullFolder(String).
     */
    @Override
    public byte[] pullFolder(String remotePath) {
        Response response = execute(PULL_FOLDER, ImmutableMap.of("path", remotePath));
        String base64String = response.getValue().toString();

        return DatatypeConverter.parseBase64Binary(base64String);
    }

    /**
     * @see PerformsTouchActions#performTouchAction(TouchAction).
     */
    @SuppressWarnings("rawtypes")
    @Override public TouchAction performTouchAction(
        TouchAction touchAction) {
        ImmutableMap<String, ImmutableList> parameters = touchAction.getParameters();
        execute(PERFORM_TOUCH_ACTION, parameters);
        return touchAction;
    }

    /**
     * @see PerformsTouchActions#performMultiTouchAction(MultiTouchAction).
     */
    @Override
    @SuppressWarnings({"rawtypes"})
    public void performMultiTouchAction(
        MultiTouchAction multiAction) {
        ImmutableMap<String, ImmutableList> parameters = multiAction.getParameters();
        execute(PERFORM_MULTI_TOUCH, parameters);
    }

    /**
     * @see TouchShortcuts#tap(int, WebElement, int).
     */
    @Override public void tap(int fingers, WebElement element, int duration) {
        MultiTouchAction multiTouch = new MultiTouchAction(this);

        for (int i = 0; i < fingers; i++) {
            multiTouch.add(createTap(element, duration));
        }

        multiTouch.perform();
    }

    /**
     * @see TouchShortcuts#tap(int, int, int, int).
     */
    @Override public void tap(int fingers, int x, int y, int duration) {
        MultiTouchAction multiTouch = new MultiTouchAction(this);

        for (int i = 0; i < fingers; i++) {
            multiTouch.add(createTap(x, y, duration));
        }

        multiTouch.perform();
    }

    protected void doSwipe(int startx, int starty, int endx, int endy, int duration) {
        TouchAction touchAction = new TouchAction(this);

        // appium converts press-wait-moveto-release to a swipe action
        touchAction.press(startx, starty).waitAction(duration).moveTo(endx, endy).release();

        touchAction.perform();
    }

    /**
     * @see TouchShortcuts#swipe(int, int, int, int, int).
     */
    @Override public abstract void swipe(int startx, int starty, int endx, int endy, int duration);

    /**
     * Convenience method for pinching an element on the screen.
     * "pinching" refers to the action of two appendages pressing the
     * screen and sliding towards each other.
     * NOTE:
     * This convenience method places the initial touches around the element, if this would
     * happen to place one of them off the screen, appium with return an outOfBounds error.
     * In this case, revert to using the MultiTouchAction api instead of this method.
     *
     * @param el The element to pinch.
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
     * "pinching" refers to the action of two appendages pressing the screen and
     * sliding towards each other.
     * NOTE:
     * This convenience method places the initial touches around the element at a distance,
     * if this would happen to place one of them off the screen, appium will return an
     * outOfBounds error. In this case, revert to using the MultiTouchAction api instead of this
     * method.
     *
     * @param x x coordinate to terminate the pinch on.
     * @param y y coordinate to terminate the pinch on.
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
     * "zooming in" refers to the action of two appendages pressing the screen and sliding
     * away from each other.
     * NOTE:
     * This convenience method slides touches away from the element, if this would happen
     * to place one of them off the screen, appium will return an outOfBounds error.
     * In this case, revert to using the MultiTouchAction api instead of this method.
     *
     * @param el The element to pinch.
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
     * "zooming in" refers to the action of two appendages pressing the screen
     * and sliding away from each other.
     * NOTE:
     * This convenience method slides touches away from the element, if this would happen to
     * place one of them off the screen, appium will return an outOfBounds error. In this case,
     * revert to using the MultiTouchAction api instead of this method.
     *
     * @param x x coordinate to start zoom on.
     * @param y y coordinate to start zoom on.
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
     * the method for the specific setting you want to read.
     *
     * @return JsonObject, a straight-up hash of settings.
     */
    public JsonObject getSettings() {
        Response response = execute(GET_SETTINGS);

        JsonParser parser = new JsonParser();
        return  (JsonObject) parser.parse(response.getValue().toString());
    }

    /**
     * Set settings for this test session It's probably better to use a
     * convenience function, rather than use this function directly. Try finding
     * the method for the specific setting you want to change.
     *
     * @param settings Map of setting keys and values.
     */
    private void setSettings(ImmutableMap<?, ?> settings) {
        execute(SET_SETTINGS, prepareArguments("settings", settings));
    }

    /**
     * Set a setting for this test session It's probably better to use a
     * convenience function, rather than use this function directly. Try finding
     * the method for the specific setting you want to change.
     *
     * @param setting AppiumSetting you wish to set.
     * @param value   value of the setting.
     */
    protected void setSetting(AppiumSetting setting, Object value) {
        setSettings(prepareArguments(setting.toString(), value));
    }

    @Override public WebDriver context(String name) {
        checkNotNull(name, "Must supply a context name");
        execute(DriverCommand.SWITCH_TO_CONTEXT, ImmutableMap.of("name", name));
        return this;
    }

    @Override public Set<String> getContextHandles() {
        Response response = execute(DriverCommand.GET_CONTEXT_HANDLES);
        Object value = response.getValue();
        try {
            List<String> returnedValues = (List<String>) value;
            return new LinkedHashSet<>(returnedValues);
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

    /**
     * @return a map with localized strings defined in the app.
     * @see HasAppStrings#getAppStringMap().
     */
    @Override public Map<String, String> getAppStringMap() {
        Response response = execute(GET_STRINGS);
        return (Map<String, String>) response.getValue();
    }

    /**
     * @param language strings language code.
     * @return a map with localized strings defined in the app.
     * @see HasAppStrings#getAppStringMap(String).
     */
    @Override public Map<String, String> getAppStringMap(String language) {
        Response response = execute(GET_STRINGS, prepareArguments("language", language));
        return (Map<String, String>) response.getValue();
    }

    /**
     * @param language   strings language code.
     * @param stringFile strings filename.
     * @return a map with localized strings defined in the app.
     * @see HasAppStrings#getAppStringMap(String, String).
     */
    @Override public Map<String, String> getAppStringMap(String language, String stringFile) {
        String[] parameters = new String[] {"language", "stringFile"};
        Object[] values = new Object[] {language, stringFile};
        Response response = execute(GET_STRINGS, prepareArguments(parameters, values));
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

    /**
     * @return a map with values that hold session details.
     *
     */
    public Map<String, Object> getSessionDetails() {
        Response response = execute(GET_SESSION);
        return (Map<String, Object>) response.getValue();
    }
}
