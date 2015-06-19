package io.appium.java_client;

import io.appium.java_client.generic.searchcontext.GenericFindsByClassName;
import io.appium.java_client.generic.searchcontext.GenericFindsByCssSelector;
import io.appium.java_client.generic.searchcontext.GenericFindsById;
import io.appium.java_client.generic.searchcontext.GenericFindsByLinkText;
import io.appium.java_client.generic.searchcontext.GenericFindsByName;
import io.appium.java_client.generic.searchcontext.GenericFindsByTagName;
import io.appium.java_client.generic.searchcontext.GenericFindsByXPath;
import io.appium.java_client.generic.searchcontext.GenericSearchContext;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.remote.Response;

@SuppressWarnings({ "unchecked", "rawtypes" })
abstract class DefaultGenericMobileElement<T extends WebElement> extends RemoteWebElement implements 
        GenericSearchContext<T>, GenericFindsById<T>, GenericFindsByXPath<T>, GenericFindsByLinkText<T>, GenericFindsByTagName<T>,
        GenericFindsByClassName<T>, GenericFindsByCssSelector<T>, GenericFindsByName<T>, FindsByAccessibilityId<T>, TouchableElement{

    @Override
    public Response execute(String driverCommand, Map<String, ?> parameters) {
        return super.execute(driverCommand, parameters);
    }

	@Override
    public List findElements(By by){
        return super.findElements(by);
    }

    @Override
    public T findElement(By by){
        return (T) super.findElement(by);
    }

	@Override
    public List findElementsById(String id){
        return super.findElementsById(id);
    }

    @Override
    public T findElementById(String id){
        return (T) super.findElementById(id);
    }

    public T findElementByLinkText(String using) {
        return (T) super.findElementByLinkText(using);
    }

    public List findElementsByLinkText(String using) {
        return super.findElementsByLinkText(using);
    }

    public T findElementByPartialLinkText(String using) {
        return (T) super.findElementByPartialLinkText(using);
    }

    public List findElementsByPartialLinkText(String using) {
        return super.findElementsByPartialLinkText(using);
    }

    public T findElementByTagName(String using) {
        return (T) super.findElementByTagName(using);
    }

    public List findElementsByTagName(String using) {
        return super.findElementsByTagName(using);
    }

    public T findElementByName(String using) {
        return (T) super.findElementByName(using);
    }

    public List findElementsByName(String using) {
        return super.findElementsByName(using);
    }

    public T findElementByClassName(String using) {
        return (T) super.findElementByClassName(using);
    }

    public List findElementsByClassName(String using) {
        return super.findElementsByClassName(using);
    }

    public T findElementByCssSelector(String using) {
        return (T) super.findElementByCssSelector(using);
    }

    public List findElementsByCssSelector(String using) {
        return super.findElementsByCssSelector(using);
    }

    public T findElementByXPath(String using) {
        return (T) super.findElementByXPath(using);
    }

    public List findElementsByXPath(String using) {
        return super.findElementsByXPath(using);
    }

    @Override
    public T findElementByAccessibilityId(String using) {
        return (T) findElement("accessibility id", using);
    }

    @Override
    public List findElementsByAccessibilityId(String using) {
        return (List<T>) findElements("accessibility id", using);
    }
}
