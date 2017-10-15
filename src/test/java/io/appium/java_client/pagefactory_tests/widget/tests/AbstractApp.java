package io.appium.java_client.pagefactory_tests.widget.tests;

import java.util.List;

public interface AbstractApp {

    <T extends DefaultStubWidget> T getWidget();

    <T extends DefaultStubWidget> List<T> getWidgets();

}
