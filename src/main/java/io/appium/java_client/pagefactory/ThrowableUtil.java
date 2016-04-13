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

import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.StaleElementReferenceException;

import java.lang.reflect.InvocationTargetException;

class ThrowableUtil {
    private static final String INVALID_SELECTOR_PATTERN = "Invalid locator strategy:";

    static boolean isInvalidSelectorRootCause(Throwable e) {
        if (e == null) {
            return false;
        }

        if (InvalidSelectorException.class.isAssignableFrom(e.getClass())) {
            return true;
        }

        if (String.valueOf(e.getMessage()).contains(INVALID_SELECTOR_PATTERN) || String
            .valueOf(e.getMessage()).contains("Locator Strategy \\w+ is not supported")) {
            return true;
        }

        return isInvalidSelectorRootCause(e.getCause());
    }

    static boolean isStaleElementReferenceException(Throwable e) {
        if (e == null) {
            return false;
        }

        if (StaleElementReferenceException.class.isAssignableFrom(e.getClass())) {
            return true;
        }

        return isStaleElementReferenceException(e.getCause());
    }

    static Throwable extractReadableException(Throwable e) {
        if (!RuntimeException.class.equals(e.getClass()) && !InvocationTargetException.class
            .equals(e.getClass())) {
            return e;
        }

        return extractReadableException(e.getCause());
    }
}
