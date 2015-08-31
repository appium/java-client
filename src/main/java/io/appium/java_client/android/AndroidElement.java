package io.appium.java_client.android;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.MobileCommand;
import io.appium.java_client.MobileElement;


public class AndroidElement extends MobileElement implements
		FindsByAndroidUIAutomator<MobileElement> {
	
	@Override
	public MobileElement findElementByAndroidUIAutomator(String using) {
		return (MobileElement) findElement("-android uiautomator", using);
	}

	@Override
	public List<MobileElement> findElementsByAndroidUIAutomator(String using) {
		List<MobileElement> result = new ArrayList<MobileElement>();
		List<WebElement> found = findElements("-android uiautomator", using);
		for (WebElement e: found)
			result.add((AndroidElement) e);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void replaceValue(String value) {
		ImmutableMap.Builder builder = ImmutableMap.builder();
		builder.put("id", getId()).put("value", new String[] { value });
		execute(MobileCommand.REPLACE_VALUE, builder.build());
	}
}
