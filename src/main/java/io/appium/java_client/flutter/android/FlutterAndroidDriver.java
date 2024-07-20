package io.appium.java_client.flutter.android;

import io.appium.java_client.AppiumClientConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.flutter.FlutterDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.remote.http.HttpClient;

import java.net.URL;

/**
 * Custom AndroidDriver implementation with additional Flutter-specific capabilities.
 */
public class FlutterAndroidDriver extends AndroidDriver implements FlutterDriver {

    public FlutterAndroidDriver(HttpCommandExecutor executor, Capabilities capabilities) {
        super(executor, capabilities);
    }

    public FlutterAndroidDriver(URL remoteAddress, Capabilities capabilities) {
        super(remoteAddress, capabilities);
    }

    public FlutterAndroidDriver(URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(remoteAddress, httpClientFactory, capabilities);
    }

    public FlutterAndroidDriver(AppiumDriverLocalService service, Capabilities capabilities) {
        super(service, capabilities);
    }

    public FlutterAndroidDriver(
            AppiumDriverLocalService service, HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(service, httpClientFactory, capabilities);
    }

    public FlutterAndroidDriver(AppiumServiceBuilder builder, Capabilities capabilities) {
        super(builder, capabilities);
    }

    public FlutterAndroidDriver(
            AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(builder, httpClientFactory, capabilities);
    }

    public FlutterAndroidDriver(HttpClient.Factory httpClientFactory, Capabilities capabilities) {
        super(httpClientFactory, capabilities);
    }

    public FlutterAndroidDriver(ClientConfig clientConfig, Capabilities capabilities) {
        super(clientConfig, capabilities);
    }

    public FlutterAndroidDriver(AppiumClientConfig appiumClientConfig, Capabilities capabilities) {
        super(appiumClientConfig, capabilities);
    }

    public FlutterAndroidDriver(Capabilities capabilities) {
        super(capabilities);
    }

    public FlutterAndroidDriver(URL remoteSessionAddress, String automationName) {
        super(remoteSessionAddress, automationName);
    }

    @Override
    public RemoteWebDriver getDriver() {
        return this;
    }
}
