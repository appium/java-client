package io.appium.java_client;

import java.util.Map;

public class DisplayMetrics {
    private Integer height;
    private Integer width;
    private Integer density;
    private Float wdpi;
    private Float hdpi;
    private Float scaleFactor;

    public DisplayMetrics(Map<String, String> map) {
        height = Integer.parseInt(map.get("height"));
        width = Integer.parseInt(map.get("width"));
        density = Integer.parseInt(map.get("density"));
        wdpi = Float.parseFloat(map.get("wdpi"));
        hdpi = Float.parseFloat(map.get("hdpi"));
        scaleFactor = Float.parseFloat(map.get("scaleFactor"));
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getDensity() {
        return density;
    }

    public Float getWdpi() {
        return wdpi;
    }

    public Float getHdpi() {
        return hdpi;
    }

    public Float getScaleFactor() {
        return scaleFactor;
    }
}
