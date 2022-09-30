package io.appium.java_client;

import io.appium.java_client.internal.Config;
import org.openqa.selenium.BuildInfo;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.http.Filter;
import org.openqa.selenium.remote.http.HttpHandler;

import java.util.Locale;

/**
 * Manage Appium Client configurations.
 */

public class AddAppiumUserAgent implements Filter {

    public static final String VERSION_KEY = "appiumClient.version";

    /**
     * A custom User Agent name for Appium Java client.
     * e.g. appium/8.3.0 (selenium/4.5.0 (java mac))
     */
    public static final String USER_AGENT = String.format(
            "appium/%s (selenium/%s (java %s))",
            Config.main().getValue(VERSION_KEY, String.class),
            new BuildInfo().getReleaseLabel(),
            (Platform.getCurrent().family() == null
                    ? Platform.getCurrent().toString().toLowerCase(Locale.US)
                    : Platform.getCurrent().family().toString().toLowerCase(Locale.US)));

    @Override
    public HttpHandler apply(HttpHandler next) {

        return req -> {
            if (req.getHeader("User-Agent") == null) {
                req.addHeader("User-Agent", USER_AGENT);
            }

            return next.execute(req);
        };
    }
}
