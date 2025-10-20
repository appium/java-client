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

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.junit.jupiter.api.AfterAll;

import java.time.Duration;
import java.util.Optional;

@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public class BaseIOSTest {

    protected static AppiumDriverLocalService service;
    protected static IOSDriver driver;
    protected static final int PORT = 4723;
    public static final String DEVICE_NAME = Optional.ofNullable(System.getenv("IOS_DEVICE_NAME"))
            .orElse("iPhone 17");
    public static final String PLATFORM_VERSION = Optional.ofNullable(System.getenv("IOS_PLATFORM_VERSION"))
            .orElse("26.0");
    public static final Duration WDA_LAUNCH_TIMEOUT = Duration.ofMinutes(4);
    public static final Duration SERVER_START_TIMEOUT = Duration.ofMinutes(3);
    protected static String PREBUILT_WDA_PATH = System.getenv("PREBUILT_WDA_PATH");


    /**
     * Starts a local server.
     *
     * @return service instance
     */
    public static AppiumDriverLocalService startAppiumServer() {
        service = new AppiumServiceBuilder()
                .withIPAddress("127.0.0.1")
                .usingPort(PORT)
                .withTimeout(SERVER_START_TIMEOUT)
                .withArgument(GeneralServerFlag.ALLOW_INSECURE, "*:session_discovery")
                .build();
        service.start();
        return service;
    }

    /**
     * finishing.
     */
    @AfterAll public static void afterClass() {
        if (driver != null) {
            driver.quit();
        }
        if (service != null) {
            service.stop();
        }
    }
}
