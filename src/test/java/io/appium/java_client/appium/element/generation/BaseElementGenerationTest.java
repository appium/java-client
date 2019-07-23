package io.appium.java_client.appium.element.generation;

import static org.junit.Assert.assertEquals;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.junit.After;
import org.openqa.seleniumone.By;
import org.openqa.seleniumone.Capabilities;
import org.openqa.seleniumone.WebElement;

import java.util.function.BiPredicate;
import java.util.function.Supplier;

public class BaseElementGenerationTest {
    protected AppiumDriver<?> driver;
    private AppiumDriverLocalService service;

    protected final BiPredicate<By, Class<? extends WebElement>> commonPredicate = (by, aClass) -> {
        WebElement element = driver.findElement(by);
        assertEquals(element.getClass(), aClass);
        return true;
    };

    @After
    public void tearDown() {
        try {
            if (driver != null) {
                driver.quit();
            }
            if (service != null) {
                service.stop();
            }
        } finally {
            driver = null;
            service = null;
        }
    }

    protected boolean check(Supplier<Capabilities> capabilitiesSupplier,
                         BiPredicate<By, Class<? extends WebElement>> filter,
                         By by, Class<? extends WebElement> clazz) {
        service = AppiumDriverLocalService.buildDefaultService();
        driver = new AppiumDriver<>(service, capabilitiesSupplier.get());
        return filter.test(by, clazz);
    }

}
