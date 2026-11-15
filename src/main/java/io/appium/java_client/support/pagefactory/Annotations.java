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

package io.appium.java_client.support.pagefactory;

import io.appium.java_client.support.ByIdOrName;
import io.appium.java_client.support.CacheLookup;
import io.appium.java_client.support.pagefactory.internal.PageFactoryFinderSupport;
import org.openqa.selenium.By;

import java.lang.reflect.Field;

import static java.util.Objects.requireNonNull;

/** Processes locator and cache annotations on a page object field. */
public class Annotations extends AbstractAnnotations {
    private final Field field;

    /**
     * Creates an annotation processor for the given field.
     *
     * @param field the field to inspect
     */
    public Annotations(Field field) {
        this.field = requireNonNull(field, "Field must not be null");
    }

    @Override
    public boolean isLookupCached() {
        return PageFactoryFinderSupport.isCompatibleAnnotationPresent(field, CacheLookup.class);
    }

    @Override
    public By buildBy() {
        assertValidAnnotations();

        By ans = PageFactoryFinderSupport.buildBy(field);
        if (ans == null) {
            ans = buildByFromDefault();
        }
        return ans;
    }

    /**
     * Gets the annotated field handled by this instance.
     *
     * @return the field
     */
    protected Field getField() {
        return field;
    }

    /**
     * Builds the fallback locator when no locator annotation produces one.
     *
     * @return a locator using the field name
     */
    protected By buildByFromDefault() {
        return new ByIdOrName(field.getName());
    }

    /** Verifies that mutually exclusive locator annotations are not combined on the field. */
    protected void assertValidAnnotations() {
        PageFactoryFinderSupport.validateFinderAnnotations(field);
    }
}
