package io.appium.java_client.ios;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IOSElementTest extends AppIOSTest {

    @Test public void findByAccessibilityIdTest() {
        assertThat(driver.findElementsByAccessibilityId("Compute Sum").size(),
                not(is(0)));
    }

    //    @Test
    //    FIXME: Stabilize the test on CI
    //    public void setValueTest() {
    //        WebDriverWait wait = new WebDriverWait(driver, 20);
    //
    //        IOSElement slider = wait.until(driver1 -> driver1.findElement(By.className("XCUIElementTypeSlider")));
    //        slider.setValue("0%");
    //        assertEquals("0%", slider.getAttribute("value"));
    //    }
}
