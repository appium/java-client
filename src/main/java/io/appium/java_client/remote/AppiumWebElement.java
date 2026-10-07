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

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;

import java.util.List;
import java.util.Map;

import static io.appium.java_client.remote.DriverCommand.FIND_CHILD_ELEMENT;
import static io.appium.java_client.remote.DriverCommand.FIND_CHILD_ELEMENTS;
import static java.util.Objects.requireNonNull;

/**
 * An element of an Appium session. It is the {@link WebElement} implementation returned by
 * {@link AppiumRemoteWebDriver}. Adapted from Selenium's {@code RemoteWebElement} (Apache License 2.0).
 */
public class AppiumWebElement implements WebElement, Locatable, TakesScreenshot, WrapsDriver {
    /**
     * Creates a new instance.
     */
    public AppiumWebElement() {
    }

    /**
     * The key of an element reference in the W3C protocol.
     */
    public static final String ELEMENT_KEY = "element-6066-11e4-a52e-4f735466cecf";

    private @Nullable String foundBy;
    /** The element reference id assigned by the server. */
    protected String id;
    /** The driver that found this element. */
    protected AppiumRemoteWebDriver parent;

    /**
     * Records how the element has been found.
     *
     * @param foundFrom the search context the element has been found in
     * @param locator the name of the locator strategy
     * @param term the locator value
     */
    protected void setFoundBy(SearchContext foundFrom, String locator, String term) {
        this.foundBy = String.format("[%s] -> %s: %s", foundFrom, locator, term);
    }

    /**
     * Sets the driver that found this element.
     *
     * @param parent the parent driver
     */
    public void setParent(AppiumRemoteWebDriver parent) {
        this.parent = parent;
    }

    /**
     * Returns the element reference id.
     *
     * @return the element id, or {@code null} if it is not set
     */
    @Nullable
    public String getId() {
        return id;
    }

    /**
     * Sets the element reference id.
     *
     * @param id the element id
     */
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void click() {
        execute(DriverCommand.CLICK_ELEMENT(id));
    }

    @Override
    public void submit() {
        execute(DriverCommand.SUBMIT_ELEMENT(id));
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        requireNonNull(keysToSend, "Keys to send");
        if (keysToSend.length == 0) {
            throw new IllegalArgumentException("Keys to send should be a not null CharSequence");
        }
        for (CharSequence cs : keysToSend) {
            requireNonNull(cs, "Keys to send");
        }
        execute(DriverCommand.SEND_KEYS_TO_ELEMENT(id, new CharSequence[]{String.join("", keysToSend)}));
    }

    @Override
    public void clear() {
        execute(DriverCommand.CLEAR_ELEMENT(id));
    }

    @Override
    public String getTagName() {
        return (String) execute(DriverCommand.GET_ELEMENT_TAG_NAME(id)).getValue();
    }

    @Override
    public String getDomProperty(String name) {
        return stringValueOf(execute(DriverCommand.GET_ELEMENT_DOM_PROPERTY(id, name)).getValue());
    }

    @Override
    public String getDomAttribute(String name) {
        return stringValueOf(execute(DriverCommand.GET_ELEMENT_DOM_ATTRIBUTE(id, name)).getValue());
    }

    @Override
    public String getAttribute(String name) {
        return stringValueOf(execute(DriverCommand.GET_ELEMENT_ATTRIBUTE(id, name)).getValue());
    }

    @Override
    public String getAriaRole() {
        return stringValueOf(execute(DriverCommand.GET_ELEMENT_ARIA_ROLE(id)).getValue());
    }

    @Override
    public String getAccessibleName() {
        return stringValueOf(execute(DriverCommand.GET_ELEMENT_ACCESSIBLE_NAME(id)).getValue());
    }

    @Nullable
    private static String stringValueOf(@Nullable Object o) {
        return o == null ? null : String.valueOf(o);
    }

    @Override
    public boolean isSelected() {
        return toBoolean(execute(DriverCommand.IS_ELEMENT_SELECTED(id)).getValue());
    }

    @Override
    public boolean isEnabled() {
        return toBoolean(execute(DriverCommand.IS_ELEMENT_ENABLED(id)).getValue());
    }

    @Override
    public boolean isDisplayed() {
        Object value = execute(DriverCommand.IS_ELEMENT_DISPLAYED(id)).getValue();
        // See https://github.com/SeleniumHQ/selenium/issues/9266
        return value != null && toBoolean(value);
    }

    private static boolean toBoolean(Object value) {
        try {
            return (Boolean) value;
        } catch (ClassCastException ex) {
            throw new WebDriverException("Returned value cannot be converted to Boolean: " + value, ex);
        }
    }

