package io.appium.java_client.events;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Alert;

public class StubAlert implements Alert {
    @Override public void dismiss() {
        //STUB it does nothing
    }

    @Override public void accept() {
        //STUB it does nothing
    }

    @Override public String getText() {
        return StringUtils.EMPTY;
    }

    @Override public void sendKeys(String keysToSend) {
        //STUB it does nothing
    }
}
