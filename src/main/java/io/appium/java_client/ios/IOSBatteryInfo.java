package io.appium.java_client.ios;

import io.appium.java_client.battery.BatteryInfo;

import java.util.Map;

/** Battery information of an iOS device. */
public class IOSBatteryInfo extends BatteryInfo {

    /**
     * Creates the battery info from the raw server response.
     *
     * @param input The raw battery info map.
     */
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

    /** iOS battery charging state. */
    public enum BatteryState {
        /** The state is not known. */
        UNKNOWN,
        /** The device is not plugged in. */
        UNPLUGGED,
        /** The battery is charging. */
        CHARGING,
        /** The battery is fully charged. */
        FULL
    }
}
