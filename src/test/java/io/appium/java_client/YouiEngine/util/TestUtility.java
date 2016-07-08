package io.appium.java_client.YouiEngine.util;

import org.openqa.selenium.WebElement;

import java.util.Iterator;
import java.util.List;

/**
 * This TestUtility is available to help test authors by wrapping common helper methods into a
 * single class which can be instantiated in their test classes.
 */
public class TestUtility {
    /** Causes a delay in the provided number of seconds on the calling thread. */
    public static void delayInSeconds(long delay) throws InterruptedException {
        delay = delay * 1000;
        Thread.sleep(delay);
    }

    /** This will print out the provided list to the console in a simple format. */
    public static void outputListContents(List<WebElement> listItems) {
        System.out.println("\nList Contents:");
        for (Iterator<WebElement> item = listItems.iterator(); item.hasNext(); ) {
            System.out.println("\tItem: " + item.next());
        }
    }
}
