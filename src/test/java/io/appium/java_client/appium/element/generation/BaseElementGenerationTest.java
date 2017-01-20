package io.appium.java_client.appium.element.generation;

import static org.junit.Assert.assertTrue;

import io.appium.java_client.AppiumDriver;
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
    protected final BiPredicate<By, Class<? extends WebElement>> commonPredicate = (by, aClass) -> {
        WebElement element = driver.findElement(by);
        assertTrue(element.getClass().equals(aClass));
        return true;
    };

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        driver = null;
    }

    protected boolean check(Supplier<DesiredCapabilities> serverCapabilitiesSupplier,
                         Supplier<Capabilities> clientCapabilitiesSupplier,
                         BiPredicate<By, Class<? extends WebElement>> filter,
                         By by, Class<? extends WebElement> clazz) {
        AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .withCapabilities(serverCapabilitiesSupplier.get());
        driver = new AppiumDriver<>(builder, clientCapabilitiesSupplier.get());
        return filter.test(by, clazz);
    }

}
