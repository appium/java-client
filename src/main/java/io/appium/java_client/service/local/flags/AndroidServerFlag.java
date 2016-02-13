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

@Deprecated
/**
* Here is the list of Android specific server arguments.
* All flags are optional, but some are required in conjunction with certain others.
* The full list is available here: http://appium.io/slate/en/master/?ruby#appium-server-arguments
* Android specific arguments are marked by (Android-only)
 *
 * This flags are deprecated since appium node 1.5.x. This list will be removed in the next release.
 * Be careful.
*/
public enum AndroidServerFlag implements ServerArgument{
    /**
     * Port to use on device to talk to Appium. Sample:
     * --bootstrap-port 4724
     */
    BOOTSTRAP_PORT_NUMBER("--bootstrap-port"),
    /**
     * Java package of the Android app you want to run (e.g.,
     * com.example.android.MyApp). Sample:
     * --app-pkg com.example.android.MyApp
     */
    PACKAGE("--app-pkg"),
    /**
     * Activity name for the Android activity you want to launch
     * from your package (e.g., MainActivity). Sample:
     * --app-activity MainActivity
     */
    ACTIVITY("--app-activity"),
    /**
     * Package name for the Android activity you want to wait for
     * (e.g., com.example.android.MyApp). Sample:
     * --app-wait-package com.example.android.MyApp
     */
    APP_WAIT_PACKAGE("--app-wait-package"),
    /**
     * Activity name for the Android activity you want to wait
     * for (e.g., SplashActivity). Sample:
     * --app-wait-activity SplashActivity
     */
    APP_WAIT_ACTIVITY("--app-wait-activity"),
    /**
     * Fully qualified instrumentation class.
     * Passed to -w in adb shell am instrument -e coverage true -w. Sample:
     * --android-coverage com.my.Pkg/com.my.Pkg.instrumentation.MyInstrumentation
     */
    ANDROID_COVERAGE("--android-coverage"),
    /**
     * Name of the avd to launch. Sample:
     * --avd @default
     */
    AVD("--avd"),
    /**
     * Additional emulator arguments to launch the avd. Sample:
     * --avd-args -no-snapshot-load
     */
    AVD_ARGS("--avd-args"),
    /**
     * Timeout in seconds while waiting for device to become
     * ready. Sample:
     * --device-ready-timeout 5
     */
    DEVICE_READY_TIMEOUT("--device-ready-timeout"),
    /**
     * Local port used for communication with Selendroid. Sample:
     * --selendroid-port 8080
     */
    SELENDROID_PORT("--selendroid-port"),
    /**
     * When set the keystore will be used to sign apks. Default: false
     */
    USE_KEY_STORE("--use-keystore"),
    /**
     * Path to keystore. Sample:
     * --keystore-path /Users/user/.android/debug.keystore
     */
    KEY_STORE_PATH("--keystore-path"),
    /**
     * Password to keystore. Default: android
     */
    KEY_STORE_PASSWORD("--keystore-password"),
    /**
     * Key alias. Default: androiddebugkey
     */
    KEY_ALIAS("--key-alias"),
    /**
     * Key password. Default: android
     */
    KEY_PASSWORD("--key-password"),
    /**
     * Intent action which will be used to start activity. Default:
     * android.intent.action.MAIN. Sample: --intent-action android.intent.action.MAIN
     */
    INTENT_ACTION("--intent-action"),
    /**
     * Intent category which will be used to start activity. Default: android.intent.category.LAUNCHER.
     * Sample:s --intent-category android.intent.category.APP_CONTACTS
     */
    INTENT_CATEGORY("--intent-category"),
    /**
     * Flags that will be used to start activity. Default: 0x10200000.
     * Sample: --intent-flags 0x10200000
     */
    INTENT_FLAGS("--intent-flags"),
    /**
     * Additional intent arguments that will be used to start
     * activity. Default: null.
     * Sample: --intent-args 0x10200000
     */
    INTENT_ARGUMENTS("--intent-args"),
    /**
     * When included, refrains from stopping the app before
     * restart. Default: false
     */
    DO_NOT_STOP_APP_ON_RESET("--dont-stop-app-on-reset"),
    /**
     * If set, prevents Appium from killing the adb server
     * instance. Default: false
     */
    SUPPRESS_ADB_KILL_SERVER("--suppress-adb-kill-server");

    private final String arg;

    AndroidServerFlag(String arg) {
        this.arg = arg;
    }

    @Override
    public String getArgument() {
        return arg;
    }
}
