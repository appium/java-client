package io.appium.java_client.pagefactory_tests.widget.tests;

import java.util.List;

public interface ExtendedApp extends AbstractApp {

    public abstract <T extends AbstractWidget> T getAnnotatedWidget();

    public abstract <T extends AbstractWidget> List<T> getAnnotatedWidgets();

    public abstract <T extends AbstractWidget> T getExtendedWidget();

    public abstract <T extends AbstractWidget> List<T> getExtendedWidgets();

    public abstract <T extends AbstractWidget> T getExtendedWidgetWithOverriddenLocators();

    public abstract <T extends AbstractWidget> List<T> getExtendedWidgetsWithOverriddenLocators();
}
