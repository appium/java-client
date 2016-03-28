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
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     * <p/>
     * Java package of the Android app you want to run (e.g.,
     * com.example.android.MyApp). Sample:
     * --app-pkg com.example.android.MyApp
     */
    @Deprecated PACKAGE("--app-pkg"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     * <p/>
     * Activity name for the Android activity you want to launch
     * from your package (e.g., MainActivity). Sample:
     * --app-activity MainActivity
     */
    @Deprecated ACTIVITY("--app-activity"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     * <p/>
     * Package name for the Android activity you want to wait for
     * (e.g., com.example.android.MyApp). Sample:
     * --app-wait-package com.example.android.MyApp
     */
    @Deprecated APP_WAIT_PACKAGE("--app-wait-package"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     * <p/>
     * Activity name for the Android activity you want to wait
     * for (e.g., SplashActivity). Sample:
     * --app-wait-activity SplashActivity
     */
    @Deprecated APP_WAIT_ACTIVITY("--app-wait-activity"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     * <p/>
     * Fully qualified instrumentation class.
     * Passed to -w in adb shell am instrument -e coverage true -w. Sample:
     * --android-coverage com.my.Pkg/com.my.Pkg.instrumentation.MyInstrumentation
     */
    @Deprecated ANDROID_COVERAGE("--android-coverage"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     * <p/>
     * Name of the avd to launch. Sample:
     * --avd @default
     */
    @Deprecated AVD("--avd"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     * <p/>
     * Additional emulator arguments to launch the avd. Sample:
     * --avd-args -no-snapshot-load
     */
    @Deprecated AVD_ARGS("--avd-args"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     * <p/>
     * Timeout in seconds while waiting for device to become
     * ready. Sample:
     * --device-ready-timeout 5
     */
    @Deprecated DEVICE_READY_TIMEOUT("--device-ready-timeout"),
    /**
     * Local port used for communication with Selendroid. Sample:
     * --selendroid-port 8080
     */
    SELENDROID_PORT("--selendroid-port"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     * <p/>
     * When set the keystore will be used to sign apks. Default: false
     */
    @Deprecated USE_KEY_STORE("--use-keystore"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     * <p/>
     * Path to keystore. Sample:
     * --keystore-path /Users/user/.android/debug.keystore
     */
    @Deprecated KEY_STORE_PATH("--keystore-path"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     * <p/>
     * Password to keystore. Default: android
     */
    @Deprecated KEY_STORE_PASSWORD("--keystore-password"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     * <p/>
     * Key alias. Default: androiddebugkey
     */
    @Deprecated KEY_ALIAS("--key-alias"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     * <p/>
     * Key password. Default: android
     */
    @Deprecated KEY_PASSWORD("--key-password"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     * <p/>
     * Intent action which will be used to start activity. Default:
     * android.intent.action.MAIN. Sample: --intent-action android.intent.action.MAIN
     */
    @Deprecated INTENT_ACTION("--intent-action"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     * <p/>
     * Intent category which will be used to start activity. Default: android.intent.category.LAUNCHER.
     * Sample:s --intent-category android.intent.category.APP_CONTACTS
     */
    @Deprecated INTENT_CATEGORY("--intent-category"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     * <p/>
     * Flags that will be used to start activity. Default: 0x10200000.
     * Sample: --intent-flags 0x10200000
     */
    @Deprecated INTENT_FLAGS("--intent-flags"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     * <p/>
     * Additional intent arguments that will be used to start
     * activity. Default: null.
     * Sample: --intent-args 0x10200000
     */
    @Deprecated INTENT_ARGUMENTS("--intent-args"),
    /**
     * This flag IS deprecated since appium node 1.5.x. It will be removed in the next release.
     * Be careful.
     * <p/>
     * When included, refrains from stopping the app before
     * restart. Default: false
     */
    @Deprecated DO_NOT_STOP_APP_ON_RESET("--dont-stop-app-on-reset"),
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
     * ChromeDriver executable full path
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
