# Selenium interoperability

Appium Java client depends on `selenium-api` only, and its drivers are not Selenium `RemoteWebDriver` instances.
The optional `java-client-selenium-bridge` artifact adapts an Appium driver for the Selenium code that needs one,
for example the Selenium `Augmenter`, or the BiDi modules like `LogInspector`. It depends on
`selenium-remote-driver`, so it is compiled against the Selenium releases from 4.50.0 on, like the core artifact.

```gradle
dependencies {
    implementation('io.appium:java-client:X.Y.Z')
    implementation('io.appium:java-client-selenium-bridge:X.Y.Z')
}
```

## RemoteWebDriver

`SeleniumBridge.asRemoteWebDriver` returns a Selenium `RemoteWebDriver` that works in the session of the Appium
driver. Both share the same session and the same HTTP client, so calls can be mixed freely.
Quitting the Selenium driver quits the session.

```java
var driver = new AndroidDriver(serverUrl, options);
RemoteWebDriver selenium = SeleniumBridge.asRemoteWebDriver(driver);
WebDriver augmented = new Augmenter().augment(selenium);
```

The commands of Selenium that Appium does not serve, like downloads, are not available.

## BiDi

Create the session with the `webSocketUrl` capability (`options.enableBiDi()`), then pass the Selenium driver
to a BiDi module. The WebSocket connection is opened on the first use, with the timeouts, proxy,
credentials and SSL context of the `AppiumClientConfig` of the driver.

```java
var driver = new AndroidDriver(serverUrl, options.enableBiDi());
var selenium = SeleniumBridge.asRemoteWebDriver(driver);
try (var logInspector = new LogInspector(selenium)) {
    logInspector.onGenericLog(entry -> System.out.println(entry.getText()));
    driver.getPageSource();
}
```

To customize the HTTP client that opens the WebSocket connection, use the `asRemoteWebDriver` overload that takes
a Selenium `HttpClient.Factory`.
