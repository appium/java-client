/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.appium.java_client.pagefactory;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchableElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import static io.appium.java_client.pagefactory.utils.ProxyFactory.getEnhancedProxy;
import static io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility.getAutomation;
import static io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility.getPlatform;
import static io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility.unpackWebDriverFromSearchContext;

/**
 * Default decorator for use with PageFactory. Will decorate 1) all of the
 * WebElement fields and 2) List<WebElement> fields that have
 * {@literal @AndroidFindBy}, {@literal @AndroidFindBys}, or
 * {@literal @iOSFindBy/@iOSFindBys} annotation with a proxy that locates the
 * elements using the passed in ElementLocatorFactory.
 *
 * Please pay attention: fields of {@link WebElement}, {@link RemoteWebElement},
 * {@link MobileElement}, {@link AndroidElement} and {@link IOSElement} are allowed 
 * to use with this decorator
 */
public class AppiumFieldDecorator implements FieldDecorator{

    private static final List<Class<? extends WebElement>> availableElementClasses =
            new ArrayList<Class<? extends WebElement>>(){
                private static final long serialVersionUID = 1L;
                {
                    add(WebElement.class);
                    add(RemoteWebElement.class);
                    add(MobileElement.class);
                    add(TouchableElement.class);
                    add(AndroidElement.class);
                    add(IOSElement.class);
                }

            };

    private final static Map<Class<? extends SearchContext>, Class<? extends WebElement>> elementRuleMap =
            new HashMap<Class<? extends SearchContext>, Class<? extends WebElement>>(){
                private static final long serialVersionUID = 1L;
                {
                    put(AndroidDriver.class, AndroidElement.class);
                    put(IOSDriver.class, IOSElement.class);
                }
            };

    private final SearchContext context;
    private final WebDriver originalDriver;
    private final DefaultFieldDecorator elementFieldDecoracor;
    public static long DEFAULT_IMPLICITLY_WAIT_TIMEOUT = 1;
    public static TimeUnit DEFAULT_TIMEUNIT = TimeUnit.SECONDS;


    private static boolean isAvailableWebElementListField(Field field){
        if (!List.class.isAssignableFrom(field.getType())) {
            return false;
        }

        Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType)) {
            return false;
        }

        Type listType = ((ParameterizedType) genericType).getActualTypeArguments()[0];

        boolean result = false;
        for (Class<? extends WebElement> webElementClass:
                availableElementClasses){
            if (!webElementClass.equals(listType)){
                continue;
            }
            result = true;
            break;
        }
        return result;
    }

    public AppiumFieldDecorator(SearchContext context, long implicitlyWaitTimeOut, TimeUnit timeUnit) {
        this(context, new TimeOutDuration(implicitlyWaitTimeOut, timeUnit));
    }

    public AppiumFieldDecorator(SearchContext context, TimeOutDuration timeOutDuration) {
        this.context = context;
        this.originalDriver = unpackWebDriverFromSearchContext(this.context);
        String platform = getPlatform(originalDriver);
        String automation = getAutomation(originalDriver);

        elementFieldDecoracor = new DefaultFieldDecorator(
                new AppiumElementLocatorFactory(context, platform, automation, timeOutDuration, originalDriver)){
            @Override
            protected WebElement proxyForLocator(ClassLoader ignored, ElementLocator locator){
                return getAProxOfASingleElement(locator);
            }

            @Override
            protected List<WebElement> proxyForListLocator(ClassLoader ignored, ElementLocator locator)  {
                return  getAProxOfElementList(locator);
            }

            @Override
            protected boolean isDecoratableList(Field field) {
                return isAvailableWebElementListField(field);
            }
        };
    }

    public AppiumFieldDecorator(SearchContext context) {
        this(context, DEFAULT_IMPLICITLY_WAIT_TIMEOUT, DEFAULT_TIMEUNIT);
    }

    public Object decorate(ClassLoader ignored, Field field) {
        //TODO add logic of page object decoration
        return elementFieldDecoracor.decorate(ignored, field);
    }


    private Class<?> getTypeForProxy(){
        Class<? extends SearchContext> driverClass = originalDriver.getClass();
        Iterable<Map.Entry<Class<? extends SearchContext>, Class<? extends WebElement>>> rules = elementRuleMap.entrySet();
        Iterator<Map.Entry<Class<? extends SearchContext>, Class<? extends WebElement>>> iterator = rules.iterator();
        while (iterator.hasNext()){ //it will return MobileElement subclass when here is something
            //that extends AppiumDriver or MobileElement
            Map.Entry<Class<? extends SearchContext>, Class<? extends WebElement>> e = iterator.next();
            if (e.getKey().isAssignableFrom(driverClass))
                return e.getValue();
        } //it is compatible with desktop browser. So at this case it returns RemoteWebElement.class
        return RemoteWebElement.class;
    }

    private WebElement getAProxOfASingleElement(ElementLocator locator){
        ElementInterceptor elementInterceptor = new ElementInterceptor(locator,
                originalDriver);
        return (WebElement) getEnhancedProxy(getTypeForProxy(),
                elementInterceptor);
    }

    @SuppressWarnings("unchecked")
    private List<WebElement> getAProxOfElementList(ElementLocator locator)  {
        ElementListInterceptor elementInterceptor = new ElementListInterceptor(locator);
        return  getEnhancedProxy(ArrayList.class,
                elementInterceptor);
    }
}
