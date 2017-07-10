package io.appium.java_client.pagefactory_tests.widget.tests;

import java.util.List;

public interface ExtendedApp extends AbstractApp {

    <T extends AbstractWidget> T getAnnotatedWidget();

    <T extends AbstractWidget> List<T> getAnnotatedWidgets();

    <T extends AbstractWidget> T getExtendedWidget();

    <T extends AbstractWidget> List<T> getExtendedWidgets();

    <T extends AbstractWidget> T getExtendedWidgetWithOverriddenLocators();

    <T extends AbstractWidget> List<T> getExtendedWidgetsWithOverriddenLocators();
}
