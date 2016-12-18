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

package io.appium.java_client.functions;

import org.openqa.selenium.WebDriver;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * This is extended version of {@link org.openqa.selenium.support.ui.ExpectedCondition}. It is combined
 * with {@link java.util.function.Function}.
 *
 * @param <T> The return type
 */
@FunctionalInterface
public interface ExpectedCondition<T> extends org.openqa.selenium.support.ui.ExpectedCondition<T>,
        java.util.function.Function<WebDriver, T> {

    @Override default <V> Function<V, T> compose(Function<? super V, ? extends WebDriver> before) {
        Objects.requireNonNull(before);
        return (V v) -> {
            WebDriver driver = before.apply(v);
            return Optional.ofNullable(driver != null ? apply(driver) : null).orElse(null);
        };
    }

    @Override default <V> Function<WebDriver, V> andThen(Function<? super T, ? extends V> after) {
        Objects.requireNonNull(after);
        return (WebDriver w) -> {
            T result = apply(w);
            return Optional.ofNullable(result != null ? after.apply(result) : null).orElse(null);
        };
    }
}
