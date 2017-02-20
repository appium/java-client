# Steps: 

- you have to prepare environment for Android. Details are provided here: http://appium.io/slate/en/master/?java#setup-(android)

- you have to download the desktop app [for Windows or Mac OS X](https://bitbucket.org/appium/appium.app/downloads/) or install it using _npm_ 
_$ npm install -g appium_ or _$ npm install appium@required_version_

- it needs to launch the appium server. If you use the server installed via npm then 

  _$ node **the_path_to_js_file** --arg1 value1 --arg2 value2_ 
where **the_path_to_js_file** is the full path to **appium.js** file (if the node server version version <= 1.4.16) or **main.js** (if the node server version version >= 1.5.0). It is not necessary to use arguments. The list of arguments: http://appium.io/slate/en/master/?java#appium-server-arguments


# The starting of an app

It looks like creation of a common [RemoteWebDriver](https://selenium.googlecode.com/git/docs/api/java/org/openqa/selenium/remote/RemoteWebDriver.html) instance. 

[Common capabilities](http://appium.io/slate/en/master/?java#the---default-capabilities-flag)

[Android-specific capabilities](http://appium.io/slate/en/master/?java#android-only)

[Common capabilities provided by Java client](http://appium.github.io/java-client/io/appium/java_client/remote/MobileCapabilityType.html)

[Android-specific capabilities provided by Java client](http://appium.github.io/java-client/io/appium/java_client/remote/AndroidMobileCapabilityType.html)

```java
import java.io.File;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
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

If it needs to start browser then: 

```java
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import java.net.URL;


...
DesiredCapabilities capabilities = new DesiredCapabilities();
capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.CHROME);
//if it is necessary to use the default Android browser then MobileBrowserType.BROWSER
//is your choise
...
//you are free to set additional capabilities 
AppiumDriver<MobileElement> driver = new AndroidDriver<>(
new URL("http://target_ip:used_port/wd/hub"), capabilities);
```

or 

```java
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;


...
DesiredCapabilities capabilities = new DesiredCapabilities();
capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.CHROME);
//you are free to set additional capabilities 
RemoteWebDriver driver = new RemoteWebDriver(
new URL("http://target_ip:used_port/wd/hub"), capabilities);
```