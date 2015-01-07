package io.appium.java_client;


public interface DeviceActionShortcuts {

	/**
	   * Hides the keyboard if it is showing.
	   * On iOS, there are multiple strategies for hiding the keyboard. Defaults to the "tapOutside" strategy (taps outside the keyboard).
	   * Switch to using hideKeyboard(HideKeyboardStrategy.PRESS_KEY, "Done") if this doesn't work.
	   */
	  public void hideKeyboard();

}
