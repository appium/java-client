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

import io.appium.java_client.driverscripts.ScriptOptions;
import io.appium.java_client.driverscripts.ScriptType;
import io.appium.java_client.driverscripts.ScriptValue;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class LogEventTest extends BaseAndroidTest {

    @Test
    public void verifyLoggingCustomEvents() {
        driver.logEvent("appium", "funEvent");
        Object value = driver.getSessionDetail("events");
        //noinspection unchecked
        assertNotNull(((Map<String, Object>) value).get("appium:funEvent"));
    }
}
