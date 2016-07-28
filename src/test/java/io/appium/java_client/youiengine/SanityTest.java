package io.appium.java_client.youiengine;

import io.appium.java_client.android.Connection;
import io.appium.java_client.youiengine.util.BaseYouiEngineTest;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.remote.SessionId;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static io.appium.java_client.android.AndroidKeyCode.KEYCODE_H;
import static io.appium.java_client.android.AndroidKeyCode.KEYCODE_I;
import static org.hamcrest.CoreMatchers.not;

/**
 * This test class performs a simple series of tests to confirm our implementation in the
 * YouiEngine driver and in this java_client. This test class uses the included
 * YouiEngineAppiumSample app as a target for these tests. The intent of each test can be found
 * before each of the test methods.
 *
 * <p>This test uses the model provided in the Appium java_client test tutorial.
 *
 * <p>Uncompress the iOS YouiEngineAppiumSample.app.zip before using this with iOS as a target.
 */
public class SanityTest extends BaseYouiEngineTest {

    // Confirm we can get the page source and it is not empty.
    @org.junit.Test
    public void pageSourceTest() throws Exception {
        String source;
        source = driver.getPageSource();
        System.out.println("\nPageSource: " + source);
        Assert.assertThat(source, not(""));

    }

    // Confirm we can take a screenshot.
    @org.junit.Test
    public void screenshotTest() throws Exception {
        File screenShot = driver.getScreenshotAs(OutputType.FILE);
        File output = new File("screenShot.PNG");
        FileUtils.copyFile(screenShot, output);

        System.out.println("\nOutput: " + output.getAbsolutePath());
    }

    // Confirm we can take a screenshot with a path supplied.
    @org.junit.Test
    public void screenshotWithPathTest() throws Exception {
        String fileWithPath = Paths.get(System.getProperty("user.home"))
                + "/Desktop/Screenshots/screenShot.PNG";
        File output = new File(fileWithPath);

        File screenShot = driver.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenShot, output);

