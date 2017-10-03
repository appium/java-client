package io.appium.java_client.ios;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IOSElementTest extends UICatalogIOSTest {

    @Test public void findByAccessibilityIdTest() {
        assertThat((driver.findElementsByClassName("UIAWindow").get(1))
                        .findElementsByAccessibilityId("Sliders").size(),
                not(is(0)));
    }

    @Test public void setValueTest() {
        driver.findElementsByClassName("UIAWindow").get(1)
                .findElementByAccessibilityId("Sliders").click();

        WebDriverWait wait = new WebDriverWait(driver, 20);

        IOSElement slider = (IOSElement) wait.until(input -> {
            List<WebElement> result = input.findElements(By.className("UIASlider"));
            if (result.size() == 0) {
                return null;
            }
            return result;
        }).get(1);
        slider.setValue("0%");
        assertEquals("0%", slider.getAttribute("value"));
    }
}
