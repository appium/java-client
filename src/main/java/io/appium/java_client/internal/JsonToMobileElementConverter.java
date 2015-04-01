package io.appium.java_client.internal;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.util.Collection;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.internal.JsonToWebElementConverter;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Reconstitutes {@link WebElement}s from their JSON representation. Will recursively convert Lists
 * and Maps to catch nested references. All other values pass through the converter unchanged.
 */
public abstract class JsonToMobileElementConverter extends JsonToWebElementConverter {
	protected AppiumDriver<?> driver;

	public JsonToMobileElementConverter(AppiumDriver<?> driver) {
		super(driver);
		this.driver = driver;
	}

	public Object apply(Object result) {
		if (result instanceof Collection<?>) {
			Collection<?> results = (Collection<?>) result;
			return Lists.newArrayList(Iterables.transform(results, this));
		}

		if (result instanceof Map<?, ?>) {
			Map<?, ?> resultAsMap = (Map<?, ?>) result;
			if (resultAsMap.containsKey("ELEMENT")) {
				MobileElement<?> element = newMobileElement();
				element.setId(String.valueOf(resultAsMap.get("ELEMENT")));
				element.setFileDetector(driver.getFileDetector());
				return element;
			} else {
				return Maps.transformValues(resultAsMap, this);
			}
		}

		if (result instanceof Number) {
			if (result instanceof Float || result instanceof Double) {
				return ((Number) result).doubleValue();
			}
			return ((Number) result).longValue();
		}

		return result;
	}

	protected abstract MobileElement<?> newMobileElement(); //{
		//MobileElement toReturn = new MobileElement();
		//toReturn.setParent(driver);
		//return toReturn;
	//}
}
