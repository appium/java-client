package io.appium.java_client.android;

import java.util.List;

import org.openqa.selenium.WebElement;

import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.MobileElement;

/**
 * @param <RequiredElementType> means the required type from the list of allowed types below 
 * that implement {@link WebElement} Instances of the defined type will be 
 * returned via findElement* and findElements*. 
 * Warning (!!!). Allowed types:<br/>
 * {@link WebElement}<br/>
 * {@link TouchableElement}<br/>
 * {@link RemoteWebElement}<br/>
 * {@link MobileElement}
 * {@link AndroidElement}
 */
public class AndroidElement<RequiredElementType extends WebElement> extends MobileElement<RequiredElementType> implements
		FindsByAndroidUIAutomator<RequiredElementType> {
	
	@SuppressWarnings("unchecked")
	@Override
	public RequiredElementType findElementByAndroidUIAutomator(String using) {
		return (RequiredElementType) findElement("-android uiautomator", using);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RequiredElementType> findElementsByAndroidUIAutomator(String using) {
		return (List<RequiredElementType>) findElements("-android uiautomator", using);
	}

}
