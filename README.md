java-client
===========

Java language binding for writing Appium Tests, conforms to [Mobile JSON Wire Protocol](https://code.google.com/p/selenium/source/browse/spec-draft.md?repo=mobile)

Depends upon the Selenium Java client library, available [here](http://docs.seleniumhq.org/download/)

Hosted in Maven Central Repository:

```
<dependency>
  <groupId>io.appium</groupId>
  <artifactId>java-client</artifactId>
  <version>1.1.0</version>
</dependency>
```

Or, the compiled jar can be found in out/artifacts/java_client

Javadocs: http://appium.github.io/java-client/

###Added functions###
More can be found in the docs, but here's a quick list of features which this project has added to the usual selenium binding.


- resetApp()
- getAppString()
- sendKeyEvent()
- currentActivity()
- pullFile()
- pushFile()
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
- shake()
- complexFind()
- Context Switching: .context(), .getContextHandles(), getContext())

Locators:
- findElementByAccessibilityId()
- findElementsByAccessibilityId()
- findElementByIosUIAutomation()
- findElementsByIosUIAutomation()
- findElementByAndroidUIAutomator()
- findElementsByAndroidUIAutomator()

##Changelog##
*1.1.0*
- AppiumDriver now implements Rotatable. rotate() and getOrientation() methods added
- when no appium server is running, the proper error is thrown, instead of a NullPointerException
*1.0.2*
- recompiled to include some missing methods such as shake() and complexFind()
