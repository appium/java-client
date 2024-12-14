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

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.bidi.log.LogEntry;
import org.openqa.selenium.bidi.module.LogInspector;

import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class IOSBiDiTest extends AppIOSTest {

    @Test
    @Disabled("Need to resolve compatibility issues")
    public void listenForIosLogs() {
        var logs = new CopyOnWriteArrayList<LogEntry>();
        try (var logInspector = new LogInspector(driver)) {
            logInspector.onLog(logs::add);
            driver.getPageSource();
        }
        assertFalse(logs.isEmpty());
    }

}
