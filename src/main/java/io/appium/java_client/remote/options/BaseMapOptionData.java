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

package io.appium.java_client.remote.options;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class BaseMapOptionData<T extends BaseMapOptionData<T>> {
    private Map<String, Object> options;

    public BaseMapOptionData() {
    }

    public BaseMapOptionData(Map<String, Object> options) {
        this.options = options;
    }

    public BaseMapOptionData(String json) {
        //noinspection unchecked
        this((Map<String, Object>) new Gson().fromJson(json, Map.class));
    }

    /**
     * Sets the given value on the data object.
     *
     * @param key Key name.
     * @param value The actual value to set.
     * @return self instance for chaining.
     */
    public T assignOptionValue(String key, Object value) {
        if (options == null) {
            options = new HashMap<>();
        }
        options.put(key, value);
        //noinspection unchecked
        return (T) this;
    }

    /**
     * Retrieves a value with the given name from a data object.
     * This method does not perform any type transformation, but rather
     * just tries to cast the received value to the given type, so
     * be careful while providing a very specific result type value to
     * not get a type cast error.
     *
     * @param name Key name.
     * @param <R> The expected value type.
     * @return The actual value.
     */
    public <R> Optional<R> getOptionValue(String name) {
        //noinspection unchecked
        return Optional.ofNullable(options)
                .map(opts -> (R) opts.getOrDefault(name, null));
    }

    public Map<String, Object> toMap() {
        return Optional.ofNullable(options).orElseGet(Collections::emptyMap);
    }

    public JsonObject toJson() {
        return new GsonBuilder().create().toJsonTree(toMap()).getAsJsonObject();
    }

    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(toMap());
    }
}
