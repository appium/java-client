package io.appium.java_client.ios;

import io.appium.java_client.battery.BatteryInfo;

import java.util.Map;

public class IOSBatteryInfo extends BatteryInfo {

    public IOSBatteryInfo(Map<String, Object> input) {
        super(input);
    }

    @SuppressWarnings("unchecked")
    @Override
    public BatteryState getState() {
        final int state = ((Long) getInput().get("state")).intValue();
        switch (state) {
            case 1:
                return BatteryState.UNPLUGGED;
            case 2:
                return BatteryState.CHARGING;
            case 3:
                return BatteryState.FULL;
            default:
                return BatteryState.UNKNOWN;
        }
    }

    public enum BatteryState {
        UNKNOWN, UNPLUGGED, CHARGING, FULL
    }
}
