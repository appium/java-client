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

package io.appium.java_client.android.options.app;

import io.appium.java_client.remote.options.BaseMapOptionData;

import java.util.Map;
import java.util.Optional;

public class ActivityOptions extends BaseMapOptionData<ActivityOptions> {
    public ActivityOptions() {
        super();
    }

    public ActivityOptions(Map<String, Object> options) {
        super(options);
    }

    /**
     * Display id which you want to assign to launch the main app activity on.
     * This might be useful if the device under test supports multiple displays.
     *
     * @param id Display identifier.
     * @return self instance for chaining.
     */
    public ActivityOptions withLaunchDisplayId(int id) {
        return assignOptionValue("launchDisplayId", id);
    }

    /**
     * Get display id which you want to assign to launch the main app activity on.
     *
     * @return Display identifier.
     */
    public Optional<Integer> getLaunchDisplayId() {
        Optional<Object> result = getOptionValue("launchDisplayId");
        return result.map((v) -> Integer.parseInt(String.valueOf(v)));
    }
}
