package io.appium.java_client.android;

import io.appium.java_client.NetworkConnectionSetting;

public interface HasNetworkConnection {

	/**
	 * Get the current network settings of the device.
	 * 
	 * @return NetworkConnectionSetting objects will let you inspect the status
	 *         of AirplaneMode, Wifi, Data connections
	 */
	public NetworkConnectionSetting getNetworkConnection();

	/**
	 * Set the network connection of the device. This is an Android-only method
	 * 
	 * @param connection
	 *            The NetworkConnectionSetting configuration to use for the
	 *            device
	 */
	public void setNetworkConnection(NetworkConnectionSetting connection);

}
