# Requirements
- Installed Node.js 0.12 or greater. 

- At least an appium server instance installed via __npm__. 

# The basic principle.

It works the similar way as common [ChromeDriver](https://selenium.googlecode.com/git/docs/api/java/org/openqa/selenium/chrome/ChromeDriver.html), [InternetExplorerDriver](https://selenium.googlecode.com/git/docs/api/java/org/openqa/selenium/ie/InternetExplorerDriver.html) of Selenium project or [PhantomJSDriver](http://cdn.ivandemarino.me/phantomjsdriver-javadoc/org/openqa/selenium/phantomjs/PhantomJSDriver.html). They use subclasses of the [DriverService](https://selenium.googlecode.com/git/docs/api/java/org/openqa/selenium/remote/service/DriverService.html).

# Which capabilities this feature provides

This feature providese abilities and options of the starting of a local Appium node server. End users still able to open apps as usual 

```java
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
    capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
    capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);
    driver = new AndroidDriver<>(new URL("remoteOrLocalAddress"), capabilities);        
```

when the server is launched locally\remotely. Also user is free to launch a local Appium node server and open their app for the further testing the following way:

```java
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
    capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
    capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);
    driver = new AndroidDriver<>(capabilities);        
``` 

# How to prepare the local service before the starting


## If there is no specific parameters then 

```java
    import io.appium.java_client.service.local.AppiumDriverLocalService;
    ...

    AppiumDriverLocalService service = AppiumDriverLocalService.buildDefaultService();
    service.start();
    ...
    service.stop();
``` 

### FYI

There are possible problems related to local environment which could break this:
```java
AppiumDriverLocalService service = AppiumDriverLocalService.buildDefaultService();
```

It is more usual for UNIX/LINUX-like OS's. Also there are situations when should be used an another Node.JS instance, e.g. the instance which is installed in the directory that differs from one defined at the PATH environmental variable. The same may be true for Appium node server (it is related to _appium.js_ file (v <= 1.4.16) and _main.js_ (v >= 1.5.0)).

At this case user is able to set up values of the **NODE_BINARY_PATH** (The environmental variable used to define the path to executable NodeJS file (node.exe for WIN and node for Linux/MacOS X)) and the **APPIUM_BINARY_PATH** (The environmental variable used to define the path to executable appium.js (1.4.x and lower) or main.js (1.5.x and higher)) environmental variables/system properties. Also it is possible to define these values programmatically: 

```java
//appium.node.js.exec.path
System.setProperty(AppiumServiceBuilder.NODE_PATH , 
"the path to the desired node.js executable");

System.setProperty(AppiumServiceBuilder.APPIUM_PATH , 
"the path to the desired appium.js or main.js");

AppiumDriverLocalService service = AppiumDriverLocalService.buildDefaultService();
```

## If there should be non default parameters specified then 

```java
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
...

AppiumDriverLocalService service = AppiumDriverLocalService.
buildService(new AppiumServiceBuilder().
withArgument(GeneralServerFlag.TEMP_DIRECTORY,
                    "The_path_to_the_temporary_directory"));
``` 

or 

```java
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
...

AppiumDriverLocalService service = new AppiumServiceBuilder().
withArgument(GeneralServerFlag.TEMP_DIRECTORY,
                    "The_path_to_the_temporary_directory").build();
``` 

Lists of available server side flags are here:

- io.appium.java_client.service.local.flags.GeneralServerFlag;
- io.appium.java_client.service.local.flags.AndroidServerFlag;
- io.appium.java_client.service.local.flags.IOSServerFlag

 
## Which parameters also can be defined

- If it is necessary to define some specific port or any free port

```java
new AppiumServiceBuilder().usingPort(4000);
``` 

or 

```java
new AppiumServiceBuilder().usingAnyFreePort();
``` 

- If it is necessary to use another IP address

```java
new AppiumServiceBuilder().withIPAddress("127.0.0.1");
``` 

- If it is necessary to define output log file

```java
import java.io.File; 
  ...

new AppiumServiceBuilder().withLogFile(logFile);
``` 

- If it is necessary to define another Node.js executable file

```java
import java.io.File;
 
...

new AppiumServiceBuilder().usingDriverExecutable(nodeJSExecutable);
``` 

- If it is necessary to define another appium.js/main.js file

```java
import java.io.File;
 
...
//appiumJS is the full or relative path to 
//the appium.js (v<=1.4.16) or maim.js (v>=1.5.0)
new AppiumServiceBuilder().withAppiumJS(new File(appiumJS));
``` 

- It is possible to define server capabilities (node server v >= 1.5.0)

```java
DesiredCapabilities serverCapabilities = new DesiredCapabilities();
...//the capability filling

AppiumServiceBuilder builder = new AppiumServiceBuilder().
withCapabilities(serverCapabilities);
AppiumDriverLocalService service = builder.build();
service.start();
...
service.stop();
```

Capabilities which are used by a builder can be completed/orerriden any similar way:

```java
DesiredCapabilities serverCapabilities = new DesiredCapabilities();
serverCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
serverCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
serverCapabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
serverCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
serverCapabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
serverCapabilities.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE, 
chrome.getAbsolutePath()); //this capability set can be used for all cases

AppiumServiceBuilder builder = new AppiumServiceBuilder().
withCapabilities(serverCapabilities);
AppiumDriverLocalService service = builder.build();

DesiredCapabilities clientCapabilities = new DesiredCapabilities();
clientCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, 
"io.appium.android.apis");
clientCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, 
".view.WebView1");
```

then

```java
AndroidDriver<MobileElement> driver = 
new AndroidDriver<>(service, clientCapabilities);
```

or 

```java
AndroidDriver<MobileElement> driver = 
new AndroidDriver<>(builder, clientCapabilities);
```

or 

```java
service.start();
AndroidDriver<MobileElement> driver = 
new AndroidDriver<>(service.getUrl(), clientCapabilities);
```

# How to create an AppiumDriver instance

Many constructors of [AndroidDriver](http://appium.github.io/java-client/io/appium/java_client/android/AndroidDriver.html)/[IOSDriver](http://appium.github.io/java-client/io/appium/java_client/ios/IOSDriver.html) use [AppiumDriverLocalService](http://appium.github.io/java-client/io/appium/java_client/service/local/AppiumDriverLocalService.html) or [AppiumServiceBuilder](http://appium.github.io/java-client/io/appium/java_client/service/local/AppiumServiceBuilder.html) as parameters.
The list of constructors is below.

```java
public AndroidDriver(URL remoteAddress,
            org.openqa.selenium.Capabilities desiredCapabilities)

public AndroidDriver(URL remoteAddress,
            org.openqa.selenium.remote.http.HttpClient.Factory httpClientFactory,
            org.openqa.selenium.Capabilities desiredCapabilities)

public AndroidDriver(AppiumDriverLocalService service,
            org.openqa.selenium.Capabilities desiredCapabilities)

public AndroidDriver(AppiumDriverLocalService service,
            org.openqa.selenium.remote.http.HttpClient.Factory httpClientFactory,
            org.openqa.selenium.Capabilities desiredCapabilities)

public AndroidDriver(AppiumServiceBuilder builder,
            org.openqa.selenium.Capabilities desiredCapabilities)

public AndroidDriver(AppiumServiceBuilder builder,
            org.openqa.selenium.remote.http.HttpClient.Factory httpClientFactory,
            org.openqa.selenium.Capabilities desiredCapabilities)

public AndroidDriver(org.openqa.selenium.remote.http.HttpClient.Factory httpClientFactory,
            org.openqa.selenium.Capabilities desiredCapabilities)

public AndroidDriver(org.openqa.selenium.Capabilities desiredCapabilities)
``` 

```java
public IOSDriver(URL remoteAddress,
            org.openqa.selenium.Capabilities desiredCapabilities)

public IOSDriver(URL remoteAddress,
            org.openqa.selenium.remote.http.HttpClient.Factory httpClientFactory,
            org.openqa.selenium.Capabilities desiredCapabilities)

public IOSDriver(AppiumDriverLocalService service,
            org.openqa.selenium.Capabilities desiredCapabilities)

public IOSDriver(AppiumDriverLocalService service,
            org.openqa.selenium.remote.http.HttpClient.Factory httpClientFactory,
            org.openqa.selenium.Capabilities desiredCapabilities)

public IOSDriver(AppiumServiceBuilder builder,
            org.openqa.selenium.Capabilities desiredCapabilities)

public IOSDriver(AppiumServiceBuilder builder,
            org.openqa.selenium.remote.http.HttpClient.Factory httpClientFactory,
            org.openqa.selenium.Capabilities desiredCapabilities)

public IOSDriver(org.openqa.selenium.remote.http.HttpClient.Factory httpClientFactory,
            org.openqa.selenium.Capabilities desiredCapabilities)

public IOSDriver(org.openqa.selenium.Capabilities desiredCapabilities)
``` 

An instance of __AppiumDriverLocalService__ which has passed through constructors will be stopped when

```java
  driver.quit();
``` 

If it is necessary to keep the service alive during a long time then something like that is available

```java
  service.start();
  
  ....

  new IOSDriver<MobileElement>(service.getUrl(), capabilities)
``` 
