package io.appium.java_client;

import org.openqa.selenium.WebElement;

public interface ScrollsTo<T extends WebElement> {
	
	/**
	* Scroll to an element which contains the given text.
	* @param text
	*/	
	public T scrollTo(String text);
	
	/**
	* Scroll to an element with the given text.
	* @param text
	*/	
	public T scrollToExact(String text);

}
