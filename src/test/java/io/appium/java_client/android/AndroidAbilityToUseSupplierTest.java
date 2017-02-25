package io.appium.java_client.android;

import static org.junit.Assert.assertNotEquals;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.functions.ActionSupplier;
import org.junit.Test;
import org.openqa.selenium.Point;

import java.util.List;

public class AndroidAbilityToUseSupplierTest extends BaseAndroidTest {

    private final ActionSupplier<TouchAction> horizontalSwipe = () -> {
        driver.findElementById("io.appium.android.apis:id/gallery");

        AndroidElement gallery = driver.findElementById("io.appium.android.apis:id/gallery");
        List<MobileElement> images = gallery
                .findElementsByClassName("android.widget.ImageView");
        Point location = gallery.getLocation();
        Point center = gallery.getCenter();

        return new TouchAction(driver).press(images.get(2), -10, center.y - location.y)
                .waitAction(2000).moveTo(gallery, 10, center.y - location.y).release();
    };

    private final ActionSupplier<TouchAction> verticalSwiping = () ->
        new TouchAction(driver).press(driver.findElementByAccessibilityId("Gallery"))
                .waitAction(2000).moveTo(driver.findElementByAccessibilityId("Auto Complete")).release();

    @Test public void horizontalSwipingWithSupplier() throws Exception {
        Activity activity = new Activity("io.appium.android.apis", ".view.Gallery1");
        driver.startActivity(activity);
        AndroidElement gallery = driver.findElementById("io.appium.android.apis:id/gallery");
        List<MobileElement> images = gallery
                .findElementsByClassName("android.widget.ImageView");
        int originalImageCount = images.size();

        horizontalSwipe.get().perform();

        assertNotEquals(originalImageCount, gallery
                .findElementsByClassName("android.widget.ImageView").size());
    }

    @Test public void verticalSwipingWithSupplier() throws Exception {
        driver.resetApp();
        driver.findElementByAccessibilityId("Views").click();

        Point originalLocation = driver.findElementByAccessibilityId("Gallery").getLocation();
        verticalSwiping.get().perform();
        Thread.sleep(5000);
        assertNotEquals(originalLocation, driver.findElementByAccessibilityId("Gallery").getLocation());
    }
}
