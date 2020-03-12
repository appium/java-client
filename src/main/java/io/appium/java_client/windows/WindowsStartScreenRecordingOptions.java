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

package io.appium.java_client.windows;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.screenrecording.BaseStartScreenRecordingOptions;

import java.time.Duration;
import java.util.Map;

import static java.util.Optional.ofNullable;

public class WindowsStartScreenRecordingOptions
        extends BaseStartScreenRecordingOptions<WindowsStartScreenRecordingOptions> {
    private Integer fps;
    private String videoFilter;
    private String preset;

    public static WindowsStartScreenRecordingOptions startScreenRecordingOptions() {
        return new WindowsStartScreenRecordingOptions();
    }

    /**
     * The count of frames per second in the resulting video.
     * Increasing fps value also increases the size of the resulting
     * video file and the CPU usage.
     *
     * @param fps The actual frames per second value.
     *            The default value is 15.
     * @return self instance for chaining.
     */
    public WindowsStartScreenRecordingOptions withFps(int fps) {
        this.fps = fps;
        return this;
    }
    
    /**
     * The video filter spec to apply for ffmpeg.
     * See https://trac.ffmpeg.org/wiki/FilteringGuide for more details on the possible values.
     * Example: Set it to `scale=ifnot(gte(iw\,1024)\,iw\,1024):-2` in order to limit the video width
     * to 1024px. The height will be adjusted automatically to match the actual screen aspect ratio.
     *
     * @param videoFilter Valid ffmpeg video filter spec string.
     * @return self instance for chaining.
     */
    public WindowsStartScreenRecordingOptions withVideoFilter(String videoFilter) {
        this.videoFilter = videoFilter;
        return this;
    }

    /**
     * A preset is a collection of options that will provide a certain encoding speed to compression ratio.
     * A slower preset will provide better compression (compression is quality per filesize).
     * This means that, for example, if you target a certain file size or constant bit rate, you will
     * achieve better quality with a slower preset. Read https://trac.ffmpeg.org/wiki/Encode/H.264
     * for more details.
     *
     * @param preset One of the supported encoding presets. Possible values are:
     * - ultrafast
     * - superfast
     * - veryfast (default)
     * - faster
     * - fast
     * - medium
     * - slow
     * - slower
     * - veryslow
     * @return self instance for chaining.
     */
    public WindowsStartScreenRecordingOptions withPreset(String preset) {
        this.preset = preset;
        return this;
    }

    /**
     * The maximum recording time. The default value is 600 seconds (10 minutes).
     * The minimum time resolution unit is one second.
     *
     * @param timeLimit The actual time limit of the recorded video.
     * @return self instance for chaining.
     */
    @Override
    public WindowsStartScreenRecordingOptions withTimeLimit(Duration timeLimit) {
        return super.withTimeLimit(timeLimit);
    }

    @Override
    public Map<String, Object> build() {
        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        builder.putAll(super.build());
        ofNullable(fps).map(x -> builder.put("fps", x));
        ofNullable(preset).map(x -> builder.put("preset", x));
        ofNullable(videoFilter).map(x -> builder.put("videoFilter", x));
        return builder.build();
    }
}
