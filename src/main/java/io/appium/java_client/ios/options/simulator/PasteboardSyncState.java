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

/**
 * Possible values of the {@code simulatorPasteboardAutomaticSync} capability.
 */
public enum PasteboardSyncState {
    /** Forces the pasteboard sync flag enabled. */
    ON,
    /** Disables the pasteboard sync. */
    OFF,
    /** Does not provide the flag to the simulator launch command. */
    SYSTEM
}
