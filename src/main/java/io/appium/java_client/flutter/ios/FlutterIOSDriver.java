package io.appium.java_client.flutter.ios;

import io.appium.java_client.AppiumClientConfig;
import io.appium.java_client.flutter.FlutterIntegrationTestDriver;
import io.appium.java_client.http.HttpClient;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AppiumCommandExecutor;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Capabilities;

import java.net.URL;

/**
 * Custom IOSDriver implementation with additional Flutter-specific capabilities.
 */
public class FlutterIOSDriver extends IOSDriver implements FlutterIntegrationTestDriver {

    /**
     * Creates a new instance based on command {@code executor} and {@code capabilities}.
     *
     * @param executor the command executor that sends the commands to the server
     * @param capabilities the capabilities of the session to create
     */
    public FlutterIOSDriver(AppiumCommandExecutor executor, Capabilities capabilities) {
        super(executor, capabilities);
    }

    /**
     * Creates a new instance based on the server address and {@code capabilities}.
     *
     * @param remoteAddress the address of the remote Appium server
     * @param capabilities the capabilities of the session to create
     */
    public FlutterIOSDriver(URL remoteAddress, Capabilities capabilities) {
        super(remoteAddress, capabilities);
    }

    /**
     * Creates a new instance based on the server address, a custom HTTP client factory
     * and {@code capabilities}.
     *
     * @param remoteAddress the address of the remote Appium server
     * @param httpClientFactory the factory that creates the HTTP client
     * @param capabilities the capabilities of the session to create
     */
    public FlutterIOSDriver(URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(remoteAddress, httpClientFactory, capabilities);
    }

    /**
     * Creates a new instance based on the local Appium service and {@code capabilities}.
     *
     * @param service the local Appium service to start and connect to
     * @param capabilities the capabilities of the session to create
     */
    public FlutterIOSDriver(AppiumDriverLocalService service, Capabilities capabilities) {
        super(service, capabilities);
    }

    /**
     * Creates a new instance based on the local Appium service, a custom HTTP client factory
     * and {@code capabilities}.
     *
     * @param service the local Appium service to start and connect to
     * @param httpClientFactory the factory that creates the HTTP client
     * @param capabilities the capabilities of the session to create
     */
    public FlutterIOSDriver(
            AppiumDriverLocalService service, HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(service, httpClientFactory, capabilities);
    }

    /**
     * Creates a new instance based on the local Appium service builder and {@code capabilities}.
     *
     * @param builder the builder of the local Appium service to start and connect to
     * @param capabilities the capabilities of the session to create
     */
    public FlutterIOSDriver(AppiumServiceBuilder builder, Capabilities capabilities) {
        super(builder, capabilities);
    }

    /**
     * Creates a new instance based on the local Appium service builder, a custom HTTP client
     * factory and {@code capabilities}.
     *
     * @param builder the builder of the local Appium service to start and connect to
     * @param httpClientFactory the factory that creates the HTTP client
     * @param capabilities the capabilities of the session to create
     */
    public FlutterIOSDriver(
            AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(builder, httpClientFactory, capabilities);
    }

    /**
     * Creates a new instance based on a custom HTTP client factory and {@code capabilities}.
     * The default local Appium service is used.
     *
     * @param httpClientFactory the factory that creates the HTTP client
     * @param capabilities the capabilities of the session to create
     */
    public FlutterIOSDriver(HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(httpClientFactory, capabilities);
    }

    /**
     * Creates a new instance based on the HTTP client configuration and {@code capabilities}.
     *
     * @param appiumClientConfig the HTTP client configuration
     * @param capabilities the capabilities of the session to create
     */
    public FlutterIOSDriver(AppiumClientConfig appiumClientConfig, Capabilities capabilities) {
        super(appiumClientConfig, capabilities);
    }

    /**
     * Connects to a running session at the given address.
     * This API is supposed to be used for <b>debugging purposes only</b>.
     *
     * @param remoteSessionAddress the address of the <b>running</b> session including the session identifier
     */
    public FlutterIOSDriver(URL remoteSessionAddress) {
        super(remoteSessionAddress);
    }

    /**
     * Creates a new instance based on {@code capabilities} using the default local Appium service.
     *
     * @param capabilities the capabilities of the session to create
     */
    public FlutterIOSDriver(Capabilities capabilities) {
        super(capabilities);
    }
}
