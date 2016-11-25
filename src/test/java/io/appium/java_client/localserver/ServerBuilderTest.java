package io.appium.java_client.localserver;

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
import static java.lang.System.setProperty;
import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class ServerBuilderTest {

    private static Properties properties;
    private static String testIP;
    private AppiumDriverLocalService service;
    private File file;
    private OutputStream stream;


    /**
     * initialization.
     */
    @BeforeClass public static void beforeClass() throws Exception {
        File file =
            new File("src/test/java/io/appium/java_client/localserver/custom_node_path.properties");
        FileInputStream fileInput = new FileInputStream(file);
        properties = new Properties();
        properties.load(fileInput);
        fileInput.close();

        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
            .hasMoreElements(); ) {
            NetworkInterface intf = en.nextElement();
            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
                .hasMoreElements(); ) {
                InetAddress inetAddress = enumIpAddr.nextElement();
                if (!inetAddress.isLoopbackAddress()) {
                    InetAddressValidator validator = InetAddressValidator.getInstance();
                    testIP = inetAddress.getHostAddress().toString();
                    if (validator.isValid(testIP)) {
                        break;
                    }
                    testIP = null;
                }
            }
            if (testIP != null) {
                break;
            }
        }
    }

    @After
    public void tearDown() throws Exception {
        if (service != null) {
            service.stop();
        }
        if (stream != null) {
            stream.close();
        }
        if (file != null && file.exists()) {
            file.delete();
        }
        System.clearProperty(APPIUM_PATH);
    }

    private static File findCustomNode() {
        Platform current = Platform.getCurrent();
        if (current.is(Platform.WINDOWS)) {
            return new File(String.valueOf(properties.get("path.to.custom.node.win")));
        }

        if (current.is(Platform.MAC)) {
            return new File(String.valueOf(properties.get("path.to.custom.node.macos")));
        }

        return new File(String.valueOf(properties.get("path.to.custom.node.linux")));
    }

    @Test public void checkAbilityToStartDefaultService() {
        service = buildDefaultService();
        service.start();
        assertEquals(true, service.isRunning());
    }

    @Test public void checkAbilityToStartServiceUsingNodeDefinedInProperties() {
        String definedNode = findCustomNode().getAbsolutePath();
        setProperty(APPIUM_PATH, definedNode);
        service = buildDefaultService();
        service.start();
        assertTrue(service.isRunning());
    }

    @Test public void checkAbilityToStartServiceUsingNodeDefinedExplicitly() {
        File node = findCustomNode();
        service = new AppiumServiceBuilder().withAppiumJS(node).build();
        service.start();
        assertTrue(service.isRunning());
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
        file = new File("test");
        file.createNewFile();
        stream = new FileOutputStream(file);
        service = buildDefaultService();
        service.addOutPutStream(stream);
        service.start();
        assertTrue(file.length() > 0);
    }

    @Test public void checkAbilityToChangeOutputStreamAfterTheServiceIsStarted() throws Exception {
        file = new File("test");
        file.createNewFile();
        stream = new FileOutputStream(file);
        service = buildDefaultService();
        service.start();
        service.addOutPutStream(stream);
        service.isRunning();
        assertTrue(file.length() > 0);
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
        file = new File("Log.txt");
        file.createNewFile();
        service = new AppiumServiceBuilder().withLogFile(file).build();
        service.start();
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
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
        file = new File("Log2.txt");

        service = new AppiumServiceBuilder()
                .withArgument(() -> "--log", file.getAbsolutePath())
                .build();
        service.start();
        assertTrue(file.exists());
    }

    @Test public void checkAbilityToStartServiceWithLogFileUsingShortFlag() throws Exception {
        file = new File("Log3.txt");
        
        service = new AppiumServiceBuilder()
                .withArgument(() -> "-g", file.getAbsolutePath())
                .build();
        service.start();
        assertTrue(file.exists());
    }
}
