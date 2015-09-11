package io.appium.java_client.localserver;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Platform;

import java.io.*;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class ServerBuilderTest {

    private static Properties properties;

    @BeforeClass
    public static void beforeClass() throws Exception{
        File file = new File("src/test/java/io/appium/java_client/localserver/custom_node_path.properties");
        FileInputStream fileInput = new FileInputStream(file);
        properties = new Properties();
        properties.load(fileInput);
        fileInput.close();
    }

    private static File findCustomNode(){
        Platform current = Platform.getCurrent();
        if (current.is(Platform.WINDOWS))
            return new File(String.valueOf(properties.get("path.to.custom.node.win")));

        if (current.is(Platform.MAC))
            return new File(String.valueOf(properties.get("path.to.custom.node.macos")));

        return new File(String.valueOf(properties.get("path.to.custom.node.linux")));
    }

    @Test
    public void checkAbilityToBuildDefaultService(){
        AppiumDriverLocalService.buildDefaultService();
    }

    @Test
    public void checkAbilityToBuildServiceWithDefinedParametersAndNodeSetInProperties(){
        try {
            String definedNode = findCustomNode().getAbsolutePath();
            System.setProperty(AppiumServiceBuilder.APPIUM_NODE_PROPERTY, definedNode);
            AppiumDriverLocalService.buildService(new AppiumServiceBuilder().withIPAddress("127.0.0.1").
                    usingPort(4000).withArgument(GeneralServerFlag.LOG_TIMESTAMP,""));
        }
        finally {
            System.clearProperty(AppiumServiceBuilder.APPIUM_NODE_PROPERTY);
        }
    }

    @Test
    public void checkAbilityToStartServiceOnAFreePort(){
        try {
            String definedNode = findCustomNode().getAbsolutePath();
            System.setProperty(AppiumServiceBuilder.APPIUM_NODE_PROPERTY, definedNode);
            AppiumDriverLocalService service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder().withIPAddress("127.0.0.1").
                    usingAnyFreePort().withArgument(GeneralServerFlag.LOG_TIMESTAMP));
            service.start();
            assertEquals(true, service.isRunning());
            service.stop();
        }
        finally {
            System.clearProperty(AppiumServiceBuilder.APPIUM_NODE_PROPERTY);
        }
    }

    @Test
    public void checkAbilityToBuildServiceWithDefinedParametersAndExternallyDefinedNode(){
        File definedNode = findCustomNode();
        AppiumDriverLocalService.buildService(new AppiumServiceBuilder().withAppiumJS(definedNode).withIPAddress("127.0.0.1").
                usingPort(4000).withArgument(GeneralServerFlag.LOG_TIMESTAMP,""));
    }

    @Test
    public void checkStartingOfDefaultService(){
        AppiumDriverLocalService service = AppiumDriverLocalService.buildDefaultService();
        service.start();
        assertEquals(true, service.isRunning());
        service.stop();
    }

    @Test
    public void checkStartingOfTheServiceDefinedByProperty(){
        try {
            String definedNode = findCustomNode().getAbsolutePath();
            System.setProperty(AppiumServiceBuilder.APPIUM_NODE_PROPERTY, definedNode);
            AppiumDriverLocalService service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder().withIPAddress("127.0.0.1").
                    usingPort(4000).withArgument(GeneralServerFlag.LOG_TIMESTAMP));
            service.start();
            assertEquals(true, service.isRunning());
            service.stop();
        }
        finally {
            System.clearProperty(AppiumServiceBuilder.APPIUM_NODE_PROPERTY);
        }
    }

    @Test
    public void checkStartingOfTheServiceDefinedExternally(){
        File definedNode = findCustomNode();
        AppiumDriverLocalService service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder().withAppiumJS(definedNode).withIPAddress("127.0.0.1").
                usingPort(4000).withArgument(GeneralServerFlag.LOG_TIMESTAMP,""));
        service.start();
        assertEquals(true, service.isRunning());
        service.stop();
    }
}
