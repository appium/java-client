package io.appium.java_client.android;

import static org.junit.Assert.assertTrue;

import io.appium.java_client.ws.StringMessagesHandler;
import org.junit.Test;

import java.time.Duration;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class AndroidLogcatListenerTest extends BaseAndroidTest {

    @Test
    public void verifyLogcatListenerCanBeAssigned() {
        final Semaphore messageSemaphore = new Semaphore(1);
        final Semaphore connectedSemaphore = new Semaphore(1);
        final Duration timeout = Duration.ofSeconds(5);

        try {
            driver.startLogcatBroadcast();
            driver.addLogcatListener(new StringMessagesHandler() {
                @Override
                public void onMessage(String message) {
                    messageSemaphore.release();
                }

                @Override
                public void onConnected() {
                    connectedSemaphore.release();
                }

                @Override
                public void onDisconnected() {
                    // ignore
                }

                @Override
                public void onError(Throwable reason) {
                    // ignore
                }
            });

            connectedSemaphore.acquire();
            messageSemaphore.acquire();

            assertTrue(String.format("Didn't connect to the web socket after %s timeout", timeout),
                    connectedSemaphore.tryAcquire(timeout.toMillis(), TimeUnit.MILLISECONDS));
            assertTrue(String.format("Didn't receive any log message after %s timeout", timeout),
                    messageSemaphore.tryAcquire(timeout.toMillis(), TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            connectedSemaphore.release();
            messageSemaphore.release();
            driver.stopLogcatBroadcast();
        }
    }
}
