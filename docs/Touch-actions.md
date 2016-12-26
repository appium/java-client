Appium server side provides abilities to emulate touch actions. It is possible construct single, complex and multiple touch actions.
 
#How to use a single touch action

```java
import io.appium.java_client.TouchAction;

...
//tap
new TouchAction(driver)
                .tap(driver
                .findElementById("io.appium.android.apis:id/start")).perform();
```

#How to construct complex actions

```java
import io.appium.java_client.TouchAction;

...
//swipe
TouchAction swipe = new TouchAction(driver).press(images.get(2), -10, center.y - location.y)
                .waitAction(2000).moveTo(gallery, 10, center.y - location.y).release();
swipe.perform();
```

#How to construct multiple touch action. 

```java
import io.appium.java_client.TouchAction;
import io.appium.java_client.MultiTouchAction;

...
//tap by few fingers
 MultiTouchAction multiTouch = new MultiTouchAction(driver);

for (int i = 0; i < fingers; i++) {
    TouchAction tap = new TouchAction(driver);
    multiTouch.add(tap.press(element).waitAction(duration).release());
}

multiTouch.perform();
```

