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

    /**
     * Set Gradle version.
     * By default, the version from the build.gradle is used.
     *
     * @param version E.g. "6.3".
     * @return self instance for chaining.
     */
    public EspressoBuildConfig withGradleVersion(String version) {
        return assignToolsVersionsField("gradle", version);
    }

    /**
     * Get Gradle version.
     *
     * @return Gradle version.
     */
    public Optional<String> getGradleVersion() {
        return getToolsVersionsFieldValue("gradle");
    }

    /**
     * Set Gradle plugin version. It must correspond to the Gradle version
     * (if provided). By default, the version from the build.gradle is used.
     *
     * @param version E.g. "4.1.1"
     * @return self instance for chaining.
     */
    public EspressoBuildConfig withAndroidGradlePluginVersion(String version) {
        return assignToolsVersionsField("androidGradlePlugin", version);
    }

    /**
     * Get Gradle plugin version.
     *
     * @return Gradle plugin version.
     */
    public Optional<String> getAndroidGradlePluginVersion() {
        return getToolsVersionsFieldValue("androidGradlePlugin");
    }

    /**
     * Set Android build tools version to compile the server with.
     * By default, the version from the build.gradle is used.
     *
     * @param version E.g. "28.0.3".
     * @return self instance for chaining.
     */
    public EspressoBuildConfig withBuildToolsVersion(String version) {
        return assignToolsVersionsField("buildTools", version);
    }

    /**
     * Get Android build tools version.
     *
     * @return Android build tools version.
     */
    public Optional<String> getBuildToolsVersion() {
        return getToolsVersionsFieldValue("buildTools");
    }

    /**
     * Set Android SDK version to compile the server for.
     * By default, the version from the app build.gradle is used.
     *
     * @param version E.g. "28"
     * @return self instance for chaining.
     */
    public EspressoBuildConfig withCompileSdkVersion(String version) {
        return assignToolsVersionsField("compileSdk", version);
    }

    /**
     * Get the target Android SDK version.
     *
     * @return Android SDK version.
     */
    public Optional<String> getCompileSdkVersion() {
        return getToolsVersionsFieldValue("compileSdk");
    }

    /**
     * Set the minimum Android SDK version to compile the server for.
     * By default, the version from the app build.gradle is used.
     *
     * @param apiLevel E.g. 18.
     * @return self instance for chaining.
     */
    public EspressoBuildConfig withMinSdk(int apiLevel) {
        return assignToolsVersionsField("minSdk", apiLevel);
    }

    /**
     * Get the minimum Android SDK version.
     *
     * @return Minimum Android SDK version.
     */
    public Optional<Integer> getMinSdkVersion() {
        Optional<Object> result = getToolsVersionsFieldValue("minSdk");
        return result.map((v) -> Integer.parseInt(String.valueOf(v)));
    }

    /**
     * Set the target Android SDK version to compile the server for.
     * By default, the version from the app build.gradle is used.
     *
     * @param apiLevel E.g. 28.
     * @return self instance for chaining.
     */
    public EspressoBuildConfig withTargetSdk(int apiLevel) {
        return assignToolsVersionsField("targetSdk", apiLevel);
    }

    /**
     * Get the target Android SDK version.
     *
     * @return Target Android SDK version.
     */
    public Optional<Integer> getTargetSdkVersion() {
        Optional<Object> result = getToolsVersionsFieldValue("targetSdk");
        return result.map((v) -> Integer.parseInt(String.valueOf(v)));
    }

    /**
     * Kotlin version to compile the server for.
     * By default, the version from the build.gradle is used.
     *
     * @param version E.g. "1.5.10".
     * @return self instance for chaining.
     */
    public EspressoBuildConfig withKotlinVersion(String version) {
        return assignToolsVersionsField("kotlin", version);
    }

    /**
     * Get the target Kotlin version.
     *
     * @return Kotlin version.
     */
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

    /**
     * Set a non-empty array of dependent module names with their versions.
     * The scripts add all these items as "implementation" lines of dependencies
     * category in the app build.gradle script.
     *
     * @param dependencies E.g. ["xerces.xercesImpl:2.8.0", "xerces.xmlParserAPIs:2.6.2"].
     * @return self instance for chaining.
     */
    public EspressoBuildConfig withAdditionalAppDependencies(List<String> dependencies) {
        return assignDependenciesField(ADDITIONAL_APP_DEPENDENCIES, dependencies);
    }

    /**
     * Get the array of dependent application module names with their versions.
     *
     * @return Dependent module names with their versions.
     */
    public Optional<List<String>> getAdditionalAppDependencies() {
        return getDependenciesValue(ADDITIONAL_APP_DEPENDENCIES);
    }

    /**
     * Set a non-empty array of dependent module names with their versions.
     * The scripts add all these items as "androidTestImplementation" lines of
     * dependencies category in the app build.gradle script.
     *
     * @param dependencies E.g. ["xerces.xercesImpl:2.8.0", "xerces.xmlParserAPIs:2.6.2"].
     * @return self instance for chaining.
     */
    public EspressoBuildConfig withAdditionalAndroidTestDependencies(List<String> dependencies) {
        return assignDependenciesField(ADDITIONAL_ANDROID_TEST_DEPENDENCIES, dependencies);
    }

    /**
     * Get the array of dependent Android test module names with their versions.
     *
     * @return Dependent module names with their versions.
     */
    public Optional<List<String>> getAdditionalAndroidTestDependencies() {
        return getDependenciesValue(ADDITIONAL_ANDROID_TEST_DEPENDENCIES);
    }

    public JsonObject toJson() {
        return Optional.ofNullable(json).orElseGet(JsonObject::new);
    }

    @Override
    public String toString() {
        return toJson().toString();
    }
}
