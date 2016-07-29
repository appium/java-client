package io.appium.java_client.events;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;

public class ExtendedWebDriver implements WebDriver {



    @Override public void get(String url) {
        //STUB it does nothing
    }

    @Override public String getCurrentUrl() {
        return null;
    }

    @Override public String getTitle() {
        return null;
    }

    @Override public <T extends WebElement> List<T> findElements(By by) {
        return null;
    }

    @Override public StubAndroidElement findElement(By by) {
        return new StubAndroidElement();
    }

    @Override public String getPageSource() {
        return null;
    }

    @Override public void close() {
        //STUB it does nothing
    }

    @Override public void quit() {
        //STUB it does nothing
    }

    @Override public Set<String> getWindowHandles() {
        return null;
    }

    @Override public String getWindowHandle() {
        return null;
    }

    @Override public TargetLocator switchTo() {
        return null;
    }

    @Override public Navigation navigate() {
        return null;
    }

    @Override public Options manage() {
        return null;
    }
}
