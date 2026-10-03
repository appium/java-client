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

package io.appium.java_client.support.ui;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

/**
 * A generic interface for waiting until a condition is true or not null.
 * Adapted from Selenium's {@code org.openqa.selenium.support.ui.Wait} (Apache License 2.0).
 *
 * @param <F> the argument to pass to any function called
 */
public interface Wait<F> {

    /**
     * Implementations should wait until the condition evaluates to a value that is neither null nor
     * false. Because of this contract, the return type must not be Void.
     *
     * <p>If the condition does not become true within a certain time (as defined by the implementing
     * class), this method will throw a non-specified {@link Throwable}. This is so that an
     * implementor may throw whatever is idiomatic for a given test infrastructure (e.g. JUnit4 would
     * throw {@link AssertionError}).
     *
     * @param <V> the return type of the method, which must not be Void
     * @param isTrue the function to evaluate against the input
     * @return truthy value from the isTrue condition
     */
    <V extends @Nullable Object> @NonNull V until(Function<? super F, ? extends V> isTrue);
}
