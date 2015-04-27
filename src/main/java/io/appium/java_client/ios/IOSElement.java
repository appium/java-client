package io.appium.java_client.ios;

import io.appium.java_client.FindsByIosUIAutomation;
import io.appium.java_client.MobileCommand;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ScrollsTo;

import org.openqa.selenium.WebElement;

import com.google.common.collect.ImmutableMap;

import java.util.List;

public class IOSElement extends MobileElement implements FindsByIosUIAutomation, ScrollsTo {
	
	@Override
	public IOSElement findElementByIosUIAutomation(String using) {
		return (IOSElement) findElement("-ios uiautomation", using);
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
    /**
     * Scroll to the CellView whose 'index' is equal to the input cellIndex.
     * This scrolling happens within the first UIATableView on the UI. Use the method on IOSElement to scroll from a different ScrollView.
     * @param integer cell index to match
     */

    public MobileElement scrollTobyCellIndex(int cellIndex){
        cellIndex = cellIndex -1;
        return (MobileElement) findElementByIosUIAutomation(".cells()["+cellIndex+"].scrollToVisible();");
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setValue(String value) {		
		ImmutableMap.Builder builder = ImmutableMap.builder();
		builder.put("id", id).put("value", value);
		execute(MobileCommand.SET_VALUE, builder.build());
	}
}
