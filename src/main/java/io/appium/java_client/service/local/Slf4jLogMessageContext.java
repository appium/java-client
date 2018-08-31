package io.appium.java_client.service.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

/**
 * This class provides context to a Slf4jLogMessageConsumer. 
 * 
 * @see {@link AppiumDriverLocalService#addSlf4jLogMessageConsumer(java.util.function.BiConsumer)}
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
	 * @return {@link Logger} instance associated with this context.
	 * 
	 */
	public Logger logger() {
		return logger;
	}

	/**
	 * @return {@link Level} for log message associated with this context.
	 */
	public Level level() {
		return level;
	}

	/**
	 * @return name of {@link Logger} associated with this context.
	 */
	public String name() {
		return logger.getName();
	}

}