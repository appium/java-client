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

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;
import org.openqa.selenium.remote.DriverCommand;

import java.util.AbstractMap;

public interface SupportsExtendedGeolocationCommands extends ExecutesMethod {

    /**
     * Allows to set geo location with extended parameters
     * available for Android platform.
     *
     * @param location The location object to set.
     */
    default void setLocation(AndroidGeoLocation location) {
        ImmutableMap<String, ?> parameters = ImmutableMap
                .of("location", location.build());
        CommandExecutionHelper.execute(this,
                new AbstractMap.SimpleEntry<>(DriverCommand.SET_LOCATION, parameters));
    }
}
