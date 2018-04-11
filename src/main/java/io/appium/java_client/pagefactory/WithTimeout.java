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

package io.appium.java_client.pagefactory;

import static java.time.temporal.ChronoUnit.FOREVER;
import static java.time.temporal.ChronoUnit.MILLIS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * This annotation is used when some element waits for time
 * that differs from defined by default.
 */
@Retention(RetentionPolicy.RUNTIME) @Target({ElementType.FIELD})
public @interface WithTimeout {
    /**
     * Desired waiting duration.
     *
     * @return waiting duration
     */
    long time();

    /**
     * Desired time unit.
     *
     * @deprecated use {@link #chronoUnit()} instead. This property is going to be removed.
     * @return time unit
     */
    @Deprecated
    TimeUnit unit() default NANOSECONDS;

    /**
     * Desired time unit.
     *
     * @return time unit. Default value {@link java.time.temporal.ChronoUnit#FOREVER} was added
     *     for backward compatibility temporary.
     */
    ChronoUnit chronoUnit() default FOREVER;

    class DurationBuilder {
        static Duration build(WithTimeout withTimeout) {
            //providing backward compatibility
            if (!FOREVER.equals(withTimeout.chronoUnit())) {
                return Duration.of(withTimeout.time(), withTimeout.chronoUnit());
            }

            return Duration.of(MILLISECONDS.convert(withTimeout.time(), withTimeout.unit()), MILLIS);
        }
    }
}
