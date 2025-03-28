package io.appium.java_client.android;

import io.appium.java_client.functions.AppiumFunction;
import io.appium.java_client.functions.ExpectedCondition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.appium.java_client.HasBrowserCheck.NATIVE_CONTEXT;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AndroidFunctionTest extends BaseAndroidTest {

    private final AppiumFunction<WebDriver, List<WebElement>> searchingFunction = input -> {
        List<WebElement> result = input.findElements(By.tagName("a"));

        if (result.size() > 0) {
            return result;
        }
        return null;
    };

    private final AppiumFunction<Pattern, WebDriver> contextFunction = input -> {
        Set<String> contexts = driver.getContextHandles();
        String current = driver.getContext();
        contexts.forEach(context -> {
            Matcher m = input.matcher(context);
            if (m.find()) {
                driver.context(context);
            }
        });
        if (!current.equals(driver.getContext())) {
            return driver;
        }
        return null;
    };

    private final AppiumFunction<List<WebElement>, List<WebElement>> filteringFunction = input -> {
        final List<WebElement> result = new ArrayList<>();
        input.forEach(element -> {
            if (element.getText().equals("Hello World! - 1")) {
                result.add(element);
            }
        });

        if (result.size() > 0) {
            return result;
        }
        return null;
    };

    @BeforeAll
    public static void startWebViewActivity() {
        if (driver != null) {
            startActivity(".view.WebView1");
        }
    }

    @BeforeEach
    public void setUp() {

        driver.context(NATIVE_CONTEXT);
    }

    @Test
    public void complexWaitingTestWithPreCondition() {
        AppiumFunction<Pattern, List<WebElement>> compositeFunction =
                searchingFunction.compose(contextFunction);

        Wait<Pattern> wait = new FluentWait<>(Pattern.compile("WEBVIEW"))
                .withTimeout(ofSeconds(30));
        List<WebElement> elements = wait.until(compositeFunction);

        assertThat("Element size should be 1", elements.size(), is(1));
        assertThat("WebView is expected", driver.getContext(), containsString("WEBVIEW"));
    }

    @Test public void complexWaitingTestWithPostConditions() {
        final List<Boolean> calls = new ArrayList<>();

        AppiumFunction<Pattern, WebDriver> waitingForContext = input -> {
            WebDriver result = contextFunction.apply(input);
            if (result != null) {
                calls.add(true);
            }
            return result;
        };

        AppiumFunction<Pattern, List<WebElement>> compositeFunction = waitingForContext
                .andThen((ExpectedCondition<List<WebElement>>) input -> {
                    List<WebElement> result = searchingFunction.apply(input);
                    if (result != null) {
                        calls.add(true);
                    }
                    return result;
                })
                .andThen((AppiumFunction<List<WebElement>, List<WebElement>>) input -> {
                    List<WebElement> result = filteringFunction.apply(input);
                    if (result != null) {
                        calls.add(true);
                    }
                    return result;
                });

        Wait<Pattern> wait = new FluentWait<>(Pattern.compile("WEBVIEW"))
                .withTimeout(ofSeconds(30));
        List<WebElement> elements = wait.until(compositeFunction);
        assertThat("Element size should be 1", elements.size(), is(1));
        assertThat("WebView is expected", driver.getContext(), containsString("WEBVIEW"));
        assertThat("There should be 3 calls", calls.size(), is(3));
    }

    @Test
    public void nullPointerExceptionSafetyTestWithPrecondition() {
        Wait<Pattern> wait = new FluentWait<>(Pattern.compile("Fake_context"))
                .withTimeout(ofSeconds(30)).pollingEvery(ofMillis(500));
        assertThrows(TimeoutException.class, () -> wait.until(searchingFunction.compose(contextFunction)));
    }

    @Test
    public void nullPointerExceptionSafetyTestWithPostConditions() {
        Wait<Pattern> wait = new FluentWait<>(Pattern.compile("Fake_context"))
                .withTimeout(ofSeconds(30)).pollingEvery(ofMillis(500));
        assertThrows(TimeoutException.class,
                () -> wait.until(contextFunction.andThen(searchingFunction).andThen(filteringFunction)));
    }
}
