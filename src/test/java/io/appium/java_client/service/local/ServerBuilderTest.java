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
import static java.lang.System.getProperty;
import static java.lang.System.setProperty;
import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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
import java.util.Enumeration;
import java.util.List;

public class ServerBuilderTest {

    /**
     * It may be impossible to find the path to the instance of appium server due to different circumstance.
     * So user may use environment variables/system properties to define the full path to the server
     * appium.js that is supposed to be default.
     */
    private static final String PATH_TO_APPIUM_NODE_IN_PROPERTIES = getProperty(APPIUM_PATH);
    /**
     * This is the path to the stub appium.js file
     */
    private static final String PATH_T0_TEST_APPIUM_JS = "src/test/java/io/appium/java_client/service/local/appium.js";

    private static String testIP;
    private AppiumDriverLocalService service;
    private File testLogFile;
    private OutputStream stream;

    /**
     * initialization.
     */
    @BeforeClass public static void beforeClass() throws Exception {
        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
            .hasMoreElements(); ) {
            NetworkInterface intf = en.nextElement();
            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
                .hasMoreElements(); ) {
                InetAddress inetAddress = enumIpAddr.nextElement();
                if (!inetAddress.isLoopbackAddress()) {
                    InetAddressValidator validator = InetAddressValidator.getInstance();

                    testIP = ofNullable(testIP).orElseGet(() -> {
                        String result = inetAddress.getHostAddress();
                        if (validator.isValid(result)) {
                            return result;
                        }
                        return null;
                    });
                }
            }
        }
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
        String definedNode = new File(PATH_T0_TEST_APPIUM_JS).getAbsolutePath();
        setProperty(APPIUM_PATH, definedNode);
        assertThat(new File(new AppiumServiceBuilder().createArgs().get(0)), is(new File(definedNode)));
    }

    @Test public void checkAbilityToUseNodeDefinedExplicitly() {
        File node = new File(PATH_T0_TEST_APPIUM_JS);
        AppiumServiceBuilder builder = new AppiumServiceBuilder().withAppiumJS(node);
        assertThat(new File(builder.createArgs().get(0)).getAbsolutePath(), is(node.getAbsolutePath()));
    }

    @Test public void checkAbilityToStartServiceOnAFreePort() {
        service = new AppiumServiceBuilder().usingAnyFreePort().build();
        service.start();
        assertTrue(service.isRunning());
    }

    @Test public void checkAbilityToStartServiceUsingNonLocalhostIP() throws Exception {
        service = new AppiumServiceBuilder().withIPAddress(testIP).build();
        service.start();
        assertTrue(service.isRunning());
    }

    @Test public void checkAbilityToStartServiceUsingFlags() throws Exception {
        service = new AppiumServiceBuilder()
            .withArgument(CALLBACK_ADDRESS, testIP)
            .withArgument(SESSION_OVERRIDE)
            .withArgument(PRE_LAUNCH)
            .build();
        service.start();
        assertTrue(service.isRunning());
    }

    @Test public void checkAbilityToStartServiceUsingCapabilities() throws Exception {
        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "ApiDemos-debug.apk");
        File pageFactoryDir = new File("src/test/java/io/appium/java_client/pagefactory_tests");
        File chrome = new File(pageFactoryDir, "chromedriver.exe");

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(PLATFORM_NAME, "Android");
        caps.setCapability(FULL_RESET, true);
        caps.setCapability(NEW_COMMAND_TIMEOUT, 60);
        caps.setCapability(APP_PACKAGE, "io.appium.android.apis");
        caps.setCapability(APP_ACTIVITY, ".view.WebView1");
        caps.setCapability(APP, app.getAbsolutePath());
        caps.setCapability(CHROMEDRIVER_EXECUTABLE, chrome.getAbsolutePath());

        service = new AppiumServiceBuilder().withCapabilities(caps).build();
        service.start();
        assertTrue(service.isRunning());
    }

    @Test public void checkAbilityToStartServiceUsingCapabilitiesAndFlags() throws Exception {
        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "ApiDemos-debug.apk");
        File pageFactoryDir = new File("src/test/java/io/appium/java_client/pagefactory_tests");
        File chrome = new File(pageFactoryDir, "chromedriver.exe");

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(PLATFORM_NAME, "Android");
        caps.setCapability(FULL_RESET, true);
        caps.setCapability(NEW_COMMAND_TIMEOUT, 60);
        caps.setCapability(APP_PACKAGE, "io.appium.android.apis");
        caps.setCapability(APP_ACTIVITY, ".view.WebView1");
        caps.setCapability(APP, app.getAbsolutePath());
        caps.setCapability(CHROMEDRIVER_EXECUTABLE, chrome.getAbsolutePath());

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

    @Test public void checkAbilityToStartServiceWithPortUsingFlag() throws Exception {
        String port = "8996";
        String expectedUrl = String.format("http://0.0.0.0:%s/wd/hub", port);

        service = new AppiumServiceBuilder()
                .withArgument(() -> "--port", port)
                .build();
        String actualUrl = service.getUrl().toString();
        assertEquals(expectedUrl, actualUrl);
        service.start();
    }
    
    @Test public void checkAbilityToStartServiceWithPortUsingShortFlag() throws Exception {
        String port = "8996";
        String expectedUrl = String.format("http://0.0.0.0:%s/wd/hub", port);

        service = new AppiumServiceBuilder()
                .withArgument(() -> "-p", port)
                .build();
        String actualUrl = service.getUrl().toString();
        assertEquals(expectedUrl, actualUrl);
        service.start();
    }

    @Test public void checkAbilityToStartServiceWithIpUsingFlag() throws Exception {
        String expectedUrl = String.format("http://%s:4723/wd/hub", testIP);

        service = new AppiumServiceBuilder()
                .withArgument(() -> "--address", testIP)
                .build();
        String actualUrl = service.getUrl().toString();
        assertEquals(expectedUrl, actualUrl);
        service.start();
    }

    @Test public void checkAbilityToStartServiceWithIpUsingShortFlag() throws Exception {
        String expectedUrl = String.format("http://%s:4723/wd/hub", testIP);

        service = new AppiumServiceBuilder()
                .withArgument(() -> "-a", testIP)
                .build();
        String actualUrl = service.getUrl().toString();
        assertEquals(expectedUrl, actualUrl);
        service.start();
    }

    @Test public void checkAbilityToStartServiceWithLogFileUsingFlag() throws Exception {
        testLogFile = new File("Log2.txt");

        service = new AppiumServiceBuilder()
                .withArgument(() -> "--log", testLogFile.getAbsolutePath())
                .build();
        service.start();
        assertTrue(testLogFile.exists());
    }

    @Test public void checkAbilityToStartServiceWithLogFileUsingShortFlag() throws Exception {
        testLogFile = new File("Log3.txt");
        
        service = new AppiumServiceBuilder()
                .withArgument(() -> "-g", testLogFile.getAbsolutePath())
                .build();
        service.start();
        assertTrue(testLogFile.exists());
    }
}
