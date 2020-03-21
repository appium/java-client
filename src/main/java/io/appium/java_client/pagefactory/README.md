# Standard Selectors

## AndroidFindBy / iOSXCUITFindBy / WindowsFindBy

# Advanced Selectors

## iOS's String Predicates

You can specify a [predicate](https://developer.apple.com/documentation/foundation/nspredicate)
in your Class Chain to limit the number of matched items. There's
[a detailed guide to that](https://appium.io/docs/en/writing-running-appium/ios/ios-predicate/index.html)
on the Appium Docs website with some Appium-specific considerations.

## iOS's Class Chain Queries

Our XCUiTest integration has full support for the 'Class Chain' concept. This
can do much of what XPath does...but faster. Note that many Class Chains leverage
String Predicates too.

### String Predicates in Class Chains

There's a special array-style syntax for defining predicates in a Class Chain:

```
// Start with [
// Followed by `
// Followed by some text
// Followed by `
// End with ]
[`label != 'a'`]
[`isWDVisible == 1`]
[`value < 0`]
```

#### Searching Descendents

If you replace the backticks (`) around a predicate with dollar signs ($), then
the Class Chain will match against the children, grandchildren, and other
descendents of each element.

```
// Find a cell element with the label 'here'
XCUIElementTypeCell[`label == 'here'`]
// Find a cell element which contains SOMETHING ELSE that has the label 'here'
XCUIElementTypeCell[$label == 'here'$]
```

#### Handling Quote Marks

Most of the time, you can treat pairs of single quotes or double quotes
interchangably. If you're searching with a string that contains quote marks,
though, you'll [want to be careful](https://stackoverflow.com/q/14116217).

```c
// Make sure to escape each quote mark that matches your delimiter
"text with \"some\" 'quote' marks"
// To NSPredicate, the line above and the line below are equivalent
'text with "some" \'quote\' marks'
```
```java
// When defining a iOSXCUITFindBy annotation, you'll be constrained by the
// Java string-quoting rules too.
@iOSXCUITFindBy(iOSClassChain = "**/SomeElement[`'text with \"some\" \\\'quote\\\' marks'`]")
```

### External References

Refer to [the official WebDriverAgent query docs](https://github.com/facebookarchive/WebDriverAgent/wiki/Class-Chain-Queries-Construction-Rules)
to learn more about the general concept.

### Sample usage:

```java
// Selector for image elements
@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeImage")

// Selector for the first image element on screen
@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeImage[1]")
// Selector for the second image element on screen
@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeImage[2]")
// Selector for the last image element on screen
@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeImage[-1]")
// Selector for the penultimate image element on screen
@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeImage[-2]")

// Selector for every cell with the name 'Foo' (single quote style)
@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell[`name == 'Foo'`]")
// Selector for every cell with the name "Foo" (double quote style)
@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell[`name == \"Foo\"`]")

// Selector for every cell with a name that starts with 'Foo' (single quote style)
@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell[`name BEGINSWITH 'Foo'`]")
// Selector for every cell with a name that starts with "Foo" (double quote style)
@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell[`name BEGINSWITH \"Foo\"`]")

// Selector for every cell with a name that starts with "it's not"
@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell[`name BEGINSWITH \"it's not\"`]")

// Selector that'll match every top-level element on screen
@iOSXCUITFindBy(iOSClassChain = "*")
// Selector that'll match every leaf element on screen (watch out: this can be SLOW)
@iOSXCUITFindBy(iOSClassChain = "**/*")

// You can place an index after a predicate: the following finds the last image element with name 'Foo'
@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell[`name == 'Foo'`][-1]")
```

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

```java
// Create a selector that looks for the text "Hello World":
@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Hello World\")")

// Create a selector that tries to find an ImageView:
@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.ImageView\")")

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
```

..and here are some that create UiScrollable objects:

```java
private static final String ourImageSelector = ".className(\"android.widget.ImageView\")";
private static final String ourListSelector = ".className(\"android.widget.ListView\")";

// Create a scrollable associated with a list (by itself, this doesn't do anything useful...)
@AndroidFindBy(uiAutomator = "new UiScrollable(" + ourListSelector + ")")

// Create a scrollable that scrolls forward along a list until it finds an ImageView:
@AndroidFindBy(uiAutomator = "new UiScrollable(" + ourListSelector + ").scrollIntoView(" + ourImageSelector + ")")

```
