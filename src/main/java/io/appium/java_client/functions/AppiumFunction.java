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

import com.google.common.base.Function;

import java.util.Objects;
import java.util.Optional;

/**
 * This is extended version of {@link com.google.common.base.Function}. It is combined
 * with {@link java.util.function.Function}. It was designed in order to provide compatibility
 * with the {@link org.openqa.selenium.support.ui.Wait}.
 *
 * @param <F> The input type
 * @param <T> The return type
 */
@FunctionalInterface
public interface AppiumFunction<F, T>  extends Function<F, T>, java.util.function.Function<F, T> {

    @Override default <V> AppiumFunction<V, T> compose(java.util.function.Function<? super V, ? extends F> before) {
        Objects.requireNonNull(before);
        return (V v) -> {
            F f = before.apply(v);
            return Optional.ofNullable(f != null ? apply(f) : null).orElse(null);
        };
    }

    @Override default <V> AppiumFunction<F, V> andThen(java.util.function.Function<? super T, ? extends V> after) {
        Objects.requireNonNull(after);
        return (F f) -> {
            T result = apply(f);
            return Optional.ofNullable(result != null ? after.apply(result) : null).orElse(null);
        };
    }
}
