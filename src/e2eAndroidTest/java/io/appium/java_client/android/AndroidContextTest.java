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

import io.appium.java_client.NoSuchContextException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AndroidContextTest extends BaseAndroidTest {

    @BeforeAll public static void beforeClass2() throws Exception {
        startActivity(".view.WebView1");
        Thread.sleep(20000);
    }

    @Test public void testGetContext() {
        assertEquals("NATIVE_APP", driver.getContext());
    }

    @Test public void testGetContextHandles() {
        assertEquals(driver.getContextHandles().size(), 2);
    }

    @Test public void testSwitchContext() {
        driver.getContextHandles();
        driver.context("WEBVIEW_io.appium.android.apis");
        assertEquals(driver.getContext(), "WEBVIEW_io.appium.android.apis");
        driver.context("NATIVE_APP");
        assertEquals(driver.getContext(), "NATIVE_APP");
    }

    @Test public void testContextError() {
        assertThrows(NoSuchContextException.class, () -> driver.context("Planet of the Ape-ium"));
    }

}
