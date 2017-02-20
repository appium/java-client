# Steps: 

- you have to prepare environment for iOS. [Details are provided here](http://appium.io/slate/en/master/?ruby#system-setup-(ios))

- it needs to launch the appium server. You can launch Appium desktop application. If you use the server installed via npm then 

  _$ node **the_path_to_js_file** --arg1 value1 --arg2 value2_ 
It is not necessary to use arguments. [The list of arguments](http://appium.io/slate/en/master/?java#appium-server-arguments)

# The starting of an app

It looks like creation of a common [RemoteWebDriver](https://selenium.googlecode.com/git/docs/api/java/org/openqa/selenium/remote/RemoteWebDriver.html) instance. 

[Common capabilities](http://appium.io/slate/en/master/?ruby#the---default-capabilities-flag)

[iOS-specific capabilities](http://appium.io/slate/en/master/?ruby#ios-only)

[Common capabilities provided by Java client](http://appium.github.io/java-client/io/appium/java_client/remote/MobileCapabilityType.html)

[iOS-specific capabilities provided by Java client](http://appium.github.io/java-client/io/appium/java_client/remote/IOSMobileCapabilityType.html)


```java
import java.io.File;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import java.net.URL;

...
File app  = new File("The absolute or relative path to an *.app, *.zip or ipa file");
DesiredCapabilities capabilities = new DesiredCapabilities();
capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "The_target_version");
capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
//The_target_version is the supported iOS version, e.g. 8.1, 8.2, 9.2 etc
capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
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
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.MobileElement;
import java.net.URL;

...
File app  = new File("The absolute or relative path to an *.app, *.zip or ipa file");
DesiredCapabilities capabilities = new DesiredCapabilities();
capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "The_target_version");
//The_target_version is the supported iOS version, e.g. 8.1, 8.2, 9.2 etc
capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
//you are free to set additional capabilities 
AppiumDriver<MobileElement> driver = new IOSDriver<>(
new URL("http://target_ip:used_port/wd/hub"), //if it needs to use locally started server
//then the target_ip is 127.0.0.1 or 0.0.0.0
//the default port is 4723
capabilities);
```

##If it needs to start browser then 

```java
capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.SAFARI);
```

##There are two automation types

Default iOS Automation (v < iOS 10.x) does not require any specific capability. However you can 
```java
capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);
```

You have to define this automation type to be able to use XCUIT mode for new iOS versions (v > 10.x)
```java
capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
```

# Possible cases

You can use ```io.appium.java_client.AppiumDriver``` and ```io.appium.java_client.ios.IOSDriver``` as well. The main difference 
is that ```IOSDriver``` implements all API that describes interaction with iOS native/hybrid app.   ```AppiumDriver``` allows to
use iOS-specific API eventually.
 
_Samples of the searching by iOSNsPredicateString using_ ```io.appium.java_client.AppiumDriver``` 

```java
import io.appium.java_client.FindsByIosNSPredicate;
import io.appium.java_client.ios.IOSElement;

...

FindsByIosNSPredicate<IOSElement> findsByIosNSPredicate = new FindsByIosNSPredicate<IOSElement>() {
    @Override
    public IOSElement findElement(String by, String using) {
        return driver.findElement(by, using);
    }

    @Override
    public List<IOSElement> findElements(String by, String using) {
        return driver.findElements(by, using);
    }
};
        
findsByIosNSPredicate.findElementByIosNsPredicate("some predicate");
```

```java
driver.findElement(MobileBy.iOSNsPredicateString("some predicate"));
```

All that ```IOSDriver``` can do by design.
