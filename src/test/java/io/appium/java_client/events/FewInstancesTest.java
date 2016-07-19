package io.appium.java_client.events;

import static com.thoughtworks.selenium.SeleneseTestCase.assertNotEquals;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import io.appium.java_client.events.listeners.AlertListener;
import io.appium.java_client.events.listeners.ContextListener;
import io.appium.java_client.events.listeners.ElementListener;
import io.appium.java_client.events.listeners.ExceptionListener;
import io.appium.java_client.events.listeners.JavaScriptListener;
import io.appium.java_client.events.listeners.NavigationListener;
import io.appium.java_client.events.listeners.RotationListener;
import io.appium.java_client.events.listeners.SearchingListener;
import io.appium.java_client.events.listeners.SingleListeners;
import io.appium.java_client.events.listeners.WindowListener;
import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class FewInstancesTest extends BaseListenerTest {

    private static EmptyWebDriver emptyWebDriver1;
    private static SearchingListener searchingListener1;
    private static NavigationListener navigationListener1;
    private static ElementListener elementListener1;
    private static JavaScriptListener javaScriptListener1;
    private static ExceptionListener exceptionListener1;
    private static AlertListener alertListener1;
    private static ContextListener contextListener1;
    private static RotationListener rotationListener1;
    private static WindowListener windowListener1;

    private static SearchingListener searchingListener2;
    private static NavigationListener navigationListener2;
    private static ElementListener elementListener2;
    private static JavaScriptListener javaScriptListener2;
    private static ExceptionListener exceptionListener2;
    private static AlertListener alertListener2;
    private static ContextListener contextListener2;
    private static RotationListener rotationListener2;
    private static WindowListener windowListener2;

    @BeforeClass public static void beforeClass() throws Exception {
        emptyWebDriver1 = new EmptyWebDriver();
        emptyWebDriver1 = EventFiringWebDriverFactory.getEventFiringWebDriver(emptyWebDriver1);

        searchingListener1 = (SearchingListener) SingleListeners
            .listeners.get(SearchingListener.class);
        navigationListener1 = (NavigationListener) SingleListeners
            .listeners.get(NavigationListener.class);
        elementListener1 = (ElementListener) SingleListeners
            .listeners.get(ElementListener.class);
        javaScriptListener1 = (JavaScriptListener) SingleListeners
            .listeners.get(JavaScriptListener.class);
        exceptionListener1 = (ExceptionListener) SingleListeners
            .listeners.get(ExceptionListener.class);
        alertListener1 = (AlertListener) SingleListeners
            .listeners.get(AlertListener.class);
        contextListener1 = (ContextListener) SingleListeners
            .listeners.get(ContextListener.class);
        rotationListener1 = (RotationListener) SingleListeners
            .listeners.get(RotationListener.class);
        windowListener1 =
            (WindowListener) SingleListeners.listeners.get(WindowListener.class);

        WebDriver stubWebDriver2 = new EmptyWebDriver();
        EventFiringWebDriverFactory.getEventFiringWebDriver(stubWebDriver2);

        searchingListener2 = (SearchingListener) SingleListeners
            .listeners.get(SearchingListener.class);
        navigationListener2 = (NavigationListener) SingleListeners
            .listeners.get(NavigationListener.class);
        elementListener2 = (ElementListener) SingleListeners
            .listeners.get(ElementListener.class);
        javaScriptListener2 = (JavaScriptListener) SingleListeners
            .listeners.get(JavaScriptListener.class);
        exceptionListener2 = (ExceptionListener) SingleListeners
            .listeners.get(ExceptionListener.class);
        alertListener2 = (AlertListener) SingleListeners
            .listeners.get(AlertListener.class);
        contextListener2 = (ContextListener) SingleListeners
            .listeners.get(ContextListener.class);
        rotationListener2 = (RotationListener) SingleListeners
            .listeners.get(RotationListener.class);
        windowListener2 =
            (WindowListener) SingleListeners.listeners.get(WindowListener.class);
    }

    @Test
    public void listenersAreDifferent() {
        assertNotEquals(searchingListener1, searchingListener2);
        assertNotEquals(elementListener1, elementListener2);
        assertNotEquals(navigationListener1, navigationListener2);
        assertNotEquals(javaScriptListener1, javaScriptListener2);
        assertNotEquals(exceptionListener1, exceptionListener2);
        assertNotEquals(alertListener1, alertListener2);
        assertNotEquals(contextListener1, contextListener2);
        assertNotEquals(rotationListener1, rotationListener2);
        assertNotEquals(windowListener1, windowListener2);
    }

    @Test
    public void assertThatOneDriverDoNotListensToAnother() {
        assertThat(super.assertThatSearchListenerWorks(emptyWebDriver1,
            searchingListener1, StringUtils.EMPTY),
            is(true));
        assertThat("The second searching listener should have no messages",
            searchingListener2.messages.size(), is(0));
    }

    @Test
    public void assertThatOneDriverDoNotListensToAnother2() {
        assertThat(super.assertThatSearchListenerWorksAgainstElements(emptyWebDriver1,
            searchingListener1, StringUtils.EMPTY),
            is(true));
        assertThat("The second searching listener should have no messages",
            searchingListener2.messages.size(), is(0));
    }

    @Test
    public void assertThatOneDriverDoNotListensToAnother3() throws Exception {
        assertThat(super.assertThatNavigationListenerWorks(emptyWebDriver1,
            navigationListener1, StringUtils.EMPTY),
            is(true));
        assertThat("The second navigation listener should have no messages",
            navigationListener2.messages.size(), is(0));
    }

    @Test
    public void assertThatOneDriverDoNotListensToAnother4() {
        assertThat(super.assertThatJavaScriptListenerWorks(emptyWebDriver1,
            javaScriptListener1, StringUtils.EMPTY),
            is(true));
        assertThat("The second java script listener should have no messages",
            javaScriptListener2.messages.size(), is(0));
    }

    @Test
    public void assertThatOneDriverDoNotListensToAnother5() {
        assertThat(super.assertThatExceptionListenerWorks(emptyWebDriver1,
            exceptionListener1, StringUtils.EMPTY),
            is(true));
        assertThat("The second exception listener should have no messages",
            exceptionListener2.messages.size(), is(0));
    }

    @Test
    public void assertThatOneDriverDoNotListensToAnother6() {
        assertThat(super.assertThatAlertListenerWorks(emptyWebDriver1,
            alertListener1, StringUtils.EMPTY),
            is(true));
        assertThat("The second alert listener should have no messages",
            alertListener2.messages.size(), is(0));
    }

    @Test
    public void assertThatOneDriverDoNotListensToAnother7() {
        assertThat(super.assertThatConrextListenerWorks(emptyWebDriver1,
            contextListener1, StringUtils.EMPTY),
            is(true));
        assertThat("The second context listener should have no messages",
            contextListener2.messages.size(), is(0));
    }

    @Test
    public void assertThatOneDriverDoNotListensToAnother8() {
        assertThat(super.assertThatRotationListenerWorks(emptyWebDriver1,
            rotationListener1, StringUtils.EMPTY),
            is(true));
        assertThat("The second rotation listener should have no messages",
            rotationListener2.messages.size(), is(0));
    }

    @Test
    public void assertThatOneDriverDoNotListensToAnother9() {
        assertThat(super.assertThatWindowListenerWorks(emptyWebDriver1,
            windowListener1, StringUtils.EMPTY),
            is(true));
        assertThat("The second window listener should have no messages",
            windowListener2.messages.size(), is(0));
    }
}
