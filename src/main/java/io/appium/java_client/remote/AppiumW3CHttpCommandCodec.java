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

package io.appium.java_client.remote;

import org.openqa.selenium.remote.http.W3CHttpCommandCodec;

import java.util.Map;

import static org.openqa.selenium.remote.DriverCommand.GET_ELEMENT_ATTRIBUTE;
import static org.openqa.selenium.remote.DriverCommand.GET_ELEMENT_LOCATION;
import static org.openqa.selenium.remote.DriverCommand.GET_ELEMENT_LOCATION_ONCE_SCROLLED_INTO_VIEW;
import static org.openqa.selenium.remote.DriverCommand.GET_ELEMENT_SIZE;
import static org.openqa.selenium.remote.DriverCommand.GET_PAGE_SOURCE;
import static org.openqa.selenium.remote.DriverCommand.IS_ELEMENT_DISPLAYED;
import static org.openqa.selenium.remote.DriverCommand.SEND_KEYS_TO_ACTIVE_ELEMENT;
import static org.openqa.selenium.remote.DriverCommand.SEND_KEYS_TO_ELEMENT;
import static org.openqa.selenium.remote.DriverCommand.SET_ALERT_VALUE;
import static org.openqa.selenium.remote.DriverCommand.SET_TIMEOUT;
import static org.openqa.selenium.remote.DriverCommand.SUBMIT_ELEMENT;


public class AppiumW3CHttpCommandCodec extends W3CHttpCommandCodec {

    /**
     * This class overrides the built-in Selenium W3C commands codec,
     * since the latter hardcodes many commands in Javascript,
     * which does not work with Appium.
     * Check https://www.w3.org/TR/webdriver/ to see all available W3C
     * endpoints.
     */
    public AppiumW3CHttpCommandCodec() {
        defineCommand(GET_ELEMENT_ATTRIBUTE, get("/session/:sessionId/element/:id/attribute/:name"));
        defineCommand(GET_PAGE_SOURCE, get("/session/:sessionId/source"));
    }

    @Override
    public void alias(String commandName, String isAnAliasFor) {
        // This blocks parent constructor from undesirable aliases assigning
        switch (commandName) {
            case GET_ELEMENT_ATTRIBUTE:
            case GET_ELEMENT_LOCATION:
            case GET_ELEMENT_LOCATION_ONCE_SCROLLED_INTO_VIEW:
            case GET_ELEMENT_SIZE:
            case IS_ELEMENT_DISPLAYED:
            case SUBMIT_ELEMENT:
            case GET_PAGE_SOURCE:
                break;
            default:
                super.alias(commandName, isAnAliasFor);
        }
    }

    @Override
    protected Map<String, ?> amendParameters(String name, Map<String, ?> parameters) {
        // This blocks parent constructor from undesirable parameters amending
        switch (name) {
            case SEND_KEYS_TO_ACTIVE_ELEMENT:
            case SEND_KEYS_TO_ELEMENT:
            case SET_ALERT_VALUE:
            case SET_TIMEOUT:
                return super.amendParameters(name, parameters);
            default:
                return parameters;
        }
    }
}
