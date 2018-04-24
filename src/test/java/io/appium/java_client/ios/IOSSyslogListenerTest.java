package io.appium.java_client.ios;

import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.junit.Test;

import java.time.Duration;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class IOSSyslogListenerTest extends UICatalogIOSTest {

    @Test
    public void verifySyslogListenerCanBeAssigned() {
        final Semaphore messageSemaphore = new Semaphore(1);
        final Duration timeout = Duration.ofSeconds(15);

        driver.addSyslogMessagesListener((msg) -> messageSemaphore.release());
        driver.addSyslogConnectionListener(() -> System.out.println("Connected to the web socket"));
        driver.addSyslogDisconnectionListener(() -> System.out.println("Disconnected from the web socket"));
        driver.addSyslogErrorsListener(Throwable::printStackTrace);
        try {
            driver.startSyslogBroadcast();
            messageSemaphore.acquire();
            // This is needed for pushing some internal log messages
            driver.runAppInBackground(Duration.ofSeconds(1));
            assertTrue(String.format("Didn't receive any log message after %s timeout",
                    DurationFormatUtils.formatDuration(timeout.toMillis(), "H:mm:ss", true)),
                    messageSemaphore.tryAcquire(timeout.toMillis(), TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        } finally {
            messageSemaphore.release();
            driver.stopSyslogBroadcast();
        }
    }
}
