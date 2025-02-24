package io.appium.java_client.remote.options;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BaseOptionsTest {

    @ParameterizedTest
    @CsvSource({
        "test, appium:test",
        "appium:test, appium:test",
        "browserName, browserName",
        "digital.ai:accessKey, digital.ai:accessKey",
        "digital-ai:accessKey, digital-ai:accessKey",
        "digital-ai:my_custom-cap:xyz, digital-ai:my_custom-cap:xyz",
        "digital-ai:my_custom-cap?xyz, digital-ai:my_custom-cap?xyz",
    })
    void verifyW3CMapping(String capName, String expected) {
        var w3cName = BaseOptions.toW3cName(capName);
        assertEquals(expected, w3cName);
    }
}