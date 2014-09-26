package io.appium.java_client.android;

import java.util.List;

import org.openqa.selenium.WebElement;

import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.MobileElement;

public class AndroidElement extends MobileElement implements
		FindsByAndroidUIAutomator {
	
	@Override
	public WebElement findElementByAndroidUIAutomator(String using) {
		return findElement("-android uiautomator", using);
	}

	@Override
	public List<WebElement> findElementsByAndroidUIAutomator(String using) {
		return findElements("-android uiautomator", using);
	}

	/**
	   * Scroll forward to the element which has a description or name which contains the input text.
	   * The scrolling is performed on the first scrollView present on the UI
	   * @param text
	   */	
	@Override
	public MobileElement scrollTo(String text) {
		String uiScrollables = AndroidDriver.UiScrollable("new UiSelector().descriptionContains(\"" + text + "\")") +
				AndroidDriver.UiScrollable("new UiSelector().textContains(\"" + text + "\")");
        return (MobileElement) findElementByAndroidUIAutomator(uiScrollables);
	}

	/**
	 * Scroll forward to the element which has a description or name which exactly matches the input text.
	 * The scrolling is performed on the first scrollView present on the UI
	 * @param text
	 */
	@Override
	public MobileElement scrollToExact(String text) {
	    String uiScrollables = AndroidDriver.UiScrollable("new UiSelector().description(\"" + text + "\")") +
	    		AndroidDriver.UiScrollable("new UiSelector().text(\"" + text + "\")");
        return (MobileElement) findElementByAndroidUIAutomator(uiScrollables);
	}
}
