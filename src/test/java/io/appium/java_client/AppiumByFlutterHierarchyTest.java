package io.appium.java_client;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppiumByFlutterHierarchyTest {

    private final Gson gson = new Gson();

    @Test
    void flutterDescendantLocatorIsValidJsonWithExpectedShape() {
        AppiumBy.FlutterBy of = AppiumBy.flutterType("myType");
        AppiumBy.FlutterBy matching = AppiumBy.flutterText("myText");

        String locatorString = (String) AppiumBy.flutterDescendant(of, matching, true, false)
                .getRemoteParameters().value();

        @SuppressWarnings("unchecked")
        Map<String, Object> locator = gson.fromJson(locatorString, Map.class);
        assertEquals(Map.of("using", "-flutter type", "value", "myType"), locator.get("of"));
        assertEquals(Map.of("using", "-flutter text", "value", "myText"), locator.get("matching"));
        assertEquals(Map.of("matchRoot", true, "skipOffstage", false), locator.get("parameters"));
    }

    @Test
    void flutterAncestorLocatorIsValidJsonWithExpectedShape() {
        AppiumBy.FlutterBy of = AppiumBy.flutterKey("myKey");
        AppiumBy.FlutterBy matching = AppiumBy.flutterSemanticsLabel("myLabel");

        String locatorString = (String) AppiumBy.flutterAncestor(of, matching, true).getRemoteParameters().value();

        @SuppressWarnings("unchecked")
        Map<String, Object> locator = gson.fromJson(locatorString, Map.class);
        assertEquals(Map.of("using", "-flutter key", "value", "myKey"), locator.get("of"));
        assertEquals(Map.of("using", "-flutter semantics label", "value", "myLabel"), locator.get("matching"));
        assertEquals(Map.of("matchRoot", true), locator.get("parameters"));
    }
}
