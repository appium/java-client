package io.appium.java_client.pagefactory_tests;

import static org.junit.Assert.assertTrue;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.junit.Test;
import org.openqa.seleniumone.By;
import org.openqa.seleniumone.WebDriver;
import org.openqa.seleniumone.WebElement;
import org.openqa.seleniumone.support.PageFactory;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

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
            System.out.print(url);
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
            System.out.print("Closed");
        }

        @Override
        public void quit() {
            System.out.print("Died");
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
        Supplier<Boolean> result = () -> {
            PageFactory
                    .initElements(new AppiumFieldDecorator(new MockWebDriver()),
                            new TempGenericPage<>());
            return true;
        };
        assertTrue(result.get());
    }
}