package io.appium.java_client.service.local.flags;

/**
* Here is the list of Android specific server arguments.
* All flags are optional, but some are required in conjunction with certain others.
* The full list is available here: {@link http://appium.io/slate/en/master/?ruby#appium-server-arguments}
* Android specific arguments are marked by (Android-only)
*/
public enum AndroidServerFlag implements ServerArgument{
    /**
     * Port to use on device to talk to Appium<br/>
     * Sample:<br/>
     * --bootstrap-port 4724
     */
    BOOTSTRAP_PORT_NUMBER("--bootstrap-port"),
    /**
     * Java package of the Android app you want to run (e.g.,
     * com.example.android.MyApp)<br/>
     * Sample:<br/>
     * --app-pkg com.example.android.MyApp
     */
    PACKAGE("--app-pkg"),
    /**
     * Activity name for the Android activity you want to launch
     * from your package (e.g., MainActivity)<br/>
     * Sample:<br/>
     * --app-activity MainActivity
     */
    ACTIVITY("--app-activity"),
    /**
     * Package name for the Android activity you want to wait for
     * (e.g., com.example.android.MyApp)<br/>
     * Sample:<br/>
     * --app-wait-package com.example.android.MyApp
     */
    APP_WAIT_PACKAGE("--app-wait-package"),
    /**
     * Activity name for the Android activity you want to wait
     * for (e.g., SplashActivity)
     * Sample:<br/>
     * --app-wait-activity SplashActivity
     */
    APP_WAIT_ACTIVITY("--app-wait-activity"),
    /**
     * Fully qualified instrumentation class.
     * Passed to -w in adb shell am instrument -e coverage true -w <br/>
     * Sample: <br/>
     * --android-coverage com.my.Pkg/com.my.Pkg.instrumentation.MyInstrumentation
     */
    ANDROID_COVERAGE("--android-coverage"),
    /**
     * Name of the avd to launch<br/>
     * Sample:<br/>
     * --avd @default
     */
    AVD("--avd"),
    /**
     * Additional emulator arguments to launch the avd<br/>
     * Sample:<br/>
     * --avd-args -no-snapshot-load
     */
    AVD_ARGS("--avg-args"),
    /**
     * Timeout in seconds while waiting for device to become
     * ready<br/>
     * Sample:<br/>
     * --device-ready-timeout 5
     */
    DEVICE_READY_TIMEOUT("--device-ready-timeout"),
    /**
     * Local port used for communication with Selendroid<br/>
     * Sample:<br/>
     * --selendroid-port 8080
     */
    SELENDROID_PORT("--selendroid-port"),
    /**
     * When set the keystore will be used to sign apks.<br/>
     * Default: false
     */
    USE_KEY_STORE("--use-keystore"),
    /**
     * Path to keystore<br/>
     * Sample:<br/>
     * --keystore-path /Users/user/.android/debug.keystore
     */
    KEY_STORE_PATH("--keystore-path"),
    /**
     * Password to keystore<br/>
     * Default: android
     */
    KEY_STORE_PASSWORD("--keystore-password"),
    /**
     * Key alias<br/>
     * Default: androiddebugkey
     */
    KEY_ALIAS("--key-alias"),
    /**
     * Key password<br/>
     * Default: android
     */
    KEY_PASSWORD("--key-password"),
    /**
     * Intent action which will be used to start activity<br/>
     * Default: android.intent.action.MAIN<br/>
     * Sample:<br/>
     * --intent-action android.intent.action.MAIN
     */
    INTENT_ACTION("--intent-action"),
    /**
     * Intent category which will be used to start activity<br/>
     * Default: android.intent.category.LAUNCHER<br/>
     * Sample:<br/>
     * --intent-category android.intent.category.APP_CONTACTS
     */
    INTENT_CATEGORY("--intent-category"),
    /**
     * Flags that will be used to start activity<br/>
     * Default: 0x10200000<br/>
     * Sample:<br/>
     * --intent-flags 0x10200000
     */
    INTENT_FLAGS("--intent-flags"),
    /**
     * Additional intent arguments that will be used to start
     * activity<br/>
     * Default: null<br/>
     * Sample:<br/>
     * --intent-args 0x10200000
     */
    INTENT_ARGUMENTS("--intent-args"),
    /**
     * When included, refrains from stopping the app before
     * restart<br/>
     * Default: false<br/>
     */
    DO_NOT_STOP_APP_ON_RESET("--dont-stop-app-on-reset"),
    /**
     * If set, prevents Appium from killing the adb server
     * instance<br/>
     * Default: false<br/>
     */
    SUPPRESS_ADB_KILL_SERVER("--suppress-adb-kill-server");

    private final String arg;

    private AndroidServerFlag(String arg) {
        this.arg = arg;
    }

    @Override
    public String getArgument() {
        return arg;
    }
}
