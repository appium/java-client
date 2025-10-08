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

package io.appium.java_client.internal;

import org.openqa.selenium.WebDriverException;

import java.lang.reflect.Field;

public class ReflectionHelpers {

    private ReflectionHelpers() {
    }

    /**
     * Sets the given value to a private instance field.
     *
     * @param cls       The target class or a superclass.
     * @param target    Target instance.
     * @param fieldName Target field name.
     * @param newValue  The value to be set.
     * @return The same instance for chaining.
     */
    public static <T> T setPrivateFieldValue(Class<?> cls, T target, String fieldName, Object newValue) {
        try {
            final Field f = cls.getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(target, newValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new WebDriverException(e);
        }
        return target;
    }
}
