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
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"unchecked", "rawtypes"})
abstract class DefaultGenericMobileDriver<T extends WebElement> extends RemoteWebDriver
    implements MobileDriver<T> {

    public DefaultGenericMobileDriver(CommandExecutor executor, Capabilities desiredCapabilities) {
        super(executor, desiredCapabilities);
    }

    @Override public Response execute(String driverCommand, Map<String, ?> parameters) {
        return super.execute(driverCommand, parameters);
    }

    @Override public Response execute(String command) {
        return super.execute(command, ImmutableMap.of());
    }

    @Override public List findElements(By by) {
        return super.findElements(by);
    }

    @Override public List findElements(String by, String using) {
        return super.findElements(by, using);
    }

    @Override public T findElement(By by) {
        return (T) super.findElement(by);
    }

    @Override public T findElement(String by, String using) {
        return (T) super.findElement(by, using);
    }

    @Override
    public String toString() {
        Capabilities capabilities = getCapabilities();
        return String.format("%s, Capabilities: %s", getClass().getCanonicalName(),
                capabilities != null ? capabilities.asMap().toString() : "null");
    }
}
