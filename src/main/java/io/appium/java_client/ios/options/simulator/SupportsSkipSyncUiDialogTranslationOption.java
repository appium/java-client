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

import static io.appium.java_client.internal.CapabilityHelpers.toSafeBoolean;

/**
 * Provides getters and setters for the {@code skipSyncUiDialogTranslation} capability.
 *
 * @param <T> options type, used for chaining.
 */
public interface SupportsSkipSyncUiDialogTranslationOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    /**
     * Name of the {@code skipSyncUiDialogTranslation} capability.
     */
    String SKIP_SYNC_UI_DIALOG_TRANSLATION_OPTION = "skipSyncUiDialogTranslation";

    /**
     * Skip synchronizing UI dialog translations on simulators.
     *
     * @return self instance for chaining.
     */
    default T skipSyncUiDialogTranslation() {
        return amend(SKIP_SYNC_UI_DIALOG_TRANSLATION_OPTION, true);
    }

    /**
     * Sets whether to skip synchronizing UI dialog translations. Defaults to {@code false}.
     *
     * @param value Set to {@code true} to skip synchronizing UI dialog translations.
     * @return self instance for chaining.
     */
    default T setSkipSyncUiDialogTranslation(boolean value) {
        return amend(SKIP_SYNC_UI_DIALOG_TRANSLATION_OPTION, value);
    }

    /**
     * Get whether to skip synchronizing UI dialog translations.
     *
     * @return True or false.
     */
    default Optional<Boolean> doesSkipSyncUiDialogTranslation() {
        return Optional.ofNullable(
                toSafeBoolean(getCapability(SKIP_SYNC_UI_DIALOG_TRANSLATION_OPTION))
        );
    }
}
