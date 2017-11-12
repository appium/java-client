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

package io.appium.java_client.touch;

import java.util.HashMap;
import java.util.Map;

public abstract class ActionOptions<T extends ActionOptions<T>> {
    /**
     * This method is automatically called before building
     * options map to verify the consistency of the instance.
     *
     * @throws IllegalArgumentException if there are problems with this options map.
     */
    protected abstract void verify();

    /**
     * Creates a map based on the provided options.
     *
     * @return options mapping.
     */
    public Map<String, Object> build() {
        verify();
        return new HashMap<>();
    }
}
