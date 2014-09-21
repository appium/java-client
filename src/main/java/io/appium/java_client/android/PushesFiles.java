package io.appium.java_client.android;

import io.appium.java_client.InteractsWithFiles;

public interface PushesFiles extends InteractsWithFiles {

	/**
	 * Save base64 encoded data as a file on the remote mobile device.
	 * 
	 * @param remotePath
	 *            Path to file to write data to on remote device
	 * @param base64Data
	 *            Base64 encoded byte array of data to write to remote device
	 */
	public void pushFile(String remotePath, byte[] base64Data);

}
