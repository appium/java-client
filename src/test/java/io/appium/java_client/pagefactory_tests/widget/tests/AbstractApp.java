package io.appium.java_client.pagefactory_tests.widget.tests;

import java.util.List;

public abstract class AbstractApp {

    public abstract <T extends AbstractWidget> T getWidget();

    public abstract <T extends AbstractWidget> List<T> getWidgets();

    public abstract <T extends AbstractWidget> T getAnnotatedWidget();

    public abstract <T extends AbstractWidget> List<T> getAnnotatedWidgets();

    public abstract <T extends AbstractWidget> T getExtendedWidget();

    public abstract <T extends AbstractWidget> List<T> getExtendedWidgets();

    public abstract <T extends AbstractWidget> T getExtendedWidgetWithOverriddenLocators();

    public abstract <T extends AbstractWidget> List<T> getExtendedWidgetsWithOverriddenLocators();
}
