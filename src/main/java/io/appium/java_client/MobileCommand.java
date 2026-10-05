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

import io.appium.java_client.http.HttpMethod;
import io.appium.java_client.imagecomparison.BaseComparisonOptions;
import io.appium.java_client.imagecomparison.ComparisonMode;
import io.appium.java_client.screenrecording.BaseStartScreenRecordingOptions;
import io.appium.java_client.screenrecording.BaseStopScreenRecordingOptions;
import org.jspecify.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The repository of the Appium commands that are not defined by the W3C protocol.
 * Platform-specific features are available as `mobile:` extensions of the drivers.
 */
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "checkstyle:ConstantName"})
public class MobileCommand {
    @Deprecated
    protected static final String GET_SESSION;
    protected static final String LOG_EVENT;
    protected static final String GET_EVENTS;

    // The file transfer commands of the drivers that have no `mobile:` extensions for them, e.g. Windows
    public static final String PULL_FILE;
    public static final String PULL_FOLDER;
    public static final String PUSH_FILE;

    public static final String START_RECORDING_SCREEN;
    public static final String STOP_RECORDING_SCREEN;

    //Android
    protected static final String GET_SETTINGS;
    protected static final String SET_SETTINGS;
    protected static final String COMPARE_IMAGES;
    protected static final String EXECUTE_DRIVER_SCRIPT;
    @Deprecated
    protected static final String GET_ALLSESSION;
    protected static final String EXECUTE_GOOGLE_CDP_COMMAND;

    public static final String GET_SCREEN_ORIENTATION = "getScreenOrientation";
    public static final String SET_SCREEN_ORIENTATION = "setScreenOrientation";
    public static final String GET_SCREEN_ROTATION = "getScreenRotation";
    public static final String SET_SCREEN_ROTATION = "setScreenRotation";

    public static final String GET_CONTEXT_HANDLES = "getContextHandles";
    public static final String GET_CURRENT_CONTEXT_HANDLE = "getCurrentContextHandle";
    public static final String SWITCH_TO_CONTEXT = "switchToContext";

    public static final String GET_LOCATION = "getLocation";
    public static final String SET_LOCATION = "setLocation";

    public static final Map<String, AppiumCommandInfo> commandRepository;

    static {
        GET_SESSION = "getSession";
        LOG_EVENT = "logCustomEvent";
        GET_EVENTS = "getLogEvents";

        PULL_FILE = "pullFile";
        PULL_FOLDER = "pullFolder";
        PUSH_FILE = "pushFile";

        START_RECORDING_SCREEN = "startRecordingScreen";
        STOP_RECORDING_SCREEN = "stopRecordingScreen";

        GET_SETTINGS = "getSettings";
        SET_SETTINGS = "setSettings";
        COMPARE_IMAGES = "compareImages";
        EXECUTE_DRIVER_SCRIPT = "executeDriverScript";
        GET_ALLSESSION = "getAllSessions";
        EXECUTE_GOOGLE_CDP_COMMAND = "executeCdp";

        commandRepository = new HashMap<>();
        commandRepository.put(PULL_FILE, postC("/session/:sessionId/appium/device/pull_file"));
        commandRepository.put(PULL_FOLDER, postC("/session/:sessionId/appium/device/pull_folder"));
        commandRepository.put(PUSH_FILE, postC("/session/:sessionId/appium/device/push_file"));
        commandRepository.put(GET_SETTINGS, getC("/session/:sessionId/appium/settings"));
        commandRepository.put(SET_SETTINGS, postC("/session/:sessionId/appium/settings"));
        commandRepository.put(GET_SESSION, getC("/session/:sessionId/"));
        commandRepository.put(START_RECORDING_SCREEN,
                postC("/session/:sessionId/appium/start_recording_screen"));
        commandRepository.put(STOP_RECORDING_SCREEN,
                postC("/session/:sessionId/appium/stop_recording_screen"));
        commandRepository.put(GET_EVENTS,
                postC("/session/:sessionId/appium/events"));
        commandRepository.put(LOG_EVENT,
                postC("/session/:sessionId/appium/log_event"));

        //Android
        commandRepository.put(COMPARE_IMAGES, postC("/session/:sessionId/appium/compare_images"));
        commandRepository.put(EXECUTE_DRIVER_SCRIPT, postC("/session/:sessionId/appium/execute_driver"));
        commandRepository.put(GET_ALLSESSION, getC("/sessions"));
        commandRepository.put(EXECUTE_GOOGLE_CDP_COMMAND, postC("/session/:sessionId/goog/cdp/execute"));

        commandRepository.put(GET_SCREEN_ORIENTATION, getC("/session/:sessionId/orientation"));
        commandRepository.put(SET_SCREEN_ORIENTATION, postC("/session/:sessionId/orientation"));
        commandRepository.put(GET_SCREEN_ROTATION, getC("/session/:sessionId/rotation"));
        commandRepository.put(SET_SCREEN_ROTATION, postC("/session/:sessionId/rotation"));

        commandRepository.put(GET_CONTEXT_HANDLES, getC("/session/:sessionId/contexts"));
        commandRepository.put(GET_CURRENT_CONTEXT_HANDLE, getC("/session/:sessionId/context"));
        commandRepository.put(SWITCH_TO_CONTEXT, postC("/session/:sessionId/context"));

        commandRepository.put(GET_LOCATION, getC("/session/:sessionId/location"));
        commandRepository.put(SET_LOCATION, postC("/session/:sessionId/location"));
    }

