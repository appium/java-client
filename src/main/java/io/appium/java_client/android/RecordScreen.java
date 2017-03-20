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

package io.appium.java_client.android;

import static io.appium.java_client.android.AndroidMobileCommandHelper.startRecordingScreenCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.stopRecordingScreenCommand;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;

public interface RecordScreen extends ExecutesMethod {
    /**
     * record the display of devices running Android 4.4 (API level 19) and higher.
     * It records screen activity to an MPEG-4 file. Audio is not recorded with the video file.
     *
     * @param filePath the video file name
     *                 for example, "/sdcard/demo.mp4"
     * @param videoSize the format is widthxheight.
     *                  if it is "default", the default value is the device's native display resolution (if supported),
     *                  1280x720 if not. For best results,
     *                  use a size supported by your device's Advanced Video Coding (AVC) encoder.
     *                  for example, "1280x720"
     * @param timeLimit the maximum recording time, in seconds. if it is -1,
     *                  the default and maximum value is 180 (3 minutes).
     * @param bitRate the video bit rate for the video, in megabits per second.
     *                if it is -1, the default value is 4Mbps. You can increase the bit rate to improve video quality,
     *                but doing so results in larger movie files.
     *                for example, 6000000
     *
     */
    default void startRecordingScreen(String filePath, String videoSize, int timeLimit, int bitRate) throws Exception {
        CommandExecutionHelper.execute(this, startRecordingScreenCommand( filePath, videoSize, timeLimit, bitRate ));
    }

    /**
     * record the display of devices running Android 4.4 (API level 19) and higher.
     * It records screen activity to an MPEG-4 file. Audio is not recorded with the video file.
     * The video size has the default value which is the device's native display resolution (if supported).
     * The maximum recording time has the default value which is 180 (3 minutes).
     * The video bit rate has the default value which is 4Mbps.
     *
     * @param filePath the video file name
     *                 for example, "/sdcard/demo.mp4"
     *
     */
    default void startRecordingScreen(String filePath) throws Exception {
        CommandExecutionHelper.execute(this, startRecordingScreenCommand( filePath, "default", -1, -1 ));
    }

    /**
     * stop recording the screen.
     */
    default void stopRecordingScreen() throws Exception {
        CommandExecutionHelper.execute(this, stopRecordingScreenCommand());
    }

}
