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

package io.appium.java_client;

import lombok.Getter;
import lombok.ToString;

import javax.annotation.Nullable;

/**
 * Represents the physical location.
 */
@Getter
@ToString
public class Location {
    private final double latitude;
    private final double longitude;
    private final Double altitude;

    /**
     * Create {@link Location} with latitude, longitude and altitude values.
     *
     * @param latitude latitude value.
     * @param longitude longitude value.
     * @param altitude altitude value (can be null).
     */
    public Location(double latitude, double longitude, @Nullable Double altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    /**
     * Create {@link Location} with latitude and longitude values.
     *
     * @param latitude latitude value.
     * @param longitude longitude value.
     */
    public Location(double latitude, double longitude) {
        this(latitude, longitude, null);
    }
}
