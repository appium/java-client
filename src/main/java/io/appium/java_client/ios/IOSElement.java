package io.appium.java_client.ios;

import io.appium.java_client.FindsByIosUIAutomation;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ScrollsTo;
import org.openqa.selenium.WebElement;

import java.util.List;

public class IOSElement extends MobileElement implements FindsByIosUIAutomation, ScrollsTo {
	
	@Override
	public WebElement findElementByIosUIAutomation(String using) {
		return findElement("-ios uiautomation", using);
	}

	@Override
	public List<WebElement> findElementsByIosUIAutomation(String using) {
		return findElements("-ios uiautomation", using);
	}

  /**
   * Scroll to the element whose 'text' attribute contains the input text.
   * Scrolling happens within this element
   * @param text input text contained in text attribute
   */
	@Override
	public MobileElement scrollTo(String text) {
		return (MobileElement) findElementByIosUIAutomation(".scrollToElementWithPredicate(\"name CONTAINS '" + text + "'\")");
	}

  /**
   * Scroll to the element whose 'text' attribute matches the input text.
   * Scrolling happens within this element
   * @param text input text contained in text attribute
   */
	@Override
	public MobileElement scrollToExact(String text) {
		return (MobileElement) findElementByIosUIAutomation(".scrollToElementWithName(\"" + text + "\")");
	}
}
