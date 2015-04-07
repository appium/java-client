package io.appium.java_client.ios;

import io.appium.java_client.FindsByIosUIAutomation;
import io.appium.java_client.MobileCommand;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ScrollsTo;

import org.openqa.selenium.WebElement;

import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.List;

public class IOSElement extends MobileElement implements 
FindsByIosUIAutomation<MobileElement>, ScrollsTo<MobileElement> {
	
	@Override
	public MobileElement findElementByIosUIAutomation(String using) {
		return (IOSElement) findElement("-ios uiautomation", using);
	}

	@Override
	public List<MobileElement> findElementsByIosUIAutomation(String using) {
		List<MobileElement> result = new ArrayList<MobileElement>();
		List<WebElement> found = findElements("-ios uiautomation", using);
		for (WebElement e: found)
			result.add((IOSElement) e);
		return result;
	}

  /**
   * Scroll to the element whose 'text' attribute contains the input text.
   * Scrolling happens within this element
   * @param text input text contained in text attribute
   */
	@Override
	public MobileElement scrollTo(String text) {
		return (IOSElement) findElementByIosUIAutomation(".scrollToElementWithPredicate(\"name CONTAINS '" + text + "'\")");
	}

  /**
   * Scroll to the element whose 'text' attribute matches the input text.
   * Scrolling happens within this element
   * @param text input text contained in text attribute
   */
	@Override
	public MobileElement scrollToExact(String text) {
		return (IOSElement) findElementByIosUIAutomation(".scrollToElementWithName(\"" + text + "\")");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setValue(String value) {		
		ImmutableMap.Builder builder = ImmutableMap.builder();
		builder.put("id", id).put("value", value);
		execute(MobileCommand.SET_VALUE, builder.build());
	}
}
