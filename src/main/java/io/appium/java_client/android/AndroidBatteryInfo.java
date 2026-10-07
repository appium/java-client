package io.appium.java_client.android;

import io.appium.java_client.battery.BatteryInfo;

import java.util.Map;

/** Battery information of an Android device. */
public class AndroidBatteryInfo extends BatteryInfo {

    /**
     * Creates the battery info from the raw server response.
     *
     * @param input The raw battery info map.
     */
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

    /** Android battery charging state. */
    public enum BatteryState {
        /** The state is not known. */
        UNKNOWN,
        /** The battery is charging. */
        CHARGING,
        /** The battery is discharging. */
        DISCHARGING,
        /** The device is plugged in, but the battery is not charging. */
        NOT_CHARGING,
        /** The battery is fully charged. */
        FULL
    }
}
