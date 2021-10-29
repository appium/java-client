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

package io.appium.java_client.android.options.server;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EspressoBuildConfig {
    public static final String TOOLS_VERSION = "toolsVersions";
    public static final String ADDITIONAL_APP_DEPENDENCIES = "additionalAppDependencies";
    public static final String ADDITIONAL_ANDROID_TEST_DEPENDENCIES
            = "additionalAndroidTestDependencies";

    private JsonObject json;

    public EspressoBuildConfig() {
    }

    public EspressoBuildConfig(JsonObject json) {
        this.json = json;
    }

    public EspressoBuildConfig(String json) {
        this(JsonParser.parseString(json).getAsJsonObject());
    }

    private EspressoBuildConfig assignToolsVersionsField(String name, Object value) {
        if (json == null) {
            json = new JsonObject();
        }
        boolean hasTools = json.has(TOOLS_VERSION);
        JsonObject toolsVersions = hasTools
                ? json.getAsJsonObject(TOOLS_VERSION)
                : new JsonObject();
        if (value instanceof Number) {
            toolsVersions.addProperty(name, (Number) value);
        } else {
            toolsVersions.addProperty(name, String.valueOf(value));
        }
        if (!hasTools) {
            json.add(TOOLS_VERSION, toolsVersions);
        }
        return this;
    }

    private <R> Optional<R> getToolsVersionsFieldValue(String name) {
        //noinspection unchecked
        return json == null || !json.has(TOOLS_VERSION)
                ? Optional.empty()
                : Optional.ofNullable((R) json.getAsJsonObject(TOOLS_VERSION).get(name));
    }

    public EspressoBuildConfig withGradleVersion(String version) {
        return assignToolsVersionsField("gradle", version);
    }

    public Optional<String> getGradleVersion() {
        return getToolsVersionsFieldValue("gradle");
    }

    public EspressoBuildConfig withAndroidGradlePluginVersion(String version) {
        return assignToolsVersionsField("androidGradlePlugin", version);
    }

    public Optional<String> getAndroidGradlePluginVersion() {
        return getToolsVersionsFieldValue("androidGradlePlugin");
    }

    public EspressoBuildConfig withBuildToolsVersion(String version) {
        return assignToolsVersionsField("buildTools", version);
    }

    public Optional<String> getBuildToolsVersion() {
        return getToolsVersionsFieldValue("buildTools");
    }

    public EspressoBuildConfig withCompileSdkVersion(String version) {
        return assignToolsVersionsField("compileSdk", version);
    }

    public Optional<String> getCompileSdkVersion() {
        return getToolsVersionsFieldValue("compileSdk");
    }

    public EspressoBuildConfig withMinSdk(int apiLevel) {
        return assignToolsVersionsField("minSdk", apiLevel);
    }

    public Optional<Integer> getMinSdkVersion() {
        Optional<Object> result = getToolsVersionsFieldValue("minSdk");
        return result.map((v) -> Integer.parseInt(String.valueOf(v)));
    }

    public EspressoBuildConfig withTargetSdk(int apiLevel) {
        return assignToolsVersionsField("targetSdk", apiLevel);
    }

    public Optional<Integer> getTargetSdkVersion() {
        Optional<Object> result = getToolsVersionsFieldValue("targetSdk");
        return result.map((v) -> Integer.parseInt(String.valueOf(v)));
    }

    public EspressoBuildConfig withKotlinVersion(String version) {
        return assignToolsVersionsField("kotlin", version);
    }

    public Optional<String> getKotlinVersion() {
        return getToolsVersionsFieldValue("kotlin");
    }

    private EspressoBuildConfig assignDependenciesField(String name, List<String> value) {
        if (json == null) {
            json = new JsonObject();
        }
        boolean hasField = json.has(name);
        JsonArray dependencies = hasField
                ? json.getAsJsonArray(name)
                : new JsonArray();
        while (dependencies.size() > 0) {
            dependencies.remove(0);
        }
        value.forEach(dependencies::add);
        if (!hasField) {
            json.add(name, dependencies);
        }
        return this;
    }

    private Optional<List<String>> getDependenciesValue(String name) {
        return json == null
                ? Optional.empty()
                : Optional.ofNullable(json.getAsJsonArray(name))
                    .map((v) -> {
                        List<String> result = new ArrayList<>();
                        v.forEach((x) -> result.add(String.valueOf(x)));
                        return result;
                    });
    }

    public EspressoBuildConfig withAdditionalAppDependencies(List<String> dependencies) {
        return assignDependenciesField(ADDITIONAL_APP_DEPENDENCIES, dependencies);
    }

    public Optional<List<String>> getAdditionalAppDependencies() {
        return getDependenciesValue(ADDITIONAL_APP_DEPENDENCIES);
    }

    public EspressoBuildConfig withAdditionalAndroidTestDependencies(List<String> dependencies) {
        return assignDependenciesField(ADDITIONAL_ANDROID_TEST_DEPENDENCIES, dependencies);
    }

    public Optional<List<String>> getAdditionalAndroidTestDependencies() {
        return getDependenciesValue(ADDITIONAL_ANDROID_TEST_DEPENDENCIES);
    }

    public JsonObject toJson() {
        return json == null ? new JsonObject() : json;
    }

    @Override
    public String toString() {
        return toJson().toString();
    }
}
