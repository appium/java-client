package io.appium.java_client.android;

import static org.junit.Assert.assertTrue;

import io.appium.java_client.ws.MessagesHandler;
import org.junit.Test;

import java.time.Duration;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class AndroidLogcatListenerTest extends BaseAndroidTest {

    @Test
    public void verifyLogcatListenerCanBeAssigned() {
        final Semaphore messageSemaphore = new Semaphore(1);
        final Duration timeout = Duration.ofSeconds(5);

        driver.addLogcatListener(new MessagesHandler<String>() {
            @Override
            public void onMessage(String message) {
                messageSemaphore.release();
            }

            @Override
            public void onConnected() {
                System.out.println("Connected to web socket");
            }

            @Override
            public void onDisconnected() {
                System.out.println("Disconnected from web socket");
            }

            @Override
            public void onError(Throwable cause) {
                cause.printStackTrace();
            }
        });
        try {
            driver.startLogcatBroadcast();
            messageSemaphore.acquire();
            assertTrue(String.format("Didn't receive any log message after %s timeout", timeout),
                    messageSemaphore.tryAcquire(timeout.toMillis(), TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        } finally {
            messageSemaphore.release();
            driver.stopLogcatBroadcast();
        }
    }
}
