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

import java.net.InetAddress;
import java.net.UnknownHostException;

public class BaseIOSTest {

    protected static AppiumDriverLocalService service;
    protected static IOSDriver<IOSElement> driver;
    protected static final int PORT = 4723;

    /**
     * Starts a local server.
     *
     * @return ip of a local host
     * @throws UnknownHostException when it is impossible to get ip address of a local host
     */
    public static String startAppiumServer() throws UnknownHostException {
        service = new AppiumServiceBuilder().usingPort(PORT).build();
        service.start();
        InetAddress inetAddress = InetAddress.getLocalHost();
        return inetAddress.getHostAddress();
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
