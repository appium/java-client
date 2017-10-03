package io.appium.java_client.pagefactory_tests.widget.tests;

import java.util.List;

public interface ExtendedApp extends AbstractApp {

    <T extends DefaultStubWidget> T getAnnotatedWidget();

    <T extends DefaultStubWidget> List<T> getAnnotatedWidgets();

    <T extends DefaultStubWidget> T getExtendedWidget();

    <T extends DefaultStubWidget> List<T> getExtendedWidgets();

    <T extends DefaultStubWidget> T getExtendedWidgetWithOverriddenLocators();

    <T extends DefaultStubWidget> List<T> getExtendedWidgetsWithOverriddenLocators();
}
