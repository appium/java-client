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

package io.appium.java_client.remote;

import io.appium.java_client.internal.http.ConnectionException;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.ImmutableCapabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Pdf;
import org.openqa.selenium.Point;
import org.openqa.selenium.PrintsPage;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Interactive;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.print.PrintOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.singleton;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * The base of the Appium drivers. It translates the WebDriver API into commands that are
 * executed by a {@link CommandExecutor}. Adapted from Selenium's {@code RemoteWebDriver}
 * (Apache License 2.0), without the features that are specific to browsers.
 */
public class AppiumRemoteWebDriver implements WebDriver, JavascriptExecutor, HasCapabilities, Interactive,
        PrintsPage, TakesScreenshot {
    private static final Logger LOG = LoggerFactory.getLogger(AppiumRemoteWebDriver.class);

    private final ElementLocation elementLocation = new ElementLocation();
    private ErrorHandler errorHandler = new ErrorHandler();
    private CommandExecutor executor;
    protected Capabilities capabilities;
    private @Nullable SessionId sessionId;
    private final ExecuteMethod executeMethod = (commandName, parameters) -> {
        Response response = parameters == null || parameters.isEmpty()
                ? execute(commandName, Map.of()) : execute(commandName, parameters);
        return response.getValue();
    };
    private final JsonToWebElementConverter converter = new JsonToWebElementConverter(this);
    private final Logs remoteLogs = new RemoteLogs(executeMethod);

    /**
     * Creates a driver that is not connected to a session. It is meant for subclasses that attach
     * to an existing session.
     */
    @SuppressWarnings("DataFlowIssue")
    protected AppiumRemoteWebDriver() {
        this.capabilities = new ImmutableCapabilities();
        this.executor = null;
    }

    /**
     * Creates a driver and starts a session.
     *
     * @param executor the executor of the commands
     * @param capabilities the capabilities of the session
     */
    public AppiumRemoteWebDriver(CommandExecutor executor, Capabilities capabilities) {
        this.executor = requireNonNull(executor, "Command executor");
        this.capabilities = requireNonNull(capabilities, "Capabilities");
        try {
            startSession(capabilities);
        } catch (RuntimeException e) {
            try {
                quit();
            } catch (Exception ignored) {
                // The original failure is propagated
            }
            throw e;
        }
    }

    @Nullable
    public SessionId getSessionId() {
        return sessionId;
    }

    protected void setSessionId(String opaqueKey) {
        sessionId = new SessionId(opaqueKey);
    }

    /**
     * Starts a session with the given capabilities.
     *
     * @param requestCapabilities the capabilities to request
     */
    protected void startSession(Capabilities requestCapabilities) {
        Response response = execute(DriverCommand.NEW_SESSION(singleton(requestCapabilities)));
        if (response == null) {
            throw new SessionNotCreatedException("The underlying command executor returned a null response.");
        }
        Object responseValue = response.getValue();
        if (responseValue == null) {
            throw new SessionNotCreatedException(
                    "The underlying command executor returned a response without payload: " + response);
        }
        if (!(responseValue instanceof Map)) {
            throw new SessionNotCreatedException(
                    "The underlying command executor returned a response with a non well formed payload: "
                            + response);
        }
        @SuppressWarnings("unchecked")
        Map<String, Object> rawCapabilities = (Map<String, Object>) responseValue;
        this.capabilities = new MutableCapabilities(rawCapabilities);
        this.sessionId = new SessionId(response.getSessionId());
    }

    public ErrorHandler getErrorHandler() {
        return errorHandler;
    }

    public void setErrorHandler(ErrorHandler handler) {
        this.errorHandler = handler;
    }

    public CommandExecutor getCommandExecutor() {
        return executor;
    }

    protected void setCommandExecutor(CommandExecutor executor) {
        this.executor = executor;
    }

    @Override
    public Capabilities getCapabilities() {
        return capabilities;
    }

    @Override
    public void get(String url) {
        execute(DriverCommand.GET(url));
    }

    @Override
    public String getTitle() {
        Object value = execute(DriverCommand.GET_TITLE).getValue();
        return value == null ? "" : value.toString();
    }

    @Override
    public String getCurrentUrl() {
        Response response = execute(DriverCommand.GET_CURRENT_URL);
        if (response == null || response.getValue() == null) {
            throw new WebDriverException("Remote browser did not respond to getCurrentUrl");
        }
        return response.getValue().toString();
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        Object result = execute(DriverCommand.SCREENSHOT).getValue();
        if (result instanceof String) {
            return outputType.convertFromBase64Png((String) result);
        } else if (result instanceof byte[]) {
            return outputType.convertFromPngBytes((byte[]) result);
        }
        throw new RuntimeException(String.format("Unexpected result for %s command: %s", DriverCommand.SCREENSHOT,
                result == null ? "null" : result.getClass().getName() + " instance"));
    }

    @Override
    public Pdf print(PrintOptions printOptions) throws WebDriverException {
        Object result = execute(DriverCommand.PRINT_PAGE(printOptions)).getValue();
        return new Pdf((String) requireNonNull(result));
    }

    @Override
    public WebElement findElement(By locator) {
        requireNonNull(locator, "Locator");
        return findElement(this, DriverCommand::FIND_ELEMENT, locator);
    }

    WebElement findElement(SearchContext context, BiFunction<String, Object, CommandPayload> findCommand, By locator) {
        return elementLocation.findElement(this, context, findCommand, locator);
    }

    @Override
    public List<WebElement> findElements(By locator) {
        requireNonNull(locator, "Locator");
        return findElements(this, DriverCommand::FIND_ELEMENTS, locator);
    }

    /**
     * Finds elements in the given context.
     *
     * @param context the element or driver to search in
     * @param findCommand creates the command payload from the locator strategy and value
     * @param locator the locator
     * @return the found elements, empty if there are none
     */
    public List<WebElement> findElements(SearchContext context,
                                         BiFunction<String, Object, CommandPayload> findCommand, By locator) {
        return elementLocation.findElements(this, context, findCommand, locator);
    }

    @Override
    public String getPageSource() {
        return (String) execute(DriverCommand.GET_PAGE_SOURCE).getValue();
    }

    @Override
    public void close() {
        execute(DriverCommand.CLOSE);
    }

    @Override
    public void quit() {
        // Nothing to do without a session
        if (sessionId == null) {
            return;
        }
        try {
            execute(DriverCommand.QUIT);
        } finally {
            sessionId = null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<String> getWindowHandles() {
        Object value = execute(DriverCommand.GET_WINDOW_HANDLES).getValue();
        try {
            return new LinkedHashSet<>((List<String>) value);
        } catch (ClassCastException ex) {
            throw new WebDriverException("Returned value cannot be converted to List<String>: " + value, ex);
        }
    }

    @Override
    public String getWindowHandle() {
        return String.valueOf(execute(DriverCommand.GET_CURRENT_WINDOW_HANDLE).getValue());
    }

    @Nullable
    @Override
    public Object executeScript(String script, @Nullable Object... args) {
        return execute(DriverCommand.EXECUTE_SCRIPT(script, convertScriptArguments(args))).getValue();
    }

    @Nullable
    @Override
    public Object executeAsyncScript(String script, @Nullable Object... args) {
        return execute(DriverCommand.EXECUTE_ASYNC_SCRIPT(script, convertScriptArguments(args))).getValue();
    }

    private static List<Object> convertScriptArguments(@Nullable Object... args) {
        return Stream.of(args).map(new WebElementToJsonConverter()).collect(Collectors.toList());
    }

    @Override
    public TargetLocator switchTo() {
        return new RemoteTargetLocator();
    }

    @Override
    public Navigation navigate() {
        return new RemoteNavigation();
    }

    @Override
    public Options manage() {
        return new RemoteWebDriverOptions();
    }

    /**
     * Executes a command and checks its response.
     *
     * @param payload the command to execute
     * @return the response, which is null if the executor reports none
     */
    @Nullable
    protected Response execute(CommandPayload payload) {
        Command command = new Command(sessionId, payload);
        Response response;

        long start = System.currentTimeMillis();
        String currentName = Thread.currentThread().getName();
        Thread.currentThread().setName(
                String.format("Forwarding %s on session %s to remote", command.getName(), sessionId));
        try {
            LOG.debug("Executing: {}", command);
            response = executor.execute(command);
            LOG.debug("Executed: {}", response);

            if (response == null) {
                return null;
            }

            // Converts the element references to elements
            response.setValue(converter.apply(response.getValue()));
        } catch (Throwable e) {
            LOG.debug("Exception: {}", e.getMessage());
            WebDriverException toThrow;
            if (command.getName().equals(DriverCommand.NEW_SESSION)) {
                if (e instanceof SessionNotCreatedException) {
                    toThrow = (WebDriverException) e;
                } else {
                    // The real cause of a remote end failure is usually hidden in the cause
                    String cause = e.getCause() != null ? " " + e.getCause().getMessage() : "";
                    toThrow = new SessionNotCreatedException("Possible causes are invalid address of the remote "
                            + "server or browser start-up failure." + cause, e);
                }
            } else if (e instanceof WebDriverException) {
                toThrow = (WebDriverException) e;
            } else if (e instanceof ConnectionException) {
                toThrow = new UnreachableBrowserException(
                        "Error communicating with the remote browser at " + ((ConnectionException) e).uri(), e);
            } else {
                toThrow = new UnreachableBrowserException(
                        "Error communicating with the remote browser. It may have died.", e);
            }
            populateWebDriverException(toThrow);
            if (toThrow instanceof UnreachableBrowserException) {
                // The values are not shown to avoid leaking credentials
                toThrow.addInfo("Command", "[" + command.getSessionId() + ", " + command.getName() + " "
                        + command.getParameters().keySet() + "]");
            } else {
                toThrow.addInfo("Command", command.toString());
            }
            throw toThrow;
        } finally {
            Thread.currentThread().setName(currentName);
        }

        try {
            errorHandler.throwIfResponseFailed(response, System.currentTimeMillis() - start);
        } catch (WebDriverException ex) {
            populateWebDriverException(ex);
            ex.addInfo("Command", command.toString());
            throw ex;
        }
        return response;
    }

    protected Response execute(String driverCommand, Map<String, ?> parameters) {
        return execute(new CommandPayload(driverCommand, parameters));
    }

    protected Response execute(String command) {
        return execute(command, Map.of());
    }

    private void populateWebDriverException(WebDriverException ex) {
        ex.addInfo(WebDriverException.DRIVER_INFO, this.getClass().getName());
        if (getSessionId() != null) {
            ex.addInfo(WebDriverException.SESSION_ID, getSessionId().toString());
        }
        if (getCapabilities() != null) {
            ex.addInfo("Capabilities", getCapabilities().toString());
        }
    }

    protected ExecuteMethod getExecuteMethod() {
        return executeMethod;
    }

    @Override
    public void perform(Collection<Sequence> actions) {
        execute(DriverCommand.ACTIONS(actions));
    }

    @Override
    public void resetInputState() {
        execute(DriverCommand.CLEAR_ACTIONS_STATE);
    }

    @Override
    public String toString() {
        Capabilities caps = getCapabilities();
        if (caps == null) {
            return super.toString();
        }
        Object platformName = caps.getCapability("platformName");
        if (platformName == null) {
            platformName = "unknown";
        }
        return String.format("%s: %s on %s (%s)", getClass().getSimpleName(), caps.getBrowserName(), platformName,
                getSessionId());
    }

    protected class RemoteWebDriverOptions implements Options {
        @Override
        public Logs logs() {
            return remoteLogs;
        }

        @Override
        public void addCookie(Cookie cookie) {
            cookie.validate();
            execute(DriverCommand.ADD_COOKIE(cookie));
        }

        @Override
        public void deleteCookieNamed(String name) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Cookie name must not be blank");
            }
            execute(DriverCommand.DELETE_COOKIE(name));
        }

        @Override
        public void deleteCookie(Cookie cookie) {
            deleteCookieNamed(cookie.getName());
        }

        @Override
        public void deleteAllCookies() {
            execute(DriverCommand.DELETE_ALL_COOKIES);
        }

        @Override
        @SuppressWarnings("unchecked")
        public Set<Cookie> getCookies() {
            Object returned = execute(DriverCommand.GET_ALL_COOKIES).getValue();
            Set<Cookie> toReturn = new HashSet<>();
            if (!(returned instanceof Collection)) {
                return toReturn;
            }
            for (Object o : (Collection<?>) returned) {
                Map<String, Object> rawCookie = (Map<String, Object>) o;
                // The keys are defined in https://w3c.github.io/webdriver/#dfn-table-for-cookie-conversion
                Cookie.Builder builder = new Cookie.Builder((String) rawCookie.get("name"),
                        (String) rawCookie.get("value"))
                        .path((String) rawCookie.get("path"))
                        .domain((String) rawCookie.get("domain"))
                        .isSecure(rawCookie.containsKey("secure") && (Boolean) rawCookie.get("secure"))
                        .isHttpOnly(rawCookie.containsKey("httpOnly") && (Boolean) rawCookie.get("httpOnly"))
                        .sameSite((String) rawCookie.get("sameSite"));
                Number expiryNum = (Number) rawCookie.get("expiry");
                builder.expiresOn(expiryNum == null ? null : new Date(SECONDS.toMillis(expiryNum.longValue())));
                toReturn.add(builder.build());
            }
            return toReturn;
        }

        @Nullable
        @Override
        public Cookie getCookieNamed(String name) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Cookie name must not be blank");
            }
            for (Cookie cookie : getCookies()) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
            return null;
        }

        @Override
        public Timeouts timeouts() {
            return new RemoteTimeouts();
        }

        @Override
        public Window window() {
            return new RemoteWindow();
        }

        protected class RemoteTimeouts implements Timeouts {
            @Override
            public Timeouts implicitlyWait(Duration duration) {
                execute(DriverCommand.SET_IMPLICIT_WAIT_TIMEOUT(duration));
                return this;
            }

            @Override
            public Duration getImplicitWaitTimeout() {
                return getTimeout("implicit");
            }

            @Override
            public Timeouts scriptTimeout(Duration duration) {
                execute(DriverCommand.SET_SCRIPT_TIMEOUT(duration));
                return this;
            }

            @Override
            public Duration getScriptTimeout() {
                return getTimeout("script");
            }

            @Override
            public Timeouts pageLoadTimeout(Duration duration) {
                execute(DriverCommand.SET_PAGE_LOAD_TIMEOUT(duration));
                return this;
            }

            @Override
            public Duration getPageLoadTimeout() {
                return getTimeout("pageLoad");
            }

            @SuppressWarnings("unchecked")
            private Duration getTimeout(String type) {
                Map<String, Object> timeouts = (Map<String, Object>) execute(DriverCommand.GET_TIMEOUTS).getValue();
                return Duration.ofMillis(((Number) timeouts.get(type)).longValue());
            }
        }

        protected class RemoteWindow implements Window {
            @Override
            @SuppressWarnings("unchecked")
            public Dimension getSize() {
                Map<String, Object> rawSize = (Map<String, Object>) execute(
                        DriverCommand.GET_CURRENT_WINDOW_SIZE).getValue();
                return new Dimension(((Number) rawSize.get("width")).intValue(),
                        ((Number) rawSize.get("height")).intValue());
            }

            @Override
            public void setSize(Dimension targetSize) {
                execute(DriverCommand.SET_CURRENT_WINDOW_SIZE(targetSize));
            }

            @Override
            @SuppressWarnings("unchecked")
            public Point getPosition() {
                Map<String, Object> rawPoint = (Map<String, Object>) execute(
                        DriverCommand.GET_CURRENT_WINDOW_POSITION()).getValue();
                return new Point(((Number) rawPoint.get("x")).intValue(), ((Number) rawPoint.get("y")).intValue());
            }

            @Override
            public void setPosition(Point targetPosition) {
                execute(DriverCommand.SET_CURRENT_WINDOW_POSITION(targetPosition));
            }

            @Override
            public void maximize() {
                execute(DriverCommand.MAXIMIZE_CURRENT_WINDOW);
            }

            @Override
            public void minimize() {
                execute(DriverCommand.MINIMIZE_CURRENT_WINDOW);
            }

            @Override
            public void fullscreen() {
                execute(DriverCommand.FULLSCREEN_CURRENT_WINDOW);
            }
        }
    }

    private class RemoteNavigation implements Navigation {
        @Override
        public void back() {
            execute(DriverCommand.GO_BACK);
        }

        @Override
        public void forward() {
            execute(DriverCommand.GO_FORWARD);
        }

        @Override
        public void to(String url) {
            get(url);
        }

        @Override
        public void to(URL url) {
            get(String.valueOf(url));
        }

        @Override
        public void refresh() {
            execute(DriverCommand.REFRESH);
        }
    }

    protected class RemoteTargetLocator implements TargetLocator {
        @Override
        public WebDriver frame(int frameIndex) {
            execute(DriverCommand.SWITCH_TO_FRAME(frameIndex));
            return AppiumRemoteWebDriver.this;
        }

        @Override
        public WebDriver frame(String frameName) {
            String name = frameName.replaceAll("(['\"\\\\#.:;,!?+<>=~*^$|%&@`{}\\-/\\[\\]\\(\\)])", "\\\\$1");
            List<WebElement> frameElements = AppiumRemoteWebDriver.this.findElements(
                    By.cssSelector("frame[name='" + name + "'],iframe[name='" + name + "']"));
            if (frameElements.isEmpty()) {
                frameElements = AppiumRemoteWebDriver.this.findElements(
                        By.cssSelector("frame#" + name + ",iframe#" + name));
            }
            if (frameElements.isEmpty()) {
                throw new NoSuchFrameException("No frame element found by name or id " + frameName);
            }
            return frame(frameElements.get(0));
        }

        @Override
        public WebDriver frame(WebElement frameElement) {
            Object elementAsJson = new WebElementToJsonConverter().apply(frameElement);
            execute(DriverCommand.SWITCH_TO_FRAME(elementAsJson));
            return AppiumRemoteWebDriver.this;
        }

        @Override
        public WebDriver parentFrame() {
            execute(DriverCommand.SWITCH_TO_PARENT_FRAME);
            return AppiumRemoteWebDriver.this;
        }

        @Override
        public WebDriver window(String windowHandleOrName) {
            try {
                execute(DriverCommand.SWITCH_TO_WINDOW(windowHandleOrName));
                return AppiumRemoteWebDriver.this;
            } catch (NoSuchWindowException nsw) {
                // Simulate the search by name
                String original = getWindowHandle();
                for (String handle : getWindowHandles()) {
                    try {
                        execute(DriverCommand.SWITCH_TO_WINDOW(handle));
                        if (windowHandleOrName.equals(executeScript("return window.name"))) {
                            return AppiumRemoteWebDriver.this;
                        }
                    } catch (NoSuchWindowException nswe) {
                        // Try the next one
                    }
                }
                execute(DriverCommand.SWITCH_TO_WINDOW(original));
                throw nsw;
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public WebDriver newWindow(WindowType typeHint) {
            Response response = execute(DriverCommand.SWITCH_TO_NEW_WINDOW(typeHint));
            String newWindowHandle = ((Map<String, Object>) response.getValue()).get("handle").toString();
            switchTo().window(newWindowHandle);
            return AppiumRemoteWebDriver.this;
        }

        @Override
        public WebDriver defaultContent() {
            execute(DriverCommand.SWITCH_TO_FRAME(null));
            return AppiumRemoteWebDriver.this;
        }

        @Override
        public WebElement activeElement() {
            return (WebElement) execute(DriverCommand.GET_ACTIVE_ELEMENT).getValue();
        }

        @Override
        public Alert alert() {
            execute(DriverCommand.GET_ALERT_TEXT);
            return new RemoteAlert();
        }
    }

    private class RemoteAlert implements Alert {
        @Override
        public void dismiss() {
            execute(DriverCommand.DISMISS_ALERT);
        }

        @Override
        public void accept() {
            execute(DriverCommand.ACCEPT_ALERT);
        }

        @Override
        public String getText() {
            return (String) execute(DriverCommand.GET_ALERT_TEXT).getValue();
        }

        @Override
        public void sendKeys(String keysToSend) {
            requireNonNull(keysToSend, "Keys to send should be a not null CharSequence");
            execute(DriverCommand.SET_ALERT_VALUE(keysToSend));
        }
    }
}
