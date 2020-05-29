package io.appium.java_client.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class Config {
    private static Config mainInstance = null;
    private static final String MAIN_CONFIG = "main.properties";
    private static final Map<String, Properties> cache = new ConcurrentHashMap<>();
    private final String configName;

    /**
     * Retrieve a facade for the main config.
     *
     * @return interaction helper for 'main.properties' config
     */
    public static synchronized Config main() {
        if (mainInstance == null) {
            mainInstance = new Config(MAIN_CONFIG);
        }
        return mainInstance;
    }

    private Config(String configName) {
        this.configName = configName;
    }

    /**
     * Retrieve a value from properties file.
     *
     * @param <T> the value type.
     * @param key the name of the corresponding key which value to retrieve
     * @param valueType the expected type of the value to be retrieved
     * @return the actual value
     * @throws IllegalArgumentException if the given key does not exist
     * @throws ClassCastException if the retrieved value cannot be cast to `valueType` type
     */
    public <T> T getValue(String key, Class<T> valueType) {
        return getOptionalValue(key, valueType)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("There is no '%s' key in '%s' config", key, configName)
                ));
    }

    /**
     * Retrieve a value from properties file.
     *
     * @param <T> the type of the resulting value.
     * @param key the name of the corresponding key which value to retrieve
     * @param valueType the expected type of the value to be retrieved
     * @return the actual value or {@link Optional#empty()} if the key is not present
     * @throws UncheckedIOException if the given properties file does not exist/not accessible
     * @throws ClassCastException if the retrieved value cannot be cast to `valueType` type
     */
    public <T> Optional<T> getOptionalValue(String key, Class<T> valueType) {
        final Properties cachedProps = cache.computeIfAbsent(configName, (k) -> {
            try (InputStream configFileStream = getClass().getClassLoader().getResourceAsStream(configName)) {
                final Properties p = new Properties();
                p.load(configFileStream);
                return p;
            } catch (IOException e) {
                throw new UncheckedIOException(String.format("Configuration file '%s' cannot be loaded",
                        configName), e);
            }
        });
        return cachedProps.containsKey(key) ? Optional.of(valueType.cast(cachedProps.get(key))) : Optional.empty();
    }
}
