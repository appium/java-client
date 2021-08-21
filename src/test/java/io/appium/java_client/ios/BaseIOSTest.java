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
import org.junit.AfterClass;

import java.net.SocketException;
import java.net.UnknownHostException;

import static io.appium.java_client.TestUtils.getLocalIp4Address;

public class BaseIOSTest {

    protected static AppiumDriverLocalService service;
    protected static IOSDriver<IOSElement> driver;
    protected static final int PORT = 4723;
    public static final String DEVICE_NAME = System.getenv("IOS_DEVICE_NAME") != null
            ? System.getenv("IOS_DEVICE_NAME") : "iPhone 12";
    public static final String PLATFORM_VERSION = System.getenv("IOS_PLATFORM_VERSION") != null
            ? System.getenv("IOS_PLATFORM_VERSION") : "14.5";


    /**
     * Starts a local server.
     *
     * @return ip of a local host
     * @throws UnknownHostException when it is impossible to get ip address of a local host
     */
    public static String startAppiumServer() throws UnknownHostException, SocketException {
        service = new AppiumServiceBuilder().usingPort(PORT).build();
        service.start();
        return getLocalIp4Address();
    }

    /**
     * finishing.
     */
    @AfterClass public static void afterClass() {
        if (driver != null) {
            driver.quit();
        }
        if (service != null) {
            service.stop();
        }
    }
}