    /**
     * This methods forms GET commands.
     *
     * @param url is the command URL
     * @return an instance of {@link AppiumCommandInfo}
     */
    public static AppiumCommandInfo getC(String url) {
        return new AppiumCommandInfo(url, HttpMethod.GET);
    }

    /**
     * This methods forms POST commands.
     *
     * @param url is the command URL
     * @return an instance of {@link AppiumCommandInfo}
     */
    public static AppiumCommandInfo postC(String url) {
        return new AppiumCommandInfo(url, HttpMethod.POST);
    }

    /**
     * This methods forms DELETE commands.
     *
     * @param url is the command URL
     * @return an instance of {@link AppiumCommandInfo}
     */
    public static AppiumCommandInfo deleteC(String url) {
        return new AppiumCommandInfo(url, HttpMethod.DELETE);
    }

    public static Map.Entry<String, Map<String, ?>> getSettingsCommand() {
        return Map.entry(GET_SETTINGS, Map.of());
    }

    public static Map.Entry<String, Map<String, ?>> setSettingsCommand(String setting, Object value) {
        return setSettingsCommand(Map.of(setting, value));
    }

    public static Map.Entry<String, Map<String, ?>> setSettingsCommand(Map<String, Object> settings) {
        return Map.entry(SET_SETTINGS, Map.of("settings", settings));
    }

    public static Map.Entry<String, Map<String, ?>> startRecordingScreenCommand(BaseStartScreenRecordingOptions opts) {
        return Map.entry(START_RECORDING_SCREEN, Map.of("options", opts.build()));
    }

    public static Map.Entry<String, Map<String, ?>> stopRecordingScreenCommand(BaseStopScreenRecordingOptions opts) {
        return Map.entry(STOP_RECORDING_SCREEN, Map.of("options", opts.build()));
    }

    /**
     * Forms a {@link Map} of parameters for images comparison.
     *
     * @param mode one of possible comparison modes
     * @param img1Data base64-encoded data of the first image
     * @param img2Data base64-encoded data of the second image
     * @param options comparison options
     * @return key-value pairs
     */
    public static Map.Entry<String, Map<String, ?>> compareImagesCommand(ComparisonMode mode,
                                                                         byte[] img1Data, byte[] img2Data,
                                                                         @Nullable BaseComparisonOptions options) {
        var args = new HashMap<String, Object>();
        args.put("mode", mode.toString());
        args.put("firstImage", new String(img1Data, StandardCharsets.UTF_8));
        args.put("secondImage", new String(img2Data, StandardCharsets.UTF_8));
        Optional.ofNullable(options).ifPresent(opts -> args.put("options", options.build()));
        return Map.entry(COMPARE_IMAGES, Collections.unmodifiableMap(args));
    }

}
