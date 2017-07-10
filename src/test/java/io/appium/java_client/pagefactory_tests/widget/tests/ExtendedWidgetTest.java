package io.appium.java_client.pagefactory_tests.widget.tests;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.support.PageFactory.initElements;

public abstract class ExtendedWidgetTest extends WidgetTest {


    protected ExtendedWidgetTest(ExtendedApp app, WebDriver driver) {
        super(app, driver);
    }

    @Test
    public abstract void checkAnAnnotatedWidget();

    @Test
    public abstract void checkAnExtendedWidget();

    @Test
    public abstract void checkTheLocatorOverridingOnAWidget();

    protected static void defaultTest(AbstractWidget single, List<AbstractWidget> multiple, By rootLocator, By subLocator) {

        assertThat(single.toString(), containsString(rootLocator.toString()));
        assertThat(multiple.stream().map(AbstractWidget::toString).collect(toList()),
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
