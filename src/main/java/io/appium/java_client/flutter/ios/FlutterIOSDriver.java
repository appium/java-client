package io.appium.java_client.flutter.ios;

import io.appium.java_client.AppiumClientConfig;
import io.appium.java_client.flutter.FlutterDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.remote.http.HttpClient;

import java.net.URL;

/**
 * Custom IOSDriver implementation with additional Flutter-specific capabilities.
 */
public class FlutterIOSDriver extends IOSDriver implements FlutterDriver {

    public FlutterIOSDriver(HttpCommandExecutor executor, Capabilities capabilities) {
        super(executor, capabilities);
    }

    public FlutterIOSDriver(URL remoteAddress, Capabilities capabilities) {
        super(remoteAddress, capabilities);
    }

    public FlutterIOSDriver(URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(remoteAddress, httpClientFactory, capabilities);
    }

    public FlutterIOSDriver(AppiumDriverLocalService service, Capabilities capabilities) {
        super(service, capabilities);
    }

    public FlutterIOSDriver(
            AppiumDriverLocalService service, HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(service, httpClientFactory, capabilities);
    }

    public FlutterIOSDriver(AppiumServiceBuilder builder, Capabilities capabilities) {
        super(builder, capabilities);
    }

    public FlutterIOSDriver(
            AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(builder, httpClientFactory, capabilities);
    }

    public FlutterIOSDriver(HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(httpClientFactory, capabilities);
    }

    public FlutterIOSDriver(ClientConfig clientConfig, Capabilities capabilities) {
        super(clientConfig, capabilities);
    }

    public FlutterIOSDriver(AppiumClientConfig appiumClientConfig, Capabilities capabilities) {
        super(appiumClientConfig, capabilities);
    }

    public FlutterIOSDriver(URL remoteSessionAddress) {
        super(remoteSessionAddress);
    }

    public FlutterIOSDriver(Capabilities capabilities) {
        super(capabilities);
    }
}