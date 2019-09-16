package io.appium.java_client.pagefactory_tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.PageFactory;
import io.appium.java_client.MobileElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GenericTest {

    /**
     * The Generic types are null on an unbound page.
     */
    private static <T> void assertUnboundGenericsNull(T genericItem,
            List<? extends T> genericItems) {
        assertNull(genericItem);
        assertNull(genericItems);
    }

    /**
     * The Generic types are not null on a page bound by a WebElement (or WebElement
     * sub type).
     */
    private static <T extends WebElement> void assertBoundGenericsNotNull(T genericItem,
            List<? extends T> genericItems) {
        assertNotNull(genericItem);
        assertNotNull(genericItems);
    }

    /**
     * The Element types are never null.
     */
    private static void assertElementsNotNull(WebElement elementItem,
            List<? extends WebElement> elementItems) {
        assertNotNull(elementItem);
        assertNotNull(elementItems);
    }

    /**
     * The Object types are always null.
     */
    private static void assertObjectsNull(Object objectItem, List<? extends Object> objectItems) {
        assertNull(objectItem);
        assertNull(objectItems);
    }

    /**
     * A page with no generic types. The Object types are never initialized.
     */
    static class TempNoGenericsPage {
        public WebElement webElementItem;
        public MobileElement mobileElementItem;
        public Object objectItem;

        public List<WebElement> webElementItems;
        public List<MobileElement> mobileElementItems;
        public List<Object> objectItems;

        public void assertInit() {
            assertElementsNotNull(webElementItem, webElementItems);
            assertElementsNotNull(mobileElementItem, mobileElementItems);
            assertObjectsNull(objectItem, objectItems);
        }
    }

    /**
     * A page with an unbound generic type. The generic and Object types are never
     * initialized.
     */
    static class TempUnboundPage<T> {
        public T genericItem;
        public WebElement webElementItem;
        public MobileElement mobileElementItem;
        public Object objectItem;

        public List<T> genericItems;
        public List<WebElement> webElementItems;
        public List<MobileElement> mobileElementItems;
        public List<Object> objectItems;

        public void assertInit() {
            assertUnboundGenericsNull(genericItem, genericItems);
            assertElementsNotNull(webElementItem, webElementItems);
            assertElementsNotNull(mobileElementItem, mobileElementItems);
            assertObjectsNull(objectItem, objectItems);
        }
    }

    /**
     * A page with a WebElement bound generic type. The Object types are never
     * initialized.
     */
    static class TempWebBoundPage<T extends WebElement> {
        public T genericItem;
        public WebElement webElementItem;
        public MobileElement mobileElementItem;
        public Object objectItem;

        public List<T> genericItems;
        public List<WebElement> webElementItems;
        public List<MobileElement> mobileElementItems;
        public List<Object> objectItems;

        public void assertInit() {
            assertBoundGenericsNotNull(genericItem, genericItems);
            assertElementsNotNull(webElementItem, webElementItems);
            assertElementsNotNull(mobileElementItem, mobileElementItems);
            assertObjectsNull(objectItem, objectItems);
        }
    }

    /**
     * A page with a MobileElement bound generic type. The Object types are never
     * initialized.
     */
    static class TempMobileBoundPage<T extends MobileElement> {
        public T genericItem;
        public WebElement webElementItem;
        public MobileElement mobileElementItem;
        public Object objectItem;

        public List<T> genericItems;
        public List<WebElement> webElementItems;
        public List<MobileElement> mobileElementItems;
        public List<Object> objectItems;

        public void assertInit() {
            assertBoundGenericsNotNull(genericItem, genericItems);
            assertElementsNotNull(webElementItem, webElementItems);
            assertElementsNotNull(mobileElementItem, mobileElementItems);
            assertObjectsNull(objectItem, objectItems);
        }
    }

    static class MockWebDriver implements WebDriver, HasCapabilities {

        @Override
        public void get(String url) {
            System.out.print(url);
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
        public <T extends WebElement> List<T> findElements(By by) {
            throw new NotImplementedException("MockWebDriver did not expect to findElements");
        }

        @Override
        public <T extends WebElement> T findElement(By by) {
            throw new NotImplementedException("MockWebDriver did not expect to findElement");
        }

        @Override
        public String getPageSource() {
            return null;
        }

        @Override
        public void close() {
            System.out.print("Closed");
        }

        @Override
        public void quit() {
            System.out.print("Died");
        }

        @Override
        public Set<String> getWindowHandles() {
            return null;
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

            final Map<String, Object> capabilities = new HashMap<>();

            // These are needed to map the proxy element to a MobileElement.
            capabilities.put(CapabilityType.PLATFORM_NAME, Platform.ANY.toString());
            capabilities.put(MobileCapabilityType.AUTOMATION_NAME,
                    AutomationName.IOS_XCUI_TEST.toString());

            return new Capabilities() {

                @Override
                public Object getCapability(String capabilityName) {
                    return capabilities.get(capabilityName);
                }

                @Override
                public Map<String, Object> asMap() {
                    return capabilities;
                }
            };
        }
    }

    @Test
    public void noGenericsTestCase() {
        TempNoGenericsPage page = new TempNoGenericsPage();
        PageFactory.initElements(new AppiumFieldDecorator(new MockWebDriver()), page);
        page.assertInit();
    }

    @Test
    public void unBoundTestCase() {
        TempUnboundPage<?> page = new TempUnboundPage<>();
        PageFactory.initElements(new AppiumFieldDecorator(new MockWebDriver()), page);
        page.assertInit();
    }

    @Test
    public void unboundWebElementTestCase() {
        TempUnboundPage<WebElement> page = new TempUnboundPage<>();
        PageFactory.initElements(new AppiumFieldDecorator(new MockWebDriver()), page);
        page.assertInit();
    }

    @Test
    public void webBoundUnknownElementTestCase() {
        TempWebBoundPage<?> page = new TempWebBoundPage<>();
        PageFactory.initElements(new AppiumFieldDecorator(new MockWebDriver()), page);
        page.assertInit();
    }

    @Test
    public void webBoundWebElementTestCase() {
        TempWebBoundPage<WebElement> page = new TempWebBoundPage<>();
        PageFactory.initElements(new AppiumFieldDecorator(new MockWebDriver()), page);
        page.assertInit();
    }

    @Test
    public void webBoundMobileElementTestCase() {
        TempWebBoundPage<MobileElement> page = new TempWebBoundPage<>();
        PageFactory.initElements(new AppiumFieldDecorator(new MockWebDriver()), page);
        page.assertInit();
    }

    @Test
    public void mobileBoundUnknownElementTestCase() {
        TempMobileBoundPage<?> page = new TempMobileBoundPage<>();
        PageFactory.initElements(new AppiumFieldDecorator(new MockWebDriver()), page);
        page.assertInit();
    }

    @Test
    public void mobileBoundMobileElementTestCase() {
        TempMobileBoundPage<MobileElement> page = new TempMobileBoundPage<>();
        PageFactory.initElements(new AppiumFieldDecorator(new MockWebDriver()), page);
        page.assertInit();
    }
}
