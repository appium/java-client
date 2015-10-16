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

package io.appium.java_client.service.local.flags;

/**
 * Here is the list of Android specific server arguments.
 */
public enum AndroidServerFlag implements ServerArgument {
    /**
     * Port to use on device to talk to Appium. Sample:
     * --bootstrap-port 4724
     */
    BOOTSTRAP_PORT_NUMBER("--bootstrap-port"),
    /**
     * Local port used for communication with Selendroid. Sample:
     * --selendroid-port 8080
     */
    SELENDROID_PORT("--selendroid-port"),
    /**
     * If set, prevents Appium from killing the adb server
     * instance. Default: false
     */
    SUPPRESS_ADB_KILL_SERVER("--suppress-adb-kill-server"),

    /**
     * Port upon which ChromeDriver will run. Sample: --chromedriver-port 9515
     */
    CHROME_DRIVER_PORT("--chromedriver-port"),
    /**
     * ChromeDriver executable full path.
     */
    CHROME_DRIVER_EXECUTABLE("--chromedriver-executable");

    private final String arg;

    AndroidServerFlag(String arg) {
        this.arg = arg;
    }

    @Override public String getArgument() {
        return arg;
    }
}
