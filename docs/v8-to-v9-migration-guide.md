This is the list of main changes between major versions 8 and 9 of Appium 
java client. This list should help you to successfully migrate your 
existing automated tests codebase. 


## The support for Java compilers below version 11 has been dropped 

- The minimum supported Java version is now 11. The library won't work
with Java compilers below this version.

## The minimum supported Selenium version is set to 4.14.1

- Selenium versions below 4.14.1 won't work with Appium java client 9+.
Check the [Compatibility Matrix](../README.md#compatibility-matrix) for more
details about versions compatibility.

## Removed previously deprecated items

- `MobileBy` class has been removed. Use 
[AppiumBy](../src/main/java/io/appium/java_client/AppiumBy.java) instead
- `launchApp`, `resetApp` and `closeApp` methods along with their
`SupportsLegacyAppManagement` container. 
Use [the corresponding extension methods](https://github.com/appium/appium/issues/15807) instead.
- `WindowsBy` class and related location strategies.
- `ByAll` class has been removed in favour of the same class from Selenium lib.
- `AndroidMobileCapabilityType` interface. Use 
[UIAutomator2 driver options](../src/main/java/io/appium/java_client/android/options/UiAutomator2Options.java) 
or [Espresso driver options](../src/main/java/io/appium/java_client/android/options/EspressoOptions.java) instead.
- `IOSMobileCapabilityType` interface. Use 
[XCUITest driver options](../src/main/java/io/appium/java_client/ios/options/XCUITestOptions.java) instead.
- `MobileCapabilityType` interface. Use 
[driver options](../src/main/java/io/appium/java_client/remote/options/BaseOptions.java) instead.
- `MobileOptions` class. Use
[driver options](../src/main/java/io/appium/java_client/remote/options/BaseOptions.java) instead.
- `YouiEngineCapabilityType` interface. Use
[driver options](../src/main/java/io/appium/java_client/remote/options/BaseOptions.java) instead.
- Several misspelled methods. Use properly spelled alternatives instead.
- `startActivity` method from AndroidDriver. Use 
[mobile: startActivity](https://github.com/appium/appium-uiautomator2-driver#mobile-startactivity) 
extension method instead.
- `APPIUM` constant from the AutomationName interface. It is not needed anymore.
- `PRE_LAUNCH` value from the GeneralServerFlag enum. It is not needed anymore.

## Moved items

- `AppiumUserAgentFilter` class to `io.appium.java_client.internal.filters` package.
