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
import static io.appium.java_client.remote.MobileCapabilityType.PLATFORM_NAME;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.isBlank;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.internal.CapabilityHelpers;
import io.appium.java_client.internal.JsonToMobileElementConverter;
import io.appium.java_client.remote.AppiumCommandExecutor;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.Location;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.ErrorHandler;
import org.openqa.selenium.remote.ExecuteMethod;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.html5.RemoteLocationContext;
import org.openqa.selenium.remote.http.HttpClient;

import java.net.URL;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Default Appium driver implementation.
 *
 * @param <T> the required type of class which implement {@link WebElement}.
 *            Instances of the defined type will be returned via findElement* and findElements*
 *            Warning (!!!). Allowed types:
 *            {@link WebElement}, {@link org.openqa.selenium.remote.RemoteWebElement},
 *            {@link MobileElement} and its subclasses that designed
 *            specifically for each target mobile OS (still Android and iOS)
 */
@SuppressWarnings("unchecked")
public class AppiumDriver<T extends WebElement>
        extends DefaultGenericMobileDriver<T> implements ComparesImages, FindsByImage<T>, FindsByCustom<T>,
        ExecutesDriverScript, LogsEvents, HasSettings {

    private static final ErrorHandler errorHandler = new ErrorHandler(new ErrorCodesMobile(), true);
    // frequently used command parameters
    private URL remoteAddress;
    private RemoteLocationContext locationContext;
    private ExecuteMethod executeMethod;

    /**
     * Creates a new instance based on command {@code executor} and {@code capabilities}.
     *
     * @param executor     is an instance of {@link HttpCommandExecutor}
     *                     or class that extends it. Default commands or another vendor-specific
     *                     commands may be specified there.
     * @param capabilities take a look at {@link Capabilities}
     */
    public AppiumDriver(HttpCommandExecutor executor, Capabilities capabilities) {
        super(executor, capabilities);
        this.executeMethod = new AppiumExecutionMethod(this);
        locationContext = new RemoteLocationContext(executeMethod);
        super.setErrorHandler(errorHandler);
        this.remoteAddress = executor.getAddressOfRemoteServer();
        this.setElementConverter(new JsonToMobileElementConverter(this));
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
     * Changes platform name and returns new capabilities.
     *
     * @param originalCapabilities the given {@link Capabilities}.
     * @param newPlatform          a {@link MobileCapabilityType#PLATFORM_NAME} value which has
     *                             to be set up
     * @return {@link Capabilities} with changed mobile platform value
     * @deprecated Please use {@link #updateDefaultPlatformName(Capabilities, String)} instead
     */
    @Deprecated
    protected static Capabilities substituteMobilePlatform(Capabilities originalCapabilities,
                                                           String newPlatform) {
        DesiredCapabilities dc = new DesiredCapabilities(originalCapabilities);
        dc.setCapability(PLATFORM_NAME, newPlatform);
        return dc;
    }

    /**
     * Changes platform name if it is not set and returns new capabilities.
     *
     * @param originalCapabilities the given {@link Capabilities}.
     * @param defaultName          a {@link MobileCapabilityType#PLATFORM_NAME} value which has
     *                             to be set up
     * @return {@link Capabilities} with changed mobile platform name value or the original capabilities
     */
    protected static Capabilities updateDefaultPlatformName(Capabilities originalCapabilities,
                                                            String defaultName) {
        if (originalCapabilities.getCapability(PLATFORM_NAME) == null) {
            DesiredCapabilities dc = new DesiredCapabilities(originalCapabilities);
            dc.setCapability(PLATFORM_NAME, defaultName);
            return dc;
        }
        return originalCapabilities;
    }

    @Override
    public List<T> findElements(By by) {
        return super.findElements(by);
    }

    @Override
    public List<T> findElements(String by, String using) {
        return super.findElements(by, using);
    }

    @Override
    public List<T> findElementsById(String id) {
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

    @Override
    public List<T> findElementsByAccessibilityId(String using) {
        return super.findElementsByAccessibilityId(using);
    }

    @Override
    public ExecuteMethod getExecuteMethod() {
        return executeMethod;
    }

    @Override
    public WebDriver context(String name) {
        checkNotNull(name, "Must supply a context name");
        try {
            execute(DriverCommand.SWITCH_TO_CONTEXT, ImmutableMap.of("name", name));
            return this;
        } catch (WebDriverException e) {
            throw new NoSuchContextException(e.getMessage(), e);
        }
    }

    @Override
    public Set<String> getContextHandles() {
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

    @Override
    public String getContext() {
        String contextName =
                String.valueOf(execute(DriverCommand.GET_CURRENT_CONTEXT_HANDLE).getValue());
        if ("null".equalsIgnoreCase(contextName)) {
            return null;
        }
        return contextName;
    }

    /**
     * This method is used to get build version status of running Appium server.
     *
     * @return map containing version details
     */
    public Map<String, Object> getStatus() {
        return (Map<String, Object>) execute(DriverCommand.STATUS).getValue();
    }

    @Override
    public DeviceRotation rotation() {
        Response response = execute(DriverCommand.GET_SCREEN_ROTATION);
        DeviceRotation deviceRotation =
                new DeviceRotation((Map<String, Number>) response.getValue());
        if (deviceRotation.getX() < 0 || deviceRotation.getY() < 0 || deviceRotation.getZ() < 0) {
            throw new WebDriverException("Unexpected orientation returned: " + deviceRotation);
        }
        return deviceRotation;
    }

    @Override
    public void rotate(DeviceRotation rotation) {
        execute(DriverCommand.SET_SCREEN_ROTATION, rotation.parameters());
    }


    @Override
    public void rotate(ScreenOrientation orientation) {
        execute(DriverCommand.SET_SCREEN_ORIENTATION,
                ImmutableMap.of("orientation", orientation.value().toUpperCase()));
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
            throw new WebDriverException("Unexpected orientation returned: " + orientation);
        }
    }

    @Override
    public Location location() {
        return locationContext.location();
    }

    @Override
    public void setLocation(Location location) {
        locationContext.setLocation(location);
    }

    public URL getRemoteAddress() {
        return remoteAddress;
    }

    @Override
    public boolean isBrowser() {
        String browserName = CapabilityHelpers.getCapability(getCapabilities(),
                CapabilityType.BROWSER_NAME, String.class);
        if (!isBlank(browserName)) {
            try {
                return (boolean) executeScript("return !!window.navigator;");
            } catch (WebDriverException ign) {
                // ignore
            }
        }
        try {
            return !containsIgnoreCase(getContext(), "NATIVE_APP");
        } catch (WebDriverException e) {
            return false;
        }
    }

    @Override
    protected void startSession(Capabilities capabilities) {
        super.startSession(capabilities);
        // The RemoteWebDriver implementation overrides platformName
        // so we need to restore it back to the original value
        Object originalPlatformName = capabilities.getCapability(PLATFORM_NAME);
        Capabilities originalCaps = super.getCapabilities();
        if (originalPlatformName != null && originalCaps instanceof MutableCapabilities) {
            ((MutableCapabilities) super.getCapabilities()).setCapability(PLATFORM_NAME,
                    originalPlatformName);
        }
    }
}
