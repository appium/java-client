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

package io.appium.java_client;

import org.openqa.selenium.remote.http.W3CHttpCommandCodec;

import java.util.Map;

import static org.openqa.selenium.remote.DriverCommand.ACCEPT_ALERT;
import static org.openqa.selenium.remote.DriverCommand.ACTIONS;
import static org.openqa.selenium.remote.DriverCommand.CLEAR_ACTIONS_STATE;
import static org.openqa.selenium.remote.DriverCommand.DISMISS_ALERT;
import static org.openqa.selenium.remote.DriverCommand.EXECUTE_ASYNC_SCRIPT;
import static org.openqa.selenium.remote.DriverCommand.EXECUTE_SCRIPT;
import static org.openqa.selenium.remote.DriverCommand.GET_ACTIVE_ELEMENT;
import static org.openqa.selenium.remote.DriverCommand.GET_ALERT_TEXT;
import static org.openqa.selenium.remote.DriverCommand.GET_CURRENT_WINDOW_HANDLE;
import static org.openqa.selenium.remote.DriverCommand.GET_CURRENT_WINDOW_POSITION;
import static org.openqa.selenium.remote.DriverCommand.GET_CURRENT_WINDOW_SIZE;
import static org.openqa.selenium.remote.DriverCommand.GET_ELEMENT_LOCATION;
import static org.openqa.selenium.remote.DriverCommand.GET_ELEMENT_RECT;
import static org.openqa.selenium.remote.DriverCommand.GET_ELEMENT_SIZE;
import static org.openqa.selenium.remote.DriverCommand.GET_WINDOW_HANDLES;
import static org.openqa.selenium.remote.DriverCommand.MAXIMIZE_CURRENT_WINDOW;
import static org.openqa.selenium.remote.DriverCommand.SEND_KEYS_TO_ACTIVE_ELEMENT;
import static org.openqa.selenium.remote.DriverCommand.SEND_KEYS_TO_ELEMENT;
import static org.openqa.selenium.remote.DriverCommand.SET_ALERT_VALUE;
import static org.openqa.selenium.remote.DriverCommand.SET_CURRENT_WINDOW_POSITION;
import static org.openqa.selenium.remote.DriverCommand.SET_CURRENT_WINDOW_SIZE;
import static org.openqa.selenium.remote.DriverCommand.SET_TIMEOUT;


public class AppiumW3CHttpCommandCodec extends W3CHttpCommandCodec {

    public AppiumW3CHttpCommandCodec() {
        alias(GET_ELEMENT_LOCATION, GET_ELEMENT_RECT);
        alias(GET_ELEMENT_SIZE, GET_ELEMENT_RECT);

        defineCommand(EXECUTE_SCRIPT, post("/session/:sessionId/execute/sync"));
        defineCommand(EXECUTE_ASYNC_SCRIPT, post("/session/:sessionId/execute/async"));

        defineCommand(MAXIMIZE_CURRENT_WINDOW, post("/session/:sessionId/window/maximize"));
        defineCommand(GET_CURRENT_WINDOW_SIZE, get("/session/:sessionId/window/rect"));
        defineCommand(SET_CURRENT_WINDOW_SIZE, post("/session/:sessionId/window/rect"));
        alias(GET_CURRENT_WINDOW_POSITION, GET_CURRENT_WINDOW_SIZE);
        alias(SET_CURRENT_WINDOW_POSITION, SET_CURRENT_WINDOW_SIZE);
        defineCommand(GET_CURRENT_WINDOW_HANDLE, get("/session/:sessionId/window"));
        defineCommand(GET_WINDOW_HANDLES, get("/session/:sessionId/window/handles"));

        defineCommand(ACCEPT_ALERT, post("/session/:sessionId/alert/accept"));
        defineCommand(DISMISS_ALERT, post("/session/:sessionId/alert/dismiss"));
        defineCommand(GET_ALERT_TEXT, get("/session/:sessionId/alert/text"));
        defineCommand(SET_ALERT_VALUE, post("/session/:sessionId/alert/text"));

        defineCommand(GET_ACTIVE_ELEMENT, get("/session/:sessionId/element/active"));

        defineCommand(ACTIONS, post("/session/:sessionId/actions"));
        defineCommand(CLEAR_ACTIONS_STATE, delete("/session/:sessionId/actions"));
    }

    @Override
    protected Map<String, ?> amendParameters(String name, Map<String, ?> parameters) {
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
