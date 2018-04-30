package io.appium.java_client.android;

import io.appium.java_client.battery.BatteryInfo;

import java.util.Map;

public class AndroidBatteryInfo extends BatteryInfo {

    public AndroidBatteryInfo(Map<String, Object> input) {
        super(input);
    }

    /**
     * @return Battery status value.
     */
    public BatteryStatus getStatus() {
        final int status = ((Long) getInput().get("status")).intValue();
        switch (status) {
            case 2:
                return BatteryStatus.CHARGING;
            case 3:
                return BatteryStatus.DISCHARGING;
            case 4:
                return BatteryStatus.NOT_CHARGING;
            case 5:
                return BatteryStatus.FULL;
            default:
                return BatteryStatus.UNKNOWN;
        }
    }

    public enum BatteryStatus {
        UNKNOWN, CHARGING, DISCHARGING, NOT_CHARGING, FULL
    }
}
