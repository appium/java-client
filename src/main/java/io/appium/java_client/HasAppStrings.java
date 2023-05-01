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

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.UnsupportedCommandException;

import java.util.AbstractMap;
import java.util.Map;

import static io.appium.java_client.MobileCommand.GET_STRINGS;
import static io.appium.java_client.MobileCommand.prepareArguments;

public interface HasAppStrings extends ExecutesMethod, CanRememberExtensionPresence {
    /**
     * Get all defined Strings from an app for the default language.
     * See the documentation for 'mobile: getAppStrings' extension for more details.
     *
     * @return a map with localized strings defined in the app
     */
    default Map<String, String> getAppStringMap() {
        final String extName = "mobile: getAppStrings";
        try {
            return CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName);
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            return CommandExecutionHelper.execute(markExtensionAbsence(extName), GET_STRINGS);
        }
    }

    /**
     * Get all defined Strings from an app for the specified language.
     * See the documentation for 'mobile: getAppStrings' extension for more details.
     *
     * @param language strings language code
     * @return a map with localized strings defined in the app
     */
    default Map<String, String> getAppStringMap(String language) {
        final String extName = "mobile: getAppStrings";
        try {
            return CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, ImmutableMap.of(
                    "language", language
            ));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            return CommandExecutionHelper.execute(
                    markExtensionAbsence(extName),
                    new AbstractMap.SimpleEntry<>(GET_STRINGS, prepareArguments("language", language))
            );
        }
    }

    /**
     * Get all defined Strings from an app for the specified language and
     * strings filename. See the documentation for 'mobile: getAppStrings'
     * extension for more details.
     *
     * @param language   strings language code
     * @param stringFile strings filename. Ignored on Android
     * @return a map with localized strings defined in the app
     */
    default Map<String, String> getAppStringMap(String language, String stringFile) {
        final String extName = "mobile: getAppStrings";
        try {
            return CommandExecutionHelper.executeScript(assertExtensionExists(extName), extName, ImmutableMap.of(
                    "language", language,
                    "stringFile", stringFile
            ));
        } catch (UnsupportedCommandException e) {
            // TODO: Remove the fallback
            String[] parameters = new String[]{"language", "stringFile"};
            Object[] values = new Object[]{language, stringFile};
            return CommandExecutionHelper.execute(
                    markExtensionAbsence(extName),
                    new AbstractMap.SimpleEntry<>(GET_STRINGS, prepareArguments(parameters, values))
            );
        }
    }

}
