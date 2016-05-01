package io.appium.java_client.pagefactory_tests;


import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Set;

public class GenericTest {

    static class TempGenericPage<T> {

        public List<T> items;

        public List<T> getItems() {
            return items;
        }
    }

    static class MockWebDriver implements WebDriver {

        @Override
        public void get(String url) {

        }

        @Override
        public String getCurrentUrl() {
            return null;
        }

        @Override
        public String getTitle() {
            return null;
        }

        @Override
        public List<WebElement> findElements(By by) {
            return null;
        }

        @Override
        public WebElement findElement(By by) {
            return null;
        }

        @Override
        public String getPageSource() {
            return null;
        }

        @Override
        public void close() {

        }

        @Override
        public void quit() {

        }

        @Override
        public Set<String> getWindowHandles() {
            return null;
        }

        @Override
        public String getWindowHandle() {
            return null;
        }

        @Override
        public TargetLocator switchTo() {
            return null;
        }

        @Override
        public Navigation navigate() {
            return null;
        }

        @Override
        public Options manage() {
            return null;
        }
    }

    @Test
    public void genericTestCse() {
        PageFactory
                .initElements(new AppiumFieldDecorator(new MockWebDriver()),
                        new TempGenericPage<>());
    }
}