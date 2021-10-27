package io.appium.java_client.android;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static java.time.Duration.ofSeconds;
import static org.junit.Assert.assertNotEquals;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.functions.ActionSupplier;
import io.appium.java_client.touch.offset.ElementOption;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;

import java.util.List;

public class AndroidAbilityToUseSupplierTest extends BaseAndroidTest {

    private final ActionSupplier<AndroidTouchAction> horizontalSwipe = () -> {
        driver.findElement(By.id("io.appium.android.apis:id/gallery"));

        AndroidElement gallery = driver.findElement(By.id("io.appium.android.apis:id/gallery"));
        List<MobileElement> images = gallery.findElements(AppiumBy.className("android.widget.ImageView"));
        Point location = gallery.getLocation();
        Point center = gallery.getCenter();

        ElementOption pressOption = element(images.get(2),-10,center.y - location.y);
        ElementOption moveOption = element(gallery, 10,center.y - location.y);

        return new AndroidTouchAction(driver)
                .press(pressOption)
                .waitAction(waitOptions(ofSeconds(2)))
                .moveTo(moveOption)
                .release();
    };

    private final ActionSupplier<AndroidTouchAction> verticalSwiping = () ->
        new AndroidTouchAction(driver)
                .press(element(driver.findElement(AppiumBy.accessibilityId("Gallery"))))

                .waitAction(waitOptions(ofSeconds(2)))

                .moveTo(element(driver.findElement(AppiumBy.accessibilityId("Auto Complete"))))
                .release();

    @Test public void horizontalSwipingWithSupplier() {
        Activity activity = new Activity("io.appium.android.apis", ".view.Gallery1");
        driver.startActivity(activity);
        AndroidElement gallery = driver.findElement(By.id("io.appium.android.apis:id/gallery"));
        List<MobileElement> images = gallery.findElements(AppiumBy.className("android.widget.ImageView"));
        int originalImageCount = images.size();

        horizontalSwipe.get().perform();

        assertNotEquals(originalImageCount,
                gallery.findElements(AppiumBy.className("android.widget.ImageView")).size());
    }

    @Test public void verticalSwipingWithSupplier() throws Exception {
        driver.resetApp();
        driver.findElement(AppiumBy.accessibilityId("Views")).click();

        Point originalLocation = driver.findElement(AppiumBy.accessibilityId("Gallery")).getLocation();
        verticalSwiping.get().perform();
        Thread.sleep(5000);
        assertNotEquals(originalLocation, driver.findElement(AppiumBy.accessibilityId("Gallery")).getLocation());
    }
}
