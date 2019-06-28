package io.appium.java_client.driverscripts;

import javax.annotation.Nullable;
import java.util.Map;

public class ScriptValue {
    private final Object result;
    private final Map<String, Object> logs;

    public ScriptValue(Object result, Map<String, Object> logs) {
        this.result = result;
        this.logs = logs;
    }

    /**
     * The result of ExecuteDriverScript call.
     *
     * @return The actual returned value depends on the script content
     */
    @Nullable
    public Object getResult() {
        return result;
    }

    /**
     * Retrieves logs mapping from ExecuteDriverScript call.
     *
     * @return Mapping keys are log levels, for example `warn` or
     * `error` and the values are lists of strings that were printed
     * by the script into the corresponding logging level
     */
    public Map<String, Object> getLogs() {
        return logs;
    }
}
