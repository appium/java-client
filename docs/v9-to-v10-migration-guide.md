This is the list of main changes between major versions 9 and 10 of Appium 
java client. This list should help you to successfully migrate your 
existing automated tests codebase.


## The minimum supported Selenium version is set to 4.35.0

- Selenium versions below 4.35.0 won't work with Appium java client 10+.
Check the [Compatibility Matrix](../README.md#compatibility-matrix) for more
details about versions compatibility.

## Removed previously deprecated items

- `org.openqa.selenium.remote.html5.RemoteLocationContext`, `org.openqa.selenium.html5.Location` and 
  `org.openqa.selenium.html5.LocationContext` imports have been removed since they don't exist
  in Selenium lib anymore. Use appropriate replacements from this library instead for APIs and 
  interfaces that were using deprecated classes, like `io.appium.java_client.Location`. 
