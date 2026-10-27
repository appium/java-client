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

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * This annotation is used when some element or all elements in a page object/widget class
 * wait for time that differs from the default duration.
 *
 * <p>Can be placed on a specific field or on a page object / {@link Widget} class (inherited by subclasses).
 * Precedence order is: field-level {@code @WithTimeout} &gt; widget class {@code @WithTimeout}
 * &gt; page/declaring class {@code @WithTimeout} (including superclasses and enclosing classes)
 * &gt; the default {@link Duration} configured on {@link AppiumFieldDecorator}.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME) @Target({ElementType.FIELD, ElementType.TYPE})
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
     * @return time unit.
     */
    ChronoUnit chronoUnit();

    /**
     * Builds a {@link Duration} from the {@link WithTimeout} annotation.
     */
    class DurationBuilder {
        private DurationBuilder() {
        }

        static Duration build(WithTimeout withTimeout) {
            return Duration.of(withTimeout.time(), withTimeout.chronoUnit());
        }
    }
}
