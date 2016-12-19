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

package io.appium.java_client.android;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import io.appium.java_client.MobileElement;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.touch.TouchActions;

import java.util.List;

public class AndroidTouchActionsTest extends BaseAndroidTest {

    @Before public void setUp() throws Exception {
        driver.resetApp();
    }

    @Test public void singleTapTest() throws Exception {
        driver.startActivity("io.appium.android.apis", ".view.Buttons1");
        Thread.sleep(5000);
        new TouchActions(driver)
                .singleTap(driver.findElementById("io.appium.android.apis:id/button_toggle"))
                .perform();
        Thread.sleep(5000);
        assertEquals("ON" ,driver
                .findElementById("io.appium.android.apis:id/button_toggle").getText());
    }
    
    @Test public void horizontalFlickTest() throws Exception {
        driver.startActivity("io.appium.android.apis", ".view.Gallery1");

        AndroidElement gallery = driver.findElementById("io.appium.android.apis:id/gallery");
        List<MobileElement> images = gallery
                .findElementsByClassName("android.widget.ImageView");
        int originalImageCount = images.size();
        Point location = gallery.getLocation();
        Point center = gallery.getCenter();
        
        TouchActions actions = new TouchActions(driver);
        actions.flick(gallery, -10, center.y - location.y, 1000)
                .perform();
        
        assertNotEquals(originalImageCount, gallery
                .findElementsByClassName("android.widget.ImageView").size());
    }
  
}
