package io.appium.java_client;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorCodesMobileTest {
    private final ErrorCodesMobile errorCodes = new ErrorCodesMobile();

    @Test
    void resolvesMobileExceptionFromMessage() {
        assertEquals(NoSuchContextException.class, errorCodes.getExceptionType("No such context found"));
    }

    @Test
    void resolvesStandardExceptionsFromW3cState() {
        assertEquals(NoSuchElementException.class, errorCodes.getExceptionType("no such element"));
        assertEquals(StaleElementReferenceException.class,
                errorCodes.getExceptionType("stale element reference"));
    }

    @Test
    void resolvesUnknownOrMissingStateToWebDriverException() {
        assertEquals(WebDriverException.class, errorCodes.getExceptionType("not a real error"));
        assertEquals(WebDriverException.class, errorCodes.getExceptionType((String) null));
    }
}
