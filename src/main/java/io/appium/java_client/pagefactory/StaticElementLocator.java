package io.appium.java_client.pagefactory;

import io.appium.java_client.support.pagefactory.ElementLocator;
import org.openqa.selenium.WebElement;

import java.util.List;

final class StaticElementLocator implements ElementLocator {
    private final WebElement element;

    StaticElementLocator(WebElement element) {
        this.element = element;
    }

    @Override
    public WebElement findElement() {
        return element;
    }

    @Override
    public List<WebElement> findElements() {
        return List.of(element);
    }

    @Override
    public String toString() {
        return element.toString();
    }
}
