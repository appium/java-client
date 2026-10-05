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
driver. Both share the same session and the same HTTP client, so commands can be mixed freely. As long as the
result is referenced, the same instance is returned for the same Appium driver. Quitting the Selenium driver
quits the session.

```java
var driver = new AndroidDriver(serverUrl, options);
RemoteWebDriver selenium = SeleniumBridge.asRemoteWebDriver(driver);
WebDriver augmented = new Augmenter().augment(selenium);
```

Elements are not interchangeable between the two drivers: an element that one driver found cannot be passed to
the other, for example as a script argument. Find the element again with the driver that needs it.

The commands of Selenium that Appium does not serve, like downloads, are not available.

## BiDi

Create the session with the `webSocketUrl` capability (`options.enableBiDi()`), then pass the Selenium driver
to a BiDi module. Selenium opens the WebSocket connection when the bridge is created, with the timeouts, proxy,
credentials and SSL context of the `AppiumClientConfig` of the driver. If the connection cannot be made, the
BiDi modules fail with a `BiDiException`.

```java
var driver = new AndroidDriver(serverUrl, options.enableBiDi());
var selenium = SeleniumBridge.asRemoteWebDriver(driver);
try (var logInspector = new LogInspector(selenium)) {
    logInspector.onGenericLog(entry -> System.out.println(entry.getText()));
    driver.getPageSource();
} finally {
    selenium.closeBiDi();
}
```

The connection stays open until `closeBiDi()` or `quit()`, so release it when you are done with the BiDi modules.
`closeBiDi()` keeps the session open, but the modules that were created before cannot be used afterwards.
The next `asRemoteWebDriver` call opens a new connection.

To customize the HTTP client that opens the WebSocket connection, use the `asRemoteWebDriver` overload that takes
a Selenium `HttpClient.Factory`. It creates a new Selenium driver with its own connection on every call.
