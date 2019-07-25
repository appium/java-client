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

import static java.util.Optional.ofNullable;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.screenrecording.BaseStartScreenRecordingOptions;

import java.time.Duration;
import java.util.Map;

public class AndroidStartScreenRecordingOptions
        extends BaseStartScreenRecordingOptions<AndroidStartScreenRecordingOptions> {
    private Integer bitRate;
    private String videoSize;
    private Boolean isBugReportEnabled;

    public static AndroidStartScreenRecordingOptions startScreenRecordingOptions() {
        return new AndroidStartScreenRecordingOptions();
    }

    /**
     * The video bit rate for the video, in megabits per second.
     * The default value is 4000000 (4 Mb/s) for Android API level below 27
     * and 20000000 (20 Mb/s) for API level 27 and above.
     * You can increase the bit rate to improve video quality,
     * but doing so results in larger movie files.
     *
     * @param bitRate The actual bit rate (Mb/s).
     * @return self instance for chaining.
     */
    public AndroidStartScreenRecordingOptions withBitRate(int bitRate) {
        this.bitRate = bitRate;
        return this;
    }
    
    /**
     * The video size of the generated media file. The format is WIDTHxHEIGHT.
     * The default value is the device's native display resolution (if supported),
     * 1280x720 if not. For best results,
     * use a size supported by your device's Advanced Video Coding (AVC) encoder.
     *
     * @param videoSize The actual video size: WIDTHxHEIGHT.
     * @return self instance for chaining.
     */
    public AndroidStartScreenRecordingOptions withVideoSize(String videoSize) {
        this.videoSize = videoSize;
        return this;
    }

    /**
     * Makes the recorder to display an additional information on the video overlay,
     * such as a timestamp, that is helpful in videos captured to illustrate bugs.
     * This option is only supported since API level 27 (Android P).
     *
     * @return self instance for chaining.
     */
    public AndroidStartScreenRecordingOptions enableBugReport() {
        this.isBugReportEnabled = true;
        return this;
    }

    /**
     * The maximum recording time. The default and maximum value is 180 seconds (3 minutes).
     * Setting values greater than this or less than zero will cause an exception. The minimum
     * time resolution unit is one second.
     *
     * <p>Since Appium 1.8.2 the time limit can be up to 1800 seconds (30 minutes).
     * Appium will automatically try to merge the 3-minutes chunks recorded
     * by the screenrecord utility, however, this requires FFMPEG utility
     * to be installed and available in PATH on the server machine. If the utility is not
     * present then the most recent screen recording chunk is going to be returned as the result.</p>
     *
     * @param timeLimit The actual time limit of the recorded video.
     * @return self instance for chaining.
     */
    @Override
    public AndroidStartScreenRecordingOptions withTimeLimit(Duration timeLimit) {
        return super.withTimeLimit(timeLimit);
    }

    @Override
    public Map<String, Object> build() {
        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        builder.putAll(super.build());
        ofNullable(bitRate).map(x -> builder.put("bitRate", x));
        ofNullable(videoSize).map(x -> builder.put("videoSize", x));
        ofNullable(isBugReportEnabled).map(x -> builder.put("bugReport", x));
        return builder.build();
    }
}
