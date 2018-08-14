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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import io.appium.java_client.android.connection.ConnectionState;
import io.appium.java_client.android.connection.ConnectionStateBuilder;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AndroidConnectionTest extends BaseAndroidTest {

    @Test
    public void test1() {
        ConnectionState state = driver.setConnection(new ConnectionStateBuilder()
                .withWiFiEnabled()
                .build());
        assertTrue(state.isWiFiEnabled());
    }

    @Test
    public void test2() {
        ConnectionState state = driver.setConnection(new ConnectionStateBuilder()
                .withAirplaneModeDisabled()
                .build());
        assertFalse(state.isAirplaneModeEnabled());
        assertFalse(state.isWiFiEnabled());
        assertFalse(state.isDataEnabled());
        state = driver.setConnection(new ConnectionStateBuilder(state)
                .withAirplaneModeEnabled()
                .build());
        assertTrue(state.isAirplaneModeEnabled());
    }

    @Test
    public void test3() {
        //runs only on Sims or rooted devices
        ConnectionState state = driver.setConnection(
                new ConnectionStateBuilder(driver.getConnection())
                        .withAirplaneModeDisabled()
                        .withWiFiEnabled()
                        .withDataEnabled()
                        .build());
        assertFalse(state.isAirplaneModeEnabled());
        assertTrue(state.isWiFiEnabled());
        assertTrue(state.isDataEnabled());
    }
}
