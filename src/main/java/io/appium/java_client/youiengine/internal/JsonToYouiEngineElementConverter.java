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

package io.appium.java_client.youiengine.internal;

import io.appium.java_client.MobileElement;
import io.appium.java_client.internal.JsonToMobileElementConverter;
import io.appium.java_client.youiengine.YouiEngineElement;

import org.openqa.selenium.remote.RemoteWebDriver;


public class JsonToYouiEngineElementConverter extends JsonToMobileElementConverter {
    public JsonToYouiEngineElementConverter(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    protected MobileElement newMobileElement() {
        YouiEngineElement toReturn = new YouiEngineElement();
        toReturn.setParent(driver);
        return toReturn;
    }
}

