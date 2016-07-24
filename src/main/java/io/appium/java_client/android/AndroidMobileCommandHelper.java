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

package io.appium.java_client.android;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.MobileCommand;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.internal.HasIdentity;

import java.util.AbstractMap;
import java.util.Map;

/**
 * The repository of Android-specific mobile commands defined in the Mobile JSON
 * wire protocol.
 */
public class AndroidMobileCommandHelper extends MobileCommand {

    public static Map.Entry<String, Map<String, ?>> currentActivityCommand() {
        return new AbstractMap.SimpleEntry<String,
            Map<String, ?>>(CURRENT_ACTIVITY, ImmutableMap.<String, Object>of());
    }

    public static Map.Entry<String, Map<String, ?>> endTestCoverageCommand(String intent,
        String path) {
        String[] parameters = new String[] {"intent", "path"};
        Object[] values = new Object[] {intent, path};
        return new AbstractMap.SimpleEntry<String,
            Map<String, ?>>(END_TEST_COVERAGE, prepareArguments(parameters, values));
    }

    public static Map.Entry<String, Map<String, ?>> getNetworkConnectionCommand() {
        return new AbstractMap.SimpleEntry<String,
            Map<String, ?>>(GET_NETWORK_CONNECTION, ImmutableMap.<String, Object>of());
    }

    public static Map.Entry<String, Map<String, ?>> isLockedCommand() {
        return new AbstractMap.SimpleEntry<String,
            Map<String, ?>>(IS_LOCKED, ImmutableMap.<String, Object>of());
    }

    public static Map.Entry<String, Map<String, ?>> pressKeyCodeCommand(int key) {
        return new AbstractMap.SimpleEntry<String,
            Map<String, ?>>(PRESS_KEY_CODE, prepareArguments("keycode", key));
    }

    public static Map.Entry<String, Map<String, ?>> pressKeyCodeCommand(int key,
        Integer metastate) {
        String[] parameters = new String[] {"keycode", "metastate"};
        Object[] values = new Object[] {key, metastate};
        return new AbstractMap.SimpleEntry<String,
            Map<String, ?>>(PRESS_KEY_CODE, prepareArguments(parameters, values));
    }

    public static Map.Entry<String, Map<String, ?>> longPressKeyCodeCommand(int key) {
        return new AbstractMap.SimpleEntry<String,
            Map<String, ?>>(LONG_PRESS_KEY_CODE, prepareArguments("keycode", key));
    }

    public static Map.Entry<String, Map<String, ?>> longPressKeyCodeCommand(int key,
        Integer metastate) {
        String[] parameters = new String[] {"keycode", "metastate"};
        Object[] values = new Object[] {key, metastate};
        return new AbstractMap.SimpleEntry<String,
            Map<String, ?>>(LONG_PRESS_KEY_CODE, prepareArguments(parameters, values));
    }

    public static Map.Entry<String, Map<String, ?>> openNotificationsCommand() {
        return new AbstractMap.SimpleEntry<String,
            Map<String, ?>>(OPEN_NOTIFICATIONS, ImmutableMap.<String, Object>of());
    }

    public static Map.Entry<String, Map<String, ?>>  pushFileCommandCommand(String remotePath,
        byte[] base64Data) {
        String[] parameters = new String[] {"path", "data"};
        Object[] values = new Object[] {remotePath, base64Data};
        return new AbstractMap.SimpleEntry<String,
            Map<String, ?>>(PUSH_FILE, prepareArguments(parameters, values));
    }

    public static Map.Entry<String, Map<String, ?>> setConnectionCommand(Connection connection) {
        String[] parameters = new String[] {"name", "parameters"};
        Object[] values =
            new Object[] {"network_connection", ImmutableMap.of("type", connection.bitMask)};
        return new AbstractMap.SimpleEntry<String,
            Map<String, ?>>(SET_NETWORK_CONNECTION, prepareArguments(parameters, values));
    }

    public static Map.Entry<String, Map<String, ?>> startActivityCommand(String appPackage,
        String appActivity,
        String appWaitPackage,
        String appWaitActivity, String intentAction,
        String intentCategory, String intentFlags,
        String optionalIntentArguments,boolean stopApp )
        throws IllegalArgumentException {

        checkArgument((!StringUtils.isBlank(appPackage)
                && !StringUtils.isBlank(appActivity)),
            String.format("'%s' and '%s' are required.", "appPackage", "appActivity"));

        appWaitPackage = !StringUtils.isBlank(appWaitPackage) ? appWaitPackage : "";
        appWaitActivity = !StringUtils.isBlank(appWaitActivity) ? appWaitActivity : "";
        intentAction = !StringUtils.isBlank(intentAction) ? intentAction : "";
        intentCategory = !StringUtils.isBlank(intentCategory) ? intentCategory : "";
        intentFlags = !StringUtils.isBlank(intentFlags) ? intentFlags : "";
        optionalIntentArguments = !StringUtils.isBlank(optionalIntentArguments)
            ? optionalIntentArguments : "";

        ImmutableMap<String, ?> parameters = ImmutableMap
            .<String, Object>builder().put("appPackage", appPackage)
            .put("appActivity", appActivity)
            .put("appWaitPackage", appWaitPackage)
            .put("appWaitActivity", appWaitActivity)
            .put("dontStopAppOnReset", !stopApp)
            .put("intentAction", intentAction)
            .put("intentCategory", intentCategory)
            .put("intentFlags", intentFlags)
            .put("optionalIntentArguments", optionalIntentArguments)
            .build();
        return new AbstractMap.SimpleEntry<String,
            Map<String, ?>>(START_ACTIVITY, parameters);
    }

    public static Map.Entry<String, Map<String, ?>> toggleLocationServicesCommand() {
        return new AbstractMap.SimpleEntry<String,
            Map<String, ?>>(TOGGLE_LOCATION_SERVICES, ImmutableMap.<String, Object>of());
    }

    public static Map.Entry<String, Map<String, ?>> unlockCommand() {
        return new AbstractMap.SimpleEntry<String,
            Map<String, ?>>(UNLOCK, ImmutableMap.<String, Object>of());
    }

    public static Map.Entry<String, Map<String, ?>>  lockDeviceCommand() {
        return new AbstractMap.SimpleEntry<String,
            Map<String, ?>>(LOCK, prepareArguments("seconds", 0));
    }

    public static Map.Entry<String, Map<String, ?>>  replaceElementValueCommand(
        HasIdentity hasIdentityObject, String value) {
        String[] parameters = new String[] {"id", "value"};
        Object[] values =
            new Object[] {hasIdentityObject.getId(), value};

        return new AbstractMap.SimpleEntry<String,
            Map<String, ?>>(REPLACE_VALUE, prepareArguments(parameters, values));
    }
}