        System.out.println("\nOutput: " + output.getAbsolutePath());
    }

    // Confirm we can find an element using the class name strategy.
    @org.junit.Test
    public void findElementByClassTest() throws Exception {
        WebElement button = null;
        try {
            button = driver.findElement(By.className("CYIPushButtonView"));
        } catch (NoSuchElementException exception) {
            Assert.fail("Did not find the control.");
        }
        Assert.assertNotNull(button);
    }

    // Confirm we can find an element using the name strategy.
    @org.junit.Test
    public void findElementByNameTest() throws Exception {
        WebElement buttonsButton = null;
        try {
            buttonsButton = driver.findElement(By.name("Buttons"));
        } catch (NoSuchElementException exception) {
            Assert.fail("Did not find the control.");
        }

        Assert.assertNotNull(buttonsButton);
    }

    // Confirm we throw a NoSuchElementException when the element was not found.
    @org.junit.Test
    public void findElementNotFoundTest() throws Exception {
        boolean exceptionThrown = false;
        WebElement noSuchElement = null;

        try {
            noSuchElement = driver.findElement(By.className("DoesNotExistView"));
        } catch (NoSuchElementException exception) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);
    }

    // Confirm we can find multiple elements using the class name strategy.
    @org.junit.Test
    public void findMultipleElementsTest() throws Exception {
        app.goToTextEditScreen();

        int expectedCount = 2;
        List<WebElement> atlasTextSceneViewList = driver
                .findElements(By.className("CYIAtlasTextSceneNode"));
        utils.outputListContents(atlasTextSceneViewList);

        Assert.assertEquals(atlasTextSceneViewList.size(), expectedCount);
    }

    /* Confirm we can perform a relative find. The parent is found using the class name strategy
     * and the child is found using the name strategy. */
    @org.junit.Test
    public void findSingleElementFromElementTest() throws Exception {
        app.goToTextEditScreen();

        WebElement field = driver.findElement(By.className("CYITextEditView"));
        WebElement childItem = field.findElement(By.name("Text"));

        String foundText = childItem.getText();
        String expectedText = "TextEdit";
        Assert.assertEquals(expectedText, foundText);
    }

    /* Confirm we can perform a relative find resulting in multiple elements. The parent is found
     * using the class name strategy and its children are also found using the class name strategy.
     * */
    @org.junit.Test
    public void findMultipleElementsFromElementTest() throws Exception {
        app.goToTextEditScreen();

        WebElement sceneView = driver.findElement(By.className("CYISceneView"));
        List<WebElement> labelItems = sceneView.findElements(
                By.className("CYIAtlasTextSceneNode"));
        utils.outputListContents(labelItems);

        int expectedCount = 2;
        int actualCount = labelItems.size();
        Assert.assertEquals(expectedCount, actualCount);
    }

    // Confirm we get the text of a button.
    @org.junit.Test
    public void getTextTest() throws Exception {
        app.goToButtonsScreen();

        String expectedText = "Pushed 0 Times";
        Assert.assertEquals(expectedText, app.buttonsScreen.getPushButtonCaption());
    }

    // Confirm we can retrieve the name attribute of an input field.
    @org.junit.Test
    public void getNameTest() throws Exception {
        app.goToTextEditScreen();
        String expected = "TextEdit";

        WebElement inputField = app.textEditScreen.getTextEdit();
        Assert.assertEquals(expected, inputField.getAttribute("name"));
    }

    // Confirm we can set the text of an input field.
    @org.junit.Test
    public void valueSetTest() throws Exception {
        app.goToTextEditScreen();
        String expected = "One Two 3";
        app.textEditScreen.setTextEditValue(expected);
        utils.delayInSeconds(2);

        Assert.assertEquals(expected, app.textEditScreen.getTextEditValue());
    }

    //TODO create one with special keys to validate we support special keys

    // Confirm we can stop and then start up the app.
    @org.junit.Test
    public void startStopAppTest() throws Exception {
        driver.closeApp();
        // NOTE: utils.delayInSeconds allows time for the app to update between commands. It will
        //  be replaced in the future with app side signals or events to notify the Appium the
        //  app is ready.
        utils.delayInSeconds(2);
        driver.launchApp();
        utils.delayInSeconds(2);
    }

    // Confirm we can send the app to the background for a short time.
    @org.junit.Test
    public void runInBackgroundTest() throws Exception {
        driver.runAppInBackground(10);
        try {
            app.goToButtonsScreen();
        } catch (Exception ex) {
            Assert.fail("Failed to find or interact with the button after backgrounding the app. "
                    + "May not have restored properly.");
        }
    }

    // Confirm we can toggle the Android device's location services.
    @org.junit.Test
    public void toggleLocationServicesTest() throws Exception {
        boolean pass;
        try {
            driver.toggleLocationServices(); // off
            utils.delayInSeconds(2);
            driver.toggleLocationServices(); // on
            utils.delayInSeconds(2);
            pass = true;
        } catch (WebDriverException wdEx) {
            pass = isAndroid ? false : true;
        }
        Assert.assertTrue(pass);
    }

    // Confirm we can get the context.
    @org.junit.Test
    public void getContextTest() throws Exception {
        String contextValue = driver.getContext();

        Assert.assertNotNull(contextValue);
        System.out.println("\nContext value: " + contextValue);
    }

    /* Confirm we can get all contexts.
     * NOTE: YouiEngine currently only supports one context. */
    @org.junit.Test
    public void getContextsTest() throws Exception {
        Set<String> contextValues = driver.getContextHandles();

        Assert.assertNotNull(contextValues);
        System.out.println("\nContext values: " + contextValues);
    }

    // Confirm we can get the session id.
    @org.junit.Test
    public void getSessionIdTest() throws Exception {
        SessionId sessionId = driver.getSessionId();
        Assert.assertNotNull(sessionId);

        System.out.println("\nSession Id: " + sessionId);
    }

    /* Confirm we can remove the app.
     * NOTE: Only supported on Android and this will also shut down the driver. */
    @org.junit.Test
    public void removeAppTest() throws Exception {
        boolean actual = false;
        try {
            driver.removeApp(bundleId);
            actual = true; // did not throw exception
        } catch (WebDriverException wdException) {
            Assert.fail("WebDriverException was thrown.");
        }
        Assert.assertTrue(actual);
    }

    /* Confirm we can remove the app.
     * NOTE: Only supported on Android and this will also shut down the driver. */
    @org.junit.Test
    public void installAppTest() throws Exception {
        boolean actual = false;
        try {
            driver.removeApp(bundleId);
            utils.delayInSeconds(5);
            driver.installApp(appPath);
            utils.delayInSeconds(5);
            actual = driver.isAppInstalled(bundleId);
        } catch (WebDriverException wdException) {
            if (isAndroid) {
                Assert.fail("WebDriverException was thrown while on Android.");
            } else {
                actual = true;
            }
        }
        Assert.assertTrue(actual);
    }

    // Confirm we can determine if an app is installed.
    @org.junit.Test
    public void isAppInstalledTest() throws Exception {
        boolean actual = false;
        try {
            actual = driver.isAppInstalled(bundleId);
        } catch (WebDriverException wdException) {
            if (!driver.appPlatform.equals(driver.IOS)) {
                Assert.fail("WebDriverException was thrown when not on iOS.");
            } else {
                System.out.println("\nExpected exception was thrown.");
                actual = true; // did not get a value, but we threw an exception for iOS
            }
        }
        Assert.assertTrue(actual);
    }

    // Confirm we can press a key code
    @org.junit.Test
    public void pressKeyCodeTest() throws Exception {
        app.goToTextEditScreen();
        boolean actual = false;
        app.textEditScreen.getTextEdit().click();
        utils.delayInSeconds(1);
        try {
            driver.pressKeyCode(KEYCODE_H);
            driver.pressKeyCode(KEYCODE_I);

            String myText = app.textEditScreen.getTextEditValue();
            actual = Objects.equals(myText, "hi");

        } catch (WebDriverException wdException) {
            if (!driver.appPlatform.equals(driver.IOS)) {
                Assert.fail("WebDriverException was thrown when not on iOS.");
            } else {
                System.out.println("\nExpected exception was thrown.");
                actual = true; // did not get a value, but we threw an exception for iOS
            }
        }
        Assert.assertTrue(actual);
    }

    // Confirm we can long press a key code
    @org.junit.Test
    public void longPressKeyCodeTest() throws Exception {
        app.goToTextEditScreen();

        boolean actual = false;

        app.textEditScreen.getTextEdit().click();
        utils.delayInSeconds(2);

        try {
            driver.longPressKeyCode(KEYCODE_H);

            utils.delayInSeconds(5);
            String myText = app.textEditScreen.getTextEditValue();
            char myChar0 = myText.charAt(0);
            char myChar1 = myText.charAt(1);

            System.out.println("Resulting text: " + myText);
            actual = (Objects.equals(myChar0, 'h')) && (Objects.equals(myChar1, 'h'));

        } catch (WebDriverException wdException) {
            if (!driver.appPlatform.equals(driver.IOS)) {
                Assert.fail("WebDriverException was thrown when not on iOS.");
            } else {
                System.out.println("\nExpected exception was thrown.");
                actual = true; // did not get a value, but we threw an exception for iOS
            }
        }
        Assert.assertTrue(actual);
    }

    /** Helper to test NetworkConnections. */
    public void networkConnectionTest(Connection stateToTest) throws Exception {
        boolean actual = false;
        try {
            Connection stateToSet;

            // Get initial state
            Connection statePrior;
            statePrior = driver.getConnection();
            Assert.assertNotEquals(Connection.NONE, statePrior);

            // Set state to Airplane
            Connection stateAfter;
            stateToSet = Connection.AIRPLANE;
            driver.setConnection(stateToSet);
            stateAfter = driver.getConnection();
            Assert.assertEquals(stateToSet, stateAfter);

            // Set state to test
            stateToSet = stateToTest;
            driver.setConnection(stateToSet);
            stateAfter = driver.getConnection();
            Assert.assertEquals(stateToSet, stateAfter);

            // Set state to initial state
            stateToSet = statePrior;
            driver.setConnection(stateToSet);
            stateAfter = driver.getConnection();
            Assert.assertEquals(stateToSet, stateAfter);
            actual = true; // Passed all tests

        } catch (WebDriverException wdException) {
            if (!driver.appPlatform.equals(driver.IOS)) {
                Assert.fail("WebDriverException was thrown when not on iOS.");
            } else {
                System.out.println("\nExpected exception was thrown.");
                actual = true; // did not get a value, but we threw an exception for iOS
            }
        }
        Assert.assertTrue(actual);
    }

    // Confirm we can set Network to WIFI only
    @org.junit.Test
    public void networkConnectionWiFiTest() throws Exception {
        networkConnectionTest(Connection.WIFI);
    }

    // Confirm we can set Network to DATA only
    @org.junit.Test
    public void networkConnectionDataTest() throws Exception {
        networkConnectionTest(Connection.DATA);
    }

    // Confirm we can set Network to ALL
    @org.junit.Test
    public void networkConnectionAllTest() throws Exception {
        networkConnectionTest(Connection.ALL);
    }

    /* Performs a device level orientation change and confirms we can retrieve the expected
    * orientation. */
    @org.junit.Test
    public void verifyOrientationChangesTest() throws Exception {
        ScreenOrientation currentOrientation = driver.getOrientation();

        // Check that the default orientation for this app was Portrait.
        if (currentOrientation == ScreenOrientation.PORTRAIT) {
            System.out.println("\nOrientation was Portrait.");
        } else {
            Assert.fail("\nOrientation was not Portrait.");
        }

        utils.delayInSeconds(10);

        // Switch the orientation to Landscape
        driver.rotate(ScreenOrientation.LANDSCAPE);
        utils.delayInSeconds(10);

        currentOrientation =  driver.getOrientation();
        if (currentOrientation == ScreenOrientation.LANDSCAPE) {
            System.out.println("\nOrientation was Landscape.");
        } else {
            Assert.fail("\nOrientation was not Landscape.");
        }

        // Switch back to Portrait
        driver.rotate(ScreenOrientation.PORTRAIT);
        utils.delayInSeconds(10);

        currentOrientation =  driver.getOrientation();
        if (currentOrientation == ScreenOrientation.PORTRAIT) {
            System.out.println("\nOrientation was Portrait.");
        } else {
            Assert.fail("\nOrientation was not Portrait.");
        }
    }

    // Call the get app string and ensure we do not throw any exception.
    @org.junit.Test
    public void getStringsTest() throws Exception {
        Map appStringMap = driver.getAppStringMap();
        // we expect null for iOS because this does not have a localizable string file
        // for Android it returns a value
        if (isAndroid) {
            Assert.assertNotNull(appStringMap);
            System.out.println("\nMap: " + appStringMap.toString());
        } else {
            Assert.assertEquals(null, appStringMap);
        }
    }

    // TODO These overloads do not currently apply to You.i Engine applications.
    // getAppStringMap(language)
    // getAppStringMap(language, stringFile)

    // Confirm the resetApp call works.
    @org.junit.Test
    public void resetTest() throws Exception {
        try {
            driver.resetApp();
        } catch (Exception ex ) {
            Assert.fail("\nException was thrown when trying to reset the app.");
        }
    }

    // Confirm the getLogTypes call works.
    @org.junit.Test
    public void getLogTypesTest() throws Exception {
        Set<String> logTypes = driver.getLogTypes();
        Assert.assertNotNull(logTypes);
        System.out.println("\nLog Types: " + logTypes);
    }

    // Confirm the getLogs call works with LOG_SYSTEM.
    @org.junit.Test
    public void getSysLogTest() throws Exception {
        if (driver.appPlatform.equals(driver.ANDROID)) {
            System.out.println("Not a supported Log Type for Android.");
            return;
        }
        LogEntries logs = driver.getLogs(driver.LOG_SYSTEM);
        Assert.assertNotNull(logs);
        System.out.println("\nLogs: " + logs.toString());
    }

    // Confirm the getLogs call works with LOG_CRASH.
    @org.junit.Test
    public void getCrashLogTest() throws Exception {
        if (driver.appPlatform.equals(driver.ANDROID)) {
            System.out.println("Not a supported Log Type for Android.");
            return;
        }
        LogEntries logs = driver.getLogs(driver.LOG_CRASH);
        Assert.assertNotNull(logs);
        System.out.println("\nLogs: " + logs.toString());
    }

    // Confirm the getLogs call works with LOG_CLIENT.
    @org.junit.Test
    public void getClientLogTest() throws Exception {
        LogEntries logs = driver.getLogs(driver.LOG_CLIENT);
        Assert.assertNotNull(logs);
        System.out.println("\nLogs: " + logs.toString());
    }

    // Confirm the getLogs call works with LOG_LOGCAT.
    @org.junit.Test
    public void getLogCatLogTest() throws Exception {
        if (driver.appPlatform.equals(driver.IOS)) {
            System.out.println("Not a supported Log Type for iOS.");
            return;
        }
        LogEntries logs = driver.getLogs(driver.LOG_LOGCAT);
        Assert.assertNotNull(logs);
        System.out.println("\nLogs: " + logs.toString());
    }

    // Confirm the mobileShake call works.
    @org.junit.Test
    public void mobileShakeTest() throws Exception {
        if (driver.appPlatform.equals(driver.ANDROID)) {
            System.out.println("mobileShake() is not supported for Android.");
            return;
        }
        driver.shake();
    }

    // Regression - ensure no exceptions occur when sending a click to a CYIAtlasTextSceneNode.
    @org.junit.Test
    public void clickOnCyiTextAtlasTest() throws Exception {
        app.goToTextEditScreen();
        WebElement textLabel = driver.findElement(By.className("CYIAtlasTextSceneNode"));
        try {
            textLabel.click();
        } catch (Exception ex) {
            // we don't want any exceptions to be thrown
            Assert.fail("An exception occurred when clicking on a CYIAtlasTextSceneNode.");
        }
    }

    /* The following tests ensure that the server properly responds to these unsupported requests
     with the appropriate error. */
    @org.junit.Test
    public void findElementByXPathTest() throws Exception {
        WebElement textEdit;
        try {
            textEdit = driver.findElement(By.xpath(".//CYISceneView//CYITextEditView"));
            Assert.assertNotNull(textEdit);
        } catch (Exception ex) {
            outputException(ex, "Expected exception was thrown.");
            return;
        }
        //shouldn't get this far.
        Assert.fail("Did not throw the expected exception.");
    }

    @org.junit.Test
    public void findElementsByXPathTest() throws Exception {
        List<WebElement> textEdits;
        try {
            textEdits = driver.findElements(By.xpath(".//CYISceneView//CYITextEditView"));
            Assert.assertNotNull(textEdits);
        } catch (Exception ex) {
            outputException(ex, "Expected exception was thrown.");
            return;
        }
        //shouldn't get this far.
        Assert.fail("Did not throw the expected exception.");
    }

    @org.junit.Test
    public void findElementByCssSelectorTest() throws Exception {
        WebElement textEdit;
        try {
            textEdit = driver.findElement(By.cssSelector(".something"));
            Assert.assertNotNull(textEdit);
        } catch (Exception ex) {
            outputException(ex, "Expected exception was thrown.");
            return;
        }
        //shouldn't get this far.
        Assert.fail("Did not throw the expected exception.");
    }

    @org.junit.Test
    public void findElementsByCssSelectorTest() throws Exception {
        List<WebElement> textEdits;
        try {
            textEdits = driver.findElements(By.cssSelector(".something"));
            Assert.assertNotNull(textEdits);
        } catch (Exception ex) {
            outputException(ex, "Expected exception was thrown.");
            return;
        }
        //shouldn't get this far.
        Assert.fail("Did not throw the expected exception.");
    }

    @org.junit.Test
    public void findElementByLinkTextTest() throws Exception {
        WebElement textEdit;
        try {
            textEdit = driver.findElement(By.linkText("Link"));
            Assert.assertNotNull(textEdit);
        } catch (Exception ex) {
            outputException(ex, "Expected exception was thrown.");
            return;
        }
        //shouldn't get this far.
        Assert.fail("Did not throw the expected exception.");
    }

    @org.junit.Test
    public void findElementsByLinkTextTest() throws Exception {
        List<WebElement> textEdits;
        try {
            textEdits = driver.findElements(By.linkText("Link"));
            Assert.assertNotNull(textEdits);
        } catch (Exception ex) {
            outputException(ex, "Expected exception was thrown.");
            return;
        }
        //shouldn't get this far.
        Assert.fail("Did not throw the expected exception.");
    }

    @org.junit.Test
    public void findElementByPartialLinkTextTest() throws Exception {
        WebElement textEdit;
        try {
            textEdit = driver.findElement(By.partialLinkText("Link"));
            Assert.assertNotNull(textEdit);
        } catch (Exception ex) {
            outputException(ex, "Expected exception was thrown.");
            return;
        }
        //shouldn't get this far.
        Assert.fail("Did not throw the expected exception.");
    }

    @org.junit.Test
    public void findElementsByPartialLinkTextTest() throws Exception {
        List<WebElement> textEdits;
        try {
            textEdits = driver.findElements(By.partialLinkText("Link"));
            Assert.assertNotNull(textEdits);
        } catch (Exception ex) {
            outputException(ex, "Expected exception was thrown.");
            return;
        }
        //shouldn't get this far.
        Assert.fail("Did not throw the expected exception.");
    }

    @org.junit.Test
    public void isSelectedTest() throws Exception {
        app.goToButtonsScreen();
        WebElement toggleButton = app.buttonsScreen.getToggleButton();

        // Initial state should be 'off'.
        Assert.assertFalse(toggleButton.isSelected());

        // Toggle this to on and check again.
        toggleButton.click();
        utils.delayInSeconds(1);
        Assert.assertTrue(toggleButton.isSelected());

        // Toggle this back off and confirm it is off.
        toggleButton.click();
        utils.delayInSeconds(1);
        Assert.assertFalse(toggleButton.isSelected());
    }

    @org.junit.Test
    public void isEnabledYes() throws Exception {
        app.goToButtonsScreen();
        boolean result = app.buttonsScreen.getPushButton().isEnabled();
        Assert.assertTrue(result);
    }

    @org.junit.Test
    public void isEnabledNo() throws Exception {
        app.goToButtonsScreen();

        boolean result =  app.buttonsScreen.getDisabledButton().isEnabled();
        Assert.assertEquals(false, result);
    }

    @org.junit.Test
    public void isEnabledNotSupported() throws Exception {
        boolean result = false;
        app.goToButtonsScreen();
        try {
            app.buttonsScreen.getImage().isEnabled();
        } catch (UnsupportedCommandException ucEx) {
            result = true;
        }
        Assert.assertTrue(result);
    }

    @org.junit.Test
    public void isDisplayedYes() {
        app.goToButtonsScreen();
        boolean result = app.buttonsScreen.getPushButton().isDisplayed();
        Assert.assertTrue(result);
    }

    @org.junit.Test
    public void isDisplayedPartial() {
        app.goToButtonsScreen();
        boolean result = app.buttonsScreen.getPartiallyObscuredButton().isDisplayed();
        // partially obscured views are still visible so this would return true
        Assert.assertTrue(result);
    }

    @org.junit.Test
    public void isDisplayedNo() {
        app.goToButtonsScreen();
        boolean result = app.buttonsScreen.getOpaqueButton().isDisplayed();
        Assert.assertEquals(false, result);
    }

    @org.junit.Test
    public void isDisplayedFullyObscured() {
        app.goToButtonsScreen();
        // even though this is fully obscured, it is still rendered
        boolean result = app.buttonsScreen.getFullyObscuredButton().isDisplayed();
        Assert.assertEquals(true, result);
    }

    // Attempting to use a stale element reference should throw the appropriate exception.
    @org.junit.Test
    public void clickStaleElementTest() throws Exception {
        WebElement textEditButton = app.landerScreen.getTextEditButton();

        textEditButton.click();
        utils.delayInSeconds(2);

        boolean staleElementReferenceExceptionThrown = false;
        try {
            // Click the button on the previous page
            textEditButton.click();
        } catch (StaleElementReferenceException sere) {
            staleElementReferenceExceptionThrown = true;
        }
        Assert.assertTrue(staleElementReferenceExceptionThrown);
    }

    /* Set the text of an input field and delete a portion of it using BACK_SPACE.
     *  */
    @org.junit.Test
    public void sendKeysBackspaceTest() throws Exception {
        app.goToTextEditScreen();
        String expectedText = "You.i";
        String deletedText = " Engine";
        String sentText = expectedText + deletedText;

        app.textEditScreen.setTextEditValue(sentText);
        utils.delayInSeconds(2);
        Assert.assertEquals(sentText, app.textEditScreen.getTextEditValue());

        for (int i = 0; i < deletedText.length(); ++i) {
            app.textEditScreen.getTextEdit().sendKeys(Keys.BACK_SPACE);
        }
        Assert.assertEquals(expectedText, app.textEditScreen.getTextEditValue());
    }

    /* Set the text of an input field and delete a portion of it using ARROW_LEFT and DELETE.
     *  */
    @org.junit.Test
    public void sendKeysDeleteTest() throws Exception {
        app.goToTextEditScreen();
        String expectedText = "You.i";
        String deletedText = " Engine";
        String sentText = expectedText + deletedText;

        app.textEditScreen.setTextEditValue(sentText);
        utils.delayInSeconds(2);
        Assert.assertEquals(sentText, app.textEditScreen.getTextEditValue());

        // move the cursor to the front of the text to be deleted
        for (int i = 0; i < deletedText.length(); ++i) {
            app.textEditScreen.getTextEdit().sendKeys(Keys.ARROW_LEFT);
        }
        utils.delayInSeconds(2);
        Assert.assertEquals(sentText, app.textEditScreen.getTextEditValue());

        for (int i = 0; i < deletedText.length(); ++i) {
            app.textEditScreen.getTextEdit().sendKeys(Keys.DELETE);
        }
        Assert.assertEquals(expectedText, app.textEditScreen.getTextEditValue());
    }

    @org.junit.Test
    public void lockDeviceTest() throws Exception {
        driver.lockFor(5);

        boolean result = true;
        try {
            app.goToButtonsScreen();
        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }
        Assert.assertTrue(result);
    }

    @org.junit.Test
    public void isLockedTest() throws Exception {
        boolean result = false;

        try {
            Assert.assertFalse(driver.isLocked()); // should not be locked
            driver.lock();
            utils.delayInSeconds(5);
            Assert.assertTrue(driver.isLocked()); // should now be locked
            // at this point we don't need to unlock
        } catch (NoSuchMethodException nsmEx) {
            result = isAndroid ? false : true; // expected for iOS
        }
        Assert.assertTrue(result);
    }

    private void outputException(Exception ex, String message) {
        System.out.println("\n" + message + "\nClass: " + ex.getClass() + "\nMessage: "
                + ex.getMessage() + "\nCause: " + ex.getCause() + "\nStackTrace: "
                + ex.getStackTrace());
    }
}