    @Override
    public String getText() {
        return (String) execute(DriverCommand.GET_ELEMENT_TEXT(id)).getValue();
    }

    @Override
    public String getCssValue(String propertyName) {
        return (String) execute(DriverCommand.GET_ELEMENT_VALUE_OF_CSS_PROPERTY(id, propertyName)).getValue();
    }

    @Override
    public List<WebElement> findElements(By locator) {
        return parent.findElements(this, (using, value) -> FIND_CHILD_ELEMENTS(getId(), using, value), locator);
    }

    @Override
    public SearchContext getShadowRoot() {
        return (SearchContext) execute(DriverCommand.GET_ELEMENT_SHADOW_ROOT(getId())).getValue();
    }

    @Override
    public WebElement findElement(By locator) {
        return parent.findElement(this, (using, value) -> FIND_CHILD_ELEMENT(getId(), using, value), locator);
    }

    /**
     * Executes the command payload in the context of this element.
     *
     * @param payload the command payload
     * @return the response of the server
     */
    protected Response execute(CommandPayload payload) {
        try {
            return parent.execute(payload);
        } catch (WebDriverException ex) {
            ex.addInfo("Element", this.toString());
            throw ex;
        }
    }

    /**
     * Executes the command in the context of this element.
     *
     * @param command the name of the command
     * @param parameters the parameters of the command
     * @return the response of the server
     */
    protected Response execute(String command, Map<String, ?> parameters) {
        try {
            return parent.execute(command, parameters);
        } catch (WebDriverException ex) {
            ex.addInfo("Element", this.toString());
            throw ex;
        }
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof WebElement)) {
            return false;
        }
        WebElement other = (WebElement) obj;
        while (other instanceof WrapsElement) {
            other = ((WrapsElement) other).getWrappedElement();
        }
        if (!(other instanceof AppiumWebElement)) {
            return false;
        }
        return id.equals(((AppiumWebElement) other).id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : super.hashCode();
    }

    @Override
    public WebDriver getWrappedDriver() {
        return parent;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Point getLocation() {
        Map<String, Object> rawPoint = (Map<String, Object>) execute(DriverCommand.GET_ELEMENT_RECT(id)).getValue();
        return new Point(((Number) rawPoint.get("x")).intValue(), ((Number) rawPoint.get("y")).intValue());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Dimension getSize() {
        Map<String, Object> rawSize = (Map<String, Object>) execute(DriverCommand.GET_ELEMENT_RECT(id)).getValue();
        return new Dimension(((Number) rawSize.get("width")).intValue(), ((Number) rawSize.get("height")).intValue());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Rectangle getRect() {
        Map<String, Object> rawRect = (Map<String, Object>) execute(DriverCommand.GET_ELEMENT_RECT(id)).getValue();
        int x = ((Number) rawRect.get("x")).intValue();
        int y = ((Number) rawRect.get("y")).intValue();
        int width = ((Number) rawRect.get("width")).intValue();
        int height = ((Number) rawRect.get("height")).intValue();
        return new Rectangle(x, y, height, width);
    }

    @Override
    public Coordinates getCoordinates() {
        return new Coordinates() {
            @Override
            public Point onScreen() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            @SuppressWarnings("unchecked")
            public Point inViewPort() {
                Map<String, Number> mapped = (Map<String, Number>) execute(
                        DriverCommand.GET_ELEMENT_LOCATION_ONCE_SCROLLED_INTO_VIEW(getId())).getValue();
                return new Point(mapped.get("x").intValue(), mapped.get("y").intValue());
            }

            @Override
            public Point onPage() {
                return getLocation();
            }

            @Override
            public Object getAuxiliary() {
                return getId();
            }
        };
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        Object result = execute(DriverCommand.ELEMENT_SCREENSHOT(id)).getValue();
        if (result instanceof String) {
            return outputType.convertFromBase64Png((String) result);
        } else if (result instanceof byte[]) {
            return outputType.convertFromPngBytes((byte[]) result);
        }
        throw new RuntimeException(String.format("Unexpected result for %s command: %s",
                DriverCommand.ELEMENT_SCREENSHOT, result == null ? "null" : result.getClass().getName() + " instance"));
    }

    @Override
    public String toString() {
        if (foundBy == null) {
            return String.format("[%s -> unknown locator]", super.toString());
        }
        return String.format("[%s]", foundBy);
    }

    /**
     * The W3C reference of the element, which is how it is serialized.
     *
     * @return the element reference
     */
    public Map<String, Object> toJson() {
        return Map.of(ELEMENT_KEY, getId());
    }
}
