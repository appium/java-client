package io.appium.java_client.pagefactory_tests.widget.tests;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.openqa.selenium.support.PageFactory.initElements;

public abstract class WidgetTest {

    protected final AbstractApp app;

    protected WidgetTest(AbstractApp app, WebDriver driver) {
        this.app = app;
        initElements(new AppiumFieldDecorator(driver), app);
    }

    @Test
    public abstract void checkThatWidgetsAreCreatedCorrectly();

    @Test
    public abstract void checkCaseWhenWidgetClassHasDeclaredLocatorAnnotation();

    @Test
    public abstract void checkCaseWhenWidgetClassHasNoDeclaredAnnotationButItHasSuperclass();

    @Test
    public abstract void checkCaseWhenBothWidgetFieldAndClassHaveDeclaredAnnotations();

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
