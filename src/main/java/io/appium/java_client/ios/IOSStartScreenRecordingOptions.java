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

package io.appium.java_client.ios;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.ofNullable;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.screenrecording.BaseStartScreenRecordingOptions;

import java.time.Duration;
import java.util.Map;

public class IOSStartScreenRecordingOptions
        extends BaseStartScreenRecordingOptions<IOSStartScreenRecordingOptions> {
    private String videoType;
    private String videoQuality;

    public static IOSStartScreenRecordingOptions startScreenRecordingOptions() {
        return new IOSStartScreenRecordingOptions();
    }

    public enum VideoType {
        H264, MP4, FMP4
    }

    /**
     * The format of the screen capture to be recorded.
     * Available formats: "h264", "mp4" or "fmp4". Default is "mp4".
     * Only works for Simulator.
     *
     * @param videoType one of available format names.
     * @return self instance for chaining.
     */
    public IOSStartScreenRecordingOptions withVideoType(VideoType videoType) {
        this.videoType = checkNotNull(videoType).name().toLowerCase();
        return this;
    }

    public enum VideoQuality {
        LOW, MEDIUM, HIGH, PHOTO
    }

    /**
     * The video encoding quality (low, medium, high, photo - defaults to medium).
     * Only works for real devices.
     *
     * @param videoQuality one of possible quality preset names.
     * @return self instance for chaining.
     */
    public IOSStartScreenRecordingOptions withVideoQuality(VideoQuality videoQuality) {
        this.videoQuality = checkNotNull(videoQuality).name().toLowerCase();
        return this;
    }

    /**
     * The maximum recording time. The default value is 180 seconds (3 minutes).
     * The maximum value is 10 minutes.
     * Setting values greater than this or less than zero will cause an exception. The minimum
     * time resolution unit is one second.
     *
     * @param timeLimit The actual time limit of the recorded video.
     * @return self instance for chaining.
     */
    @Override
    public IOSStartScreenRecordingOptions withTimeLimit(Duration timeLimit) {
        return super.withTimeLimit(timeLimit);
    }

    @Override
    public Map<String, Object> build() {
        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        builder.putAll(super.build());
        ofNullable(videoType).map(x -> builder.put("videoType", x));
        ofNullable(videoQuality).map(x -> builder.put("videoQuality", x));
        return builder.build();
    }
}
