package io.appium.java_client.touch.offset;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Proxy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ElementOptionTest {

    @Test
    void withElementRejectsNonRemoteWebElement() {
        WebElement stub = (WebElement) Proxy.newProxyInstance(
                WebElement.class.getClassLoader(),
                new Class<?>[]{WebElement.class},
                (proxy, method, args) -> {
                    if ("toString".equals(method.getName())) {
                        return "stub-web-element";
                    }
                    if (method.getReturnType().equals(boolean.class)) {
                        return false;
                    }
                    if (method.getReturnType().equals(int.class)) {
                        return 0;
                    }
                    return null;
                });

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> ElementOption.element(stub));

        assertThat(thrown.getMessage(), containsString("RemoteWebElement"));
    }
}
