package io.appium.java_client.ios;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.FindsByIosUIAutomation;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.internal.JsonToIOSElementConverter;
import io.appium.java_client.remote.MobilePlatform;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;

import java.net.URL;
import java.util.List;

import static io.appium.java_client.MobileCommand.*;

public class IOSDriver extends AppiumDriver implements IOSDeviceActionShortcuts, GetsNamedTextField, FindsByIosUIAutomation{
	private static final String IOS_PLATFORM = MobilePlatform.IOS;

	public IOSDriver(URL remoteAddress, Capabilities desiredCapabilities) {
		super(remoteAddress, substituteMobilePlatform(desiredCapabilities,
				IOS_PLATFORM));
		this.setElementConverter(new JsonToIOSElementConverter(this));
  }

  /**
   * Scroll to the element whose 'text' attribute contains the input text.
   * This scrolling happens within the first UIATableView on the UI. Use the additional 'context' param to specify a different scrollView.
   * @param text input text contained in text attribute
   */
   @Override
   public MobileElement scrollTo(String text) {
     return ((MobileElement) findElementByClassName("UIATableView")).scrollTo(text);
   }

   /**
   * Scroll to the element whose 'text' attribute is equal to the input text.
   * This scrolling happens within the first UIATableView on the UI. Use the additional 'context' param to specify a different scrollView.
   * @param text input text to match
   */
   @Override
   public MobileElement scrollToExact(String text) {
	  return ((MobileElement) findElementByClassName("UIATableView")).scrollToExact(text);
   }

   /**
	 * @see IOSDeviceActionShortcuts#hideKeyboard(String, String)
	 */
	@Override
	public void hideKeyboard(String strategy, String keyName) {
		String[] parameters = new String[] { "strategy", "key" };
		Object[] values = new Object[] { strategy, keyName };		
		execute(HIDE_KEYBOARD, getCommandImmutableMap(parameters, values));
	}
	
	/**
	 * @see IOSDeviceActionShortcuts#hideKeyboard(String)
	 */
	@Override
	public void hideKeyboard(String keyName) {
		execute(HIDE_KEYBOARD, ImmutableMap.of("keyName", keyName));
	}	
	
	/**
	 * @see IOSDeviceActionShortcuts#shake()
	 */
	@Override
	public void shake() {
		execute(SHAKE);
	}	
	
	/**
	 * @see GetsNamedTextField#getNamedTextField(String)
	 */
	@Override
	public WebElement getNamedTextField(String name) {
		MobileElement element = (MobileElement) findElementByAccessibilityId(name);
		if (element.getTagName() != "TextField") {
			return element.findElementByAccessibilityId(name);
		}
		return element;
	}
	
	@Override
	public WebElement findElementByIosUIAutomation(String using) {
		return findElement("-ios uiautomation", using);
	}

	@Override
	public List<WebElement> findElementsByIosUIAutomation(String using) {
		return findElements("-ios uiautomation", using);
	}	
}
