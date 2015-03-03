package io.appium.java_client;

import org.openqa.selenium.WebDriverException;

public class IllegalCoordinatesException extends WebDriverException {
	private static final long serialVersionUID = 1L;

	public IllegalCoordinatesException(String message) {
		super(message);
	}

}
