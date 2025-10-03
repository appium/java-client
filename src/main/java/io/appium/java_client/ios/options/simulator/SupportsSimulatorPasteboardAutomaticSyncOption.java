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

package io.appium.java_client.ios.options.simulator;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;

import java.util.Optional;

import static java.util.Locale.ROOT;

public interface SupportsSimulatorPasteboardAutomaticSyncOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String SIMULATOR_PASTEBOARD_AUTOMATIC_SYNC = "simulatorPasteboardAutomaticSync";

    /**
     * Handle the -PasteboardAutomaticSync flag when simulator process launches.
     * It could improve launching simulator performance not to sync pasteboard with
     * the system when this value is off. on forces the flag enabled. system does
     * not provide the flag to the launching command. on, off, or system is available.
     * They are case-insensitive. Defaults to off.
     *
     * @param state Either on, off or system.
     * @return self instance for chaining.
     */
    default T setSimulatorPasteboardAutomaticSync(PasteboardSyncState state) {
        return amend(SIMULATOR_PASTEBOARD_AUTOMATIC_SYNC, state.toString().toLowerCase(ROOT));
    }

    /**
     * Get the pasteboard automation sync state.
     *
     * @return Pasteboard sync state.
     */
    default Optional<PasteboardSyncState> getSimulatorPasteboardAutomaticSync() {
        return Optional.ofNullable(getCapability(SIMULATOR_PASTEBOARD_AUTOMATIC_SYNC))
                .map(v -> PasteboardSyncState.valueOf(String.valueOf(v).toUpperCase(ROOT)));
    }
}
