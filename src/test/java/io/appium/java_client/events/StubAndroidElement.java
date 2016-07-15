package io.appium.java_client.events;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;

import java.util.ArrayList;
import java.util.List;

public class StubAndroidElement extends AndroidElement {
    @Override public String toString() {
        return this.getClass().getCanonicalName();
    }

    @Override public MobileElement findElement(String by, String using) {
        return new StubAndroidElement();
    }

    @Override public List<MobileElement> findElements(String by, String using) {
        List<MobileElement> result = new ArrayList<>();
        result.add(new StubAndroidElement());
        result.add(new StubAndroidElement());
        return result;
    }

    public void replaceValue(String value) {
        //STUB it does nothing
    }
}
