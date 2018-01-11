package io.appium.java_client.events;

import io.appium.java_client.events.api.Listener;
import org.openqa.selenium.WebDriver;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;

public class EventFiringObjectFactory {

    /**
     * This method makes an event firing object.
     *
     * @param t an original {@link Object} that is
     *               supposed to be listenable
     * @param driver an instance of {@link org.openqa.selenium.WebDriver}
     * @param listeners is a collection of {@link io.appium.java_client.events.api.Listener} that
     *                  is supposed to be used for the event firing
     * @param <T> T
     * @return an {@link Object} that fires events
     */
    @SuppressWarnings("unchecked")
    public static <T> T getEventFiringObject(T t, WebDriver driver, Collection<Listener> listeners) {
        final List<Listener> listenerList = new ArrayList<>();

        for (Listener listener : ServiceLoader.load(
                Listener.class)) {
            listenerList.add(listener);
        }

        listeners.stream().filter(listener -> !listenerList.contains(listener)).forEach(listenerList::add);

        AbstractApplicationContext context = new AnnotationConfigApplicationContext(
                DefaultBeanConfiguration.class);
        return (T) context.getBean(
                DefaultBeanConfiguration.LISTENABLE_OBJECT, t, driver, listenerList, context);
    }

    /**
     * This method makes an event firing object.
     *
     * @param t an original {@link Object} that is
     *               supposed to be listenable
     * @param driver an instance of {@link org.openqa.selenium.WebDriver}
     * @param <T> T
     * @return an {@link Object} that fires events
     */
    public static <T> T getEventFiringObject(T t, WebDriver driver) {
        return getEventFiringObject(t, driver, Collections.<Listener>emptyList());
    }

    /**
     * This method makes an event firing object.
     *
     * @param t an original {@link Object} that is
     *               supposed to be listenable
     * @param driver an instance of {@link org.openqa.selenium.WebDriver}
     * @param listeners is an array of {@link io.appium.java_client.events.api.Listener} that
     *                  is supposed to be used for the event firing
     *
     * @param <T> T
     * @return an instance of {@link org.openqa.selenium.WebDriver} that fires events
     */
    public static <T> T getEventFiringObject(T t, WebDriver driver, Listener ... listeners) {
        return getEventFiringObject(t, driver, Arrays.asList(listeners));
    }
}
