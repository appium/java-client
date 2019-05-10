package io.appium.java_client.events;

import static com.google.common.collect.ImmutableSortedSet.of;
import static io.appium.java_client.events.EventFiringObjectFactory.getEventFiringObject;
import static io.appium.java_client.events.EventFiringWebDriverFactory.getEventFiringWebDriver;
import static io.appium.java_client.events.listeners.SingleListeners.listeners;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import io.appium.java_client.events.listeners.AlertListener;
import io.appium.java_client.events.listeners.AlertListener2;
import io.appium.java_client.events.listeners.ContextListener;
import io.appium.java_client.events.listeners.ContextListener2;
import io.appium.java_client.events.listeners.SearchingListener;
import io.appium.java_client.events.listeners.SearchingListener2;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ContextAware;
import org.openqa.selenium.WebDriver;

import java.util.Set;
import java.util.function.Predicate;

public class ListenableObjectTest {
    private static final String PREFIX = "Externally defined listener: ";

    private final EmptyWebDriver emptyWebDriver = new EmptyWebDriver();
    private final ContextListener2 contextListener = new ContextListener2();
    private final AlertListener2 alertListener = new AlertListener2();
    private final SearchingListener2 searchingListener = new SearchingListener2();

    private final ContextAware contextAware = new ContextAware() {
        @Override
        public WebDriver context(String name) {
            return emptyWebDriver;
        }

        @Override
        public Set<String> getContextHandles() {
            return of(EMPTY);
        }

        @Override
        public String getContext() {
            return EMPTY;
        }
    };

    private final Predicate<ContextAware> contextAwarePredicate = (contextAware) -> {
        contextAware.context("WEB_VIEW");

        assertThat(contextListener.messages,
                contains(PREFIX + "Attempt to change current context to NATIVE_APP",
                        PREFIX + "The previous context has been changed to NATIVE_APP",
                        PREFIX + "Attempt to change current context to WEB_VIEW",
                        PREFIX + "The previous context has been changed to WEB_VIEW"));

        ContextListener singleContextListener = (ContextListener)
                listeners.get(ContextListener.class);
        assertThat(singleContextListener.messages,
                contains("Attempt to change current context to NATIVE_APP",
                        "The previous context has been changed to NATIVE_APP",
                        "Attempt to change current context to WEB_VIEW",
                        "The previous context has been changed to WEB_VIEW"));
        return true;
    };

    private final Predicate<Alert> alertPredicate = alert -> {
        alert.accept();
        alert.dismiss();
        alert.sendKeys("Keys");

        assertThat(alertListener.messages,
                contains(PREFIX + "Attempt to accept alert",
                        PREFIX + "The alert was accepted",
                        PREFIX + "Attempt to dismiss alert",
                        PREFIX + "The alert was dismissed",
                        PREFIX + "Attempt to send keys to alert",
                        PREFIX + "Keys were sent to alert"));

        AlertListener singleAlertListener = (AlertListener)
                listeners.get(AlertListener.class);

        assertThat(singleAlertListener.messages,
                contains("Attempt to accept alert",
                        "The alert was accepted",
                        "Attempt to dismiss alert",
                        "The alert was dismissed",
                        "Attempt to send keys to alert",
                        "Keys were sent to alert"));
        return true;
    };

    private final Predicate<WebDriver> webDriverPredicate = driver -> {
        driver.findElement(By.id("someId"));
        assertThat(searchingListener.messages,
                contains(PREFIX + "Attempt to find something using By.id: someId. The root element is null",
                        PREFIX + "The searching for something using By.id: someId has beed finished. "
                                + "The root element was null"));

        SearchingListener singleSearchingListener = (SearchingListener)
                listeners.get(SearchingListener.class);

        assertThat(singleSearchingListener.messages,
                contains("Attempt to find something using By.id: someId. The root element is null",
                        "The searching for something using By.id: someId has beed finished. "
                                + "The root element was null"));
        return true;
    };


    @Test public void listenableObjectSample() {
        try {
            ContextAware listenableContextAware =
                    getEventFiringObject(contextAware, emptyWebDriver, contextListener, alertListener);
            WebDriver webDriver = listenableContextAware.context("NATIVE_APP");
            assertTrue(contextAwarePredicate.test(listenableContextAware));

            Alert alert = webDriver.switchTo().alert();
            assertTrue(alertPredicate.test(alert));

            assertTrue(webDriverPredicate.test(getEventFiringWebDriver(webDriver, searchingListener)));
        } finally {
            listeners.get(ContextListener.class).messages.clear();
            listeners.get(AlertListener.class).messages.clear();
            listeners.get(SearchingListener.class).messages.clear();
        }
    }
}
