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

import io.appium.java_client.internal.CapabilityHelpers;
import io.appium.java_client.remote.options.BaseMapOptionData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EspressoBuildConfig extends BaseMapOptionData<EspressoBuildConfig> {
    public static final String TOOLS_VERSION = "toolsVersions";
    public static final String ADDITIONAL_APP_DEPENDENCIES = "additionalAppDependencies";
    public static final String ADDITIONAL_ANDROID_TEST_DEPENDENCIES
            = "additionalAndroidTestDependencies";

    public EspressoBuildConfig() {
        super();
    }

    public EspressoBuildConfig(String json) {
        super(json);
    }

    private EspressoBuildConfig assignToolsVersionsField(String name, Object value) {
        Optional<Map<String, Object>> toolsVersionsOptional = getOptionValue(TOOLS_VERSION);
        Map<String, Object> toolsVersions = toolsVersionsOptional.orElseGet(HashMap::new);
        toolsVersions.put(name, value);
        if (!toolsVersionsOptional.isPresent()) {
            assignOptionValue(TOOLS_VERSION, toolsVersions);
        }
        return this;
    }

    private <R> Optional<R> getToolsVersionsFieldValue(String name) {
        Optional<Map<String, Object>> toolsVersionsOptional = getOptionValue(TOOLS_VERSION);
        //noinspection unchecked
        return toolsVersionsOptional.map((v) -> (R) v.getOrDefault(name, null));
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
     * Set the version of the included androidx.compose.ui components to compile the server for.
     * By default, the version from app's build.gradle is used.
     *
     * @param composeVersion E.g. "1.0.5"
     * @return self instance for chaining.
     */
    public EspressoBuildConfig withComposeVersion(String composeVersion) {
        return assignToolsVersionsField("composeVersion", composeVersion);
    }

    /**
     * Get the version of included androidx.compose.ui components.
     *
     * @return Version of androidx.compose.ui components.
     */
    public Optional<String> getComposeVersion() {
        return getToolsVersionsFieldValue("composeVersion");
    }

    /**
     * Set the minimum version of JVM the project sources are compatible with.
     *
     * @param sourceCompatibility E.g. "VERSION_12"
     * @return self instance for chaining.
     */
    public EspressoBuildConfig withSourceCompatibility(String sourceCompatibility) {
        return assignToolsVersionsField("sourceCompatibility", sourceCompatibility);
    }

    /**
     * Get the minimum version of JVM the project sources are compatible with.
     *
     * @return Minimum version of JVM the project sources are compatible with.
     */
    public Optional<String> getSourceCompatibility() {
        return getToolsVersionsFieldValue("sourceCompatibility");
    }

    /**
     * Set the target version of the generated JVM bytecode as a string.
     *
     * @param jvmTarget E.g. "1_10" or `1_8`
     * @return self instance for chaining.
     */
    public EspressoBuildConfig withJvmTarget(String jvmTarget) {
        return assignToolsVersionsField("jvmTarget", jvmTarget);
    }

    /**
     * Get the target version of the generated JVM bytecode as a string.
     *
     * @return Target version of the generated JVM bytecode as a string.
     */
    public Optional<String> getJvmTarget() {
        return getToolsVersionsFieldValue("jvmTarget");
    }

    /**
     * Set the target version of JVM the project sources are compatible with.
     *
     * @param targetCompatibility E.g. "VERSION_12"
     * @return self instance for chaining.
     */
    public EspressoBuildConfig withTargetCompatibility(String targetCompatibility) {
        return assignToolsVersionsField("targetCompatibility", targetCompatibility);
    }

    /**
     * Get the target version of JVM the project sources are compatible with.
     *
     * @return Target version of JVM the project sources are compatible with.
     */
    public Optional<String> getTargetCompatibility() {
        return getToolsVersionsFieldValue("targetCompatibility");
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
        return result.map(CapabilityHelpers::toInteger);
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
        return result.map(CapabilityHelpers::toInteger);
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

    /**
     * Set a non-empty array of dependent module names with their versions.
     * The scripts add all these items as "implementation" lines of dependencies
     * category in the app build.gradle script.
     *
     * @param dependencies E.g. ["xerces.xercesImpl:2.8.0", "xerces.xmlParserAPIs:2.6.2"].
     * @return self instance for chaining.
     */
    public EspressoBuildConfig withAdditionalAppDependencies(List<String> dependencies) {
        return assignOptionValue(ADDITIONAL_APP_DEPENDENCIES, dependencies);
    }

    /**
     * Get the array of dependent application module names with their versions.
     *
     * @return Dependent module names with their versions.
     */
    public Optional<List<String>> getAdditionalAppDependencies() {
        return getOptionValue(ADDITIONAL_APP_DEPENDENCIES);
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
        return assignOptionValue(ADDITIONAL_ANDROID_TEST_DEPENDENCIES, dependencies);
    }

    /**
     * Get the array of dependent Android test module names with their versions.
     *
     * @return Dependent module names with their versions.
     */
    public Optional<List<String>> getAdditionalAndroidTestDependencies() {
        return getOptionValue(ADDITIONAL_ANDROID_TEST_DEPENDENCIES);
    }
}
