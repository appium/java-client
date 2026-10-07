This is the list of main changes between major versions 10 and 11 of Appium
java client. This list should help you to successfully migrate your
existing automated tests codebase.

The main theme of this release is that Appium java client depends on `selenium-api` only. The rest of
Selenium (`selenium-remote-driver`, `selenium-http`, `selenium-json`, `selenium-os`, `selenium-support`)
is not a dependency anymore. The parts of it that the client needs are now maintained in this project,
so changes in the browser oriented parts of Selenium cannot break Appium sessions anymore.
See [Transitive dependencies management](transitive-dependencies-management.md) for details.

The minimum supported Appium server version is now Appium 3, and the deprecated APIs that were kept for the older
servers are removed.

For Appium 3 and newer servers nothing changes on the wire: the requests sent to the server, the capabilities and
the options classes, the driver classes and their methods, and the exceptions thrown for server errors are the
same as in v10. What you need to adapt are the imports, the few places where your code relied on Selenium
classes that the client does not extend or expose anymore, and the usages of the removed deprecated APIs.


## Migration steps

1. Update the client to `11.x` and Selenium to `4.50.0` or newer. Selenium versions below 4.50.0 won't work with
Appium java client 11+. Check the [Compatibility Matrix](../README.md#compatibility-matrix) for more details.
Use Appium server 3 or newer and the drivers and plugins that are compatible with it, see
[the Appium server](#the-appium-server).
2. Declare the Selenium modules your own code uses. They are not brought by the client anymore:
`selenium-support` (`WebDriverWait`, `ExpectedConditions`, `Select`, `EventFiringDecorator`, ...),
`selenium-remote-driver` (`RemoteWebDriver`, `Augmenter`, ...), browser drivers (`ChromeOptions`, ...).
3. Run the [OpenRewrite recipe](#automated-migration), it fixes most of the imports.
4. Fix what is left of the compilation errors using the [tables](#what-moved) below.
5. If you need `RemoteWebDriver` or Selenium BiDi modules, add the optional
`io.appium:java-client-selenium-bridge` artifact, see [Selenium interoperability](selenium-bridge.md).

## Automated migration

The [recipe file](openrewrite/migrate-to-v11.yml) is a declarative [OpenRewrite](https://docs.openrewrite.org)
recipe. Run it **before** changing the client version, while the v10 types are still on the classpath.
Save the file as `rewrite.yml` in the root of your project and run:

Maven

```bash
mvn -U org.openrewrite.maven:rewrite-maven-plugin:run \
  -Drewrite.activeRecipes=io.appium.java_client.MigrateToV11
```

Gradle

```gradle
plugins {
    id 'org.openrewrite.rewrite' version '7.39.0'
}

rewrite {
    activeRecipe('io.appium.java_client.MigrateToV11')
}
```

```bash
./gradlew rewriteRun
```

| Recipe                                                   | What it does                                                                                                                                       |
|----------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------|
| `io.appium.java_client.MigrateToV11`                     | Moves the types listed in the [first table](#what-moved) to their new packages.                                                                    |
| `io.appium.java_client.MigrateToV11PageObjectAnnotations` | Optional. Switches `@FindBy`, `@FindBys`, `@FindAll`, `@CacheLookup` and `How` to the Appium ones. Skip it if the same code also uses the Selenium `PageFactory`. |
| `io.appium.java_client.FindV11Breakages`                 | Marks the usages that have no automatic replacement (`RemoteWebDriver`, `HasBiDi`, `BiDi`, `DriverService`, touch actions, `AppiumFunction`, the mobile command helpers) with `/*~~>*/`. Search for `MobileCommand` yourself, as only some of its constants are removed.         |

Several recipes can be activated at once, separated with commas. The recipe does not touch your build files
and does not rewrite casts to `RemoteWebDriver`. Review the result before committing it. If the same codebase
also automates browsers, check the changes of `RemoteWebElement`, as the recipe replaces every usage of it.

## What moved

These types are covered by the recipe. The names stay the same, only the package changes.

| v10 (Selenium)                                                                                                                                                                                                             | v11                                                                      |
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------|
| `org.openqa.selenium.remote.{SessionId, Response, Command, CommandPayload, DriverCommand, ExecuteMethod, CommandExecutor, ErrorHandler, ErrorCodes, ScreenshotException, UnreachableBrowserException}`                    | `io.appium.java_client.remote.*`                                         |
| `org.openqa.selenium.remote.CapabilityType`                                                                                                                                                                                | `io.appium.java_client.remote.CapabilityType` (`PLATFORM_NAME` and `BROWSER_NAME` only) |
| `org.openqa.selenium.remote.RemoteWebElement`                                                                                                                                                                              | `io.appium.java_client.remote.AppiumWebElement`                          |
| `org.openqa.selenium.remote.HttpCommandExecutor`                                                                                                                                                                           | `io.appium.java_client.remote.AppiumCommandExecutor`                     |
| `org.openqa.selenium.remote.CommandInfo`                                                                                                                                                                                   | `io.appium.java_client.AppiumCommandInfo`                                |
| `org.openqa.selenium.remote.http.{ClientConfig, HttpClient, HttpRequest, HttpResponse, HttpMethod, HttpHandler, HttpHeader, HttpMessage, Filter, Contents, WebSocket, Message, TextMessage, BinaryMessage, CloseMessage, ConnectionFailedException}` | `io.appium.java_client.http.*`                                           |
| `org.openqa.selenium.support.{PageFactory}` and `org.openqa.selenium.support.pagefactory.{ElementLocator, ElementLocatorFactory, FieldDecorator, DefaultFieldDecorator, AbstractAnnotations, ByAll}`                        | `io.appium.java_client.support.*` and `io.appium.java_client.support.pagefactory.*` |
| `org.openqa.selenium.support.{FindBy, FindBys, FindAll, CacheLookup, How}` (optional, the Selenium ones keep working while `selenium-support` is available)                                                                | `io.appium.java_client.support.*`                                        |
| `io.appium.java_client.functions.ExpectedCondition`                                                                                                                                                                        | `org.openqa.selenium.support.ui.ExpectedCondition`                       |

## What needs a manual change

| v10                                                                                                      | v11                                                                                                                                                                               |
|----------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `AppiumDriver` is a `RemoteWebDriver`; casts to it, `Augmenter`, `RemoteWebDriver` parameters             | `AppiumDriver` extends `AppiumRemoteWebDriver`. Type against `WebDriver`, or use `SeleniumBridge.asRemoteWebDriver(driver)` from the [bridge](selenium-bridge.md).                |
| `HasBiDi`, `driver.getBiDi()`, `driver.maybeGetBiDi()`                                                   | Removed. Use the Selenium BiDi modules through the [bridge](selenium-bridge.md). The `webSocketUrl` capability of the session is still available.                                |
| `new ErrorHandler(codes, true)`                                                                          | `new ErrorHandler(codes)`                                                                                                                                                         |
| `new AppiumDriver(HttpCommandExecutor, caps)` and the same for the platform drivers                      | `new AppiumDriver(AppiumCommandExecutor, caps)`                                                                                                                                   |
| `new AndroidDriver(ClientConfig, caps)`, same for the other drivers                                      | `new AndroidDriver(AppiumClientConfig, caps)`, create it with `AppiumClientConfig.defaultConfig()` or `AppiumClientConfig.fromClientConfig(clientConfig)`                         |
| `ClientConfig.defaultConfig()`                                                                           | `ClientConfig` is an interface now, use `AppiumClientConfig.defaultConfig()`                                                                                                      |
| `driver.getClientConfig()`                                                                               | `((AppiumCommandExecutor) driver.getCommandExecutor()).getAppiumClientConfig()`                                                                                                   |
| `AppiumCommandExecutor#setPrivateFieldValue`, the public `client` field                                  | Removed, use `getClient()`                                                                                                                                                        |
| `AppiumDriverLocalService`/`AppiumServiceBuilder` as `DriverService`/`DriverService.Builder`             | They are standalone classes now. `getExecutable`, `setExecutable`, `sendOutputTo`, `getDriverProperty`, `getDriverEnvironmentVariable`, `score` and `withLogOutput` are removed. |
| `TouchAction`, `MultiTouchAction`, `PerformsTouchActions`, `AndroidTouchAction`, `IOSTouchAction`, `io.appium.java_client.touch.*`, `functions.ActionSupplier` | Removed. Use [W3C Actions](#touch-actions) or the `mobile:` gesture commands.                                                                                                     |
| `PageFactory.initElements(driver, page)` (the shortcut that takes a `WebDriver`) | Removed. Use `PageFactory.initElements(new AppiumFieldDecorator(driver), page)`. |
| `Wait`/`FluentWait`/`Sleeper` of Selenium used with `AppiumFluentWait`                                   | `AppiumFluentWait` extends `io.appium.java_client.support.ui.FluentWait`. Change the declared type to `AppiumFluentWait` (or `var`), or to `io.appium.java_client.support.ui.Wait`. |
| `AppiumFunction`, `MobileCommand.prepareArguments` | Removed. Use `java.util.function.Function` and a `Map`. `Function#compose` and `Function#andThen` pass `null` on, while the `AppiumFunction` ones skipped the next step, so the steps of a `FluentWait` chain must handle `null` themselves. |
| `AndroidMobileCommandHelper`, `IOSMobileCommandHelper`, the deprecated `MobileCommand` constants and `*Command` methods | Removed. Send the `mobile:` extension with `executeScript`, see [the removed commands](#the-removed-deprecated-commands). |
| `CanRememberExtensionPresence`, `AppiumDriver#assertExtensionExists`, `AppiumDriver#markExtensionAbsence` | Removed, the fallbacks that needed them are removed. |
| `AppiumProtocolHandshake`, `AppiumNewSessionCommandPayload`                                              | Removed                                                                                                                                                                           |
| `RemoteWebDriver` and `RemoteWebElement` features that Appium servers do not serve: downloads, file upload and `FileDetector`, web storage, DevTools, tracing, `fireSessionEvent`, `setLogLevel`, `RemoteWebDriver.builder()` | Not available. Shadow roots, virtual authenticators, federated credential management and `manage().logs()` keep working. |

## Details

### The Appium server

- The minimum supported Appium server version is Appium 3. Only the drivers and plugins that are compatible with
Appium 3 or newer are supported.
- A major release of the client guarantees support only for the Appium server API of the major server version that
is current at the time of the release and of the previous major version. Endpoints and commands that were removed or
deprecated in the server earlier may not be supported anymore. Check the
[Appium 2 to 3 migration guide](https://appium.io/docs/en/latest/guides/migrating-2-to-3/) for the endpoints that
Appium 3 removed.

### The removed deprecated commands

The driver interfaces (`InteractsWithApps`, `LocksDevice`, `PullsFiles`, `PushesFiles`, `HidesKeyboard`,
`HasClipboard`, `PressesKey`, `StartsActivity`, ...) used to call the legacy command when the server did not have the
`mobile:` extension. They call the `mobile:` extensions only now, so `driver.installApp(...)`, `driver.lockDevice()`
and the other API methods need no change, but `UnsupportedCommandException` and `InvalidArgumentException` of the
server are not swallowed anymore. If your code sends the legacy commands with `MobileCommand`, switch to the extensions.
The arguments of the extensions differ from the ones of the legacy commands, check the documentation of the drivers.

| Removed (`MobileCommand` constants and `*Command` helpers)                                                             | Use instead                                                                                        |
|------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------|
| `RESET`                                                                                                                | `mobile: clearApp`, then `mobile: activateApp`                                                     |
| `LAUNCH_APP`, `CLOSE_APP`                                                                                              | `mobile: activateApp` or `mobile: launchApp` (iOS), `mobile: terminateApp`                         |
| `RUN_APP_IN_BACKGROUND`, `GET_STRINGS`                                                                                 | `mobile: backgroundApp`, `mobile: getAppStrings`                                                   |
| `IS_APP_INSTALLED`, `INSTALL_APP`, `ACTIVATE_APP`, `QUERY_APP_STATE`, `TERMINATE_APP`, `REMOVE_APP`                    | `mobile: isAppInstalled`, `installApp`, `activateApp`, `queryAppState`, `terminateApp`, `removeApp` |
| `GET_CLIPBOARD`, `SET_CLIPBOARD`                                                                                       | `mobile: getClipboard`, `mobile: setClipboard`                                                     |
| `HIDE_KEYBOARD`, `IS_KEYBOARD_SHOWN`, `hideKeyboardCommand`, `isKeyboardShownCommand`                                  | `mobile: hideKeyboard`, `mobile: isKeyboardShown`                                                  |
| `LOCK`, `UNLOCK`, `IS_LOCKED`, `lockDeviceCommand`, `unlockDeviceCommand`, `getIsDeviceLockedCommand`                  | `mobile: lock`, `mobile: unlock`, `mobile: isLocked`                                               |
| `GET_DEVICE_TIME`                                                                                                      | `mobile: getDeviceTime`                                                                            |
| `pushFileCommand`                                                                                                      | `mobile: pushFile`. `PULL_FILE`, `PULL_FOLDER` and `PUSH_FILE` stay, they are public now.          |
| `SHAKE`, `TOUCH_ID`, `TOUCH_ID_ENROLLMENT` (iOS)                                                                       | `mobile: shake`, `mobile: sendBiometricMatch`, `mobile: enrollBiometric`                           |
| `PRESS_KEY_CODE`, `LONG_PRESS_KEY_CODE`, `pressKeyCodeCommand`, `longPressKeyCodeCommand` (Android)                    | `mobile: pressKey`                                                                                 |
| `CURRENT_ACTIVITY`, `GET_CURRENT_PACKAGE`, `START_ACTIVITY` (Android)                                                  | `mobile: getCurrentActivity`, `mobile: getCurrentPackage`, `mobile: startActivity`                 |
| `GET_DISPLAY_DENSITY`, `GET_SYSTEM_BARS`, `OPEN_NOTIFICATIONS` (Android)                                               | `mobile: getDisplayDensity`, `mobile: getSystemBars`, `mobile: openNotifications`                  |
| `GET_NETWORK_CONNECTION`, `SET_NETWORK_CONNECTION`, `TOGGLE_WIFI`, `TOGGLE_DATA`, `TOGGLE_AIRPLANE_MODE` (Android)     | `mobile: getConnectivity`, `mobile: setConnectivity`                                               |
| `TOGGLE_LOCATION_SERVICES`, `FINGER_PRINT` (Android)                                                                   | `mobile: toggleGps`, `mobile: fingerprint`                                                         |
| `SEND_SMS`, `GSM_CALL`, `GSM_SIGNAL`, `GSM_VOICE`, `NETWORK_SPEED`, `POWER_CAPACITY`, `POWER_AC_STATE` (Android emulator) | `mobile: sendSms`, `gsmCall`, `gsmSignal`, `gsmVoice`, `networkSpeed`, `powerCapacity`, `powerAc`  |
| `GET_PERFORMANCE_DATA`, `GET_SUPPORTED_PERFORMANCE_DATA_TYPES` (Android)                                               | `mobile: getPerformanceData`, `mobile: getPerformanceDataTypes`                                    |
| `SET_VALUE`, `REPLACE_VALUE` (Android)                                                                                 | W3C `sendKeys`, `mobile: replaceElementValue`                                                      |
| `END_TEST_COVERAGE`                                                                                                    | No replacement, `mobile: shell` can run the instrumentation command                                |

`setPowerAC` requested `mobile: powerAC`, which the driver does not register (it is `mobile: powerAc`), so it always used
the legacy command. It requests the right extension now.

`WindowsDriver#launchApp` and `WindowsDriver#closeApp` send `windows: launchApp` and `windows: closeApp`. The Windows driver
has no extensions to transfer files, so `WindowsDriver` keeps sending the driver commands for `pullFile`, `pullFolder` and
`pushFile`.

### `AppiumDriver` is not a `RemoteWebDriver` anymore

- `AppiumDriver` extends `io.appium.java_client.remote.AppiumRemoteWebDriver`, and the elements it returns
are `io.appium.java_client.remote.AppiumWebElement`. Both implement `WebDriver` and `WebElement` from
`selenium-api`, so code that is typed against the interfaces does not need any change.
- `AppiumCommandExecutor` does not extend `HttpCommandExecutor` anymore. Its constructors accept
`AppiumCommandInfo` maps, and `AppiumDriverLocalService` instead of `DriverService`.
- `AppiumW3CHttpCommandCodec` does not extend the Selenium W3C codec anymore, and `ErrorCodesMobile` extends
`io.appium.java_client.remote.ErrorCodes`.

### The HTTP client is maintained in this project

- Custom HTTP clients and filters must implement the new `HttpClient`, `Filter` and `HttpHandler` interfaces,
which have the same shape as the Selenium ones. The default implementation is based on `java.net.http`, as before.
The Selenium client implementations (Netty, OkHttp) do not plug in anymore.
- `AppiumClientConfig` does not extend the Selenium `ClientConfig` anymore. The instances are immutable, and the
methods that change a setting return a new instance, as before.
- `AppiumClientConfig#withFilter` keeps the Appium user agent and idempotency filters, and the WebSocket timeout
is not reset to the default anymore when other settings are changed.
- `AppiumClientConfig.directConnect`, the default timeouts and the default HTTP version are not changed.

### The local server service

- `AppiumDriverLocalService#close` stops the server.
- A server that fails to start now always throws `AppiumServerHasNotBeenStartedLocallyException`.

### Page objects and waits

- The page factory and `FluentWait` engine is now `io.appium.java_client.support`. Page objects that use the
Selenium `@FindBy`, `@FindBys`, `@FindAll` and `@CacheLookup` annotations keep working if `selenium-support`
is available, but it is recommended to switch to the annotations from `io.appium.java_client.support`.
See [Page objects](Page-objects.md).

### Touch actions

The deprecated touch actions API is removed. A tap with W3C Actions:

```java
var finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
var tap = new Sequence(finger, 0)
        .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y))
        .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
        .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
driver.perform(List.of(tap));
```

The gestures can also be performed with the commands of the Appium drivers, for example
`driver.executeScript("mobile: clickGesture", Map.of("x", x, "y", y))` for UiAutomator2 and
`driver.executeScript("mobile: tap", Map.of("x", x, "y", y))` for XCUITest. See the driver documentation
for the full list of the gesture commands.

### BiDi

- `AppiumDriver` does not implement `HasBiDi` anymore and the `getBiDi` and `maybeGetBiDi` methods are removed.
Selenium deprecated them for removal and changed the BiDi API in an incompatible way in the recent releases.
- To use the Selenium BiDi modules (for example `LogInspector`), add the `io.appium:java-client-selenium-bridge`
artifact and wrap the driver with `SeleniumBridge.asRemoteWebDriver(driver)`. See
[Selenium interoperability](selenium-bridge.md).

### Dependencies

- The runtime classpath of the client is `selenium-api`, Gson, ByteBuddy, SLF4J API and JSpecify. Guava,
OpenTelemetry, `selenium-manager`, `selenium-remote-driver`, `selenium-http`, `selenium-json` and `selenium-os`
are not brought transitively anymore. Declare the ones your code uses.
- Pinning Selenium: only `selenium-api` has to be pinned, see
[Transitive dependencies management](transitive-dependencies-management.md).
