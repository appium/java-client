package io.appium.java_client.pagefactory_tests.widget.tests;

import io.appium.java_client.pagefactory.Widget;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.*;

import java.util.List;

public class DefaultStubWidget extends Widget implements WebElement {
    protected DefaultStubWidget(WebElement element) {
        super(element);
    }

    public <T extends Widget> T getSubWidget() {
        return null;
    }

    public <T extends Widget> List<T> getSubWidgets() {
        return List.of();
    }

    @Override
    public String toString() {
        return getWrappedElement().toString();
    }

    @Override
    public void click() {
        getWrappedElement().click();
    }

    @Override
    public void submit() {
        getWrappedElement().submit();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        getWrappedElement().sendKeys(keysToSend);
    }

    @Override
    public void clear() {
        getWrappedElement().clear();
    }

    @Override
    public String getTagName() {
        return getWrappedElement().getTagName();
    }

    @Override
    public @Nullable String getAttribute(String name) {
        return getWrappedElement().getAttribute(name);
    }

    @Override
    public boolean isSelected() {
        return getWrappedElement().isSelected();
    }

    @Override
    public boolean isEnabled() {
        return getWrappedElement().isEnabled();
    }

    @Override
    public String getText() {
        return getWrappedElement().getText();
    }

    @Override
    public boolean isDisplayed() {
        return getWrappedElement().isDisplayed();
    }

    @Override
    public Point getLocation() {
        return getWrappedElement().getLocation();
    }

    @Override
    public Dimension getSize() {
        return getWrappedElement().getSize();
    }

    @Override
    public Rectangle getRect() {
        return getWrappedElement().getRect();
    }

    @Override
    public String getCssValue(String propertyName) {
        return getWrappedElement().getCssValue(propertyName);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return getWrappedElement().getScreenshotAs(target);
    }
}
