package io.appium.java_client.service.local;

import static io.appium.java_client.TestResources.apiDemosApk;
import static io.appium.java_client.TestUtils.getLocalIp4Address;
import static io.appium.java_client.service.local.AppiumDriverLocalService.buildDefaultService;
import static io.appium.java_client.service.local.AppiumServiceBuilder.APPIUM_PATH;
import static io.appium.java_client.service.local.AppiumServiceBuilder.BROADCAST_IP_ADDRESS;
import static io.appium.java_client.service.local.AppiumServiceBuilder.DEFAULT_APPIUM_PORT;
import static io.appium.java_client.service.local.flags.GeneralServerFlag.BASEPATH;
import static io.appium.java_client.service.local.flags.GeneralServerFlag.CALLBACK_ADDRESS;
import static io.appium.java_client.service.local.flags.GeneralServerFlag.SESSION_OVERRIDE;
import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver;
import static java.lang.System.getProperty;
import static java.lang.System.setProperty;
import static java.nio.file.FileSystems.getDefault;
import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class ServerBuilderTest {

    /**
     * It may be impossible to find the path to the instance of appium server due to different circumstance.
     * So user may use environment variables/system properties to define the full path to the server
     * main.js that is supposed to be default.
     */
    private static final String PATH_TO_APPIUM_NODE_IN_PROPERTIES = getProperty(APPIUM_PATH);

    private static final Path ROOT_TEST_PATH = getDefault().getPath("src")
            .resolve("test").resolve("java").resolve("io").resolve("appium").resolve("java_client");

    /**
     * This is the path to the stub main.js file
     */
    private static final Path PATH_T0_TEST_MAIN_JS = ROOT_TEST_PATH
            .resolve("service").resolve("local").resolve("main.js");

    private static String testIP;
    private AppiumDriverLocalService service;
    private File testLogFile;
    private OutputStream stream;
    private static WebDriverManager chromeManager;

    /**
     * initialization.
     */
    @BeforeAll
    public static void beforeClass() throws Exception {
        testIP = getLocalIp4Address();
        chromeManager = chromedriver();
        chromeManager.setup();
    }

    @AfterEach
    public void tearDown() throws Exception {
        ofNullable(service).ifPresent(AppiumDriverLocalService::stop);

        if (stream != null) {
            stream.close();
        }

        ofNullable(testLogFile).ifPresent(savedTestLogFile -> {
            if (savedTestLogFile.exists()) {
                savedTestLogFile.delete();
            }
        });

        System.clearProperty(APPIUM_PATH);
        ofNullable(PATH_TO_APPIUM_NODE_IN_PROPERTIES).ifPresent(s -> setProperty(APPIUM_PATH, s));
    }

    @Test
    public void checkAbilityToAddLogMessageConsumer() {
        List<String> log = new ArrayList<>();
        service = buildDefaultService();
        service.clearOutPutStreams();
        service.addLogMessageConsumer(log::add);
        service.start();
        assertTrue(log.size() > 0);
    }

    @Test
    public void checkAbilityToStartDefaultService() {
        service = buildDefaultService();
        service.start();
        assertTrue(service.isRunning());
    }

    @Test
    public void checkAbilityToFindNodeDefinedInProperties() {
        File definedNode = PATH_T0_TEST_MAIN_JS.toFile();
        setProperty(APPIUM_PATH, definedNode.getAbsolutePath());
        assertThat(new AppiumServiceBuilder().createArgs().get(0), is(definedNode.getAbsolutePath()));
    }

    @Test
    public void checkAbilityToUseNodeDefinedExplicitly() {
        File mainJS = PATH_T0_TEST_MAIN_JS.toFile();
        AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .withAppiumJS(mainJS);
        assertThat(builder.createArgs().get(0),
                is(mainJS.getAbsolutePath()));
    }

    @Test
    public void checkAbilityToStartServiceOnAFreePort() {
        service = new AppiumServiceBuilder().usingAnyFreePort().build();
        service.start();
        assertTrue(service.isRunning());
    }

    @Test
    public void checkAbilityToStartServiceUsingNonLocalhostIP() {
        service = new AppiumServiceBuilder().withIPAddress(testIP).build();
        service.start();
        assertTrue(service.isRunning());
    }

    @Test
    public void checkAbilityToStartServiceUsingFlags() {
        service = new AppiumServiceBuilder()
                .withArgument(CALLBACK_ADDRESS, testIP)
                .withArgument(SESSION_OVERRIDE)
                .build();
        service.start();
        assertTrue(service.isRunning());
    }

    @Test
    public void checkAbilityToStartServiceUsingCapabilities() {
        UiAutomator2Options options = new UiAutomator2Options()
                .fullReset()
                .setNewCommandTimeout(Duration.ofSeconds(60))
                .setAppPackage("io.appium.android.apis")
                .setAppActivity(".view.WebView1")
                .setApp(apiDemosApk().toAbsolutePath().toString())
                .setChromedriverExecutable(chromeManager.getDownloadedDriverPath());

        service = new AppiumServiceBuilder().withCapabilities(options).build();
        service.start();
        assertTrue(service.isRunning());
    }

    @Test
    public void checkAbilityToStartServiceUsingCapabilitiesAndFlags() {
        File app = ROOT_TEST_PATH.resolve("ApiDemos-debug.apk").toFile();

        UiAutomator2Options options = new UiAutomator2Options()
                .fullReset()
                .setNewCommandTimeout(Duration.ofSeconds(60))
                .setAppPackage("io.appium.android.apis")
                .setAppActivity(".view.WebView1")
                .setApp(app.getAbsolutePath())
                .setChromedriverExecutable(chromeManager.getDownloadedDriverPath())
                .amend("winPath", "C:\\selenium\\app.apk")
                .amend("unixPath", "/selenium/app.apk")
                .amend("quotes", "\"'")
                .setChromeOptions(
                        ImmutableMap.of("env", ImmutableMap.of("test", "value"), "val2", 0)
                );

        service = new AppiumServiceBuilder()
                .withArgument(CALLBACK_ADDRESS, testIP)
                .withArgument(SESSION_OVERRIDE)
                .withCapabilities(options).build();
        service.start();
        assertTrue(service.isRunning());
    }

    @Test
    public void checkAbilityToChangeOutputStream() throws Exception {
        testLogFile = new File("test");
        testLogFile.createNewFile();
        stream = new FileOutputStream(testLogFile);
        service = buildDefaultService();
        service.addOutPutStream(stream);
        service.start();
        assertThat(testLogFile.length(), greaterThan(0L));
    }

    @Test
    public void checkAbilityToChangeOutputStreamAfterTheServiceIsStarted() throws Exception {
        testLogFile = new File("test");
        testLogFile.createNewFile();
        stream = new FileOutputStream(testLogFile);
        service = buildDefaultService();
        service.start();
        service.addOutPutStream(stream);
        service.isRunning();
        assertThat(testLogFile.length(), greaterThan(0L));
    }

    @Test
    public void checkAbilityToShutDownService() {
        service = buildDefaultService();
        service.start();
        service.stop();
        assertFalse(service.isRunning());
    }

    @Test
    public void checkAbilityToStartAndShutDownFewServices() throws Exception {
        List<AppiumDriverLocalService> services = asList(
                new AppiumServiceBuilder().usingAnyFreePort().build(),
                new AppiumServiceBuilder().usingAnyFreePort().build(),
                new AppiumServiceBuilder().usingAnyFreePort().build(),
                new AppiumServiceBuilder().usingAnyFreePort().build());
        services.parallelStream().forEach(AppiumDriverLocalService::start);
        assertTrue(services.stream().allMatch(AppiumDriverLocalService::isRunning));
        SECONDS.sleep(1);
        services.parallelStream().forEach(AppiumDriverLocalService::stop);
        assertTrue(services.stream().noneMatch(AppiumDriverLocalService::isRunning));
    }

    @Test
    public void checkAbilityToStartServiceWithLogFile() throws Exception {
        testLogFile = new File("Log.txt");
        testLogFile.createNewFile();
        service = new AppiumServiceBuilder().withLogFile(testLogFile).build();
        service.start();
        assertTrue(testLogFile.exists());
        assertThat(testLogFile.length(), greaterThan(0L));
    }

    @Test
    public void checkAbilityToStartServiceWithPortUsingFlag() {
        String port = "8996";
        String expectedUrl = String.format("http://0.0.0.0:%s/", port);

        service = new AppiumServiceBuilder()
                .withArgument(() -> "--port", port)
                .build();
        String actualUrl = service.getUrl().toString();
        assertEquals(expectedUrl, actualUrl);
        service.start();
    }

    @Test
    public void checkAbilityToStartServiceWithPortUsingShortFlag() {
        String port = "8996";
        String expectedUrl = String.format("http://0.0.0.0:%s/", port);

        service = new AppiumServiceBuilder()
                .withArgument(() -> "-p", port)
                .build();
        String actualUrl = service.getUrl().toString();
        assertEquals(expectedUrl, actualUrl);
        service.start();
    }

    @Test
    public void checkAbilityToStartServiceWithIpUsingFlag() {
        String expectedUrl = String.format("http://%s:4723/", testIP);

        service = new AppiumServiceBuilder()
                .withArgument(() -> "--address", testIP)
                .build();
        String actualUrl = service.getUrl().toString();
        assertEquals(expectedUrl, actualUrl);
        service.start();
    }

    @Test
    public void checkAbilityToStartServiceWithIpUsingShortFlag() {
        String expectedUrl = String.format("http://%s:4723/", testIP);

        service = new AppiumServiceBuilder()
                .withArgument(() -> "-a", testIP)
                .build();
        String actualUrl = service.getUrl().toString();
        assertEquals(expectedUrl, actualUrl);
        service.start();
    }

    @Test
    public void checkAbilityToStartServiceWithLogFileUsingFlag() {
        testLogFile = new File("Log2.txt");

        service = new AppiumServiceBuilder()
                .withArgument(() -> "--log", testLogFile.getAbsolutePath())
                .build();
        service.start();
        assertTrue(testLogFile.exists());
    }

    @Test
    public void checkAbilityToStartServiceWithLogFileUsingShortFlag() {
        testLogFile = new File("Log3.txt");

        service = new AppiumServiceBuilder()
                .withArgument(() -> "-g", testLogFile.getAbsolutePath())
                .build();
        service.start();
        assertTrue(testLogFile.exists());
    }

    @Test
    public void checkAbilityToStartServiceUsingValidBasePathWithMultiplePathParams() {
        String baseUrl = String.format("http://%s:%d/", BROADCAST_IP_ADDRESS, DEFAULT_APPIUM_PORT);
        String basePath = "wd/hub";
        service = new AppiumServiceBuilder().withArgument(BASEPATH, basePath).build();
        service.start();
        assertTrue(service.isRunning());
        assertEquals(baseUrl + basePath + "/", service.getUrl().toString());
    }

    @Test
    public void checkAbilityToStartServiceUsingValidBasePathWithSinglePathParams() {
        String baseUrl = String.format("http://%s:%d/", BROADCAST_IP_ADDRESS, DEFAULT_APPIUM_PORT);
        String basePath = "/wd/";
        service = new AppiumServiceBuilder().withArgument(BASEPATH, basePath).build();
        service.start();
        assertTrue(service.isRunning());
        assertEquals(baseUrl + basePath.substring(1), service.getUrl().toString());
    }

    @Test
    public void checkAbilityToValidateBasePathForEmptyBasePath() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AppiumServiceBuilder().withArgument(BASEPATH, "").build();
        });
    }

    @Test
    public void checkAbilityToValidateBasePathForBlankBasePath() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AppiumServiceBuilder().withArgument(BASEPATH, "   ").build();
        });
    }

    @Test
    public void checkAbilityToValidateBasePathForNullBasePath() {
        assertThrows(NullPointerException.class, () -> {
            new AppiumServiceBuilder().withArgument(BASEPATH, null).build();
        });
    }
}
