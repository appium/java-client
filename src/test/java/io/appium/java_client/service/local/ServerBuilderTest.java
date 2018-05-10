package io.appium.java_client.service.local;

import static io.appium.java_client.remote.AndroidMobileCapabilityType.APP_ACTIVITY;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.APP_PACKAGE;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE;
import static io.appium.java_client.remote.MobileCapabilityType.APP;
import static io.appium.java_client.remote.MobileCapabilityType.FULL_RESET;
import static io.appium.java_client.remote.MobileCapabilityType.NEW_COMMAND_TIMEOUT;
import static io.appium.java_client.remote.MobileCapabilityType.PLATFORM_NAME;
import static io.appium.java_client.service.local.AppiumDriverLocalService.buildDefaultService;
import static io.appium.java_client.service.local.AppiumServiceBuilder.APPIUM_PATH;
import static io.appium.java_client.service.local.flags.GeneralServerFlag.CALLBACK_ADDRESS;
import static io.appium.java_client.service.local.flags.GeneralServerFlag.PRE_LAUNCH;
import static io.appium.java_client.service.local.flags.GeneralServerFlag.SESSION_OVERRIDE;
import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver;
import static java.lang.System.getProperty;
import static java.lang.System.setProperty;
import static java.nio.file.FileSystems.getDefault;
import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.List;

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

    private static String getLocalIP(NetworkInterface intf) {
        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
                .hasMoreElements(); ) {
            InetAddress inetAddress = enumIpAddr.nextElement();
            if (!inetAddress.isLoopbackAddress()) {
                InetAddressValidator validator = InetAddressValidator.getInstance();
                String calculated = inetAddress.getHostAddress();
                if (validator.isValid(calculated)) {
                    return calculated;
                }
            }
        }
        return null;
    }

    /**
     * initialization.
     */
    @BeforeClass public static void beforeClass() throws Exception {
        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
                .hasMoreElements(); ) {
            NetworkInterface intf = en.nextElement();
            if ((testIP = getLocalIP(intf)) != null) {
                break;
            }
        }
        chromeManager = chromedriver();
        chromeManager.setup();
    }

    @After
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

    @Test public void checkAbilityToStartDefaultService() {
        service = buildDefaultService();
        service.start();
        assertEquals(true, service.isRunning());
    }

    @Test public void checkAbilityToFindNodeDefinedInProperties() {
        File definedNode = PATH_T0_TEST_MAIN_JS.toFile();
        setProperty(APPIUM_PATH, definedNode.getAbsolutePath());
        assertThat(new AppiumServiceBuilder().createArgs().get(0), is(definedNode.getAbsolutePath()));
    }

    @Test public void checkAbilityToUseNodeDefinedExplicitly() {
        File mainJS = PATH_T0_TEST_MAIN_JS.toFile();
        AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .withAppiumJS(mainJS);
        assertThat(builder.createArgs().get(0),
                is(mainJS.getAbsolutePath()));
    }

    @Test public void checkAbilityToStartServiceOnAFreePort() {
        service = new AppiumServiceBuilder().usingAnyFreePort().build();
        service.start();
        assertTrue(service.isRunning());
    }

    @Test public void checkAbilityToStartServiceUsingNonLocalhostIP() {
        service = new AppiumServiceBuilder().withIPAddress(testIP).build();
        service.start();
        assertTrue(service.isRunning());
    }

    @Test public void checkAbilityToStartServiceUsingFlags() {
        service = new AppiumServiceBuilder()
            .withArgument(CALLBACK_ADDRESS, testIP)
            .withArgument(SESSION_OVERRIDE)
            .withArgument(PRE_LAUNCH)
            .build();
        service.start();
        assertTrue(service.isRunning());
    }

    @Test public void checkAbilityToStartServiceUsingCapabilities() {
        File app = ROOT_TEST_PATH.resolve("ApiDemos-debug.apk").toFile();

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(PLATFORM_NAME, "Android");
        caps.setCapability(FULL_RESET, true);
        caps.setCapability(NEW_COMMAND_TIMEOUT, 60);
        caps.setCapability(APP_PACKAGE, "io.appium.android.apis");
        caps.setCapability(APP_ACTIVITY, ".view.WebView1");
        caps.setCapability(APP, app.getAbsolutePath());
        caps.setCapability(CHROMEDRIVER_EXECUTABLE, chromeManager.getBinaryPath());

        service = new AppiumServiceBuilder().withCapabilities(caps).build();
        service.start();
        assertTrue(service.isRunning());
    }

    @Test public void checkAbilityToStartServiceUsingCapabilitiesAndFlags() {
        File app = ROOT_TEST_PATH.resolve("ApiDemos-debug.apk").toFile();

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(PLATFORM_NAME, "Android");
        caps.setCapability(FULL_RESET, true);
        caps.setCapability(NEW_COMMAND_TIMEOUT, 60);
        caps.setCapability(APP_PACKAGE, "io.appium.android.apis");
        caps.setCapability(APP_ACTIVITY, ".view.WebView1");
        caps.setCapability(APP, app.getAbsolutePath());
        caps.setCapability(CHROMEDRIVER_EXECUTABLE, chromeManager.getBinaryPath());

        service = new AppiumServiceBuilder()
                .withArgument(CALLBACK_ADDRESS, testIP)
                .withArgument(SESSION_OVERRIDE)
                .withArgument(PRE_LAUNCH)
                .withCapabilities(caps).build();
        service.start();
        assertTrue(service.isRunning());
    }

    @Test public void checkAbilityToChangeOutputStream() throws Exception {
        testLogFile = new File("test");
        testLogFile.createNewFile();
        stream = new FileOutputStream(testLogFile);
        service = buildDefaultService();
        service.addOutPutStream(stream);
        service.start();
        assertThat(testLogFile.length(), greaterThan(0L));
    }

    @Test public void checkAbilityToChangeOutputStreamAfterTheServiceIsStarted() throws Exception {
        testLogFile = new File("test");
        testLogFile.createNewFile();
        stream = new FileOutputStream(testLogFile);
        service = buildDefaultService();
        service.start();
        service.addOutPutStream(stream);
        service.isRunning();
        assertThat(testLogFile.length(), greaterThan(0L));
    }

    @Test public void checkAbilityToShutDownService() {
        service = buildDefaultService();
        service.start();
        service.stop();
        assertFalse(service.isRunning());
    }

    @Test public void checkAbilityToStartAndShutDownFewServices() throws Exception {
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

    @Test public void checkAbilityToStartServiceWithLogFile() throws Exception {
        testLogFile = new File("Log.txt");
        testLogFile.createNewFile();
        service = new AppiumServiceBuilder().withLogFile(testLogFile).build();
        service.start();
        assertTrue(testLogFile.exists());
        assertThat(testLogFile.length(), greaterThan(0L));
    }

    @Test public void checkAbilityToStartServiceWithPortUsingFlag() {
        String port = "8996";
        String expectedUrl = String.format("http://0.0.0.0:%s/wd/hub", port);

        service = new AppiumServiceBuilder()
                .withArgument(() -> "--port", port)
                .build();
        String actualUrl = service.getUrl().toString();
        assertEquals(expectedUrl, actualUrl);
        service.start();
    }
    
    @Test public void checkAbilityToStartServiceWithPortUsingShortFlag() {
        String port = "8996";
        String expectedUrl = String.format("http://0.0.0.0:%s/wd/hub", port);

        service = new AppiumServiceBuilder()
                .withArgument(() -> "-p", port)
                .build();
        String actualUrl = service.getUrl().toString();
        assertEquals(expectedUrl, actualUrl);
        service.start();
    }

    @Test public void checkAbilityToStartServiceWithIpUsingFlag() {
        String expectedUrl = String.format("http://%s:4723/wd/hub", testIP);

        service = new AppiumServiceBuilder()
                .withArgument(() -> "--address", testIP)
                .build();
        String actualUrl = service.getUrl().toString();
        assertEquals(expectedUrl, actualUrl);
        service.start();
    }

    @Test public void checkAbilityToStartServiceWithIpUsingShortFlag() {
        String expectedUrl = String.format("http://%s:4723/wd/hub", testIP);

        service = new AppiumServiceBuilder()
                .withArgument(() -> "-a", testIP)
                .build();
        String actualUrl = service.getUrl().toString();
        assertEquals(expectedUrl, actualUrl);
        service.start();
    }

    @Test public void checkAbilityToStartServiceWithLogFileUsingFlag() {
        testLogFile = new File("Log2.txt");

        service = new AppiumServiceBuilder()
                .withArgument(() -> "--log", testLogFile.getAbsolutePath())
                .build();
        service.start();
        assertTrue(testLogFile.exists());
    }

    @Test public void checkAbilityToStartServiceWithLogFileUsingShortFlag() {
        testLogFile = new File("Log3.txt");
        
        service = new AppiumServiceBuilder()
                .withArgument(() -> "-g", testLogFile.getAbsolutePath())
                .build();
        service.start();
        assertTrue(testLogFile.exists());
    }
}
