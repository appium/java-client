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

package io.appium.java_client.android.options.avd;

import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.CanSetCapability;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.internal.Either;

import java.util.List;
import java.util.Optional;

public interface SupportsAvdArgsOption<T extends BaseOptions<T>> extends
        Capabilities, CanSetCapability<T> {
    String AVD_ARGS_OPTION = "avdArgs";

    /**
     * Set emulator command line arguments.
     *
     * @param args Emulator command line arguments.
     * @return self instance for chaining.
     */
    default T setAvdArgs(List<String> args) {
        return amend(AVD_ARGS_OPTION, args);
    }

    /**
     * Set emulator command line arguments.
     *
     * @param args Emulator command line arguments.
     * @return self instance for chaining.
     */
    default T setAvdArgs(String args) {
        return amend(AVD_ARGS_OPTION, args);
    }

    /**
     * Get emulator command line arguments.
     *
     * @return Either arguments list of command line string.
     */
    default Optional<Either<List<String>, String>> getAvdArgs() {
        //noinspection unchecked
        return Optional.ofNullable(getCapability(AVD_ARGS_OPTION))
                .map((v) -> v instanceof List
                        ? Either.left((List<String>) v)
                        : Either.right(String.valueOf(v))
                );
    }
}
