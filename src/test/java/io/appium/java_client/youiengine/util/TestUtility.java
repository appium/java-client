package io.appium.java_client.youiengine.util;

import org.openqa.selenium.WebElement;

import java.util.Iterator;
import java.util.List;

/**
 * This TestUtility is available to help test authors by wrapping common helper methods into a
 * single class which can be instantiated in their test classes.
 */
public class TestUtility {
    /** Causes a delay in the provided number of seconds on the calling thread.
     * This practice of a hard set delay is not a good practice. The specified delay may work for
     * one device but not another, slower, device. This method is in place as a temporary work
     * around until an additional timeout option is added into the automation layer. */
    public static void delayInSeconds(long delay) {
        long delaySec = delay * 1000;
        long currentTime = System.currentTimeMillis();
        long startTime = currentTime;
        do {
            try {
                Thread.sleep(currentTime + delaySec - startTime);
            } catch (InterruptedException iEx) { }
            currentTime = System.currentTimeMillis();
        } while (currentTime < startTime + delaySec);
    }

    /** This will print out the provided list to the console in a simple format. */
    public static void outputListContents(List<WebElement> listItems) {
        System.out.println("\nList Contents:");
        for (Iterator<WebElement> item = listItems.iterator(); item.hasNext(); ) {
            System.out.println("\tItem: " + item.next());
        }
    }
}
