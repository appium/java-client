package io.appium.java_client.youiengine.frames;

import io.appium.java_client.youiengine.YouiEngineDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by paulfoster on 2016-07-21.
 */
public class LanderFrame {

    private YouiEngineDriver driver;

    /* Constructor takes an instance of the YouiEngineDriver. This ensures there is an open and
        running session to use.     */
    public LanderFrame(YouiEngineDriver youiEngineDriver) {
        driver = youiEngineDriver;
    }

    /* The following get methods perform the find as called to ensure the WebElement is present
        and not a stale reference.     */
    public WebElement getTextEditButton() {
        return driver.findElementByName("Text Edit");
    }

    public WebElement getButtonsButton() {
        return driver.findElementByName("Buttons");
    }

    /* Helper methods to make authoring test scripts easier and increase readability in the
        authored test scripts.   */
    public void clickTextEditButton() {
        getTextEditButton().click();
    }

    public void clickButtonsButton() {
        getButtonsButton().click();
    }
}
