package io.appium.java_client;

import java.util.Map;

public class Owner {
    private String user;
    private String group;

    public Owner(Map<String, String> map) {
        user = map.get("user");
        group = map.get("group");
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
