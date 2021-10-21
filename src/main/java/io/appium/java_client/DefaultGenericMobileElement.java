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

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.remote.Response;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"unchecked", "rawtypes"})
abstract class DefaultGenericMobileElement<T extends WebElement> extends RemoteWebElement implements ExecutesMethod {

    @Override public Response execute(String driverCommand, Map<String, ?> parameters) {
        return super.execute(driverCommand, parameters);
    }

    @Override public Response execute(String command) {
        return super.execute(command, ImmutableMap.of());
    }

    @Override public List findElements(By by) {
        return super.findElements(by);
    }

    @Override public T findElement(By by) {
        return (T) super.findElement(by);
    }

}
