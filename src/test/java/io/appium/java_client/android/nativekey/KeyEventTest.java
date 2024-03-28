package io.appium.java_client.android.nativekey;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class KeyEventTest {

    @Test
    void shouldThrowExceptionWhenKeyCodeIsNotSet() {
        var keyEvent = new KeyEvent();

        var exception = assertThrows(IllegalStateException.class, keyEvent::build);

        assertEquals("The key code must be set", exception.getMessage());
    }

    @Test
    void shouldBuildMinimalParameters() {
        var keyEvent = new KeyEvent().withKey(AndroidKey.A);

        Map<String, Object> params = keyEvent.build();

        assertParameters(params, AndroidKey.A, null, null);
    }

    @Test
    void shouldBuildFullParameters() {
        var keyEvent = new KeyEvent()
                .withKey(AndroidKey.A)
                .withMetaModifier(KeyEventMetaModifier.CAP_LOCKED)
                .withFlag(KeyEventFlag.KEEP_TOUCH_MODE);

        Map<String, Object> params = keyEvent.build();

        assertParameters(params, AndroidKey.A, KeyEventMetaModifier.CAP_LOCKED.getValue(),
                KeyEventFlag.KEEP_TOUCH_MODE.getValue());
    }

    private static void assertParameters(Map<String, Object> params, AndroidKey key, Integer metastate, Integer flags) {
        assertEquals(key.getCode(), params.get("keycode"));
        assertEquals(metastate, params.get("metastate"));
        assertEquals(flags, params.get("flags"));
    }
}
