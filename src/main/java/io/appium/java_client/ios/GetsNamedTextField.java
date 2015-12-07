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

package io.appium.java_client.ios;

import org.openqa.selenium.WebElement;

public interface GetsNamedTextField<T extends WebElement> {
    /**
     * In iOS apps, named TextFields have the same accessibility Id as their
     * containing TableElement. This is a convenience method for getting the
     * named TextField, rather than its containing element.
     *
     * @param name
     *            accessiblity id of TextField
     * @return The textfield with the given accessibility id
     */
    T getNamedTextField(String name);

}
