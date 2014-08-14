package io.appium.java_client;

/**
 * for use with setting Network Connections on a mobile device.
 * Each network option can be enabled or disabled.
 * Current network options available: Airplane Mode, Wifi, Data
 */
public class NetworkConnectionSetting {

  public int value = 0;
  private int airplaneMode = 1;
  private int wifi = 2;
  private int data = 4;

  /**
   *
   * @param airplaneMode boolean for airplane mode enabled
   * @param wifi boolean for wifi enabled
   * @param data boolean for data enabled
   */
  public NetworkConnectionSetting(boolean airplaneMode, boolean wifi, boolean data) {
    int a = airplaneMode ? this.airplaneMode : 0;
    int b = wifi ? this.wifi : 0;
    int c = data ? this.data : 0;

    value = a | b | c;
  }

  /**
   * Instantiate Network Connection Settings with the straight-up bitmask. See the Mobile JSON Wire Protocol spec for details.
   * @param bitmask
   */
  public NetworkConnectionSetting(int bitmask) {
    value = bitmask;
  }

  public boolean airplaneModeEnabled() { return (value & airplaneMode) != 0; }

  public boolean wifiEnabled() { return (value & wifi) != 0; }

  public boolean dataEnabled() { return (value & data) != 0; }

  public void setAirplaneMode(boolean enable) {
    if (enable) {
      value = value | airplaneMode;
    } else {
      value = value & ~airplaneMode;
    }
  }

  public void setWifi(boolean enable) {
    if (enable) {
      value = value | wifi;
    } else {
      value = value & ~wifi;
    }
  }

  public void setData(boolean enable) {
    if (enable) {
      value = value | data;
    } else {
      value = value & ~data;
    }
  }

  public int hashCode() {
    return value;
  }

  public boolean equals(Object obj) {
    if (obj instanceof Integer) {
      return value == (Integer) obj;
    }
    if (obj instanceof NetworkConnectionSetting) {
      return value == ((NetworkConnectionSetting)obj).value;
    }
    else {
      return false;
    }
  }

  public String toString() {
    return "{ AirplaneMode: " + airplaneModeEnabled() + ", Wifi: " + wifiEnabled() + ", Data: " + dataEnabled() + "}";
  }

}
