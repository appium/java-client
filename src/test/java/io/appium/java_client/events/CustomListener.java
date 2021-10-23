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

package io.appium.java_client.events;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.WebDriverListener;

import java.lang.reflect.Method;

public class CustomListener implements WebDriverListener {
    private boolean didCallBeforeGet = false;
    private boolean didCallAfterGet = false;
    private String url;

    private boolean didCallBeforeAnyWebDriverCall = false;
    private boolean didCallAfterWebDriverAnyCall = false;

    @Override
    public void beforeGet(WebDriver driver, String url) {
        didCallBeforeGet = true;
        this.url = url;
    }

    @Override
    public void afterGet(WebDriver driver, String url) {
        didCallAfterGet = true;
        this.url = url;
    }

    @Override
    public void beforeAnyWebDriverCall(WebDriver driver, Method method, Object[] args) {
        didCallBeforeAnyWebDriverCall = true;
    }

    @Override
    public void afterAnyWebDriverCall(WebDriver driver, Method method, Object[] args, Object result) {
        didCallAfterWebDriverAnyCall = true;
    }

    public boolean isDidCallBeforeGet() {
        return didCallBeforeGet;
    }

    public boolean isDidCallAfterGet() {
        return didCallAfterGet;
    }

    public String getUrl() {
        return url;
    }

    public boolean isDidCallBeforeAnyWebDriverCall() {
        return didCallBeforeAnyWebDriverCall;
    }

    public boolean isDidCallAfterWebDriverAnyCall() {
        return didCallAfterWebDriverAnyCall;
    }
}