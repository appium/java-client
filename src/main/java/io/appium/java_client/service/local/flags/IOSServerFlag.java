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
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     * <p/>
     * the relative path of the dir where Localizable.strings file
     * resides. Default: en.lproj. Sample: --localizable-strings-dir en.lproj
     */
    @Deprecated LOCALIZABLE_STRING_PATH("--localizable-strings-dir"),
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
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     * <p/>
     * how long in ms to wait for Instruments to launch. Default: 90000
     */
    @Deprecated LAUNCH_TIMEOUT("--launch-timeout"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     * <p/>
     * IOS has a weird built-in unavoidable delay. We patch this in
     * appium. If you do not want it patched, pass in this flag. Default: false
     */
    @Deprecated USE_NATIVE_INSTRUMENTS("--native-instruments-lib"),
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
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     * <p/>
     * Calendar format for the iOS simulator.
     * Default: null
     * Sample: --calendar-format gregorian
     */
    @Deprecated CALENDAR_FORMAT("--calendar-format"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     * <p/>
     * use LANDSCAPE or PORTRAIT to initialize all requests to this
     * orientation. Sample: --orientation LANDSCAPE
     */
    @Deprecated ORIENTATION("--orientation"),
    /**
     * .tracetemplate file to use with Instruments. Sample: --tracetemplate /Users/me/Automation.tracetemplate
     */
    TRACE_TEMPLATE_FILE_PATH("--tracetemplate"),
    /**
     * custom path to the instruments commandline tool. Sample: --instruments /path/to/instruments
     */
    CUSTOM_INSTRUMENTS_PATH("--instruments"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     * <p/>
     * if set, the iOS simulator log will be written to the console. Default: false
     */
    @Deprecated SHOW_SIMULATOR_LOG("--show-sim-log"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     * <p/>
     * if set, the iOS system log will be written to the console. Default: false
     */
    @Deprecated SHOW_IOS_LOG("--show-ios-log"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     * <p/>
     * Whether to keep keychains (Library/Keychains) when reset app
     * between sessions. Default: false
     */
    @Deprecated KEEP_KEYCHAINS("--keep-keychains"),
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
     * Local port used for communication with ios-webkit-debug-proxy
     * <p/>
     * Default value: 27753
     * <p/>
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
