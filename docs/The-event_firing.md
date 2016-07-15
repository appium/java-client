since 4.1.0

# The purpose

This feature allows end user to organize the event logging on the client side. Also this feature may be useful in a binding with standard or custom reporting
frameworks. 
  
  
# The API

The API was designed the way which allows end user to select events (searching, navigation, exception throwing etc.) which should be listened to. It contains 
the following list of interfaces (new items may be added further): 

- `io.appium.java_client.events.api.Listener` is the basic interface
- `io.appium.java_client.events.api.general.AlertEventListener` is for the listening to alerts
- `io.appium.java_client.events.api.general.ElementEventListener` is for the listening to actions related to elements
- `io.appium.java_client.events.api.general.JavaScriptEventListener` is for the listening to java script executing
- `io.appium.java_client.events.api.general.ListensToException` is for the listening to exceptions which are thrown
- `io.appium.java_client.events.api.general.NavigationEventListener` is for the listening to events related to navigation
- `io.appium.java_client.events.api.general.SearchingEventListener` is for the listening to events related to the searching.
- `io.appium.java_client.events.api.general.WindowEventListener` is for the listening to actions on a window
- `io.appium.java_client.events.api.mobile.ContextEventListener` is for the listening to the switching to mobile context
- `io.appium.java_client.events.api.mobile.RotationEventListener` is for the listening to screen rotation
- `io.appium.java_client.events.api.general.AppiumWebDriverEventListener` was added to provide the compatibility with 
user's implementation of `org.openqa.selenium.support.events.WebDriverEventListener`. Also it extends some interfaces above.
 
# Briefly about the engine. 

This is pretty similar solution as the `org.openqa.selenium.support.events.EventFiringWebDriver` of the Selenium project. You 
can read about this thing there [The blog post](http://seleniumworks.blogspot.ru/2014/02/eventfiringwebdriver.html).  

Here we were trying to improve existing drawbacks and restrictions using: 

- API splitting, see above.

- the binding of some [Spring framework engines](https://projects.spring.io/spring-framework/) with [AspectJ](https://en.wikipedia.org/wiki/AspectJ).

# How to use

It is easy. 

```java
import io.appium.java_client.events.api.general.AlertEventListener;

public class AlertListener implements AlertEventListener {
...
}

...
import io.appium.java_client.events.api.general.ElementEventListener;

public class ElementListener implements ElementEventListener {
...
}

//and so on
...
import io.appium.java_client.events.EventFiringWebDriverFactory;
import io.appium.java_client.events.api.Listener;
...

AndroidDriver driver = new AndroidDriver(parameters);
driver = EventFiringWebDriverFactory.getEventFiringWebDriver(driver, new AlertListener(), 
    new ElementListener());
    
//or 
AndroidDriver driver2 = new AndroidDriver(parameters); 
List<Listener> listeners = new ArrayList<>();
listeners.add(new AlertListener());
listeners.add(new ElementListener());
driver = EventFiringWebDriverFactory.getEventFiringWebDriver(driver2, listeners);
```

## What if there are listeners which used everywhere by default.

In order to avoid the repeating actions an end user is free to do these things: 
 
- create folders `/META-INF/services` and put the file `io.appium.java_client.events.api.Listener` there. Please read about 
[SPI](https://docs.oracle.com/javase/tutorial/sound/SPI-intro.html).

![image](https://cloud.githubusercontent.com/assets/4927589/16731325/24eab680-4780-11e6-8551-a3c72d4b9c38.png)

- define the list of default listeners at the `io.appium.java_client.events.api.Listener`

![image](https://cloud.githubusercontent.com/assets/4927589/16731509/2734a4e0-4781-11e6-81cb-ab64a5924c35.png)

And then it is enough

```java

//and so on
...
import io.appium.java_client.events.EventFiringWebDriverFactory;
...

AndroidDriver driver = new AndroidDriver(parameters);
driver = EventFiringWebDriverFactory.getEventFiringWebDriver(driver);
```

If there are listeners defined externally when this collection is merged with default set of listeners.

# How to reuse customized WebDriverEventListener

If an end user has their own `org.openqa.selenium.support.events.WebDriverEventListener` implementation then in order to 
make it compatible with this engine it is enough to do the following.


```java
import org.openqa.selenium.support.events.WebDriverEventListener;
import io.appium.java_client.events.api.general.AppiumWebDriverEventListener;

public class UsersWebDriverEventListener implements WebDriverEventListener, AppiumWebDriverEventListener {
...
}
```

or just 

```java
import io.appium.java_client.events.api.general.AppiumWebDriverEventListener;

public class UsersWebDriverEventListener implements AppiumWebDriverEventListener {
...
}
```
