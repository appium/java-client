#java-client

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.appium/java-client/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.appium/java-client)
[![Javadoc](https://javadoc-emblem.rhcloud.com/doc/io.appium/java-client/badge.svg)](http://www.javadoc.io/doc/io.appium/java-client)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/f365c5e9458b42bf8a5b1d928d7e4f48)](https://www.codacy.com/app/appium/java-client)
[![Build Status](https://travis-ci.org/appium/java-client.svg?branch=master)](https://travis-ci.org/appium/java-client)

This is the Java language binding for writing Appium Tests, conforms to [Mobile JSON Wire Protocol](https://github.com/SeleniumHQ/mobile-spec/blob/master/spec-draft.md)

[How to install it and to use it](https://github.com/appium/java-client/blob/master/docs/Installing-the-project.md)

[API docs](http://appium.github.io/java-client/)

###Structure###

There is an abstract `io.appium.java_client.AppiumDriver` class which extends `org.openqa.selenium.remote.RemoteWebDriver` 
from the Selenium Java Client. The `io.appium.java_client.AppiumDriver` class contains all methods shared by iOS and Android.
`io.appium.java_client.ios.IOSDriver` and `io.appium.java_client.android.AndroidDriver` both extend `io.appium.java_client.AppiumDriver` 
and provide more methods, and specific implementations for some methods.

In the same way, `io.appium.java_client.ios.IOSElement` and `io.appium.java_client.android.AndroidElement` both are subclasses of 
`io.appium.java_client.MobileElement`


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
- isAppInstalled()
- installApp()
- removeApp()
- launchApp()
- closeApp()
- endTestCoverage()
- isLocked()
- shake()
- getSessionDetails()
- openNotifications()
- Context Switching: .context(), .getContextHandles(), getContext())
- setConnection(), getConnection()
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

### Features and other interesting information###

You can get it on [WIKI](https://github.com/appium/java-client/wiki)

## Changelog#
*5.0.0 (under construction yet)*
- **[MAJOR ENHANCEMENT]**: Migration to Java 8. Epic: [#399](https://github.com/appium/java-client/issues/399)
  - API with default implementation. PR [#470](https://github.com/appium/java-client/pull/470)
  - Tools that provide _Page Object_ engines were redesigned. The migration to [repeatable annotations](http://docs.oracle.com/javase/tutorial/java/annotations/repeating.html). Details you can read there: [#497](https://github.com/appium/java-client/pull/497). [Documentation was synced as well](https://github.com/appium/java-client/blob/master/docs/Page-objects.md#also-it-is-possible-to-define-chained-or-any-possible-locators).
- **[MAJOR ENHANCEMENT]**: Migration from Maven to Gradle. Feature request is [#214](https://github.com/appium/java-client/issues/214). Fixes: [#442](https://github.com/appium/java-client/pull/442), [#465](https://github.com/appium/java-client/pull/465).
- **[MAJOR ENHANCEMENT]****[MAJOR REFACTORING]**. Non-abstract **AppiumDriver** and **MobileElement**: 
  - Now the `io.appium.java_client.AppiumDriver` can use an instance of any `io.appium.java_client.MobileBy` subclass for the searching. It should work as expected when current session supports the given selector. It will throw `org.openqa.selenium.WebDriverException` otherwise. [#462](https://github.com/appium/java-client/pull/462)
  - The new interface `io.appium.java_client.FindsByFluentSelector` was added. [#462](https://github.com/appium/java-client/pull/462)
  - API was redesigned: 
  
    these interfaces were marked deprecated and they are going to be removed [#513](https://github.com/appium/java-client/pull/513)[#514](https://github.com/appium/java-client/pull/514):
      - `io.appium.java_client.DeviceActionShortcuts`
      - `io.appium.java_client.android.AndroidDeviceActionShortcuts`
      - `io.appium.java_client.ios.IOSDeviceActionShortcuts`
      
    instead following inerfaces were designed:
      - `io.appium.java_client.HasDeviceTime`
      - `io.appium.java_client.HidesKeyboard`
      - `io.appium.java_client.HidesKeyboardWithKeyName`
      - `io.appium.java_client.PressesKeyCode`
      - `io.appium.java_client.ios.ShakesDevice`
      - `io.appium.java_client.HasSessionDetails`
       _That was done because Windows automation tools have some features that were considered as Android-specific and iOS-specific._
      - `io.appium.java_client.CreatesSwipeAction`  
      
    The list of methods which were marked _deprecated_ and they are going to be removed
      - `AppiumDriver#swipe(int, int, int, int, int)`
      - `AppiumDriver#pinch(WebElement)`
      - `AppiumDriver#pinch(int, int)`
      - `AppiumDriver#zoom(WebElement)`
      - `AppiumDriver#zoom(int, int)`
      - `AppiumDriver#tap(int, WebElement, int)`
      - `AppiumDriver#tap(int, int, int, int)`
      - `AppiumDriver#swipe(int, int, int, int, int)`
      - `MobileElement#swipe(SwipeElementDirection, int)`
      - `MobileElement#swipe(SwipeElementDirection, int, int, int)`
      
    redesign of `TouchAction` and `MultiTouchAction`
      - constructors were redesigned. There is no strict binding of `AppiumDriver` and `TouchAction` /`MultiTouchAction`. They can pass any instance of a class that implements `PerformsTouchActions`.
      - deprecated methods of `AppiumDriver`/`MobileElement` were moved to `TouchAction`/`MultiTouchAction`.
      - `io.appium.java_client.android.AndroidTouchAction` and `io.appium.java_client.ios.IOSTouchAction` were added. They create the swiping gesture. Both classes implement the new `io.appium.java_client.CreatesSwipeAction` API.   
      
      _The work is not finished yet._
      
- **[MAJOR ENHANCEMENT]**: The new interface `io.appium.java_client.FindsByIosNSPredicate` was added. [#462](https://github.com/appium/java-client/pull/462). With [@rafael-chavez](https://github.com/rafael-chavez) 's authorship. 
- **[MAJOR ENHANCEMENT]**: The new interface `io.appium.java_client.MobileBy.ByIosNsPredicate` was added. [#462](https://github.com/appium/java-client/pull/462). With [@rafael-chavez](https://github.com/rafael-chavez) 's authorship. 
- **[MAJOR ENHANCEMENT]**: The new interface `io.appium.java_client.FindsByWindowsAutomation` was added. [#462](https://github.com/appium/java-client/pull/462). With [@jonstoneman](https://github.com/jonstoneman) 's authorship. 
- **[MAJOR ENHANCEMENT]**: The new interface `io.appium.java_client.MobileBy.ByWindowsAutomation` was added. [#462](https://github.com/appium/java-client/pull/462). With [@jonstoneman](https://github.com/jonstoneman) 's authorship.
- [ENHANCEMENT] Added the ability to set UiAutomator Congfigurator values. [#410](https://github.com/appium/java-client/pull/410). 
[#477](https://github.com/appium/java-client/pull/477).
- **[UPDATE]** to Selenium 3.0. [#489](https://github.com/appium/java-client/pull/489)
- [ENHANCEMENT]. Additional methods which perform device rotation were implemented. [#489](https://github.com/appium/java-client/pull/489). [#439](https://github.com/appium/java-client/pull/439). But it works only for iOS in XCUIT mode. The feature request: [#7131](https://github.com/appium/appium/issues/7131) 
- [ENHANCEMENT]. TouchID Implementation (iOS Sim Only). Details: [#509](https://github.com/appium/java-client/pull/509)
- [ENHANCEMENT]. The ability to use port, ip and log file as server arguments was provided. Feature request: [#521](https://github.com/appium/java-client/issues/521). Fixes: [#522](https://github.com/appium/java-client/issues/522), [#524](https://github.com/appium/java-client/issues/524).
- [ENHANCEMENT]. The new interface ```io.appium.java_client.android.HasDeviceDetails``` was added. It is implemented by ```io.appium.java_client.android.AndroidDriver``` by default. [#518](https://github.com/appium/java-client/pull/518)
- [ENHANCEMENT].New touch actions were added. ```io.appium.java_client.ios.IOSTouchAction#doubleTap(WebElement, int, int)``` and ```io.appium.java_client.ios.IOSTouchAction#doubleTap(WebElement)```. [#523](https://github.com/appium/java-client/pull/523), [#444](https://github.com/appium/java-client/pull/444)
- [BUG FIX]: There was the issue when "@WithTimeout" was changing general timeout of the waiting for elements. Bug report: [#467](https://github.com/appium/java-client/issues/467). Fixes: [#468](https://github.com/appium/java-client/issues/468), [#469](https://github.com/appium/java-client/issues/469), [#480](https://github.com/appium/java-client/issues/480). Read: [supported-settings](https://github.com/appium/appium/blob/master/docs/en/advanced-concepts/settings.md#supported-settings)
- Added the server flag `io.appium.java_client.service.local.flags.AndroidServerFlag#REBOOT`. [#476](https://github.com/appium/java-client/pull/476)
- Added `io.appium.java_client.remote.AndroidMobileCapabilityType.APP_WAIT_DURATION ` capability. [#461](https://github.com/appium/java-client/pull/461)
- the new automation type `io.appium.java_client.remote.MobilePlatform#ANDROID_UIAUTOMATOR2` was add.

*4.1.2*

- Following capabilities were added: 
  - `io.appium.java_client.remote.AndroidMobileCapabilityType.ANDROID_INSTALL_TIMEOUT`
  - `io.appium.java_client.remote.AndroidMobileCapabilityType.NATIVE_WEB_SCREENSHOT`
  - `io.appium.java_client.remote.AndroidMobileCapabilityType.ANDROID_SCREENSHOT_PATH`. The pull request: [#452](https://github.com/appium/java-client/pull/452)
- `org.openqa.selenium.Alert` was reimplemented for iOS. Details: [#459](https://github.com/appium/java-client/pull/459) 
- The deprecated `io.appium.java_client.generic.searchcontext` was removed.
- The dependency on `com.google.code.gson` was updated to 2.7. Also it was adde to exclusions
for `org.seleniumhq.selenium` `selenium-java`. 
- The new AutomationName was added. IOS_XCUI_TEST. It is needed for the further development.
- The new MobilePlatform was added.  WINDOWS.  It is needed for the further development.

*4.1.1*

BUG FIX: Issue [#450](https://github.com/appium/java-client/issues/450). Fix: [#451](https://github.com/appium/java-client/issues/451). Thanks to [@tutunang](https://github.com/appium/java-client/pull/451) for the report.

*4.1.0*
- all code marked `@Deprecated` was removed.
- `getSessionDetails()` was added. Thanks to [@saikrishna321](https://github.com/saikrishna321) for the contribution.
- FIX [#362](https://github.com/appium/java-client/issues/362), [#220](https://github.com/appium/java-client/issues/220), [#323](https://github.com/appium/java-client/issues/323). Details read there: [#413](https://github.com/appium/java-client/pull/413)
- FIX [#392](https://github.com/appium/java-client/issues/392). Thanks to [@truebit](https://github.com/truebit) for the bug report.
- The dependency on `cglib` was replaced by the dependency on `cglib-nodep`. FIX [#418](https://github.com/appium/java-client/issues/418)
- The casting to the weaker interface `HasIdentity`  instead of class `RemoteWebElement` was added. It is the internal refactoring of the `TouchAction`. [#432](https://github.com/appium/java-client/pull/432). Thanks to [@asolntsev](https://github.com/asolntsev) for the contribution.
- The `setValue` method was moved to `MobileElement`. It works against text input elements on Android.
- The dependency on `org.springframework` `spring-context` v`4.3.2.RELEASE` was added
- The dependency on `org.aspectj` `aspectjweaver` v`1.8.9` was added
- ENHANCEMENT: The alternative event firing engine. The feature request: [#242](https://github.com/appium/java-client/issues/242). 
Implementation: [#437](https://github.com/appium/java-client/pull/437). Also [new WIKI chapter](https://github.com/appium/java-client/blob/master/docs/The-event_firing.md) was added.
- ENHANCEMENT: Convenient access to specific commands for each supported mobile OS. Details: [#445](https://github.com/appium/java-client/pull/445)  
- dependencies and plugins were updated
- ENHANCEMENT: `YouiEngineDriver` was added. Details: [appium server #6215](https://github.com/appium/appium/pull/6215), [#429](https://github.com/appium/java-client/pull/429), [#448](https://github.com/appium/java-client/pull/448). It is just the draft of the new solution that is going to be extended further. Please stay tuned. There are many interesting things are coming up. Thanks to `You I Engine` team for the contribution. 

*4.0.0*
- all code marked `@Deprecated` was removed. Java client won't support old servers (v<1.5.0)
anymore.
- the ability to start an activity using Android intent actions, intent categories, flags and arguments
was added to `AndroidDriver`. Thanks to [@saikrishna321](https://github.com/saikrishna321) for the contribution.
- `scrollTo()` and `scrollToExact()` became deprecated. They are going to be removed in the next release.
- The interface `io.appium.java_client.ios.GetsNamedTextField` and the declared method `T getNamedTextField(String name)` are 
deprecated as well. They are going to be removed in the next release.
- Methods `findElements(String by, String using)` and `findElement(String by, String using)` of `org.openga.selenium.remote.RemoteWebdriver` are public now. Thanks to [@SrinivasanTarget](https://github.com/SrinivasanTarget).
- the `io.appium.java_client.NetworkConnectionSetting` class was marked deprecated
- the enum `io.appium.java_client.android.Connection` was added. All supported network bitmasks are defined there.
- Android. Old methods which get/set connection were marked  `@Deprecated`
- Android. New methods which consume/return `io.appium.java_client.android.Connection` were added.
- the `commandRepository` field is public now. The modification of the `MobileCommand`
- Constructors like `AppiumDriver(HttpCommandExecutor executor, Capabilities capabilities)` were added to 
`io.appium.java_client.android.AndroidDriver` and `io.appium.java_client.ios.IOSDriver`
- The refactoring of `io.appium.java_client.internal.JsonToMobileElementConverter`. Now it accepts 
`org.openqa.selenium.remote.RemoteWebDriver` as the constructor parameter. It is possible to re-use 
`io.appium.java_client.android.internal.JsonToAndroidElementConverter` or 
`io.appium.java_client.ios.internal.JsonToIOSElementConverter` by RemoteWebDriver when it is needed.
- Constructors of the abstract `io.appium.java_client.AppiumDriver` were redesigned. Now they require 
a subclass of `io.appium.java_client.internal.JsonToMobileElementConverter`. Constructors of 
`io.appium.java_client.android.AndroidDriver` and `io.appium.java_client.ios.IOSDriver` are same still.
- The `pushFile(String remotePath, File file)` was added to AndroidDriver
- FIX of TouchAction. Instances of the TouchAction class are reusable now
- FIX of the swiping issue (iOS, server version >= 1.5.0). Now the swiping is implemented differently by 
AndroidDriver and IOSDriver. Thanks to [@truebit](https://github.com/truebit) and [@nuggit32](https://github.com/nuggit32) for the catching.
- the project was integrated with [maven-checkstyle-plugin](https://maven.apache.org/plugins/maven-checkstyle-plugin/). Thanks to [@SrinivasanTarget](https://github.com/SrinivasanTarget) for the work
- source code was improved according to code style checking rules. 
- the integration with `org.owasp dependency-check-maven` was added. Thanks to [@saikrishna321](https://github.com/saikrishna321) 
for the work.
- the integration with `org.jacoco jacoco-maven-plugin` was added. Thanks to [@SrinivasanTarget](https://github.com/SrinivasanTarget) for the contribution.

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

>  gradle clean -Dtest.single=IOSAlertTest test
