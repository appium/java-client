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

import org.openqa.selenium.remote.Response;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static io.appium.java_client.MobileCommand.getSettingsCommand;
import static io.appium.java_client.MobileCommand.setSettingsCommand;

public interface HasSettings extends ExecutesMethod {

    /**
     * Set a setting for this test session It's probably better to use a
     * convenience function, rather than use this function directly. Try finding
     * the method for the specific setting you want to change.
     *
     * @param setting Setting you wish to set.
     * @param value   Value of the setting.
     * @return        Self instance for chaining.
     */
    default HasSettings setSetting(Setting setting, Object value) {
        return setSetting(setting.toString(), value);
    }

    /**
     * Set a setting for this test session It's probably better to use a
     * convenience function, rather than use this function directly. Try finding
     * the method for the specific setting you want to change.
     *
     * @param settingName Setting name you wish to set.
     * @param value       Value of the setting.
     * @return            Self instance for chaining.
     */
    default HasSettings setSetting(String settingName, Object value) {
        CommandExecutionHelper.execute(this, setSettingsCommand(settingName, value));
        return this;
    }

    /**
     * Sets settings for this test session.
     *
     * @param settings a map with settings, where key is the setting name you wish to set and value is the value of
     *                 the setting.
     * @return         Self instance for chaining.
     */
    default HasSettings setSettings(EnumMap<Setting, Object> settings) {
        Map<String, Object> convertedSettings = settings.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().toString(), Entry::getValue));
        return setSettings(convertedSettings);
    }

    /**
     * Sets settings for this test session.
     *
     * @param settings a map with settings, where key is the setting name you wish to set and value is the value of
     *                 the setting.
     * @return         Self instance for chaining.
     */
    default HasSettings setSettings(Map<String, Object> settings) {
        CommandExecutionHelper.execute(this, setSettingsCommand(settings));
        return this;
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
        return (Map<String, Object>) response.getValue();
    }
}
