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

package io.appium.java_client.functions;

import org.openqa.seleniumone.WebDriver;

/**
 * This is extended version of {@link org.openqa.seleniumone.support.ui.ExpectedCondition}. It is combined
 * with {@link java.util.function.Function}.
 *
 * @param <T> The return type
 */
@FunctionalInterface
public interface ExpectedCondition<T> extends org.openqa.seleniumone.support.ui.ExpectedCondition<T>,
        AppiumFunction<WebDriver, T> {
}
