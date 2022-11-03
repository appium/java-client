since v8.0.0

# The purpose

This feature allows end user to organize the event logging on the client side. 
Also, this feature may be useful in a binding with standard or custom reporting
frameworks. The feature has been introduced first since Selenium API v4.  

# The API

There are two main entities used to implement events firing logic: 
- [org.openqa.selenium.support.events.EventFiringDecorator](https://github.com/SeleniumHQ/selenium/blob/trunk/java/src/org/openqa/selenium/support/events/EventFiringDecorator.java) class
- [org.openqa.selenium.support.events.WebDriverListener](https://github.com/SeleniumHQ/selenium/blob/trunk/java/src/org/openqa/selenium/support/events/WebDriverListener.java) interface

## WebDriverListener

Classes that implement this interface are intended to be used with EventFiringDecorator.
This interface provides empty default implementation for all methods that do nothing.
You could easily extend that interface to add more methods that you'd like to listen to.
The strategy to add new/custom event listeners is the following. Let say there is a public `setOrientation`
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

## createProxy API (since Java Client 8.3.0)

This API is unique to Appium Java Client and does not exist in Selenium. The reason for 
its existence is the fact that the original event listeners API provided by Selenium is limited
because it can only use interface types for decorator objects. For example, the code below won't
work:

```java
IOSDriver driver = new IOSDriver(new URL("http://doesnot.matter/"), new ImmutableCapabilities())
{
    @Override
    protected void startSession(Capabilities capabilities)
    {
        // Override in a sake of simplicity to avoid the actual session start
    }
};
WebDriverListener webDriverListener = new WebDriverListener()
{
};
IOSDriver decoratedDriver = (IOSDriver) new EventFiringDecorator(IOSDriver.class, webDriverListener).decorate(
        driver);
```

The last line throws `ClassCastException` because `decoratedDriver` is of type `IOSDriver`, 
which is a class rather than an interface. 
See the issue [#1694](https://github.com/appium/java-client/issues/1694) for more
details. In order to workaround this limitation a special proxy implementation has been created,
which is capable of decorating class types:

```java
import io.appium.java_client.proxy.MethodCallListener;
import io.appium.java_client.proxy.NotImplementedException;

import static io.appium.java_client.proxy.Helpers.createProxy;

// ...

MethodCallListener listener = new MethodCallListener() {
    @Override
    public void beforeCall(Object target, Method method, Object[] args) {
        if (!method.getName().equals("get")) {
            throw new NotImplementedException();
        }
        acc.append("beforeCall ").append(method.getName()).append("\n");
    }

    @Override
    public void afterCall(Object target, Method method, Object[] args, Object result) {
        if (!method.getName().equals("get")) {
            throw new NotImplementedException();
        }
        acc.append("afterCall ").append(method.getName()).append("\n");
    }
};

IOSDriver decoratedDriver = createProxy(
        IOSDriver.class,
        new Object[] {new URL("http://localhost:4723/"), new XCUITestOptions()},
        new Class[] {URL.class, Capabilities.class},
        listener
);

decoratedDriver.get("http://example.com/");

assertThat(acc.toString().trim()).isEqualTo(
        String.join("\n",
                "beforeCall get",
                "afterCall get"
        )
);
```

This proxy is not tied to WebDriver descendants and could be used to any classes that have
**public** constructors. It also allows to intercept exceptions thrown by **public** class methods and/or
change/replace the original methods behavior. It is important to know that callbacks are **not** invoked 
for methods derived from the standard `Object` class, like `toString` or `equals`. 
Check [unit tests](../src/test/java/io/appium/java_client/proxy/ProxyHelpersTest.java) for more examples.
