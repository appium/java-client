package io.appium.java_client;

import static java.nio.file.FileSystems.getDefault;
import static org.openqa.selenium.Platform.MAC;
import static org.openqa.selenium.Platform.WINDOWS;
import static org.openqa.selenium.Platform.getCurrent;

import org.openqa.selenium.Platform;

import java.io.File;
import java.nio.file.Path;

public final class ChromeDriverPathUtil {
    private static final Path ROOT_TEST_PATH = getDefault().getPath("src")
            .resolve("test").resolve("java").resolve("io").resolve("appium").resolve("java_client");

    /**
     * @return the choromedriver file which depends on platform.
     */
    public static File getChromeDriver() {
        Path resultPath;
        Platform current = getCurrent();

        if (current.is(WINDOWS)) {
            resultPath = ROOT_TEST_PATH.resolve("chromedriver.exe");
        } else if (current.is(MAC)) {
            resultPath = ROOT_TEST_PATH.resolve("chromedriver_mac");
        } else {
            resultPath = ROOT_TEST_PATH.resolve("chromedriver_linux");
        }

        return resultPath.toFile();
    }
}
