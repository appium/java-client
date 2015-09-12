package io.appium.java_client.ios;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.FindsByAccessibilityId;
import io.appium.java_client.FindsByIosUIAutomation;
import io.appium.java_client.ScrollsTo;
import io.appium.java_client.ios.internal.JsonToIOSElementConverter;
import io.appium.java_client.remote.MobilePlatform;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;

import java.net.URL;
import java.util.List;

import static io.appium.java_client.MobileCommand.*;

/**
 * @param <RequiredElementType> means the required type from the list of allowed types below 
 * that implement {@link WebElement} Instances of the defined type will be 
 * returned via findElement* and findElements*. 
 * Warning (!!!). Allowed types:<br/>
 * {@link WebElement}<br/>
 * {@link TouchableElement}<br/>
 * {@link RemoteWebElement}<br/>
 * {@link MobileElement}
 * {@link IOSElement}
 */
public class IOSDriver<RequiredElementType extends WebElement> extends AppiumDriver<RequiredElementType> implements IOSDeviceActionShortcuts, GetsNamedTextField<RequiredElementType>, 
FindsByIosUIAutomation<RequiredElementType>{
	private static final String IOS_PLATFORM = MobilePlatform.IOS;

	public IOSDriver(URL remoteAddress, Capabilities desiredCapabilities) {
		super(remoteAddress, substituteMobilePlatform(desiredCapabilities,
				IOS_PLATFORM));
		this.setElementConverter(new JsonToIOSElementConverter(this));
    }

    public IOSDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        super(service, substituteMobilePlatform(desiredCapabilities,
                IOS_PLATFORM));
        this.setElementConverter(new JsonToIOSElementConverter(this));
    }

    public IOSDriver(AppiumServiceBuilder builder, Capabilities desiredCapabilities) {
        super(builder, substituteMobilePlatform(desiredCapabilities,
                IOS_PLATFORM));
        this.setElementConverter(new JsonToIOSElementConverter(this));
    }

    public IOSDriver(Capabilities desiredCapabilities) {
        super(substituteMobilePlatform(desiredCapabilities,
                IOS_PLATFORM));
        this.setElementConverter(new JsonToIOSElementConverter(this));
    }

  /**
   * Scroll to the element whose 'text' attribute contains the input text.
   * This scrolling happens within the first UIATableView on the UI. Use the method on IOSElement to scroll from a different ScrollView.
   * @param text input text contained in text attribute
   */
   @SuppressWarnings("unchecked")
   @Override
   public RequiredElementType scrollTo(String text) {
     return (RequiredElementType) ((ScrollsTo<?>) 
    		 findElementByClassName("UIATableView")).scrollTo(text);
   }

   /**
   * Scroll to the element whose 'text' attribute is equal to the input text.
   * This scrolling happens within the first UIATableView on the UI. Use the method on IOSElement to scroll from a different ScrollView.
   * @param text input text to match
   */
   @SuppressWarnings("unchecked")
   @Override
   public RequiredElementType scrollToExact(String text) {
	  return (RequiredElementType) ((ScrollsTo<?>) 
			  findElementByClassName("UIATableView")).scrollToExact(text);
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
	@SuppressWarnings("unchecked")
	@Override
	public RequiredElementType getNamedTextField(String name) {
		RequiredElementType element = findElementByAccessibilityId(name);
		if (element.getTagName() != "TextField") {
			return (RequiredElementType) ((FindsByAccessibilityId<?>) element).
					findElementByAccessibilityId(name);
		}
		return element;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public RequiredElementType findElementByIosUIAutomation(String using) {
		return (RequiredElementType) findElement("-ios uiautomation", using);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RequiredElementType> findElementsByIosUIAutomation(String using) {
		return (List<RequiredElementType>) findElements("-ios uiautomation", using);
	}	
}
