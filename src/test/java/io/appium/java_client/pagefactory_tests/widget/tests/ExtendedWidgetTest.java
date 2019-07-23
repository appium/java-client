package io.appium.java_client.pagefactory_tests.widget.tests;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.openqa.seleniumone.By;
import org.openqa.seleniumone.WebDriver;

import java.util.List;

public abstract class ExtendedWidgetTest extends WidgetTest {

    protected ExtendedWidgetTest(ExtendedApp app, WebDriver driver) {
        super(app, driver);
    }

    @Test
    public abstract void checkCaseWhenWidgetClassHasDeclaredLocatorAnnotation();

    @Test
    public abstract void checkCaseWhenWidgetClassHasNoDeclaredAnnotationButItHasSuperclass();

    @Test
    public abstract void checkCaseWhenBothWidgetFieldAndClassHaveDelaredAnnotations();

    protected static void checkThatLocatorsAreCreatedCorrectly(DefaultStubWidget single,
                                                               List<DefaultStubWidget> multiple, By rootLocator,
                                                               By subLocator) {

        assertThat(single.toString(), containsString(rootLocator.toString()));
        assertThat(multiple.stream().map(DefaultStubWidget::toString).collect(toList()),
                contains(containsString(rootLocator.toString()),
                        containsString(rootLocator.toString())));

        assertThat(single.getSubWidget().toString(), containsString(subLocator.toString()));
        assertThat(single.getSubWidgets().stream().map(Object::toString).collect(toList()),
                contains(containsString(subLocator.toString()),
                        containsString(subLocator.toString())));

        assertThat(multiple.stream().map(abstractWidget -> abstractWidget.getSubWidget().toString()).collect(toList()),
                contains(containsString(subLocator.toString()),
                        containsString(subLocator.toString())));
    }
}
