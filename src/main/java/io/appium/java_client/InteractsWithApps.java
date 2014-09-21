package io.appium.java_client;

public interface InteractsWithApps {
	/**
	 * Launch the app which was provided in the capabilities at session creation
	 */
	public void launchApp();

	/**
	 * Install an app on the mobile device
	 * 
	 * @param appPath
	 *            path to app to install
	 */
	public void installApp(String appPath);

	/**
	 * Checks if an app is installed on the device
	 * 
	 * @param bundleId
	 *            bundleId of the app
	 * @return True if app is installed, false otherwise
	 */
	public boolean isAppInstalled(String bundleId);

	/**
	 * Reset the currently running app for this session
	 */
	public void resetApp();

	/**
	 * Runs the current app as a background app for the number of seconds
	 * requested. This is a synchronous method, it returns after the back has
	 * been returned to the foreground.
	 * 
	 * @param seconds
	 *            Number of seconds to run App in background
	 */
	public void runAppInBackground(int seconds);

	/**
	 * Remove the specified app from the device (uninstall)
	 * 
	 * @param bundleId
	 *            the bunble identifier (or app id) of the app to remove
	 */
	public void removeApp(String bundleId);

	/**
	 * Close the app which was provided in the capabilities at session creation
	 */
	public void closeApp();

}
