package io.appium.java_client;

public interface InteractsWithFiles {

	/**
	 * 
	 * @param remotePath
	 *            On Android and iOS, this is either the path to the file
	 *            (relative to the root of the app's file system). On iOS only,
	 *            if path starts with /AppName.app, which will be replaced with
	 *            the application's .app directory
	 * @return A byte array of Base64 encoded data.
	 */
	public byte[] pullFile(String remotePath);

	/**
	 * Pull a folder from the simulator/device. Does not work on iOS Real
	 * Devices, but works on simulators
	 * 
	 * @param remotePath
	 *            On Android and iOS, this is either the path to the file
	 *            (relative to the root of the app's file system). On iOS only,
	 *            if path starts with /AppName.app, which will be replaced with
	 *            the application's .app directory
	 * @return A byte array of Base64 encoded data, representing a ZIP ARCHIVE
	 *         of the contents of the requested folder.
	 */
	public byte[] pullFolder(String remotePath);

}
