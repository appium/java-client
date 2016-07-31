package io.appium.java_client.youiengine.frames;

import io.appium.java_client.youiengine.YouiEngineDriver;
import io.appium.java_client.youiengine.util.TestUtility;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;

/**
 * This class is used to encapsulate all common application behaviours and make them
 * available to test authors from one object. Test scripts instance this object and call methods
 * from this class or its member objects.
 *
 * By wrapping interactions in methods based off of UI components, a hierarchy is created that
 * allows test authors to write simple code. The actual code behind the interactions can be
 * updated in one place which detaches the test scripts from the implementation reducing
 * maintenance should the application under test change.
 *
 * Created by paulfoster on 2016-07-21.
 */

public class YouiEngineAppiumSampleApp {
    private YouiEngineDriver driver;
    private static TestUtility utility;

    public LanderFrame landerScreen;
    public TextEditFrame textEditScreen;
    public ButtonsFrame buttonsScreen;

    /* Constructor takes an instance of the YouiEngineDriver. This ensures there is an open and
         running session to use and allows the test author to swap capabilities at the test script
         level.

         This allows the YouiEngineSampleApp class to be platform independent.   */
    public YouiEngineAppiumSampleApp(YouiEngineDriver youiEngineDriver) {
        driver = youiEngineDriver;
        landerScreen = new LanderFrame(driver);
        textEditScreen = new TextEditFrame(driver);
        buttonsScreen = new ButtonsFrame(driver);
    }

    /* Helper methods to allow test authors to perform app level navigation. The leverage common
         designs in the app such as the presence of a Back button on the second tier screens. */
    public void goHome() throws NoSuchFrameException {
        if (!exists("Text Edit")) {
            if (!exists("Btn-Back")) {
                throw new NoSuchFrameException("Unable to determine what frame is currently " +
                        "active.");
            }
            driver.findElementByName("Btn-Back").click();
        }
    }

    public void goToButtonsScreen() {
        navigateTo("PushButton");
    }

    public void goToTextEditScreen() {
        navigateTo("TextEdit");
    }

    // Private helper methods.
    private boolean exists(String nameToSearchFor) {
        try {
            driver.findElementByName(nameToSearchFor);
        }
        catch (NoSuchElementException nseEx) {
            return false;
        }
        return true;
    }

    private void navigateTo(String target) {
        // check to see if we can find a known View on the screen before we try navigating
        // because we might already be there.
        if (!exists(target)) {
            // check if a back button exists, if so click it to ensure the app is at the lander
            // screen
            if (exists("Btn-Back")){
                goHome();
            }
            if (target.equals("TextEdit")) {
                landerScreen.clickTextEditButton();
            } else {
                landerScreen.clickButtonsButton();
            }
        }
        // TODO for some reason the implicit wait is not working for this one scenario?
        utility.delayInSeconds(2);
    }
}
