package io.appium.java_client.youiengine.internal;

import io.appium.java_client.MobileElement;
import io.appium.java_client.internal.JsonToMobileElementConverter;
import io.appium.java_client.youiengine.YouiEngineElement;

import org.openqa.selenium.remote.RemoteWebDriver;

public class JsonToYouiEngineElementConverter extends JsonToMobileElementConverter {
    public JsonToYouiEngineElementConverter(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    protected MobileElement newMobileElement() {
        YouiEngineElement toReturn = new YouiEngineElement();
        toReturn.setParent(driver);
        return (MobileElement) toReturn;
    }
}
