package io.appium.java_client.youiengine;

import static io.appium.java_client.android.AndroidKeyCode.KEYCODE_VOLUME_UP;
import static org.hamcrest.CoreMatchers.not;

import io.appium.java_client.android.Connection;
import io.appium.java_client.youiengine.util.AppiumTest;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
public class SanityTest extends AppiumTest {

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
        WebElement posterItem = null;
        try {
            posterItem = driver.findElement(By.className("CYIPushButtonView"));
        } catch (NoSuchElementException exception) {
            Assert.fail("Did not find the control.");
        }

        Assert.assertNotNull(posterItem);
    }

    // Confirm we can find an element using the name strategy.
    @org.junit.Test
    public void findElementByNameTest() throws Exception {
        WebElement posterList = null;
        try {
            posterList = driver.findElement(By.name("TextEdit"));
        } catch (NoSuchElementException exception) {
            Assert.fail("Did not find the control.");
        }

        Assert.assertNotNull(posterList);
    }

    // Confirm we throw a NoSuchElementException when the element was not found.
    @org.junit.Test
    public void findElementNotFoundTest() throws Exception {
        boolean exceptionThrown = false;
        WebElement posterItem = null;

        try {
            posterItem = driver.findElement(By.className("DoesNotExistView"));
        } catch (NoSuchElementException exception) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);
        Assert.assertNull(posterItem);
    }

    // Confirm we can find multiple elements using the class name strategy.
    @org.junit.Test
    public void findMultipleElementsTest() throws Exception {
        int expectedCount = 4;
        List<WebElement> atlasTextSceneViewList = driver
                .findElementsByClassName("CYIAtlasTextSceneNode");
        utils.outputListContents(atlasTextSceneViewList);

        Assert.assertEquals(atlasTextSceneViewList.size(), expectedCount);
    }

    /* Confirm we can perform a relative find. The parent is found using the class name strategy
     * and the child is found using the name strategy. */
    @org.junit.Test
    public void findSingleElementFromElementTest() throws Exception {
        WebElement buttonView = driver.findElement(By.className("CYITextEditView"));
        WebElement childItem = buttonView.findElement(By.name("Text"));

        String foundText = childItem.getText();
        String expectedText = "TextEdit";
        Assert.assertEquals(expectedText, foundText);
    }

    /* Confirm we can perform a relative find resulting in multiple elements. The parent is found
     * using the class name strategy and its children are also found using the class name strategy.
     * */
    @org.junit.Test
    public void findMultipleElementsFromElementTest() throws Exception {
        WebElement listContainer = driver.findElement(By.className("CYIScreenView"));
        List<WebElement> labelItems = listContainer.findElements(
                By.className("CYIAtlasTextSceneNode"));
        utils.outputListContents(labelItems);

        int expectedCount = 4;
        int actualCount = labelItems.size();
        Assert.assertEquals(expectedCount, actualCount);
    }

    // Confirm we get the text of a button.
    @org.junit.Test
    public void getTextTest() throws Exception {
        WebElement pushButton = driver.findElement(By.className("CYIPushButtonView"));

        String foundText = pushButton.findElement(By.name("Text")).getText();
        String expectedText = "Pushed 0 Times";
        Assert.assertEquals(expectedText, foundText);
    }

    // Confirm we can retrieve the name attribute of an input field.
    @org.junit.Test
    public void getNameTest() throws Exception {
        String expected = "TextEdit";

        WebElement inputField = driver.findElementByName(expected);
        String actual = inputField.getAttribute("name");

        Assert.assertEquals(expected, actual);
    }

    // Confirm we can set the text of an input field.
    @org.junit.Test
    public void valueSetTest() throws Exception {
        String expected = "One Two 3";
        WebElement field = driver.findElement(By.name("TextEdit"));
        field.sendKeys(expected);
        utils.delayInSeconds(2);

        String found = field.findElement(By.name("Text")).getText();
        Assert.assertEquals(expected, found);
    }

    //TODO create one with special keys to validate we support special keys

    // Confirm we can stop and then start up the app.
    @org.junit.Test
    public void startStopAppTest() throws Exception {
        driver.closeApp();
        utils.delayInSeconds(2);
        driver.launchApp();
        utils.delayInSeconds(2);
    }

    // Confirm we can send the app to the background for a short time.
    @org.junit.Test
    public void runInBackgroundTest() throws Exception {
        // TODO US-3491 - backgrounding our app closes the socket server
        if (driver.appPlatform.equals(driver.ANDROID)) {
            Assert.fail("US-3491 - backgrounding our app closes the socket server on Android.");
        }

        driver.runAppInBackground(10);
        try {
            WebElement pushButton = driver.findElement(By.name("PushButton"));
            pushButton.click();
        } catch (Exception ex) {
            Assert.fail("Failed to find or interact with the button after backgrounding the app. "
                    + "May not have restored properly.");
        }
    }
    //

    // Confirm we can toggle the Android device's location services.
    @org.junit.Test
    public void toggleLocationServicesTest() throws Exception {
        try {
            driver.toggleLocationServices(); // off
            utils.delayInSeconds(2);
            driver.toggleLocationServices(); // on
            utils.delayInSeconds(2);
        } catch (NoSuchMethodException nsmException) {
            if (!driver.appPlatform.equals(driver.IOS)) {
                Assert.fail("NoSuchMethodException was thrown when not on iOS.");
            } else {
                System.out.println("\nExpected exception was thrown.");
            }
        } catch (UnreachableBrowserException ubException) {
            Assert.fail("UnreachableBrowserException was thrown. Method may not work for the "
                    + "supplied device.");
        }
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
        boolean expected = true;
        Assert.assertEquals(expected, actual);
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
            Assert.fail("WebDriverException was thrown.");
        }
        boolean expected = true;
        Assert.assertEquals(expected, actual);
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
        boolean expected = true;
        Assert.assertEquals(expected, actual);
    }

    // Confirm we can press a key code
    @org.junit.Test
    public void pressKeyCodeTest() throws Exception {
        boolean actual = true;
        try {
            driver.pressKeyCode(KEYCODE_VOLUME_UP);
        } catch (WebDriverException wdException) {
            if (!driver.appPlatform.equals(driver.IOS)) {
                Assert.fail("WebDriverException was thrown when not on iOS.");
            } else {
                System.out.println("\nExpected exception was thrown.");
                actual = true; // did not get a value, but we threw an exception for iOS
            }
        }
        boolean expected = true;
        Assert.assertEquals(expected, actual);
    }

    // Confirm we can long press a key code
    @org.junit.Test
    public void longPressKeyCodeTest() throws Exception {
        boolean actual = true;
        try {
            driver.longPressKeyCode(KEYCODE_VOLUME_UP);
        } catch (WebDriverException wdException) {
            if (!driver.appPlatform.equals(driver.IOS)) {
                Assert.fail("WebDriverException was thrown when not on iOS.");
            } else {
                System.out.println("\nExpected exception was thrown.");
                actual = true; // did not get a value, but we threw an exception for iOS
            }
        }
        boolean expected = true;
        Assert.assertEquals(expected, actual);
    }

    /** Helper to test NetworkConnections. */
    public void NetworkConnectionTest(Connection stateToTest) throws Exception {
        try {
            Connection stateToSet;

            // Get initial state
            Connection statePrior = Connection.NONE;
            statePrior = driver.getConnection();
            Assert.assertNotEquals(Connection.NONE, statePrior);

            // Set state to Airplane
            Connection stateAfter = Connection.NONE;
            stateToSet = Connection.AIRPLANE;
            driver.setConnection(stateToSet);
            stateAfter = driver.getConnection();
            Assert.assertEquals(stateAfter, stateToSet);

            // Set state to test
            stateToSet = stateToTest;
            driver.setConnection(stateToSet);
            stateAfter = driver.getConnection();
            Assert.assertEquals(stateAfter, stateToSet);

            // Set state to initial state
            stateToSet = statePrior;
            driver.setConnection(stateToSet);
            stateAfter = driver.getConnection();
            Assert.assertEquals(stateAfter, stateToSet);

        } catch (WebDriverException wdException) {
            if (!driver.appPlatform.equals(driver.IOS)) {
                Assert.fail("WebDriverException was thrown when not on iOS.");
            } else {
                System.out.println("\nExpected exception was thrown.");
            }
        }
    }

    // Confirm we can set Network to WIFI only
    @org.junit.Test
    public void NetworkConnectionWiFiTest() throws Exception {
        NetworkConnectionTest(Connection.WIFI);
    }

    // Confirm we can set Network to DATA only
    @org.junit.Test
    public void NetworkConnectionDataTest() throws Exception {
        NetworkConnectionTest(Connection.DATA);
    }

    // Confirm we can set Network to ALL
    @org.junit.Test
    public void NetworkConnectionAllTest() throws Exception {
        NetworkConnectionTest(Connection.ALL);
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

        // Switch the orientation to Landscape
        driver.rotate(ScreenOrientation.LANDSCAPE);
        utils.delayInSeconds(2);

        currentOrientation =  driver.getOrientation();
        if (currentOrientation == ScreenOrientation.LANDSCAPE) {
            System.out.println("\nOrientation was Landscape.");
        } else {
            Assert.fail("\nOrientation was not Landscape.");
        }

        // Switch back to Portrait
        driver.rotate(ScreenOrientation.PORTRAIT);
        utils.delayInSeconds(2);

        currentOrientation =  driver.getOrientation();
        if (currentOrientation == ScreenOrientation.PORTRAIT) {
            System.out.println("\nOrientation was Portrait.");
        } else {
            Assert.fail("\nOrientation was not Portrait.");
        }
    }

    // Call the get app string and ensure we get something back.
    @org.junit.Test
    public void getStringsTest() throws Exception {
        Map appStringMap = driver.getAppStringMap();
        Assert.assertNotNull(appStringMap);
        System.out.println("\nMap: " + appStringMap.toString());
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
        driver.mobileShake();
    }


    // Regression - ensure no exceptions occur when sending a click to a CYIAtlasTextSceneNode.
    @org.junit.Test
    public void clickOnCyiTextAtlasTest() throws Exception {
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

    private void outputException(Exception ex, String message) {
        System.out.println("\n" + message + "\nClass: " + ex.getClass() + "\nMessage: "
                + ex.getMessage() + "\nCause: " + ex.getCause() + "\nStackTrace: "
                + ex.getStackTrace());
    }
}
