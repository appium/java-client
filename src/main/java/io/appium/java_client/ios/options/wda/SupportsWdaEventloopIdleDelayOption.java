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

package io.appium.java_client.ios.options.wda;

import io.appium.java_client.internal.CapabilityHelpers;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.time.Duration;
import java.util.Optional;

import static io.appium.java_client.internal.CapabilityHelpers.toDuration;

public interface SupportsWdaEventloopIdleDelayOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String WDA_EVENTLOOP_IDLE_DELAY_OPTION = "wdaEventloopIdleDelay";

    /**
     * Delays the invocation of -[XCUIApplicationProcess setEventLoopHasIdled:] by the
     * duration specified with this capability. This can help quiescence apps
     * that fail to do so for no obvious reason (and creating a session fails for
     * that reason). This increases the time for session creation
     * because -[XCUIApplicationProcess setEventLoopHasIdled:] is called multiple times.
     * If you enable this capability start with at least 3 seconds and try increasing it,
     * if creating the session still fails. Defaults to 0.
     *
     * @param duration Idle duration.
     * @return self instance for chaining.
     */
    default T setWdaEventloopIdleDelay(Duration duration) {
        return amend(WDA_EVENTLOOP_IDLE_DELAY_OPTION, duration.toMillis() / 1000.0);
    }

    /**
     * Get the event loop idle delay.
     *
     * @return Idle duration.
     */
    default Optional<Duration> getWdaEventloopIdleDelay() {
        return Optional.ofNullable(getCapability(WDA_EVENTLOOP_IDLE_DELAY_OPTION))
                .map(CapabilityHelpers::toDouble)
                .map((d) -> toDuration((long) (d * 1000.0)));
    }
}
