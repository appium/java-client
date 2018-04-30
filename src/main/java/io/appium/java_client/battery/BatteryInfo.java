package io.appium.java_client.battery;

import java.util.Map;

public abstract class BatteryInfo {
    private final Map<String, Object> input;

    public BatteryInfo(Map<String, Object> input) {
        this.input = input;
    }

    /**
     * @return Battery level in range [0.0, 1.0], where 1.0 means 100% charge.
     */
    public double getLevel() {
        final Object value = getInput().get("level");
        if (value instanceof Long) {
            return ((Long) value).doubleValue();
        }
        return (double) value;
    }

    protected Map<String, Object> getInput() {
        return this.input;
    }
}
