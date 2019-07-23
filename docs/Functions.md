Appium java client has some features based on [Java 8 Functional interfaces](https://www.oreilly.com/learning/java-8-functional-interfaces).

# Conditions

```java
io.appium.java_client.functions.AppiumFunction
```
It extends 
```java 
java.util.function.Function
```
and 
```java
com.google.common.base.Function
```
to make end user available to use _org.openqa.seleniumone.support.ui.Wait_. There is additional interface 
```java
io.appium.java_client.functions.ExpectedCondition
```
which extends 
```java
io.appium.java_client.functions.AppiumFunction
``` 

and

```java
org.openqa.seleniumone.support.ui.ExpectedCondition
```

This feature provides the ability to create complex condition of the waiting for something.
  
```java
//waiting for elements
 private final AppiumFunction<WebDriver, List<WebElement>> searchingFunction = input -> {
    List<WebElement> result = input.findElements(By.tagName("a"));

    if (result.size() > 0) {
        return result;
    }
    return null;
};

//waiting for some context using regular expression pattern
private final AppiumFunction<Pattern, WebDriver> contextFunction = input -> {
    Set<String> contexts = driver.getContextHandles();
    String current = driver.getContext();
    contexts.forEach(context -> {
        Matcher m = input.matcher(context);
        if (m.find()) {
            driver.context(context);
        }
    });
    if (!current.equals(driver.getContext())) {
        return driver;
    }
    return null;
};
```

## using one function as pre-condition

```java
@Test public void tezt() {
    ....
    Wait<Pattern> wait = new FluentWait<>(Pattern.compile("WEBVIEW"))
               .withTimeout(30, TimeUnit.SECONDS);
    List<WebElement> elements = wait.until(searchingFunction.compose(contextFunction));    
    ....
}
```

## using one function as post-condition

```java
import org.openqa.seleniumone.support.ui.FluentWait;
import org.openqa.seleniumone.support.ui.Wait;

@Test public void tezt() {
    ....
    Wait<Pattern> wait = new FluentWait<>(Pattern.compile("WEBVIEW"))
               .withTimeout(30, TimeUnit.SECONDS);
    List<WebElement> elements = wait.until(contextFunction.andThen(searchingFunction));    
    ....
}
```

# Touch action supplier

[About touch actions](https://github.com/appium/java-client/blob/master/docs/Touch-actions.md)

You can use suppliers to declare touch/multitouch actions for some screens/tests. Also it is possible to 
create gesture libraries/utils using suppliers. Appium java client provides this interface

```java
io.appium.java_client.functions.ActionSupplier
``` 

## Samples

```java
private final ActionSupplier<TouchAction> horizontalSwipe = () -> {
    driver.findElementById("io.appium.android.apis:id/gallery");

    AndroidElement gallery = driver.findElementById("io.appium.android.apis:id/gallery");
    List<MobileElement> images = gallery
                .findElementsByClassName("android.widget.ImageView");
    Point location = gallery.getLocation();
    Point center = gallery.getCenter();

    return new TouchAction(driver).press(images.get(2), -10, center.y - location.y)
                .waitAction(2000).moveTo(gallery, 10, center.y - location.y).release();
};

private final ActionSupplier<TouchAction> verticalSwiping = () ->
        new TouchAction(driver).press(driver.findElementByAccessibilityId("Gallery"))
                .waitAction(2000).moveTo(driver.findElementByAccessibilityId("Auto Complete")).release();

@Test public void tezt() {
    ...
    horizontalSwipe.get().perform();
    ...
    verticalSwiping.get().perform();   
    ...
}
```

```java
public class GestureUtils {
    
    public static ActionSupplier<TouchAction> swipe(final AppiumDriver<?> driver, final params) {
        return () -> {
            new TouchAction(driver).press(params)
            .waitAction(params).moveTo(params).release();
        };
    }    
}

public class SomeTest {
    @Test public void tezt() {
        ...
        GestureUtils.swipe(driver, params).get().perform();
        ...
    }
}

```