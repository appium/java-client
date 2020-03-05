# Standard Selectors

## AndroidFindBy / iOSXCUITFindBy / WindowsFindBy

# Advanced Selectors

## iOS's Class Chain Queries

Our XCUiTest integration has full support for the 'Class Chain' concept. This
can do much of what XPath does...but faster.

### External References

Refer to [the official WebDriverAgent query docs](https://github.com/facebookarchive/WebDriverAgent/wiki/Class-Chain-Queries-Construction-Rules)
to learn more about the general concept.

### Sample usage:

    // Selector for image elements
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeImage")

    // Selector for every cell with the name 'Foo'
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell[`name == "Foo"`]")
    // Selector for every cell with a name that starts with 'Foo'
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell[`name BEGINSWITH "Foo"`]")

    // Selector that'll match every top-level element on screen
    @iOSXCUITFindBy(iOSClassChain = "*")
    // Selector that'll match every leaf element on screen (watch out: this can be SLOW)
    @iOSXCUITFindBy(iOSClassChain = "**/*")

## Android's uiAutomator String

Available when using [AndroidBy](AndroidBy) and [AndroidFindBy](AndroidFindBy) with
[appium-uiautomator2-server](https://github.com/appium/appium-uiautomator2-server). This
string will be used by the server to construct a UiSelector or UiScrollable object.

### External References

For an overview of what the backend is capable of, please check out the

* [Main UI Automator Guide](https://developer.android.com/training/testing/ui-automator)
* [UiScrollable API docs](https://developer.android.com/reference/androidx/test/uiautomator/UiScrollable)
and
* [UiSelector API docs](https://developer.android.com/reference/androidx/test/uiautomator/UiSelector)

### Sample Strings

Here are some ways you could configure a UiSelector in your project:

    // Create a selector that looks for the text "Hello World":
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Hello World\")")

    // Create a selector that matches resource ids against a regular expression:
    private static final String looksLikeAPage = "page_number_\d*";
    @AndroidFindBy(uiAutomator = "new UiSelector().resourceIdMatches(\"" + looksLikeAPage + "\")")

    // The agent also supports some abbreviated forms - all 3 of the below
    // strings are equivalent.
    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.EditText\")")
    @AndroidFindBy(uiAutomator = "UiSelector().className(\"android.widget.EditText\")")
    @AndroidFindBy(uiAutomator = ".className(\"android.widget.EditText\")")

    // You can connect up conditions to search for multiple things at once
    @AndroidFindBy(uiAutomator = ".resourceId(\"android:id/list\").classNameMatches(\"\.*RecyclerView\").index(3)")

..and here are some that create UiScrollable objects:

    // TODO: Provide samples
