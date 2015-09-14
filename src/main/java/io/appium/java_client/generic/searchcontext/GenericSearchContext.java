/*
 * Copyright 2015 Appium Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
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

package io.appium.java_client.generic.searchcontext;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public interface GenericSearchContext<T extends WebElement> {
    List<T> findElements(By by);
    T findElement(By by);
}
