package io.appium.java_client.youiengine.frames;


import io.appium.java_client.youiengine.YouiEngineDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by paulfoster on 2016-07-21.
 */
public class ButtonsFrame extends BaseChildFrame {

    /* Constructor takes an instance of the YouiEngineDriver. This ensures there is an open and
        running session to use.     */
    public ButtonsFrame(YouiEngineDriver youiEngineDriver) {
        super(youiEngineDriver);
    }

    /* The following get methods perform the find as called to ensure the WebElement is present
        and not a stale reference.     */
    public WebElement getPushButton() {
        return driver.findElementByName("PushButton");
    }

    public WebElement getToggleButton() {
        return driver.findElementByName("ToggleButton");
    }

    public WebElement getPartiallyObscuredButton() {
        return driver.findElementByName("PartHiddenButton");
    }

    public WebElement getFullyObscuredButton() {
        return driver.findElementByName("FullyHiddenButton");
    }

    public WebElement getImageView() {
        return driver.findElementByName("ImageView");
    }

    public WebElement getDisabledButton() {
        return driver.findElementByName("DisabledButton");
    }

    public WebElement getOpaqueButton() {
        return driver.findElementByName("OpaqueButton");
    }

    public WebElement getImage() {
        return driver.findElementByName("Image-TestNotEnableable");
    }

    /* Helper methods to make authoring test scripts easier and increase readability in the
        authored test scripts.   */
    public String getPushButtonCaption() {
        return getPushButton().getText();
    }

    public String getToggleButtonCaption() {
        return getToggleButton().getText();
    }
}
