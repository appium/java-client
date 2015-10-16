Appium Java client has facilities which components to [Page Object](https://github.com/SeleniumHQ/selenium/wiki/PageObjects) design pattern and [Selenium PageFactory](https://github.com/SeleniumHQ/selenium/wiki/PageFactory).


# WebElement/list of WebElement field can be populated by default:
```java
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
...

@FindBy(someStrategy) //for browser or web view html UI 
//also for mobile native applications when other locator strategies are not defined
WebElement someElement;

@FindBy(someStrategy) //for browser or web view html UI 
//also for mobile native applications when other locator strategies are not defined
List<WebElement> someElements;
```

# If there is need to use convinient locators for mobile native applications then the following is available:

```java
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.remote.RemoteWebElement;
import io.appium.java_client.pagefactory.*;
import io.appium.java_client.ios.IOSElement;

@AndroidFindBy(someStrategy) //for Android UI when Android UI automator is used
AndroidElement someElement;

@AndroidFindBy(someStrategy) //for Android UI when Android UI automator is used
List<AndroidElement> someElements;

@SelendroidFindBy(someStrategy) //for Android UI when Selendroid automation is used
RemoteWebElement someElement;

@SelendroidFindBy(someStrategy) //for Android UI when Selendroid automation is used
List<RemoteWebElement> someElements;

@iOSFindBy(someStrategy) //for iOS native UI
IOSElement someElement;

@iOSFindBy(someStrategy) //for iOS native UI
List<IOSElement> someElements;
```

# The example for the crossplatform mobile native testing

```java
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.*;

@AndroidFindBy(someStrategy) 
@iOSFindBy(someStrategy) 
MobileElement someElement;

@AndroidFindBy(someStrategy) //for the crossplatform mobile native
@iOSFindBy(someStrategy) //testing
List<MobileElement> someElements;
```

# The fully cross platform examle

```java
import org.openqa.selenium.remote.RemoteWebElement;
import io.appium.java_client.pagefactory.*;
import org.openqa.selenium.support.FindBy;

//the fully cross platform examle
@FindBy(someStrategy) //for browser or web view html UI
@AndroidFindBy(someStrategy) //for Android native UI 
@iOSFindBy(someStrategy)  //for iOS native UI 
RemoteWebElement someElement;

//the fully cross platform examle
@FindBy(someStrategy)
@AndroidFindBy(someStrategy) //for Android native UI 
@iOSFindBy(someStrategy)  //for iOS native UI 
List<RemoteWebElement> someElements;
```

# Also it is possible to define chained or any possible locators.

- Chained
```java
import org.openqa.selenium.remote.RemoteWebElement;
import io.appium.java_client.pagefactory.*;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.FindBy;

@FindBys({@FindBy(someStrategy1), @FindBy(someStrategy2)}) 
@AndroidFindBys({@AndroidFindBy(someStrategy1), @AndroidFindBy(someStrategy2)}) 
@iOSFindBys({@iOSFindBy(someStrategy1), @iOSFindBy(someStrategy2)}) 
RemoteWebElement someElement;

@FindBys({@FindBy(someStrategy1), @FindBy(someStrategy2)}) 
@AndroidFindBys({@AndroidFindBy(someStrategy1), @AndroidFindBy(someStrategy2)}) 
@iOSFindBys({@iOSFindBy(someStrategy1), @iOSFindBy(someStrategy2)}) 
List<RemoteWebElement> someElements;
```

- Any possible
```java
import org.openqa.selenium.remote.RemoteWebElement;
import io.appium.java_client.pagefactory.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindByAll;

@FindAll({@FindBy(someStrategy1), @FindBy(someStrategy2)}) 
@AndroidFindAll({@AndroidFindBy(someStrategy1), @AndroidFindBy(someStrategy2)}) 
@iOSFindAll({@iOSFindBy(someStrategy1), @iOSFindBy(someStrategy2)}) 
RemoteWebElement someElement;

@FindAll({@FindBy(someStrategy1), @FindBy(someStrategy2)}) 
@AndroidFindAll({@AndroidFindBy(someStrategy1), @AndroidFindBy(someStrategy2)}) 
@iOSFindAll({@iOSFindBy(someStrategy1), @iOSFindBy(someStrategy2)}) 
List<RemoteWebElement> someElements;
```

# Appium Java client is integrated with Selenium PageFactory by AppiumFieldDecorator. 

Object fields are populated as below: 

- 
```java
import io.appium.java_client.pagefactory.*;
import org.openqa.selenium.support.PageFactory;

PageFactory.initElements(new AppiumFieldDecorator(searchContext 
              /*searchContext is a WebDriver or WebElement
              instance */), 
              pageObject //an instance of PageObject.class
);
```

-
```java
import io.appium.java_client.pagefactory.*;
import org.openqa.selenium.support.PageFactory;
import java.util.concurrent.TimeUnit;

PageFactory.initElements(new AppiumFieldDecorator(searchContext, 
              /*searchContext is a WebDriver or WebElement
              instance */
	    15, //default implicit waiting timeout for all strategies
		TimeUnit.SECONDS), 
			pageObject //an instance of PageObject.class
);
```

- 
```java
import io.appium.java_client.pagefactory.*;
import org.openqa.selenium.support.PageFactory;
import java.util.concurrent.TimeUnit;

PageFactory.initElements(new AppiumFieldDecorator(searchContext, 
              /*searchContext is a WebDriver or WebElement
              instance */
	    new TimeOutDuration(15, //default implicit waiting timeout for all strategies
		TimeUnit.SECONDS)), 
			pageObject //an instance of PageObject.class
);
```

If time of the waiting for elements differs from usual (longer, or shorter when element is needed only for quick checkings/assertions) then 

```java
import io.appium.java_client.pagefactory.*;

@WithTimeout(timeOut = yourTime, timeUnit = yourTimeUnit)
RemoteWebElement someElement;

@WithTimeout(timeOut = yourTime, timeUnit = yourTimeUnit)
List<RemoteWebElement> someElements;
```

# The additional feature.

## The simple example 
Let's imagine that the task is to check an Android client of the [http://www.rottentomatoes.com](http://www.rottentomatoes.com/). Let it be like a picture below
 
![](https://cloud.githubusercontent.com/assets/4927589/11120641/51c1fda8-8962-11e5-8b17-323b5f236fce.png) Lets imagine that it is only a part of the screen. 

A typical page object could look like: 

```java
public class RottenTomatoesScreen {
    //convinient locator  
    private List<AndroidElement> titles;
 
    //convinient locator  
    private List<AndroidElement> scores;

    //convinient locator  
    private List<AndroidElement> castings;
    //element declaration goes on 

    public String getMovieCount(){
       //.......
    }  

    public String getTitle(params){
       //.......
    }  

    public String getScore(params){
      //.......
    }  

    public String getCasting(params){
      //.......
    } 

    public void openMovieInfo(params){
      //.......
    } 

    //method declaration goes on 
}
```

The description above can be decomposed. Let's work it out! 

Firstly a Movie-widget could be described this way: 

```java
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;

public class Movie extends Widget{
   protected Movie(WebElement element) {
        super(element);
   }
    
   //convinient locator  
   private AndroidElement title;
 
   //convinient locator  
   private AndroidElement score;

   //convinient locator  
   private AndroidElement casting;

   public String getTitle(params){
       //.......
   }  

   public String getScore(params){
      //.......
   }  

   public String getCasting(params){
      //.......
   } 

   public void openMovieInfo(params){
       ((AndroidElement) getWrappedElement()).tap(1, 1500);
   } 
    
}
```

So, now page object looks 

```java
public class RottenTomatoesScreen {

     @AndroidFindBy(a locator which convinient to find a single movie-root - element)
     private List<Movie> movies;

      //element declaration goes on 

     public String getMovieCount(){
        return movies.size();
     }  

     public Movie getMovie(int index){
        //any interaction with sub-elements of a movie-element
        //will be performed outside of the page-object instance 
        return movie.get(index);
     }
     //method declaration goes on 
}
```

### Ok. What if Movie-class is reused and a wrapped root element is usually found by the same locator?

Then
```java
//the class is annotated !!!
@AndroidFindBy(a locator which convinient to find a single movie-root - element)
public class Movie extends Widget{
...
}

```
and

```java
public class RottenTomatoesScreen {
     //!!! locator is not necessary at this case
     private List<Movie> movies;
...
}
```

### Ok. What if movie list is not a whole screen? E.g. we want to describe it as a widget with nested movies.  

Then: 

```java
//with the usual locator or without it
public class Movies extends Widget{

    //with a custom locator or without it
    private List<Movie> movies;
...
}
```

and 

```java
public class RottenTomatoesScreen {

    //with a custom locator or without it
    Movies movies;
...
}
```

### Good! How to poputate all these fields? 

As usual:

```java
RottenTomatoesScreen screen = new RottenTomatoesScreen();
PageFactory.initElements(new AppiumFieldDecorator(searchContext /*WebDriver or WebElement
              instance */), screen);
```


## Specification

A class which describes a widget or group of elements should extend 

```java
io.appium.java_client.pagefactory.Widget;
```

Any widget/group of elements can be described it terms of sub-elements or nested sub-widgets.  
Appium-specific annotations are used for this purpose. 

### Any class which describes the real widget or group of elements can be annotated

That means that when the same "widget" is used frequently and any root element of this can be found by the same locator then user can 

```java
@FindBy(relevant locator) //how to find a root element
public class UsersWidget extends Widget{

  @FindBy(relevant locator) //this element will be found 
  //using the root element
  WebElement subElement1;

  @FindBy(relevant locator) //this element will be found 
  //using the root element
  WebElement subElement2;

  @FindBy(relevant locator) //a root element 
  //of this widget is the sub-element which 
  //will be found from top-element
  UsersWidget subWidget;

  //and so on..   
}
```

and then it is enough

```java
  //above is the other field declaration

  UsersWidget widget;

  //below is the other field/method declaration
```

If the widget really should be found using an another locator then 

```java
  //above is the other field declaration
  @FindBy(another relevant locator) //this locator overrides 
  //the declared in the using class
  UsersWidget widget;

  //below is the other field/method declaration
```

### Ok. What should users do if they want to implement a subclass which describes a similar group of elements for the same platform?

There is nothing special. 

```java
@FindBy(relevant locator) //how to find a root element
public class UsersWidget extends Widget{
... 
}

```
```java
//at this case the root element will be found by the locator
//which is declared in superclass
public class UsersOverriddenWidget extends UsersWidget {
... 
}
```

and 

```java
@FindBy(relevant locator2) //this locator overrides 
//all locators declared in superclasses
public class UsersOverriddenWidget2 extends UsersWidget {
... 
}
```

### Is it possible to reuse "widgets" in crossplatform testing? 

If there is no special details of interaction with an application browser version and/or versions for different mobile OS's then 


```java
@FindBy(relevant locator for browser/webview html or by default) 
@AndroidFindBy(relevant locator for Android UI automator)
@iOSFindBy(relevant locator for iOS UI automation)
public class UsersWidget extends Widget {

  @FindBy(relevant locator for browser/webview html or by default) 
  @AndroidFindBy(relevant locator for Android UI automator)
  @iOSFindBy(relevant locator for iOS UI automation)
  RemoteWebElement subElement1;

  @FindBy(relevant locator for browser/webview html or by default) 
  @AndroidFindBy(relevant locator for Android UI automator)
  @iOSFindBy(relevant locator for iOS UI automation)
  RemoteWebElement subElement2;

  //overrides a html/default
  //locator declared in the used class
  @FindBy(relevant locator for browser/webview html or by default) 
  //overrides an Android UI automator
  //locator declared in the used class
  @AndroidFindBy(relevant locator for Android UI automator) 
  //overrides an iOS UI automation
  //locator declared in the using class 
  @iOSFindBy(relevant locator for iOS UI automation)   
  UsersWidget subWidget;

  //and so on..   
}
```

### What if interaction with a "widget" has special details for each used platform, but the same at high-level

Then it is possible 

```java
public /*abstract*/ class DefaultAbstractUsersWidget extends Widget{

}
```

and 

```java
@FindBy(locator)
public class UsersWidgetForHtml extends DefaultAbstractUsersWidget {

}
```

and 

```java
@AndroidFindBy(locator)
public class UsersWidgetForAndroid extends DefaultAbstractUsersWidget {

}
```

and even

```java
@iOSFindBy(locator)
public class UsersWidgetForIOS extends DefaultAbstractUsersWidget {

}
```

and then 


```java
  import io.appium.java_client.pagefactory.OverrideWidget;
  ...
  
  //above is the other field declaration
  @OverrideWidget(html = UsersWidgetForHtml.class, 
  androidUIAutomator = UsersWidgetForAndroid.class, 
  iOSUIAutomation = UsersWidgetForIOS .class)
  DefaultAbstractUsersWidget widget;

  //below is the other field/method declaration
```

This use case has some restrictions;

- All classes which are declared by the OverrideWidget annotation should be subclasses of the class declared by field

- All classes which are declared by the OverrideWidget should not be abstract. If a declared class is overriden partially like 

```java
  //above is the other field declaration

  @OverrideWidget(iOSUIAutomation = UsersWidgetForIOS .class)
  DefaultUsersWidget widget; //lets assume that there are differences of 
  //interaction with iOS and by default we use DefaultUsersWidget.
  //Then DefaultUsersWidget should not be abstract too.
  //

  //below is the other field/method declaration
```

- for now it is not possible to 
  
```java
  import io.appium.java_client.pagefactory.OverrideWidget;
  ...
  
  //above is the other field declaration
  @OverrideWidget(html = UsersWidgetForHtml.class, 
  androidUIAutomator = UsersWidgetForAndroid.class, 
  iOSUIAutomation = UsersWidgetForIOS .class)
  DefaultAbstractUsersWidget widget;

  //below is the other field/method declaration

  //user's code
  ((UsersWidgetForAndroid) widget).doSpecialWorkForAndroing()
```

The workaround: 

```java
  import io.appium.java_client.pagefactory.OverrideWidget;
  ...
  
  //above is the other field declaration
  @OverrideWidget(html = UsersWidgetForHtml.class, 
  androidUIAutomator = UsersWidgetForAndroid.class, 
  iOSUIAutomation = UsersWidgetForIOS .class)
  DefaultAbstractUsersWidget widget;

  //below is the other field/method declaration

  //user's code
  ((UsersWidgetForAndroid) widget.getSelfReference()).doSpecialWorkForAndroing()
```

### Good! What about widget lists?

All that has been mentioned above is true for "widget" lists. 

### One more restriction

It is strongly recommended to implement  each subclass of __io.appium.java_client.pagefactory.Widget__ with this constructor

```java
   public /*or any other available modifier*/ WidgetSubclass(WebElement element) {
       super(element);
   }
```