# Steps: 

- you have to prepare environment for Android. [Details are provided here](https://appium.io/docs/en/drivers/android-uiautomator2/#basic-setup)

- it needs to launch the appium server. You can launch Appium desktop application. If you use the server installed via npm then 

  _$ node **the_path_to_main.js_file** --arg1 value1 --arg2 value2_ 
It is not necessary to use arguments. [The list of arguments](https://appium.io/docs/en/writing-running-appium/server-args/)


# The starting of an app

It looks like creation of a common [RemoteWebDriver](https://seleniumonehq.github.io/seleniumone/docs/api/java/org/openqa/seleniumone/remote/RemoteWebDriver.html) instance. 

[Common capabilities](https://appium.io/docs/en/writing-running-appium/caps/#general-capabilities)

[Android-specific capabilities](https://appium.io/docs/en/writing-running-appium/caps/#android-only)

[Common capabilities provided by Java client](https://javadoc.io/page/io.appium/java-client/latest/io/appium/java_client/remote/MobileCapabilityType.html)

[Android-specific capabilities provided by Java client](https://javadoc.io/page/io.appium/java-client/latest/io/appium/java_client/remote/AndroidMobileCapabilityType.html)

```java
import java.io.File;
import org.openqa.seleniumone.remote.DesiredCapabilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.MobileElement;
import java.net.URL;

...
File app  = new File("The absolute or relative path to an *.apk file");
DesiredCapabilities capabilities = new DesiredCapabilities();
capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
//you are free to set additional capabilities 
AppiumDriver<MobileElement> driver = new AppiumDriver<>(
new URL("http://target_ip:used_port/wd/hub"), //if it needs to use locally started server
//then the target_ip is 127.0.0.1 or 0.0.0.0
//the default port is 4723
capabilities);
```

or

```java
import java.io.File;
import org.openqa.seleniumone.remote.DesiredCapabilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import java.net.URL;

...
File app  = new File("The absolute or relative path to an *.apk file");
DesiredCapabilities capabilities = new DesiredCapabilities();
capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
//you are free to set additional capabilities 
AppiumDriver<MobileElement> driver = new AndroidDriver<>(
new URL("http://target_ip:used_port/wd/hub"), //if it needs to use locally started server
//then the target_ip is 127.0.0.1 or 0.0.0.0
//the default port is 4723
capabilities);
```


## If it needs to start browser then

This capability should be used

```java
capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.CHROME);
//if it is necessary to use the default Android browser then MobileBrowserType.BROWSER
//is your choice
```

## There are three automation types

```java
capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.SELENDROID);
```

This automation type is usually recommended for old versions (<4.2) of Android.

Default Android UIAutomator does not require any specific capability. However you can 
```java
capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);
```

You have to define this automation type to be able to use Android UIAutomator2 for new Android versions
```java
capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
```

# Possible cases

You can use ```io.appium.java_client.AppiumDriver``` and ```io.appium.java_client.android.AndroidDriver``` as well. The main difference 
is that ```AndroidDriver``` implements all API that describes interaction with Android native/hybrid app.   ```AppiumDriver``` allows to
use Android-specific API eventually.
 
 _The sample of the activity starting by_ ```io.appium.java_client.AppiumDriver```
 
 ```java
 import io.appium.java_client.android.StartsActivity;
 import io.appium.java_client.android.Activity;

...

StartsActivity startsActivity = new StartsActivity() {
    @Override
    public Response execute(String driverCommand, Map<String, ?> parameters) {
        return driver.execute(driverCommand, parameters);
    }

    @Override
    public Response execute(String driverCommand) {
        return driver.execute(driverCommand);
    }
};

Activity activity = new Activity("app package goes here", "app activity goes here")
    .setWaitAppPackage("app wait package goes here");
    .setWaitAppActivity("app wait activity goes here");
StartsActivity startsActivity.startActivity(activity);
 ```
 
_Samples of the searching by AndroidUIAutomator using_ ```io.appium.java_client.AppiumDriver``` 

```java
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.android.AndroidElement;

...

FindsByAndroidUIAutomator<AndroidElement> findsByAndroidUIAutomator = 
    new FindsByAndroidUIAutomator<AndroidElement>() {
    @Override
    public AndroidElement findElement(String by, String using) {
        return driver.findElement(by, using);
    }

    @Override
    public List<AndroidElement> findElements(String by, String using) {
        return driver.findElements(by, using);
    };
};

findsByAndroidUIAutomator.findElementByAndroidUIAutomator("automatorString");
```

```java
driver.findElement(MobileBy.AndroidUIAutomator("automatorString"));
```

All that ```AndroidDriver``` can do by design.
