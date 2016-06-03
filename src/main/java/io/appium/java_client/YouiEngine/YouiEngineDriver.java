package io.appium.java_client.YouiEngine;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.YouiEngine.internal.JsonToYouiEngineElementConverter;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.remote.Response;

import java.net.URL;
import java.util.Set;


public class YouiEngineDriver<T extends WebElement> extends AppiumDriver<T> {

    public static final String IOS = "ios";
    public static final String ANDROID = "android";
    public static final String LOG_SYSTEM = "syslog";
    public static final String LOG_CRASH = "crashlog";
    public static final String LOG_LOGCAT = "logcat";
    public static final String LOG_PERFORMANCE = "performance";
    public static final String LOG_CLIENT = "client";

    public String appPlatform;

    private String unsupportedMethodForPlatform;

    /** Constructor takes in the Appium Server URL and the capabilities you want to use for this
     * test execution. **/
    public YouiEngineDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(remoteAddress, desiredCapabilities, JsonToYouiEngineElementConverter.class);

        if (desiredCapabilities.getCapability(MobileCapabilityType.PLATFORM_NAME) != null) {
            appPlatform = desiredCapabilities.getCapability(MobileCapabilityType.PLATFORM_NAME)
                    .toString().toLowerCase();
        }

        // potential error/assert messages
        initializeErrorStrings();
    }

    private void initializeErrorStrings() {
        unsupportedMethodForPlatform = "Unsupported method for platform: " + appPlatform;
    }

    @Override
    public void swipe(int startx, int starty, int endx, int endy, int duration) {
        super.doSwipe(startx, starty, endx, endy, duration); // pass this down?
    }

    /** Returns a collection of available log types. **/
    public Set<String> getLogTypes() {
        return super.manage().logs().getAvailableLogTypes();
    }

    /** Returns LogEntries for the specified log type. **/
    public LogEntries getLogs(String logType) {
        // TODO should validate the logType arg
        return super.manage().logs().get(logType);
    }

    /** Asks the device to lock itself for the given amount of time in seconds then it will
     * request the device unlock itself. */
    public void lockFor(int seconds) throws InterruptedException, NoSuchMethodException {
        if (appPlatform.equals(IOS)) {
            super.execute("lock", getCommandImmutableMap("secs", seconds));
        } else if (appPlatform.equals(ANDROID)) {
            this.lock();
            Thread.sleep((long)seconds * 1000); // convert to milliseconds
            this.unlock();
        } else {
            throw new NoSuchMethodException(unsupportedMethodForPlatform);
        }
    }

    /** Requests the device emit a shake action.
     * Only available on iOS. */
    public void mobileShake() throws NoSuchMethodException {
        if (!appPlatform.equals(IOS)) {
            throw new NoSuchMethodException(unsupportedMethodForPlatform);
        }
        this.execute("mobileShake");
    }

    /** Requests toggling the Location Service setting.
     * Only available on Android. */
    public void toggleLocationServices() throws NoSuchMethodException {
        if (!appPlatform.equals(ANDROID)) {
            throw new NoSuchMethodException(unsupportedMethodForPlatform);
        }
        super.execute("toggleLocationServices");
    }

    /** Requests toggling the device's Flight Mode setting.
     * Only available on Android. */
    public void toggleFlightMode() throws NoSuchMethodException {
        if (!appPlatform.equals(ANDROID)) {
            throw new NoSuchMethodException(unsupportedMethodForPlatform);
        }
        super.execute("toggleFlightMode");
    }

    /** Requests toggling the device's WiFi setting.
     * Only available on Android. */
    public void toggleWiFi() throws NoSuchMethodException {
        if (!appPlatform.equals(ANDROID)) {
            throw new NoSuchMethodException(unsupportedMethodForPlatform);
        }
        super.execute("toggleWiFi");
    }

    /** Requests toggling the device's Data setting.
     * Only available on Android. */
    public void toggleData() throws NoSuchMethodException {
        if (!appPlatform.equals(ANDROID)) {
            throw new NoSuchMethodException(unsupportedMethodForPlatform);
        }
        super.execute("toggleData");
    }

    /** Requests that the device locks itself.
     * Only available on Android. */
    public void lock() throws NoSuchMethodException {
        if (!appPlatform.equals(ANDROID)) {
            throw new NoSuchMethodException(unsupportedMethodForPlatform);
        }
        super.execute("lock");
    }

    /** Requests that the device unlocks itself.
     * Only available on Android. */
    public void unlock() throws NoSuchMethodException {
        if (!appPlatform.equals(ANDROID)) {
            throw new NoSuchMethodException(unsupportedMethodForPlatform);
        }
        super.execute("unlock");
    }

    /** Requests device's lock state, returning True if it is locked and False if not.
     * Only available on Android. */
    public boolean isLocked() throws NoSuchMethodException {
        if (!appPlatform.equals(ANDROID)) {
            throw new NoSuchMethodException(unsupportedMethodForPlatform);
        }
        Response response = super.execute("isLocked");
        System.out.println(response);
        return true;
    }
}

