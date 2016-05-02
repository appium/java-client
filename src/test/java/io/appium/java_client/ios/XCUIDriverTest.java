/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.appium.java_client.ios;

import static org.junit.Assert.assertTrue;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;

public class XCUIDriverTest {

    private static final String SOURCE = "src/test/java/io/appium/java_client/";
    private static AppiumDriverLocalService service;
    protected static IOSDriver<MobileElement> driver;
        
    /**
     * initialization.
     */
    @BeforeClass
    public static void beforeClass() throws Exception {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();

        if (service == null || !service.isRunning()) {
            throw new RuntimeException("An appium server node is not started!");
        }

        String source = SOURCE + "UICatalog.app.zip";

        try {
            ZipFile zipFile = new ZipFile(source);
            zipFile.extractAll(SOURCE);
        } catch (ZipException e) {
            String msg = "Could not extract file";
            throw new ZipException(msg, e);
        }

        File appDir = new File(SOURCE);
        File app = new File(appDir, "UICatalog.app");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.3");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 6");
        //sometimes environment has performance problems
        capabilities.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 500000);
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        driver = new IOSDriver<>(service.getUrl(), capabilities);
    }

    /**
     * finishing.
     */
    @AfterClass
    public static void afterClass() throws IOException {
        if (driver != null) {
            driver.quit();
        }
        if (service != null) {
            service.stop();
        }
        try {
            FileUtils.deleteDirectory(new File(SOURCE + "/UICatalog.app"));
        } catch (IOException e) {
            throw e;
        }

    }
    
    //TODO There is no ability to check this function usibg simulators.
    // When CI will have been set up then this test will be returned
    public void getDeviceTimeTest() {
        String time = driver.getDeviceTime();
        assertTrue(time.length() == 28);
    }

    /**
     * Verifies UICatalog element is present in view.
     */
    @Test
    public void getiOSElementByPredicate() {
        //Needs to run on the XCUITest ios Driver (https://github.com/appium/appium-xcuitest-driver.git).
        driver.findElement(MobileBy.IosNsPredicateString("identifier == \"UICatalog\""));
    }

}
