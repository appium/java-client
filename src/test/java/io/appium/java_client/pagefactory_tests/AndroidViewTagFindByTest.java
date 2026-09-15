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

package io.appium.java_client.pagefactory_tests;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.DefaultElementByBuilder;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobilePlatform;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AndroidViewTagFindByTest {

    static class Page {
        @AndroidFindBy(viewTag = "login-button")
        private WebElement loginButton;
    }

    @Test
    void androidFindByViewTagBuildsAndroidViewTagLocator() throws Exception {
        Field field = Page.class.getDeclaredField("loginButton");
        DefaultElementByBuilder builder =
                new DefaultElementByBuilder(MobilePlatform.ANDROID, AutomationName.ANDROID_UIAUTOMATOR2);
        builder.setAnnotated(field);
        By by = builder.buildBy();

        // Single AndroidFindBy strategies are wrapped in ByChained by DefaultElementByBuilder
        assertTrue(by.toString().contains(AppiumBy.androidViewTag("login-button").toString()),
                () -> "expected viewTag locator in " + by);
    }
}
