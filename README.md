# java-client

[![Maven Central Version](https://img.shields.io/maven-central/v/io.appium/java-client)](https://central.sonatype.com/artifact/io.appium/java-client)
[![Javadocs](https://www.javadoc.io/badge/io.appium/java-client.svg)](https://www.javadoc.io/doc/io.appium/java-client)
[![Appium Java Client CI](https://github.com/appium/java-client/actions/workflows/ci.yml/badge.svg)](https://github.com/appium/java-client/actions/workflows/ci.yml)

This is the Java language bindings for writing Appium Tests that conform to [WebDriver Protocol](https://w3c.github.io/webdriver/)

## v8 to v9 Migration

Since v9 the client only supports Java 11 and above.
Follow the [v8 to v9 Migration Guide](./docs/v8-to-v9-migration-guide.md) to streamline the migration process.

## v7 to v8 Migration

Since version 8 Appium Java Client had several major changes, which might require to
update your client code. Make sure to follow the [v7 to v8 Migration Guide](./docs/v7-to-v8-migration-guide.md)
to streamline the migration process.

## Add Appium java client to your test framework

### Stable

#### Maven

Add the following to pom.xml:

```xml
<dependency>
  <groupId>io.appium</groupId>
  <artifactId>java-client</artifactId>
  <version>${version.you.require}</version>
  <scope>test</scope>
</dependency>
```

#### Gradle

Add the following to build.gradle:

```groovy
dependencies {
  testImplementation 'io.appium:java-client:${version.you.require}'
}
```

### Beta/Snapshots

Java client project is available to use even before it is officially published to Maven Central. Refer [jitpack.io](https://jitpack.io/#appium/java-client)

#### Maven

Add the following to pom.xml:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Add the dependency:

```xml
<dependency>
    <groupId>com.github.appium</groupId>
    <artifactId>java-client</artifactId>
    <version>latest commit ID from master branch</version>
</dependency>
```

#### Gradle

Add the JitPack repository to your build file. Add it to your root build.gradle at the end of repositories:

```groovy
allprojects {
    repositories {
        // ...
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency:

```groovy
dependencies {
    implementation 'com.github.appium:java-client:latest commit id from master branch'
}
```

### Compatibility Matrix
 Appium Java Client                                                                                 | Selenium client
----------------------------------------------------------------------------------------------------|-----------------------------
`9.5.0`                                                                                             | `4.34.0`
`9.4.0`                                                                                             | `4.26.0`, `4.27.0`, `4.28.0`, `4.28.1`, `4.29.0`, `4.30.0`, `4.31.0`, `4.32.0`, `4.33.0`
 `9.2.1`(known issues: appium/java-client#2145, appium/java-client#2146), `9.2.2`, `9.2.3`, `9.3.0` | `4.19.0`, `4.19.1`, `4.20.0`, `4.21.0`, `4.22.0`, `4.23.0`, `4.23.1`, `4.24.0`, `4.25.0`, `4.26.0`, `4.27.0`
 `9.1.0`, `9.2.0`                                                                                   | `4.17.0`, `4.18.0`, `4.18.1`
 `9.0.0`                                                                                            | `4.14.1`, `4.15.0`, `4.16.0` (partially [corrupted](https://github.com/SeleniumHQ/selenium/issues/13256)), `4.16.1`
 N/A                                                                                                | `4.14.0`
 `8.5.0`, `8.5.1`, `8.6.0`                                                                          | `4.9.1`, `4.10.0`, `4.11.0`, `4.12.0`, `4.12.1` (known issue: appium/java-client#2004), `4.13.0`
 `8.4.0`                                                                                            | `4.8.2`, `4.8.3`, `4.9.0`
 `8.3.0`                                                                                            | `4.7.0`, `4.7.1`, `4.7.2`, `4.8.0`, `4.8.1`
 `8.2.1`                                                                                            | `4.5.0`, `4.5.1`, `4.5.2`, `4.5.3`, `4.6.0`

#### Why is it so complicated?

Selenium client does not follow [Semantic Versioning](https://semver.org/), so breaking changes might be introduced
even in patches, which requires the Appium team to update the Java client in response.

#### How to pin Selenium dependencies?

Appium Java Client declares Selenium dependencies using an open version range which is handled differently by different
build tools. Sometimes users may want to pin used Selenium dependencies for [various reasons](https://github.com/appium/java-client/issues/1823).
Follow the [Transitive Dependencies Management article](docs/transitive-dependencies-management.md) for more information
about establishing a fixed Selenium version for your Java test framework.

## Drivers Support

Appium java client has dedicated classes to support the following Appium drivers:

- [UiAutomator2](https://github.com/appium/appium-uiautomator2-driver) and [Espresso](https://github.com/appium/appium-espresso-driver): [AndroidDriver](src/main/java/io/appium/java_client/android/AndroidDriver.java)
- [XCUITest](https://github.com/appium/appium-xcuitest-driver): [IOSDriver](src/main/java/io/appium/java_client/ios/IOSDriver.java)
- [Windows](https://github.com/appium/appium-windows-driver): [WindowsDriver](src/main/java/io/appium/java_client/windows/WindowsDriver.java)
- [Safari](https://github.com/appium/appium-safari-driver): [SafariDriver](src/main/java/io/appium/java_client/safari/SafariDriver.java)
- [Gecko](https://github.com/appium/appium-geckodriver): [GeckoDriver](src/main/java/io/appium/java_client/gecko/GeckoDriver.java)
- [Mac2](https://github.com/appium/appium-mac2-driver): [Mac2Driver](src/main/java/io/appium/java_client/mac/Mac2Driver.java)

To automate other platforms that are not listed above you could use
[AppiumDriver](src/main/java/io/appium/java_client/AppiumDriver.java) or its custom derivatives.

Appium java client is built on top of Selenium and implements the same interfaces that the foundation
[RemoteWebDriver](https://github.com/SeleniumHQ/selenium/blob/trunk/java/src/org/openqa/selenium/remote/RemoteWebDriver.java)
does. However, Selenium lib is mostly focused on web browser automation while
Appium is universal and covers a wide range of possible platforms, e.g. mobile and desktop
operating systems, IOT devices, etc. Thus, the foundation `AppiumDriver` class in this package
extends `RemoteWebDriver` with additional features, and makes it more flexible, so it is not so
strictly focused on web-browser related operations.

## Appium Server Service Wrapper

Appium java client provides a dedicated class to control Appium server execution.
The class is [AppiumDriverLocalService](src/main/java/io/appium/java_client/service/local/AppiumDriverLocalService.java).
It allows to run and verify the Appium server **locally** from your test framework code
and provides several convenient shortcuts. The service could be used as below:

```java
AppiumDriverLocalService service = AppiumDriverLocalService.buildDefaultService();
service.start();
try {
    // do stuff with drivers
} finally {
    service.stop();
}
```

You could customize the service behavior, for example, provide custom
command line arguments or change paths to server executables
using [AppiumServiceBuilder](src/main/java/io/appium/java_client/service/local/AppiumServiceBuilder.java)

**Note**

> AppiumDriverLocalService does not support server management on non-local hosts

## Usage Examples

### UiAutomator2

```java
UiAutomator2Options options = new UiAutomator2Options()
    .setUdid("123456")
    .setApp("/home/myapp.apk");
AndroidDriver driver = new AndroidDriver(
    // The default URL in Appium 1 is http://127.0.0.1:4723/wd/hub
    new URI("http://127.0.0.1:4723").toURL(), options
);
try {
    WebElement el = driver.findElement(AppiumBy.xpath("//Button"));
    el.click();
    driver.getPageSource();
} finally {
    driver.quit();
}
```

### XCUITest

```java
XCUITestOptions options = new XCUITestOptions()
    .setUdid("123456")
    .setApp("/home/myapp.ipa");
IOSDriver driver = new IOSDriver(
    // The default URL in Appium 1 is http://127.0.0.1:4723/wd/hub
    new URI("http://127.0.0.1:4723").toURL(), options
);
try {
    WebElement el = driver.findElement(AppiumBy.accessibilityId("myId"));
    el.click();
    driver.getPageSource();
} finally {
    driver.quit();
}
```

### Any generic driver that does not have a dedicated class

```java
BaseOptions options = new BaseOptions()
    .setPlatformName("myplatform")
    .setAutomationName("mydriver")
    .amend("mycapability1", "capvalue1")
    .amend("mycapability2", "capvalue2");
AppiumDriver driver = new AppiumDriver(
    // The default URL in Appium 1 is http://127.0.0.1:4723/wd/hub
    new URI("http://127.0.0.1:4723").toURL(), options
);
try {
    WebElement el = driver.findElement(AppiumBy.className("myClass"));
    el.click();
    driver.getPageSource();
} finally {
    driver.quit();
}
```

Check the corresponding driver's READMEs to know the list of capabilities and features it supports.

You can find many more code examples by checking client's
[unit and integration tests](src/test/java/io/appium/java_client).

## Troubleshooting

### InaccessibleObjectException is thrown in runtime if Java 16+ is used

Appium Java client uses reflective access to private members of other modules
to ensure proper functionality of several features, like the Page Object model.
If you get a runtime exception and `InaccessibleObjectException` is present in
the stack trace and your Java runtime is at version 16 or higher, then consider the following
[Oracle's tutorial](https://docs.oracle.com/en/java/javase/16/migrate/migrating-jdk-8-later-jdk-releases.html#GUID-7BB28E4D-99B3-4078-BDC4-FC24180CE82B)
and/or checking [existing issues](https://github.com/appium/java-client/search?q=InaccessibleObjectException&type=issues)
for possible solutions. The idea there would be to explicitly allow
access for particular modules using `--add-exports/--add-opens` command line arguments.

Another possible, but weakly advised solution, would be to downgrade Java to
version 15 or lower.

### Issues related to environment variables' presence or to their values

Such issues are usually the case when the Appium server is started directly from your
framework code rather than run separately by a script or manually. Depending
on the way the server process is started it may or may not inherit the currently
active shell environment. That is why you may still receive errors about the variables'
presence even though these variables are defined for your command line interpreter.
Again, there is no universal solution to that, as there are many ways to spin up a new
server process. Consider checking the [Appium Environment Troubleshooting](docs/environment.md)
document for more information on how to debug and fix process environment issues.

## Changelog

Visit [CHANGELOG.md](CHANGELOG.md) to see the full list of changes between versions.

## Running tests

Run a test using

>  gradle clean -Dtest.single=IOSAlertTest test
