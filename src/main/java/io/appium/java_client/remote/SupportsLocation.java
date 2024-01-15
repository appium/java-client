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

package io.appium.java_client.remote;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;
import io.appium.java_client.MobileCommand;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.html5.LocationContext;
import org.openqa.selenium.remote.html5.RemoteLocationContext;

import java.util.Map;
import java.util.Optional;

public interface SupportsLocation extends WebDriver, ExecutesMethod, LocationContext {

    @Deprecated(forRemoval = true)
    RemoteLocationContext getLocationContext();

    /**
     * Gets the current device's geolocation coordinates.
     *
     * @return A {@link Location} containing the location information. Returns null if the location is not available
     * @deprecated This method and whole {@link LocationContext} interface are deprecated, use {@link #getLocation()}
     *     instead.
     */
    @Deprecated(forRemoval = true)
    default Location location() {
        return getLocationContext().location();
    }

    /**
     * Gets the current device's geolocation coordinates.
     *
     * @return A {@link Location} containing the location information. Throws {@link WebDriverException} if the
     *     location is not available.
     */
    default io.appium.java_client.Location getLocation() {
        Map<String, Number> result = CommandExecutionHelper.execute(this, MobileCommand.GET_LOCATION);
        return new io.appium.java_client.Location(
                result.get("latitude").doubleValue(),
                result.get("longitude").doubleValue(),
                Optional.ofNullable(result.get("altitude")).map(Number::doubleValue).orElse(null)
        );
    }

    /**
     * Sets the current device's geolocation coordinates.
     *
     * @param location A {@link Location} containing the new location information.
     * @deprecated This method and whole {@link LocationContext} interface are deprecated, use
     *     {@link #setLocation(io.appium.java_client.Location)} instead.
     */
    @Deprecated(forRemoval = true)
    default void setLocation(Location location) {
        getLocationContext().setLocation(location);
    }

    /**
     * Sets the current device's geolocation coordinates.
     *
     * @param location A {@link Location} containing the new location information.
     */
    default void setLocation(io.appium.java_client.Location location) {
        ImmutableMap.Builder<String, Object> locationParameters = ImmutableMap.builder();
        locationParameters.put("latitude", location.getLatitude());
        locationParameters.put("longitude", location.getLongitude());
        Optional.ofNullable(location.getAltitude()).ifPresent(altitude -> locationParameters.put("altitude", altitude));
        execute(MobileCommand.SET_LOCATION, Map.of("location", location));
    }
}
