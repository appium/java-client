package io.appium.java_client.ios;

import io.appium.java_client.FindsByIosUIAutomation;
import io.appium.java_client.MobileCommand;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ScrollsTo;

import org.openqa.selenium.WebElement;

import com.google.common.collect.ImmutableMap;

import java.util.List;

public class IOSElement<RequiredElementType extends WebElement> extends MobileElement<RequiredElementType> implements 
FindsByIosUIAutomation<RequiredElementType>, ScrollsTo<RequiredElementType> {
	
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

  /**
   * Scroll to the element whose 'text' attribute contains the input text.
   * Scrolling happens within this element
   * @param text input text contained in text attribute
   */
	@Override
	public RequiredElementType scrollTo(String text) {
		return (RequiredElementType) findElementByIosUIAutomation(".scrollToElementWithPredicate(\"name CONTAINS '" + text + "'\")");
	}

  /**
   * Scroll to the element whose 'text' attribute matches the input text.
   * Scrolling happens within this element
   * @param text input text contained in text attribute
   */
	@Override
	public RequiredElementType scrollToExact(String text) {
		return (RequiredElementType) findElementByIosUIAutomation(".scrollToElementWithName(\"" + text + "\")");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setValue(String value) {		
		ImmutableMap.Builder builder = ImmutableMap.builder();
		builder.put("id", id).put("value", value);
		execute(MobileCommand.SET_VALUE, builder.build());
	}
}
