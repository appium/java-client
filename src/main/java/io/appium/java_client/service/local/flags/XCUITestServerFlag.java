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
 * Here is the list of XCUITest driver specific server arguments (Appium 4+).
 */
public enum XCUITestServerFlag implements ServerArgument {
    /**
     * Local port used for communication with WebDriverAgent.
     * Default: 8100
     * Sample: --driver-xcuitest-webdriveragent-port=8200
     */
    WEBDRIVERAGENT_PORT("--driver-xcuitest-webdriveragent-port");

    private final String arg;

    XCUITestServerFlag(String arg) {
        this.arg = arg;
    }

    @Override public String getArgument() {
        return arg;
    }
}
