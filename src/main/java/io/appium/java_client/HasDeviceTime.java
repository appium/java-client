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

import static io.appium.java_client.MobileCommand.GET_DEVICE_TIME;

import com.google.common.collect.ImmutableMap;

import org.openqa.selenium.remote.Response;

public interface HasDeviceTime extends ExecutesMethod {

    /**
     * Gets device date and time for both iOS(host time is returned for simulators) and Android devices.
     *
     * @param format The set of format specifiers. Read
     *               https://momentjs.com/docs/ to get the full list of supported
     *               datetime format specifiers. The default format is
     *               `YYYY-MM-DDTHH:mm:ssZ`, which complies to ISO-8601
     * @return Device time string
     */
    default String getDeviceTime(String format) {
        Response response = execute(GET_DEVICE_TIME, ImmutableMap.of("format", format));
        return response.getValue().toString();
    }

    /**
     * Gets device date and time for both iOS(host time is returned for simulators) and Android devices.
     * The default format since Appium 1.8.2 is `YYYY-MM-DDTHH:mm:ssZ`, which complies to ISO-8601.
     *
     * @return Device time string
     */
    default String getDeviceTime() {
        Response response = execute(GET_DEVICE_TIME);
        return response.getValue().toString();
    }
}
