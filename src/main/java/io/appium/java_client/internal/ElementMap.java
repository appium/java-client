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

package io.appium.java_client.internal;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.youiengine.YouiEngineElement;

import java.util.Map;
import java.util.Optional;

public enum ElementMap {
    ANDROID_UIAUTOMATOR2(AutomationName.ANDROID_UIAUTOMATOR2.toLowerCase(), AndroidElement.class),
    SELENDROID(AutomationName.SELENDROID.toLowerCase(), AndroidElement.class),
    YOUI_ENGINE(AutomationName.YOUI_ENGINE.toLowerCase(), YouiEngineElement.class),
    IOS_XCUI_TEST(AutomationName.IOS_XCUI_TEST.toLowerCase(), IOSElement.class),
    ANDROID_UI_AUTOMATOR(MobilePlatform.ANDROID.toLowerCase(), AndroidElement.class),
    IOS_UI_AUTOMATION(MobilePlatform.IOS.toLowerCase(), IOSElement.class);


    private static final Map<String, ElementMap> mobileElementMap;

    static {
        ImmutableMap.Builder<String, ElementMap> builder = ImmutableMap.builder();
        for (ElementMap e:values()){
            builder.put(e.getPlatformOrAutomation(), e);
        }
        mobileElementMap = builder.build();
    }



    private final String platformOrAutomation;
    private final Class<? extends MobileElement> elementClass;

    private ElementMap(String platformOrAutomation, Class<? extends MobileElement> elementClass) {
        this.platformOrAutomation = platformOrAutomation;
        this.elementClass = elementClass;
    }

    public String getPlatformOrAutomation() {
        return platformOrAutomation;
    }

    public Class<? extends MobileElement> getElementClass() {
        return elementClass;
    }

    public static Class<? extends MobileElement> getElementClass(String platform, String automation) {
        ElementMap element = Optional.ofNullable(mobileElementMap.get(automation))
                .orElse(mobileElementMap.get(platform));
        if (element == null) {
            return null;
        }
        return element.getElementClass();
    }
}
