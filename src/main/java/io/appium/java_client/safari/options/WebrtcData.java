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

package io.appium.java_client.safari.options;

import io.appium.java_client.remote.options.BaseMapOptionData;

import java.util.Map;
import java.util.Optional;

public class WebrtcData extends BaseMapOptionData<WebrtcData> {
    public WebrtcData() {
    }

    public WebrtcData(Map<String, Object> options) {
        super(options);
    }

    /**
     * Normally, Safari refuses to allow media capture over insecure connections.
     * This restriction is relaxed by default for WebDriver sessions for testing
     * purposes (for example, a test web server not configured for HTTPS). When
     * this capability is specified, Safari will revert to the normal behavior of
     * preventing media capture over insecure connections.
     *
     * @param disabled True to disable insecure media capture.
     * @return self instance for chaining.
     */
    public WebrtcData withDisableInsecureMediaCapture(boolean disabled) {
        return assignOptionValue("DisableInsecureMediaCapture", disabled);
    }

    /**
     * Get whether to disable insecure media capture.
     *
     * @return True or false.
     */
    public Optional<Boolean> doesDisableInsecureMediaCapture() {
        return getOptionValue("DisableInsecureMediaCapture");
    }

    /**
     * To protect a user's privacy, Safari normally filters out WebRTC
     * ICE candidates that correspond to internal network addresses when
     * capture devices are not in use. This capability suppresses ICE candidate
     * filtering so that both internal and external network addresses are
     * always sent as ICE candidates.
     *
     * @param disabled True to disable ICE candidates filtering.
     * @return self instance for chaining.
     */
    public WebrtcData withDisableIceCandidateFiltering(boolean disabled) {
        return assignOptionValue("DisableICECandidateFiltering", disabled);
    }

    /**
     * Get whether to disable ICE candidates filtering.
     *
     * @return True or false.
     */
    public Optional<Boolean> doesDisableIceCandidateFiltering() {
        return getOptionValue("DisableICECandidateFiltering");
    }
}
