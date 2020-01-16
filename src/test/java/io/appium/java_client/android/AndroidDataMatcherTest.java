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

package io.appium.java_client.android;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.MobileBy;
import org.junit.Test;
import org.openqa.selenium.json.Json;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertNotNull;

public class AndroidDataMatcherTest extends BaseEspressoTest {

    @Test
    public void testFindByDataMatcher() {
        final WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions
            .elementToBeClickable(MobileBy.AccessibilityId("Graphics")));
        driver.findElement(MobileBy.AccessibilityId("Graphics")).click();

        String selector = new Json().toJson(ImmutableMap.of(
            "name", "hasEntry",
            "args", ImmutableList.of("title", "Sweep")
        ));

        assertNotNull(wait.until(ExpectedConditions
            .presenceOfElementLocated(MobileBy.androidDataMatcher(selector))));
    }
}
