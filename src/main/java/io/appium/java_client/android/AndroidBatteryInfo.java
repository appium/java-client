package io.appium.java_client.android;

import io.appium.java_client.battery.BatteryInfo;

import java.util.Map;

public class AndroidBatteryInfo extends BatteryInfo {

    public AndroidBatteryInfo(Map<String, Object> input) {
        super(input);
    }

    @SuppressWarnings("unchecked")
    @Override
    public BatteryState getState() {
        final int state = ((Long) getInput().get("state")).intValue();
        switch (state) {
            case 2:
                return BatteryState.CHARGING;
            case 3:
                return BatteryState.DISCHARGING;
            case 4:
                return BatteryState.NOT_CHARGING;
            case 5:
                return BatteryState.FULL;
            default:
                return BatteryState.UNKNOWN;
        }
    }

    public enum BatteryState {
        UNKNOWN, CHARGING, DISCHARGING, NOT_CHARGING, FULL
    }
}
