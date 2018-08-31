package io.appium.java_client.service.local;

import static io.appium.java_client.service.local.AppiumDriverLocalService.parseSlf4jContextFromLogMessage;
import static org.junit.Assert.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;
import static org.slf4j.event.Level.DEBUG;
import static org.slf4j.event.Level.INFO;

import org.junit.Test;
import org.slf4j.event.Level;

public class AppiumDriverLocalServiceTest {
	
	@Test public void canParseSlf4jLoggerContext() throws Exception {
		assertLoggerContext(INFO, "appium.service.androidbootstrap", "[AndroidBootstrap] [BOOTSTRAP LOG] [debug] json loading complete.");
		assertLoggerContext(INFO, "appium.service.adb", "[ADB] Cannot read version codes of ");
		assertLoggerContext(INFO, "appium.service.xcuitest", "[XCUITest] Determining device to run tests on: udid: '1234567890', real device: true");
		assertLoggerContext(INFO, "appium.service", "no-prefix log message.");
		assertLoggerContext(INFO, "appium.service", "no-prefix log [not-a-logger-name] message.");
		assertLoggerContext(DEBUG, "appium.service.mjsonwp", "[debug] [MJSONWP] Calling AppiumDriver.getStatus() with args: []");
		assertLoggerContext(DEBUG, "appium.service.xcuitest", "[debug] [XCUITest] Xcode version set to 'x.y.z' ");
		assertLoggerContext(DEBUG, "appium.service.jsonwpproxy", "[debug] [JSONWP Proxy] Proxying [GET /status] to [GET http://localhost:18218/status] with no body");
		
	}
	
	private void assertLoggerContext(Level expectedLevel, String expectedLoggerName, String logMessage) {
		Slf4jLogMessageContext ctx = parseSlf4jContextFromLogMessage(logMessage);
		assertEquals(expectedLoggerName, ctx.name());
		assertEquals(expectedLevel, ctx.level());
		assertEquals(getLogger(expectedLoggerName), ctx.logger());
	}
}
