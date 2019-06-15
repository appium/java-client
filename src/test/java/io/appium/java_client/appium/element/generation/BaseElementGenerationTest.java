package io.appium.java_client.appium.element.generation;

import static org.junit.Assert.assertEquals;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

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

    protected boolean check(Supplier<DesiredCapabilities> serverCapabilitiesSupplier,
                         Supplier<Capabilities> clientCapabilitiesSupplier,
                         BiPredicate<By, Class<? extends WebElement>> filter,
                         By by, Class<? extends WebElement> clazz) {
        AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .withCapabilities(serverCapabilitiesSupplier.get());
        service = AppiumDriverLocalService.buildService(builder);
        driver = new AppiumDriver<>(service, clientCapabilitiesSupplier.get());
        return filter.test(by, clazz);
    }

}
