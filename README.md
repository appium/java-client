java-client
===========

Java language binding for writing Appium Tests, conforms to [Mobile JSON Wire Protocol](https://code.google.com/p/selenium/source/browse/spec-draft.md?repo=mobile)

Depends upon the Selenium Java client library, available [here](http://docs.seleniumhq.org/download/)

[Download the jar from Maven](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22io.appium%22%20AND%20a%3A%22java-client%22) or add the following to pom.xml:

```
<dependency>
  <groupId>io.appium</groupId>
  <artifactId>java-client</artifactId>
  <version>3.2.0</version>
</dependency>
```

It currently depends on selenium-java 2.47.1. If it is necessary to use another version of Selenium then you can configure pom.xml as follows:

```
<dependency>
  <groupId>io.appium</groupId>
  <artifactId>java-client</artifactId>
  <version>3.2.0</version>
  <exclusions>
    <exclusion>
      <groupId>org.seleniumhq.selenium</groupId>
      <artifactId>selenium-java</artifactId>
    </exclusion>
  </exclusions>
</dependency>

<dependency>
  <groupId>org.seleniumhq.selenium</groupId>
  <artifactId>selenium-java</artifactId>
  <version>${selenium.version.you.require}</version>
</dependency>
```

Javadocs: http://appium.github.io/java-client/

###Structure###

There is an abstract _AppiumDriver_ class which inherits from the Selenium Java Client.
The _AppiumDriver_ class contains all methods shared by iOS and Android.
_IOSDriver_ and _AndroidDriver_ both extend _AppiumDriver_ and provide more methods, and specific implementations for some methods.

In the same way, _IOSElement_ and _AndroidElement_ both are subclasses of _MobileElement_

You can instantiate and AppiumDriver with the class of element you want commands to return. For example
`AppiumDriver<MobileElement> driver;`
and now when you call the Find functions, they return elements of class MobileElement.
You can also instantiate drivers like this, to make things simpler:
`AndroidDriver<AndroidElement> driver = new AndroidDriver(.......`
`IOSElement el = driver.findElementByAccessiblityId('sample');`

###Added functions###
More can be found in the docs, but here's a quick list of features which this project has added to the usual selenium binding.


- startActivity()
- resetApp()
- getAppString()
- pressKeyCode()
- longPressKeyCode()
- longPressKey()
- currentActivity()
- pullFile()
- pushFile()
- pullFolder()
- replaceValue()
- hideKeyboard()
- runAppInBackground()
- performTouchAction()
- performMultiTouchAction()
- tap()
- swipe()
- pinch()
- zoom()
- getNamedTextField()
- isAppInstalled()
- installApp()
- removeApp()
- launchApp()
- closeApp()
- endTestCoverage()
- lockScreen()
- isLocked()
- shake()
- scrollTo()
- scrollToExact()
- openNotifications()
- Context Switching: .context(), .getContextHandles(), getContext())
- getNetworkConnection(), setNetworkConnection()
- ignoreUnimportantViews(), getSettings()
- toggleLocationServices()

Locators:
- findElementByAccessibilityId()
- findElementsByAccessibilityId()
- findElementByIosUIAutomation()
- findElementsByIosUIAutomation()
- findElementByAndroidUIAutomator()
- findElementsByAndroidUIAutomator()

## Note to developers! ##
If you are working on this project and use Intellij Idea, you need to change the compiler to the Eclipse compilers instead of the default.
If you are using the Eclipse IDE, make sure you are using verison Luna or later.

##Changelog##
*3.2.0*
- updated the dependency on Selenium to version 2.47.1
- the new dependency on commons-validator v1.4.1
- the ability to start programmatically/silently an Appium node server is provided now. Details please read at [#240](https://github.com/appium/java-client/pull/240).
Historical reference: [The similar solution](https://github.com/Genium-Framework/Appium-Support) has been designed by [@Hassan-Radi](https://github.com/Hassan-Radi).
The mentioned framework and the current solution use different approaches.
- Throwing declarations were added to some searching methods. The __"getMouse"__ method of RemoteWebDriver was marked __Deprecated__
- Add `replaceValue` method for elements.
- Replace `sendKeyEvent()` method in android with pressKeyCode(int key) and added: pressKeyCode(int key, Integer metastate), longPressKeyCode(int key), longPressKeyCode(int key, Integer metastate)

*3.1.1*
- Page-object findBy strategies are now aware of which driver (iOS or Android) you are using. For more details see the Pull Request: https://github.com/appium/java-client/pull/213
- If somebody desires to use their own Webdriver implementation then it has to implement HasCapabilities.
- Added a new annotation: `WithTimeout`. This annotation allows one to specify a specific timeout for finding an element which overrides the drivers default timeout. For more info see: https://github.com/appium/java-client/pull/210
- Corrected an uninformative Exception message.

*3.0.0*
- AppiumDriver class is now a Generic. This allows us to return elements of class MobileElement (and its subclasses) instead of always returning WebElements and requiring users to cast to MobileElement. See https://github.com/appium/java-client/pull/182
- Full set of Android KeyEvents added.
- Selenium client version updated to 2.46
- PageObject enhancements
- Junit dependency removed

*2.2.0*
- Added new TouchAction methods for LongPress, on an element, at x,y coordinates, or at an offset from within an element
- SwipeElementDirection changed. Read the documentation, it's now smarter about how/where to swipe
- Added APPIUM_VERSION MobileCapabilityType
- `sendKeyEvent()` moved from AppiumDriver to AndroidDriver
- `linkText` and `partialLinkText` locators added
- setValue() moved from MobileElement to iOSElement
- Fixed Selendroid PageAnnotations

*2.1.0*
- Moved hasAppString() from AndroidDriver to AppiumDriver
- Fixes to PageFactory
- Added @AndroidFindAll and @iOSFindAll
- Added toggleLocationServices() to AndroidDriver
- Added touchAction methods to MobileElement, so now you can do `element.pinch()`, `element.zoom()`, etc.
- Added the ability to choose a direction to swipe over an element. Use the `SwipeElementDirection` enums: `UP, DOWN, LEFT, RIGHT`

*2.0.0*
- AppiumDriver is now an abstract class, use IOSDriver and AndroidDriver which both extend it. You no longer need to include the `PLATFORM_NAME` desired capability since it's automatic for each class. Thanks to @TikhomirovSergey for all their work
- ScrollTo() and ScrollToExact() methods reimplemented
- Zoom() and Pinch() are now a little smarter and less likely to fail if you element is near the edge of the screen. Congratulate @BJap on their first PR!

*1.7.0*
- Removed `scrollTo()` and `scrollToExact()` methods because they relied on `complexFind()`. They will be added back in the next version!
- Removed `complexFind()`
- Added `startActivity()` method
- Added `isLocked()` method
- Added `getSettings()` and `ignoreUnimportantViews()` methods

*1.6.2*
- Added MobilePlatform interface (Android, IOS, FirefoxOS)
- Added MobileBrowserType interface (Safari, Browser, Chromium, Chrome)
- Added MobileCapabilityType.APP_WAIT_ACTIVITY
- Fixed small Integer cast issue (in Eclipse it won't compile)
- Set -source and -target of the Java Compiler to 1.7 (for maven compiler plugin)
- Fixed bug in Page Factory

*1.6.1*
- Fixed the logic for checking connection status on NetworkConnectionSetting objects

*1.6.0*
- Added @findBy annotations. Explanation here: https://github.com/appium/java-client/pull/68 Thanks to TikhomirovSergey
- Appium Driver now implements LocationContext interface, so setLocation() works for setting GPS coordinates

*1.5.0*
- Added MobileCapabilityType enums for desired capabilities
- `findElement` and `findElements` return MobileElement objects (still need to be casted, but no longer instantiated)
- new appium v1.2 `hideKeyboard()` strategies added
- `getNetworkConnection()` and `setNetworkConnection()` commands added

*1.4.0*
- Added openNotifications() method, to open the notifications shade on Android
- Added pullFolder() method, to pull an entire folder as a zip archive from a device/simulator
- Upgraded Selenium dependency to 2.42.2

*1.3.0*
- MultiGesture with a single TouchAction fixed for Android
- Now depends upon Selenium java client 2.42.1
- Cleanup of Errorcode handling, due to merging a change into Selenium

*1.2.1*
- fix dependency issue

*1.2.0*
- complexFind() now returns MobileElement objects
- added scrollTo() and scrollToExact() methods for use with complexFind()

*1.1.0*
- AppiumDriver now implements Rotatable. rotate() and getOrientation() methods added
- when no appium server is running, the proper error is thrown, instead of a NullPointerException

*1.0.2*
- recompiled to include some missing methods such as shake() and complexFind()

## Running tests

Run a test using

>  mvn -Dtest=io.appium.java_client.MobileDriverAndroidTest clean test
