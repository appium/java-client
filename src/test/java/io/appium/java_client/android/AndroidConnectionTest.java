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

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AndroidConnectionTest extends BaseAndroidTest {

    @Test public void test1() {
        driver.setConnection(Connection.WIFI);
        assertEquals(Connection.WIFI,
            driver.getConnection());
    }

    @Test public void test2() {
        driver.setConnection(Connection.NONE);
        assertEquals(Connection.NONE,
            driver.getConnection());
        driver.setConnection(Connection.AIRPLANE);
        assertEquals(Connection.AIRPLANE,
            driver.getConnection());
    }

    @Test public void test3() {
        driver.setConnection(Connection.ALL);
        assertEquals(Connection.ALL,
            driver.getConnection());
    }
}
