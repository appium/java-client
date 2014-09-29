package io.appium.java_client.android;

import io.appium.java_client.DeviceActionShortcuts;

public interface AndroidDeviceActionShortcuts extends DeviceActionShortcuts {
	/**
	 * Send a key event along with an Android metastate to an Android device
	 * Metastates are things like *shift* to get uppercase characters
	 * 
	 * @param key code for the key pressed on the Android device
	 * @param metastate metastate for the keypress
	 * 
	 * @see AndroidKeyCode
	 * @see AndroidKeyMetastate
	 */
	public void sendKeyEvent(int key, Integer metastate);
}
