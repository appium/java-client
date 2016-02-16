package io.appium.java_client;

import java.util.Map;

public class PackageVersion {
    private Integer code;
    private String name;

    public PackageVersion(Map<String, String> map) {
        code = Integer.parseInt(map.get("code"));
        name = map.get("name");
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
