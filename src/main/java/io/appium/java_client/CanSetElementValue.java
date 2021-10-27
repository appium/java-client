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

package io.appium.java_client;

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.remote.RemoteWebElement;

public interface CanSetElementValue extends ExecutesMethod {
    /**
     * Set a value to an element.
     *
     * @param webElement Web element instance.
     * @param value Value to set.
     */
    default void setElementValue(RemoteWebElement webElement, String value) {
        this.execute(MobileCommand.SET_VALUE, ImmutableMap.of(
                "id", webElement.getId(),
                "value", value
        ));
    }
}
