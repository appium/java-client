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

import static org.junit.Assert.assertEquals;

import io.appium.java_client.NoSuchContextException;
import org.junit.Test;

public class IOSContextTest extends BaseIOSWebViewTest {

    @Test public void testGetContext() {
        assertEquals("NATIVE_APP", driver.getContext());
    }

    @Test public void testGetContextHandles() {
        assertEquals(driver.getContextHandles().size(), 2);
    }

    @Test public void testSwitchContext() {
        driver.getContextHandles();
        driver.context("WEBVIEW_1");
        assertEquals(driver.getContext(), "WEBVIEW_1");
        driver.context("NATIVE_APP");
    }

    @Test(expected = NoSuchContextException.class) public void testContextError() {
        driver.context("Planet of the Ape-ium");
    }
}
