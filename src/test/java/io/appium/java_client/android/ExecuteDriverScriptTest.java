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
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ExecuteDriverScriptTest extends BaseAndroidTest {

    @Test
    public void verifyBasicScriptExecution() {
        String script = String.join("\n", Arrays.asList(
                "const status = await driver.status();",
                "console.warn('warning message');",
                "return status;")
        );
        ScriptValue value = driver.executeDriverScript(script, new ScriptOptions()
                .withTimeout(5000)
                .withScriptType(ScriptType.WEBDRIVERIO));
        //noinspection unchecked
        assertNotNull(((Map<String, Object>) value.getResult()).get("build"));
        //noinspection unchecked
        assertThat(((List<String>)value.getLogs().get("warn")).get(0),
                is(equalTo("warning message")));
    }
}
