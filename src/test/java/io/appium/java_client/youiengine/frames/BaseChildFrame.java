package io.appium.java_client.youiengine.frames;

import io.appium.java_client.youiengine.YouiEngineDriver;
import org.openqa.selenium.WebElement;

/**
 * This base class is used for child screens in the YouiEngineAppiumSample app. All child screens
 * must have a Back button allowing users to return to the initial page.
 *
 * Created by paulfoster on 2016-07-26.
 */
public abstract class BaseChildFrame {
    protected YouiEngineDriver driver;

    public BaseChildFrame(YouiEngineDriver youiEngineDriver) {
        driver = youiEngineDriver;
    }

    public WebElement getBackButton() {
        return driver.findElementByName("Btn-Back");
    }

    public void goBack() {
        getBackButton().click();
    }
}
