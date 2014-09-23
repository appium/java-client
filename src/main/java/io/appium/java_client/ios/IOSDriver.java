package io.appium.java_client.ios;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;

import java.net.URL;

import static io.appium.java_client.MobileCommand.*;

public class IOSDriver extends AppiumDriver implements IOSDeviceActionShortcuts, GetsNamedTextField{
	private static final String IOS_PLATFORM = MobilePlatform.IOS;

	public IOSDriver(URL remoteAddress, Capabilities desiredCapabilities) {
		super(remoteAddress, substituteMobilePlatform(desiredCapabilities,
				IOS_PLATFORM));
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
}
