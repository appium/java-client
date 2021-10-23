since v8.0.0

# The purpose

This feature allows end user to organize the event logging on the client side. 
Also this feature may be useful in a binding with standard or custom reporting
frameworks. The feature has been introduced first since Selenium API v4.  

# The API

There are two main entities used to implement events firing logic: 
- [org.openqa.selenium.support.events.EventFiringDecorator](https://github.com/SeleniumHQ/selenium/blob/trunk/java/src/org/openqa/selenium/support/events/EventFiringDecorator.java) class
- [org.openqa.selenium.support.events.WebDriverListener](https://github.com/SeleniumHQ/selenium/blob/trunk/java/src/org/openqa/selenium/support/events/WebDriverListener.java) interface

## WebDriverListener

Classes that implement this interface are intended to be used with EventFiringDecorator.
This interface provides empty default implementation for all methods that do nothing.
You could easily extend that interface to add more methods that you'd like to listen to.
The strategy to add new/custom event listeners is the following. Let say there is a public setOrientation
method in the target WebDriver instance. Then you'd have to add `beforeSetOrientation` and/or
`afterSetOrientation` methods to your WebDriverListener descendant accepting single argument 
of `WebDriver` type. If the target method accepts one or more arguments then these arguments 
should also be added to the event listeners in the same order they are accepted by the original method, 
but the very first argument should still be the firing WebDriver instance.

_Important_: Make sure that your implementation of WebDriverListener class is public
and that event listener methods are also public.

## EventFiringDecorator

This decorator creates a wrapper around an arbitrary WebDriver instance that notifies
registered listeners about events happening in this WebDriver and derived objects, 
such as WebElements and Alert.
Listeners should implement WebDriverListener. It supports three types of events:
- "before"-event: a method is about to be called;
- "after"-event: a method was called successfully and returned some result;
- "error"-event: a method was called and thrown an exception.

To use this decorator you have to prepare a listener, create a decorator using this listener, 
decorate the original WebDriver instance with this decorator and use the new WebDriver instance
created by the decorator instead of the original one: 
```java
WebDriver original = new AndroidDriver(); 
// it is expected that MyListener class implements WebDriverListener
// interface or its descendant
WebDriverListener listener = new MyListener(); 
WebDriver decorated = new EventFiringDecorator(listener).decorate(original);
// the next call is going to fire:
// - beforeAnyCall
// - beforeAnyWebDriverCall
// - beforeGet
// - afterGet 
// - afterAnyWebDriverCall
// - afterAnyCall
// events in the listener instence (in this order)
decorated.get("http://example.com/"); 
// the next call is going to fire:
// - beforeAnyCall
// - beforeAnyWebDriverCall
// - beforeFindElement
// - afterFindElement
// - afterAnyWebDriverCall
// - afterAnyCall
// events in the listener instence (in this order)
WebElement header = decorated.findElement(By.tagName("h1")); 
// if an error happens during any of these calls the the onError event is fired
```
The instance of WebDriver created by the decorator implements all the same interfaces 
as the original driver. A listener can subscribe to "specific" or "generic" events (or both). 
A "specific" event correspond to a single specific method, a "generic" event correspond to any 
method called in a class or in any class. To subscribe to a "specific" event a listener should 
implement a method with a name derived from the target method to be watched. The listener methods 
for "before"-events receive the parameters passed to the decorated method. The listener 
methods for "after"-events receive the parameters passed to the decorated method as well as the 
result returned by this method.
