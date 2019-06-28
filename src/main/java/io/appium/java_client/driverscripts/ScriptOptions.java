package io.appium.java_client.driverscripts;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.ofNullable;


public class ScriptOptions {
    private ScriptType scriptType = ScriptType.WEBDRIVERIO;
    private Long timeoutMs;

    /**
     * Sets the script type.
     *
     * @param type the actual script type
     * @return self instance for chaining
     */
    public ScriptOptions withScriptType(ScriptType type) {
        this.scriptType = checkNotNull(type);
        return this;
    }

    /**
     * Sets the script execution timeout.
     * If this is not set the the maximum duration of the script
     * is not limited (e. g. may block forever)
     *
     * @param timeoutMs the timeout in milliseconds
     * @return self instance for chaining
     */
    public ScriptOptions withTimeout(long timeoutMs) {
        this.timeoutMs = timeoutMs;
        return this;
    }

    /**
     * Builds a values map for further usage in HTTP requests to Appium.
     *
     * @return The map containing the provided options
     */
    public Map<String, Object> build() {
        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        ofNullable(scriptType).map(x -> builder.put("type", x.name().toLowerCase()));
        ofNullable(timeoutMs).map(x -> builder.put("timeout", x));
        return builder.build();
    }
}
