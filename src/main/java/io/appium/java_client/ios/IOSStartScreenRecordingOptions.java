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

import io.appium.java_client.screenrecording.BaseStartScreenRecordingOptions;
import io.appium.java_client.screenrecording.ScreenRecordingUploadOptions;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

public class IOSStartScreenRecordingOptions
        extends BaseStartScreenRecordingOptions<IOSStartScreenRecordingOptions> {
    private String videoType;
    private String videoQuality;
    private String videoScale;
    private String videoFilters;
    private Integer fps;

    public static IOSStartScreenRecordingOptions startScreenRecordingOptions() {
        return new IOSStartScreenRecordingOptions();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IOSStartScreenRecordingOptions withUploadOptions(ScreenRecordingUploadOptions uploadOptions) {
        return (IOSStartScreenRecordingOptions) super.withUploadOptions(uploadOptions);
    }

    /**
     * The video codec type used for encoding of the recorded screen capture.
     * Execute `ffmpeg -codecs` in the terminal to see the list of supported video codecs.
     * 'mjpeg' by default.
     *
     * @since Appium 1.10.0
     * @param videoType one of available video codec names, for example 'libx264'.
     * @return self instance for chaining.
     */
    public IOSStartScreenRecordingOptions withVideoType(String videoType) {
        this.videoType = requireNonNull(videoType);
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
        this.videoQuality = requireNonNull(videoQuality).name().toLowerCase();
        return this;
    }

    /**
     * The Frames Per Second rate of the recorded video. Defaults to 10.
     *
     * @since Appium 1.10.0
     * @param fps frames per second value in range 1..60.
     * @return self instance for chaining.
     */
    public IOSStartScreenRecordingOptions withFps(int fps) {
        this.fps = fps;
        return this;
    }

    /**
     * The scaling value to apply. Read https://trac.ffmpeg.org/wiki/Scaling for possible values.
     * No scale is applied by default.
     * If filters are set then the scale setting is effectively ignored.
     *
     * @since Appium 1.10.0
     * @param videoScale ffmpeg-compatible scale format specifier.
     * @return self instance for chaining.
     */
    public IOSStartScreenRecordingOptions withVideoScale(String videoScale) {
        this.videoScale = requireNonNull(videoScale);
        return this;
    }

    /**
     * The maximum recording time. The default value is 180 seconds (3 minutes).
     * The maximum value is 30 minutes.
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

    /**
     * The FFMPEG video filters to apply. These filters allow to scale, flip, rotate and do many
     * other useful transformations on the source video stream. The format of the property
     * must comply with https://ffmpeg.org/ffmpeg-filters.html.
     *
     * @since Appium 1.15
     * @param filters One or more filters to apply to the resulting video stream,
     *                for example "transpose=1" to rotate the resulting video 90 degrees clockwise.
     * @return self instance for chaining.
     */
    public IOSStartScreenRecordingOptions withVideoFilters(String filters) {
        this.videoFilters = filters;
        return this;
    }

    @Override
    public Map<String, Object> build() {
        var map = new HashMap<>(super.build());
        ofNullable(videoType).map(x -> map.put("videoType", x));
        ofNullable(videoQuality).map(x -> map.put("videoQuality", x));
        ofNullable(videoScale).map(x -> map.put("videoScale", x));
        ofNullable(videoFilters).map(x -> map.put("videoFilters", x));
        ofNullable(fps).map(x -> map.put("videoFps", x));
        return Collections.unmodifiableMap(map);
    }
}
