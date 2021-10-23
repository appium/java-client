This is the list of main changes between major versions 7 and 8 of Appium 
java client. This list should help you to successfully migrate your 
existing automated tests codebase. 

- Java client now supports Selenium 4, which also means it is 
strictly W3C compliant. Old JWP-based servers are not supported 
anymore, and it won't be possible to use the new client version
with them. Capabilities that enforce usage of JWP protocol don't have 
any effect anymore.
- All findBy* shortcut methods were removed. Consider using
findElement(s) instead.
- All methods that use TimeUnit class or where the time is passed as
a numeric type were replaced with their alternatives using
[java.time.Duration](https://docs.oracle.com/javase/8/docs/api/java/time/Duration.html) 
class.
- The current event firing mechanism that Appium java client uses 
has been deprecated in favour of the one that Selenium4 provides
natively. Read TBD for more details on how to use it.
- The recommended way to provide capabilities for driver creation is
to use specific option builders inherited from BaseOptions class. For
example XCUITestOptions to create a XCUITest driver instance and
UiAutomator2Options to create an UiAutomator2 driver instance.
