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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import org.junit.jupiter.api.Test;

public class BatteryTest extends BaseAndroidTest {

    @Test
    public void veryGettingBatteryInformation() {
        final AndroidBatteryInfo batteryInfo = driver.getBatteryInfo();
        assertThat(batteryInfo.getLevel(), is(greaterThan(0.0)));
        assertThat(batteryInfo.getState(), is(not(AndroidBatteryInfo.BatteryState.UNKNOWN)));
    }
}
