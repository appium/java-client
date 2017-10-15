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

import static io.appium.java_client.MobileCommand.getSettingsCommand;
import static io.appium.java_client.MobileCommand.setSettingsCommand;

import com.google.common.collect.ImmutableMap;

import org.openqa.selenium.remote.Response;

import java.util.Map;


public interface HasSettings extends ExecutesMethod {

    /**
     * Set a setting for this test session It's probably better to use a
     * convenience function, rather than use this function directly. Try finding
     * the method for the specific setting you want to change.
     *
     * @param setting Setting you wish to set.
     * @param value   value of the setting.
     */
    default void setSetting(Setting setting, Object value) {
        CommandExecutionHelper.execute(this, setSettingsCommand(setting, value));
    }

    /**
     * Get settings stored for this test session It's probably better to use a
     * convenience function, rather than use this function directly. Try finding
     * the method for the specific setting you want to read.
     *
     * @return JsonObject, a straight-up hash of settings.
     */
    @SuppressWarnings("unchecked")
    default Map<String, Object> getSettings() {
        Map.Entry<String, Map<String, ?>> keyValuePair = getSettingsCommand();
        Response response = execute(keyValuePair.getKey(), keyValuePair.getValue());

        return ImmutableMap.<String, Object>builder()
                .putAll(Map.class.cast(response.getValue())).build();
    }
}
