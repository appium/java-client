package io.appium.java_client.android;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.functions.ActionSupplier;
import io.appium.java_client.touch.offset.ElementOption;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import java.util.List;

import static io.appium.java_client.TestUtils.getCenter;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AndroidAbilityToUseSupplierTest extends BaseAndroidTest {

    private final ActionSupplier<AndroidTouchAction> horizontalSwipe = () -> {
        driver.findElement(By.id("io.appium.android.apis:id/gallery"));

        WebElement gallery = driver.findElement(By.id("io.appium.android.apis:id/gallery"));
        List<WebElement> images = gallery.findElements(AppiumBy.className("android.widget.ImageView"));
        Point location = gallery.getLocation();
        Point center = getCenter(gallery, location);

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
        WebElement gallery = driver.findElement(By.id("io.appium.android.apis:id/gallery"));
        List<WebElement> images = gallery.findElements(AppiumBy.className("android.widget.ImageView"));
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
