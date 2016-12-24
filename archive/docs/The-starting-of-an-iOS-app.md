# Steps: 

- you have to prepare environment for iOS. Details are provided here: http://appium.io/slate/en/master/?ruby#system-setup-(ios)

- you have to download the desktop app [for Mac OS X](https://bitbucket.org/appium/appium.app/downloads/) or install it using _npm_ 
_$ npm install -g appium_ or _$ npm install appium@required_version_

- it needs to launch the appium server. If you use the server installed via npm then 

  _$ node **the_path_to_js_file** --arg1 value1 --arg2 value2_ 
where **the_path_to_js_file** is the full path to **appium.js** file (if the node server version version <= 1.4.16) or **main.js** (if the node server version version >= 1.5.0). It is not necessary to use arguments. The list of arguments: http://appium.io/slate/en/master/?ruby#appium-server-arguments

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

If it needs to start browser then: 

```java
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import java.net.URL;


...
DesiredCapabilities capabilities = new DesiredCapabilities();
capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "The_target_version");
//The_target_version is the supported iOS version, e.g. 8.1, 8.2, 9.2 etc
capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.SAFARI);
...
//you are free to set additional capabilities 
AppiumDriver<MobileElement> driver = new IOSDriver<>(
new URL("http://target_ip:used_port/wd/hub"), capabilities);
```

or 

```java
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.remote.MobileBrowserType;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;


...
DesiredCapabilities capabilities = new DesiredCapabilities();
capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "The_target_version");
//The_target_version is the supported iOS version, e.g. 8.1, 8.2, 9.2 etc
capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.SAFARI);
//you are free to set additional capabilities 
RemoteWebDriver driver = new RemoteWebDriver(
new URL("http://target_ip:used_port/wd/hub"), capabilities);
```
