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

    public static AndroidStartScreenRecordingOptions startScreenRecordingOptions() {
        return new AndroidStartScreenRecordingOptions();
    }

    /**
     * The video bit rate for the video, in megabits per second.
     * The default value is 4. You can increase the bit rate to improve video quality,
     * but doing so results in larger movie files.
     * The value of 5000000 equals to 5Mb/sec.
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
     * The maximum recording time. The default and maximum value is 180 seconds (3 minutes).
     * Setting values greater than this or less than zero will cause an exception. The minimum
     * time resolution unit is one second.
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
        return builder.build();
    }
}
