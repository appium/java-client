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
 * Here is the list of iOS specific server arguments.
 */
public enum IOSServerFlag implements ServerArgument {
    /**
     * absolute path to compiled .ipa file. Sample: --ipa /abs/path/to/my.ipa
     */
    IPA_ABSOLUTE_PATH("--ipa"),
    /**
     * How many times to retry launching Instruments before saying it
     * crashed or timed out. Sample: --backend-retries 3
     */
    BACK_END_RETRIES("--backend-retries"),
    /**
     * Use the safari app. Default: false
     */
    SAFARI("--safari"),
    /**
     * use the default simulator that instruments launches
     * on its own. Default: false
     */
    DEFAULT_DEVICE("--default-device"),
    /**
     * Use the iPhone Simulator no matter what the app wants. Default: false
     */
    FORCE_IPHONE_SIMULATOR("--force-iphone"),
    /**
     * Use the iPad Simulator no matter what the app wants. Default: false
     */
    FORCE_IPAD_SIMULATOR("--force-ipad"),
    /**
     * .tracetemplate file to use with Instruments.
     * Sample: --tracetemplate /Users/me/Automation.tracetemplate
     */
    TRACE_TEMPLATE_FILE_PATH("--tracetemplate"),
    /**
     * custom path to the instruments commandline tool. Sample: --instruments /path/to/instruments
     */
    CUSTOM_INSTRUMENTS_PATH("--instruments"),
    /**
     * Xcode 6 has a bug on some platforms where a certain simulator can only be
     * launched without error if all other simulator devices are first deleted.
     * This option causes Appium to delete all devices other than the one being
     * used by Appium. Note that this is a permanent deletion, and you are
     * responsible for using simctl or xcode to manage the categories of devices
     * used with Appium. Default: false
     */
    ISOLATE_SIM_DEVICE("--isolate-sim-device"),
    /**
     * Absolute path to directory Appium use to save ios instruments traces,
     * defaults to /appium-instruments. Default: null
     */
    TRACE_DIRECTORY_ABSOLUTE_PATH("--trace-dir"),

    /**
     * Local port used for communication with ios-webkit-debug-proxy.
     * Default value: 27753
     * Sample: --webkit-debug-proxy-port 27753
     */
    WEBKIT_DEBUG_PROXY_PORT("--webkit-debug-proxy-port");

    private final String arg;

    IOSServerFlag(String arg) {
        this.arg = arg;
    }

    @Override public String getArgument() {
        return arg;
    }
}
