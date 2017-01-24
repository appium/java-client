package io.appium.java_client.events;

import static com.google.common.collect.ImmutableSortedSet.of;
import static io.appium.java_client.events.EventFiringObjectFactory.getEventFiringObject;
import static io.appium.java_client.events.EventFiringWebDriverFactory.getEventFiringWebDriver;
import static io.appium.java_client.events.listeners.SingleListeners.listeners;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

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
import org.openqa.selenium.security.Credentials;

import java.util.Set;

public class ListenableObjectTest {
    private static final String PREFIX = "Externally defined listener: ";

    @Test public void listenableObjectSample() {
        try {
            EmptyWebDriver emptyWebDriver = new EmptyWebDriver();
            ContextAware contextAware = new ContextAware() {
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

            ContextListener2 contextListener = new ContextListener2();
            AlertListener2 alertListener = new AlertListener2();
            contextAware = getEventFiringObject(contextAware, emptyWebDriver, contextListener, alertListener);

            EmptyWebDriver webDriver = (EmptyWebDriver) contextAware.context("NATIVE_APP");
            contextAware.context("WEB_VIEW");
            assertNotNull(webDriver);

            assertThat(contextListener.messages,
                    hasItems(PREFIX + "Attempt to change current context to NATIVE_APP",
                            PREFIX + "The previous context has been changed to NATIVE_APP",
                            PREFIX + "Attempt to change current context to WEB_VIEW",
                            PREFIX + "The previous context has been changed to WEB_VIEW"));
            assertThat(contextListener.messages.size(), is(4));

            ContextListener singleContextListener = (ContextListener) listeners.get(ContextListener.class);

            assertThat(singleContextListener.messages,
                    hasItems("Attempt to change current context to NATIVE_APP",
                            "The previous context has been changed to NATIVE_APP",
                            "Attempt to change current context to WEB_VIEW",
                            "The previous context has been changed to WEB_VIEW"));
            assertThat(singleContextListener.messages.size(), is(4));

            Alert alert = webDriver.switchTo().alert();
            alert.accept();
            alert.dismiss();
            alert.sendKeys("Keys");
            Credentials credentials = new Credentials() {
                @Override
                public int hashCode() {
                    return super.hashCode();
                }

                @Override
                public String toString() {
                    return "Test credentials 1";
                }
            };

            Credentials credentials2 = new Credentials() {
                @Override
                public int hashCode() {
                    return super.hashCode();
                }

                @Override
                public String toString() {
                    return "Test credentials 2";
                }
            };

            alert.setCredentials(credentials);
            alert.authenticateUsing(credentials2);

            assertThat(alertListener.messages,
                    hasItems(PREFIX + "Attempt to accept alert",
                            PREFIX + "The alert was accepted",
                            PREFIX + "Attempt to dismiss alert",
                            PREFIX + "The alert was dismissed",
                            PREFIX + "Attempt to send keys to alert",
                            PREFIX + "Keys were sent to alert",
                            PREFIX + "Attempt to send credentials " + credentials.toString() + " to alert",
                            PREFIX + "Credentials " + credentials.toString() + " were sent to alert",
                            PREFIX + "Attempt to send credentials " + credentials2.toString() + " to alert",
                            PREFIX + "Credentials " + credentials2.toString() + " were sent to alert"));
            assertThat(alertListener.messages.size(), is(10));

            AlertListener singleAlertListener = (AlertListener) listeners.get(AlertListener.class);
            assertThat(singleAlertListener.messages,
                    hasItems("Attempt to accept alert",
                            "The alert was accepted",
                            "Attempt to dismiss alert",
                            "The alert was dismissed",
                            "Attempt to send keys to alert",
                            "Keys were sent to alert",
                            "Attempt to send credentials " + credentials.toString() + " to alert",
                            "Credentials " + credentials.toString() + " were sent to alert",
                            "Attempt to send credentials " + credentials2.toString() + " to alert",
                            "Credentials " + credentials2.toString() + " were sent to alert"));
            assertThat(singleAlertListener.messages.size(), is(10));

            SearchingListener2 searchingListener = new SearchingListener2();
            webDriver = getEventFiringWebDriver(webDriver, searchingListener);

            webDriver.findElement(By.id("someId"));
            assertThat(searchingListener.messages,
                    hasItems(PREFIX + "Attempt to find something using By.id: someId. The root element is null",
                            PREFIX + "The searching for something using By.id: someId has beed finished. "
                                    + "The root element was null"));
            assertThat(searchingListener.messages.size(), is(2));

            SearchingListener singleSearchingListener = (SearchingListener)
                    listeners.get(SearchingListener.class);
            assertThat(singleSearchingListener.messages,
                    hasItems("Attempt to find something using By.id: someId. The root element is null",
                            "The searching for something using By.id: someId has beed finished. "
                                    + "The root element was null"));
            assertThat(singleSearchingListener.messages.size(), is(2));
        } finally {
            listeners.get(ContextListener.class).messages.clear();
            listeners.get(AlertListener.class).messages.clear();
            listeners.get(SearchingListener.class).messages.clear();
        }
    }
}
