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

import org.openqa.selenium.remote.Response;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.appium.java_client.MobileCommand.EXECUTE_CDP_COMMAND;

public interface ExecuteCDPCommand extends ExecutesMethod {

    /**
     * Allows to execute ChromeDevProtocol commands against Appium browser session
     *
     * @since Appium 1.18
     * @param command Command to execute against the browser (For Ref : https://chromedevtools.github.io/devtools-protocol/)
     * @param params additional parameters required to execute the command
     * @return Value (Output of the command execution)
     * @throws org.openqa.selenium.WebDriverException if there was a failure while executing the command
     */
    default Map<String,Object> executeCdpCommand(String command, @Nullable Map<String, Object> params) {
        Map<String, Object> data = new HashMap<>();
        data.put("cmd",checkNotNull(command));
        if(params != null){
            data.put("params",params);
        }else{
            params = new HashMap<>();
            data.put("params",params);
        }
        Response response = execute(EXECUTE_CDP_COMMAND, data);
        Map<String, Object> value = (Map<String, Object>) response.getValue();
        return value;
    }

    /**
     * Allows to execute ChromeDevProtocol commands against Appium browser session without parameters
     *
     * @since Appium 1.18
     * @param command Command to execute against the browser (For Ref : https://chromedevtools.github.io/devtools-protocol/)
     * @return Value (Output of the command execution)
     * @throws org.openqa.selenium.WebDriverException if there was a failure while executing the command
     */
    default Map<String,Object> executeCdpCommand(String command) {
        return executeCdpCommand(command,null);
    }
}
