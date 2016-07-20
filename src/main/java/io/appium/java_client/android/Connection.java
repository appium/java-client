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

/**
 * for use with setting Network Connections on a mobile device.
 */
public enum Connection {
    NONE(0),
    AIRPLANE(1),
    WIFI(2),
    DATA(4),
    ALL(6);

    final int bitMask;

    Connection(int bitMask) {
        this.bitMask = bitMask;
    }

    public int getBitMask() {
        return this.bitMask;
    }
}
