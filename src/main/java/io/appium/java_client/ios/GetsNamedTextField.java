package io.appium.java_client.ios;

import org.openqa.selenium.WebElement;

public interface GetsNamedTextField {
	/**
	 * In iOS apps, named TextFields have the same accessibility Id as their
	 * containing TableElement. This is a convenience method for getting the
	 * named TextField, rather than its containing element.
	 * 
	 * @param name
	 *            accessiblity id of TextField
	 * @return The textfield with the given accessibility id
	 */
	public WebElement getNamedTextField(String name);

}
