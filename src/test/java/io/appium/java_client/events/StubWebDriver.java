package io.appium.java_client.events;

import com.google.common.collect.ImmutableList;

import io.appium.java_client.FindsByAccessibilityId;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.FindsByIosUIAutomation;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ContextAware;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.FindsByClassName;
import org.openqa.selenium.internal.FindsByCssSelector;
import org.openqa.selenium.internal.FindsById;
import org.openqa.selenium.internal.FindsByLinkText;
import org.openqa.selenium.internal.FindsByTagName;
import org.openqa.selenium.internal.FindsByXPath;
import org.openqa.selenium.logging.Logs;

import java.net.URL;
import java.util.List;
import java.util.Set;

public class StubWebDriver implements WebDriver, ContextAware, Rotatable, FindsByClassName,
    FindsByCssSelector, FindsById, FindsByLinkText, FindsByTagName, FindsByXPath,
    FindsByAccessibilityId<StubWebElement>, FindsByAndroidUIAutomator<StubWebElement>,
    FindsByIosUIAutomation<StubWebElement>, JavascriptExecutor {
    @Override public WebDriver context(String name) {
        return null;
    }

    @Override public Set<String> getContextHandles() {
        return null;
    }

    @Override public String getContext() {
        return StringUtils.EMPTY;
    }

    @Override public void rotate(ScreenOrientation orientation) {

    }

    @Override public ScreenOrientation getOrientation() {
        return null;
    }

    @Override public void get(String url) {

    }

    @Override public String getCurrentUrl() {
        return null;
    }

    @Override public String getTitle() {
        return null;
    }

    @Override public List<StubWebElement> findElements(By by) {
        return ImmutableList.of(new StubWebElement(), new StubWebElement());
    }

    @Override public StubWebElement findElement(By by) {
        return new StubWebElement();
    }

    @Override public String getPageSource() {
        return null;
    }

    @Override public void close() {

    }

    @Override public void quit() {

    }

    @Override public Set<String> getWindowHandles() {
        return null;
    }

    @Override public String getWindowHandle() {
        throw new RuntimeException("Test WebDriver exception");
    }

    @Override public TargetLocator switchTo() {
        return new StubTargetLocator(this);
    }

    @Override public Navigation navigate() {
        return new StubNavigation();
    }

    @Override public Options manage() {
        return new StubOptions();
    }

    @Override public StubWebElement findElementByClassName(String using) {
        return new StubWebElement();
    }

    @Override public List<StubWebElement> findElementsByClassName(String using) {
        return ImmutableList.of(new StubWebElement(), new StubWebElement());
    }

    @Override public StubWebElement findElementByCssSelector(String using) {
        return new StubWebElement();
    }

    @Override public List<StubWebElement> findElementsByCssSelector(String using) {
        return ImmutableList.of(new StubWebElement(), new StubWebElement());
    }

    @Override public StubWebElement findElementById(String using) {
        return new StubWebElement();
    }

    @Override public List<StubWebElement> findElementsById(String using) {
        return ImmutableList.of(new StubWebElement(), new StubWebElement());
    }

    @Override public StubWebElement findElementByLinkText(String using) {
        return new StubWebElement();
    }

    @Override public List<StubWebElement> findElementsByLinkText(String using) {
        return ImmutableList.of(new StubWebElement(), new StubWebElement());
    }

    @Override public StubWebElement findElementByPartialLinkText(String using) {
        return new StubWebElement();
    }

    @Override public List<StubWebElement> findElementsByPartialLinkText(String using) {
        return ImmutableList.of(new StubWebElement(), new StubWebElement());
    }

    @Override public StubWebElement findElementByTagName(String using) {
        return new StubWebElement();
    }

    @Override public List<StubWebElement> findElementsByTagName(String using) {
        return ImmutableList.of(new StubWebElement(), new StubWebElement());
    }

    @Override public StubWebElement findElementByXPath(String using) {
        return new StubWebElement();
    }

    @Override public List<StubWebElement> findElementsByXPath(String using) {
        return ImmutableList.of(new StubWebElement(), new StubWebElement());
    }

    @Override public StubWebElement findElementByAccessibilityId(String using) {
        return new StubWebElement();
    }

    @Override public List<StubWebElement> findElementsByAccessibilityId(String using) {
        return ImmutableList.of(new StubWebElement(), new StubWebElement());
    }

    @Override public StubWebElement findElementByAndroidUIAutomator(String using) {
        return new StubWebElement();
    }

    @Override public List<StubWebElement> findElementsByAndroidUIAutomator(String using) {
        return ImmutableList.of(new StubWebElement(), new StubWebElement());
    }

    @Override public StubWebElement findElementByIosUIAutomation(String using) {
        return new StubWebElement();
    }

    @Override public List<StubWebElement> findElementsByIosUIAutomation(String using) {
        return ImmutableList.of(new StubWebElement(), new StubWebElement());
    }

    @Override public Object executeScript(String script, Object... args) {
        return null;
    }

    @Override public Object executeAsyncScript(String script, Object... args) {
        return null;
    }

    private class StubTargetLocator implements TargetLocator {

        private final WebDriver driver;

        StubTargetLocator(WebDriver driver) {
            this.driver = driver;
        }

        @Override public WebDriver frame(int index) {
            return driver;
        }

        @Override public WebDriver frame(String nameOrId) {
            return driver;
        }

        @Override public WebDriver frame(WebElement frameElement) {
            return driver;
        }

        @Override public WebDriver parentFrame() {
            return driver;
        }

        @Override public WebDriver window(String nameOrHandle) {
            return driver;
        }

        @Override public WebDriver defaultContent() {
            return driver;
        }

        @Override public WebElement activeElement() {
            return new StubWebElement();
        }

        @Override public Alert alert() {
            return new StubAlert();
        }
    }

    private class StubOptions implements Options {

        @Override public void addCookie(Cookie cookie) {

        }

        @Override public void deleteCookieNamed(String name) {

        }

        @Override public void deleteCookie(Cookie cookie) {

        }

        @Override public void deleteAllCookies() {

        }

        @Override public Set<Cookie> getCookies() {
            return null;
        }

        @Override public Cookie getCookieNamed(String name) {
            return null;
        }

        @Override public Timeouts timeouts() {
            return null;
        }

        @Override public ImeHandler ime() {
            return null;
        }

        @Override public Window window() {
            return new StubWindow();
        }

        @Override public Logs logs() {
            return null;
        }
    }

    private class StubNavigation implements Navigation {

        @Override public void back() {

        }

        @Override public void forward() {

        }

        @Override public void to(String url) {

        }

        @Override public void to(URL url) {

        }

        @Override public void refresh() {

        }
    }
}
