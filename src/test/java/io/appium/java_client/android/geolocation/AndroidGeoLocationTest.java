package io.appium.java_client.android.geolocation;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AndroidGeoLocationTest {

    @Test
    void shouldThrowExceptionWhenLatitudeIsNotSet() {
        var androidGeoLocation = new AndroidGeoLocation().withLongitude(24.105078);

        var exception = assertThrows(IllegalArgumentException.class, androidGeoLocation::build);

        assertEquals("A valid 'latitude' must be provided", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenLongitudeIsNotSet() {
        var androidGeoLocation = new AndroidGeoLocation().withLatitude(56.946285);

        var exception = assertThrows(IllegalArgumentException.class, androidGeoLocation::build);

        assertEquals("A valid 'longitude' must be provided", exception.getMessage());
    }

    @Test
    void shodBuildMinimalParameters() {
        var androidGeoLocation = new AndroidGeoLocation()
                .withLongitude(24.105078)
                .withLatitude(56.946285);

        assertParameters(androidGeoLocation.build(), 24.105078, 56.946285, null, null, null);
    }

    @Test
    void shodBuildFullParameters() {
        var androidGeoLocation = new AndroidGeoLocation()
                .withLongitude(24.105078)
                .withLatitude(56.946285)
                .withAltitude(7)
                .withSpeed(1.5)
                .withSatellites(12);

        assertParameters(androidGeoLocation.build(), 24.105078, 56.946285, 7.0, 1.5, 12);
    }

    private static void assertParameters(Map<String, ?> actualParams, double longitude, double latitude,
            Double altitude, Double speed, Integer satellites) {
        assertEquals(longitude, actualParams.get("longitude"));
        assertEquals(latitude, actualParams.get("latitude"));
        assertEquals(altitude, actualParams.get("altitude"));
        assertEquals(speed, actualParams.get("speed"));
        assertEquals(satellites, actualParams.get("satellites"));
    }
}
