package io.appium.java_client.pagefactory_tests.widget.tests;

import static com.google.common.collect.ImmutableList.of;
import static io.appium.java_client.remote.AutomationName.APPIUM;
import static io.appium.java_client.remote.AutomationName.IOS_XCUI_TEST;
import static io.appium.java_client.remote.AutomationName.SELENDROID;
import static io.appium.java_client.remote.MobilePlatform.ANDROID;
import static io.appium.java_client.remote.MobilePlatform.IOS;
import static io.appium.java_client.remote.MobilePlatform.WINDOWS;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import io.appium.java_client.HasSessionDetails;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.remote.Response;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public abstract class AbstractStubWebDriver implements WebDriver, HasSessionDetails {

    @Override
    public Map<String, Object> getSessionDetails() {
        return null;
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
    public abstract String getPlatformName();

    @Override
    public abstract String getAutomationName();

    @Override
    public boolean isBrowser() {
        return false;
    }

    @Override
    public void get(String url) {
        //this is just stub and it does nothing.
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
    public List<StubWebElement> findElements(By by) {
        return of(new StubWebElement(this, by), new StubWebElement(this, by));
    }

    @Override
    public StubWebElement findElement(By by) {
        return new StubWebElement(this, by);
    }

    @Override
    public String getPageSource() {
        return null;
    }

    @Override
    public void close() {
        //this is just stub and it does nothing.
    }

    @Override
    public void quit() {
        //this is just stub and it does nothing.
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
        return new Options() {
            @Override
            public void addCookie(Cookie cookie) {
                //does nothing
            }

            @Override
            public void deleteCookieNamed(String name) {
                //does nothing
            }

            @Override
            public void deleteCookie(Cookie cookie) {
                //does nothing
            }

            @Override
            public void deleteAllCookies() {
                //does nothing
            }

            @Override
            public Set<Cookie> getCookies() {
                return new HashSet<>();
            }

            @Override
            public Cookie getCookieNamed(String name) {
                return new Cookie(name, EMPTY);
            }

            @Override
            public Timeouts timeouts() {
                return new Timeouts() {
                    @Override
                    public Timeouts implicitlyWait(long time, TimeUnit unit) {
                        return this;
                    }

                    @Override
                    public Timeouts setScriptTimeout(long time, TimeUnit unit) {
                        return this;
                    }

                    @Override
                    public Timeouts pageLoadTimeout(long time, TimeUnit unit) {
                        return this;
                    }
                };
            }

            @Override
            public ImeHandler ime() {
                return null;
            }

            @Override
            public Window window() {
                return null;
            }

            @Override
            public Logs logs() {
                return null;
            }
        };
    }

    public static class StubAndroidDriver extends AbstractStubWebDriver {

        @Override
        public String getPlatformName() {
            return ANDROID;
        }

        @Override
        public String getAutomationName() {
            return APPIUM;
        }
    }

    public static class StubSelendroidDriver extends AbstractStubWebDriver {

        @Override
        public String getPlatformName() {
            return ANDROID;
        }

        @Override
        public String getAutomationName() {
            return SELENDROID;
        }
    }

    public static class StubIOSDriver extends AbstractStubWebDriver {

        @Override
        public String getPlatformName() {
            return IOS;
        }

        @Override
        public String getAutomationName() {
            return APPIUM;
        }
    }

    public static class StubIOSXCUITDriver extends AbstractStubWebDriver {

        @Override
        public String getPlatformName() {
            return IOS;
        }

        @Override
        public String getAutomationName() {
            return IOS_XCUI_TEST;
        }
    }

    public static class StubWindowsDriver extends AbstractStubWebDriver {

        @Override
        public String getPlatformName() {
            return WINDOWS;
        }

        @Override
        public String getAutomationName() {
            return APPIUM;
        }
    }

    public static class StubBrowserDriver extends AbstractStubWebDriver {

        @Override
        public String getPlatformName() {
            return EMPTY;
        }

        @Override
        public String getAutomationName() {
            return EMPTY;
        }
    }

    public static class StubAndroidBrowserOrWebViewDriver extends AbstractStubWebDriver {

        @Override
        public String getPlatformName() {
            return ANDROID;
        }

        @Override
        public String getAutomationName() {
            return APPIUM;
        }

        @Override
        public boolean isBrowser() {
            return true;
        }
    }
}
