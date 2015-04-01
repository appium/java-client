package io.appium.java_client.ios.internal;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.internal.JsonToMobileElementConverter;
import io.appium.java_client.ios.IOSElement;

public class JsonToIOSElementConverter extends JsonToMobileElementConverter {

	public JsonToIOSElementConverter(AppiumDriver<?> driver) {
		super(driver);
	}

	@Override
	protected MobileElement<?> newMobileElement() {
		IOSElement<?> toReturn = new IOSElement<>();
		toReturn.setParent(driver);
		return toReturn;
	}

}
