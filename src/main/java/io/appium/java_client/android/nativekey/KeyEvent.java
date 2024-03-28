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

package io.appium.java_client.android.nativekey;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public class KeyEvent {
    private Integer keyCode;
    private Integer metaState;
    private Integer flags;

    public KeyEvent() {
    }

    public KeyEvent(AndroidKey key) {
        this.keyCode = key.getCode();
    }

    /**
     * Sets the key code. This code is mandatory.
     *
     * @param key Native Android key.
     * @return self instance for chaining
     */
    public KeyEvent withKey(AndroidKey key) {
        this.keyCode = key.getCode();
        return this;
    }

    /**
     * Adds the meta modifier.
     *
     * @param keyEventMetaModifier Native Android modifier value. Multiple modifiers can
     *                             be combined into a single key event.
     * @return self instance for chaining
     */
    public KeyEvent withMetaModifier(KeyEventMetaModifier keyEventMetaModifier) {
        if (this.metaState == null) {
            this.metaState = 0;
        }
        this.metaState |= keyEventMetaModifier.getValue();
        return this;
    }

    /**
     * Adds the flag.
     *
     * @param keyEventFlag Native Android flag value. Several flags can
     *                     be combined into a single key event.
     * @return self instance for chaining
     */
    public KeyEvent withFlag(KeyEventFlag keyEventFlag) {
        if (this.flags == null) {
            this.flags = 0;
        }
        this.flags |= keyEventFlag.getValue();
        return this;
    }

    /**
     * Builds a map, which is ready to be used by the downstream API.
     *
     * @return API parameters mapping
     * @throws IllegalStateException if key code is not set
     */
    public Map<String, Object> build() {
        var map = new HashMap<String, Object>();
        Integer keyCode = ofNullable(this.keyCode).orElseThrow(() -> new IllegalStateException("The key code must be set"));
        map.put("keycode", keyCode);
        ofNullable(this.metaState).ifPresent(x -> map.put("metastate", x));
        ofNullable(this.flags).ifPresent(x -> map.put("flags", x));
        return Collections.unmodifiableMap(map);
    }
}

