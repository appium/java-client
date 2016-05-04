java-client
===========

Java language binding for writing Appium Tests, conforms to [Mobile JSON Wire Protocol](https://github.com/SeleniumHQ/mobile-spec/blob/master/spec-draft.md)

Depends upon the Selenium Java client library, available [here](http://docs.seleniumhq.org/download/)

[Download the jar from Maven](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22io.appium%22%20AND%20a%3A%22java-client%22) or add the following to pom.xml:

```
<dependency>
  <groupId>io.appium</groupId>
  <artifactId>java-client</artifactId>
  <version>3.4.1</version>
</dependency>
```

It currently depends on selenium-java 2.53.0. If it is necessary to use another version of Selenium then you can configure pom.xml as follows:

```
<dependency>
  <groupId>io.appium</groupId>
  <artifactId>java-client</artifactId>
  <version>3.4.1</version>
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
- getAppStringMap()
- pressKeyCode()
- longPressKeyCode()
- longPressKey()
- currentActivity()
- getDeviceTime()
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
- isLocked()
- shake()
- scrollTo()
- scrollToExact()
- openNotifications()
- Context Switching: .context(), .getContextHandles(), getContext())
- getNetworkConnection(), setNetworkConnection()
- ignoreUnimportantViews(), getSettings()
- toggleLocationServices()
- lockDevice()
- unlockDevice()

Locators:
- findElementByAccessibilityId()
- findElementsByAccessibilityId()
- findElementByIosUIAutomation()
- findElementsByIosUIAutomation()
- findElementByAndroidUIAutomator()
- findElementsByAndroidUIAutomator()
- findElementByIosNsPredicate()
- findElementsByIosNsPredicate()

## Note to developers! ##
If you are working on this project and use Intellij Idea, you need to change the compiler to the Eclipse compilers instead of the default.
If you are using the Eclipse IDE, make sure you are using version Luna or later.

##Changelog##
*4.0.0 (under construction yet)*
- FIX of TouchAction. Instances of the TouchAction class are reusable now
- FIX of the swiping issue (iOS, server version >= 1.5.0). Now the swiping is implemented differently by 
AndroidDriver and IOSDriver. Thanks to [@truebit](https://github.com/truebit) and [@nuggit32](https://github.com/nuggit32) for the catching.
- the project was integrated with [maven-checkstyle-plugin](https://maven.apache.org/plugins/maven-checkstyle-plugin/). Thanks to [@SrinivasanTarget](https://github.com/SrinivasanTarget) for the work
- all code marked `@Deprecated` was removed. Java client won't support old servers (v<1.5.0)
anymore.
- source code was improved according to code style checking rules. 
- The refactoring of `io.appium.java_client.internal.JsonToMobileElementConverter`. Now it accepts 
`org.openqa.selenium.remote.RemoteWebDriver` as the constructor parameter. It is possible to re-use 
`io.appium.java_client.android.internal.JsonToAndroidElementConverter` or 
`io.appium.java_client.ios.internal.JsonToIOSElementConverter` by RemoteWebDriver when it is needed.
- Constructors of the abstract `io.appium.java_client.AppiumDriver` were redesigned. Now they require 
a subclass of `io.appium.java_client.internal.JsonToMobileElementConverter`. Constructors of 
`io.appium.java_client.android.AndroidDriver` and `io.appium.java_client.ios.IOSDriver` are same still.
- the integration with `org.owasp dependency-check-maven` was added. Thanks to [@saikrishna321](https://github.com/saikrishna321) 
for the work.
- the integration with `org.jacoco jacoco-maven-plugin` was added. Thanks to [@SrinivasanTarget](https://github.com/SrinivasanTarget) for the contribution.
- the searching for elements by nspredicate string was added. It requires the [XCUITest mode](https://github.com/appium/java-client/blob/master/docs/Installing-xcuitest-driver.md). Thanks to [@rafael-chavez](https://github.com/appium/java-client/pull/352) for the contribution
- the `nsPredicate` parameter was added to the `iOSFindBy` annotation
- the `commandRepository` field is public now. The modification of the `MobileCommand`

...

*3.4.1*
- Update to Selenium v2.53.0
- all dependencies were updated to latest versions
- the dependency on org.apache.commons commons-lang3 v3.4 was added
- the fix of Widget method invocation.[#340](https://github.com/appium/java-client/issues/340). A class visibility was taken into account. Thanks to [aznime](https://github.com/aznime) for the catching.
Server flags were added:
  - GeneralServerFlag.ASYNC_TRACE
  - IOSServerFlag.WEBKIT_DEBUG_PROXY_PORT
- Source code was formatted using [eclipse-java-google-style.xml](https://google-styleguide.googlecode.com/svn/trunk/eclipse-java-google-style.xml). This is not the complete solution. The code style checking is going to be added further. Thanks to [SrinivasanTarget](https://github.com/SrinivasanTarget) for the work!

*3.4.0*
- Update to Selenium v2.52.0
- `getAppStrings()` methods are deprecated now. They are going to be removed. `getAppStringMap()` methods were added and now return a map with app strings (keys and values)
instead of a string. Thanks to [@rgonalo](https://github.com/rgonalo) for the contribution.
- Add `getAppStringMap(String language, String stringFile)` method to allow searching app strings in the specified file
- FIXED of the bug which causes deadlocks of AppiumDriver LocalService in multithreading. Thanks to [saikrishna321](https://github.com/saikrishna321) for the [bug report](https://github.com/appium/java-client/issues/283).
- FIXED Zoom methods, thanks to [@kkhaidukov](https://github.com/kkhaidukov)
- FIXED The issue of compatibility of AppiumServiceBuilder with Appium node server v >= 1.5.x. Take a look at [#305](https://github.com/appium/java-client/issues/305)
- `getDeviceTime()` was added. Thanks to [@SrinivasanTarget](https://github.com/SrinivasanTarget) for the contribution.
- FIXED `longPressKeyCode()` methods. Now they use the convenient JSONWP command.Thanks to [@kirillbilchenko](https://github.com/kirillbilchenko) for the proposed fix.
- FIXED javadoc.
- Page object tools were updated. Details read here: [#311](https://github.com/appium/java-client/issues/311), [#313](https://github.com/appium/java-client/pull/313), [#317](https://github.com/appium/java-client/pull/317). By.name locator strategy is deprecated for Android and iOS. It is still valid for the Selendroid mode. Thanks to [@SrinivasanTarget](https://github.com/SrinivasanTarget) for the helping.
- The method `lockScreen(seconds)` is deprecated and it is going to be removed in the next release. Since Appium node server v1.5.x it is recommended to use
`AndroidDriver.lockDevice()...AndroidDriver.unlockDevice()` or `IOSDriver.lockDevice(int seconds)` instead. Thanks to [@namannigam](https://github.com/namannigam) for
the catching. Read [#315](https://github.com/appium/java-client/issues/315)
- `maven-release-plugin` was added to POM.XML configuration
- [#320](https://github.com/appium/java-client/issues/320) fix. The `Widget.getSelfReference()` was added. This method allows to extract a real widget-object from inside a proxy at some extraordinary situations. Read: [PR](https://github.com/appium/java-client/pull/327). Thanks to [SergeyErmakovMercDev](https://github.com/SergeyErmakovMercDev) for the reporting.
- all capabilities were added according to [this description](https://github.com/appium/appium/blob/1.5/docs/en/writing-running-appium/caps.md). There are three classes: `io.appium.java_client.remote.MobileCapabilityType` (just modified), `io.appium.java_client.remote.AndroidMobileCapabilityType` (android-specific capabilities), `io.appium.java_client.remote.IOSMobileCapabilityType` (iOS-specific capabilities). Details are here: [#326](https://github.com/appium/java-client/pull/326)
- some server flags were marked `deprecated` because they are deprecated since server node v1.5.x. These flags are going to be removed at the java client release. Details are here: [#326](https://github.com/appium/java-client/pull/326)
- The ability to start Appium node programmatically using desired capabilities. This feature is compatible with Appium node server v >= 1.5.x. Details are here: [#326](https://github.com/appium/java-client/pull/326)

*3.3.0*
- updated the dependency on Selenium to version 2.48.2
- bug fix and enhancements of io.appium.java_client.service.local.AppiumDriverLocalService
    - FIXED bug which was found and reproduced with Eclipse for Mac OS X. Please read about details here: [#252](https://github.com/appium/java-client/issues/252)
    Thanks to [saikrishna321](https://github.com/saikrishna321) for the bug report
    - FIXED bug which was found out by [Jonahss](https://github.com/Jonahss). Thanks for the reporting. Details: [#272](https://github.com/appium/java-client/issues/272)
    and [#273](https://github.com/appium/java-client/issues/273)
    - For starting an appium server using localService, added additional environment variable to specify the location of Node.js binary: NODE_BINARY_PATH
    - The ability to set additional output streams was provided
- The additional __startActivity()__ method was added to AndroidDriver. It allows to start activities without the stopping of a target app
Thanks to [deadmoto](https://github.com/deadmoto) for the contribution
- The additional extension of the Page Object design pattern was designed. Please read about details here: [#267](https://github.com/appium/java-client/pull/267)
- New public constructors to AndroidDriver/IOSDriver that allow passing a custom HttpClient.Factory Details: [#276](https://github.com/appium/java-client/pull/278) thanks to [baechul](https://github.com/baechul)

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
