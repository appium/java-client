package io.appium.java_client;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestUtils {
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
}
