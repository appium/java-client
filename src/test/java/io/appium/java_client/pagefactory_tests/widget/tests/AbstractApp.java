package io.appium.java_client.pagefactory_tests.widget.tests;

import java.util.List;

public interface AbstractApp {

    <T extends AbstractWidget> T getWidget();

    <T extends AbstractWidget> List<T> getWidgets();

}
