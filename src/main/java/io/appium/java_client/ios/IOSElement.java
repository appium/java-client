package io.appium.java_client.ios;

import java.util.List;

import org.openqa.selenium.WebElement;

import io.appium.java_client.FindsByIosUIAutomation;
import io.appium.java_client.MobileElement;

public class IOSElement extends MobileElement implements FindsByIosUIAutomation{
	
	@Override
	public WebElement findElementByIosUIAutomation(String using) {
		return findElement("-ios uiautomation", using);
	}

	@Override
	public List<WebElement> findElementsByIosUIAutomation(String using) {
		return findElements("-ios uiautomation", using);
	}

	@Override
	public MobileElement scrollTo(String text) {
		return (MobileElement) findElementByIosUIAutomation(".scrollToElementWithPredicate(\"name CONTAINS '" + text + "'\")");
	}

	@Override
	public MobileElement scrollToExact(String text) {
		return (MobileElement) findElementByIosUIAutomation(".scrollToElementWithName(\"" + text + "\")");
	}
}
