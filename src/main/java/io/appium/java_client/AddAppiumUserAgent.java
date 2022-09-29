package io.appium.java_client;

import org.openqa.selenium.BuildInfo;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.http.Filter;
import org.openqa.selenium.remote.http.HttpHandler;

import java.util.Locale;

/**
 * Manage Appium Client configurations.
 */

public class AddAppiumUserAgent implements Filter {

    public static final String USER_AGENT = String.format(
            "appium/%s (selenium/%s (java %s))",
            "TODO: appium version",
            new BuildInfo().getReleaseLabel(),
            (Platform.getCurrent().family() == null ?
                    Platform.getCurrent().toString().toLowerCase(Locale.US) :
                    Platform.getCurrent().family().toString().toLowerCase(Locale.US)));

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
