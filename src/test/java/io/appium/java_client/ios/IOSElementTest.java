package io.appium.java_client.ios;

import static java.time.Duration.ofSeconds;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import io.appium.java_client.selenium.By;
import io.appium.java_client.selenium.support.ui.WebDriverWait;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IOSElementTest extends AppIOSTest {

    @Test
    public void findByAccessibilityIdTest() {
        assertThat(driver.findElementsByAccessibilityId("Compute Sum").size(),
                not(is(0)));
    }

    // FIXME: Stabilize the test on CI
    @Ignore
    @Test
    public void setValueTest() {
        WebDriverWait wait = new WebDriverWait(driver, ofSeconds(20));

        IOSElement slider = wait.until(
                driver1 -> driver1.findElement(By.className("XCUIElementTypeSlider")));
        slider.setValue("0%");
        assertEquals("0%", slider.getAttribute("value"));
    }
}
