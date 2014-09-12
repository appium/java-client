package io.appium.java_client;

public interface DeviceActionShortcuts {

	/**
	   * Hides the keyboard if it is showing.
	   * On iOS, there are multiple strategies for hiding the keyboard. Defaults to the "tapOutside" strategy (taps outside the keyboard).
	   * Switch to using hideKeyboard(HideKeyboardStrategy.PRESS_KEY, "Done") if this doesn't work.
	   */
	  public void hideKeyboard();

	/**
	 * Send a key event to the device
	 * 
	 * @param key code for the key pressed on the device
	 * 
	 * @see AndroidKeyCode
	 * @see IOSKeyCode
	 */
	public void sendKeyEvent(int key);

}
