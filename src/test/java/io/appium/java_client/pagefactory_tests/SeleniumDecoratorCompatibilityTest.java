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

package io.appium.java_client.pagefactory_tests;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.HasBrowserCheck;
import io.appium.java_client.events.stubs.EmptyWebDriver;
import io.appium.java_client.events.stubs.StubWebElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility;
import io.appium.java_client.remote.AppiumWebElement;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.remote.Response;
import io.appium.java_client.support.PageFactory;
import io.appium.java_client.support.pagefactory.ByChained;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.ImmutableCapabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static io.appium.java_client.remote.options.SupportsAutomationNameOption.AUTOMATION_NAME_OPTION;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeleniumDecoratorCompatibilityTest {
    @AndroidFindBy(id = "some_id")
    private WebElement element;

    @AndroidFindBy(accessibility = "acc_id")
    private AppiumWebElement appiumElement;

    @AndroidFindBy(id = "some_id")
    private AppiumWebElement sameAppiumElement;

    @AndroidFindBy(id = "list_id")
    private List<AppiumWebElement> appiumElements;

    @AndroidFindBy(id = "list_id")
    private List<WebElement> webElements;

    @AndroidFindBy(id = "widget_root")
    private SampleWidget widget;

    @AndroidFindBy(id = "widget_list")
    private List<SampleWidget> widgets;

    @Test
    public void canInvokeElementReturnedBySeleniumDecorator() {
        CountingDriver originalDriver = new CountingDriver();
        CountingListener listener = new CountingListener();
        WebDriver decoratedDriver = new EventFiringDecorator<>(listener).decorate(originalDriver);
        PageFactory.initElements(new AppiumFieldDecorator(decoratedDriver), this);

        element.click();

        assertEquals(1, originalDriver.element.clickCount);
        assertEquals(1, listener.clickCount);
    }

    @Test
    public void translatesMobileByAnnotationsThroughDecoratedDriverAndPartialCapabilitiesWrapper() {
        CountingDriver originalDriver = new CountingDriver();
        WebDriver partialWrapper = new EmptyCapabilitiesDriverWrapper(originalDriver);
        WebDriver decoratedDriver = new EventFiringDecorator<>(new CountingListener()).decorate(partialWrapper);
        PageFactory.initElements(new AppiumFieldDecorator(decoratedDriver), this);

        element.click();
        appiumElement.click();

        assertEquals(
                List.of(
                        new ByChained(AppiumBy.id("some_id")),
                        new ByChained(AppiumBy.accessibilityId("acc_id"))
                ),
                originalDriver.usedLocators
        );
    }

    @Test
    public void canInvokeNonWebElementInterfaceMethodsOnDecoratedElement() {
        CountingDriver originalDriver = new CountingDriver();
        WebDriver decoratedDriver = new EventFiringDecorator<>(new CountingListener()).decorate(originalDriver);
        PageFactory.initElements(new AppiumFieldDecorator(decoratedDriver), this);

        assertSame(originalDriver.element.coordinates, ((Locatable) element).getCoordinates());
        assertArrayEquals(
                originalDriver.element.screenshotBytes,
                ((TakesScreenshot) element).getScreenshotAs(OutputType.BYTES)
        );
        assertSame(decoratedDriver, ((WrapsDriver) element).getWrappedDriver());
        assertEquals(originalDriver.element.hashCode(), element.hashCode());
    }

    @Test
    public void canInvokeAppiumWebElementMethodsAndCompareEqualityWithDecoratedElement() {
        AppiumElementDriver originalDriver = new AppiumElementDriver();
        CountingListener listener = new CountingListener();
        WebDriver decoratedDriver = new EventFiringDecorator<>(listener).decorate(originalDriver);
        PageFactory.initElements(new AppiumFieldDecorator(decoratedDriver), this);

        assertEquals("appium-el-1", appiumElement.getId());
        assertEquals(
                Map.of(AppiumWebElement.ELEMENT_KEY, "appium-el-1"),
                appiumElement.toJson()
        );

        WebElement decoratedFound = decoratedDriver.findElement(AppiumBy.id("some_id"));
        assertEquals(appiumElement, decoratedFound);
        assertEquals(decoratedFound, appiumElement);
        assertEquals(appiumElement, originalDriver.primaryElement);
        assertEquals(originalDriver.primaryElement, appiumElement);
        assertEquals(appiumElement, sameAppiumElement);
        assertNotEquals(appiumElement, originalDriver.secondaryElement);
        assertNotEquals(originalDriver.secondaryElement, appiumElement);
    }

    @Test
    public void canUseAppiumWebElementAndWebElementListsWithSeleniumDecorator() {
        AppiumElementDriver originalDriver = new AppiumElementDriver();
        CountingListener listener = new CountingListener();
        WebDriver decoratedDriver = new EventFiringDecorator<>(listener).decorate(originalDriver);
        PageFactory.initElements(new AppiumFieldDecorator(decoratedDriver), this);

        assertEquals(2, appiumElements.size());
        AppiumWebElement first = appiumElements.get(0);
        first.click();

        assertEquals(1, listener.clickCount);
        assertEquals("appium-el-1", first.getId());
        assertEquals(originalDriver.primaryElement, first);
        assertEquals(first, originalDriver.primaryElement);

        assertEquals(2, webElements.size());
        webElements.get(1).click();
        assertEquals(2, listener.clickCount);
    }

    @Test
    public void canUseWidgetsAndWidgetListsWithSeleniumDecorator() {
        CountingDriver originalDriver = new CountingDriver();
        CountingListener listener = new CountingListener();
        WebDriver decoratedDriver = new EventFiringDecorator<>(listener).decorate(originalDriver);
        PageFactory.initElements(new AppiumFieldDecorator(decoratedDriver), this);

        widget.tapChild();

        assertEquals(1, originalDriver.element.childElement.clickCount);
        assertEquals(1, listener.clickCount);
        assertEquals(
                List.of(new ByChained(AppiumBy.id("child_id"))),
                originalDriver.element.usedChildLocators
        );

        assertEquals(1, widgets.size());
        widgets.get(0).tapChild();
        assertEquals(2, originalDriver.element.childElement.clickCount);
        assertEquals(2, listener.clickCount);
    }

    @Test
    public void unpacksFromDualWrappersAndGuardsAgainstSelfReferentialCycles() {
        CountingElement innerElement = new CountingElement();
        SearchContext dualWrapper = new DualDriverAndElementWrapper(null, innerElement);
        Optional<CountingElement> unpacked = WebDriverUnpackUtility.unpackObjectFromSearchContext(
                dualWrapper, CountingElement.class
        );
        assertTrue(unpacked.isPresent());
        assertSame(innerElement, unpacked.get());

        CyclicSearchContext cyclic = new CyclicSearchContext();
        assertFalse(WebDriverUnpackUtility.unpackObjectFromSearchContext(cyclic, WebDriver.class).isPresent());
        assertSame(cyclic, WebDriverUnpackUtility.unwrapElement(cyclic));
    }

    public static class SampleWidget extends Widget {
        @AndroidFindBy(id = "child_id")
        private WebElement child;

        protected SampleWidget(WebElement element) {
            super(element);
        }

        public void tapChild() {
            child.click();
        }
    }

    public static class CountingDriver extends EmptyWebDriver implements HasBrowserCheck {
        private final CountingElement element = new CountingElement();
        private final List<By> usedLocators = new ArrayList<>();

        @Override
        public Capabilities getCapabilities() {
            return new ImmutableCapabilities(
                    CapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID,
                    AUTOMATION_NAME_OPTION, AutomationName.ANDROID_UIAUTOMATOR2
            );
        }

        @Override
        public boolean isBrowser() {
            return false;
        }

        @Override
        public Response execute(String driverCommand, Map<String, ?> parameters) {
            return null;
        }

        @Override
        public Response execute(String driverCommand) {
            return null;
        }

        @Override
        public StubWebElement findElement(By by) {
            usedLocators.add(by);
            return element;
        }

        @Override
        public List<WebElement> findElements(By by) {
            usedLocators.add(by);
            return List.of(element);
        }
    }

    public static class CountingElement extends StubWebElement implements Locatable, TakesScreenshot {
        private final Coordinates coordinates = new StubCoordinates();
        private final byte[] screenshotBytes = new byte[]{1, 2, 3};
        private final List<By> usedChildLocators = new ArrayList<>();
        private final CountingChildElement childElement = new CountingChildElement();
        private int clickCount;

        @Override
        public void click() {
            clickCount++;
        }

        @Override
        public Coordinates getCoordinates() {
            return coordinates;
        }

        @Override
        public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
            return target.convertFromPngBytes(screenshotBytes);
        }

        @Override
        public WebElement findElement(By by) {
            usedChildLocators.add(by);
            return childElement;
        }
    }

    public static class CountingChildElement extends StubWebElement {
        private int clickCount;

        @Override
        public void click() {
            clickCount++;
        }
    }

    public static class StubCoordinates implements Coordinates {
        @Override
        public Point onScreen() {
            return new Point(0, 0);
        }

        @Override
        public Point inViewPort() {
            return new Point(0, 0);
        }

        @Override
        public Point onPage() {
            return new Point(0, 0);
        }

        @Override
        public Object getAuxiliary() {
            return "aux";
        }
    }

    public static class BaseWebDriverStub implements WebDriver, HasCapabilities {
        @Override
        public void get(String url) {
        }

        @Override
        public String getCurrentUrl() {
            return null;
        }

        @Override
        public String getTitle() {
            return null;
        }

        @Override
        public List<WebElement> findElements(By by) {
            return List.of();
        }

        @Override
        public WebElement findElement(By by) {
            return null;
        }

        @Override
        public String getPageSource() {
            return null;
        }

        @Override
        public void close() {
        }

        @Override
        public void quit() {
        }

        @Override
        public Set<String> getWindowHandles() {
            return Set.of();
        }

        @Override
        public String getWindowHandle() {
            return null;
        }

        @Override
        public TargetLocator switchTo() {
            return null;
        }

        @Override
        public Navigation navigate() {
            return null;
        }

        @Override
        public Options manage() {
            return null;
        }

        @Override
        public Capabilities getCapabilities() {
            return new ImmutableCapabilities();
        }
    }

    public static class AppiumElementDriver extends BaseWebDriverStub implements HasBrowserCheck {
        private final StubAppiumWebElement primaryElement = new StubAppiumWebElement("appium-el-1");
        private final StubAppiumWebElement secondaryElement = new StubAppiumWebElement("appium-el-2");

        @Override
        public Capabilities getCapabilities() {
            return new ImmutableCapabilities(
                    CapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID,
                    AUTOMATION_NAME_OPTION, AutomationName.ANDROID_UIAUTOMATOR2
            );
        }

        @Override
        public boolean isBrowser() {
            return false;
        }

        @Override
        public Response execute(String driverCommand, Map<String, ?> parameters) {
            return null;
        }

        @Override
        public Response execute(String driverCommand) {
            return null;
        }

        @Override
        public WebElement findElement(By by) {
            return primaryElement;
        }

        @Override
        public List<WebElement> findElements(By by) {
            return List.of(primaryElement, secondaryElement);
        }
    }

    public static class StubAppiumWebElement extends AppiumWebElement {
        private int clickCount;

        public StubAppiumWebElement(String id) {
            setId(id);
        }

        @Override
        public void click() {
            clickCount++;
        }
    }

    public static class EmptyCapabilitiesDriverWrapper extends BaseWebDriverStub implements WrapsDriver {
        private final WebDriver delegate;

        public EmptyCapabilitiesDriverWrapper(WebDriver delegate) {
            this.delegate = delegate;
        }

        @Override
        public WebDriver getWrappedDriver() {
            return delegate;
        }

        @Override
        public WebElement findElement(By by) {
            return delegate.findElement(by);
        }

        @Override
        public List<WebElement> findElements(By by) {
            return delegate.findElements(by);
        }
    }

    public static class DualDriverAndElementWrapper implements SearchContext, WrapsDriver, WrapsElement {
        private final WebDriver driver;
        private final WebElement element;

        public DualDriverAndElementWrapper(WebDriver driver, WebElement element) {
            this.driver = driver;
            this.element = element;
        }

        @Override
        public WebDriver getWrappedDriver() {
            return driver;
        }

        @Override
        public WebElement getWrappedElement() {
            return element;
        }

        @Override
        public List<WebElement> findElements(By by) {
            return List.of();
        }

        @Override
        public WebElement findElement(By by) {
            return element;
        }
    }

    public static class CyclicSearchContext extends StubWebElement implements WrapsDriver, WrapsElement {
        @Override
        public WebDriver getWrappedDriver() {
            return null;
        }

        @Override
        public WebElement getWrappedElement() {
            return this;
        }
    }

    public static class CountingListener implements WebDriverListener {
        private int clickCount;

        @Override
        public void afterClick(WebElement element) {
            clickCount++;
        }
    }
}
