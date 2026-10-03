This is the list of main changes between major versions 10 and 11 of Appium
java client. This list should help you to successfully migrate your
existing automated tests codebase.

The main theme of this release is that Appium java client depends on `selenium-api` only. The rest of
Selenium (`selenium-remote-driver`, `selenium-http`, `selenium-json`, `selenium-os`, `selenium-support`)
is not a dependency anymore. The parts of it that the client needs are now maintained in this project,
so changes in the browser oriented parts of Selenium cannot break Appium sessions anymore.
See [Transitive dependencies management](transitive-dependencies-management.md) for details.


## The minimum supported Selenium version is set to 4.50.0

- Selenium versions below 4.50.0 won't work with Appium java client 11+.
Check the [Compatibility Matrix](../README.md#compatibility-matrix) for more details.
- If your code uses classes from the Selenium modules that are not dependencies anymore (`WebDriverWait`,
`ExpectedConditions`, `Select`, `EventFiringDecorator`, `ChromeOptions`, etc.), declare these modules
in your own build.

## `AppiumDriver` is not a `RemoteWebDriver` anymore

- `AppiumDriver` extends `io.appium.java_client.remote.AppiumRemoteWebDriver`, and the elements it returns
are `io.appium.java_client.remote.AppiumWebElement` instead of `org.openqa.selenium.remote.RemoteWebElement`.
Both still implement `WebDriver` and `WebElement` from `selenium-api`, so code that is typed against the
interfaces does not need any change. Casts to `RemoteWebDriver` and `RemoteWebElement`, and code that
requires them (for example the Selenium `Augmenter`), must be replaced.
- The following types moved to the `io.appium.java_client.remote` package: `Response`, `Command`,
`CommandPayload`, `SessionId`, `DriverCommand`, `ExecuteMethod`, `CommandExecutor`, `ErrorHandler`,
`ErrorCodes`, `ScreenshotException` and `UnreachableBrowserException`.
`CommandInfo` is replaced by `io.appium.java_client.AppiumCommandInfo`.
`CapabilityType` is replaced by `io.appium.java_client.remote.CapabilityType`, which only contains
`PLATFORM_NAME` and `BROWSER_NAME`.
- Features that are specific to browsers are not available anymore: virtual authenticators, federated
credential management, downloads, shadow DOM, DevTools, web storage commands and file upload.
- `AppiumCommandExecutor` does not extend `HttpCommandExecutor` anymore. Its constructors accept
`AppiumCommandInfo` maps, and `AppiumDriverLocalService` instead of `DriverService`.
The `setPrivateFieldValue` method and the public `client` field are removed, use `getClient()`.
- `AppiumW3CHttpCommandCodec` does not extend the Selenium W3C codec anymore.
- The deprecated `AppiumProtocolHandshake` and `AppiumNewSessionCommandPayload` classes are removed.
- `ErrorCodesMobile` extends `io.appium.java_client.remote.ErrorCodes`.

## The HTTP client is maintained in this project

- The `org.openqa.selenium.remote.http` package is replaced by `io.appium.java_client.http`. The
`HttpClient.Factory` parameter of the driver constructors is now `io.appium.java_client.http.HttpClient.Factory`.
Custom HTTP clients and filters must implement the new `HttpClient`, `Filter` and `HttpHandler` interfaces,
which have the same shape as the Selenium ones. The default implementation is based on `java.net.http`.
- `AppiumClientConfig` does not extend the Selenium `ClientConfig` anymore, and the driver constructors that
accepted the Selenium `ClientConfig` are removed, use `AppiumClientConfig` instead.
`AppiumClientConfig.fromClientConfig` accepts `io.appium.java_client.http.ClientConfig`.
- `AppiumClientConfig#withFilter` keeps the Appium user agent and idempotency filters, and the WebSocket timeout
is not reset to the default anymore when other settings are changed.
- `AppiumClientConfig.directConnect`, the default timeouts and the default HTTP version are not changed.

## The local server service does not extend `DriverService`

- `AppiumDriverLocalService` and `AppiumServiceBuilder` do not extend the Selenium `DriverService` and
`DriverService.Builder` anymore. `getExecutable`, `setExecutable`, `sendOutputTo`, `getDriverProperty`,
`getDriverEnvironmentVariable`, `score` and `withLogOutput` are removed.
- `AppiumDriverLocalService#close` stops the server.
- A server that fails to start now always throws `AppiumServerHasNotBeenStartedLocallyException`.

## Page objects and waits

- The page factory and `FluentWait` engine is now `io.appium.java_client.support`. Page objects that use the
Selenium `@FindBy`, `@FindBys`, `@FindAll` and `@CacheLookup` annotations keep working if `selenium-support`
is available, but it is recommended to switch to the annotations from `io.appium.java_client.support`.
See [Page objects](Page-objects.md).
- `PageFactory.initElements(WebDriver, Object)` and the `ExpectedCondition` extension from the
`io.appium.java_client.functions` package are removed.
- `AppiumFunction` does not extend the Guava `Function` anymore, and `MobileCommand.prepareArguments`
returns a `Map`, because Guava is not a dependency anymore.

## BiDi

- `AppiumDriver` does not implement `HasBiDi` anymore and the `getBiDi` and `maybeGetBiDi` methods are removed.
Selenium deprecated them for removal and changed the BiDi API in an incompatible way in the recent releases.
The BiDi session address is still available in the `webSocketUrl` capability of the created session.
