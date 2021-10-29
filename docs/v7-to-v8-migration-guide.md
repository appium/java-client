This is the list of main changes between major versions 7 and 8 of Appium 
java client. This list should help you to successfully migrate your 
existing automated tests codebase. 

- Java client now supports Selenium 4, which also means it is 
*strictly* W3C compliant. Old JWP-based servers are not supported 
anymore, and it won't be possible to use the new client version
with them. Capabilities that enforce usage of JWP protocol don't have 
any effect anymore.
- All `findBy*` shortcut methods were removed. Consider using
`findElement[s]` instead.
- All methods that use TimeUnit class or where the time is passed as
a simple numeric value were replaced with their alternatives using
[java.time.Duration](https://docs.oracle.com/javase/8/docs/api/java/time/Duration.html) 
class.
- The current event firing mechanism that Appium java client uses 
has been deprecated in favour of the one that Selenium4 provides
natively. Read [The-event_firing.md](The-event_firing.md) for more 
details on how to use it.
- The recommended way to provide capabilities for driver creation is
to use specific option builders inherited from 
[BaseOptions class](https://github.com/appium/java-client/blob/master/src/main/java/io/appium/java_client/remote/options/BaseOptions.java).
For example 
[XCUITestOptions](https://github.com/appium/java-client/blob/master/src/main/java/io/appium/java_client/ios/options/XCUITestOptions.java) 
to create a XCUITest driver instance or
[UiAutomator2Options](https://github.com/appium/java-client/blob/master/src/main/java/io/appium/java_client/android/options/UiAutomator2Options.java) 
to create an UiAutomator2 driver instance.
Do not use `DesiredCapabilities` class for this purpose in W3C context.
- All `AppiumDriver` descendants and the base class itself are not generic
anymore and work with `WebElement` interface only.
- `DefaultGenericMobileElement` class has been removed completely together 
with its descendants (`MobileElement`, `IOSElement`, `AndroidElement` etc.). 
Use `WebElement` instead.
- Due to the above change the page factory is now only creating elements
that are instantiated from `RemoteWebElement` and implement `WebElement` interface.
- If you use some special methods that `MobileElement` or its descendants provided
before then change these: `replaceValue` has been moved to the driver instance and
is called now `replaceElementValue`; `setValue` has been moved to the driver 
instance and is called now `setElementValue`.
- `DefaultGenericMobileDriver` class has been removed. Now `AppiumDriver` is 
inherited directly from Selenium's `RemoteWebDriver`.
- The base Appium driver does not extend `ContextAware`, `Rotatable` and other
mobile-specific interfaces. Instead, it only has the very basic set of methods.
Mobile specific extensions have been respectively moved to `IOSDriver` and
`AndroidDriver`.
- Removed the obsolete `HasSessionDetails` extensions as it was using legacy 
JWP calls to retrieve session details.
`MobileBy` class has been deprecated. Consider using 
[AppiumBy](TBD) 
instead.
Also, all locator names in `AppiumBy` have been aligned to follow the common
(camelCase) naming strategy, e.g. `MobileBy.AccessibilityId` was changed
to `AppiumBy.accessibilityId`.
