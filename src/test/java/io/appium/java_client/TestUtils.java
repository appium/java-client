package io.appium.java_client;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.function.Supplier;

public class TestUtils {
    private TestUtils() {
    }

    public static String getLocalIp4Address() throws SocketException, UnknownHostException {
        // https://stackoverflow.com/questions/9481865/getting-the-ip-address-of-the-current-machine-using-java
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            return socket.getLocalAddress().getHostAddress();
        }
    }

    public static Path resourcePathToLocalPath(String resourcePath) {
        URL url = ClassLoader.getSystemResource(resourcePath);
        if (url == null) {
            throw new IllegalArgumentException(String.format("Cannot find the '%s' resource", resourcePath));
        }
        return Paths.get(url.getPath());
    }

    public static String resourceAsString(String resourcePath) {
        try {
            return new String(Files.readAllBytes(resourcePathToLocalPath(resourcePath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void waitUntilTrue(Supplier<Boolean> func, Duration timeout, Duration interval) {
        long started = System.currentTimeMillis();
        RuntimeException lastError = null;
        while (System.currentTimeMillis() - started < timeout.toMillis()) {
            lastError = null;
            try {
                Boolean result = func.get();
                if (result != null && result) {
                    return;
                }
                //noinspection BusyWait
                Thread.sleep(interval.toMillis());
            } catch (RuntimeException | InterruptedException e) {
                if (e instanceof InterruptedException) {
                    throw new RuntimeException(e);
                } else {
                    lastError = (RuntimeException) e;
                }
            }
        }
        if (lastError != null) {
            throw lastError;
        }
        throw new TimeoutException(String.format("Condition unmet after %sms timeout", timeout.toMillis()));
    }

    public static Point getCenter(WebElement webElement) {
        return getCenter(webElement, null);
    }

    public static Point getCenter(WebElement webElement, @Nullable Point location) {
        Dimension dim = webElement.getSize();
        if (location == null) {
            location = webElement.getLocation();
        }
        return new Point(location.x + dim.width / 2, location.y + dim.height / 2);
    }
}
