package io.appium.java_client.android.internal;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.internal.JsonToMobileElementConverter;

public class JsonToAndroidElementConverter extends JsonToMobileElementConverter {

	public JsonToAndroidElementConverter(AppiumDriver driver) {
		super(driver);
	}

	@Override
	protected MobileElement newMobileElement() {
		AndroidElement toReturn = new AndroidElement();
		toReturn.setParent(driver);
		return toReturn;
	}

}
