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
package io.appium.java_client.internal;

import io.appium.java_client.ios.IOSDriver;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriverException;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SessionConnectTest {

    @Test
    void canConnectToASession() throws MalformedURLException {
        IOSDriver driver = new IOSDriver(
                new URL("http://localhost:4723/session/1234"), "xcuitest"
        );
        assertEquals(driver.getSessionId().toString(), "1234");
        assertThrows(WebDriverException.class, driver::quit);
    }

}
