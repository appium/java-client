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

package io.appium.java_client.android.options.app;

import io.appium.java_client.remote.options.BaseMapOptionData;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class IntentOptions extends BaseMapOptionData<IntentOptions> {
    public IntentOptions() {
        super();
    }

    public IntentOptions(Map<String, Object> options) {
        super(options);
    }

    /**
     * An intent action name. Application-specific actions should be prefixed with
     * the vendor's package name.
     *
     * @param action E.g. ACTION_MAIN.
     * @return self instance for chaining.
     */
    public IntentOptions withAction(String action) {
        return assignOptionValue("action", action);
    }

    /**
     * Get the action name.
     *
     * @return Action name.
     */
    public Optional<String> getAction() {
        return getOptionValue("action");
    }

    /**
     * Set an intent data URI.
     *
     * @param data E.g. content://contacts/people/1.
     * @return self instance for chaining.
     */
    public IntentOptions withData(String data) {
        return assignOptionValue("data", data);
    }

    /**
     * Get intent data URI.
     *
     * @return Intent data URI.
     */
    public Optional<String> getData() {
        return getOptionValue("data");
    }

    /**
     * Intent MIME type.
     *
     * @param type E.g. image/png.
     * @return self instance for chaining.
     */
    public IntentOptions withType(String type) {
        return assignOptionValue("type", type);
    }

    /**
     * Get an intent type.
     *
     * @return Intent type.
     */
    public Optional<String> getType() {
        return getOptionValue("type");
    }

    /**
     * Set intent categories.
     *
     * @param categories One or more comma-separated Intent categories.
     * @return self instance for chaining.
     */
    public IntentOptions withCategories(String categories) {
        return assignOptionValue("categories", categories);
    }

    /**
     * Get intent categories.
     *
     * @return Intent categories.
     */
    public Optional<String> getCategories() {
        return getOptionValue("categories");
    }

    /**
     * Set intent component name with package name prefix
     * to create an explicit intent.
     *
     * @param component E.g. com.example.app/.ExampleActivity.
     * @return self instance for chaining.
     */
    public IntentOptions withComponent(String component) {
        return assignOptionValue("component", component);
    }

    /**
     * Get intent component name.
     *
     * @return Intent component name.
     */
    public Optional<String> getComponent() {
        return getOptionValue("component");
    }

    /**
     * Single-string value, which represents intent flags set encoded into
     * an integer. Could also be provided in hexadecimal format. Check
     * https://developer.android.com/reference/android/content/Intent.html#setFlags(int)
     * for more details.
     *
     * @param intFlags E.g. 0x0F.
     * @return self instance for chaining.
     */
    public IntentOptions withIntFlags(String intFlags) {
        return assignOptionValue("intFlags", intFlags);
    }

    /**
     * Get intent flags.
     *
     * @return Intent flags encoded into a hexadecimal value.
     */
    public Optional<String> getIntFlags() {
        return getOptionValue("intFlags");
    }

    /**
     * Comma-separated string of intent flag names.
     *
     * @param flags E.g. 'ACTIVITY_CLEAR_TASK' (the 'FLAG_' prefix is optional).
     * @return self instance for chaining.
     */
    public IntentOptions withFlags(String flags) {
        return assignOptionValue("flags", flags);
    }

    /**
     * Get intent flag names.
     *
     * @return Comma-separated string of intent flag names.
     */
    public Optional<String> getFlags() {
        return getOptionValue("flags");
    }

    /**
     * The name of a class inside of the application package that
     * will be used as the component for this Intent.
     *
     * @param className E.g. com.example.app.MainActivity.
     * @return self instance for chaining.
     */
    public IntentOptions withClassName(String className) {
        return assignOptionValue("className", className);
    }

    /**
     * Get class name.
     *
     * @return Class name.
     */
    public Optional<String> getClassName() {
        return getOptionValue("className");
    }

    /**
     * Intent string parameters.
     *
     * @param es Map, where the key is arg parameter name and value is its string value.
     * @return self instance for chaining.
     */
    public IntentOptions withEs(Map<String, String> es) {
        return assignOptionValue("es", es);
    }

    /**
     * Get intent string parameters.
     *
     * @return Intent string parameters mapping.
     */
    public Optional<Map<String, String>> getEs() {
        return getOptionValue("es");
    }

    /**
     * Intent null parameters.
     *
     * @param esn List, where keys are parameter names.
     * @return self instance for chaining.
     */
    public IntentOptions withEsn(List<String> esn) {
        return assignOptionValue("esn", esn);
    }

    /**
     * Get intent null parameters.
     *
     * @return Intent null parameters.
     */
    public Optional<List<String>> getEsn() {
        return getOptionValue("esn");
    }

    /**
     * Intent boolean parameters.
     *
     * @param ez Map, where keys are parameter names and values are booleans.
     * @return self instance for chaining.
     */
    public IntentOptions withEz(Map<String, Boolean> ez) {
        return assignOptionValue("ez", ez);
    }

    /**
     * Get intent boolean parameters.
     *
     * @return Intent boolean parameters.
     */
    public Optional<Map<String, Boolean>> getEz() {
        return getOptionValue("ez");
    }

    /**
     * Intent integer parameters.
     *
     * @param ei Map, where keys are parameter names and values are integers.
     * @return self instance for chaining.
     */
    public IntentOptions withEi(Map<String, Integer> ei) {
        return assignOptionValue("ei", ei);
    }

    private <T> Map<String, T> convertMapValues(Map<String, Object> map, Function<String, T> converter) {
        return map.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey, (entry) -> converter.apply(String.valueOf(entry.getValue())))
                );
    }

    /**
     * Get intent integer parameters.
     *
     * @return Intent integer parameters.
     */
    public Optional<Map<String, Integer>> getEi() {
        Optional<Map<String, Object>> value = getOptionValue("ei");
        return value.map((v) -> convertMapValues(v, Integer::parseInt));
    }

    /**
     * Intent long parameters.
     *
     * @param el Map, where keys are parameter names and values are long numbers.
     * @return self instance for chaining.
     */
    public IntentOptions withEl(Map<String, Long> el) {
        return assignOptionValue("el", el);
    }

    /**
     * Get intent long parameters.
     *
     * @return Intent long parameters.
     */
    public Optional<Map<String, Long>> getEl() {
        Optional<Map<String, Object>> value = getOptionValue("el");
        return value.map((v) -> convertMapValues(v, Long::parseLong));
    }

    /**
     * Intent float parameters.
     *
     * @param ef Map, where keys are parameter names and values are float numbers.
     * @return self instance for chaining.
     */
    public IntentOptions withEf(Map<String, Float> ef) {
        return assignOptionValue("ef", ef);
    }

    /**
     * Get intent float parameters.
     *
     * @return Intent float parameters.
     */
    public Optional<Map<String, Float>> getEf() {
        Optional<Map<String, Object>> value = getOptionValue("ef");
        return value.map((v) -> convertMapValues(v, Float::parseFloat));
    }

    /**
     * Intent URI-data parameters.
     *
     * @param eu Map, where keys are parameter names and values are valid URIs.
     * @return self instance for chaining.
     */
    public IntentOptions withEu(Map<String, String> eu) {
        return assignOptionValue("eu", eu);
    }

    /**
     * Get intent URI parameters.
     *
     * @return Intent URI parameters.
     */
    public Optional<Map<String, String>> getEu() {
        return getOptionValue("eu");
    }

    /**
     * Intent component name parameters.
     *
     * @param ecn Map, where keys are parameter names and values are valid component names.
     * @return self instance for chaining.
     */
    public IntentOptions withEcn(Map<String, String> ecn) {
        return assignOptionValue("ecn", ecn);
    }

    /**
     * Get intent component name parameters.
     *
     * @return Intent component name parameters.
     */
    public Optional<Map<String, String>> getEcn() {
        return getOptionValue("ecn");
    }

    private static Map<String, String> mergeValues(Map<String, ?> map) {
        return map.entrySet().stream()
                .collect(
                        Collectors.toMap(Map.Entry::getKey, (entry) -> ((List<?>) entry.getValue()).stream()
                                .map(String::valueOf)
                                .collect(Collectors.joining(",")))
                );
    }

    /**
     * Intent integer array parameters.
     *
     * @param eia Map, where keys are parameter names and values are lists of integers.
     * @return self instance for chaining.
     */
    public IntentOptions withEia(Map<String, List<Integer>> eia) {
        return assignOptionValue("eia", mergeValues(eia));
    }

    /**
     * Get intent integer array parameters.
     *
     * @return Intent integer array parameters.
     */
    public Optional<Map<String, String>> getEia() {
        return getOptionValue("eia");
    }

    /**
     * Intent long array parameters.
     *
     * @param ela Map, where keys are parameter names and values are lists of long numbers.
     * @return self instance for chaining.
     */
    public IntentOptions withEla(Map<String, List<Long>> ela) {
        return assignOptionValue("ela", mergeValues(ela));
    }

    /**
     * Get intent long array parameters.
     *
     * @return Intent long array parameters.
     */
    public Optional<Map<String, String>> getEla() {
        return getOptionValue("ela");
    }

    /**
     * Intent float array parameters.
     *
     * @param efa Map, where keys are parameter names and values are lists of float numbers.
     * @return self instance for chaining.
     */
    public IntentOptions withEfa(Map<String, List<Float>> efa) {
        return assignOptionValue("efa", mergeValues(efa));
    }

    /**
     * Get intent float array parameters.
     *
     * @return Intent float array parameters.
     */
    public Optional<Map<String, String>> getEfa() {
        return getOptionValue("efa");
    }
}
