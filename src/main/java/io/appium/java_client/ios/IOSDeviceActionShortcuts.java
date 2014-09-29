package io.appium.java_client.ios;

import io.appium.java_client.DeviceActionShortcuts;

public interface IOSDeviceActionShortcuts extends DeviceActionShortcuts {

	/**
	 * Hides the keyboard by pressing the button specified by keyName if it is
	 * showing.
	 * 
	 * @param keyName
	 *            The button pressed by the mobile driver to attempt hiding the
	 *            keyboard
	 */
	public void hideKeyboard(String keyName);

	/**
	 * Hides the keyboard if it is showing. Available strategies are PRESS_KEY
	 * and TAP_OUTSIDE. One taps outside the keyboard, the other presses a key
	 * of your choosing (probably the 'Done' key). Hiding the keyboard often
	 * depends on the way an app is implemented, no single strategy always
	 * works.
	 * 
	 * @param strategy
	 *            HideKeyboardStrategy
	 * @param keyName
	 *            a String, representing the text displayed on the button of the
	 *            keyboard you want to press. For example: "Done"
	 */
	public void hideKeyboard(String strategy, String keyName);

	/**
	 * Simulate shaking the device
	 */
	public void shake();

}
