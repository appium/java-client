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

package io.appium.java_client.screenrecording;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;

import static io.appium.java_client.MobileCommand.START_RECORDING_SCREEN;
import static io.appium.java_client.MobileCommand.startRecordingScreenCommand;
import static io.appium.java_client.MobileCommand.STOP_RECORDING_SCREEN;
import static io.appium.java_client.MobileCommand.stopRecordingScreenCommand;


public interface CanRecordScreen extends ExecutesMethod {

    /**
     * Start asynchronous screen recording process.
     *
     * @param options see the documentation on the {@link BaseStartScreenRecordingOptions}
     *                descendant for the particular platform.
     * @return Base-64 encoded content of the recorded media file or an empty string
     * if the file has been successfully uploaded to a remote location (depends on the actual options).
     */
    default <T extends BaseStartScreenRecordingOptions> String startRecordingScreen(T options) {
        return CommandExecutionHelper.execute(this, startRecordingScreenCommand(options));
    }

    /**
     * Start asynchronous screen recording process with default options.
     *
     * @return Base-64 encoded content of the recorded media file.
     */
    default String startRecordingScreen() {
        return this.execute(START_RECORDING_SCREEN).getValue().toString();
    }

    /**
     * Gather the output from the previously started screen recording to a media file.
     *
     * @param options see the documentation on the {@link BaseStopScreenRecordingOptions}
     *                descendant for the particular platform.
     * @return Base-64 encoded content of the recorded media file or an empty string
     * if the file has been successfully uploaded to a remote location (depends on the actual options).
     */
    default <T extends BaseStopScreenRecordingOptions> String stopRecordingScreen(T options) {
        return CommandExecutionHelper.execute(this, stopRecordingScreenCommand(options));
    }

    /**
     * Gather the output from the previously started screen recording to a media file
     * with default options.
     *
     * @return Base-64 encoded content of the recorded media file.
     */
    default String stopRecordingScreen() {
        return this.execute(STOP_RECORDING_SCREEN).getValue().toString();
    }
}
