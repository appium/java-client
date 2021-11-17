This is the list of main changes between major versions 7 and 8 of Appium 
java client. This list should help you to successfully migrate your 
existing automated tests codebase. 


## Strict W3C specification compatibility

- Java client now supports Selenium 4, which also means it is 
*strictly* W3C compliant. Old JWP-based servers are not supported 
anymore, and it won't be possible to use the new client version
with them. Capabilities that enforce the usage of JWP protocol
on Appium drivers don't have any effect anymore.
- The recommended way to provide capabilities for driver creation is
to use specific option builders inherited from
[BaseOptions class](https://github.com/appium/java-client/blob/master/src/main/java/io/appium/java_client/remote/options/BaseOptions.java).
For example
[XCUITestOptions](https://github.com/appium/java-client/blob/master/src/main/java/io/appium/java_client/ios/options/XCUITestOptions.java)
to create a XCUITest driver instance or
[UiAutomator2Options](https://github.com/appium/java-client/blob/master/src/main/java/io/appium/java_client/android/options/UiAutomator2Options.java)
to create an UiAutomator2 driver instance.
If there is no driver-specific options class for your driver then either use 
`BaseOptions` builder as the base class to define your capabilities or request 
driver developers to add one. _Do not_ use `DesiredCapabilities` class for this purpose in W3C context.
Check [unit tests](https://github.com/appium/java-client/blob/master/src/test/java/io/appium/java_client/drivers/options/OptionsBuildingTest.java)
for more examples on how to build driver options.

## Elements lookup

- All `findBy*` shortcut methods were removed. Consider using
`findElement[s](By. or AppiumBy.)` instead.
- `MobileBy` class has been deprecated. Consider using
[AppiumBy](https://github.com/appium/java-client/blob/master/src/main/java/io/appium/java_client/AppiumBy.java)
instead. 
- All locator names in `AppiumBy` have been aligned to follow the common
(camelCase) naming strategy, e.g. `MobileBy.AccessibilityId` was changed
to `AppiumBy.accessibilityId`.
- The changes made in Selenium 4 broke `class name` selector strategy in Appium.
`AppiumBy.className` should be used instead of Selenium's `By.className` now.

## Time

- All methods that use TimeUnit class or where the time is passed as
a simple numeric value were replaced with their alternatives using
[java.time.Duration](https://docs.oracle.com/javase/8/docs/api/java/time/Duration.html) 
class.

## Events

- The current event firing mechanism that Appium java client uses 
has been deprecated in favour of the one that Selenium 4 provides
natively. Read [The-event_firing.md](The-event_firing.md) for more 
details on how to use it.

## AppiumDriver

- All `AppiumDriver` descendants and the base class itself are not generic
anymore and work with `WebElement` interface only.
- The base Appium driver does not extend `ContextAware`, `Rotatable` and other
mobile-specific interfaces. Instead, it only has the very basic set of methods.
Mobile specific extensions have been respectively moved to `IOSDriver` and
`AndroidDriver`.
- Removed the obsolete `HasSessionDetails` extensions as it was using legacy
JWP calls to retrieve session details.
- `DefaultGenericMobileDriver` class has been removed. Now `AppiumDriver` is
inherited directly from Selenium's `RemoteWebDriver`.

## MobileElement

- `DefaultGenericMobileElement` class has been removed completely together 
with its descendants (`MobileElement`, `IOSElement`, `AndroidElement` etc.). 
Use `WebElement` instead.
- Due to the above change the page factory is now only creating elements
that are instantiated from `RemoteWebElement` and implement `WebElement` interface.
- If you used some special methods that `MobileElement` or its descendants provided
then change these: 
  - `replaceValue` has been moved to the corresponding `AndroidDriver`
  instance and is called now `replaceElementValue`
  - use `sendKeys` method of `WebElement` interface instead of `setValue`.

## Touch Actions

- The `TouchAction` and `MultiTouchAction` classes have been deprecated.
The support of these actions will be removed from future Appium versions. 
Please use [W3C Actions](https://w3c.github.io/webdriver/#actions) instead 
or the corresponding extension methods for the driver (if available).
Check
  - https://www.youtube.com/watch?v=oAJ7jwMNFVU
  - https://appiumpro.com/editions/30-ios-specific-touch-action-methods
  - https://github.com/appium/appium/blob/master/docs/en/writing-running-appium/android/android-mobile-gestures.md
  - https://github.com/appium/appium/blob/master/docs/en/writing-running-appium/ios/ios-xctest-mobile-gestures.md
  - https://appiumpro.com/editions/29-automating-complex-gestures-with-the-w3c-actions-api
for more details on how to properly apply W3C Actions to your automation context.

## resetApp/launchApp/closeApp

- AppiumDriver methods `resetApp`, `launchApp` and `closeApp` have been deprecated as
they are going to be removed from future Appium versions. Check 
https://github.com/appium/appium/issues/15807 for more details.
