package io.appium.java_client.localserver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.commons.validator.routines.InetAddressValidator;
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
import java.util.Properties;

public class ServerBuilderTest {

    private static Properties properties;
    private static String testIP;

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

    @Test public void checkAbilityToBuildDefaultService() {
        AppiumDriverLocalService service = AppiumDriverLocalService.buildDefaultService();
        try {
            service.start();
            assertEquals(true, service.isRunning());
        } finally {
            service.stop();
        }
    }

    @Test public void checkAbilityToBuildServiceUsingNodeDefinedInProperties() {
        AppiumDriverLocalService service = null;
        try {
            String definedNode = findCustomNode().getAbsolutePath();
            System.setProperty(AppiumServiceBuilder.APPIUM_PATH, definedNode);
            service = AppiumDriverLocalService.buildDefaultService();
            service.start();
            assertEquals(true, service.isRunning());
        } finally {
            if (service != null) {
                service.stop();
            }
            System.clearProperty(AppiumServiceBuilder.APPIUM_PATH);
        }
    }

    @Test public void checkAbilityToBuildServiceUsingNodeDefinedExplicitly() {
        AppiumDriverLocalService service = null;
        try {
            File node = findCustomNode();
            service = new AppiumServiceBuilder().withAppiumJS(node).build();
            service.start();
            assertEquals(true, service.isRunning());
        } finally {
            if (service != null) {
                service.stop();
            }
        }
    }

    @Test public void checkAbilityToStartServiceOnAFreePort() {
        AppiumDriverLocalService service = null;
        try {
            service = new AppiumServiceBuilder().usingAnyFreePort().build();
            service.start();
            assertEquals(true, service.isRunning());
        } finally {
            if (service != null) {
                service.stop();
            }
        }
    }

    @Test public void checkAbilityToStartServiceUsingNonLocalhostIP() throws Exception {
        AppiumDriverLocalService service = null;
        try {
            service = new AppiumServiceBuilder().withIPAddress(testIP).build();
            service.start();
            assertEquals(true, service.isRunning());
        } finally {
            if (service != null) {
                service.stop();
            }
        }
    }

    @Test public void checkAbilityToStartServiceUsingFlags() throws Exception {
        AppiumDriverLocalService service = null;
        try {
            service =
                new AppiumServiceBuilder().withArgument(GeneralServerFlag.CALLBACK_ADDRESS, testIP)
                    .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                    .withArgument(GeneralServerFlag.PRE_LAUNCH).build();
            service.start();
            assertEquals(true, service.isRunning());
        } finally {
            if (service != null) {
                service.stop();
            }
        }
    }

    @Test public void checkAbilityToStartServiceUsingCapabilities() throws Exception {
        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "ApiDemos-debug.apk");


        File pageFactoryDir = new File("src/test/java/io/appium/java_client/pagefactory_tests");
        File chrome = new File(pageFactoryDir, "chromedriver.exe");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
        capabilities
            .setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "io.appium.android.apis");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".view.WebView1");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE,
            chrome.getAbsolutePath());

        AppiumDriverLocalService service = null;
        try {
            service = new AppiumServiceBuilder().withCapabilities(capabilities).build();
            service.start();
            assertEquals(true, service.isRunning());
        } finally {
            if (service != null) {
                service.stop();
            }
        }
    }

    @Test public void checkAbilityToStartServiceUsingCapabilitiesAndFlags() throws Exception {
        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "ApiDemos-debug.apk");


        File pageFactoryDir = new File("src/test/java/io/appium/java_client/pagefactory_tests");
        File chrome = new File(pageFactoryDir, "chromedriver.exe");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
        capabilities
            .setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "io.appium.android.apis");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".view.WebView1");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE,
            chrome.getAbsolutePath());

        AppiumDriverLocalService service = null;
        try {
            service =
                new AppiumServiceBuilder().withArgument(GeneralServerFlag.CALLBACK_ADDRESS, testIP)
                    .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                    .withArgument(GeneralServerFlag.PRE_LAUNCH)
                    .withCapabilities(capabilities).build();
            service.start();
            assertEquals(true, service.isRunning());
        } finally {
            if (service != null) {
                service.stop();
            }
        }
    }

    @Test public void checkAbilityToChangeOutputStream() throws Exception {
        File file = new File("target/test");
        file.createNewFile();
        OutputStream stream = new FileOutputStream(file);
        AppiumDriverLocalService service = AppiumDriverLocalService.buildDefaultService();
        service.addOutPutStream(stream);
        try {
            service.start();
            assertTrue(file.length() > 0);
        } finally {
            service.stop();
            if (stream != null) {
                stream.close();
            }

            if (file.exists()) {
                file.delete();
            }
        }
    }

    @Test public void checkAbilityToChangeOutputStreamAfterTheServiceIsStarted() throws Exception {
        File file = new File("target/test");
        file.createNewFile();
        OutputStream stream = new FileOutputStream(file);
        AppiumDriverLocalService service = AppiumDriverLocalService.buildDefaultService();
        try {
            service.start();
            service.addOutPutStream(stream);
            service.isRunning();
            assertTrue(file.length() > 0);
        } finally {
            service.stop();
            if (stream != null) {
                stream.close();
            }

            if (file.exists()) {
                file.delete();
            }
        }
    }

    @Test public void checkAbilityToShutDownService() {
        AppiumDriverLocalService service = AppiumDriverLocalService.buildDefaultService();
        service.start();
        service.stop();
        assertTrue(!service.isRunning());
    }

    @Test public void checkAbilityToStartAndShutDownFewServices() throws Exception {
        AppiumDriverLocalService service1 = new AppiumServiceBuilder().usingAnyFreePort().build();
        service1.start();
        AppiumDriverLocalService service2 = new AppiumServiceBuilder().usingAnyFreePort().build();
        service2.start();
        AppiumDriverLocalService service3 = new AppiumServiceBuilder().usingAnyFreePort().build();
        service3.start();
        AppiumDriverLocalService service4 = new AppiumServiceBuilder().usingAnyFreePort().build();
        service4.start();
        service1.stop();
        Thread.sleep(1000);
        service2.stop();
        Thread.sleep(1000);
        service3.stop();
        Thread.sleep(1000);
        service4.stop();
        assertTrue(!service1.isRunning());
        assertTrue(!service2.isRunning());
        assertTrue(!service3.isRunning());
        assertTrue(!service4.isRunning());
    }

    @Test public void checkAbilityToStartServiceWithLogFile() throws Exception {
        AppiumDriverLocalService service = null;
        File rootLogDir = new File("target/");
        File log = new File(rootLogDir, "Log.txt");
        log.createNewFile();
        try {
            service = new AppiumServiceBuilder().withLogFile(log).build();
            service.start();
            assertEquals(true, log.exists());
            assertEquals(true, log.length() > 0);
        } finally {
            if (log.exists()) {
                log.delete();
            }
            if (service != null) {
                service.stop();
            }
        }
    }
}
