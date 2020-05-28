package io.appium.java_client.battery;

import java.util.Map;

public abstract class BatteryInfo {
    private final Map<String, Object> input;

    public BatteryInfo(Map<String, Object> input) {
        this.input = input;
    }

    /**
     * Returns battery level.
     *
     * @return Battery level in range [0.0, 1.0], where 1.0 means 100% charge.
     */
    public double getLevel() {
        final Object value = getInput().get("level");
        if (value instanceof Long) {
            return ((Long) value).doubleValue();
        }
        return (double) value;
    }

    /**
     * Returns battery state.
     *
     * @param <T> The type of state data object for the corresponding platform.
     * @return Battery state value.
     */
    public abstract <T> T getState();

    protected Map<String, Object> getInput() {
        return this.input;
    }
}
