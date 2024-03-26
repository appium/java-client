/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.appium.java_client.android.geolocation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;

public class AndroidGeoLocation {
    private Double longitude;
    private Double latitude;
    private Double altitude;
    private Integer satellites;
    private Double speed;

    /**
     * Initializes AndroidLocation instance.
     */
    public AndroidGeoLocation() {

    }

    /**
     * Initializes AndroidLocation instance with longitude and latitude values.
     *
     * @param latitude latitude value
     * @param longitude longitude value
     */
    public AndroidGeoLocation(double latitude, double longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * Sets geo longitude value. This value is required to set.
     *
     * @param longitude geo longitude
     * @return self instance for chaining
     */
    public AndroidGeoLocation withLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    /**
     * Sets geo latitude value. This value is required to set.
     *
     * @param latitude geo latitude
     * @return self instance for chaining
     */
    public AndroidGeoLocation withLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    /**
     * Sets geo altitude value.
     *
     * @param altitude geo altitude
     * @return self instance for chaining
     */
    public AndroidGeoLocation withAltitude(double altitude) {
        this.altitude = altitude;
        return this;
    }

    /**
     * Sets the number of geo satellites being tracked.
     * This number is respected on Emulators.
     *
     * @param satellites the count of satellites in range 1..12
     * @return self instance for chaining
     */
    public AndroidGeoLocation withSatellites(int satellites) {
        this.satellites = satellites;
        return this;
    }

    /**
     * Sets the movement speed. It is measured in meters/second
     * for real devices and in knots for emulators.
     *
     * @param speed the actual speed, which should be greater than zero
     * @return self instance for chaining
     */
    public AndroidGeoLocation withSpeed(double speed) {
        this.speed = speed;
        return this;
    }

    /**
     * Builds parameters map suitable for passing to the downstream API.
     *
     * @return Parameters mapping
     */
    public Map<String, ?> build() {
        final Map<String, Object> map = new HashMap<>();
        ofNullable(longitude).map(x -> map.put("longitude", x)).orElseThrow(() -> new IllegalArgumentException(
                "A valid 'longitude' must be provided"));
        ofNullable(latitude).map(x -> map.put("latitude", x)).orElseThrow(() -> new IllegalArgumentException(
                "A valid 'latitude' must be provided"));
        ofNullable(altitude).ifPresent(x -> map.put("altitude", x));
        ofNullable(satellites).ifPresent(x -> map.put("satellites", x));
        ofNullable(speed).ifPresent(x -> map.put("speed", x));
        return Collections.unmodifiableMap(map);
    }
}
