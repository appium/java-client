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

import org.openqa.selenium.WebElement;

import java.util.List;

public interface FindsByIosClassChain<T extends WebElement> extends FindsByFluentSelector<T> {

    default T findElementByIosClassChain(String using) {
        return findElement(MobileSelector.IOS_CLASS_CHAIN.toString(), using);
    }

    default List<T> findElementsByIosClassChain(String using) {
        return findElements(MobileSelector.IOS_CLASS_CHAIN.toString(), using);
    }
}
