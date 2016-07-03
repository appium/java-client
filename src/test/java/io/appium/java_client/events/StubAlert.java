package io.appium.java_client.events;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.security.Credentials;

public class StubAlert implements Alert {
    @Override public void dismiss() {

    }

    @Override public void accept() {

    }

    @Override public String getText() {
        return StringUtils.EMPTY;
    }

    @Override public void sendKeys(String keysToSend) {

    }

    @Override public void setCredentials(Credentials credentials) {

    }

    @Override public void authenticateUsing(Credentials credentials) {

    }
}
