# java-client

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.appium/java-client/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.appium/java-client)
[![Javadocs](https://www.javadoc.io/badge/io.appium/java-client.svg)](https://www.javadoc.io/doc/io.appium/java-client)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/f365c5e9458b42bf8a5b1d928d7e4f48)](https://www.codacy.com/app/appium/java-client)
[![Build Status](https://travis-ci.org/appium/java-client.svg?branch=master)](https://travis-ci.org/appium/java-client)

This is the Java language binding for writing Appium Tests, conforms to [Mobile JSON Wire Protocol](https://github.com/SeleniumHQ/mobile-spec/blob/master/spec-draft.md)

[API docs](https://www.javadoc.io/doc/io.appium/java-client)

### Features and other interesting information

[Tech stack](https://github.com/appium/java-client/blob/master/docs/Tech-stack.md)

[How to install the project](https://github.com/appium/java-client/blob/master/docs/Installing-the-project.md)

[WIKI](https://github.com/appium/java-client/wiki)

## How to install latest java client Beta/Snapshots

Java client project is available to use even before it is officially published to maven central. Refer [jitpack.io](https://jitpack.io/#appium/java-client)

### Maven

 - Add the following to pom.xml:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

 - Add the dependency:
 
```xml
<dependency>
    <groupId>com.github.appium</groupId>
    <artifactId>java-client</artifactId>
    <version>latest commit ID from master branch</version>
</dependency>
``` 

### Gradle

 - Add the JitPack repository to your build file. Add it in your root build.gradle at the end of repositories:
 
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

 - Add the dependency:
 
```
dependencies {
    implementation 'com.github.appium:java-client:latest commit id from master branch'
}
```

## Changelog
*8.0.0-beta*
- **[ENHANCEMENTS]**
  - Start adding UiAutomator2 options. [#1543](https://github.com/appium/java-client/pull/1543)
  - Add more UiAutomator2 options. [#1545](https://github.com/appium/java-client/pull/1545)
  - Finish creating options for UiAutomator2 driver. [#1548](https://github.com/appium/java-client/pull/1548)
  - Add WDA-related XCUITestOptions. [#1552](https://github.com/appium/java-client/pull/1552)
  - Add web view options for XCUITest driver. [#1557](https://github.com/appium/java-client/pull/1557)
  - Add the rest of XCUITest driver options. [#1561](https://github.com/appium/java-client/pull/1561)
  - Add Espresso options. [#1563](https://github.com/appium/java-client/pull/1563)
  - Add Windows driver options. [#1564](https://github.com/appium/java-client/pull/1564)
  - Add Mac2 driver options. [#1565](https://github.com/appium/java-client/pull/1565)
  - Add Gecko driver options. [#1573](https://github.com/appium/java-client/pull/1573)
  - Add Safari driver options. [#1576](https://github.com/appium/java-client/pull/1576)
  - Start adding XCUITest driver options. [#1551](https://github.com/appium/java-client/pull/1551)
  - Implement driver-specific W3C option classes. [#1540](https://github.com/appium/java-client/pull/1540)
  - Update Service to properly work with options. [#1550](https://github.com/appium/java-client/pull/1550)
- **[BREAKING CHANGE]**
  - Migrate to Selenium 4. [#1531](https://github.com/appium/java-client/pull/1531)
  - Make sure we only write W3C payload into create session command. [#1537](https://github.com/appium/java-client/pull/1537)
  - Use the new session payload creator inherited from Selenium. [#1535](https://github.com/appium/java-client/pull/1535)
  - unify locator factories naming and toString implementations. [#1538](https://github.com/appium/java-client/pull/1538)
  - drop support of deprecated Selendroid driver. [#1553](https://github.com/appium/java-client/pull/1553)
  - switch to javac compiler. [#1556](https://github.com/appium/java-client/pull/1556)
  - revise used Selenium dependencies. [#1560](https://github.com/appium/java-client/pull/1560)
  - change prefix to AppiumBy in locator toString implementation. [#1559](https://github.com/appium/java-client/pull/1559)
  - enable dependencies caching. [#1567](https://github.com/appium/java-client/pull/1567)
  - Include more tests into the pipeline. [#1566](https://github.com/appium/java-client/pull/1566)
  - Tune setting of default platform names. [#1570](https://github.com/appium/java-client/pull/1570)
  - Deprecate custom event listener implementation and default to the one provided by Selenium4. [#1541](https://github.com/appium/java-client/pull/1541)
  - Deprecate touch actions. [#1569](https://github.com/appium/java-client/pull/1569)
  - Deprecate legacy app management helpers. [#1571](https://github.com/appium/java-client/pull/1571)
  - deprecate Windows UIAutomation selector. [#1562](https://github.com/appium/java-client/pull/1562)
  - Remove unused entities. [#1572](https://github.com/appium/java-client/pull/1572)
  - Remove setElementValue helper. [#1577](https://github.com/appium/java-client/pull/1577)
  - Remove selenium package override. [#1555](https://github.com/appium/java-client/pull/1555)
  - remove redundant exclusion of Gradle task signMavenJavaPublication. [#1568](https://github.com/appium/java-client/pull/1568)
- **[BUG FIX]**
  - AndroidGeoLocation: update the constructor signature to mimic order of parameters in `org.openqa.selenium.html5.Location`. [#1526](https://github.com/appium/java-client/pull/1526)
  - Prevent duplicate builds for PRs from base repo branches. [#1496](https://github.com/appium/java-client/pull/1496)
  - Enable Dependabot for GitHub actions. [#1500](https://github.com/appium/java-client/pull/1500)
  - bind mac2element in element map for mac platform. [#1474](https://github.com/appium/java-client/pull/1474)
- **[DEPENDENCY UPDATES]**
  - `org.owasp.dependencycheck` was updated to 6.4.1.
  - `com.google.code.gson:gson` was updated to 2.8.9.

*7.6.0*
- **[ENHANCEMENTS]**
  - Add custom commands dynamically [Appium 2.0]. [#1506](https://github.com/appium/java-client/pull/1506)
  - New General Server flags are added [Appium 2.0]. [#1511](https://github.com/appium/java-client/pull/1511)
  - Add support of extended Android geolocation. [#1492](https://github.com/appium/java-client/pull/1492)
- **[BUG FIX]**
  - AndroidGeoLocation: update the constructor signature to mimic order of parameters in `org.openqa.selenium.html5.Location`. [#1526](https://github.com/appium/java-client/pull/1526)
  - Prevent duplicate builds for PRs from base repo branches. [#1496](https://github.com/appium/java-client/pull/1496)
  - Enable Dependabot for GitHub actions. [#1500](https://github.com/appium/java-client/pull/1500)
  - bind mac2element in element map for mac platform. [#1474](https://github.com/appium/java-client/pull/1474)
- **[DEPENDENCY UPDATES]**
  - `org.owasp.dependencycheck` was updated to 6.3.2.
  - `org.projectlombok:lombok` was updated to 1.18.22.
  - `com.github.johnrengelman.shadow` was updated to 7.1.0.
  - `actions/setup-java` was updated to 2.3.1.
  - `io.github.bonigarcia:webdrivermanager` was updated to 5.0.3.
  - `org.springframework:spring-context` was updated to 5.3.10.
  - `org.slf4j:slf4j-api` was updated to 1.7.32.
  - `com.google.code.gson:gson` was updated to 2.8.8.
  - `gradle` was updated to 7.1.1.
  - `commons-io:commons-io` was updated to 2.11.0.
  - `org.aspectj:aspectjweaver` was updated to 1.9.7.
  - `org.eclipse.jdt:ecj` was updated to 3.26.0.
  - `'junit:junit` was updated to 4.13.2.
  
*7.5.1*
- **[ENHANCEMENTS]**
    - Add iOS related annotations to tvOS. [#1456](https://github.com/appium/java-client/pull/1456)
- **[BUG FIX]**
    - Bring back automatic quote escaping for desired capabilities command line arguments on windows. [#1454](https://github.com/appium/java-client/pull/1454)
- **[DEPENDENCY UPDATES]**
    - `org.owasp.dependencycheck` was updated to 6.1.2.
    - `org.eclipse.jdt:ecj` was updated to 3.25.0.
    
*7.5.0*
- **[ENHANCEMENTS]**
    - Add support for Appium Mac2Driver. [#1439](https://github.com/appium/java-client/pull/1439)
    - Add support for multiple image occurrences. [#1445](https://github.com/appium/java-client/pull/1445)
    - `BOUND_ELEMENTS_BY_INDEX` Setting was added. [#1418](https://github.com/appium/java-client/pull/1418)
- **[BUG FIX]**
    - Use lower case for Windows platform key in ElementMap. [#1421](https://github.com/appium/java-client/pull/1421)
- **[DEPENDENCY UPDATES]**
    - `org.apache.commons:commons-lang3` was updated to 3.12.0.
    - `org.springframework:spring-context` was updated to 5.3.4.
    - `org.owasp.dependencycheck` was updated to 6.1.0.
    - `io.github.bonigarcia:webdrivermanager` was updated to 4.3.1.
    - `org.eclipse.jdt:ecj` was updated to 3.24.0.
    - `org.projectlombok:lombok` was updated to 1.18.16.
    - `jcenter` repository was removed.

*7.4.1*
- **[BUG FIX]**
    - Fix the configuration of `selenium-java` dependency. [#1417](https://github.com/appium/java-client/pull/1417)
- **[DEPENDENCY UPDATES]**
    - `gradle` was updated to 6.7.1.

    
*7.4.0*
- **[ENHANCEMENTS]**
    - Add ability to set multiple settings. [#1409](https://github.com/appium/java-client/pull/1409)
    - Support to execute Chrome DevTools Protocol commands against Android Chrome browser session. [#1375](https://github.com/appium/java-client/pull/1375)
    - Add new upload options i.e withHeaders, withFormFields and withFileFieldName. [#1342](https://github.com/appium/java-client/pull/1342)
    - Add AndroidOptions and iOSOptions. [#1331](https://github.com/appium/java-client/pull/1331)
    - Add idempotency key to session creation requests. [#1327](https://github.com/appium/java-client/pull/1327)
    - Add support for Android capability types: `buildToolsVersion`, `enforceAppInstall`, `ensureWebviewsHavePages`, `webviewDevtoolsPort`, and `remoteAppsCacheLimit`. [#1326](https://github.com/appium/java-client/pull/1326)
    - Added OTHER_APPS and PRINT_PAGE_SOURCE_ON_FIND_FAILURE Mobile Capability Types. [#1323](https://github.com/appium/java-client/pull/1323)
    - Make settings available for all AppiumDriver instances. [#1318](https://github.com/appium/java-client/pull/1318)
    - Add wrappers for the Windows screen recorder. [#1313](https://github.com/appium/java-client/pull/1313)
    - Add GitHub Action validating Gradle wrapper. [#1296](https://github.com/appium/java-client/pull/1296)
    - Add support for Android viewmatcher. [#1293](https://github.com/appium/java-client/pull/1293)
    - Update web view detection algorithm for iOS tests. [#1294](https://github.com/appium/java-client/pull/1294)
    - Add allow-insecure and deny-insecure server flags. [#1282](https://github.com/appium/java-client/pull/1282) 
- **[BUG FIX]**
    - Fix jitpack build failures. [#1389](https://github.com/appium/java-client/pull/1389)
    - Fix parse platformName if it is passed as enum item. [#1369](https://github.com/appium/java-client/pull/1369)
    - Increase the timeout for graceful AppiumDriverLocalService termination. [#1354](https://github.com/appium/java-client/pull/1354)
    - Avoid casting to RemoteWebElement in ElementOptions. [#1345](https://github.com/appium/java-client/pull/1345)
    - Properly translate desiredCapabilities into a command line argument. [#1337](https://github.com/appium/java-client/pull/1337)
    - Change getDeviceTime to call the `mobile` implementation. [#1332](https://github.com/appium/java-client/pull/1332)
    - Remove appiumVersion from MobileCapabilityType. [#1325](https://github.com/appium/java-client/pull/1325)
    - Set appropriate fluent wait timeouts. [#1316](https://github.com/appium/java-client/pull/1316)
- **[DOCUMENTATION UPDATES]**
    - Update Appium Environment Troubleshooting. [#1358](https://github.com/appium/java-client/pull/1358)
    - Address warnings printed by docs linter. [#1355](https://github.com/appium/java-client/pull/1355)
    - Add java docs for various Mobile Options. [#1331](https://github.com/appium/java-client/pull/1331)
    - Add AndroidFindBy, iOSXCUITFindBy and WindowsFindBy docs. [#1311](https://github.com/appium/java-client/pull/1311)
    - Renamed maim.js to main.js. [#1277](https://github.com/appium/java-client/pull/1277)
    - Improve Readability of Issue Template. [#1260](https://github.com/appium/java-client/pull/1260)
 
*7.3.0*
- **[ENHANCEMENTS]** 
    - Add support for logging custom events on the Appium Server. [#1262](https://github.com/appium/java-client/pull/1262)
    - Update Appium executable detection implementation. [#1256](https://github.com/appium/java-client/pull/1256)
    - Avoid through NPE if any setting value is null. [#1241](https://github.com/appium/java-client/pull/1241)
    - Settings API was improved to accept string names. [#1240](https://github.com/appium/java-client/pull/1240)
    - Switch `runAppInBackground` iOS implementation in sync with other platforms. [#1229](https://github.com/appium/java-client/pull/1229)
    - JavaDocs for AndroidMobileCapabilityType was updated. [#1238](https://github.com/appium/java-client/pull/1238)
    - Github Actions were introduced instead of TravisCI. [#1219](https://github.com/appium/java-client/pull/1219)
- **[BUG FIX]**
    - Fix return type of `getSystemBars` API. [#1216](https://github.com/appium/java-client/pull/1216)
    - Avoid using `getSession` call for capabilities values retrieval [W3C Support]. [#1204](https://github.com/appium/java-client/pull/1204)
    - Fix pagefactory list element initialisation when parameterised by generic type. [#1237](https://github.com/appium/java-client/pull/1237)
    - Fix AndroidKey commands. [#1250](https://github.com/appium/java-client/pull/1250)

*7.2.0*
- **[DEPENDENCY UPDATES]**
    - `org.seleniumhq.selenium:selenium-java` was reverted to stable version 3.141.59. [#1209](https://github.com/appium/java-client/pull/1209)
    - `org.projectlombok:lombok:1.18.8` was introduced. [#1193](https://github.com/appium/java-client/pull/1193)
- **[ENHANCEMENTS]** 
    - `videoFilters` property was added to IOSStartScreenRecordingOptions. [#1180](https://github.com/appium/java-client/pull/1180)
- **[IMPROVEMENTS]**
    - `Selendroid` automationName was deprecated. [#1198](https://github.com/appium/java-client/pull/1198)
    - JavaDocs for AndroidMobileCapabilityType and IOSMobileCapabilityType were updated. [#1204](https://github.com/appium/java-client/pull/1204)
    - JitPack builds were fixed. [#1203](https://github.com/appium/java-client/pull/1203)
    
*7.1.0*
- **[ENHANCEMENTS]** 
    -  Added an ability to get all the session details. [#1167 ](https://github.com/appium/java-client/pull/1167)
    - `TRACK_SCROLL_EVENTS`, `ALLOW_INVISIBLE_ELEMENTS`, `ENABLE_NOTIFICATION_LISTENER`, 
       `NORMALIZE_TAG_NAMES` and `SHUTDOWN_ON_POWER_DISCONNECT` Android Settings were added.         
    - `KEYBOARD_AUTOCORRECTION`, `MJPEG_SCALING_FACTOR`, 
       `MJPEG_SERVER_SCREENSHOT_QUALITY`, `MJPEG_SERVER_FRAMERATE`, `SCREENSHOT_QUALITY` 
        and `KEYBOARD_PREDICTION` iOS Settings were added. 
    - `GET_MATCHED_IMAGE_RESULT`, `FIX_IMAGE_TEMPLATE_SCALE`, 
       `SHOULD_USE_COMPACT_RESPONSES`, `ELEMENT_RESPONSE_ATTRIBUTES`  and 
       `DEFAULT_IMAGE_TEMPLATE_SCALE` settings were added for both Android and iOS [#1166](https://github.com/appium/java-client/pull/1166), [#1156 ](https://github.com/appium/java-client/pull/1156) and [#1120](https://github.com/appium/java-client/pull/1120)
    - The new interface `io.appium.java_client.ExecutesDriverScript ` was added. [#1165](https://github.com/appium/java-client/pull/1165)
    - Added an ability to get status of appium server. [#1153 ](https://github.com/appium/java-client/pull/1153)
    - `tvOS` platform support was added. [#1142  ](https://github.com/appium/java-client/pull/1142)
    -  The new interface `io.appium.java_client. FindsByAndroidDataMatcher` was added. [#1106](https://github.com/appium/java-client/pull/1106)
    -  The selector strategy `io.appium.java_client.MobileBy.ByAndroidDataMatcher` was added. [#1106](https://github.com/appium/java-client/pull/1106)
    -   Selendroid for android and UIAutomation for iOS are removed. [#1077  ](https://github.com/appium/java-client/pull/1077)
    - **[BUG FIX]** Platform Name enforced on driver creation is avoided now. [#1164 ](https://github.com/appium/java-client/pull/1164)
    - **[BUG FIX]** Send both signalStrengh and signalStrength for `GSM_SIGNAL`. [#1115  ](https://github.com/appium/java-client/pull/1115)
    - **[BUG FIX]** Null pointer exceptions when calling getCapabilities is handled better. [#1094  ](https://github.com/appium/java-client/pull/1094)

- **[DEPENDENCY UPDATES]**
  - `org.seleniumhq.selenium:selenium-java` was updated to 4.0.0-alpha-1.
  - `org.aspectj:aspectjweaver` was updated to 1.9.4.
  - `org.apache.httpcomponents:httpclient` was updated to 4.5.9.
  - `cglib:cglib` was updated to 3.2.12.
  - `org.springframework:spring-context` was updated to 5.1.8.RELEASE.
  - `io.github.bonigarcia:webdrivermanager` was updated to 3.6.1.
  - `org.eclipse.jdt:ecj` was updated to 3.18.0.
  - `com.github.jengelman.gradle.plugins:shadow` was updated to 5.1.0.
  - `checkstyle` was updated to 8.22.
  - `gradle` was updated to 5.4.
  - `dependency-check-gradle` was updated to 5.1.0.
  - `org.slf4j:slf4j-api` was updated to 1.7.26.
  - `org.apache.commons:commons-lang3` was updated to 3.9.

*7.0.0*
- **[ENHANCEMENTS]** 
    - The new interface `io.appium.java_client.FindsByAndroidViewTag` was added. [#996](https://github.com/appium/java-client/pull/996)
    - The selector strategy `io.appium.java_client.MobileBy.ByAndroidViewTag` was added. [#996](https://github.com/appium/java-client/pull/996)
    - The new interface `io.appium.java_client.FindsByImage` was added. [#990](https://github.com/appium/java-client/pull/990)
    - The selector strategy `io.appium.java_client.MobileBy.ByImage` was added. [#990](https://github.com/appium/java-client/pull/990)
    - The new interface `io.appium.java_client.FindsByCustom` was added. [#1041](https://github.com/appium/java-client/pull/1041)
    - The selector strategy `io.appium.java_client.MobileBy.ByCustom` was added. [#1041](https://github.com/appium/java-client/pull/1041)
    - DatatypeConverter is replaced with Base64 for JDK 9 compatibility. [#999](https://github.com/appium/java-client/pull/999)
    - Expand touch options API to accept coordinates as Point. [#997](https://github.com/appium/java-client/pull/997)
    - W3C capabilities written into firstMatch entity instead of alwaysMatch. [#1010](https://github.com/appium/java-client/pull/1010)
    - `Selendroid` for android and `UIAutomation` for iOS is deprecated. [#1034](https://github.com/appium/java-client/pull/1034) and [#1074](https://github.com/appium/java-client/pull/1074)
    - `videoScale` and `fps` screen recording options are introduced for iOS. [#1067](https://github.com/appium/java-client/pull/1067)
    - `NORMALIZE_TAG_NAMES` setting was introduced for android. [#1073](https://github.com/appium/java-client/pull/1073)
    - `threshold` argument was added to OccurrenceMatchingOptions. [#1060](https://github.com/appium/java-client/pull/1060)
    - `org.openqa.selenium.internal.WrapsElement` replaced by `org.openqa.selenium.WrapsElement`. [#1053](https://github.com/appium/java-client/pull/1053)
    - SLF4J logging support added into Appium Driver local service. [#1014](https://github.com/appium/java-client/pull/1014)
    - `IMAGE_MATCH_THRESHOLD`, `FIX_IMAGE_FIND_SCREENSHOT_DIMENSIONS`, `FIX_IMAGE_TEMPLATE_SIZE`, `CHECK_IMAGE_ELEMENT_STALENESS`, `UPDATE_IMAGE_ELEMENT_POSITION` and `IMAGE_ELEMENT_TAP_STRATEGY` setting was introduced for image elements. [#1011](https://github.com/appium/java-client/pull/1011)
- **[BUG FIX]** Better handling of InvocationTargetException [#968](https://github.com/appium/java-client/pull/968)
- **[BUG FIX]** Map sending keys to active element for W3C compatibility. [#966](https://github.com/appium/java-client/pull/966)
- **[BUG FIX]** Error message on session creation is improved. [#994](https://github.com/appium/java-client/pull/994)
- **[DEPENDENCY UPDATES]**
  - `org.seleniumhq.selenium:selenium-java` was updated to 3.141.59.
  - `com.google.code.gson:gson` was updated to 2.8.5.
  - `org.apache.httpcomponents:httpclient` was updated to 4.5.6.
  - `cglib:cglib` was updated to 3.2.8.
  - `org.apache.commons:commons-lang3` was updated to 3.8.
  - `org.springframework:spring-context` was updated to 5.1.0.RELEASE.
  - `io.github.bonigarcia:webdrivermanager` was updated to 3.0.0.
  - `org.eclipse.jdt:ecj` was updated to 3.14.0.
  - `org.slf4j:slf4j-api` was updated to 1.7.25.
  - `jacoco` was updated to 0.8.2.
  - `checkstyle` was updated to 8.12.
  - `gradle` was updated to 4.10.1.
  - `org.openpnp:opencv` was removed.
  
*6.1.0*
- **[BUG FIX]** Initing web socket clients lazily. Report [#911](https://github.com/appium/java-client/issues/911). FIX: [#912](https://github.com/appium/java-client/pull/912).
- **[BUG FIX]** Fix session payload for W3C. [#913](https://github.com/appium/java-client/pull/913)
- **[ENHANCEMENT]** Added TouchAction constructor argument verification [#923](https://github.com/appium/java-client/pull/923)
- **[BUG FIX]** Set retry flag to true by default for OkHttpFactory. [#928](https://github.com/appium/java-client/pull/928)
- **[BUG FIX]** Fix class cast exception on getting battery info. [#935](https://github.com/appium/java-client/pull/935)
- **[ENHANCEMENT]** Added an optional format argument to getDeviceTime and update the documentation. [#939](https://github.com/appium/java-client/pull/939)
- **[ENHANCEMENT]** The switching web socket client implementation to okhttp library. [#941](https://github.com/appium/java-client/pull/941)
- **[BUG FIX]** Fix of the bug [#924](https://github.com/appium/java-client/issues/924). [#951](https://github.com/appium/java-client/pull/951)

*6.0.0*
- **[ENHANCEMENT]** Added an ability to set pressure value for iOS. [#879](https://github.com/appium/java-client/pull/879)
- **[ENHANCEMENT]** Added new server arguments `RELAXED_SECURITY` and `ENABLE_HEAP_DUMP`. [#880](https://github.com/appium/java-client/pull/880)
- **[BUG FIX]** Use default Selenium HTTP client factory [#877](https://github.com/appium/java-client/pull/877)
- **[ENHANCEMENT]** Supporting syslog broadcast with iOS [#871](https://github.com/appium/java-client/pull/871)
- **[ENHANCEMENT]** Added isKeyboardShown command for iOS [#887](https://github.com/appium/java-client/pull/887)
- **[ENHANCEMENT]** Added battery information accessors [#882](https://github.com/appium/java-client/pull/882)
- **[BREAKING CHANGE]** Removal of deprecated code. [#881](https://github.com/appium/java-client/pull/881)
- **[BUG FIX]** Added `NewAppiumSessionPayload`. Bug report: [#875](https://github.com/appium/java-client/issues/875). FIX: [#894](https://github.com/appium/java-client/pull/894)
- **[ENHANCEMENT]** Added ESPRESSO automation name [#908](https://github.com/appium/java-client/pull/908)
- **[ENHANCEMENT]** Added a method for output streams cleanup [#909](https://github.com/appium/java-client/pull/909)
- **[DEPENDENCY UPDATES]**
  - `com.google.code.gson:gson` was updated to 2.8.4
  - `org.springframework:spring-context` was updated to 5.0.5.RELEASE
  - `org.aspectj:aspectjweaver` was updated to 1.9.1
  - `org.glassfish.tyrus:tyrus-clien` was updated to 1.13.1
  - `org.glassfish.tyrus:tyrus-container-grizzly` was updated to 1.2.1
  - `org.seleniumhq.selenium:selenium-java` was updated to 3.12.0


*6.0.0-BETA5*
- **[ENHANCEMENT]** Added clipboard handlers. [#855](https://github.com/appium/java-client/pull/855) [#869](https://github.com/appium/java-client/pull/869)
- **[ENHANCEMENT]** Added wrappers for Android logcat broadcaster. [#858](https://github.com/appium/java-client/pull/858)
- **[ENHANCEMENT]** Add bugreport option to Android screen recorder. [#852](https://github.com/appium/java-client/pull/852)
- **[BUG FIX]** Avoid amending parameters for SET_ALERT_VALUE endpoint. [#867](https://github.com/appium/java-client/pull/867)
- **[BREAKING CHANGE]** Refactor network connection setting on Android. [#865](https://github.com/appium/java-client/pull/865)
- **[BUG FIX]** **[BREAKING CHANGE]** Refactor of the `io.appium.java_client.AppiumFluentWait`. It uses `java.time.Duration` for time settings instead of `org.openqa.selenium.support.ui.Duration` and `java.util.concurrent.TimeUnit` [#863](https://github.com/appium/java-client/pull/863)
- **[BREAKING CHANGE]** `io.appium.java_client.pagefactory.TimeOutDuration` became deprecated. It is going to be removed. Use `java.time.Duration` instead. FIX [#742](https://github.com/appium/java-client/issues/742)  [#863](https://github.com/appium/java-client/pull/863).
- **[BREAKING CHANGE]** `io.appium.java_client.pagefactory.WithTimeOut#unit` became deprecated. It is going to be removed. Use `io.appium.java_client.pagefactory.WithTimeOut#chronoUnit` instead. FIX [#742](https://github.com/appium/java-client/issues/742)  [#863](https://github.com/appium/java-client/pull/863).
- **[BREAKING CHANGE]** constructors of `io.appium.java_client.pagefactory.AppiumElementLocatorFactory`, `io.appium.java_client.pagefactory.AppiumFieldDecorator` and `io.appium.java_client.pagefactory.AppiumElementLocator` which use `io.appium.java_client.pagefactory.TimeOutDuration` as a parameter became deprecated. Use new constructors which use `java.time.Duration`.
- **[DEPENDENCY UPDATES]**
  - `org.seleniumhq.selenium:selenium-java` was updated to 3.11.0

*6.0.0-BETA4*
- **[ENHANCEMENT]** Added handler for isDispalyed in W3C mode. [#833](https://github.com/appium/java-client/pull/833)
- **[ENHANCEMENT]** Added handlers for sending SMS, making GSM Call, setting GSM signal, voice, power capacity and power AC. [#834](https://github.com/appium/java-client/pull/834)
- **[ENHANCEMENT]** Added handlers for toggling wifi, airplane mode and data in android. [#835](https://github.com/appium/java-client/pull/835)
- **[DEPENDENCY UPDATES]**
  - `org.apache.httpcomponents:httpclient` was updated to 4.5.5
  - `cglib:cglib` was updated to 3.2.6
  - `org.springframework:spring-context` was updated to 5.0.3.RELEASE

*6.0.0-BETA3*
- **[DEPENDENCY UPDATES]**
  - `org.seleniumhq.selenium:selenium-java` was updated to 3.9.1
- **[BREAKING CHANGE]** Removal of deprecated listener-methods from the AlertEventListener. [#797](https://github.com/appium/java-client/pull/797)
- **[BUG FIX]**. Fix the `pushFile` command. [#812](https://github.com/appium/java-client/pull/812) [#816](https://github.com/appium/java-client/pull/816)
- **[ENHANCEMENT]**. Implemented custom command codec. [#817](https://github.com/appium/java-client/pull/817), [#825](https://github.com/appium/java-client/pull/825)
- **[ENHANCEMENT]** Added handlers for lock/unlock in iOS. [#799](https://github.com/appium/java-client/pull/799)
- **[ENHANCEMENT]** AddEd endpoints for screen recording API for iOS and Android. [#814](https://github.com/appium/java-client/pull/814)
- **[MAJOR ENHANCEMENT]** W3C compliance was provided. [#829](https://github.com/appium/java-client/pull/829)
- **[ENHANCEMENT]** New capability `MobileCapabilityType.FORCE_MJSONWP` [#829](https://github.com/appium/java-client/pull/829)
- **[ENHANCEMENT]** Updated applications management endpoints. [#824](https://github.com/appium/java-client/pull/824)

*6.0.0-BETA2*
- **[ENHANCEMENT]** The `fingerPrint` ability was added. It is supported by Android for now. [#473](https://github.com/appium/java-client/pull/473) [#786](https://github.com/appium/java-client/pull/786)
- **[BUG FIX]**. Less strict verification of the `PointOption`. [#795](https://github.com/appium/java-client/pull/795)

*6.0.0-BETA1*
- **[ENHANCEMENT]** **[REFACTOR]** **[BREAKING CHANGE]** **[MAJOR CHANGE]** Improvements of the TouchActions API [#756](https://github.com/appium/java-client/pull/756), [#760](https://github.com/appium/java-client/pull/760):
    - `io.appium.java_client.touch.ActionOptions` and sublasses were added
    - old methods of the `TouchActions` were marked `@Deprecated`
    - new methods which take new options.    
- **[ENHANCEMENT]**. Appium drivr local service uses default process environment by default. [#753](https://github.com/appium/java-client/pull/753)
- **[BUG FIX]**. Removed 'set' prefix from waitForIdleTimeout setting. [#754](https://github.com/appium/java-client/pull/754)
- **[BUG FIX]**. The asking for session details was optimized. Issue report [764](https://github.com/appium/java-client/issues/764).
FIX [#769](https://github.com/appium/java-client/pull/769)
- **[BUG FIX]** **[REFACTOR]**. Inconcistent MissingParameterException was removed. Improvements of MultiTouchAction. Report: [#102](https://github.com/appium/java-client/issues/102). FIX [#772](https://github.com/appium/java-client/pull/772)
- **[DEPENDENCY UPDATES]**
  - `org.apache.commons:commons-lang3` was updated to 3.7
  - `commons-io:commons-io` was updated to 2.6
  - `org.springframework:spring-context` was updated to 5.0.2.RELEASE
  - `org.aspectj:aspectjweaver` was updated to 1.8.13
  - `org.seleniumhq.selenium:selenium-java` was updated to 3.7.1

*5.0.4*
- **[BUG FIX]**. Client was crashing when user was testing iOS with server 1.7.0. Report: [#732](https://github.com/appium/java-client/issues/732). Fix: [#733](https://github.com/appium/java-client/pull/733).
- **[REFACTOR]** **[BREAKING CHANGE]** Excessive invocation of the implicit waiting timeout was removed. This is the breaking change because API of `AppiumElementLocator` and `AppiumElementLocatorFactory` was changed. Request: [#735](https://github.com/appium/java-client/issues/735), FIXES: [#738](https://github.com/appium/java-client/pull/738), [#741](https://github.com/appium/java-client/pull/741)
- **[DEPENDENCY UPDATES]**
    - org.seleniumhq.selenium:selenium-java to 3.6.0
    - com.google.code.gson:gson to 2.8.2
    - org.springframework:spring-context to 5.0.0.RELEASE
    - org.aspectj:aspectjweaver to 1.8.11

*5.0.3*
- **[BUG FIX]** Selenuim version was reverted from boundaries to the single number. Issue report: [#718](https://github.com/appium/java-client/issues/718). FIX: [#722](https://github.com/appium/java-client/pull/722)
- **[ENHANCEMENT]** The `pushFile` was added to IOSDriver. Feature request: [#720](https://github.com/appium/java-client/issues/720). Implementation: [#721](https://github.com/appium/java-client/pull/721). This feature requires appium node server v>=1.7.0

*5.0.2* **[BUG FIX RELEASE]**
- **[BUG FIX]** Dependency conflict resolving. The report: [#714](https://github.com/appium/java-client/issues/714). The fix: [#717](https://github.com/appium/java-client/pull/717). This change may affect users who use htmlunit-driver and/or phantomjsdriver. At this case it is necessary to add it to dependency list and to exclude old selenium versions.

*5.0.1* **[BUG FIX RELEASE]**
- **[BUG FIX]** The fix of the element genering on iOS was fixed. Issue report: [#704](https://github.com/appium/java-client/issues/704). Fix: [#705](https://github.com/appium/java-client/pull/705)

*5.0.0*
- **[REFACTOR]** **[BREAKING CHANGE]** 5.0.0 finalization. Removal of obsolete code. [#660](https://github.com/appium/java-client/pull/660)
- **[ENHANCEMENT]** Enable nativeWebTap setting for iOS. [#658](https://github.com/appium/java-client/pull/658)
- **[ENHANCEMENT]** The `getCurrentPackage` was added. [#657](https://github.com/appium/java-client/pull/657)
- **[ENHANCEMENT]** The `toggleTouchIDEnrollment` was added. [#659](https://github.com/appium/java-client/pull/659)
- **[BUG FIX]** The clearing of existing actions/parameters after perform is invoked. [#663](https://github.com/appium/java-client/pull/663)
- **[BUG FIX]** [#669](https://github.com/appium/java-client/pull/669) missed parameters of the `OverrideWidget` were added:
    - `iOSXCUITAutomation`
    - `windowsAutomation`
- **[BUG FIX]** ByAll was re-implemented. [#680](https://github.com/appium/java-client/pull/680) 
- **[BUG FIX]** **[BREAKING CHANGE]** The issue of compliance with Selenium grid 3.x was fixed. This change is breaking because now java_client is compatible with appiun server v>=1.6.5. Issue report [#655](https://github.com/appium/java-client/issues/655). FIX [#682](https://github.com/appium/java-client/pull/682)
- **[BUG FIX]** issues related to latest Selenium changes were fixed. Issue report [#696](https://github.com/appium/java-client/issues/696). Fix: [#699](https://github.com/appium/java-client/pull/699).
- **[UPDATE]** Dependency update
    - `selenium-java` was updated to 3.5.x
    - `org.apache.commons-lang3` was updated to 3.6
    - `org.springframework.spring-context` was updated to 4.3.10.RELEASE
- **[ENHANCEMENT]** Update of the touch ID enroll method. The older `PerformsTouchID#toggleTouchIDEnrollment` was marked `Deprecated`.
It is recoomended to use `PerformsTouchID#toggleTouchIDEnrollment(boolean)` instead.  [#695](https://github.com/appium/java-client/pull/695)
    

*5.0.0-BETA9*
- **[ENHANCEMENT]** Page factory: Mixed locator strategies were implemented. Feature request:[#565](https://github.com/appium/java-client/issues/565) Implementation: [#646](https://github.com/appium/java-client/pull/646)
- **[DEPRECATED]** All the content of the `io.appium.java_client.youiengine` package was marked `Deprecated`. It is going to be removed. [#652](https://github.com/appium/java-client/pull/652)
- **[UPDATE]** Update of the `com.google.code.gson:gson` to v2.8.1.

*5.0.0-BETA8*
- **[ENHANCEMENT]** Page factory classes became which had package visibility are `public` now. [#630](https://github.com/appium/java-client/pull/630)
  - `io.appium.java_client.pagefactory.AppiumElementLocatorFactory`
  - `io.appium.java_client.pagefactory.DefaultElementByBuilder`
  - `io.appium.java_client.pagefactory.WidgetByBuilder`

- **[ENHANCEMENT]** New capabilities were added [#626](https://github.com/appium/java-client/pull/626):
  - `AndroidMobileCapabilityType#AUTO_GRANT_PERMISSIONS`
  - `AndroidMobileCapabilityType#ANDROID_NATURAL_ORIENTATION`
  - `IOSMobileCapabilityType#XCODE_ORG_ID`
  - `IOSMobileCapabilityType#XCODE_SIGNING_ID`
  - `IOSMobileCapabilityType#UPDATE_WDA_BUNDLEID`
  - `IOSMobileCapabilityType#RESET_ON_SESSION_START_ONLY`
  - `IOSMobileCapabilityType#COMMAND_TIMEOUTS`
  - `IOSMobileCapabilityType#WDA_STARTUP_RETRIES`
  - `IOSMobileCapabilityType#WDA_STARTUP_RETRY_INTERVAL`
  - `IOSMobileCapabilityType#CONNECT_HARDWARE_KEYBOARD`
  - `IOSMobileCapabilityType#MAX_TYPING_FREQUENCY`
  - `IOSMobileCapabilityType#SIMPLE_ISVISIBLE_CHECK`
  - `IOSMobileCapabilityType#USE_CARTHAGE_SSL`
  - `IOSMobileCapabilityType#SHOULD_USE_SINGLETON_TESTMANAGER`
  - `IOSMobileCapabilityType#START_IWDP`
  - `IOSMobileCapabilityType#ALLOW_TOUCHID_ENROLL`
  - `MobileCapabilityType#EVENT_TIMINGS`

- **[UPDATE]** Dependencies were updated:
  - `org.seleniumhq.selenium:selenium-java` was updated to 3.4.0
  - `cglib:cglib` was updated to 3.2.5
  - `org.apache.httpcomponents:httpclient` was updated to 4.5.3
  - `commons-validator:commons-validator` was updated to 1.6
  - `org.springframework:spring-context` was updated to 4.3.8.RELEASE


*5.0.0-BETA7*
- **[ENHANCEMENT]** The ability to customize the polling strategy of the waiting was provided. [#612](https://github.com/appium/java-client/pull/612) 
- **[ENHANCEMENT]** **[REFACTOR]** Methods which were representing time deltas instead of elementary types became `Deprecated`. Methods which use `java.time.Duration` are suugested to be used. [#611](https://github.com/appium/java-client/pull/611)
- **[ENHANCEMENT]** The ability to calculate screenshots overlap was included. [#595](https://github.com/appium/java-client/pull/595).


*5.0.0-BETA6*
- **[UPDATE]** Update to Selenium 3.3.1
- **[ENHANCEMENT]** iOS XCUIT mode automation: API to run application in background was added. [#593](https://github.com/appium/java-client/pull/593)
- **[BUG FIX]** Issue report: [#594](https://github.com/appium/java-client/issues/594). FIX: [#597](https://github.com/appium/java-client/pull/597)
- **[ENHANCEMENT]** The class chain locator was added. [#599](https://github.com/appium/java-client/pull/599)


*5.0.0-BETA5*
- **[UPDATE]** Update to Selenium 3.2.0
- **[BUG FIX]** Excessive dependency on `guava` was removed. It causes errors. Issue report: [#588](https://github.com/appium/java-client/issues/588). FIX: [#589](https://github.com/appium/java-client/pull/589).
- **[ENHANCEMENT]**. The capability `io.appium.java_client.remote.AndroidMobileCapabilityType#SYSTEM_PORT` was added. [#591](https://github.com/appium/java-client/pull/591)

*5.0.0-BETA4*
- **[ENHANCEMENT]** Android. API to read the performance data was added. [#562](https://github.com/appium/java-client/pull/562)
- **[REFACTOR]** Android. Simplified the activity starting by reducing the number of parameters through POJO clas. Old methods which start activities were marked `@Deprecated`. [#579](https://github.com/appium/java-client/pull/579) [#585](https://github.com/appium/java-client/pull/585)
- **[BUG FIX]** Issue report:[#574](https://github.com/appium/java-client/issues/574). Fix:[#582](https://github.com/appium/java-client/pull/582)

*5.0.0-BETA3*
[BUG FIX]
- **[BUG FIX]**:Issue report: [#567](https://github.com/appium/java-client/issues/567). Fix: [#568](https://github.com/appium/java-client/pull/568)

*5.0.0-BETA2*
- **[BUG FIX]**:Issue report: [#549](https://github.com/appium/java-client/issues/549). Fix: [#551](https://github.com/appium/java-client/pull/551)
- New capabilities were added [#533](https://github.com/appium/java-client/pull/553):
  - `IOSMobileCapabilityType#USE_NEW_WDA`
  - `IOSMobileCapabilityType#WDA_LAUNCH_TIMEOUT`
  - `IOSMobileCapabilityType#WDA_CONNECTION_TIMEOUT`
  
The capability `IOSMobileCapabilityType#REAL_DEVICE_LOGGER` was removed. [#533](https://github.com/appium/java-client/pull/553)

- **[BUG FIX]/[ENHANCEMENT]**. Issue report: [#552](https://github.com/appium/java-client/issues/552). FIX [#556](https://github.com/appium/java-client/pull/556)
  - Additional methods were added to the `io.appium.java_client.HasSessionDetails`
    - `String getPlatformName()`
    - `String getAutomationName()`
    - `boolean isBrowser()`
  -  `io.appium.java_client.HasSessionDetails` is used by the ` io.appium.java_client.internal.JsonToMobileElementConverter ` to define which instance of the `org.openqa.selenium.WebElement` subclass should be created. 

- **[ENHANCEMENT]**: The additional event firing feature. PR: [#559](https://github.com/appium/java-client/pull/559). The [WIKI chapter about the event firing](https://github.com/appium/java-client/blob/master/docs/The-event_firing.md) was updated.

*5.0.0-BETA1*
- **[MAJOR ENHANCEMENT]**: Migration to Java 8. Epic: [#399](https://github.com/appium/java-client/issues/399)
  - API with default implementation. PR [#470](https://github.com/appium/java-client/pull/470)
  - Tools that provide _Page Object_ engines were redesigned. The migration to [repeatable annotations](http://docs.oracle.com/javase/tutorial/java/annotations/repeating.html). Details you can read there: [#497](https://github.com/appium/java-client/pull/497). [Documentation was synced as well](https://github.com/appium/java-client/blob/master/docs/Page-objects.md#also-it-is-possible-to-define-chained-or-any-possible-locators).
  - The new functional interface `io.appium.java_client.functions.AppiumFunctio`n was designed. It extends `java.util.function.Function` and `com.google.common.base.Function`. It was designed in order to provide compatibility with the `org.openqa.selenium.support.ui.Wait` [#543](https://github.com/appium/java-client/pull/543)
  - The new functional interface `io.appium.java_client.functions.ExpectedCondition` was designed. It extends `io.appium.java_client.functions.AppiumFunction` and ```org.openqa.selenium.support.ui.ExpectedCondition```.  [#543](https://github.com/appium/java-client/pull/543)
  - The new functional interface `io.appium.java_client.functions.ActionSupplier` was designed. It extends ```java.util.function.Supplier```. [#543](https://github.com/appium/java-client/pull/543)
  
- **[MAJOR ENHANCEMENT]**: Migration from Maven to Gradle. Feature request is [#214](https://github.com/appium/java-client/issues/214). Fixes: [#442](https://github.com/appium/java-client/pull/442), [#465](https://github.com/appium/java-client/pull/465).

- **[MAJOR ENHANCEMENT]** **[MAJOR REFACTORING]**. Non-abstract **AppiumDriver**: 
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
      
    The list of classes and methods which were marked _deprecated_ and they are going to be removed
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
      - `MobileElement#zoom()`
      - `MobileElement#pinch()`
      - `MobileElement#tap(int, int)`
      - `io.appium.java_client.SwipeElementDirection` and `io.appium.java_client.TouchebleElement` also were marked deprecated.
      
    redesign of `TouchAction` and `MultiTouchAction`
      - constructors were redesigned. There is no strict binding of `AppiumDriver` and `TouchAction` /`MultiTouchAction`. They can consume any instance of a class that implements `PerformsTouchActions`.  
      - `io.appium.java_client.ios.IOSTouchAction` was added. It extends `io.appium.java_client.TouchAction`.
      - the new interface `io.appium.java_client.PerformsActions` was added. It unifies `TouchAction` and `MultiTouchAction` now. [#543](https://github.com/appium/java-client/pull/543)
      
    `JsonToMobileElementConverter` re-design [#532](https://github.com/appium/java-client/pull/532):
       - unused `MobileElementToJsonConverter` was removed
       - `JsonToMobileElementConverter` is not rhe abstract class now. It generates instances of MobileElement subclasses according to current session parameters
       - `JsonToAndroidElementConverter` is deprecated now
       - `JsonToIOSElementConverter` is depreacated now
       - `JsonToYouiEngineElementConverter` is deprecated now.   
       - constructors of 'AppiumDriver' were re-designed.
       - constructors of 'AndroidDriver' were re-designed.
       - constructors of 'IOSDriver' were re-designed.
       
- **[MAJOR ENHANCEMENT]** Windows automation. Epic [#471](https://github.com/appium/java-client/issues/471)
  - The new interface `io.appium.java_client.FindsByWindowsAutomation` was added. [#462](https://github.com/appium/java-client/pull/462). With [@jonstoneman](https://github.com/jonstoneman) 's authorship. 
  - The new selector strategy `io.appium.java_client.MobileBy.ByWindowsAutomation` was added. [#462](https://github.com/appium/java-client/pull/462). With [@jonstoneman](https://github.com/jonstoneman) 's authorship.
  - `io.appium.java_client.windows.WindowsDriver` was designed. [#538](https://github.com/appium/java-client/pull/538)
  - `io.appium.java_client.windows.WindowsElement` was designed. [#538](https://github.com/appium/java-client/pull/538)
  - `io.appium.java_client.windows.WindowsKeyCode ` was added. [#538](https://github.com/appium/java-client/pull/538)
  - Page object tools were updated [#538](https://github.com/appium/java-client/pull/538)
      - the `io.appium.java_client.pagefactory.WindowsFindBy` annotation was added.
      - `io.appium.java_client.pagefactory.AppiumFieldDecorator` and supporting tools were actualized.
      
- **[MAJOR ENHANCEMENT]** iOS XCUIT mode automation:
  - `io.appium.java_client.remote.AutomationName#IOS_XCUI_TEST` was added
  - The new interface `io.appium.java_client.FindsByIosNSPredicate` was added. [#462](https://github.com/appium/java-client/pull/462). With [@rafael-chavez](https://github.com/rafael-chavez) 's authorship. It is implemented by `io.appium.java_client.ios.IOSDriver` and `io.appium.java_client.ios.IOSElement`.
  - The new selector strategy `io.appium.java_client.MobileBy.ByIosNsPredicate` was added. [#462](https://github.com/appium/java-client/pull/462). With [@rafael-chavez](https://github.com/rafael-chavez) 's authorship.
  - Page object tools were updated [#545](https://github.com/appium/java-client/pull/545), [#546](https://github.com/appium/java-client/pull/546)
      - the `io.appium.java_client.pagefactory.iOSXCUITFindBy` annotation was added.
      - `io.appium.java_client.pagefactory.AppiumFieldDecorator` and supporting tools were actualized.

- [ENHANCEMENT] Added the ability to set UiAutomator Congfigurator values. [#410](https://github.com/appium/java-client/pull/410). 
[#477](https://github.com/appium/java-client/pull/477).
- [ENHANCEMENT]. Additional methods which perform device rotation were implemented. [#489](https://github.com/appium/java-client/pull/489). [#439](https://github.com/appium/java-client/pull/439). But it works for iOS in XCUIT mode and for Android in UIAutomator2 mode only. The feature request: [#7131](https://github.com/appium/appium/issues/7131) 
- [ENHANCEMENT]. TouchID Implementation (iOS Sim Only). Details: [#509](https://github.com/appium/java-client/pull/509)
- [ENHANCEMENT]. The ability to use port, ip and log file as server arguments was provided. Feature request: [#521](https://github.com/appium/java-client/issues/521). Fixes: [#522](https://github.com/appium/java-client/issues/522), [#524](https://github.com/appium/java-client/issues/524).
- [ENHANCEMENT]. The new interface ```io.appium.java_client.android.HasDeviceDetails``` was added. It is implemented by ```io.appium.java_client.android.AndroidDriver``` by default. [#518](https://github.com/appium/java-client/pull/518)
- [ENHANCEMENT]. New touch actions were added. ```io.appium.java_client.ios.IOSTouchAction#doubleTap(WebElement, int, int)``` and ```io.appium.java_client.ios.IOSTouchAction#doubleTap(WebElement)```. [#523](https://github.com/appium/java-client/pull/523), [#444](https://github.com/appium/java-client/pull/444)
- [ENHANCEMENT]. All constructors declared by `io.appium.java_client.AppiumDriver` are public now.
- [BUG FIX]: There was the issue when "@WithTimeout" was changing general timeout of the waiting for elements. Bug report: [#467](https://github.com/appium/java-client/issues/467). Fixes: [#468](https://github.com/appium/java-client/issues/468), [#469](https://github.com/appium/java-client/issues/469), [#480](https://github.com/appium/java-client/issues/480). Read: [supported-settings](https://github.com/appium/appium/blob/master/docs/en/advanced-concepts/settings.md#supported-settings)
- Added the server flag `io.appium.java_client.service.local.flags.AndroidServerFlag#REBOOT`. [#476](https://github.com/appium/java-client/pull/476)
- Added `io.appium.java_client.remote.AndroidMobileCapabilityType.APP_WAIT_DURATION ` capability. [#461](https://github.com/appium/java-client/pull/461)
- the new automation type `io.appium.java_client.remote.MobilePlatform#ANDROID_UIAUTOMATOR2` was add.
- the new automation type `io.appium.java_client.remote.MobilePlatform#YOUI_ENGINE` was add.
- Additional capabilities were addede:
  - `IOSMobileCapabilityType#CUSTOM_SSL_CERT`
  - `IOSMobileCapabilityType#TAP_WITH_SHORT_PRESS_DURATION`
  - `IOSMobileCapabilityType#SCALE_FACTOR`
  - `IOSMobileCapabilityType#WDA_LOCAL_PORT`
  - `IOSMobileCapabilityType#SHOW_XCODE_LOG`
  - `IOSMobileCapabilityType#REAL_DEVICE_LOGGER`
  - `IOSMobileCapabilityType#IOS_INSTALL_PAUSE`
  - `IOSMobileCapabilityType#XCODE_CONFIG_FILE`
  - `IOSMobileCapabilityType#KEYCHAIN_PASSWORD`
  - `IOSMobileCapabilityType#USE_PREBUILT_WDA`
  - `IOSMobileCapabilityType#PREVENT_WDAATTACHMENTS`
  - `IOSMobileCapabilityType#WEB_DRIVER_AGENT_URL`
  - `IOSMobileCapabilityType#KEYCHAIN_PATH`
  - `MobileCapabilityType#CLEAR_SYSTEM_FILES`
- **[UPDATE]** to Selenium 3.0.1.
- **[UPDATE]** to Spring Framework 4.3.5.RELEASE.
- **[UPDATE]** to AspectJ weaver 1.8.10.
  


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
