package io.appium.java_client.youiengine.frames;

import io.appium.java_client.youiengine.YouiEngineDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by paulfoster on 2016-07-21.
 */
public class TextEditFrame extends BaseChildFrame {

    /* Constructor takes an instance of the YouiEngineDriver. This ensures there is an open and
        running session to use.     */
    public TextEditFrame(YouiEngineDriver youiEngineDriver) {
        super(youiEngineDriver);
    }

    /* The following get methods perform the find as called to ensure the WebElement is present
        and not a stale reference.     */
    public WebElement getTextEdit() {
        return driver.findElementByName("TextEdit");
    }

    public WebElement getPasswordEdit() {
        return driver.findElementByName("PasswordEdit");
    }

    /* Helper methods to make authoring test scripts easier and increase readability in the
        authored test scripts.   */
    public void clearTextEdit() {
        getTextEdit().clear();
    }

    public void setTextEditValue(String value) {
        getTextEdit().sendKeys(value);
    }

    public void clearPasswordEdit() {
        getPasswordEdit().clear();
    }

    public void setPasswordEditValue(String value) {
        getPasswordEdit().sendKeys(value);
    }

    public void clearAll() {
        clearTextEdit();
        clearPasswordEdit();
    }

    public String getTextEditValue() {
        return getTextEdit().getText();
    }

    public String getPasswordEditValue() {
        return getPasswordEdit().getText();
    }
}
