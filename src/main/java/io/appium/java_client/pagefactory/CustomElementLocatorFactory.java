package io.appium.java_client.pagefactory;

import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

/**
 * Created by dolf on 18.08.15
 */
public interface CustomElementLocatorFactory extends ElementLocatorFactory {
    ElementLocator createLocator(Class clazz);
}
