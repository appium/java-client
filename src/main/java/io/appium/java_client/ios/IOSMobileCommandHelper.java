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

package io.appium.java_client.ios;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.MobileCommand;

import java.util.AbstractMap;
import java.util.Map;

public class IOSMobileCommandHelper extends MobileCommand {

    public static Map.Entry<String, Map<String, ?>> hideKeyboardCommand(String keyName) {
        return new AbstractMap.SimpleEntry<String,
            Map<String, ?>>(HIDE_KEYBOARD, prepareArguments("keyName", keyName));
    }

    public static Map.Entry<String, Map<String, ?>> hideKeyboardCommand(String strategy, String keyName) {
        String[] parameters = new String[] {"strategy", "key"};
        Object[] values = new Object[] {strategy, keyName};
        return new AbstractMap.SimpleEntry<String,
                Map<String, ?>>(HIDE_KEYBOARD, prepareArguments(parameters, values));
    }

    public static Map.Entry<String, Map<String, ?>>  lockDeviceCommand(int seconds) {
        return new AbstractMap.SimpleEntry<String,
            Map<String, ?>>(LOCK, prepareArguments("seconds", seconds));
    }

    public static Map.Entry<String, Map<String, ?>>  shakeCommand() {
        return new AbstractMap.SimpleEntry<String,
            Map<String, ?>>(SHAKE, ImmutableMap.<String, Object>of());
    }
}
