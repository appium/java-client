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
     * @param format datetime format specifier:
     *                          %% literal %             %n newline              %t tab
     *                          %S seconds (00-60)       %M minute (00-59)       %m month (01-12)
     *                          %H hour (0-23)           %I hour (01-12)         %p AM/PM
     *                          %y short year (00-99)    %Y year                 %C century
     *                          %a short weekday name    %A weekday name         %u day of week (1-7, 1=mon)
     *                          %b short month name      %B month name           %Z timezone name
     *                          %j day of year (001-366) %d day of month (01-31) %e day of month ( 1-31)
     *                          %s seconds past the Epoch
     *
     *                          %U Week of year (0-53 start sunday)   %W Week of year (0-53 start monday)
     *                          %V Week of year (1-53 start monday, week < 4 days not part of this year)
     *
     *                          %D = "%m/%d/%y"    %r = "%I : %M : %S %p"   %T = "%H:%M:%S"   %h = "%b"
     *                          %x locale date     %X locale time           %c locale date/time
     *               The specifier is only supported since Appium 1.8.2.
     *               The default format specifier is "+%Y-%m-%dT%T%z".
     * @return Device time string
     */
    default String getDeviceTime(String format) {
        Response response = execute(GET_DEVICE_TIME, ImmutableMap.of("format", format));
        return response.getValue().toString();
    }

    /**
     * Gets device date and time for both iOS(host time is returned for simulators) and Android devices.
     * The default time format for is ISO-8601 since Appium 1.8.2,
     *
     * @return Device time string
     */
    default String getDeviceTime() {
        Response response = execute(GET_DEVICE_TIME);
        return response.getValue().toString();
    }
}
