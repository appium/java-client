package io.appium.java_client.service.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

/**
 * This class provides context to a Slf4jLogMessageConsumer. 
 * 
 */
public final class Slf4jLogMessageContext {
    private final Logger logger;
    private final Level level;

    Slf4jLogMessageContext(String loggerName, Level level) {
        this.level = level;
        this.logger = LoggerFactory.getLogger(loggerName);
    }

    /**
     * Returns the {@link Logger} instance associated with this context.
     * 
     * @return {@link Logger} instance associated with this context.
     * 
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Returns log {@link Level} for the log message associated with this context.
     * 
     * @return {@link Level} for log message associated with this context.
     */
    public Level getLevel() {
        return level;
    }

    /**
     * Returns the name of the {@link Logger} associated with this context.
     * 
     * @return name of {@link Logger} associated with this context.
     */
    public String getName() {
        return logger.getName();
    }
}
