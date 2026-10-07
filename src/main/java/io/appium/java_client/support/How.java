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

package io.appium.java_client.support;

import org.openqa.selenium.By;

/**
 * Location strategies for {@link FindBy#how()}.
 * Adapted from Selenium's {@code org.openqa.selenium.support.How} (Apache License 2.0).
 */
public enum How {
    /** Locates by class name. */
    CLASS_NAME {
        @Override
        public By buildBy(String value) {
            return By.className(value);
        }
    },
    /** Locates by CSS selector. */
    CSS {
        @Override
        public By buildBy(String value) {
            return By.cssSelector(value);
        }
    },
    /** Locates by id. */
    ID {
        @Override
        public By buildBy(String value) {
            return By.id(value);
        }
    },
    /** Locates by id first and then by name. */
    ID_OR_NAME {
        @Override
        public By buildBy(String value) {
            return new ByIdOrName(value);
        }
    },
    /** Locates by link text. */
    LINK_TEXT {
        @Override
        public By buildBy(String value) {
            return By.linkText(value);
        }
    },
    /** Locates by name. */
    NAME {
        @Override
        public By buildBy(String value) {
            return By.name(value);
        }
    },
    /** Locates by a part of link text. */
    PARTIAL_LINK_TEXT {
        @Override
        public By buildBy(String value) {
            return By.partialLinkText(value);
        }
    },
    /** Locates by tag name. */
    TAG_NAME {
        @Override
        public By buildBy(String value) {
            return By.tagName(value);
        }
    },
    /** Locates by XPath. */
    XPATH {
        @Override
        public By buildBy(String value) {
            return By.xpath(value);
        }
    },
    /** No strategy is set, which falls back to locating by id. */
    UNSET {
        @Override
        public By buildBy(String value) {
            return ID.buildBy(value);
        }
    };

    /**
     * Builds the locator for the given value.
     *
     * @param value the value to locate by
     * @return the locator
     */
    public abstract By buildBy(String value);
}
